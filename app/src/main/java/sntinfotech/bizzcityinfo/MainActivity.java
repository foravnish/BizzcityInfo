package sntinfotech.bizzcityinfo;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import sntinfotech.bizzcityinfo.Fragments.Aboutus;
import sntinfotech.bizzcityinfo.Fragments.BuyPackages;
import sntinfotech.bizzcityinfo.Fragments.ChangePwd;
import sntinfotech.bizzcityinfo.Fragments.ContactUs;
import sntinfotech.bizzcityinfo.Fragments.Deshboard;
import sntinfotech.bizzcityinfo.Fragments.FAQs;
import sntinfotech.bizzcityinfo.Fragments.GSTDetails;
import sntinfotech.bizzcityinfo.Fragments.HelpDesk;
import sntinfotech.bizzcityinfo.Fragments.HowItWork;
import sntinfotech.bizzcityinfo.Fragments.Inbox;
import sntinfotech.bizzcityinfo.Fragments.Matchingleads;
import sntinfotech.bizzcityinfo.Fragments.OfferPostOption;
import sntinfotech.bizzcityinfo.Fragments.PackagesHistory;
import sntinfotech.bizzcityinfo.Fragments.PremimumPage;
import sntinfotech.bizzcityinfo.Fragments.Privacy;
import sntinfotech.bizzcityinfo.Fragments.Profile;
import sntinfotech.bizzcityinfo.Fragments.PurchasedLeads;
import sntinfotech.bizzcityinfo.Fragments.TermAndCondition;
import sntinfotech.bizzcityinfo.Fragments.TranasactionHistory;
import sntinfotech.bizzcityinfo.Utils.Api;
import sntinfotech.bizzcityinfo.Utils.MyPrefrences;
import sntinfotech.bizzcityinfo.Utils.Util;
import sntinfotech.bizzcityinfo.connection.AppController;
import sntinfotech.bizzcityinfo.connection.JSONParser;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static TextView points,refresh1;
    Dialog  dialog;
    TextView profile,buyCredit,purchased,matching,prem;
    public static TextView textview,nav_logout;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        matching=(TextView)findViewById(R.id.matching);
        purchased=(TextView)findViewById(R.id.purchased);
        buyCredit=(TextView)findViewById(R.id.buyCredit);
        profile=(TextView)findViewById(R.id.profile);



        displayFirebaseRegId();


        if (getIntent().getStringExtra("type").equalsIgnoreCase("1")){
            Fragment fragment= new PremimumPage();
            FragmentManager manager=getSupportFragmentManager();
            FragmentTransaction ft=manager.beginTransaction();
            ft.replace(R.id.container,fragment).commit();

        }
        else{
            Fragment fragment= new Deshboard();
            FragmentManager manager=getSupportFragmentManager();
            FragmentTransaction ft=manager.beginTransaction();
            ft.replace(R.id.container,fragment).commit();
        }

        matching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment;
                fragment= new Matchingleads();
                loadFragment(fragment);
            }
        });

        purchased.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment;
                fragment= new PurchasedLeads();
                loadFragment(fragment);
            }
        });

        buyCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment;
                fragment= new BuyPackages();
                loadFragment(fragment);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Fragment fragment;
//                fragment= new Profile();
//                loadFragment(fragment);
                startActivity(new Intent(MainActivity.this,ProfileAct.class));
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        TextView name=(TextView)header.findViewById(R.id.name);
        TextView emailID=(TextView)header.findViewById(R.id.emailID);
        TextView sellerId=(TextView)header.findViewById(R.id.sellerId);
        imageView=(ImageView)header.findViewById(R.id.imageView);
        prem=(TextView)header.findViewById(R.id.prem);

        name.setText(MyPrefrences.getUSENAME(getApplicationContext()));
        emailID.setText("+91 "+MyPrefrences.getMobile(getApplicationContext()));
        sellerId.setText("Seller (ID- "+MyPrefrences.getUserID(getApplicationContext())+")");


        Menu menu = navigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.nav_notification);
        View actionView = MenuItemCompat.getActionView(menuItem);
        SwitchCompat otoSwitch = (SwitchCompat)  menuItem.getActionView().findViewById(R.id.notification);

        textview = (TextView) menu.findItem(R.id.nav_matchingLeads).getActionView();
        nav_logout = (TextView) menu.findItem(R.id.nav_logout).getActionView();
//        textviewProfile = (TextView) menu.findItem(R.id.nav_profile).getActionView();

        // textview.setText("2");
