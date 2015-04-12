package nyuad.nyuad_3arrassi;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;


public class CategoryActivity extends Activity {

    public static int category = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_category);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

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

}
