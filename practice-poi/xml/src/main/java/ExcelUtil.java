import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ExcelUtil {
    public Map<Integer, Map<Integer, Object>> readFlatWorkbook(Sheet sheet){
        Map<Integer, Map<Integer, Object>> sheetData = new HashMap<>();
        for (Row row: sheet){
            Map<Integer, Object> rowData = new HashMap<>();
            for (Cell cell: row){
                switch (cell.getCellType()){
                    case _NONE: break;
                    case BLANK: break;
                    case ERROR: break;
                    case STRING:
                        rowData.put(cell.getColumnIndex(), cell.getStringCellValue());
                        break;
                    case BOOLEAN:
                        rowData.put(cell.getColumnIndex(), cell.getBooleanCellValue());
                        break;
                    case NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell)){
                            rowData.put(
                                    cell.getColumnIndex(),
                                    new SimpleDateFormat("yyyy.M.d").format(cell.getDateCellValue())
                            );
                        }else{
                            rowData.put(cell.getColumnIndex(), cell.getNumericCellValue());
                        }
                        break;
                }
            }
            sheetData.put(row.getRowNum(), rowData);
        }
        return sheetData;
    }

    public void writeFlatWorkbook(Workbook workbook, Sheet sheet, Map<Integer, Map<Integer, Object>> sheetData){
        for (Integer rowKey: sheetData.keySet()){
            Row row = sheet.createRow(rowKey);
            Map<Integer, Object> rowData = sheetData.get(rowKey);
            for (Integer columnKey: rowData.keySet()){
                Cell cell = row.createCell(columnKey);
                Object obj = rowData.get(columnKey);
                if (obj instanceof String) {
                    cell.setCellValue((String) obj);
                }  else if (obj instanceof Integer) {
                    cell.setCellValue((Integer) obj);
                } else if (obj instanceof  Double){
                    cell.setCellValue((double) obj);
                } else if (obj instanceof LocalDate) {
                    cell.setCellValue((LocalDate) obj);
                    CreationHelper createHelper = workbook.getCreationHelper();
                    CellStyle dateStyle = workbook.createCellStyle();
                    dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-M-d"));
                    cell.setCellStyle(dateStyle);
                }
            }
            System.out.println();
        }
    }
}
