# 자바-버전관리 (gradle, maven)

# Maven

프로젝트 관리도구이다

- 사용 중인 라이브러리의 의존성을 관리한다
- 빌드 순서를 관리한다

설정파일

pom.xml : 프로젝트 설정 파일

- 프로젝트 설정

USER_HOME/.m2/setting.xml : 

- 저장소 설정
- 의존성 관리

MAVEN_HOEM/settings.xml : 전역 설정

### POM(project object model) 구조

maven의 기본 설정 파일로 프로젝트 정보를 관리할 수 있다 

- XML파일로, 루트 태그인 <project>로 감싸져있다
- 메이븐에서 제공하는 기본값인 POM (=Super POM) 을 상속받으며, 별도의 값을 지정하지 않는 경우 기본값이 설정된다

POM의 구성

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" 
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
http://maven.apache.org/maven-v4_0_0.xsd">
  <modelVersion>4.0.0</modelVersion>
	<groupId>org.springframework</groupId>
  <artifactId>gs-maven</artifactId>
  <packaging>jar</packaging>
  <version>0.1.0</version>
	
	<properties>
      <maven.compiler.source>1.8</maven.compiler.source>
      <maven.compiler.target>1.8</maven.compiler.target>
  </properties>

  <dependencies>
      <dependency>
          <groupId>junit</groupId>
          <artifactId>junit</artifactId>
          <version>4.12</version>
					<scope>test</scope>
      </dependency>
  </dependencies>
	<build>
		<plugins>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-shade-plugin</artifactId>
				<version>3.2.4</version>
				<executions>
					<execution>
						<phase>package</phase>
						<goals>
							<goal>shade</goal>
						</goals>
						<configuration>
							<transformers>
								<transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
									<mainClass>hello.HelloWorld</mainClass>
								</transformer>
							</transformers>
						</configuration>
					</execution>
				</executions>
			</plugin>
		</plugins>
	</build>
</project>
```

- 프로젝트 정보
- properties (속성)
- dependencies (의존성)
- build (빌드 설정)
- 추가정보

출처: [start.spring.io](http://start.spring.io) 

참고: [http://maven.apache.org/ref/3.5.3/maven-model/maven.html](http://maven.apache.org/ref/3.5.3/maven-model/maven.html)

### (프로젝트 정보)

```jsx
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" 
xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
http://maven.apache.org/maven-v4_0_0.xsd">
  <modelVersion>4.0.0</modelVersion>
	<groupId>org.springframework</groupId>
  <artifactId>gs-maven</artifactId>
	<name>test</name>
  <version>0.1.0</version>
  <packaging>jar</packaging>

	<!--properties, dependencies, build 작성-->

</project>
```

별도의 구분 없이 루트태그 아래 바로 작성하면 된다

- <modelVersion></modelVersion>

    모델 버전

- <groupId></groupId>

    필수

    e.g. org.springframework

    프로젝트를 구분하기 위한 항목

    도메인이 사용됨

- <artifactId></artifactId>

    필수

    프로젝트 이름

- <name></name>

    이름, 없어도 된다

- <version></version>

    필수

    SNAPSHOT과 RELEASE를 구분할 수 있다

- <packaging></packaging>

    jar 인지, war 인지 등

### properties (속성)

```jsx
	<properties>
    <maven.compiler.source>1.8</maven.compiler.source>
    <maven.compiler.target>1.8</maven.compiler.target>
  </properties>
```

반복해서 사용할 상수 값을 정의한다

주로 버전을 정의한다

- <${key}>value</${key}>

    키와 그 값

### dependencies (의존성)

```jsx
  <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.12</version>
			<scope>test</scope>
    </dependency>
  </dependencies>
