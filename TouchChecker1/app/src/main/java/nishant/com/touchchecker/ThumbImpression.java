package nishant.com.touchchecker;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;



import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class ThumbImpression extends AppCompatActivity
{
    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;
    private KeyStore keyStore;
    private KeyGenerator keyGenerator;
    private Cipher cipher;
    private FingerprintManager.CryptoObject cryptoObject;
    private static final String KEY_NAME = "example_key";
    TextView tv;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thumb_impression);

        keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
        tv=(TextView)findViewById(R.id.textViewInfo);

        if (!keyguardManager.isKeyguardSecure()) {
            //Toast.makeText(this, "Lock screen security not enabled in Settings", Toast.LENGTH_LONG).show();
            tv.setText("Lock screen security not enabled in Settings");
            return;
        }


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            //Toast.makeText(this, "Fingerprint authentication permission not enabled", Toast.LENGTH_LONG).show();
                tv.setText("Fingerprint authentication permission not enabled");
            return;

        }


        if(!fingerprintManager.isHardwareDetected()){
            /**
             * An error message will be displayed if the device does not contain the fingerprint hardware.
             * However if you plan to implement a default authentication method,
             * you can redirect the user to a default authentication activity from here.
             * Example:
             * Intent intent = new Intent(this, DefaultAuthenticationActivity.class);
             * startActivity(intent);
             */
                tv.setText("Your Device does not have a Fingerprint Sensor");
        }



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (!fingerprintManager.hasEnrolledFingerprints())
            {

                // This happens when no fingerprints are registered.
               // Toast.makeText(this, "Register at least one fingerprint in Settings", Toast.LENGTH_LONG).show();
                tv.setText("Register at least one fingerprint in Settings");
                return;

            }

                    generateKey();

            if (cipherInit())
            {
                cryptoObject = new FingerprintManager.CryptoObject(cipher);
                FingerPrintHandler helper = new FingerPrintHandler(this);
                helper.startAuth(fingerprintManager, cryptoObject);
            }
        }






    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }

       @RequiresApi(api = Build.VERSION_CODES.M)
       protected void generateKey()
       {
                       try
                       {
                           keyStore = KeyStore.getInstance("AndroidKeyStore");
                       } catch (Exception e)
                       {
                           e.printStackTrace();
                       }


                               try {
                                        keyGenerator = KeyGenerator.getInstance(
                                           KeyProperties.KEY_ALGORITHM_AES,
                                           "AndroidKeyStore");
                                    }
                               catch (NoSuchAlgorithmException |   NoSuchProviderException e)
                               {
                                   throw new RuntimeException("Failed to get KeyGenerator instance", e);
                               }



                       try {
                           keyStore.load(null);
                           keyGenerator.init(new
                                   KeyGenParameterSpec.Builder(KEY_NAME,
                                   KeyProperties.PURPOSE_ENCRYPT |
                                           KeyProperties.PURPOSE_DECRYPT)
                                   .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                                   .setUserAuthenticationRequired(true)
                                   .setEncryptionPaddings(
                                           KeyProperties.ENCRYPTION_PADDING_PKCS7)
                                   .build());
                           keyGenerator.generateKey();
                       }
                       catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException
                               | CertificateException | IOException e)
                       {
                           throw new RuntimeException(e);
                       }



       }




    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean cipherInit()
    {
                            try
                            {
                                cipher = Cipher.getInstance(
                                        KeyProperties.KEY_ALGORITHM_AES + "/"
                                                + KeyProperties.BLOCK_MODE_CBC + "/"
                                                + KeyProperties.ENCRYPTION_PADDING_PKCS7);
                            }
                            catch (NoSuchAlgorithmException |    NoSuchPaddingException e)
                            {
                                throw new RuntimeException("Failed to get Cipher", e);
                            }

                            try
                            {
                                keyStore.load(null);
                                SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                                        null);
                                cipher.init(Cipher.ENCRYPT_MODE, key);
                                return true;
                            } catch (KeyPermanentlyInvalidatedException e) {
                                return false;
                            } catch (KeyStoreException | CertificateException
                                    | UnrecoverableKeyException | IOException
                                    | NoSuchAlgorithmException | InvalidKeyException e) {
                                throw new RuntimeException("Failed to init Cipher", e);
                            }
    }





}


