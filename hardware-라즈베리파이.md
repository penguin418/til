# 라즈베리파이 iot프로그래밍

환경

- 2020년 11월
- 라즈베리파이4B

# 라즈베리파이

라즈베리 파이의 하드웨어에 대해 알아둔다

### 하드웨어

라즈베리파이4B의 하드웨어는 다음과 같다 (pintout 명령을 사용하였다)

```bash
,--------------------------------.
| oooooooooooooooooooo J8     +====
| 1ooooooooooooooooooo        | USB
|                             +====
|      Pi Model ???V1.1          |
|      +----+                 +====
| |D|  |SoC |                 | USB
| |S|  |    |                 +====
| |I|  +----+                    |
|                   |C|     +======
|                   |S|     |   Net
| pwr        |HDMI| |I||A|  +======
`-| |--------|    |----|V|-------'
```

### 포트구성

라즈베리파이는 40개의 단자가 있고 그 중 GPIO는 26개이다

```bash
   3V3  (1) (2)  5V     |  GPIO9 (21) (22) GPIO25
 GPIO2  (3) (4)  5V     | GPIO11 (23) (24) GPIO8 
 GPIO3  (5) (6)  GND    |    GND (25) (26) GPIO7 
 GPIO4  (7) (8)  GPIO14 |  GPIO0 (27) (28) GPIO1 
   GND  (9) (10) GPIO15 |  GPIO5 (29) (30) GND   
GPIO17 (11) (12) GPIO18 |  GPIO6 (31) (32) GPIO12
GPIO27 (13) (14) GND    | GPIO13 (33) (34) GND   
GPIO22 (15) (16) GPIO23 | GPIO19 (35) (36) GPIO16
   3V3 (17) (18) GPIO24 | GPIO26 (37) (38) GPIO20
