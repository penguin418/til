import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class ReadExcel {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("sample.xlsx");

        try (
                Workbook workbook = WorkbookFactory.create(file);
        ) {
            Sheet sheet = workbook.getSheetAt(0);
            ExcelUtil util = new ExcelUtil();
            Map<Integer, Map<Integer, Object>> sheetData = util.readFlatWorkbook(sheet);
            for (Integer rowKey: sheetData.keySet()){
                Map<Integer, Object> rowData = sheetData.get(rowKey);
                for (Integer columnKey: rowData.keySet()){
                    System.out.print(String.valueOf(rowData.get(columnKey)) + "\t\t");;
                }
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
