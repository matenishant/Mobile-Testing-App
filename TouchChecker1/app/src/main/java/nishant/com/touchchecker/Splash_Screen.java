package nishant.com.touchchecker;

import android.content.Intent;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;



public class Splash_Screen extends AppCompatActivity  implements Runnable
{
TextView tv;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);
tv=(TextView)findViewById(R.id.textSplash);
        img=(ImageView)findViewById(R.id.imageSplash);
        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.splash_anim);
img.startAnimation(animation);
        Handler h=new Handler();
        h.postDelayed( this,10000);



    }

    @Override
    public void run()
    {
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
        finish();
    }
}
