# git

```bash
## 설치 버전 확인
$ git --version

## 설정
$ git config [--global] user.name 'penguin'
$ git config [--global] user.email 'penguin@google.com'

## 설정 확인
$ git config --list

## 디렉토리 git repo 초기화
$ git init
```

[git 영역](https://www.notion.so/91e6d3d96b71451db7a04081bf23338f)

프로젝트 운영 -  스테이징

```bash
## 스테이징
$ git add <file_name>
```

프로젝트 운영 - 스테이징 파일 관리

```bash
## 스테이징된 파일(index라고 부름) 확인
$ git status
On branch master
Changes to be committed:
  (use "git reset HEAD <file>..." to unstage)
        new file:   README.txt

Changes not staged for commit:
  (use "git add <file>..." to update what will be committed)
  (use "git checkout -- <file>..." to discard changes in working directory)
        modified:   crawling.py

Untracked files:
  (use "git add <file>..." to include in what will be committed)
        rat.py

## 스테이징 목록에서 삭제
$ git reset README.txt
```

프로젝트 운영 - 커밋

```bash
## 커밋
$ git commit -m "message" -m "detail message"

## 커밋된 기록 확인 (마지막 커밋은 Head 라고 부름)
$ git log
commit 411h8tj48k4d5fhd1sgagatjr...kfds4j4kg51ef
Auther: penguin <penguin@google.com>
Date: Fri Oct 09 13:58:11 2020 + 0900

	message

	detail message
## 대표적인 옵션
## -p, --patch: 각 커밋의 수정결과를 보여주는 diff와 동일한 역할
## -2         : 상위 2개만
## --stat     : 어떤 파일이 수정되고 변경되었는지, 파일내 라인 추가 삭제 확인
## --pretty=oneline : 각 커밋을 한줄로 출력
## --graph    : 커밋간 연결관계를 아스키 그래프로 출력
## -S keyword ; 코드에서 추가/제거 내역중 keyword에 해당하는 내용 검색

## 커밋된 파일 중 변경된 사항 확인
$ git diff 
diff --git a/argicle.js b/article.js
index 46efs4fed5......41 100644
--- a.article.js
+++ b/article.js
@@ -1 + 1 @@
-#let
+#trysome
```

브랜치

```bash
git branch <새로운 브랜치 이름>

git branch -D <삭제할 브랜치 이름>

git branch --merged: 병합된 브랜치 확인
```

머지

특이한 머지: fast-forward(부모 branch에서 자식 branch를 merge)

```bash
git merge
```

충돌 해결: 충돌된 파일 수정 후, 수동으로 commit 

원격 저장소

```bash
## 풀: 가져오고 병합
$ git pull  [<저장소> <브랜치>]

## 패치: 가져오가만 하고 병합하지 않음
$ git fetch

## 푸시: 원격 저장소에 푸시
$ git push origin master

$ git remote add origin <주소>    ## 보통 원격 저장소 이름으로 origin을 씀
$ git remote add <원하는이름> <주소>  ## 하지만 다른 이름도 사용 가능함

## 이름, 주소 확인
$ git remote -v
origin [https://gitlab.com/dfadfadf.git](https://gitlab.com/dfadfadf.git) (fetch)
origin [https://gitlab.com/dfadfadf.git](https://gitlab.com/dfadfadf.git) (push)

## 원격저장소 브랜치 지정하기, clone하지 않는 이상 처음 1번은 해줘야 함
$ git push —set-upstream origin master
## 혹은
$ git branch —set-upstream-to=<저장소>/<브랜치> <로컬브랜치>

## 연결 시킨 이후 라면,
$ git push <로컬 브랜치> ## 로 연결된 브랜치에 푸시 가능
```

rebase 기능

```bash
## 이전의 스냅샷으로 이동 후, commit 수정하는 방법, 물론 로그는 남는다
$ git reset —soft HEAD~n
$ git reset —hard HEAD~n : 워킹디렉토리 사라짐

## 리베이스: 브랜치가 너무 많아 히스토리 정리가 필요한 상황에서 사용
$ git rebase

## 공통된 부모까지의 commit을 모두 가져와서 브랜치 옆에 이어붙임
## 이전 상황 1 → 2 → (3 → 5 → 6 → master) or (4 → 7 → 9 → feature/message)
git checkout master
git rebase feature/message
## 이후 상황 1 → 2 →  (4 → 7 → 9 → feature/message) -> (3' → 5' → 6' → master)

## rebase 충돌 해결
## 파일 열어서 수정후, commit이 아니라, rebase continue를 한다는 점이 병합과 다르다
$ git add <수정한 파일>
$ git rebase —continue
```
