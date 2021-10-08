package sr.unasat.thewishapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import sr.unasat.thewishapp.models.QuotesDTO;

public class MainActivity extends AppCompatActivity {

    TextView qoutesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        qoutesText = (TextView) findViewById(R.id.quote);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setProgress(0);

        getQuotes();

        Async task = new Async();
        task.setProgressBar(progressBar);
        task.execute();

    }

    //Get request (API) to get quotes-----------------------------------------------------------
    private void getQuotes() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        final String QUOTES = "https://script.google.com/macros/s/AKfycbzjt1GrB1lYbIlby4zWT8dl0mhtgFsz2Gc2eE1mYPITS2BcN-5N/exec";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, QUOTES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        final List<QuotesDTO> quotesDTO = mapJsonToCountryObject(response);
                        //System.out.println(quotesDTO);
                        Random random = new Random();
                        int i = random.nextInt(31);
                        qoutesText.setText(quotesDTO.get(i).toString());

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                qoutesText.setText("something went wrong");
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private List<QuotesDTO> mapJsonToCountryObject(String jsonArray) {
        ObjectMapper mapper = new ObjectMapper();
        List<QuotesDTO> quotesList = new ArrayList<>();
        List<Map<String, ?>> quotesArray = null;
        QuotesDTO quotes = null;

        try {
            quotesArray = mapper.readValue(jsonArray, List.class);
            for (Map<String, ?> map : quotesArray) {
                quotes = new QuotesDTO((String) map.get("Quote"), (String) map.get("Author"));
                quotesList.add(quotes);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Er is wat fout gegaan bij het parsen van de json data");
        }
        return quotesList;
    }

    //----------------------------------------------------------------------------------------
    public class Async extends AsyncTask<Void, Integer, Void> {

        ProgressBar bar;
        int progress_status;

        public void setProgressBar(ProgressBar bar) {
            this.bar = bar;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress_status = 0 ;
        }

        @Override
        protected Void doInBackground(Void... params) {
            while (progress_status < 100){
                progress_status +=2;
                publishProgress(progress_status);
                SystemClock.sleep(200);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(MainActivity.this, loginActivity.class);
                    startActivity(intent);
                }
            }, 3000);
        }

    }


}
