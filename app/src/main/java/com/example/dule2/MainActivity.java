package com.example.dule2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.xb.xsdschema.ImportDocument;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Time;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {


    AsyncHttpClient client;
    DBHelper dbHelper= new DBHelper(MainActivity.this);
    FileInputStream fis;
    XSSFWorkbook workbook;
    ContentValues contentValues = new ContentValues();


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        client = new AsyncHttpClient();
        client.get("https://github.com/lulislaw/ExcelFilesForAnroidGUU/blob/main/1kursxlsx.xlsx?raw=true", new FileAsyncHttpResponseHandler(this) {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
            }
            public void onSuccess(int statusCode, Header[] headers, File file) {
                if(file != null) {
                    String sizefile = "";
                    try {
                        BasicFileAttributes attr = Files.readAttributes(Paths.get(file.getPath()), BasicFileAttributes.class);
                        sizefile = attr.size()+"";
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    SQLiteDatabase database1 = dbHelper.getWritableDatabase();
                    Cursor cursor1 = database1.query(DBHelper.TABLE_CONTACTS, null, null, null, null, null, null);
                    cursor1.moveToLast();
                    int NameIndex1 = cursor1.getColumnIndex(DBHelper.KEY_NAME);
                    Log.d("mylog", Integer.parseInt(cursor1.getString(NameIndex1)) +" | "+ Integer.parseInt(sizefile));
                    if(Integer.parseInt(cursor1.getString(NameIndex1)) != Integer.parseInt(sizefile)) {
                        try {
                            ZipSecureFile.setMinInflateRatio(0);
                            fis = new FileInputStream(file);
                            workbook = new XSSFWorkbook(fis);
                            XSSFSheet sheet = workbook.getSheetAt(0);
                            int lenmergedregion = sheet.getMergedRegions().size();
                            int[] CellStart = new int[lenmergedregion];
                            int[] CellEnd = new int[lenmergedregion];
                            int[] RowStart = new int[lenmergedregion];
                            int[] RowEnd = new int[lenmergedregion];
                            for (int i = 0; i < lenmergedregion; i++) {
                                CellStart[i] = sheet.getMergedRegions().get(i).getFirstColumn();
                                RowStart[i] = sheet.getMergedRegions().get(i).getFirstRow();
                                CellEnd[i] = sheet.getMergedRegions().get(i).getLastColumn();
                                RowEnd[i] = sheet.getMergedRegions().get(i).getLastRow();
                            }

                            for (int i = 0; i < lenmergedregion; i++) {
                                String mergedstring = "";
                                for (int r = RowStart[i]; r <= RowEnd[i]; r++) {

                                    for (int c = CellStart[i]; c <= CellEnd[i]; c++) {
                                        if (sheet.getRow(r).getCell(c).toString().length() > 1) {
                                            mergedstring = sheet.getRow(r).getCell(c).toString();
                                        }

                                    }

                                }
                                for (int r = RowStart[i]; r <= RowEnd[i]; r++) {

                                    for (int c = CellStart[i]; c <= CellEnd[i]; c++) {
                                        sheet.getRow(r).getCell(c).setCellValue(mergedstring);
                                    }
                                }
                            }


                            SQLiteDatabase database = dbHelper.getWritableDatabase();
                            database.delete(DBHelper.TABLE_CONTACTS, null, null);


                            for (int i = 8; i < 56; i++) {

                                String name = sheet.getRow(i).getCell(5).toString();
                                String week = sheet.getRow(i).getCell(3).toString();
                                String time = sheet.getRow(i).getCell(2).toString();

                                ContentValues contentValues = new ContentValues();
                                contentValues.put(DBHelper.KEY_NAME, name);
                                contentValues.put(DBHelper.KEY_TIME, time);
                                contentValues.put(DBHelper.KEY_WEEK, week);
                                database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);

                            }
                            String name = sizefile;


                            contentValues.put(DBHelper.KEY_NAME, name);
                            contentValues.put(DBHelper.KEY_TIME, "");
                            contentValues.put(DBHelper.KEY_WEEK, "");
                            database.insert(DBHelper.TABLE_CONTACTS, null, contentValues);
 /*
                            Cursor cursor = database.query(DBHelper.TABLE_CONTACTS, null, null, null, null, null, null);

                            if (cursor.moveToFirst()) {
                                int IdIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                                int NameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
                                int TimeIndex = cursor.getColumnIndex(DBHelper.KEY_TIME);
                                int WeekIndex = cursor.getColumnIndex(DBHelper.KEY_WEEK);
                                do {
                                    Log.d("mylog", "ID = " + cursor.getInt(IdIndex) +
                                            " NAME = " + cursor.getString(NameIndex) +
                                            " TIME = " + cursor.getString(TimeIndex) +
                                            " WEEK = " + cursor.getString(WeekIndex));

                                }
                                while (cursor.moveToNext());

                            }
                            cursor.close();
*/

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    cursor1.close();
                }
            }
        });





        }

}