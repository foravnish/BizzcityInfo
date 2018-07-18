package sntinfotech.bizzcityinfo.Utils;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;

import java.io.IOException;

import sntinfotech.bizzcityinfo.BuildConfig;

/**
 * Created by Andriod Avnish on 04-Apr-18.
 */

public class VersionChecker  extends AsyncTask<String, String, String> {

    String newVersion=null;

    @Override
    protected String doInBackground(String... params) {


        //Log.d("",getActivity().getPackageName());
        Log.d("fdgdfgdfgdfhdfh", BuildConfig.APPLICATION_ID);
        try {
            Log.d("gsdfggdf", "true");
            newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + "net.one97.paytm" + "&hl=en")
                    .timeout(30000)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com")
                    .get()
                    .select("div[itemprop=softwareVersion]")
                    .first()
                    .ownText();
            return newVersion;
        } catch (IOException e) {
            e.printStackTrace();
            return newVersion;
        }


    }


}
