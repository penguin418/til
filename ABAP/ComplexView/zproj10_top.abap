*&---------------------------------------------------------------------*
*& Include          ZPROJ10_TOP
*&---------------------------------------------------------------------*

*&---------------------------------------------------------------------*
*& DATA
*&---------------------------------------------------------------------*
DATA: GS_CAR TYPE SFLIGHT-CARRID,
      GS_CON TYPE SFLIGHT-CONNID,
      GS_DAT TYPE SFLIGHT-FLDATE.
* 공유
DATA: GS_FIELD_CAT TYPE LVC_S_FCAT,
      GS_LAYOUTS   TYPE LVC_S_LAYO,
      GS_VARIANTS  TYPE DISVARIANT.
* ALV 스크린 101
DATA: OK_CODE_101      TYPE SY-UCOMM, " 레이아웃용 변수
      GO_CONT_101      TYPE REF TO CL_GUI_CUSTOM_CONTAINER,
      GO_ALV_101       TYPE REF TO CL_GUI_ALV_GRID,
      GT_FIELD_CAT_101 TYPE LVC_T_FCAT,
      GV_CNT           TYPE I.

TYPES BEGIN OF GTY_SFLIGHT.      " output 스트럭쳐
  TYPES CHECK.
  INCLUDE TYPE SFLIGHT.
TYPES END OF GTY_SFLIGHT.

DATA: GT_SFLIGHTS TYPE TABLE OF GTY_SFLIGHT, " 테이블
      GS_SFLIGHT  TYPE GTY_SFLIGHT.

* 스크린 102
DATA: OK_CODE_102 TYPE SY-UCOMM.    " 레이아웃용 변수
DATA: GO_CONT_102      TYPE REF TO CL_GUI_CUSTOM_CONTAINER,
      GO_ALV_102       TYPE REF TO CL_GUI_ALV_GRID,
      GT_FIELD_CAT_102 TYPE LVC_T_FCAT.

TYPES: BEGIN OF GTY_SBOOK_SCUSTOM,  " output 스트럭쳐
  BOOKID     LIKE SBOOK-BOOKID,
  CUSTOMID   LIKE SBOOK-CUSTOMID,
  NAME       LIKE SCUSTOM-NAME,
  CUSTTYPE   LIKE SBOOK-CUSTTYPE,
  SMOKER     LIKE SBOOK-SMOKER,
  LUGGWEIGHT LIKE SBOOK-LUGGWEIGHT,
  WUNIT      LIKE SBOOK-WUNIT,
  INVOICE    LIKE SBOOK-INVOICE,
  CLASS      LIKE SBOOK-CLASS,
  LOCCURAM   LIKE SBOOK-LOCCURAM,
  LOCCURAKEY LIKE SBOOK-LOCCURKEY,
  ORDER_DATE LIKE SBOOK-ORDER_DATE,
END OF GTY_SBOOK_SCUSTOM.

DATA: GT_SBOOK_SCUSTOM TYPE TABLE OF GTY_SBOOK_SCUSTOM. " 테이블

* 스크린 103
DATA: OK_CODE_103 TYPE SY-UCOMM.     " 레이아웃용 변수
DATA: GO_CONT_103      TYPE REF TO CL_GUI_CUSTOM_CONTAINER,
      GO_ALV_103       TYPE REF TO CL_GUI_ALV_GRID,
      GT_FIELD_CAT_103 TYPE LVC_T_FCAT.

DATA: GT_SPFLIS TYPE TABLE OF SPFLI. " 테이블


* 탭스트립 스크린 201
CONTROLS: CO_TABS TYPE TABSTRIP.     " 레이아웃용 변수
DATA: OK_CODE_201 TYPE SY-UCOMM.
DATA: DYNNR TYPE SY-DYNNR.

*&---------------------------------------------------------------------*
*& SELECTION SCREEN
*&---------------------------------------------------------------------*
SELECTION-SCREEN BEGIN OF BLOCK A WITH FRAME TITLE TEXT-001.
  SELECT-OPTIONS:
  SO_CAR FOR GS_CAR ,
  SO_CON FOR GS_CON ,
  SO_DAT FOR GS_DAT.
SELECTION-SCREEN END OF BLOCK A.
SELECTION-SCREEN BEGIN OF BLOCK B WITH FRAME TITLE TEXT-002.
  SELECTION-SCREEN BEGIN OF LINE.
    PARAMETERS
    PA_ALV RADIOBUTTON GROUP RLAY.
    SELECTION-SCREEN COMMENT (8) TEXT-003.
    PARAMETERS
    PA_TAB RADIOBUTTON GROUP RLAY.
    SELECTION-SCREEN COMMENT (15) TEXT-004.
  SELECTION-SCREEN END OF LINE.
SELECTION-SCREEN END OF BLOCK B.



*&---------------------------------------------------------------------*
*& AT SELECTION SCREEN
*&---------------------------------------------------------------------*
AT SELECTION-SCREEN.
IF SO_CAR IS INITIAL OR SO_CON IS INITIAL.
  MESSAGE W000 WITH 'Airline, Connection Number를 입력해야합니다'.
ENDIF.

CASE 'X'.
WHEN PA_ALV.
  SELECT * FROM SFLIGHT
  INTO CORRESPONDING FIELDS OF TABLE GT_SFLIGHTS
  WHERE CARRID IN SO_CAR
  AND CONNID IN SO_CON
  AND FLDATE IN SO_DAT.
  CALL SCREEN 101.

  WHEN PA_TAB.
  IF SO_DAT IS INITIAL. " fldate를 사용할지 여부를 결정
    SELECT SINGLE * FROM SFLIGHT
    INTO CORRESPONDING FIELDS OF GS_SFLIGHT
    WHERE CARRID = SO_CAR-LOW
    AND CONNID = SO_CON-LOW.
  ELSE.
    SELECT SINGLE * FROM SFLIGHT
    INTO CORRESPONDING FIELDS OF GS_SFLIGHT
    WHERE CARRID = SO_CAR-LOW
    AND CONNID = SO_CON-LOW
    AND FLDATE = SO_DAT-LOW.
  ENDIF.
  CALL SCREEN 201.
ENDCASE.