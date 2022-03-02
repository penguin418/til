package com.github.penguin418.practice01_password;

import io.vertx.core.*;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.ChainAuth;
import io.vertx.ext.auth.User;
import io.vertx.ext.auth.authentication.AuthenticationProvider;
import io.vertx.ext.auth.authorization.*;
import io.vertx.ext.auth.properties.PropertyFileAuthentication;
import io.vertx.ext.auth.properties.PropertyFileAuthorization;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PropertyAuthVerticle extends AbstractVerticle {

    private PropertyAuthHandler authHandler;
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        authHandler = new PropertyAuthHandler(vertx);

        authHandler.login("user1", "pass1").onSuccess(user -> {
            authHandler.checkRole(user, "admin");
            authHandler.checkPermission(user, "admin");
        });

        authHandler.login("user2", "pass2").onSuccess(user -> {
            authHandler.checkRole(user, "manager");
            authHandler.checkPermission(user, "manager");
        });

        authHandler.login("user3", "pass3").onSuccess(user -> {
            authHandler.checkRole(user, "writer");
            authHandler.checkPermission(user, "writer");
        });

        startPromise.complete();
    }


    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(new PropertyAuthVerticle());
    }
}

@Slf4j
class PropertyAuthHandler{
    // properties 파일을 사용해서 인증을 관리하는 방식
    private final String AUTH_FILE_NAME = "auth.properties";
    private PropertyFileAuthentication authenticationProvider;
    private AuthorizationProvider authorizationProvider;

    public PropertyAuthHandler(Vertx vertx) {
        // 인증을 위한 authentication provider
        authenticationProvider = PropertyFileAuthentication.create(vertx, AUTH_FILE_NAME);
        // 인가를 위한 authorization provider
        authorizationProvider = PropertyFileAuthorization.create(vertx, AUTH_FILE_NAME);
    }

    // 로그인
    public Future<User> login(String username, String password) {
        Promise<User> promise = Promise.promise();
        // 로그인 정보 생성
        JsonObject authInfo = new JsonObject().put("username", username).put("password", password);
        // 로그인 시도
        authenticationProvider.authenticate(authInfo).onSuccess(user -> {
            // 성공 후 이름을 다음과 같이 획득 가능
            final String loginSuccessName = user.principal().getString("username");
            log.info("{} login success", loginSuccessName);
            promise.complete(user);
        }).onFailure((event -> {
            log.warn("{} login failed", username);
            promise.fail(event);
        }));
        return promise.future();
    }

    public Future<Boolean> checkPermission(User user, String permissionName) {
        Promise<Boolean> promise = Promise.promise();
        // 입력한 권한
        final Authorization permission = WildcardPermissionBasedAuthorization.create(permissionName);
        return checkAuthorization(user, permission, permissionName, promise);
    }

    public Future<Boolean> checkRole(User user, String roleName) {
        Promise<Boolean> promise = Promise.promise();
        // 입력한 권한
        final Authorization role = RoleBasedAuthorization.create(roleName);
        return checkAuthorization(user, role, roleName, promise);
    }

    private Future<Boolean> checkAuthorization(User user, Authorization permission, String roleName, Promise<Boolean> promise) {
        // 유저 객체에서 권한을 추출
        authorizationProvider.getAuthorizations(user).onSuccess(auth -> {
            // 일치여부 확인
            AuthorizationContext context = AuthorizationContext.create(user);
            boolean result = permission.match(context);
            log.info("is user {} has {} authorization ? {}", context.user().principal().getString("username"), roleName, result);
            promise.complete(result);
        }).onFailure(promise::fail);
        return promise.future();
    }
}