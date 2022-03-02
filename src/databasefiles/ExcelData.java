package databasefiles;

import common.Aerodrome;
import common.Flight;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExcelData extends Aerodrome {

    public static String excelFileURL="";
    public static String excelFileName="";
    public static final int  COLUMN_FLIGH_NAME='C'-65;
    public static final int COLUMN_BODYTYPE = 'E'-65;
    public static final int COLUMN_REG = 'F'-65;
    public static final int COLUMN_STA ='G'-65;
    public static final int COLUMN_STD ='K'-65;
    public static final int COLUMN_DEPNUMBER = 'L'-65;
    public static final int COLUMN_FROM='N'-65;
    public static final int COLUMN_TO='O'-65;
    public static final int COLUMN_HND='P'-65;
    public static final int COLUMN_TER='Q'-65;
    public static final int COLUMN_PARK = 'I'-65;
    public static final int COLUMN_COMPANY_NAME = 'B'-65;


    public String getFileDate(String fileName){
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        try {
            Date date = format.parse(fileName);
            return format.format(date);
        } catch (ParseException e) {
            System.out.println("File Date couldnt be reached....");
        }
        return "";
    }
    public List<Flight> readExcelFile2(String fileURL)throws IOException, FileNotFoundException,NullPointerException {
        FileInputStream fis = new FileInputStream(new File(fileURL));
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        XSSFSheet sheet = wb.getSheetAt(0);

        int lastRow = sheet.getLastRowNum();
        List<Flight> flightList = new ArrayList<>();

        for(int i=1;i<lastRow-1;i++){
            XSSFRow row = sheet.getRow(i);

                Flight f = new Flight();
                f.setName(row.getCell(COLUMN_FLIGH_NAME, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString());
                f.setBodyType(row.getCell(COLUMN_BODYTYPE, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString());
                f.setRegistration(row.getCell(COLUMN_REG, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString());
                String sta = row.getCell(COLUMN_STA, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                f.setSTA(getFileDate(excelFileName)+" "+sta);
                String std = row.getCell(COLUMN_STD, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                f.setSTD(getFileDate(excelFileName)+" "+std);
                f.setDepCallSign(row.getCell(COLUMN_DEPNUMBER, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString());
                f.setFrom(row.getCell(COLUMN_FROM, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString());
                f.setTo(row.getCell(COLUMN_TO, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString());
                f.setHnd(row.getCell(COLUMN_HND, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString());
               // f.setTerminal(Integer.parseInt(row.getCell(COLUMN_TER, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString()));

                f.setTerminal((int)row.getCell(COLUMN_TER, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getNumericCellValue());
                //f.setPlot(row.getCell(COLUMN_PARK, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString());
                f.setPlot(getFlightPlot(row.getCell(COLUMN_PARK, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString()));
                f.setCompanyName(row.getCell(COLUMN_COMPANY_NAME, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString());
                if(f.getName()=="")continue;
                if(f.getSTA().contains("RON")) continue;
                flightList.add(f);

            System.out.println(f.toString());
        }
        System.out.println("-------------------------------------------------------------");
        fis.close();
        wb.close();
        return flightList;
    }


}
