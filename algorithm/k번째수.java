// 출처: 프로그래머스 코딩 테스트 연습, https://programmers.co.kr/learn/challenges
import java.util.Arrays;
class Solution {
    public int[] solution(int[] array, int[][] commands) {       
        int[] answer = new int[commands.length];
        int i=0;
        for (int[] com : commands){
            int from = com[0];
            int to = com[1];
            int pick = com[2];
            
            int[] picks = Arrays.copyOfRange(array, from-1, to);
            Arrays.sort(picks);
            answer[i++] = (picks[pick-1]);
        }
        return answer;
    }
}