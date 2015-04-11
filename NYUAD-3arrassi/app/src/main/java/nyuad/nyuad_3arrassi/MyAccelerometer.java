package nyuad.nyuad_3arrassi;

/**
 * Created by Kong on 4/11/2015.
 */

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class MyAccelerometer implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensorAccelerometer;
    private GameActivity parent;
    private MainGamePanel aGamePanel;

    private float maximumRange;

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

        float valueAzimuth = event.values[0];
        float valuePitch = event.values[1];
        aGamePanel.updateAccelerometer(valueAzimuth / maximumRange, -valuePitch / maximumRange);

     }

}