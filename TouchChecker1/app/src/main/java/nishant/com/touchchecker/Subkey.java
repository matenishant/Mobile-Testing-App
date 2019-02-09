package nishant.com.touchchecker;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;



public class Subkey extends AppCompatActivity
{
    View v;
    TextView tv,tv1;

int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subkey);
        tv=(TextView)findViewById(R.id.textView9);
        tv1=(TextView)findViewById(R.id.textViewResult);
         v= findViewById(R.id.subkey);


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {

        if(keyCode==KeyEvent.KEYCODE_VOLUME_DOWN)
        {

            v.setBackgroundColor(Color.BLACK);
            tv1.setText("Volume Down key working"+keyCode);

            //Toast.makeText(getApplicationContext(),"key UP working"+keyCode,Toast.LENGTH_SHORT).show();
            return true;

        }

    else if(keyCode==KeyEvent.KEYCODE_VOLUME_UP)
        {

            v.setBackgroundColor(Color.BLACK);
            tv1.setText("Volume Up key working"+keyCode);
            return  true;

        }


        else if(keyCode==KeyEvent.KEYCODE_POWER)
        {

            v.setBackgroundColor(Color.BLACK);
            tv1.setText("power key working"+keyCode);
            return  true;

        }


        else if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            flag+=1;
            if(flag<2) {
                v.setBackgroundColor(Color.BLACK);
                tv1.setText("Back key working" + keyCode);
            }
            if(flag==2) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        }





        else
        {

        tv1.setText(keyCode+" not working");
            return  false;
        }


return true;

    }




}
