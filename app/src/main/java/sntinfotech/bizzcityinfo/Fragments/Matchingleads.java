package sntinfotech.bizzcityinfo.Fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import sntinfotech.bizzcityinfo.BuildConfig;
import sntinfotech.bizzcityinfo.Login;
import sntinfotech.bizzcityinfo.MainActivity;
import sntinfotech.bizzcityinfo.R;
import sntinfotech.bizzcityinfo.Splash;
import sntinfotech.bizzcityinfo.Utils.Api;
import sntinfotech.bizzcityinfo.Utils.MyPrefrences;
import sntinfotech.bizzcityinfo.Utils.Util;
import sntinfotech.bizzcityinfo.Utils.VersionChecker;
import sntinfotech.bizzcityinfo.connection.JSONParser;

/**
 * A simple {@link Fragment} subclass.
 */
public class Matchingleads extends Fragment {


    public Matchingleads() {
        // Required empty public constructor
    }

    JSONArray jsonArray;
    Dialog  dialog;
    ListView gridview;
    Adapter adapter;
    List<HashMap<String,String>> DataList;
    public  static int num;
    TextView textView;
    AdView adView;
    String latestVersion;
    String ver;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_matchingleads, container, false);

        gridview=(ListView) view.findViewById(R.id.gridview);
        textView=(TextView)view.findViewById(R.id.textView);


//        adView = (AdView) view.findViewById(R.id.search_ad_view);
//        AdRequest adRequest = new AdRequest.Builder().build();
//
//        adView.loadAd(adRequest);


