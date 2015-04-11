package nyuad.nyuad_3arrassi;

/**
 * Created by Kong on 4/11/2015.
 */

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class MyAccelerometer implements SensorEventListener {

    private static final String TAG = MyAccelerometer.class.getSimpleName();
    private SensorManager sensorManager;
    private Sensor sensorAccelerometer;
    private GameActivity parent;
    private MainGamePanel aGamePanel;

    private float maximumRange;
    private int phoneState = 1;

    public MyAccelerometer(Context c, MainGamePanel GamePanel) {
        parent = (GameActivity) c;
        aGamePanel = GamePanel;
    }

    void registerListener() {
        sensorManager = (SensorManager) parent.getSystemService(Context.SENSOR_SERVICE);
        sensorAccelerometer = sensorManager.getDefaultSensor(
                Sensor.TYPE_ACCELEROMETER);

        maximumRange = sensorAccelerometer.getMaximumRange();

        sensorManager.registerListener(this,
                sensorAccelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    void unregisterListener() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // TODO Auto-generated method stub
    /*
     * event.values[0]: azimuth, rotation around the Z axis.
     * event.values[1]: pitch, rotation around the X axis.
     * event.values[2]: roll, rotation around the Y axis.
    */
        if (phoneState != 0 && (event.values[2] <= -4)){
            aGamePanel.nextWord();
            Log.d(TAG, "now screen is facing down. Value: " + event.values[2]);
            phoneState = 0;
        }
        if (phoneState != 1 && (-4 <= event.values[2] && event.values[2] <= 4)){
            Log.d(TAG, "now screen is facing middle. Value: " + event.values[2]);
            phoneState = 1;
        }
        if (phoneState != 2 && (event.values[2] >= 4)){
            Log.d(TAG, "now screen is facing up. Value: " + event.values[2]);
            phoneState = 2;
        }

        //float valueAzimuth = event.values[0];
        //float valuePitch = event.values[1];

        //aGamePanel.updateAccelerometer(valueAzimuth / maximumRange, -valuePitch / maximumRange);

        //Log.d(TAG, "event.values[2]: " + event.values[2]);



     }

}