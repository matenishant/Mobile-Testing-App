package nishant.com.touchchecker;


import android.content.Intent;

import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;




public class Dimming extends AppCompatActivity

{

   Button b4;
    static int brightness=0;int mode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dimming);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);

               startActivity(intent);

            }
        }
     /*   int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_SETTINGS);

if(permissionCheck!=0)
{
    Toast.makeText(this, "permission not granted", Toast.LENGTH_SHORT).show();
}*/

            mode = -1;
            try {

                brightness = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);  //returns integer value 0-255

                mode = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE); //this will return integer (0 or 1)
            } catch (Exception e) {
                e.printStackTrace();
            }
            b4 = (Button) findViewById(R.id.buttonBACK);

            if (mode == 1) {
                Settings.System.putInt(getContentResolver(),
                        Settings.System.SCREEN_BRIGHTNESS_MODE,
                        Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            }


            b4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, brightness);
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                }
            });


        //}
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }

    public void onPress(View v)
    {
        if(v.getId()==R.id.buttonMIN)
        {

            Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 4);

        }
        else if(v.getId()==R.id.buttonMED)
        {

            Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 122);
        }
        else if(v.getId()==R.id.buttonMAX)
        {

            Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 255);
        }


    }




}