# 출처: 프로그래머스 코딩 테스트 연습, https://programmers.co.kr/learn/challenges
from collections import defaultdict as dd

def solution(genres, plays):
    genre_playtime = dd(int)
    song_playtime = dd(list)
    
    for i, (genre, play) in enumerate(zip(genres, plays)):
        genre_playtime[genre] += play
        song_playtime[genre].append(tuple([i, play]))
    genre_priority = sorted(genre_playtime.items(), key=lambda x: x[1], reverse=True)

    best_album = []
    for genre, _ in genre_priority:
        if len(song_playtime[genre]) == 1:
            best_album.append(song_playtime[genre][0][0])
            continue
        p1_idx = 0
        p1 = -1
        p2_idx = 0
        p2 = -1
        for song, playtime in song_playtime[genre]:
            if playtime > p1:
                p2 = p1
                p2_idx = p1_idx
                p1 = playtime
                p1_idx = song
            elif playtime > p2:
                p2 = playtime
                p2_idx = song
        best_album.append(p1_idx)
        best_album.append(p2_idx)
    return best_album