//        VersionChecker versionChecker = new VersionChecker();
//        try {
//            latestVersion = versionChecker.execute().get();
//            Log.d("fsdgfsdgfsfgfgdfgfdgsd1",latestVersion);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }

        try {
            PackageInfo pInfo = getActivity().getApplicationContext().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            ver = pInfo.versionName;
            String ver2 = String.valueOf(pInfo.versionCode);

            Log.d("fsdgfsdgfsfgfgdfgfdgsd2",ver);
            // Log.d("fsdgsdgdfgdfh",ver2);


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        DataList =new ArrayList<>();
        new  MatchingLeads(getActivity()).execute();
        getActivity().setTitle("Matching Leads");
        return view;
    }

    private class MatchingLeads extends AsyncTask<String, Void, String> {
        Context context;
        public MatchingLeads(Context context) {
            this.context = context;
            dialog=new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Util.showPgDialog(dialog);
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map=new HashMap<>();

            map.put("function","leadBySubCategory");
            map.put("subCategoryId", MyPrefrences.getSCatID(getActivity()));
            map.put("user_id", MyPrefrences.getUserID(getActivity()));

            Log.d("dfgdfhgfghfgqwwer",MyPrefrences.getSCatID(getActivity()));
            Log.d("fgdfgdfhgd",MyPrefrences.getUserID(getActivity()));

            JSONParser jsonParser=new JSONParser();
            String result =jsonParser.makeHttpRequest(Api.Login,"GET",map);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.e("response", ": " + s);
            Util.cancelPgDialog(dialog);
            try {
                final JSONObject jsonObject = new JSONObject(s);
                if (jsonObject != null) {
                    if (jsonObject.optString("status").equalsIgnoreCase("success")) {


                        gridview.setVisibility(View.VISIBLE);
                        textView.setVisibility(View.GONE);

                        jsonArray=jsonObject.getJSONArray("message");
                        MainActivity.textview.setText(""+jsonArray.length());
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);

                            num=jsonArray.length();
                            HashMap<String,String> map=new HashMap<>();
                            map.put("id",jsonObject1.optString("id").toString());
                            map.put("requirement",jsonObject1.optString("requirement").toString());
                            map.put("uname",jsonObject1.optString("uname").toString());
                            map.put("city",jsonObject1.optString("city").toString());
                            map.put("city",jsonObject1.optString("city").toString());
                            map.put("point",jsonObject1.optString("point").toString());
                            map.put("green_lock",jsonObject1.optString("green_lock").toString());
                            map.put("red_lock",jsonObject1.optString("red_lock").toString());
                            map.put("total_member_use",jsonObject1.optString("total_member_use").toString());
                            map.put("posted_date",jsonObject1.optString("posted_date").toString());
                            map.put("subcategory",jsonObject1.optString("subcategory").toString());

                            DataList.add(map);

                            adapter=new Adapter();
                            gridview.setAdapter(adapter);
                        }


//                        if (Double.parseDouble(ver)>Double.parseDouble(latestVersion)){
//
//                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                            builder.setMessage("A newer version of BizzCityInfo is available. Would you like to update?")
//                                    .setCancelable(false)
//                                    .setPositiveButton(Html.fromHtml(getResources().getString(R.string.update1)), new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int id) {
//
//                                            String url = "https://play.google.com/store/apps/details?id=sntinfotech.bizzcityinfo&hl=en";
//                                            Intent i = new Intent(Intent.ACTION_VIEW);
//                                            i.setData(Uri.parse(url));
//                                            startActivity(i);
//                                        }
//                                    })
//                                    .setNegativeButton(Html.fromHtml("<font color='#FF7F27'>NO, THANKS</font>"), new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int id) {
//                                            //  Action for 'NO' Button
//                                            dialog.cancel();
//                                        }
//                                    });
//
//                            AlertDialog alert = builder.create();
//                            alert.setTitle("BizzCityInfo");
//                            alert.show();
//
//                            Button bq = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
//                            bq.setTextColor(Color.parseColor("#026db4"));
//                            Button bq1 = alert.getButton(DialogInterface.BUTTON_POSITIVE);
//                            bq1.setTextColor(Color.parseColor("#026db4"));
//
//                        }


                    }
                    else {
                        //Util.errorDialog(context,jsonObject.optString("message"));

                        gridview.setVisibility(View.GONE);
                        textView.setVisibility(View.VISIBLE);
                        textView.setText(jsonObject.optString("message").toString());

                    }
                }
            } catch (JSONException e) {
                Util.errorDialog(context,"Please connect to the Internet...");
                e.printStackTrace();
            }
        }

    }
    class Adapter extends BaseAdapter {
        TextView points,require,name,address,postedDate,subCat,leadNo;
        LayoutInflater inflater;
        ImageView lock1,lock2,lock3,lock4,lock5;
        LinearLayout qImage1;
        Adapter(){
            inflater=(LayoutInflater)getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return DataList.size();
        }

        @Override
        public Object getItem(int position) {
            return DataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.custonlistview, parent, false);
            }
            require=(TextView)convertView.findViewById(R.id.require);
            name=(TextView)convertView.findViewById(R.id.name);
            address=(TextView)convertView.findViewById(R.id.address);
            points=(TextView)convertView.findViewById(R.id.points);
            postedDate=(TextView)convertView.findViewById(R.id.postedDate);
            subCat=(TextView)convertView.findViewById(R.id.subCat);
            leadNo=(TextView)convertView.findViewById(R.id.leadNo);
            qImage1=(LinearLayout)convertView.findViewById(R.id.qImage1);

            lock1=(ImageView) convertView.findViewById(R.id.lock1);
            lock2=(ImageView) convertView.findViewById(R.id.lock2);
            lock3=(ImageView) convertView.findViewById(R.id.lock3);
            lock4=(ImageView) convertView.findViewById(R.id.lock4);
            lock5=(ImageView) convertView.findViewById(R.id.lock5);

            String insertDate = DataList.get(position).get("posted_date");
            String[] items1 = insertDate.split(" ");
            String d1=items1[0];
            String m1=items1[1];
            String[] items2 = d1.split("-");
            String d =items2[0];
            String m =items2[1];
            String y =items2[2];



            require.setText(DataList.get(position).get("requirement"));
            name.setText(DataList.get(position).get("uname"));
            address.setText(DataList.get(position).get("city"));
            points.setText(DataList.get(position).get("point"));
            postedDate.setText(y+"-"+m+"-"+d  +"    "+m1 );
            subCat.setText(DataList.get(position).get("subcategory"));
            leadNo.setText("Lead No # "+DataList.get(position).get("id"));

            int green = Integer.parseInt(DataList.get(position).get("green_lock"));
            int total = Integer.parseInt(DataList.get(position).get("total_member_use"));
