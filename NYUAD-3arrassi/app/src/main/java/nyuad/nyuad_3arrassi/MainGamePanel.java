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

import java.util.Stack;

/**
 * Created by Kong on 4/11/2015.
 */

public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = MainGamePanel.class.getSimpleName();
    MyAccelerometer accelerometer;
    private MainThread thread;
    //private long ctime;

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

    void updateAccelerometer(float tx, float ty){
        Log.d(TAG, "Accelerometer shenanigans: " + tx + ", " + ty );
    }


    @Override
    protected void onDraw(Canvas canvas) {
        //paint.setStyle(Paint.Style.FILL);
        canvas.drawColor(Color.BLUE);
        Paint textPaint = new Paint();
        String sampleText = "hello";
        textPaint.setARGB(200, 255, 255, 255);
        textPaint.setTextSize(300);
        int xPos = (int) ((canvas.getWidth() - textPaint.measureText(sampleText, 0, sampleText.length())) / 2.0f);
        int yPos = (int) ((canvas.getHeight() / 2) - ((textPaint.descent() + textPaint.ascent()) / 2)) ;
        canvas.drawText(sampleText, xPos, yPos, textPaint);
    }

    public void update(long tickCount){
    //    ctime = tickCount;
    }
    public void draw(Canvas canvas){
        //Log.d(TAG, "Time: " + ctime);
    }

}