package nishant.com.touchchecker;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Toast;

/**
 * Created by user on 9/14/2017.
 */

@RequiresApi(api = Build.VERSION_CODES.M)
public class FingerPrintHandler extends FingerprintManager.AuthenticationCallback implements View.OnClickListener
{


    private CancellationSignal cancellationSignal;
    private Context appContext;

                public FingerPrintHandler(Context context)
                {
                    appContext = context;
                }




            public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject)
            {

                cancellationSignal = new CancellationSignal();

                if (ActivityCompat.checkSelfPermission(appContext,
                        Manifest.permission.USE_FINGERPRINT) !=
                        PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
            }


            @Override
            public void onAuthenticationError(int errMsgId, CharSequence errString)
            {
                Toast.makeText(appContext,"Authentication error\n" + errString,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onAuthenticationHelp(int helpMsgId,CharSequence helpString)
            {
                Toast.makeText(appContext,
                        "Authentication help\n" + helpString,
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAuthenticationFailed()
            {
                Toast.makeText(appContext,"Authentication failed.",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result)
            {

                Toast.makeText(appContext,
                        "Authentication succeeded.",
                        Toast.LENGTH_LONG).show();
            }


    @Override
    public void onClick(View v) {

    }
}
