package com.example.dule2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cz.msebera.android.httpclient.Header;

public class LoadingActivity extends AppCompatActivity {


        File file_workbook;
        String[] url = new String[4];
        AsyncHttpClient client;
        FileInputStream fis;
        XSSFWorkbook workbook;
        private final static String bak1 = "bak1.xlsx";
        private final static String bak2 = "bak2.xlsx";
        private final static String bak3 = "bak3.xlsx";
        private final static String bak4 = "bak4.xlsx";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        url[0] = "https://github.com/lulislaw/ExcelFilesForAnroidGUU/blob/main/bak1.xlsx?raw=true";
        url[1] = "https://github.com/lulislaw/ExcelFilesForAnroidGUU/blob/main/bak2.xlsx?raw=true";
        url[2] = "https://github.com/lulislaw/ExcelFilesForAnroidGUU/blob/main/bak3.xlsx?raw=true";
        url[3] = "https://github.com/lulislaw/ExcelFilesForAnroidGUU/blob/main/bak4.xlsx?raw=true";



            client = new AsyncHttpClient();
            client.get(url[0], new FileAsyncHttpResponseHandler(this) {
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {

                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, File file) {
                    if (file != null) {
                        file_workbook = file;
                        try {
                            ZipSecureFile.setMinInflateRatio(0);
                            fis = new FileInputStream(file);
                            workbook = new XSSFWorkbook(fis);

                            for(int s = 0; s < workbook.getNumberOfSheets(); s++)
                            {
                                Sheet SHEET = workbook.getSheetAt(s);

                                int lenmergedregion = SHEET.getMergedRegions().size();
                                int[] CellStart = new int[lenmergedregion];
                                int[] CellEnd = new int[lenmergedregion];
                                int[] RowStart = new int[lenmergedregion];
                                int[] RowEnd = new int[lenmergedregion];
                                for (int i = 0; i < lenmergedregion; i++) {
                                    CellStart[i] = SHEET.getMergedRegions().get(i).getFirstColumn();
                                    RowStart[i] = SHEET.getMergedRegions().get(i).getFirstRow();
                                    CellEnd[i] = SHEET.getMergedRegions().get(i).getLastColumn();
                                    RowEnd[i] = SHEET.getMergedRegions().get(i).getLastRow();
                                }

                                for (int i = 0; i < lenmergedregion; i++) {
                                    String mergedstring = "";
                                    for (int r = RowStart[i]; r <= RowEnd[i]; r++) {

                                        for (int c = CellStart[i]; c <= CellEnd[i]; c++) {
                                            if (SHEET.getRow(r).getCell(c).toString().length() > 1) {
                                                mergedstring = SHEET.getRow(r).getCell(c).toString();
                                            }

                                        }

                                    }
                                    for (int r = RowStart[i]; r <= RowEnd[i]; r++) {

                                        for (int c = CellStart[i]; c <= CellEnd[i]; c++) {
                                            SHEET.getRow(r).getCell(c).setCellValue(mergedstring);
                                        }
                                    }
                                }


                                int a = 0;
                                for (int i = 0; i < 100; i++) {
                                    if (SHEET.getRow(5).getCell(i + 4) != null)
                                        a++;
                                    else
                                        break;
                                }

                            }
                            FileOutputStream fos = new FileOutputStream(bak1);
                            workbook.write(fos);




                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }


                }
            });










    }
}