GPIO10 (19) (20) GND    |    GND (39) (40) GPIO21
```

### 용어

프로그래밍에 필요한 회로 배경지식

- GPIO(General Purpose I/O)

    범용IO로 입력모드와 출력모드가 있다

- 3V3 → Logic Level

    디지털 회로에서는 0과 1만 존재하는데, 이를 로직레벨이라고 한다. 하지만, 실제 전류는 floating하게 흐르기 때문에 중간값이 존재할 수 있다. 그러므로 일정값을 기준으로 0과 1으로 구분하며, 라즈베리파이의 경우는 3.3V이다 (아두이노는 5V)

- GND(Ground)

    -극으로, 기준전압으로 삼아 다른 전압의 크기를 측정하기 위해 모든 전류는 최종적으로 이곳으로 보내야 한다

- ALT → PWM

    펄스 폭 변조 (#___#___#___ → ###_###_###_)

    디지털 회로에서 신호의 세기를 조절할 수 있다

    12, 18번, 13,19번 GPIO을 사용할 수 있다

## 주변 부품

주변 장치이다

### 저항

- 읽는 법
    1. 금색선이 오른쪽에 오도록 놓는다
    2. 왼쪽 두 숫자는 10의자리, 1의자리 (검갈빨주노초파보... 순)
    3. 셋째자리는 단위 10^(0123...) 금색은 10^-1

### 스위치


- PULL UP

    전류가 floating하게 흐르므로 스위치 등을 사용할 때는 Low에 가까운지 High에 가까운지 확실하게 확인할 수 있도록 PULL UP또는 PULL DOWN저항을 사용한다

    ```c
    3.3V+-\/\/\-----+-----+GPIO  off일때 1, on일때 0
          10kΩ      |
                     /
               -----+-+ +-| GND  off일때 0, on일때 1
    ```

    전원 → PULL UP → 스위치 → 일때, ON상태를 1로 만드는 것이 PULL UP이다

### LED


- 두 개의 다리

    긴 다리가 회로에서 왼쪽(+)이다

    긴다리를 -에 연결한다

### 초음파 센서

- 

# 쉘 명령어

### GPIO (Deprecated)

- gpio [옵션] [명령] [핀번호] [값]

    ⚠ 핀 번호이다

    ```bash
    # 상태 확인
    gpio readall

    # 모드 변경
    gpio **-g** mode [핀번호] [in|out]

    # 값 읽기 쓰기
    gpio -g read [핀번호]
    gpio -g write [핀번호] 값 
    ```

- 설치방법

    ```bash
    apt-get install wiringpi 
    ```

    ⚠ 라즈베리 4B에서는 기존버전을 사용할 수 없으므로 다음 오류가 출력될 것이다

    Oops - unable to determine board type... model: 17

    새로운 버전을 설치해서 해결할 수 있다

    ```bash
    wget https://github.com/WiringPi/WiringPi/archive/master.zip .
    unzip master.zip
    cd WiringPi-master/
    sudo ./build
    gpio -v
    ```

### RASPI-GPIO

- GPIO의 디버그용 프로그램
- raspi-gpio [get | set | funcs]

    ⚠ gpio 번호이다

    ```bash
    raspi-gpio get [gpio]
    raspi-gpio set [gpio] [optiosn]
    raspi-gpio funcs [gpio]

    # gpio입력
    3
    2, 7-10

    # set options
    ip: input모드
    op: output모드
    a0-a5: alt0 alt5
    dh: set 1
    dl: set 0
    ```

# 프로그래밍 라이브러리

### pigpio

raspberry pi gpio라는 뜻이다

- 컴파일시 링크를 위해 추가옵션이 필요하다

    **gcc EXAMPLE.c -pthread -lpigpio -lrt**

- 초기화

    ```c
    // 헤더파일
    #include <pigpio.h>

    int gpioinitialise() 
    // 스펠링 주의!
    // 초기화, 성공시 버전, 실패시 PI_INIT_FAILED(<0) 리턴

    void gpioTerminate()
    // 종료
    ```

- GPIO 조작

    ```c
    int gpioSetMode(unsigned gpio, unsigned mode)
    // 핀 모드 선택, 성공시 0, 실패시 PI_BAD_GPIO, PI_BAD_MODE 리턴
    // 핀번호 0-53, 모드: PI_INPUT, PI_OUTPUT, PIALT0..PIALT5

    int gpioGetMode(unsigned gpio)
    // 핀번호 모드 리턴
    // 성공시 번호 실패 시 PI_BAD_GPIO 리턴

    int gpioRead(unsigned gpio)
    // 핀번호 읽기
    // 성공시 0,1 실패 시 PI_BAD_GPIO 리턴

    int gpioWrite(unsigned gpio, unsigned level)
    // 값 쓰기
    // 성공시 0,1 실패 시  PI_BAD_GPIO, PI_BAD_MODE

    uint32_t gpioDelay(uint32_t micros)
    // 지정된 시간동안 멈춤 (마이크로 세컨드)
    ```

- PWM 조작

    ```c
    int int gpioHardwarePWM(unsigned gpio, unsigned PWMfreq, unsigned PWMduty)
    // gpio: 핀번호(12,13,18,19 GPIO 사용가능)
    // freq: 주파수 0(꺼짐) ~ 125 (초당 신호)
    // duty: 주기 0(꺼짐) ~ 1000000(100%)

    // freq/duty   500*1000 -> duty는 전체적인 비율을 결정한다
    //             ####     -> 밝기를 결정
    // 1           ########
    //             ##  ##  
    // 2           ########
    //             # # # #   -> freq는 초당 횟수를 결정한다
    // 4           ########
    //            0.5초에 한번
    // 아주 큰수   ~~~~~~~~  -> 너무 크면 항상 켜진것처럼 보인다
    ```

- 에러대처 방법

    만약 포트가 점유중이라면  실행중인 pigpiod를 재시작한다

    ```bash
    sudo killall pigpiod
    sudo pigpiod
    ```

- 설치방법

    ```bash
    wget https://github.com/joan2937/pigpio/archive/master.zip
    unzip master.zip
    cd pigpio-master
    make
    sudo make install
    ```
