package nishant.com.touchchecker;


import android.content.Context;
import android.content.Intent;


import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;

import android.net.wifi.WifiManager;

import android.os.Vibrator;
import android.provider.MediaStore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuItem;

import android.view.View;

import android.widget.Button;

import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;




public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button red, green, blue, syst, vibrate, dim, camera, touch, speaker, subKey, sensor ,thumb,record,flash;
ToggleButton wifi;
    WifiManager wifiManager;
    MediaPlayer mp;
    int flag = 0;
        boolean check=false;
    MediaPlayer mMediaPlayer;
    long lastUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

lastUpdate=System.currentTimeMillis();
        mMediaPlayer= MediaPlayer.create(getApplicationContext(), R.raw.button_click_new);

        red = (Button) findViewById(R.id.buttonRed);
        green = (Button) findViewById(R.id.buttonGreen);
        blue = (Button) findViewById(R.id.buttonBlue);
        syst = (Button) findViewById(R.id.buttonSys);
        vibrate = (Button) findViewById(R.id.buttonVibrate);
        dim = (Button) findViewById(R.id.buttonDim);

        touch = (Button) findViewById(R.id.buttonTch);
        speaker = (Button) findViewById(R.id.buttonSpeaker);
        subKey = (Button) findViewById(R.id.buttontsub);
        sensor = (Button) findViewById(R.id.buttonSen);
        camera = (Button) findViewById(R.id.buttoncamera);
        thumb=(Button)findViewById(R.id.buttonThumb);
        record=(Button)findViewById(R.id.buttonRecord);
        flash=(Button)findViewById(R.id.buttonFlash);
        red.setOnClickListener(this);
        green.setOnClickListener(this);
        blue.setOnClickListener(this);
        syst.setOnClickListener(this);
        vibrate.setOnClickListener(this);
        dim.setOnClickListener(this);
        thumb.setOnClickListener(this);
        record.setOnClickListener(this);
        flash.setOnClickListener(this);
        touch.setOnClickListener(this);
        speaker.setOnClickListener(this);
        subKey.setOnClickListener(this);

        wifi=(ToggleButton)findViewById(R.id.buttonWifi);
        wifiManager= (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifi.setChecked(wifiManager.isWifiEnabled());

        wifi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                mMediaPlayer.start();

                wifiManager.setWifiEnabled(isChecked);
                Toast.makeText(getApplicationContext(),"Wifi Is :"+isChecked, Toast.LENGTH_SHORT).show();

            }
        });



        sensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMediaPlayer.start();
                registerForContextMenu(sensor);
                openContextMenu(sensor);
            }
        });


        camera.setOnClickListener(this);


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);

        if (v.getId() == R.id.buttonSen) {
            menu.setHeaderTitle("Select");
            menu.add(0, v.getId(), 0, "Proximity");
            menu.add(0, v.getId(), 1, "Accelerometer");

        }




    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        String title = item.getTitle().toString();
        if (title.equals("Proximity")) {
            Intent i = new Intent(this, Proximity.class);
            startActivity(i);
            finish();
        }
        if (title == "Accelerometer")
        {
            Intent i = new Intent(this, TiltBall.class);
            startActivity(i);

        }


        return super.onContextItemSelected(item);
    }


    @Override
    public void onClick(View v)
    {


        if (v.getId() == R.id.buttonRed)
        {
            mMediaPlayer.start();

            Intent i = new Intent(this, Red.class);
            startActivity(i);
            finish();
        }

        if (v.getId() == R.id.buttonGreen)
        {
            mMediaPlayer.start();

            Intent i = new Intent(this, Green.class);
            startActivity(i);
            finish();

        }


        if (v.getId() == R.id.buttonBlue)
        {
            mMediaPlayer.start();
            Intent i = new Intent(this, Blue.class);
            startActivity(i);
            finish();

        }
        if (v.getId() == R.id.buttonSys)
        {
            mMediaPlayer.start();
            Intent i = new Intent(this, Systeminfo.class);
            startActivity(i);
            finish();

        }

        if (v.getId() == R.id.buttonVibrate)
        {
            mMediaPlayer.start();
            Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibe.vibrate(2000);

        }



        if (v.getId()==R.id.buttonSpeaker)
        {
            mMediaPlayer.start();

            if (flag == 0) {

                mp = MediaPlayer.create(getApplicationContext(), R.raw.my);
                mp.start();
                if(mp.isPlaying())
                {
                Toast.makeText(this, "Speaker is perfectly fine", Toast.LENGTH_SHORT).show();
                flag++;
                }
                else
                {
                    Toast.makeText(this, "Speaker is not working", Toast.LENGTH_SHORT).show();
                }

            } else {
                mp.stop();
                mp.release();

                flag = 0;

            }

        }

            if(v.getId()==R.id.buttonDim)
            {
                mMediaPlayer.start();
                Intent i= new Intent(this,Dimming.class);
                startActivity(i);
                finish();

            }

            if(v.getId()==R.id.buttonTch)
            {
                mMediaPlayer.start();
                Intent i= new Intent(this,Touch1.class);
                startActivity(i);
                finish();
            }


            if(v.getId()==R.id.buttontsub)
            {
                mMediaPlayer.start();
                Intent i= new Intent(this,Subkey.class);
                startActivity(i);
                finish();
            }


            if(v.getId()==R.id.buttonThumb)
            {
                mMediaPlayer.start();
                Intent i= new Intent(this,ThumbImpression.class);
                startActivity(i);
                finish();
            }


            if(v.getId()==R.id.buttonFlash)
            {
                mMediaPlayer.start();
                Intent i= new Intent(this,Flashlight.class);
                startActivity(i);
                finish();
            }

            if(v.getId()==R.id.buttonRecord)
            {
                mMediaPlayer.start();
                Intent i= new Intent(this,RecordPlay.class);
                startActivity(i);
                finish();
            }
            if(v.getId()==R.id.buttoncamera){
                mMediaPlayer.start();
                Intent i = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
                startActivity(i);

            }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long actualTime = System.currentTimeMillis();

            if ((actualTime - lastUpdate) < 1000) {
                finish();
            }
            else{Toast.makeText(this, "Press again to Exit", Toast.LENGTH_SHORT).show();
                lastUpdate = actualTime;}
            return true;

        }
        else
            return false;
    }
}
