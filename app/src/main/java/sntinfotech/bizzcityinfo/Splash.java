package sntinfotech.bizzcityinfo;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import sntinfotech.bizzcityinfo.Utils.MyPrefrences;

public class Splash extends AppCompatActivity {

    Button conti;
    private final int SPLASH_DISPLAY_LENGTH = 1000*3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        conti=(Button)findViewById(R.id.conti);
        TextView version=(TextView)findViewById(R.id.version);


        try {
            PackageInfo pInfo = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0);
            String ver = pInfo.versionName;
            String ver2 = String.valueOf(pInfo.versionCode);

            Log.d("fsdgsdgdfgdfh",ver);
            Log.d("fsdgsdgdfgdfh",ver2);

            version.setText("Version "+ver);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {


                /* Create an Intent that will start the Menu-Activity. */
//                Intent mainIntent = new Intent(Splash.this,MainActivity.class);
//                Splash.this.startActivity(mainIntent);
//                Splash.this.finish();

                if (MyPrefrences.getUserLogin(Splash.this)==true){
                    Intent intent =new Intent(Splash.this, MainActivity.class);
                    intent.putExtra("type","0");
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent = new Intent(Splash.this, Login.class);
                    startActivity(intent);
                    finish();
                }


            }
        }, SPLASH_DISPLAY_LENGTH);
    }

}

