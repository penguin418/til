package validator;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.IllegalFormatException;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * \@Valid에 대한 실제 검증 로직을 담고있는 프로세서 클래스
 *  필요시 상속하여 세부 로직을 수정해서 사용하세요 히힣
 *
 * @author penguin418@naver.com
 */
public class Validator {

    /**
     * \@Valid에 대해 검증한다
     *
     * @param classObj {Object} 오브젝트
     * @throws IllegalAccessException   타입에 접근할 수 없을 때(inaccessible) 발생하는 예외
     * @throws IllegalArgumentException 검증에 실패한 때 발생하는 예외
     */
    public <T> void validate(Object classObj) throws IllegalAccessException, IllegalArgumentException {
        for (Field field : classObj.getClass().getDeclaredFields()) {
            if (field.getAnnotation(Valid.class) == null)
                continue;

            FieldType fieldType = field.getAnnotation(Valid.class).value();
            field.setAccessible(true);
            Object value = field.get(classObj);
            validateField(fieldType, value);
        }
    }

    /**
     * 종류에 따라 검증 메소드를 호출한다
     *
     * @param  fieldType {FieldType} 필드
     * @param  value     {Object} 값
     * @throws IllegalArgumentException 검증에 실패한 때 발생하는 예외
     */
    private <T> void validateField(FieldType fieldType, Object value) throws IllegalArgumentException {
        switch (fieldType) {
            case EMAIL:
                validateEmail(value);
                break;
            case PASSWORD:
                validatePassword(value);
                break;
            case PHONE_NUMBER:
                validatePhoneNumber(value);
                break;
        }
    }

    /**
     * 이메일을 검증한다
     *
     * @param emailObj {String}
     * @throws IllegalArgumentException 검증에 실패한 때 발생하는 예외
     */
    protected void validateEmail(Object emailObj) throws IllegalArgumentException {
        String email = (String) emailObj;
        if (!email.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]+$")) {
            throw new IllegalArgumentException("email is not valid. " +
                    "should be like example@email.com");
        }
    }

    /**
     * 패스워드를 검증한다
     *
     * @param passwordObj {String}
     * @throws IllegalArgumentException 검증에 실패한 때 발생하는 예외
     */
    protected void validatePassword(Object passwordObj) throws IllegalArgumentException {
        String password = (String) passwordObj;
        if (Stream.of("!", "@", "#", "$", "%", "^", "&", "*").noneMatch(password::contains)) {
            throw new IllegalArgumentException("password is not valid, should have at least one escape character");
        } else if ((password.length() < 8) || (password.length() > 32)) {
            throw new IllegalArgumentException("password is not valid, should be between 8 - 32 characters");
        }
    }

    /**
     * 핸드폰 번호를 검증한다
     *
     * @param phoneNumberObj {String} 이메일
     * @throws IllegalArgumentException 검증에 실패한 때 발생하는 예외
     */
    protected void validatePhoneNumber(Object phoneNumberObj) throws IllegalArgumentException {
        String phoneNumber = (String) phoneNumberObj;
        if (!phoneNumber.matches("01[01](-\\d{3,4}){2}")) {
            throw new IllegalArgumentException("phoneNumber is not valid, should be like 010-1234-5678");
        } else if ((phoneNumber.length() <= 8) || (phoneNumber.length() >= 14)) {
            throw new IllegalArgumentException("phoneNumber is not valid, should be between 8 - 14 characters");
        }
    }
}
