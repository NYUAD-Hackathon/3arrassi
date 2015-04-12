package nyuad.nyuad_3arrassi;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


public class GameOverActivity extends Activity {
    private static final String TAG = GameOverActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_game_over);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        TextView scoreView = (TextView) findViewById(R.id.score);
        TextView commentView = (TextView) findViewById(R.id.comment);

        Intent i = getIntent();
        String score = i.getStringExtra("score");
        int iScore = Integer.parseInt(score);
        scoreView.setText(score);

        switch(iScore){
            case 1:
                commentView.setText("You only got one? Practice more");
                break;
            case 2:
                commentView.setText("Two's okay.");
                break;
            case 3:
                commentView.setText("Three? Almost there!");
                break;
            case 4:
                commentView.setText("You're not that bad at this.");
                break;
            case 5:
                commentView.setText("You got five! Good job!");
                break;
            default:
                commentView.setText("You're a decent speaker!");
                break;
        }

        Button mainMenu = (Button) findViewById(R.id.mainmenu);
        mainMenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent nextScreen = new Intent(getApplicationContext(), CategoryActivity.class);
                // starting new activity
                nextScreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(nextScreen);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        Intent nextScreen = new Intent(getApplicationContext(), CategoryActivity.class);
        nextScreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(nextScreen);
        finish();
    }
}
