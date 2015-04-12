package nyuad.nyuad_3arrassi;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class CategoryActivity extends Activity {

    public static int category = 1;
    private static final String TAG = CategoryActivity.class.getSimpleName();
    private Boolean exit = false;
    public static ArrayList<Word> wordList = new ArrayList<Word>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_category);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new DownloadWebpageTask(new AsyncResult() {
            @Override
            public void onResult(JSONObject object) {
                processJson(object);
            }
        }).execute("https://www.kimonolabs.com/api/d5uktwtc?apikey=97f8fea63f458e384d7e1c819c72c67a");


        Button category1 = (Button) findViewById(R.id.category1);

        //Listening to button event
        category1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                //Starting a new Intent
                Intent nextScreen = new Intent(getApplicationContext(), GameActivity.class);
                category = 1;
                // starting new activity
                startActivity(nextScreen);

            }
        });

        Button category2 = (Button) findViewById(R.id.category2);

        //Listening to button event
        category2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                //Starting a new Intent
                Intent nextScreen = new Intent(getApplicationContext(), GameActivity.class);
                category = 2;
                // starting new activity
                startActivity(nextScreen);

            }
        });

        Button category3 = (Button) findViewById(R.id.category3);

        //Listening to button event
        category3.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                //Starting a new Intent
                Intent nextScreen = new Intent(getApplicationContext(), GameActivity.class);
                category = 3;
                // starting new activity
                startActivity(nextScreen);

            }
        });

        Button category4 = (Button) findViewById(R.id.category4);

        //Listening to button event
        category4.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                //Starting a new Intent
                Intent nextScreen = new Intent(getApplicationContext(), GameActivity.class);
                category = 4;
                // starting new activity
                startActivity(nextScreen);

            }
        });

        Button category5 = (Button) findViewById(R.id.category5);

        //Listening to button event
        category5.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                //Starting a new Intent
                Intent nextScreen = new Intent(getApplicationContext(), GameActivity.class);
                category = 5;
                // starting new activity
                startActivity(nextScreen);

            }
        });

        Button category6 = (Button) findViewById(R.id.category6);

        //Listening to button event
        category6.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                //Starting a new Intent
                Intent nextScreen = new Intent(getApplicationContext(), GameActivity.class);
                category = 6;
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
                String sCategory = rows.getJSONObject(r).getString("category");
                int category = 0;
                if (sCategory.equals("Animal")){
                    category = 1;
                }
                if (sCategory.equals("Food")){
                    category = 2;
                }
                if (sCategory.equals("Countries")){
                    category = 3;
                }
                if (sCategory.equals("Activities")){
                    category = 4;
                }
                if (sCategory.equals("Holidays")){
                    category = 5;
                }
                if (sCategory.equals("Hackathon")){
                    category = 6;
                }

                String englishPron = rows.getJSONObject(r).getString("englishPron");
                String englishWord = rows.getJSONObject(r).getString("englishWord");

                wordList.add(new Word(category, arabWord, englishWord, arabPron, englishPron));
                Log.d(TAG, Integer.toString(category));
            }


        } catch (JSONException e) {
            //e.printStackTrace();
        }
    }

}
