package nyuad.nyuad_3arrassi;

import android.util.Log;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by Kong on 4/11/2015.
 */

public class MainThread extends Thread {

    private static final String TAG = MainThread.class.getSimpleName();
    // desired fps
    private final static int    MAX_FPS = 30;
    // maximum number of frames to be skipped
    private final static int    MAX_FRAME_SKIPS = 5;
    // the frame period
    private final static int    FRAME_PERIOD = 1000 / MAX_FPS;

    // Surface holder that can access the physical surface
    private SurfaceHolder surfaceHolder;
    // The actual view that handles inputs
    // and draws to the surface
    private MainGamePanel gamePanel;

    // flag to hold game state
    private boolean running;

    public void setRunning(boolean running) {
        this.running = running;
    }

    public MainThread(SurfaceHolder surfaceHolder, MainGamePanel gamePanel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    public SurfaceHolder returnSurfaceHolder(){
        return surfaceHolder;
    }

    @Override
    public void run() {
        Log.d(TAG, "Starting game loop");
        Canvas c;
        long beginTime;		// the time when the cycle begun
        long timeDiff;		// the time it took for the cycle to execute
        int sleepTime;		// ms to sleep (<0 if we're behind)
        int framesSkipped;	// number of frames being skipped
        sleepTime = 0;

        while (running) {
            c = null;
            try {
                c = gamePanel.getHolder().lockCanvas();
                synchronized (gamePanel.getHolder()) {
                    beginTime = System.currentTimeMillis();
                    framesSkipped = 0;	// resetting the frames skipped
                    // update game state
                    this.gamePanel.update();
                    // render state to the screen
                    // draws the canvas on the panel
                    this.gamePanel.render(c);
                    // calculate how long did the cycle take
                    timeDiff = System.currentTimeMillis() - beginTime;
                    // calculate sleep time
                    sleepTime = (int)(FRAME_PERIOD - timeDiff);
                    //Log.d(TAG, "beginTime: " + beginTime);
                    //Log.d(TAG, "timeDiff: " + timeDiff);
                    //Log.d(TAG, "sleepTime: " + sleepTime);
                    //Log.d(TAG, "framesSkipped: " + framesSkipped);

                    if (sleepTime > 0) {
                        // if sleepTime > 0 we're OK
                        try {
                            // send the thread to sleep for a short period
                            // very useful for battery saving
                            Thread.sleep(sleepTime);
                        } catch (InterruptedException e) {}
                    }

                    while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
                        // we need to catch up
                        // update without rendering
                        this.gamePanel.update();
                        // add frame period to check if in next frame
                        sleepTime += FRAME_PERIOD;
                        framesSkipped++;
                    }
                }
            } finally {
                if (c != null) {
                    gamePanel.getHolder().unlockCanvasAndPost(c);
                }
            }
            // update game state
            // render state to the screen
        }
    }
}
