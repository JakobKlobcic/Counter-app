package com.example.klub.Activity;



//na koncu uporabi - funkcija appendData dodaja točke na graf v živo http://www.android-graphview.org/realtime-chart/
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.klub.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class AnalyzeActivity extends AppCompatActivity {

    String startHour;
    String endHour;
    String startDate;
    String endDate;
    String startMonth;
    String endMonth;
    String startYear;
    String endYear;
    Date start=new Date();
    Date end=new Date();
    List <String> workTime = new ArrayList<String>();
    List <String> graphTimeList = new ArrayList<String>();
    List <String> graphNumberList = new ArrayList<String>();
    HashMap graphPointsMap = new HashMap();
    GraphView graph;
    LineGraphSeries<DataPoint> series;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyze);
        graph = findViewById(R.id.graph);
        series= new LineGraphSeries<>();


            startDate = "16";
            endDate = "16";//Integer.toString(Integer.parseInt(startDate)+1);
            startMonth ="04";
            endMonth = startMonth;
            startHour = "08";
            endHour = "13";
            startYear = "2019";
            endYear =startYear;

            //Priprava datumov v milisekunde
        String startString = startDate+"-"+startMonth+"-"+startYear+":"+startHour+":"+"00:00 +0200" ;
        String endString = endDate+"-"+endMonth+"-"+endYear+":"+endHour+":"+"00:00 +0200" ;

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy:HH:mm:SS Z");

        try {
            start = sdf.parse(startString);
            end = sdf.parse(endString);
        } catch (ParseException e) {
            e.printStackTrace();
        }


            long startLong =start.getTime();
            long endLong =end.getTime();
            double startDouble = Long.valueOf(startLong).doubleValue();
            double endDouble = Long.valueOf(endLong).doubleValue();

            
      /*  graph.getViewport().setMinX(startDouble);
        graph.getViewport().setMaxX(endDouble);
       */graph.getViewport().setScrollable(true);  // enables horizontal scrolling
        graph.getViewport().setScrollableY(true); // enables vertical scrolling


                try {

                    new getGraphPoints(this).execute().get();

                }catch(Exception e){

                    System.out.println(e);
                }





        System.out.println("time to party"+workTime);
        //graph data




      /*  StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(workTime.toArray(new String[0]));
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(5);
        graph.getViewport().setMaxX(5);




*/

    }




    public class getGraphPoints extends AsyncTask<String, Void, String> {
        Context context;


        getGraphPoints(Context ctx) {
            context = ctx;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... params) {

            String login_url = "http://mytestingground.com/Development_projects/Klub/get_graph_data.php";

            String starting = startYear + "-"+startMonth+"-"+startDate+" "+startHour+":00:00";
            String ending = endYear + "-"+endMonth+"-"+endDate+" "+endHour+":00:00";
            System.out.println("++++++Strat: "+starting+"+++++++++ending: "+ending);
          /*  try {
                String starting = startYear + "-"+startMonth+"-"+startDate+" "+startHour+":00:00";
                String ending = endYear + "-"+endMonth+"-"+endDate+" "+endHour+":00:00";
                System.out.println("++++++Strat: "+starting+"+++++++++ending: "+ending);
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("begin", "UTF-8") + "=" + URLEncoder.encode(starting, "UTF-8") + "&" + URLEncoder.encode("end", "UTF-8") + "=" + URLEncoder.encode(ending, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                System.out.print(result);
                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
*/


            try{


                String data  = URLEncoder.encode("begin", "UTF-8") + "=" +
                        URLEncoder.encode(starting, "UTF-8");
                data += "&" + URLEncoder.encode("end", "UTF-8") + "=" +
                        URLEncoder.encode(ending, "UTF-8");

                URL url = new URL(login_url);
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write( data );
                wr.flush();

                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();
                String line = "";
                String result = "";
                // Read Server Response
                while((line = reader.readLine()) != null) {
                    result += line;
                }



                return result;
            } catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }


        }



        @Override
        protected void onPostExecute(String result) {
            if(result!=null) {
                String[] entries = result.split(";;;;;;;;;;");
                int i = 0;
                while(i < entries.length){
                    String date = entries[i].split("::::::::::")[0];
                    String number = entries[i].split("::::::::::")[1];
                    graphTimeList.add(date);
                    graphNumberList.add(number);
                    i++;

                }
                System.out.println("lists#: "+graphNumberList.size());
                System.out.println("listss: "+graphTimeList);

                System.out.println("++++++++++++++++++++++++++++++++++++++ "+result+" +");
                DataPoint[]points = new DataPoint[graphTimeList.size()];
                SimpleDateFormat dfm = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss.SSS");
                int j=0;
                System.out.println("<---Before the loop--- j: "+ j + ", graph size"+ graphNumberList.size());

                while(j<graphNumberList.size() ){
                    System.out.println("------begin loop -------");

                    Date now = new Date();
                    try {
                        now = dfm.parse(graphTimeList.get(j));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    int num = Integer.parseInt(graphNumberList.get(j));
                    DataPoint dataPoint = new DataPoint(now,num);
                    points[j] = dataPoint;
                    series.appendData(new DataPoint(now,num),true,100);

                    System.out.println(graphTimeList.get(j)+"<------->"+graphNumberList.get(j));
                    j++;

                }
                System.out.println("<---after the loop---->");

                    graph.addSeries(series);


            }
        }




    }
}
