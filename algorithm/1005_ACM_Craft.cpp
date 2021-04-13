#include<cstdio>
#include<queue>
#include<map>
using namespace std;

multimap<int, int> orders;
int cost[1001];
int totalcost[1001];
void build(int k){
	if (totalcost[k] < 0){
		if(orders.count(k) == 0){
			totalcost[k] = cost[k];
			return;
		}
		int maxcost = 0;
		auto pre = orders.find(k);
		for(; pre != orders.end(); pre++){
			if(pre->first != k) break;
			build(pre->second);
			if(maxcost < totalcost[pre->second]) // 이전 레벨까지 짓기 위한
				maxcost = totalcost[pre->second];// 가장 비싼 값 선택
		}
		totalcost[k] = maxcost + cost[k]; // 총 비용
	}
}

void solution(int n, int k){
	int w;				 // object
	orders.clear();
	for(int i=0; i<1001;i++)totalcost[i]=-1; //init
	
	multimap<int,int> o; // orders
	for (int i=0; i<n; i++){
		scanf("%d", cost+i+1);
	}
	for (int i=0; i<k; i++){
		int a, b;
		scanf("%d %d", &b, &a);
		orders.insert(make_pair(a,b));
	}
	scanf("%d", &w);
	build(w);
	printf("%d\n", totalcost[w]);
}
int main(){
	int t;
	scanf("%d", &t);
	for (int i=0; i<t; i++){
		int n, k;
		scanf("%d %d", &n, &k);
		solution(n, k);	
	}
}