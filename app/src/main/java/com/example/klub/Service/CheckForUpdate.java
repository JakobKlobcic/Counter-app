package com.example.klub.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.widget.EditText;

import com.example.klub.Activity.MainActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;


public class CheckForUpdate extends Service {

    EditText ShowNumber= MainActivity.ShowNumber;
        int count =0;


    public CheckForUpdate() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

       System.out.println("#####Start Srevice-----------------------");
        final Handler h = new Handler();
        h.postDelayed(new Runnable()
        {


            @Override
            public void run()
            {

                CheckNumber checkNumber = new CheckNumber(CheckForUpdate.this);
                try {
                    checkNumber.execute().get();
                    count++;
                } catch (ExecutionException e) {

                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                h.postDelayed(this, 1000/*pol sekunde*/);//časovna perioda
            }
        }, 1000);

    }

    public class CheckNumber extends AsyncTask<String, Void, String> {

        Context context;
        CheckNumber(Context ctx) {
            context = ctx;
        }



        @Override
        protected void onPreExecute() {
            System.out.println("------onPreExecuteService-----");

        }


        @Override
        protected String doInBackground(String... params) {
            String result = "";
            String login_url = "http://mytestingground.com/Development_projects/Klub/check_for_update.php";

            try {

                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;

                }
                System.out.println(result);
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                System.out.println("-------------"+result+"---Service-------------");
                return result;
                //ČE DOBIŠ ERROR POJDI VEN IZ ASYNC TASKA!!!!!!!!
            } catch (MalformedURLException e) {
                e.printStackTrace();
                System.out.println("----------error MalformedURLException Count:"+count+"---------");

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("----------error IOException   Count:"+count+"---------");

            }



            return null;
        }
        @Override

        protected void onPostExecute(String result) {

            // stopTask(result);


            String stevilka=ShowNumber.getText().toString();
            if(stevilka!=result) {
                ShowNumber.setText(result);
            }

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("####Start Srevice-----------------------");

    }
}
