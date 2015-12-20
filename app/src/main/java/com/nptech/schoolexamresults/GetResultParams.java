package com.nptech.schoolexamresults;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class GetResultParams extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    String registrationNumber;
    String url_year_list = "http://schoolapp.site88.net/get_year_class_Details.php";
    String url_exam_list = "";
    HashMap keyValues;
    private View mProgressView;

    // JSON Node names
    private static final String TAG_SUCCESS = "Success";
    private static final String TAG_STUDENT = "student";
    // JSON parser class
    JSONParser jsonParser = new JSONParser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_result_params);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mProgressView = findViewById(R.id.getdata_progress);
        Intent i = getIntent();
        registrationNumber = i.getStringExtra("Student_Id");


        Spinner year_spinner = (Spinner) findViewById(R.id.year);
        Spinner exam_spinner = (Spinner) findViewById(R.id.exam);
        ArrayAdapter<CharSequence> year_adapter = ArrayAdapter.createFromResource(GetResultParams.this,
                R.array.year_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> exam_adapter = ArrayAdapter.createFromResource(GetResultParams.this,
                R.array.exam_array, android.R.layout.simple_spinner_item);

        year_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exam_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year_spinner.setAdapter(year_adapter);
        exam_spinner.setAdapter(exam_adapter);

        showProgress(true);
        new GetExamsListTask(registrationNumber).execute();

        Button submit = (Button)findViewById(R.id.submit_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), showResult.class);
                startActivity(i);
            }
        });

     /*   FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() == R.id.year){
                if(parent.getItemAtPosition(position).toString() != "Select year"){
                        new GetExamsListTask(parent.getItemAtPosition(position).toString(),registrationNumber);
                }
                else
                {
                    //do nothing
                }

        }
        else
        {
            //do nothing
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public class GetExamsListTask extends AsyncTask<Void, Void, Boolean> {

        String year = null;
        String registrationNumber = null;
        JSONObject student;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        GetExamsListTask(String ExamYear,String registrationNumber){
            this.year = ExamYear;
            this.registrationNumber = registrationNumber;
        }

        GetExamsListTask(String registrationNumber)
        {
            this.registrationNumber = registrationNumber;
        }
        @Override
        protected Boolean doInBackground(Void... params) {

            int success;
            JSONObject json;
            try {
                List<NameValuePair> params2 = new ArrayList<NameValuePair>();
                params2.add(new BasicNameValuePair("sid", registrationNumber));
                if(year != null){
                    params2.add(new BasicNameValuePair("year", year));
                    json = jsonParser.makeHttpRequest(
                            url_exam_list, "GET", params2);
                }
                else{
                    json = jsonParser.makeHttpRequest(
                            url_year_list, "GET", params2);
                }

                // Building Parameters



                Log.d("parameters", params.toString());
                // getting student details by making HTTP request



                // check your log for json response
                Log.d("Exams Details", json.toString());

                // json success tag
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    // successfully received product details
                    JSONArray studentObj = json
                            .getJSONArray(TAG_STUDENT); // JSON Array

                    // get first student object from JSON Array
                    student = studentObj.getJSONObject(0);


                }else{
                    // product with pid not found
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            ArrayAdapter<String> adapter;
            List<String> list;
            list = new ArrayList<String>();
            list.add("Select year");
            if(year == null) {
                String value, key;

                Iterator<String> iter = student.keys();
                while (iter.hasNext()) {
                    key = iter.next();
                    if (! key.equalsIgnoreCase("Reg_No")) {
                        Log.d("key",key);
                        try {

                            value = student.get(key).toString();
                            list.add(key);

                        } catch (JSONException e) {
                            // Something went wrong!
                        }
                    }
                }
            }
            adapter = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_spinner_item, list);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Spinner year_spinner = (Spinner) findViewById(R.id.year);
            year_spinner.setAdapter(adapter);
            showProgress(false);

        }

    }
    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);


            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);

        }
    }

}
