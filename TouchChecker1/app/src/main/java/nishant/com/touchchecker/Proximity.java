package nishant.com.touchchecker;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;



public class Proximity extends AppCompatActivity implements SensorEventListener{

    private SensorManager sensorManager;

TextView t,k;
    View v;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximity);
        t=(TextView)findViewById(R.id.textView8);
        k=(TextView)findViewById(R.id.textViewValues);
         sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
         v=findViewById(R.id.r1);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if (sensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY)
        {
            getProximity(sensorEvent);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i)
    {

    }
    private void getProximity(SensorEvent event)
    {

        float[] values = event.values;
        float x= values[0];
        float y= values[0];
        if(x>4)
        {
            t.setText("FAR" );
            k.setText(""+Math.random()*x);
            v.setBackgroundColor(Color.BLACK);
        }
        if(y<=4)
        {
            t.setText("NEAR");
            k.setText(""+Math.random()*x);
            v.setBackgroundColor(Color.BLACK);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        // register this class as a listener for the orientation and
        // accelerometer sensors
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // unregister listener
        super.onPause();
        sensorManager.unregisterListener(this);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();


        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }

}