```

의존성 정보를 담는다

- groupId : artifactId : version 정보가 항상 필요하다
- 버전 정보의 경우 생략할 수도 있다

    이때는 최신 버전이 사용된다 → 불안정

dependency scope (의존성 스코프):

- compile (default)

    미입력시 지정됨 

    컴파일/배포시 항상 함께 제공

- provided

    컴파일시에만 제공

    외부 컨테이너에서 제공하는 api는 패키징할 때 포함하지 않아도 된다

    이 경우, provided를 사용

- runtime

    실행 환경에서만 사용됨

    런타임과 테스트때는 classpath에 추가되지만, 컴파일시 추가되지 않는다

- test

    테스트 때만 사용됨

- system

    provided와 동일

    메이블 중앙 저장소 사용 안함

excluded dependencies(의존성 제외)

충돌로 인해 어떤 라이브러리의 일부 기능을 사용할 수 없는 경우 제외할 수 있다

```jsx
<dependency>
	<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-web</artifactId>
		<exclusions>
		<exclusion>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-logging</artifactId>
		</exclusion>
	</exclusions>
</dependency>
```

```jsx
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-log4j2</artifactId>
</dependency>
```

- 예를 들어, log4j2를 사요하고 싶은 경우 스프링부트는 기본적으로 common-logging을 포함하기 때문에 제외시켜주어야 한다

optional dependencies(의존성 선택)

여러 의존성을 지원하고, 그 중에 하나를 사용할 수 있도록 지원하는 기능

- <optional>true</optional>

    해당 의존성이 선택적인 요소임을 의미

참고: [https://m.blog.naver.com/PostView.nhn?blogId=kathar0s&logNo=10142875558&proxyReferer=https:%2F%2Fwww.google.com%2F](https://m.blog.naver.com/PostView.nhn?blogId=kathar0s&logNo=10142875558&proxyReferer=https:%2F%2Fwww.google.com%2F)

### dependencyManagement (의존성 관리)

```jsx
<dependencyManagement>
	<dependencies>
		<dependency>
			<groupId>org.springframework.data</groupId>
			<artifactId>spring-data-jpa</artifactId>
			<version>${spring.data.version}</version>
		</dependency>
	</dependencies>
<dependencyManagement>
```

- 여기서 등록하면 하뒤 모듈에선 버전을 따로 명시하지 않아도 된다

의존성 중개

maven은 버전이 다른 두 라이브러가 의존관계에 있다면 더 가까운 의존관계를 선택한다

여러 라이브러리을 사용하는 경우 해당 라이브러리을 `transitive dependency(=하위 의존성, 하위 의존성은 전문적인 용어는 아님)` 라고 부르며 이들 사이에 충돌이 발생할 수도 있다. 이때 메이븐은 의존성을 분석하여 더 가까운 의존성을 선택한다

```jsx
e.g. 
A 라이브러리 -> B -> C -> D(버전1)
E 라이브러리 -> D(버전2)

이때 메이븐은 D에대해 버전2를 사용

만약 호환성 문제가 있는 경우 양쪽 모두를 만족시키는 버전(e.g. 1.5)를 찾아서 의존성관리를 통해 직접 지정해주면 된다
```

또한 아래 예시 중, module & parent 처럼 큰 프로젝트에선 하위 모듈에서 같은 라이브러리를 사용하는 경우가 있다. 이때 의존성 관리를 사용하면 모든 모듈에서 동일한 버전을 사용할 수 있다

### build (빌드 설정)

```jsx
	<build>
		<plugins>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-shade-plugin</artifactId>
				<version>3.2.4</version>
				<executions>
					<execution>
						<phase>package</phase>
						<goals>
							<goal>shade</goal>
						</goals>
						<configuration>
							<transformers>
								<transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
									<mainClass>hello.HelloWorld</mainClass>
								</transformer>
							</transformers>
						</configuration>
					</execution>
				</executions>
			</plugin>
		</plugins>
	</build>
