# 출처: 프로그래머스 코딩 테스트 연습, https://programmers.co.kr/learn/challenges
def dfs(sales, links, dp, cur):
    if len(links[cur]) == 0:
        dp[cur][0] = 0
        dp[cur][1] = sales[cur-1]
    else:
        return min(dp[cur][0], dp[cur][1])
def solution(sales, links):
    answer = 0
    dp = [[0,0] for _ in range(len(sales)+1)]
    link_map = {}
    for i in range(len(sales)+1): link_map[i]=[]
    for link in links: 
        link_map[link[0]].append(link[1])
    dfs(sales, link_map, dp, 1)
    return answer