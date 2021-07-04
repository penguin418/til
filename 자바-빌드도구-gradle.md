# Gradle

스크립트 기반의 빌드 도구이다

- Groovy 문법을 사용한다 + 코틀린 문법도 지원한다!

    덕분에 아주 쉽게 값을 확인할 수 있다

    ```python
    // build.gradle 파일 내부
    println 변수_혹은_설정값
    ```

    출처: [https://docs.gradle.org/current/userguide/more_about_tasks.html#sec:task_outcomes](https://docs.gradle.org/current/userguide/more_about_tasks.html#sec:task_outcomes)

    JVM언어 중 하나로, 자바와 호환되어 자바 코드를 사용해도 된다

- JDK 1.8 이상을 지원한다
- Maven보다 빠르다

    출처: [https://gradle.org/maven-vs-gradle/](https://gradle.org/maven-vs-gradle/)`

구성

- init.gradle

    초기화 스크립트

- setting.gradle

    빌드할 프로젝트를 정의 

- build.gralde

    빌드 수행과 관련된 설정 (필수)

- gralde.properties

    그레이들에서 참조하는 설정값

    ```xml
    apply plugin: 'java'
    apply plugin: 'eclipse'
    apply plugin: 'application'

    mainClassName = 'hello.HelloWorld'

    repositories { 
        mavenCentral() 
    }

    jar {
        archiveBaseName = 'gs-gradle'
        archiveVersion =  '0.1.0'
    }

    sourceCompatibility = 1.8
    targetCompatibility = 1.8

    dependencies {
        testImplementation "junit:junit:4.12"
    }
    ```

## 구조

Gradle은 하나 이상의 Projects로 구성되있다

각 Projects는 여러 task로 구성되어있다. 

- Task(태스크)

    Gradle프로젝트의 작업 단위

    1. 태스크 구성

        태스크는 명령어의 집합이다

        ```groovy
        task 태스크이름 {
        	// 	... 할일
        	doFirst { // 최초에 수행하는 클로저
        		def variable = 1 // 변수 선언
        	}

        	doLast { // 최후에 수행하는 클로저
        		
        	}
        }
        // ..............
        gradle 태스크이름
        ```

    2. 태스크 등록

        register를 통해 등록한 태스크는 -q옵션으로 실행할 수 있다

        ```groovy
        tasks.register('태스크이름') {
        	// 내용
        }
        // ........................
        > gradle -q 태스크 이름

        ```

    3. 태스크 호출

        execute() 로 다른 태스크를 호출할 수 있다

        ```groovy
        tasks.다른태스크이름.execute()
        ```

    4. 종속성

        종속성을 정의하면 선행 태스크를 먼저 실행한다

        다음 두가지 방법이 있다

        ```groovy
        task 태스크(dependsOn: '먼저실행할태스크'){
        }
        ```

        ```groovy
        task 태스크{
        	dependsOn: '먼저실행할태스크'
        }
        ```

    5.  

# init.gradle의 구성

초기화 스크립트로 .gradle/init/init.gradle 에 위치하며, 그레이들 인스턴스를 생성한다.

특정 프로젝트가 아니라, 그레이들 인스턴스 자체에 관한 설정이다.

# settings.gradle의 구성

빌드할 프로젝트에 대한 설정을 담은 스크립트로, 단일 프로젝트를 빌드할 때는 필요없지만, 멀티  프로젝트를 빌드할 때는 필수적이다

주의: settings.gradle은 멀티 프로젝트를 정의하지만, 프로젝트간의 종속성은 정의하지 않는다. 종속성은 build.gradle에서 수행하며, 아무런 설정이 없다면 알파벳 순서대로 여러 프로젝트를 한번의 명령으로 빌드할 뿐이다.

### project

프로젝트는 빌드의 단위이다. 프로젝트는 project("<프로젝트이름>")으로 접근할 수 있고, 기본 프로젝트의 이름은 rootProject이다

- println으로 루트 프로젝트 이름을 출력해보자

    ```python
    println rootProject.name
    ```

### project.name

rootProject를 비롯한 project의 이름은 build.gradle이 존재하는 디렉토리의 이름이다. 하지만 지정할 수도 있다

- 예를 들어, settings.gradle이 없는 경우, gradle build 수행 시 다음과 같은 jar 파일이 생성된다

    ```python
    <루트 디렉토리이름>.<버전>.jar
    ```

- settings.gradle에 다음과 같이 rootProject.name을 변경하면

    ```python
    rootProject.name="치킨"
    ```

    아래와 같은 jar파일이 생성딘다

    ```python
    치킨.<버전>.jar
    ```

### project.projectDir

프로젝트는 위치를 지정할 수 있다

어떤 프로젝트가 다음 디렉터리 구조로 되있다고 생각해보자

```python
치킨
ㄴ 양념치킨/
	ㄴ src/
	ㄴ build.gradle
ㄴ src/
ㄴ build.gradle
ㄴ settings.gradle

```

- rootProject의 경우 기본 위치는 build.gralde의 위치이며, 해당 디렉토리의 src/main/java 를 참조하여 프로젝트를 빌드한다. 빌드 결과 또한 settings.gradle과 동일한 디렉토리에 생성된다

    ```python
    치킨/
    ㄴ ...
    ㄴ build/
    	ㄴ ..
    	ㄴ libs/
    		ㄴ 치킨.<버전>.jar
    ㄴ src/
    ㄴ build.gradle
    ㄴ settings.gradle
    ```

- rootProject에 다음과 같이 projectDir을 변경하면,

    ```python
    rootProject.buildDir = new File(settingsDir, '양념치킨')
    ```

    - 추가정보: settingsDir은 gradle에서 제공하는 변수로

        settingsDir은 settings.gradle의 경로

        buildDir은 build.gradle의 경로

        이다.

        ""를 사용하면 문자열 내부에서도 사용할 수 있다

        ```python
        rootProject.buildDir = file("$settingsDir/양념치킨")
        ```

        file은 new File과 동일한 역할 수행

    다음과 같이 '일반치킨' 디렉토리의 프로젝트만 빌드되버린다

    ```python
    치킨/
    ㄴ 양념치킨/
    	ㄴ build/
    		ㄴ ..
    		ㄴ libs/
    			ㄴ 양념치킨.<버전>.jar
    	ㄴ src/
    	ㄴ build.gradle
    ㄴ ...
    ```

### include

include는 서브프로젝트를 지칭하여, 멀티 프로젝트를 빌드할 수 있게 해준다.

- include가 있다고 무조건 멀티 프로젝트인 것은 아니다.

    예를 들면 안드로이드 앱의 경우 보통 settings.gradle은 다음과 같다

    ```python
    include ':app'
    ```

    안드로이드의 디렉토리 구성은 [https://developer.android.com/studio/build?hl=ko](https://developer.android.com/studio/build?hl=ko)

    ```python
    MyApp
    ㄴapp/
    	ㄴ build.gradle
    	ㄴ src/
    	ㄴ build.gradle
    ㄴ build.gradle
    ㄴ settings.gradle
    ```

    과 같은데, 최상위 build.gradle은 빌드 구성을 정의하고, 모듈 build.gradle은 프로젝트 수준의 속성을 정의한다. 모듈은 구글의 안드로이드 문서에서 사용하는 용어로, gradle에선 프로젝트로 지칭하니까 아래에선 프로젝트라는 용어를 사용한다

- 기본 프로젝트인 치킨과 다른 프로젝트인 양념치킨을 모두 빌드하고 싶다면 어떻게 해야할까?

    양념치킨 디렉토리를 include하면 된다

    ```python
    include '양념치킨'
    // rootProject는 지정하지 않아도 자동으로 포함된다!
    ```

    결과는 다음과 같다

    ```python
    치킨/
    ㄴ 양념치킨/
    	ㄴ build/
    		ㄴ libs/
    			ㄴ 양념치킨.<버전>.jar
    	ㄴ src/
    	ㄴ build.gradle
    ㄴ build/
    	ㄴ ..
    	ㄴ libs/
    		ㄴ 치킨.<버전>.jar
    ㄴ src/
    ㄴ build.gradle
    ㄴ settings.gradle
    ```

- 양념치킨의 치킨이 반복되는 것 같아 줄이고 싶다면 어떻게 해야할까? (실무에선 api-server, web-server를 줄여서 api, web으로 줄이고 싶다고 가정해보자... 사실 실무에선 처음부터 api, web으로 짓는다 카더라)

    ```python
    include '양념'
    project(':양념').projectDir = new File(settingsDir, '양념치킨')
    ```

    결과는 다음과 같다. 

    ```python
    치킨
    ㄴ 양념치킨/ [양념]
    	ㄴ build/
    		ㄴ ..
    		ㄴ libs/
    			ㄴ 양념.<버전>.jar
    	ㄴ src/
    	ㄴ build.gradle
    ㄴ build/
    	ㄴ ..
    	ㄴ libs/
    		ㄴ 치킨.<버전>.jar
    ㄴ src/
    ㄴ build.gradle
    ㄴ settings.gradle
    ```

    달라진 부분은 양념.<버전>.jar과 intellij의 프로젝트뷰에서 대괄호 안에 프로젝트 이름이 보인다는 것

다 만들고 나면, 루트 프로젝트에 src 폴더만 있지는 않을 것이다. lombok.config등의 파일이 존재할 수도 있고, docker파일이나, .env파일과 섞여 있는 모습이 마음에 들지 않을 수도 있다

- rootProject은 '공통'에 넣어보자

    먼저 루트프로젝트를 '공통'디렉터리를 만들어 옮긴다

    ```python
    치킨/
    ㄴ공통/
    	ㄴ src/
    	ㄴ build.gradle
    ㄴ 양념치킨/ [양념]
    	ㄴ src/
    	ㄴ build.gradle
    ㄴ settings.gradle
    ```

    ```python
    rootProject.projectDir = new File(settingsDir, '공통')
    include '양념'
    project(':양념').projectDir = new File(settingsDir, '양념치킨')
    ```

    결과는 다음과 같다

    ```python
    치킨/
    공통/
    	ㄴ build/
    		ㄴ ..
    		ㄴ libs/
    			ㄴ 치킨.<버전>.jar
    	ㄴ src/
    	ㄴ build.gradle
    ㄴ 양념치킨/ [양념]
    	ㄴ build/
    		ㄴ ..
    		ㄴ libs/
    			ㄴ 양념.<버전>.jar
    	ㄴ src/
    	ㄴ build.gradle
    ㄴ settings.gradle
    ```

    rootProject의 이름도 바꾸고 싶다면, 위에서 설정한 것처럼 rootProject.name을 바꾸자

# build.gralde의 구성

프로젝트의 빌드 과정에 사용되는 task를 정의하는 스크립트로, 필수적으로 필요하다

build.gradle의 작성에는 순서가 존재한다

좀더 빠르고 간결하다

의존성 scope:

~~(1) compile: 컴파일시 포함~~

~~(2) runtime: 실행 환경에서만 포함~~

(3) testCompile: 테스트 컴파일시만 포함

(4) testRuntime:  테스트 실행시 포함

(5) implementation:  사용하는 모듈까지만 빌드

(6) compileOnly: 컴파일시에만 사용

(7) runtimeOnly: 런타임시에만 사용