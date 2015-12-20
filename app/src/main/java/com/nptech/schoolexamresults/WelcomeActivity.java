package com.nptech.schoolexamresults;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    String student_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent i = getIntent();
        student_id = i.getStringExtra("registration_number");
      /* View view,subView;
        LayoutInflater inflater = (LayoutInflater)   getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.content_welcome, null);
        subView = view.findViewById(R.id.linearLayout);*/


        TextView welcome_message = (TextView)findViewById(R.id.WelcomeMessage);

        welcome_message.setText("Welcome" + " " + i.getStringExtra("student_name"));


        TextView reg_no = (TextView)findViewById(R.id.RegistrationNoValue);
        reg_no.setText(i.getStringExtra("registration_number"));


        TextView stu_name = (TextView)findViewById(R.id.StudentNameValue);
        stu_name.setText(i.getStringExtra("student_name"));
        TextView date_of_birth = (TextView)findViewById(R.id.DateOfBirthValue);
        date_of_birth.setText(i.getStringExtra("date_of_birth"));

        TextView address = (TextView) findViewById(R.id.AddressValue);
        address.setText(i.getStringExtra("address"));

        TextView father_name = (TextView)findViewById(R.id.FatherNameValue);
        father_name.setText(i.getStringExtra("father_name"));

        TextView mother_name = (TextView) findViewById(R.id.MotherNameValue);
        mother_name.setText(i.getStringExtra("mother_name"));

        TextView primary_number = (TextView) findViewById(R.id.PrimaryContactValue);
        primary_number.setText(i.getStringExtra("primary_number"));

        TextView alternate_number = (TextView) findViewById(R.id.AlternateContactValue);
        alternate_number.setText(i.getStringExtra("alternate_number"));

        TextView email_id = (TextView) findViewById(R.id.EmailValue);
        email_id.setText(i.getStringExtra("email_id"));


        ImageButton imgButton = (ImageButton)findViewById(R.id.imageButton1);
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Call to database to fetch years list
                //then redirect to new page
                Intent i = new Intent(getApplicationContext(), GetResultParams.class);
                i.putExtra("Student_Id",student_id);
                startActivity(i);

            }
        });

    }

}
