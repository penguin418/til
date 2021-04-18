// 출처: 프로그래머스 코딩 테스트 연습, https://programmers.co.kr/learn/challenges
import java.util.*;

class Solution {
    public int solution(int n) {
        int answer = 0;
        ArrayList<Integer> n3 = new ArrayList<Integer>();
        
        while(n>0){
            int remain = n % 3;
            n3.add(remain);
            n = n / 3;
        }
        
        int p = (int)Math.pow(3,n3.size()-1);
        for(int i=0;p>0; p /= 3, i++){
            answer += p * n3.get(i);
           
        }
        
        return answer;
    }
}