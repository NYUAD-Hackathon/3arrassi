package nyuad.nyuad_3arrassi;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import android.os.Handler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;


public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Boolean exit = false;
    public static ArrayList<Word> wordList = new ArrayList<Word>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new DownloadWebpageTask(new AsyncResult() {
            @Override
            public void onResult(JSONObject object) {
                processJson(object);
            }
        }).execute("https://www.kimonolabs.com/api/d5uktwtc?apikey=97f8fea63f458e384d7e1c819c72c67a");


        Button btnNextScreen = (Button) findViewById(R.id.newButton);

        //Listening to button event
        btnNextScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                //Starting a new Intent
                Intent nextScreen = new Intent(getApplicationContext(), GameActivity.class);
                // starting new activity
                startActivity(nextScreen);

            }
        });

    }
    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.", Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }

    private void processJson(JSONObject object) {

        try {
            JSONArray rows = object.getJSONArray("collection1");

            for (int r = 1; r < rows.length(); ++r) {
                String arabPron = rows.getJSONObject(r).getString("arabPron");
                String arabWord = rows.getJSONObject(r).getString("arabWord");
                int category = 1;
                String englishPron = rows.getJSONObject(r).getString("englishPron");
                String englishWord = rows.getJSONObject(r).getString("englishWord");

                wordList.add(new Word(category, arabWord, englishWord, arabPron, englishPron));
            }


        } catch (JSONException e) {
            //e.printStackTrace();
        }
    }
}