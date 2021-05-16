import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class WriteExcel {
    public static void main(String[] args) throws FileNotFoundException {
        Map<Integer, Map<Integer, Object>> sheetData = new HashMap<>();
        sheetData.put(0, new HashMap<>(){{ put(0, "hi"); put(1, 1.0); }});
        sheetData.put(1, new HashMap<>(){{ put(0, "there"); put(1, 13); }});
        sheetData.put(2, new HashMap<>(){{ put(0, "hello"); put(1, LocalDate.of(2011, 4, 11)); }});

        File file = new File("sample.xlsx");

        try (
                FileInputStream fIn = new FileInputStream(file);
                Workbook workbook = WorkbookFactory.create(fIn);

        ) {
            fIn.close();

            Sheet sheet = workbook.getSheetAt(0);
            ExcelUtil util = new ExcelUtil();
            util.writeFlatWorkbook(workbook, sheet, sheetData);
            FileOutputStream fOut = new FileOutputStream(file);
            workbook.write(fOut);
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
