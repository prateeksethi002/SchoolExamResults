package com.nptech.schoolexamresults;

import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class showResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


        // Set an OnMenuItemClickListener to handle menu item clicks
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Handle the menu item
                int id = item.getItemId();
                if(id == R.id.download_result){
                    boolean isExtStorageAvailable = isExternalStorageWritable();
                    if(isExtStorageAvailable == false){
                        Toast t= Toast.makeText(showResult.this,"Cannot export result: External storage is not available",Toast.LENGTH_SHORT);
                        t.show();
                        return true;
                    }
                    else {
                        File resultPdfFile;
                        //check for the new directory for result pdfs to reside
                        File fileDir = new File(Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_DOWNLOADS) + "/schoolResultPDFs");

                        if(fileDir.isDirectory() == false) {
                            //create if doesn't exists
                            fileDir = getPDFStorageDir("schoolResultPDFs");
                            if(fileDir == null){
                                Toast t = Toast.makeText(showResult.this,"Cannot export result: Not able to create or locate pdf directory",Toast.LENGTH_SHORT);
                                t.show();
                                return true;
                            }
                        }


                            try {
                                //create file under the
                                resultPdfFile = new File(fileDir.getAbsolutePath() + "/result3.pdf");
                            Document document = new Document();
                            PdfWriter.getInstance(document, new FileOutputStream(fileDir.getAbsolutePath() + "/result3.pdf"));
                            document.open();
                            PdfPTable table = new PdfPTable(5);
                            table.setWidthPercentage(50);
                            table.setHorizontalAlignment(Element.ALIGN_CENTER);

                                table.setWidths(new int[]{10, 10, 10, 10, 10});
                                //table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                                table.addCell("Exam");
                                table.addCell("Subject");
                                table.addCell("Maximum Marks");
                                table.addCell("Passing Marks");
                                table.addCell("Marks Obtained");
                                document.add(table);
                                document.close();
                                Toast t = Toast.makeText(showResult.this,"Result downloaded successfully at " + fileDir.getAbsolutePath() + "/result3.pdf" ,Toast.LENGTH_SHORT);
                                t.show();;
                            } catch (DocumentException e) {
                                e.printStackTrace();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }


                    }

                }
                return true;
            }

            public File getPDFStorageDir(String resultPDFs) {
                // Get the directory for the user's public pictures directory.
                File file = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS), resultPDFs);
                if (!file.mkdirs()) {

                    return null;
                }
                return file;
            }
            /* Checks if external storage is available for read and write */
            public boolean isExternalStorageWritable()
            {
                String state = Environment.getExternalStorageState();
                if (Environment.MEDIA_MOUNTED.equals(state)) {
                    return true;
                }
                return false;
            }
        });

        // Inflate a menu to be displayed in the toolbar
        toolbar.inflateMenu(R.menu.show_result_menu);
        TableLayout result = (TableLayout)findViewById(R.id.resultTable);
        TableRow dataRow = new TableRow(this);

        TextView examName = new TextView(this);
        examName.setText("FirstSemester");
        examName.setPadding(10, 10, 10, 10);
        dataRow.addView(examName,0);

        TextView subjectName = new TextView(this);
        subjectName.setText("Maths");
        subjectName.setPadding(10, 10, 10, 10);
        dataRow.addView(subjectName, 1);

        TextView maxMarks = new TextView(this);
        maxMarks.setText("100");
        maxMarks.setPadding(10, 10, 10, 10);
        dataRow.addView(maxMarks, 2);

        TextView passingMarks = new TextView(this);
        passingMarks.setText("50");
        passingMarks.setPadding(10, 10, 10, 10);
        dataRow.addView(passingMarks, 3);

        TextView marksObtained = new TextView(this);
        marksObtained.setText("98");
        marksObtained.setPadding(10,10,10,10);
        dataRow.addView(marksObtained,4);

        result.addView(dataRow);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
