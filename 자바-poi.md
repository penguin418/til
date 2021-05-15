# 자바 poi 이론

### 개요

아파치 소프트웨어 재단이 만든 라이브러리고, 마이크로소프트 오피스 파일 포맷을 읽고 쓰는 기능을 제공한다

이름의 유래는 Poor Obfuscation Implementation로, '질이 나쁘고 읽기어려운 구현'이라는 뜻에서 왔지만 현재는 별 의미없다 [출처]([https://okky.kr/article/212818](https://okky.kr/article/212818))

엑셀, 워드, 파워포인트, OLE까지 지원한다

이 문서에서 다루는 버전은 5.0.0이다

* 버전에 따라 메서드나 타입이 많이 다른 것 같다

### 원리

마이크로소프트에서 만든 word는 x

# 시작하기

그레이들 의존성을 추가한다

```
// poi 프로젝트이다
implementation 'org.apache.poi:poi:5.0.0'
// open office xml 
implementation 'org.apache.poi:poi-ooxml:5.0.0'
```

# 엑셀

## 배경지식

### Workbook

- poi에선 excel을 Workbook으로 부른다
- poi의 형식
    - HSSFWorkbook : 1997버전부터 2003버전까지 사용된 xls 파일 지원
    - XFSSFWorkbook :  xlsx 파일 지원

    두 타입은 동일한 인터페이스인 Workbook을 상속받으므로 필요한 경우 다음의 메서드를 사용가능하다 (poi-ooxml을 추가적으로 임포트)

    ```java
    Workbook Workbook = WorkbookFactory.create(fIn);
    ```

    - Sheet와 Cell도 인터페이스이기 때문에 공통적으로 사용가능하다

### Sheet

- Workbook은 다수의 Sheet를 가지고 있다

### Cell

- 각 Sheet는 다수의 Cell로 이뤄진다

# XSSFWorkbook 클래스

- XSSFWorkbook()

    빈 Workbook을 생성한다. 

    주로 파일 생성을 위해 생성된다

    ```java
    XSSFWorkbook workbook = new XSSFWorkbook();

    FileOutputStream fOut = new FileOutputStream(new File("hello.xlsx"));
    workbook.write(fOut);
    fOut.close();
    ```

    데이터가 많은 경우 저장전용의 SXSSFWorkbook 을 사용하면 메모리 사용량을 크게 줄일 수 있다. row단위로 끊어서 flush할 수 있다

- XSSFWorkbook(InputStream is)

    스트림을 통해 XSSFWorkbook을 읽는다.  (현재는 잘 쓰이지 않는다)

    ```java
    File file = new File("hello.xlsx");
    try (
      FileInputStream fIn = new FileInputStream(file);
      XSSFWorkbook workbook = new XSSFWorkbook(fIn);
    ) {
      XSSFSheet sheet = workbook.getSheetAt(0);
        // ...
    } catch (IOException e) {
      System.out.println("no hello.xlsx");
    }
    ```

    내부적으로는 스트림을 모두 읽고 이를 이용행 OPC Package 객체를 생성하므로 메모리 사용량이 크다. 대신 OPC Package객체로 모두 읽었으므로 FileInputStream객체는 닫아도 된다

    ```java
    ...
    fIn.close();
    FileOutputStream fOut = new FileOutputStream(file, true);
    try {
      workbook.write(fOut);
    } catch (IOException e) {
      e.printStackTrace();
    }
    ```

    공식 도큐먼트는 다음과 같은 사용법을 권장한다

    ```java
    OPCPackage pkg = OPCPackage.open(path);
    XSSFWorkbook wb = new XSSFWorkbook(pkg);
    // work with the wb object
    // ......
    pkg.close(); // gracefully closes the underlying zip file
    ```

- XSSFWorkbook(File file)

    파일을 통해 XSSFWorkbook을 읽는다 (자주 사용된다)

    스트림으로 읽을 때 보다 더 적은 메모리를 사용한다

    이때는 스트림과 달리 해당 file이 없는 경우 IOException이 아니라, XSSFWorkbook이 던지는 InvalidOperationException가 발생하며, 포맷 불일치 시 발생하는 InvalidFormatException 가 추가로 존재한다

    ```java
    File file = new File("hello.xlsx");
    try (
    // FileInputStream fIn = new FileInputStream(file);
      XSSFWorkbook workbook = new XSSFWorkbook(file);
    ) {
      XSSFSheet sheet = workbook.getSheetAt(0);
    	// ...
    } catch (InvalidFormatException e) {
      System.out.println("invalid format");
    }catch (InvalidOperationException e) {
      if(e.getCause() instanceof FileNotFoundException)
        System.out.println("no file");
    }
    ```

주의: XSSFWorkbook은 읽은 파일을 자동으로 닫아주지 않으므로 close메서드를 사용하여 꼭 닫아주어야 한다. 

- write(OutputStream stream)

    저장한다

sheet관련 메서드 

- createSheet()

    createSheet(String sheetname)

    새로운 시트를 생성한다
    sheetname을 지정할 수 있다
    XSSFSheet를 반환한다

    ```java
    XSSFSheet sheet = workbook.createSheet("no name");
    // ...
    ```

- getSheetAt(int index)

    인덱스 위치의 sheet를 불러온다 (0부터 시작)

    ```java
    int sheetCnt = workbook.getNumberOfSheets();
    XSSFSheet lastSheet = workbook.getSheetAt(sheetCnt - 1);
    ```

### XSSFSheet 클래스

- XSSFSheet()

    빈 XSSFSheet를 생성한다

- createRow(int rownum)

    새로운 XSSFRow 를 생성한다

    이때, rownum은 필수적이다. 같은 인덱스를 사용할 경우, 내부적으로 지우기가 수행된다

    ```java
    Map< String, Object[] > data = new TreeMap< String, Object[] >();
    data.put( "1", new Object[] { "날짜", LocalDate.of(2011,4,8)});
    data.put( "2", new Object[] { "텍스트", "피어레스" });
    data.put( "3", new Object[] { "숫자", 10 });

    int rowid = 0;
    for (String key : data.keySet();) {
      XSSFRow row = sheet.createRow(rowid++);
      Object [] objectArr = data.get(key);

      int cellid = 0;
      for (Object obj : objectArr) {
        Cell cell = row.createCell(cellid++);
        if (obj instanceof String) {
          cell.setCellValue((String) obj);
        }  else if (obj instanceof Integer) {
          cell.setCellValue((Integer) obj);
        } else if (obj instanceof LocalDate) {
          cell.setCellValue((LocalDate) obj);
    			CreationHelper createHelper = workbook.getCreationHelper();
    			XSSFCellStyle dateStyle = new XSSFCellStyle(workbook.getStylesSource());
    			dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy.M.d"));
        }
      }
    }
    ```

- iterator()

    rowIterator를 얻을 수 있으며 이를 통해 각 row을 탐색할 수 있다

    그냥 순회하는 부분만 구현하면 다음과 같다

    ```java
    for (Sheet sheet : wb ) {
        for (Row row : sheet) {
            for (Cell cell : row) {
                // Do something here
            }
        }
    }
    ```

    이 경우, 빈 셀 등과 관련한 문제가 발생할 수 있으니 주의해야 한다

    - getFirstRowNum(), getLastRowNum() 를 사용하여 최소, 최대를 구하고
    - 각 row마다 null 여부를 판단해야 한다 (null: 현재 row가 모두 비어있음)

    그래서 iterator를 사용하여 구현하면, 좀더 편하다

    ```java
    XSSFSheet sheet = workbook.getSheetAt(0);
    Iterator<Row>  rowIterator = sheet.iterator();

    while (rowIterator.hasNext()) {
      XSSFRow row = (XSSFRow) rowIterator.next();
      Iterator<Cell>  cellIterator = row.cellIterator();

      while ( cellIterator.hasNext()) {
        Cell cell = cellIterator.next();

        switch (cell.getCellType()) {
        case NUMERIC:
          String value = "";
          if(DateUtil.isCellDateFormatted(cell)){
            Date date = cell.getDateCellValue();
            value = new SimpleDateFormat("yy.d.M").format(date);
          }else{
            value = String.valueOf(cell.getNumericCellValue());
          }
          System.out.print(value + " \t\t ");
          break;

        case STRING:
          System.out.print(
            cell.getStringCellValue() + " \t\t ");
          break;
        }
      }
      System.out.println();
    }
    ```

속성 변경과 관련된 메서드

- addMergedRegion(CellRangeAddress region)

    영역을 머지한다

    ```java
    sheet.addMergedRegion(new CellRangeAddress(
    	0, // 행의 시작 (0부터 시작한다)
    	0, // 행의 끝
    	3, // 열의 시작
    	3  // 열의 끝
    ))
    ```

- autoSizeColumn(int column)

    영역에 autosize를 지정한다

    내용 수정 후 매번 실행해야 적용된다

### XSSFRow 클래스

- createCell(int columnIndex)

    새로운 셀을 생성한다

    XSSFCell을 반환한다

- setHeight(short height)

    높이를 지정한다

### XSSFCell 클래스

- setCellValue(double value)

    setCellValue(java.lang.String str)

    setCellValue(boolean value)

    setCellValue(java.util.Calendar value)

    setHyperlink(Hyperlink hyperlink)

    셀의 값을 지정한다

- setCellType(int cellType)

    셀의 타입을 지정한다

    CellType.NUMERIC : 숫자

    - localdate 타입은 존재하지 않는다. 숫자 타입으로 관리된다

        ```java
        switch (cell.getCellType()) {
        case NUMERIC:
          String value = "";
          if(DateUtil.isCellDateFormatted(cell)){
            Date date = cell.getDateCellValue();
            value = new SimpleDateFormat("yy.d.M").format(date);
          }else{
            value = String.valueOf(cell.getNumericCellValue());
          }
          System.out.print(value + " \t\t ");
          break;
        // ...
        ```

    CellType.STRING : 문자

    CellType.BOOLEAN : true, false

    CellType.FORMULA : 수식

    지정하지 않아도 기본적으로는 타입에 맞게 설정된다

- setCellFormula(String formula)

    formula를 생성한다

    실제 계산은 excel에 의해 수행된다

- setCellStyle(CellStyle style)

    셀의 스타일을 지정한다

row에서 생성할 수 있다

- Row.createCell()

    cell을 생성한다

    ```java
    XSSFCell cell = row.createCell(1);
    ```

### CellStyle

- setAlignment(HorizontalAlignment align)

    정렬방법을 지정한다

    HorizontalAlignment.CENTER_SELECTION: 

    HorizontalAlignment.FILL: width를 채움

    HorizontalAlignment.LEFT, HorizontalAlignment.CENTER, HorizontalAlignment.RIGHT: 왼쪽,  가운데, 중간에 정렬

    HorizontalAlignment.JUSTIFY : 콘텐츠 width에 맞춤

- setVerticalAlignment(VerticalAlignment align)

    세로 정렬방법을 정한다

    VerticalAlignment.BOTTOM, VerticalAlignment.CENTER, VerticalAlignment.TOP: 아래, 중간, 위 정렬

    VerticalAlignment.JUSTIFY: 세로로 정렬 및  콘텐츠 height에 맞춤

- setBorderBottom(BorderStyle border)

    setBorderLeft(BorderStyle border)

    setBorderRight(BorderStyle border)

    setBorderTop(BorderStyle border)

    테두리를 지정한다

    BorderStyle.NONE

    BorderStyle.THIN

    BorderStyle.MEDIUM

    BorderStyle.DASHED

    BorderStyle.THICK

    BorderStyle.DOUBLE

    ... 아주 많다

생성자도 있지만, 보통은 Workbook의 생성자를 사용한다

- Workbook.createCellStyle()

    새로운 Cell 스타일을 생성한다

    ```java
    XSSFCellStyle style = workbook.createCellStyle();
    ```

# 실제 사용에 대해

실제로 사용할 때는 스타일처럼 고정적으로 처리되는 부분을 직접 구현하지 않고, xlsx 템플릿파일을 만들어 두고, 그 템플릿 파일을 채워넣는 방식으로 구현한다고 한다.

그것을 감안하여 자세한 스타일링에 관련한 부분은 필요한 경우 더 공부하면 될 것 같다

### 참고:

- [https://poi.apache.org/components/spreadsheet/quick-guide.html#FileInputStream](https://poi.apache.org/components/spreadsheet/quick-guide.html#FileInputStream)
- [https://www.tutorialspoint.com/apache_poi/apache_poi_core_classes.htm](https://www.tutorialspoint.com/apache_poi/apache_poi_core_classes.htm)
- [https://swk3169.tistory.com/56](https://swk3169.tistory.com/56)
- [https://epthffh.tistory.com/entry/java-excel-poi-눈금선병합배경색테두리글씨체사이즈금액천단위](https://epthffh.tistory.com/entry/java-excel-poi-%EB%88%88%EA%B8%88%EC%84%A0%EB%B3%91%ED%95%A9%EB%B0%B0%EA%B2%BD%EC%83%89%ED%85%8C%EB%91%90%EB%A6%AC%EA%B8%80%EC%94%A8%EC%B2%B4%EC%82%AC%EC%9D%B4%EC%A6%88%EA%B8%88%EC%95%A1%EC%B2%9C%EB%8B%A8%EC%9C%84)