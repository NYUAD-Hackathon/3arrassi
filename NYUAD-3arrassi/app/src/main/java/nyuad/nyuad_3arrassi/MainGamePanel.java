package nyuad.nyuad_3arrassi;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.concurrent.TimeUnit;
import android.os.CountDownTimer;

import java.util.Stack;

/**
 * Created by Kong on 4/11/2015.
 */

public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = MainGamePanel.class.getSimpleName();
    MyAccelerometer accelerometer;
    private MainThread thread;

    private int gameTime = 0;
    private int currentWord = 0;
    private String countdownTimer = "";

    private String[] sampleWords = {"English1", "English2", "English3", "English4", "English5", "English6"};


    public MainGamePanel(Context context) {
        super(context);
        // adding the callback (this) to the surface holder to intercept events
        getHolder().addCallback(this);

        // create the game loop thread
        thread = new MainThread(getHolder(), this);
        accelerometer = new MyAccelerometer(context, this);

        // make the GamePanel focusable so it can handle events
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // at this point the surface is created and
        // we can safely start the game loop
        setWillNotDraw(false);
        accelerometer.registerListener();
        new CountDownTimer(60000, 300) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {

                countdownTimer = ""+String.format("%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
            }

            public void onFinish() {
                countdownTimer = "done!";
            }
        }.start();

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "Surface is being destroyed");
        // tell the thread to shut down and wait for it to finish
        // this is a clean shutdown
        boolean retry = true;
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                // try again shutting down the thread
            }
        }
        accelerometer.unregisterListener();
        Log.d(TAG, "Thread was shut down cleanly");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (event.getY() > getHeight() - 50) {
                thread.setRunning(false);
                ((Activity)getContext()).finish();
            } else {
                Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
            }
        }
        return super.onTouchEvent(event);
    }

    void nextWord(){
        currentWord++;
        if (currentWord > sampleWords.length - 1){
            currentWord = 0;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

    }

    public void update(){
        gameTime++;
        //Log.d(TAG, "Game time shenanigans: " + gameTime);
    }

    public void render(Canvas canvas){
        canvas.drawColor(Color.BLUE);
        Paint textPaint = new Paint();
        String sampleText = sampleWords[currentWord];
        textPaint.setARGB(200, 255, 255, 255);
        textPaint.setTextSize(300);
        int xPos = (int) ((canvas.getWidth() - textPaint.measureText(sampleWords[currentWord], 0, sampleWords[currentWord].length())) / 2.0f);
        int yPos = (int) ((canvas.getHeight() / 2) - ((textPaint.descent() + textPaint.ascent()) / 2)) ;
        canvas.drawText(sampleWords[currentWord], xPos, yPos, textPaint);

        textPaint.setARGB(200, 255, 255, 255);
        textPaint.setTextSize(100);
        Log.d(TAG, countdownTimer);
        xPos = (int) ((canvas.getWidth() - textPaint.measureText(countdownTimer, 0, countdownTimer.length())) / 2.0f);
        yPos = (int) ((canvas.getHeight() / 1.3) - ((textPaint.descent() + textPaint.ascent()) / 2)) ;
        canvas.drawText(countdownTimer, xPos, yPos, textPaint);
    }

}