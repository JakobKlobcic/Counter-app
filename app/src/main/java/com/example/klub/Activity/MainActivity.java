package com.example.klub.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.klub.Service.CheckForUpdate;
import com.example.klub.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends Activity {
    Button Add;
    Button Subtract;
    public static EditText ShowNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //System.out.println("#####OnCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("yolo" );
        ShowNumber = findViewById(R.id.showNumber);
        Add = findViewById(R.id.add);
        Subtract = findViewById(R.id.subtract);
        this.startService(new Intent(this, CheckForUpdate.class));

    }


    public void onAddClick(View view) {
        try {

            new AddPerson(this).execute().get();

        }catch(Exception e){

            System.out.println(e);
        }

    }




    public void onSubtractClick(View view) {
        try {

            new SubtractPerson(this).execute().get();

        }catch(Exception e){

            System.out.println(e);
        }
    }


    public void onAnalyzeClick(View view) {
        Intent i = new Intent(MainActivity.this, AnalyzeActivity.class);
        startActivity(i);
    }



    public class AddPerson extends AsyncTask<String, Void, String> {

        Context context;
        AddPerson(Context ctx) {
            context = ctx;
        }

        @Override
        protected void onPreExecute() {
            System.out.println("------onPreExecute-----");
            Add.setEnabled(false);
        }


        @Override
        protected String doInBackground(String... params) {
            String result = "";
            String login_url = "http://mytestingground.com/Development_projects/Klub/add_person.php";

            try {

                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;

                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                System.out.println("-------------"+result+"-------------");
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }




            // PREPARE DATA FOR THE MAP


            return null;
        }
        @Override

        protected void onPostExecute(String result) {
            ShowNumber.setText(result);
            Add.setEnabled(true);
        }
    }




    public class SubtractPerson extends AsyncTask<String, Void, String> {

        Context context;
        SubtractPerson(Context ctx) {
            context = ctx;
        }

        @Override
        protected void onPreExecute() {
            System.out.println("------onPreExecute-----");
            Subtract.setEnabled(false);
        }


        @Override
        protected String doInBackground(String... params) {
            String result = "";
            String login_url = "http://mytestingground.com/Development_projects/Klub/subtract_person.php";

            try {

                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;

                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                System.out.println("-------------"+result+"-------------");
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }


        @Override
        protected void onPostExecute(String result) {
            ShowNumber.setText(result);
            Subtract.setEnabled(true);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx onDestroy");

    }
}
