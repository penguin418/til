# 출처: 프로그래머스 코딩 테스트 연습, https://programmers.co.kr/learn/challenges
from math import floor, ceil
def solution(w,h):
    if h < w:
        c = w
        w = h
        h = c
    cnt = 0
    floor_before = 0
    for i in range(w):
        height = (i+1) * h / w
        cnt += ceil(height) - floor_before
        floor_before = floor(height)
        if height == int(height):
            return w*h - cnt * w / (i+1)
    return w*h - cnt