//        AdView adView = (AdView) findViewById(R.id.search_ad_view);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        adView.loadAd(adRequest);


        try {
            PackageInfo pInfo = getApplicationContext().getPackageManager().getPackageInfo(getPackageName(), 0);
            String ver = pInfo.versionName;
            nav_logout.setText("Ver: "+ver);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        otoSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked) {
                    MyPrefrences.setNotiStatus(getApplicationContext(),true);
                    //Toast.makeText(MainActivity.this, MyPrefrences.getNotiStatus(MainActivity.this)+"", Toast.LENGTH_SHORT).show();
                }
                else {
                    MyPrefrences.setNotiStatus(getApplicationContext(),false);
                    //Toast.makeText(MainActivity.this, MyPrefrences.getNotiStatus(MainActivity.this)+"", Toast.LENGTH_SHORT).show();
                }

            }
        });

        Log.d("fgdgdfgdf", MyPrefrences.getCatID(getApplicationContext()).toString());

        Log.d("fgdgdfgdfgdf", String.valueOf(Matchingleads.num));



//        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
//        layoutParams.setBehavior(new BottomNavigationBehavior());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

//      NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        this.setTitle("Matching Leads");

        new ProfileApi(MainActivity.this,MyPrefrences.getUserID(MainActivity.this)).execute();

        points=(TextView)findViewById(R.id.points);
        refresh1=(TextView)findViewById(R.id.refresh1);
        refresh1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation rotation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.clockwise_refresh);
                rotation.setRepeatCount(Animation.INFINITE);
                refresh1.startAnimation(rotation);

            }
        });
        points.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation rotation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.clockwise_refresh);
                rotation.setRepeatCount(Animation.INFINITE);
                refresh1.startAnimation(rotation);

            }
        });

    }

    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        String regId = pref.getString("regId", null);

        Log.d("djfsakljf;sldkfsdk", "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId)){
            //txtRegId.setText("Firebase Reg Id: " + regId);
            new TokenUpdate(MainActivity.this,regId).execute();


        }
        else {
//            txtRegId.setText("Firebase Reg Id is not received yet!");
            Log.d("djfsakljf;sldkfsdk", "Firebase Reg Id is not received yet!");
        }
    }

    private class TokenUpdate extends AsyncTask<String, Void, String> {
        Context context;
        String fireID;
        Dialog dialog3;
        public TokenUpdate(Context context,String fireID) {
            this.context = context;
            this.fireID=fireID;
            dialog =new Dialog(MainActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            // Util.showPgDialog(MainActivity.this.dialog);
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map=new HashMap<>();

            map.put("function","updateDeviceId");
            map.put("memberId", MyPrefrences.getUserID(getApplicationContext()).toString());
            map.put("deviceId", fireID.toString());
            //map.put("password", password.toString());


            JSONParser jsonParser=new JSONParser();
            String result =jsonParser.makeHttpRequest(Api.Login,"GET",map);

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.e("response", ": " + s);
            // Util.cancelPgDialog(dialog);
            try {
                final JSONObject jsonObject = new JSONObject(s);
                if (jsonObject != null) {
                    if (jsonObject.optString("status").equalsIgnoreCase("success")) {

                    }
                    else {
                        Util.errorDialog(MainActivity.this,jsonObject.optString("message"));
                    }
                }
            } catch (JSONException e) {
                Util.cancelPgDialog(dialog);
                Util.errorDialog(MainActivity.this,"Some Error! Please try again...");
                e.printStackTrace();
            }
        }

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);



//        final Menu m = menu;
//        refreshItem = menu.findItem(R.id.action_refresh);
//        refreshItem.getActionView().setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                m.performIdentifierAction(refreshItem.getItemId(), 0);
//                rotation = AnimationUtils.loadAnimation(this, R.anim.clockwise_refresh);
//                rotation.setRepeatCount(Animation.INFINITE);
//            }
//        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_matchingLeads) {
            Fragment fragment= new Matchingleads();
            FragmentManager manager=getSupportFragmentManager();
            FragmentTransaction ft=manager.beginTransaction().addToBackStack(null);
            ft.replace(R.id.container,fragment).commit();

        } else if (id == R.id.nav_purchages) {
            Fragment fragment = new PurchasedLeads();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction().addToBackStack(null);
            ft.replace(R.id.container, fragment).commit();

        } else if (id == R.id.nav_transaction) {
            Fragment fragment = new TranasactionHistory();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction().addToBackStack(null);
            ft.replace(R.id.container, fragment).commit();
        }

        else if (id == R.id.nav_offers) {
            Fragment fragment = new OfferPostOption();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction().addToBackStack(null);
            ft.replace(R.id.container, fragment).commit();

        } else if (id == R.id.nav_premimum) {
            Fragment fragment = new PremimumPage();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction().addToBackStack(null);
            ft.replace(R.id.container, fragment).commit();

        } else if (id == R.id.nav_refer) {

            refrelFriend();
        }


        else if (id == R.id.nav_dashboard) {
            Fragment fragment = new Deshboard();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction().addToBackStack(null);
            ft.replace(R.id.container, fragment).commit();
        }

        else if (id == R.id.nav_inbox) {
            Fragment fragment = new Inbox();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction().addToBackStack(null);
            ft.replace(R.id.container, fragment).commit();

        }
        else if (id == R.id.nav_buycredit) {
            Fragment fragment = new BuyPackages();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction().addToBackStack(null);
            ft.replace(R.id.container, fragment).commit();

        } else if (id == R.id.nav_profile) {
//            Fragment fragment= new Profile();
//            FragmentManager manager=getSupportFragmentManager();
//            FragmentTransaction ft=manager.beginTransaction().addToBackStack(null);
//            ft.replace(R.id.container,fragment).commit();

            startActivity(new Intent(MainActivity.this,ProfileAct.class));

        } else if (id == R.id.nav_history) {
            Fragment fragment= new PackagesHistory();
            FragmentManager manager=getSupportFragmentManager();
            FragmentTransaction ft=manager.beginTransaction().addToBackStack(null);
            ft.replace(R.id.container,fragment).commit();

        } else if (id == R.id.nav_gst_det) {
            Fragment fragment= new GSTDetails();
            FragmentManager manager=getSupportFragmentManager();
            FragmentTransaction ft=manager.beginTransaction().addToBackStack(null);
            ft.replace(R.id.container,fragment).commit();


        } else if (id == R.id.nav_help) {
            Fragment fragment= new ContactUs();
            FragmentManager manager=getSupportFragmentManager();
            FragmentTransaction ft=manager.beginTransaction().addToBackStack(null);
            ft.replace(R.id.container,fragment).commit();

        } else if (id == R.id.nav_faq) {
            Fragment fragment= new FAQs();
            FragmentManager manager=getSupportFragmentManager();
            FragmentTransaction ft=manager.beginTransaction().addToBackStack(null);
            ft.replace(R.id.container,fragment).commit();


        } else if (id == R.id.nav_helpdesk) {
            Fragment fragment= new HelpDesk();
            FragmentManager manager=getSupportFragmentManager();
            FragmentTransaction ft=manager.beginTransaction().addToBackStack(null);
            ft.replace(R.id.container,fragment).commit();

        } else if (id == R.id.nav_changePwd) {
            Fragment fragment= new ChangePwd();
            FragmentManager manager=getSupportFragmentManager();
            FragmentTransaction ft=manager.beginTransaction().addToBackStack(null);
            ft.replace(R.id.container,fragment).commit();

        } else if (id == R.id.nav_about) {
            Fragment fragment= new Aboutus();
            FragmentManager manager=getSupportFragmentManager();
            FragmentTransaction ft=manager.beginTransaction().addToBackStack(null);
            ft.replace(R.id.container,fragment).commit();
        } else if (id == R.id.nav_how) {
            Fragment fragment= new HowItWork();
            FragmentManager manager=getSupportFragmentManager();
            FragmentTransaction ft=manager.beginTransaction().addToBackStack(null);
            ft.replace(R.id.container,fragment).commit();

        } else if (id == R.id.nav_tnc) {
            Fragment fragment= new TermAndCondition();
            FragmentManager manager=getSupportFragmentManager();
            FragmentTransaction ft=manager.beginTransaction().addToBackStack(null);
            ft.replace(R.id.container,fragment).commit();

        } else if (id == R.id.nav_privacy) {
            Fragment fragment= new Privacy();
            FragmentManager manager=getSupportFragmentManager();
            FragmentTransaction ft=manager.beginTransaction().addToBackStack(null);
            ft.replace(R.id.container,fragment).commit();

        } else if (id == R.id.nav_refre) {

            new ProfileApi(MainActivity.this,MyPrefrences.getUserID(MainActivity.this)).execute();

        } else if (id == R.id.nav_logout) {


            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.alertdialogcustom_logout);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            Button ok = (Button) dialog.findViewById(R.id.btn_ok);
            Button cancel = (Button) dialog.findViewById(R.id.cancel);
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    MyPrefrences.resetPrefrences(MainActivity.this);
                    Intent intent=new Intent(MainActivity.this,Login.class);
                    startActivity(intent);
                    finishAffinity();
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialog.show();


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void refrelFriend() {

        new ReferFriend(getApplicationContext()).execute();


//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
//               Api.referal, null, new Response.Listener<JSONObject>() {
//
//            @Override
//            public void onResponse(JSONObject response) {
//                Log.d("Respose", response.toString());
//                Util.cancelPgDialog(dialog);
//                try {
//                    // Parsing json object response
//                    // response will be a json object
////                    String name = response.getString("name");
//
//                    if (response.getString("status").equalsIgnoreCase("success")){
//
//                        JSONArray jsonArray=response.getJSONArray("message");
//                        for (int i=0;i<jsonArray.length();i++){
//                            JSONObject jsonObject=jsonArray.getJSONObject(i);
//
//                            //  HomeAct.title.setText(jsonObject.optString("title"));
//
//
//                            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//                            sharingIntent.setType("text/plain");
//                            String shareBody =jsonObject.optString("content");
//                            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Download BizzCityInfo Seller App");
//                            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,shareBody);
//                            startActivity(Intent.createChooser(sharingIntent, "Share via"));
//
//
//
//                        }
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Toast.makeText(getApplicationContext(),
//                            "Error: " + e.getMessage(),
//                            Toast.LENGTH_LONG).show();
//                    Util.cancelPgDialog(dialog);
//                }
//
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d("Respose", "Error: " + error.getMessage());
//                Toast.makeText(getApplicationContext(),
//                        "Error! Please Connect to the internet", Toast.LENGTH_SHORT).show();
//                // hide the progress dialog
//                Util.cancelPgDialog(dialog);
//
//            }
//        });
//
//        // Adding request to request queue
//        jsonObjReq.setShouldCache(false);
//        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }

    private class ReferFriend extends AsyncTask<String, Void, String> {
        Context context;

        public ReferFriend(Context context) {
            this.context = context;
            dialog=new Dialog(MainActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Util.showPgDialog(dialog);
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map=new HashMap<>();

            map.put("function","sellerAppReferralLink");


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




                        //  HomeAct.title.setText(jsonObject.optString("title"));

                        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        String shareBody =jsonObject.optString("message");
                        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Download BizzCityInfo Seller App");
                        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,shareBody);
                        startActivity(Intent.createChooser(sharingIntent, "Share via"));


//
                    }
                    else if (jsonObject.optString("status").equalsIgnoreCase("failure")){

                    }
                    else {
                        Util.errorDialog(MainActivity.this,jsonObject.getJSONObject("message").toString());
                    }
                }
            } catch (JSONException e) {
                Util.errorDialog(MainActivity.this,"Some Error! Please try again...");
                e.printStackTrace();
            }
        }

    }


    //    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            Fragment fragment;
