// # 출처: 프로그래머스 코딩 테스트 연습, https://programmers.co.kr/learn/challenges
#include <string>
#include <vector>
#include <map>
#include <iostream>
using namespace std;

int solution(vector<vector<string>> clothes) {
    int answer = 0;
    
    // cloth count map 
    map<string, int> ccm;
    
    // cloth type iter
    vector<vector<string>>::iterator ct_iter = clothes.begin();
    for (; ct_iter!= clothes.end(); ct_iter++){
        cout<<(*ct_iter)[0]<<" "<<(*ct_iter)[1];
        if(ccm.find((*ct_iter)[1]) == ccm.end()){
            ccm.insert(pair<string, int>((*ct_iter)[1], 1));
        }else{
            ccm.find((*ct_iter)[1])->second++;
        }
    }
    
    int combi = 1;
    map<string, int>::iterator ccm_iter = ccm.begin();
    for(;ccm_iter != ccm.end(); ccm_iter++)
        combi *= (ccm_iter->second + 1);
    return combi-1;
}