//            int red = Integer.parseInt(DataList.get(position).get("red_lock"));

            qImage1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Util.showInfoAlertDialog2(getActivity(), "Help", "");
                }
            });
            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    if (Integer.parseInt(DataList.get(i).get("green_lock"))<=0){
                        Util.errorDialog(getActivity(),"This slot is full !");
                    }
                    else {
                        Fragment fragment = new MatchingLeadsDetails();
                        Bundle bundle = new Bundle();
                        try {
                            bundle.putString("leads", jsonArray.get(i).toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        FragmentManager manager = getActivity().getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        fragment.setArguments(bundle);
                        transaction.replace(R.id.container, fragment).addToBackStack(null).commit();
                    }
                }
            });

            if (total==1){
                lock1.setVisibility(View.VISIBLE);
                lock2.setVisibility(View.GONE);
                lock3.setVisibility(View.GONE);
                lock4.setVisibility(View.GONE);
                lock5.setVisibility(View.GONE);
            }
            else if (total==2){
                lock1.setVisibility(View.VISIBLE);
                lock2.setVisibility(View.VISIBLE);
                lock3.setVisibility(View.GONE);
                lock4.setVisibility(View.GONE);
                lock5.setVisibility(View.GONE);
            }
            else if (total==3){
                lock1.setVisibility(View.VISIBLE);
                lock2.setVisibility(View.VISIBLE);
                lock3.setVisibility(View.VISIBLE);
                lock4.setVisibility(View.GONE);
                lock5.setVisibility(View.GONE);
            }else if (total==4){
                lock1.setVisibility(View.VISIBLE);
                lock2.setVisibility(View.VISIBLE);
                lock3.setVisibility(View.VISIBLE);
                lock4.setVisibility(View.VISIBLE);
                lock5.setVisibility(View.GONE);
            }
            else if (total==5){
                lock1.setVisibility(View.VISIBLE);
                lock2.setVisibility(View.VISIBLE);
                lock3.setVisibility(View.VISIBLE);
                lock4.setVisibility(View.VISIBLE);
                lock5.setVisibility(View.VISIBLE);
            }



            if (green==1){
                lock1.setImageResource(R.drawable.unlock);
                lock2.setImageResource(R.drawable.lock);
                lock3.setImageResource(R.drawable.lock);
                lock4.setImageResource(R.drawable.lock);
                lock5.setImageResource(R.drawable.lock);
            }
            else if (green==2){
                lock1.setImageResource(R.drawable.unlock);
                lock2.setImageResource(R.drawable.unlock);
                lock3.setImageResource(R.drawable.lock);
                lock4.setImageResource(R.drawable.lock);
                lock5.setImageResource(R.drawable.lock);
            }
            else if (green==3){
                lock1.setImageResource(R.drawable.unlock);
                lock2.setImageResource(R.drawable.unlock);
                lock3.setImageResource(R.drawable.unlock);
                lock4.setImageResource(R.drawable.lock);
                lock5.setImageResource(R.drawable.lock);
            }
            else if (green==4){
                lock1.setImageResource(R.drawable.unlock);
                lock2.setImageResource(R.drawable.unlock);
                lock3.setImageResource(R.drawable.unlock);
                lock4.setImageResource(R.drawable.unlock);
                lock5.setImageResource(R.drawable.lock);
            }
            else if (green==5){
                lock1.setImageResource(R.drawable.unlock);
                lock2.setImageResource(R.drawable.unlock);
                lock3.setImageResource(R.drawable.unlock);
                lock4.setImageResource(R.drawable.unlock);
                lock5.setImageResource(R.drawable.unlock);
            }
            else if (green<=0){
                lock1.setImageResource(R.drawable.lock);
                lock2.setImageResource(R.drawable.lock);
                lock3.setImageResource(R.drawable.lock);
                lock4.setImageResource(R.drawable.lock);
                lock5.setImageResource(R.drawable.lock);
            }





//            imageLoader = AppController.getInstance().getImageLoader();
//
//            name.setText(DataList.get(position).getName().toString());
//            image.setImageUrl(DataList.get(position).getDesc().toLowerCase(),imageLoader);

            return convertView;
        }
    }

//    public class VersionChecker extends AsyncTask<String, String, String> {
//
//        String newVersion;
//
//        @Override
//        protected String doInBackground(String... params) {
//
//
//            Log.d("",getActivity().getPackageName());
//            Log.d("fdgdfgdfgdfhdfh", BuildConfig.APPLICATION_ID);
//            try {
//                Log.d("gsdfggdf","true");
//                newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + "net.one97.paytm" + "&hl=en")
//                        .timeout(30000)
//                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
//                        .referrer("http://www.google.com")
//                        .get()
//                        .select("div[itemprop=softwareVersion]")
//                        .first()
//                        .ownText();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            return newVersion;
//        }
//
//
//
//    }



}


