# 시작

```java
from bs4 import BeautifulSoup as bs
soup = bs(html_doc)
```

## 함께쓰면 좋은 라이브러리

1. requests

온라인 작업을 위한 기본적인 라이브러리

```java
response = requests.get(some_site_url)
soup = bs(response.content, 'html.parser')
print(soup.prettify())
```

2. lxml

파싱 속도를 올릴 수 있다

```python
soup = bs(html_doc, 'lxml')
```

## 요소 추출

- 첫번째 요소 추출하기

    ```python
    title  | 타이틀
    meta   | 메타
    a      | 링크
    ...
    ```

    1. 태그이름로 추출하기

        ```python
        soup.title
        # <title>Hello World</title>

        soup.title.name
        # u'title'

        soup.title.string
        # u'Hello World'
        ```

    2. find로 추출하기

    ```python
    soup.find('title')
    # <title ...

    soup.find('title', class_='cls')
    # <title class='cls' ...

    soup.find('title', id='ID')
    # <title id='ID' ...

    soup.find('title', text='TEXT')
    # <title ... > TEXT </title>
    ```

- 여러개 추출하기

    ```python
    soup.find_all('a')
    # [ .... ]
    ```

- 하위 요소 추출하기
    1. contents 사용하기

        자식 요소를 리스트로 얻을 수 있다

        ```python
        first_div = soup.div
        first_div.contents
        # [ <a href=...>
        # <some_next_tag> ]
        ```

    2. children 사용하기

        iterator를 얻을 수 있다 

        ```python
        first_div = soup.div
        for c in first_div.children:
        	...
        ```

- 상위 요소 추출하기

    parent 

# 속성 접근

- [ ] 로 접근하기

    ```python
    soup.find('a')['href']
    # /img/1.jpg
    ```

# 값 접근

- 탐험할 수 있는 값으로 접근

    ```python
    elem.string
    # <navigatablestring ..
    ```

- 값 자체로 접근

    ```python
    elem.getText()
    # text
    ```

#