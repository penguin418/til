// 출처: 프로그래머스 코딩 테스트 연습, https://programmers.co.kr/learn/challenges
#include <string>
#include <vector>
#include <algorithm>

using namespace std;

bool solution(vector<string> phone_book) {
    bool answer = true;
    sort(phone_book.begin(), phone_book.end());
    for (int i=0; i<phone_book.size(); i++){
        string prefix = phone_book[i];
        for(int j=i+1; j<phone_book.size(); j++){
            if(prefix == (phone_book[j].substr(0,prefix.length())))
                return false;
        }
    }
    return true;
}