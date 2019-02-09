package nishant.com.touchchecker;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;



public class Flashlight extends AppCompatActivity {

    private CameraManager mCameraManager;
    private String mCameraId;

    ImageButton img;
    private Boolean isTorchOn;
    private MediaPlayer mp;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState)


    {
        super.onCreate(savedInstanceState);
        Log.d("FlashLightActivity", "onCreate()");
        setContentView(R.layout.activity_flashlight);
        img = (ImageButton) findViewById(R.id.btnSwitch);
        isTorchOn = false;
        Boolean isFlashAvailable = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (!isFlashAvailable) {

            AlertDialog alert = new AlertDialog.Builder(Flashlight.this)
                    .create();
            alert.setTitle("Error !!");
            alert.setMessage("Your device doesn't support flash light!");
            alert.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // closing the application
                            finish();
                            System.exit(0);
                        }
                    });
            alert.show();
            return;
        }

        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mCameraId = mCameraManager.getCameraIdList()[0];
            }
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

      img.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              try {
                  if (isTorchOn) {
                      turnOffFlashLight();
                      isTorchOn = false;
                  } else {
                      turnOnFlashLight();
                      isTorchOn = true;
                  }
              } catch (Exception e) {
                  e.printStackTrace();
              }
          }
      });

    }



            public void turnOnFlashLight()
                    {

                        try {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                            {
                                mCameraManager.setTorchMode(mCameraId, true);
                                playOnOffSound();
                                img.setImageResource(R.drawable.lightbulb);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    public void turnOffFlashLight()
                    {

                        try {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                mCameraManager.setTorchMode(mCameraId, false);
                                playOnOffSound();
                                img.setImageResource(R.drawable.lightoff);

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }



                    private void playOnOffSound(){

                        mp = MediaPlayer.create(Flashlight.this, R.raw.turn_on);
                        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                // TODO Auto-generated method stub
                                mp.release();
                            }
                        });
                        mp.start();
                    }




                        @Override
                        protected void onStop() {
                            super.onStop();
                            if(isTorchOn){
                                turnOffFlashLight();
                            }
                        }

                        @Override
                        protected void onPause() {
                            super.onPause();
                            if(isTorchOn){
                                turnOffFlashLight();
                            }
                        }

                        @Override
                        protected void onResume() {
                            super.onResume();
                            if(isTorchOn){
                                turnOnFlashLight();
                            }
                        }


    @Override
    public void onBackPressed() {
        super.onBackPressed();


        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }


}