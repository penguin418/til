// 출처: 프로그래머스 코딩 테스트 연습, https://programmers.co.kr/learn/challenges
#include <string>
#include <vector>
#include<algorithm>

using namespace std;


string solution(vector<string> participant, vector<string> completion) {
    string answer = "";
    sort(participant.begin(), participant.end());
    sort(completion.begin(), completion.end());
    for(int i=0; i<completion.size(); i++)
        if(participant[i] != completion[i])
            return participant[i];
    return participant[participant.size()-1];
}