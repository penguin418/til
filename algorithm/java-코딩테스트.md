# java코테

[코딩테스트 연습 - 이진 변환 반복하기](https://programmers.co.kr/learn/courses/30/lessons/70129)

```java
// 이진 변환 반복하기
// 시뮬레이션
import java.util.*;

class Solution {
    public static String transformation(String s){
        s = s.replace("0", ""); // 1-
        int c = s.length();
        s = "";
        while(c > 0){
            if(c == 1){
                s = '1' + s;
                break;
            }
            s = (c % 2) + s;
            c /= 2;
        }
        return s;
    }
    public int[] solution(String s) {
        int[] answers = {0,0};
        while(!s.equals("1")){
            int cnt = 0;
            for(int i=0; i<s.length(); i++) 
                cnt += s.charAt(i) == '0' ? 1:0;
            answers[0] += 1;
            answers[1] += cnt;
            s = transformation(s);        
        }
        return answers;
    }
}v
```

[코딩테스트 연습 - 쿼드압축 후 개수 세기](https://programmers.co.kr/learn/courses/30/lessons/68936)

```java
// 쿼드압축 후 개수 세기
// 개수만 세면 되니까 상태저장은 필요없음
// arr도 전역변수에 저장할걸 그랬다
class Solution {
    static int sum0 = 0;
    static int sum1 = 0;
    public static int dfs(int[][] arr, int x, int y, int w){
        int ssum = 0;
        for(int i=x; i<x+w; i++) for(int j=y;j<y+w;j++)
            ssum += arr[i][j];
        if (ssum == w*w){
            return sum1++;
        }else if (ssum == 0){
            return sum0++;
        }
        int ww = w/2;
        if (ww == 0)
            return arr[0][0] > 0 ? sum1++ : sum0++;
        dfs(arr, x,   y,   ww);
        dfs(arr, x,   y+ww,ww);
        dfs(arr, x+ww,y,   ww);
        dfs(arr, x+ww,y+ww,ww);
        return 0;
    }
    public int[] solution(int[][] arr) {
        dfs(arr,0,0,arr.length);
        System.out.println(sum0 +" "+ sum1);
        return new int[] {sum0, sum1};
    }
}
```
