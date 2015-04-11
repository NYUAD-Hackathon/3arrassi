package nyuad.nyuad_3arrassi;

import android.util.Log;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by Kong on 4/11/2015.
 */

public class MainThread extends Thread {

    private static final String TAG = MainThread.class.getSimpleName();

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

    @Override
    public void run() {
        long tickCount = 0L;
        Log.d(TAG, "Starting game loop");
        while (running) {
            Canvas c = null;
            tickCount++;
            gamePanel.update(tickCount);
            gamePanel.draw(c);
            // update game state
            // render state to the screen
        }
        Log.d(TAG, "Game loop executed " + tickCount + " times");
    }
}