//            switch (item.getItemId()) {
//                case R.id.navigation_home:
//                    fragment= new Matchingleads();
//                    loadFragment(fragment);
//                    return true;
//                case R.id.navigation_category:
//                    fragment= new PurchasedLeads();
//                    loadFragment(fragment);
//                    return true;
//                case R.id.navigation_search:
//                    fragment= new BuyPackages();
//                    loadFragment(fragment);
//                    return true;
//                case R.id.navigation_offer:
//                    fragment= new Profile();
//                    loadFragment(fragment);
//
//                    return true;
//
//            }
//            return false;
//        }
//    };
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private class ProfileApi extends AsyncTask<String, Void, String> {
        Context context;
        String id;
        public ProfileApi(Context context,String id) {
            this.context = context;
            this.id=id;
            dialog=new Dialog(MainActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Util.showPgDialog(dialog);
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map=new HashMap<>();

            map.put("function","memberById");
            map.put("memberId", id.toString());

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

                        JSONArray jsonArray=jsonObject.getJSONArray("message");
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);

                            points.setText(jsonObject1.optString("points"));
                            if (jsonObject1.optString("premium").equalsIgnoreCase("Yes")){
                                prem.setText("Premium Member");
                            }else{
                                prem.setText("Free Member");
                            }

                            // Picasso.with(context).load(jsonObject1.optString("companyLogo")).into(imageView);
                        }
                    }
                    else {
                        Util.errorDialog(MainActivity.this,jsonObject.optString("message"));
                    }
                }
            } catch (JSONException e) {
                Util.errorDialog(MainActivity.this,"Some Error! Please try again...");
                e.printStackTrace();
            }
        }

    }

}
