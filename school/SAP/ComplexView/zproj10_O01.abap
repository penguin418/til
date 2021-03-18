*&---------------------------------------------------------------------*
*& Include          ZPROJ10_O01
*&---------------------------------------------------------------------*
CLASS LCL_HANDLER DEFINITION.
    PUBLIC SECTION.
      CLASS-METHODS:
        ON_TOOLBAR FOR EVENT TOOLBAR OF CL_GUI_ALV_GRID
          IMPORTING E_OBJECT,
        ON_USER_COMMAND FOR EVENT USER_COMMAND OF CL_GUI_ALV_GRID
          IMPORTING E_UCOMM,
        ON_HOTSPOT_CLICK FOR EVENT HOTSPOT_CLICK OF CL_GUI_ALV_GRID
          IMPORTING ES_ROW_NO E_COLUMN_ID.
  ENDCLASS.
  CLASS LCL_HANDLER IMPLEMENTATION.
    METHOD ON_TOOLBAR. " 툴바에 버튼 등록
      DATA LS_BTN TYPE STB_BUTTON.
      LS_BTN-FUNCTION = 'DETAIL'.
      LS_BTN-BUTN_TYPE = 0.
      LS_BTN-TEXT = '상세조회'.
      INSERT LS_BTN INTO E_OBJECT->MT_TOOLBAR INDEX 1.
  
      LS_BTN-FUNCTION = 'CARRINFO'.
      LS_BTN-BUTN_TYPE = 0.
      LS_BTN-TEXT = '항공사정보'.
      INSERT LS_BTN INTO E_OBJECT->MT_TOOLBAR INDEX 2.
    ENDMETHOD.
  
    METHOD ON_USER_COMMAND. " 툴바 버튼 동작
      CASE E_UCOMM.
        WHEN 'DETAIL'.
          GV_CNT = 0.
          LOOP AT GT_SFLIGHTS INTO GS_SFLIGHT WHERE CHECK = 'X'.
            SELECT * FROM SBOOK JOIN SCUSTOM
            ON SBOOK~CUSTOMID = SCUSTOM~ID
            INTO CORRESPONDING FIELDS OF TABLE GT_SBOOK_SCUSTOM
            WHERE SBOOK~CARRID = GS_SFLIGHT-CARRID
            AND SBOOK~CONNID = GS_SFLIGHT-CONNID
            and sbook~fldate = GS_SFLIGHT-fldate.
            IF SY-SUBRC <> 0.
              MESSAGE I000(BC405) WITH 'select 과정에서 오류가 발생했습니다'.
            ELSE.
              ADD 1 TO GV_CNT.
            ENDIF.
          ENDLOOP.
  
          IF GV_CNT <> 1.
            MESSAGE I000(BC405) WITH '하나의 라인을 선택해야 합니다' DISPLAY LIKE 'W'.
          ELSE.
            CALL SCREEN 102 STARTING AT 5 5.
          ENDIF.
  
        WHEN 'CARRINFO'.
          GV_CNT = 0.
          LOOP AT GT_SFLIGHTS INTO GS_SFLIGHT WHERE CHECK = 'X'.
            SELECT * FROM SPFLI
            INTO CORRESPONDING FIELDS OF TABLE GT_SPFLIS
            WHERE CARRID = GS_SFLIGHT-CARRID
            AND CONNID = GS_SFLIGHT-CONNID.
  
            IF SY-SUBRC <> 0.
              MESSAGE I000(BC405) WITH 'select 과정에서 오류가 발생했습니다'.
            ELSE.
              ADD 1 TO GV_CNT.
            ENDIF.
  
          ENDLOOP.
  
          IF GV_CNT <> 1.
            MESSAGE I000(BC405) WITH '하나의 라인을 선택해야 합니다' DISPLAY LIKE 'W'.
          ELSE.
            CALL SCREEN 103 STARTING AT 5 5.
          ENDIF.
  
      ENDCASE.
    ENDMETHOD.
  
    METHOD ON_HOTSPOT_CLICK. " 클릭 처리
      CLEAR GS_SFLIGHT.
      LOOP AT GT_SFLIGHTS INTO GS_SFLIGHT.
        IF SY-TABIX = ES_ROW_NO-ROW_ID.
          IF GS_SFLIGHT-CHECK = 'X'.
            GS_SFLIGHT-CHECK = ''.
          ELSE.
            GS_SFLIGHT-CHECK = 'X'.
          ENDIF.
        ELSE.
          GS_SFLIGHT-CHECK = ''.
        ENDIF.
        MODIFY GT_SFLIGHTS FROM GS_SFLIGHT INDEX SY-TABIX.
        IF SY-SUBRC <> 0.
          MESSAGE I000(BC405) WITH '체크할 수 없습니다' DISPLAY LIKE 'W'.
        ENDIF.
      ENDLOOP.
  
      "
      CALL METHOD GO_ALV_101->REFRESH_TABLE_DISPLAY
        EXCEPTIONS
          OTHERS = 2.
      IF SY-SUBRC <> 0.
        MESSAGE I000(BC405) WITH 'alv refresh를 실패했습니다'.
      ENDIF.
    ENDMETHOD.
  ENDCLASS.
  *&---------------------------------------------------------------------*
  *& Module STATUS_0101 OUTPUT
  *&---------------------------------------------------------------------*
  *&
  *&---------------------------------------------------------------------*
  MODULE STATUS_0101 OUTPUT.
    SET PF-STATUS '101'.
  * SET TITLEBAR 'xxx'.
  ENDMODULE.
  *&---------------------------------------------------------------------*
  *& Module ALV_0101 OUTPUT
  *&---------------------------------------------------------------------*
  *& 상세성보, 항공사정보 버튼이 있고 SFLIGHT를 출력하는 ALV를 그림
  *&---------------------------------------------------------------------*
  MODULE ALV_0101 OUTPUT.
    IF GO_CONT_101 IS INITIAL.
  
      PERFORM CREATE_CONTAINER USING GO_CONT_101 'CONTROL_AREA_101'.
      PERFORM CREATE_ALV_GRID USING GO_ALV_101 GO_CONT_101.
  
      SET HANDLER LCL_HANDLER=>ON_TOOLBAR FOR GO_ALV_101.
      SET HANDLER LCL_HANDLER=>ON_USER_COMMAND FOR GO_ALV_101.
      SET HANDLER LCL_HANDLER=>ON_HOTSPOT_CLICK FOR GO_ALV_101.
  
      CLEAR GS_FIELD_CAT.
      GS_FIELD_CAT-FIELDNAME = 'CHECK'.
      GS_FIELD_CAT-COLTEXT = '체크박스'(101).
      GS_FIELD_CAT-CHECKBOX = 'X'.
      GS_FIELD_CAT-HOTSPOT = 'X'.
      GS_FIELD_CAT-EDIT = 'X'.
      APPEND GS_FIELD_CAT TO GT_FIELD_CAT_101.
  
      CLEAR GS_FIELD_CAT.
      GS_FIELD_CAT-FIELDNAME = 'MANDT'.
      GS_FIELD_CAT-NO_OUT = 'X'.
      APPEND GS_FIELD_CAT TO GT_FIELD_CAT_101.
  
      CLEAR GS_FIELD_CAT.
      GS_FIELD_CAT-REF_TABLE = 'SFLIGHT'.
  
      GS_FIELD_CAT-FIELDNAME = 'CARRID'.
      GS_FIELD_CAT-COLTEXT = 'Airline'(102).
      APPEND GS_FIELD_CAT TO GT_FIELD_CAT_101.
  
      GS_FIELD_CAT-FIELDNAME = 'CONNID'.
      GS_FIELD_CAT-COLTEXT = 'Flight No'(103).
      APPEND GS_FIELD_CAT TO GT_FIELD_CAT_101.
  
      GS_FIELD_CAT-FIELDNAME = 'FLDATE'.
      GS_FIELD_CAT-COLTEXT = 'Date'(104).
      APPEND GS_FIELD_CAT TO GT_FIELD_CAT_101.
  
      GS_FIELD_CAT-FIELDNAME = 'PRICE'.
      GS_FIELD_CAT-COLTEXT = 'Price'(105).
      APPEND GS_FIELD_CAT TO GT_FIELD_CAT_101.
  
      GS_FIELD_CAT-FIELDNAME = 'CURRENCY'.
      GS_FIELD_CAT-COLTEXT = 'CUR'(106).
      APPEND GS_FIELD_CAT TO GT_FIELD_CAT_101.
  
      GS_FIELD_CAT-FIELDNAME = 'PLANETYPE'.
      GS_FIELD_CAT-COLTEXT   = 'Pl.type'(107).
      APPEND GS_FIELD_CAT TO GT_FIELD_CAT_101.
  
      GS_FIELD_CAT-FIELDNAME = 'SEATSMAX'.
      GS_FIELD_CAT-COLTEXT   = 'SeatsMAX_E'(108).
      APPEND GS_FIELD_CAT TO GT_FIELD_CAT_101.
  
      GS_FIELD_CAT-FIELDNAME = 'SEATSOCC'.
      GS_FIELD_CAT-COLTEXT   = 'SeatsOCC_E'(109).
      APPEND GS_FIELD_CAT TO GT_FIELD_CAT_101.
  
      GS_FIELD_CAT-FIELDNAME = 'PAYMENTSUM'.
      GS_FIELD_CAT-COLTEXT   = 'Total'(110).
      APPEND GS_FIELD_CAT TO GT_FIELD_CAT_101.
  
      GS_FIELD_CAT-FIELDNAME = 'SEATSMAX_B'.
      GS_FIELD_CAT-COLTEXT   = 'SeatsMAX_B'(111).
      APPEND GS_FIELD_CAT TO GT_FIELD_CAT_101.
  
      GS_FIELD_CAT-FIELDNAME = 'SEATSOCC_F'.
      GS_FIELD_CAT-COLTEXT   = 'SeatsOCC_F'(112).
      APPEND GS_FIELD_CAT TO GT_FIELD_CAT_101.
  
      GS_FIELD_CAT-FIELDNAME = 'SEATSMAX_F'.
      GS_FIELD_CAT-COLTEXT   = 'SeatsMAX_F'(113).
      APPEND GS_FIELD_CAT TO GT_FIELD_CAT_101.
  
      GS_FIELD_CAT-FIELDNAME = 'SEATSOCC_B'.
      GS_FIELD_CAT-COLTEXT   = 'SeatsOCC_B'(114).
      APPEND GS_FIELD_CAT TO GT_FIELD_CAT_101.
  
      CLEAR GS_LAYOUTS.
      GS_LAYOUTS-CWIDTH_OPT = 'X'.
  
      CALL METHOD GO_ALV_101->SET_TABLE_FOR_FIRST_DISPLAY
        EXPORTING
          I_STRUCTURE_NAME = 'SFLIGHT'
          IS_LAYOUT        = GS_LAYOUTS
        CHANGING
          IT_OUTTAB        = GT_SFLIGHTS
          IT_FIELDCATALOG  = GT_FIELD_CAT_101
        EXCEPTIONS
          OTHERS           = 4.
      IF SY-SUBRC <> 0.
      ENDIF.
    ELSE.
  
      PERFORM REFRESH_ALV USING GO_ALV_101.
    ENDIF.
  
  
  ENDMODULE.
  *&---------------------------------------------------------------------*
  *& Module STATUS_0102 OUTPUT
  *&---------------------------------------------------------------------*
  *&
  *&---------------------------------------------------------------------*
  MODULE STATUS_0102 OUTPUT.
    SET PF-STATUS '102'.
  * SET TITLEBAR 'xxx'.
  ENDMODULE.
  *&---------------------------------------------------------------------*
  *& Module ALV_0102 OUTPUT
  *&---------------------------------------------------------------------*
  *& SBOOK필드와 SCUSTOM일부 필드를 출력하는 ALV를 그림
  *&---------------------------------------------------------------------*
  MODULE ALV_0102 OUTPUT.
    IF GO_CONT_102 IS INITIAL.
  
      PERFORM CREATE_CONTAINER USING GO_CONT_102 'CONTROL_AREA_102'.
      PERFORM CREATE_ALV_GRID USING GO_ALV_102 GO_CONT_102.
  
      CLEAR GS_FIELD_CAT.                   " 필드 초기화
      GS_FIELD_CAT-REF_TABLE = 'SBOOK'.     " 테이블 외 다른 필드만 수정
  
      GS_FIELD_CAT-FIELDNAME = 'BOOKID'.
      GS_FIELD_CAT-COLTEXT = 'BOOKID'(201).
      APPEND GS_FIELD_CAT TO GT_FIELD_CAT_102.
  
      GS_FIELD_CAT-FIELDNAME = 'CUSTOMID'.
      GS_FIELD_CAT-COLTEXT = 'CUSTOMID'(202).
      APPEND GS_FIELD_CAT TO GT_FIELD_CAT_102.
  
      CLEAR GS_FIELD_CAT.                  " 필드 초기화
      GS_FIELD_CAT-REF_TABLE = 'SCUSTOM'.  " 테이블 외 다른 필드만 수정
  
      GS_FIELD_CAT-FIELDNAME = 'NAME'.     " SCUSTOM의 NAME필드 삽입
      GS_FIELD_CAT-COLTEXT = 'NAME'(203).
      APPEND GS_FIELD_CAT TO GT_FIELD_CAT_102.
  
      CLEAR GS_FIELD_CAT.                  " 필드 초기화
      GS_FIELD_CAT-REF_TABLE = 'SBOOK'.    " 테이블 외 다른 필드만 수정
  
      GS_FIELD_CAT-FIELDNAME = 'CUSTTYPE'.
      GS_FIELD_CAT-COLTEXT = 'CUSTTYPE'(204).
      APPEND GS_FIELD_CAT TO GT_FIELD_CAT_102.
  
      GS_FIELD_CAT-FIELDNAME = 'SMOKER'.
      GS_FIELD_CAT-COLTEXT = 'SMOKER'(205).
      APPEND GS_FIELD_CAT TO GT_FIELD_CAT_102.
  
      GS_FIELD_CAT-FIELDNAME = 'LUGGWEIGHT'.
      GS_FIELD_CAT-QFIELDNAME = 'WUNNIT'.
      GS_FIELD_CAT-COLTEXT = '화물무게'(206).
      APPEND GS_FIELD_CAT TO GT_FIELD_CAT_102.
  
      GS_FIELD_CAT-FIELDNAME = 'WUNIT'.
      GS_FIELD_CAT-COLTEXT = '단위'.
      APPEND GS_FIELD_CAT TO GT_FIELD_CAT_102.
  
      GS_FIELD_CAT-FIELDNAME = 'INVOICE'.
      GS_FIELD_CAT-COLTEXT = '지불여부'(207).
      APPEND GS_FIELD_CAT TO GT_FIELD_CAT_102.
  
      GS_FIELD_CAT-FIELDNAME = 'CLASS'.
      GS_FIELD_CAT-COLTEXT = 'CLASS'(208).
      APPEND GS_FIELD_CAT TO GT_FIELD_CAT_102.
  
      GS_FIELD_CAT-FIELDNAME = 'LOCCURAM'.
      GS_FIELD_CAT-CFIELDNAME = 'LOCCURKEY'.
      GS_FIELD_CAT-COLTEXT = 'AMOUNT'(209).
      APPEND GS_FIELD_CAT TO GT_FIELD_CAT_102.
  
      GS_FIELD_CAT-FIELDNAME = 'LOCCURKEY'.
      GS_FIELD_CAT-CFIELDNAME = ''.
      GS_FIELD_CAT-COLTEXT = 'CUR'(210).
      APPEND GS_FIELD_CAT TO GT_FIELD_CAT_102.
  
      GS_FIELD_CAT-FIELDNAME = 'ORDER_DATE'.
      GS_FIELD_CAT-COLTEXT = 'Order Date'(211).
      APPEND GS_FIELD_CAT TO GT_FIELD_CAT_102.
  
      CLEAR GS_LAYOUTS.
      GS_LAYOUTS-CWIDTH_OPT = 'X'.
  
      CALL METHOD GO_ALV_102->SET_TABLE_FOR_FIRST_DISPLAY
        EXPORTING
          IS_LAYOUT       = GS_LAYOUTS
        CHANGING
          IT_OUTTAB       = GT_SBOOK_SCUSTOM
          IT_FIELDCATALOG = GT_FIELD_CAT_102
        EXCEPTIONS
          OTHERS          = 4.
      IF SY-SUBRC <> 0.
      ENDIF.
  
    ELSE.
  
      PERFORM REFRESH_ALV USING GO_ALV_102.
    ENDIF.
  ENDMODULE.
  *&---------------------------------------------------------------------*
  *& Module STATUS_0103 OUTPUT
  *&---------------------------------------------------------------------*
  *&
  *&---------------------------------------------------------------------*
  MODULE STATUS_0103 OUTPUT.
    SET PF-STATUS '103'.
  * SET TITLEBAR 'xxx'.
  ENDMODULE.
  *&---------------------------------------------------------------------*
  *& Module ALV_0103 OUTPUT
  *&---------------------------------------------------------------------*
  *& SPFLI 를 출력하는 ALV를 그림
  *&---------------------------------------------------------------------*
  MODULE ALV_0103 OUTPUT.
    IF GO_CONT_103 IS INITIAL.
  
      PERFORM CREATE_CONTAINER USING GO_CONT_103 'CONTROL_AREA_103'.
      PERFORM CREATE_ALV_GRID USING GO_ALV_103 GO_CONT_103.
  
      CLEAR: GS_VARIANTS.                  "
      GS_VARIANTS-REPORT = SY-CPROG.
      GS_VARIANTS-VARIANT = 'carr_info_10'.
  
  
      CLEAR GS_FIELD_CAT.
      GS_FIELD_CAT-REF_TABLE = 'SPFLI'.
  
      GS_FIELD_CAT-FIELDNAME = 'CARRID'.
      GS_FIELD_CAT-COLTEXT = 'Airline'(301).
      APPEND GS_FIELD_CAT TO GT_FIELD_CAT_103.
  
      GS_FIELD_CAT-FIELDNAME = 'CONNID'.
      GS_FIELD_CAT-COLTEXT = 'Flight No'(302).
      APPEND GS_FIELD_CAT TO GT_FIELD_CAT_103.
  
      GS_FIELD_CAT-FIELDNAME = 'COUNTRYFR'.
      GS_FIELD_CAT-COLTEXT = '출발국가'(303).
      APPEND GS_FIELD_CAT TO GT_FIELD_CAT_103.
  
      GS_FIELD_CAT-FIELDNAME = 'CITYFROM'.
      GS_FIELD_CAT-COLTEXT = '출발도시'(304).
      APPEND GS_FIELD_CAT TO GT_FIELD_CAT_103.
  
      GS_FIELD_CAT-FIELDNAME = 'AIRPFROM'.
      GS_FIELD_CAT-COLTEXT = '출발공항'(305).
      APPEND GS_FIELD_CAT TO GT_FIELD_CAT_103.
  
      GS_FIELD_CAT-FIELDNAME = 'COUNTRYTO'.
      GS_FIELD_CAT-COLTEXT = '도착국가'(306).
      APPEND GS_FIELD_CAT TO GT_FIELD_CAT_103.
  
      GS_FIELD_CAT-FIELDNAME = 'CITYTO'.
      GS_FIELD_CAT-COLTEXT = '도착도시'(307).
      APPEND GS_FIELD_CAT TO GT_FIELD_CAT_103.
  
      GS_FIELD_CAT-FIELDNAME = 'AIRPTO'.
      GS_FIELD_CAT-COLTEXT = '도착공항'(308).
      APPEND GS_FIELD_CAT TO GT_FIELD_CAT_103.
  
      GS_FIELD_CAT-FIELDNAME = 'FLTIME'.
      GS_FIELD_CAT-COLTEXT = '비행시간'(309).
      APPEND GS_FIELD_CAT TO GT_FIELD_CAT_103.
  
      GS_FIELD_CAT-FIELDNAME = 'DEPTIME'.
      GS_FIELD_CAT-COLTEXT = '출발시간'(310).
      APPEND GS_FIELD_CAT TO GT_FIELD_CAT_103.
  
      GS_FIELD_CAT-FIELDNAME = 'ARRTIME'.
      GS_FIELD_CAT-COLTEXT = '도착시간'(311).
      APPEND GS_FIELD_CAT TO GT_FIELD_CAT_103.
  
      GS_FIELD_CAT-FIELDNAME = 'DISTANCE'.
      GS_FIELD_CAT-COLTEXT = '거리'(312).
      GS_FIELD_CAT-QFIELDNAME = 'DISTID'.
      APPEND GS_FIELD_CAT TO GT_FIELD_CAT_103.
  
      GS_FIELD_CAT-FIELDNAME = 'DISTID'.
      GS_FIELD_CAT-COLTEXT =  '단위'(313).
      GS_FIELD_CAT-QFIELDNAME = ''.
      APPEND GS_FIELD_CAT TO GT_FIELD_CAT_103.
  
      CLEAR GS_LAYOUTS.
      GS_LAYOUTS-CWIDTH_OPT = 'X'.
  
      CALL METHOD GO_ALV_103->SET_TABLE_FOR_FIRST_DISPLAY
        EXPORTING
          I_STRUCTURE_NAME = 'SPFLI'
          IS_VARIANT       = GS_VARIANTS
          IS_LAYOUT       = GS_LAYOUTS
        CHANGING
          IT_OUTTAB        = GT_SPFLIS
          IT_FIELDCATALOG  = GT_FIELD_CAT_103
        EXCEPTIONS
          OTHERS           = 4.
      IF SY-SUBRC <> 0.
        MESSAGE e000 with '무슨에러' DISPLAY LIKE 'W'.
      ENDIF.
    ELSE.
  
      PERFORM REFRESH_ALV USING GO_ALV_103.
    ENDIF.
  ENDMODULE.
  *&---------------------------------------------------------------------*
  *& Module STATUS_0201 OUTPUT
  *&---------------------------------------------------------------------*
  *&
  *&---------------------------------------------------------------------*
  MODULE STATUS_0201 OUTPUT.
    SET PF-STATUS '201'.
  * SET TITLEBAR 'xxx'.
  ENDMODULE.
  *&---------------------------------------------------------------------*
  *& Module TABS_201 OUTPUT
  *&---------------------------------------------------------------------*
  *&
  *&---------------------------------------------------------------------*
  MODULE TABS_201 OUTPUT.
    CASE CO_TABS-ACTIVETAB. " 활성화된 탭 선택
      WHEN 'T1'.
        DYNNR = 202.
      WHEN 'T2'.
        DYNNR = 203.
      WHEN OTHERS.          " 기본값
        CO_TABS-ACTIVETAB = 'T1'.
        DYNNR = 202.
    ENDCASE.
  ENDMODULE.