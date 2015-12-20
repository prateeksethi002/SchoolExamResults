package com.nptech.schoolexamresults;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class showResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
