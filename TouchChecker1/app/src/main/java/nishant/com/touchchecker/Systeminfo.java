package nishant.com.touchchecker;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



public class Systeminfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_systeminfo);
        Button button = (Button)findViewById(R.id.getSystemInfo);
        final TextView textView = (TextView)findViewById(R.id.info_display);
        ActivityManager actManager = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
        actManager.getMemoryInfo(memInfo);
        final long totalMemory = memInfo.totalMem;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText(
                        "SERIAL: " + Build.SERIAL + "\n" +
                                "MODEL: " + Build.MODEL + "\n" +
                                "ID: " + Build.ID + "\n" +
                                "Manufacture: " + Build.MANUFACTURER + "\n" +
                                "Brand: " + Build.BRAND + "\n" +
                                "Type: " + Build.TYPE + "\n" +
                                "User: " + Build.USER + "\n" +
                                "BASE: " + Build.VERSION_CODES.BASE + "\n" +
                                "INCREMENTAL: " + Build.VERSION.INCREMENTAL + "\n" +
                                "SDK:  " + Build.VERSION.SDK + "\n" +
                                "BOARD: " + Build.BOARD + "\n" +
                                "BRAND: " + Build.BRAND + "\n" +
                                "HOST: " + Build.HOST + "\n" +
                                "FINGERPRINT: " + Build.FINGERPRINT + "\n" +
                                "Version Code: " + Build.VERSION.RELEASE+ "\n" +
                                "RAM: "+totalMemory/0x100000L+"(MB)"

                );
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
