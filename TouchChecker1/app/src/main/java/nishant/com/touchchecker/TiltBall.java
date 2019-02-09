package nishant.com.touchchecker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;



import java.util.Timer;
import java.util.TimerTask;

public class TiltBall extends AppCompatActivity {
    BallView mBallView = null;
    Handler RedrawHandler = new Handler(); //so redraw occurs in main thread
    Timer mTmr = null;
    TimerTask mTsk = null;
    int mScrWidth, mScrHeight;
    android.graphics.PointF mBallPos, mBallSpd;

    TextView x,y,z;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        requestWindowFeature(Window.FEATURE_NO_TITLE); //hide title bar
        //set app to full screen and keep screen on
        getWindow().setFlags(0x000000, WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tilt_ball);
        final FrameLayout mainView =
                (android.widget.FrameLayout) findViewById(R.id.main_view);

        //get screen dimensions
        Display display = getWindowManager().getDefaultDisplay();
        mScrWidth = display.getWidth();
        mScrHeight = display.getHeight();
        mBallPos = new android.graphics.PointF();
        mBallSpd = new android.graphics.PointF();

//create variables for ball position and speed
        mBallPos.x = mScrWidth / 2;
        mBallPos.y = mScrHeight / 2;
        mBallSpd.x = 0;
        mBallSpd.y = 0;

        mBallView = new BallView(this, mBallPos.x, mBallPos.y, 25);

        mainView.addView(mBallView); //add ball to main screen
        mBallView.invalidate(); //call onDraw in BallView
        x = (TextView) findViewById(R.id.textView5);
        y = (TextView) findViewById(R.id.textView2);
        z = (TextView) findViewById(R.id.textView6);
//listener for accelerometer, use anonymous class for simplicity
        ((SensorManager) getSystemService(Context.SENSOR_SERVICE)).registerListener(
                new SensorEventListener() {
                    @Override
                    public void onSensorChanged(SensorEvent event) {
                        //set ball speed based on phone tilt (ignore Z axis)
                        mBallSpd.x = -event.values[0];
                        mBallSpd.y = event.values[1];
                        getAccelerometer(event);
                        //timer event will redraw ball
                    }

                    @Override
                    public void onAccuracyChanged(Sensor sensor, int accuracy) {
                    } //ignore
                },
                ((SensorManager) getSystemService(Context.SENSOR_SERVICE))
                        .getSensorList(Sensor.TYPE_ACCELEROMETER).get(0),
                SensorManager.SENSOR_DELAY_NORMAL);
        //listener for touch event
        mainView.setOnTouchListener(new android.view.View.OnTouchListener() {
            public boolean onTouch(android.view.View v, android.view.MotionEvent e) {
                //set ball position based on screen touch
                mBallPos.x = e.getX();
                mBallPos.y = e.getY();
                //timer event will redraw ball
                return true;
            }
        });
    }
    private void getAccelerometer(SensorEvent event) {
        float[] values = event.values;
        // Movement
        float x1 = values[0];
        float y1 = values[1];
        float z1 = values[2];
        x.setText(String.valueOf(x1));
        y.setText(String.valueOf(y1));
        z.setText(String.valueOf(z1));


    }   @Override
    public void onPause() //app moved to background, stop background threads
    {
        mTmr.cancel(); //kill\release timer (our only background thread)
        mTmr = null;
        mTsk = null;
        super.onPause();
    }

    @Override
    public void onResume() //app moved to foreground (also occurs at app startup)
    {
        //create timer to move ball to new position
        mTmr = new Timer();
        mTsk = new TimerTask() {
            public void run() {

//if debugging with external device,
//  a log cat viewer will be needed on the device
                android.util.Log.d("TiltBall","Timer Hit - " + mBallPos.x + ":" + mBallPos.y);

//move ball based on current speed
                mBallPos.x += mBallSpd.x;
                mBallPos.y += mBallSpd.y;

//if ball goes off screen, reposition to opposite side of screen
                if (mBallPos.x > mScrWidth) mBallPos.x=0;
                if (mBallPos.y > mScrHeight) mBallPos.y=0;
                if (mBallPos.x < 0) mBallPos.x=mScrWidth;
                if (mBallPos.y < 0) mBallPos.y=mScrHeight;

//update ball class instance
                mBallView.x = mBallPos.x;
                mBallView.y = mBallPos.y;
//redraw ball. Must run in background thread to prevent thread lock.
                RedrawHandler.post(new Runnable() {
                    public void run() {
                        mBallView.invalidate();
                    }});
            }}; // TimerTask

        mTmr.schedule(mTsk,10,10); //start timer
        super.onResume();
    } // onResume

   /* @Override
    public void onDestroy() //main thread stopped
    {
        super.onDestroy();
        //wait for threads to exit before clearing app
        System.runFinalizersOnExit(true);
        //remove app from memory
        android.os.Process.killProcess(android.os.Process.myPid());
    }
*/

    //listener for config change.
    //This is called when user tilts phone enough to trigger landscape view
    //we want our app to stay in portrait view, so bypass event
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);

    }
} //TiltBallActivity