```

프로젝트의 디렉토리 구조를 명시할 수 있다

- plugins

    빌드에서 사용되는 플러그인

- directory
- outputDirectory
- firstName
- repositories (저장소)

    사용 중인 의존성 중 maven 저장소가 아니라, 별도의 저장소가 필요한 경우 사용한다

    여러 개도 사용가능하다

    ```jsx
    	<repositories>
    		<repository>
    			<id>oracle</id>
    			<url>http://maven.jahia.org/maven2</url>
    		</repository>
    	</repositories>
    ```

    - 예제는 기본 저장소인 maven 중앙 저장소를 가리킨다
    - POM에 정의되있으며 상속받기 때문에 중앙 저장소는 명시할 필요가 없다
- pluginRepositories

    플러그인을 위해 필요한 저장소를 명시한다

- distributionManagement

    배포 위치를 명시한다

### 큰 프로젝트에서..

### module & parent

서브 모듈로 이뤄진 프로젝트를 만드려면 하위 모듈을 명시해야한다

- 부모 모듈

    ```jsx
    <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    	<modelVersion>4.0.0</modelVersion>
    	
    	<groupId>com.mycompany.app</groupId>
    	<artifactId>app</artifactId>
    	<version>1.0-SNAPSHOT</version>
    	<packaging>pom</packaging>
    	
    	<modules>
    		<module>my-app</module>
    		<module>my-webapp</module>
    	</modules>
    </project>
    ```

- 자식 모듈

    ```jsx
    	...
    	<dependencies>
    		<dependency>
    			<groupId>com.mycompany.app</groupId>
    			<artifactId>my-app</artifactId>
    			<version>1.0-SNAPSHOT</version>
    		</dependency>
    			...
    	</dependencies>
    	<parent>
    		<groupId>com.mycompany.app</groupId>
    		<artifactId>app</artifactId>
    		<version>1.0-SNAPSHOT</version>
    	</parent>
    ```

    출처: [https://maven.apache.org/guides/getting-started/index.html](https://maven.apache.org/guides/getting-started/index.html)

    - parent를 명시했을때 상속받는 상목은 다시 쓰지 않는다
        - groupId
        - version
        - description
        - url
        - inceptionYear
        - organization
        - licenses
        - developers
        - contributors
        - mailingLists
        - scm
        - issueManagement
        - ciManagement
        - properties
        - dependencyManagement
        - dependencies
        - repositories
        - pluginRepositories
        - build
        - plugin executions with matching ids
        - plugin configuration
        - etc.
        - reporting
        - profiles
    - 상속 안됨
        - artifactId
        - name
        - prerequisites

## Lifecycle (라이프 사이클) 단계

메이븐에서 제공하는 기본 빌드 순서를 라이프사이클이라고 부른다

라이프사이클의 종류

- Clean (클린)
- Build (빌드), default 라이프사이클이라고도 부른다
- Site (사이트)

각 라이프사이클은 여러 단계의 Phase(페이즈)로 이뤄져 있다. 페이즈의 순서는 바꿀 수 없다

각 단계는 명령어처럼 실행할 수 있다

```jsx
mvn clean
mvn test
```

- Clean 라이프사이클

    모든 산출물을 삭제한다

    - pre clean
    - clean
    - post clean
- Build  라이프사이클

    빌드 과정을 수행한다. 각 단계를 실행하면 앞단계를 포하하여 실행하며 이미 산출물이 있다면 생략가능하다

    - process-resources
    - compile
    - test
    - package
    - install
    - deploy
- Site 라이프사이클
    - pre site
    - site
    - post site
    - site deploy

페이즈는 논리적인 빌드 단계로 실제 수행은 플러그인을 통해 수행된다. 빌드 단계를 수정해야 할 경우, 특정 페이즈 단계에 명령을 추가하면 된다

플러그인이 수행하는 명령을 goal이라고 한다. 이때 명령이 사용하는 산출물을 target이라고 한다

정리

```jsx
라이프사이클 process-resources은 
resource단계를 거쳐 수행된다. 이 단계를 페이즈라고 부른다
이때, 사용하는 resources명령어를 goal이라고 부른다
```