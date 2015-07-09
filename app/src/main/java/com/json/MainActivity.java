package com.json;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity  {

    String url ="http://api.openweathermap.org/data/2.5/weather?q=kolkata,in";
    TextView Title_it,Con_txt,txt_dsc;
    String cc,dc;
    ImageView img_weather;
    ProgressWheel progressWheel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        new Task().execute();

    }

    private void initialize() {

        Title_it = (TextView)findViewById(R.id.Title_it);
        Con_txt = (TextView)findViewById(R.id.Con_txt);
        txt_dsc = (TextView)findViewById(R.id.txt_dsc);
        String fontPath = "fonts/two.TTF";
        String fontPath2 = "fonts/bau.TTF";
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        Typeface tf2 = Typeface.createFromAsset(getAssets(), fontPath2);
        Title_it.setTypeface(tf);
        Con_txt.setTypeface(tf2);

        img_weather = (ImageView)findViewById(R.id.img_weather);
        progressWheel = (ProgressWheel)findViewById(R.id.progress_wheel);

        Con_txt.setVisibility(View.INVISIBLE);
        txt_dsc.setVisibility(View.INVISIBLE);
    }

    public void Pars() {
        JsonParser jsonParser = new JsonParser();
        JSONObject purl = jsonParser.getJSONFromUrl(url);

        try {
            // Getting JSON Array
            JSONArray Weather = purl.getJSONArray("weather");
            JSONObject Condition = Weather.getJSONObject(0);
            cc = Condition.getString("main");
            dc = Condition.getString("description");
            Log.e("Got JSON = ",cc );



            //Set JSON Data in TextView

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class Task extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected Void doInBackground(Void... params) {

            Pars();
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressWheel.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Con_txt.setText(cc);
            txt_dsc.setText(dc);

            progressWheel.setVisibility(View.GONE);
            Con_txt.setVisibility(View.VISIBLE);
            txt_dsc.setVisibility(View.VISIBLE);
        }
    }


}
