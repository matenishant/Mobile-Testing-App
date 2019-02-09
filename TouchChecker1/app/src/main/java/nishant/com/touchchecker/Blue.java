package nishant.com.touchchecker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;



public class Blue extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue);
        View v=findViewById(R.id.R3);
        TextView tv=(TextView)findViewById(R.id.textBack3) ;
        Animation animation1= AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade);
        tv.startAnimation(animation1);
        tv.setVisibility(View.INVISIBLE);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();


        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }


}
