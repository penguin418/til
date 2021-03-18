*&---------------------------------------------------------------------*
*& Include          ZPROJ10_F01
*&---------------------------------------------------------------------*
*&---------------------------------------------------------------------*
*& Form CREATE_CONTAINER
*&---------------------------------------------------------------------*
*& CONTAINER에 대해 CREATE OBJECT를 호출해주는 서브루틴
*&---------------------------------------------------------------------*
*      -->PO_CONT  text
*&---------------------------------------------------------------------*
FORM create_container USING po_cont TYPE REF TO cl_gui_custom_container
                            po_name TYPE c.
  CREATE OBJECT po_cont
    EXPORTING
      container_name = po_name
    EXCEPTIONS
      OTHERS         = 6.
  IF sy-subrc <> 0.
    MESSAGE e000(bc405) WITH 'container 생성을 실패했습니다'.
  ENDIF.
ENDFORM.
*&---------------------------------------------------------------------*
*& Form CREATE_ALV_GRID
*&---------------------------------------------------------------------*
*& AVL에 대해 CREATE OBJECT를 호출해주는 서브루틴
*&---------------------------------------------------------------------*
*      -->PO_ALV  text
*      -->PO_CONT  text
*&---------------------------------------------------------------------*
FORM create_alv_grid  USING po_alv  TYPE REF TO cl_gui_alv_grid
                            po_cont TYPE REF TO cl_gui_custom_container.
  CREATE OBJECT po_alv
    EXPORTING
      i_parent = po_cont
    EXCEPTIONS
      OTHERS   = 5.
  IF sy-subrc <> 0.
    MESSAGE e000(bc405) WITH 'alv 생성을 실패했습니다'.
  ENDIF.
ENDFORM.
*&---------------------------------------------------------------------*
*& Form REFRESH_ALV
*&---------------------------------------------------------------------*
*& REFRESH_TABLE_DISPLAY를 호출해주는 서브루틴
*&---------------------------------------------------------------------*
*      -->PO_ALV  text
*&---------------------------------------------------------------------*
FORM refresh_alv  USING po_alv TYPE REF TO cl_gui_alv_grid.
  CALL METHOD po_alv->refresh_table_display
    EXCEPTIONS
      OTHERS = 2.
  IF sy-subrc <> 0.
    MESSAGE e000(bc405) WITH 'alv refresh를 실패했습니다'.
  ENDIF.
ENDFORM.