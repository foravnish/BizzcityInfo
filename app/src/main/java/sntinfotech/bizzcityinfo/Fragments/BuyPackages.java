package sntinfotech.bizzcityinfo.Fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.payumoney.core.PayUmoneyConfig;
import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;
import com.payumoney.sdkui.ui.utils.ResultModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sntinfotech.bizzcityinfo.MainActivity;
import sntinfotech.bizzcityinfo.PayActivity;
import sntinfotech.bizzcityinfo.PayUMoney.AppEnvironment;
import sntinfotech.bizzcityinfo.PayUMoney.AppPreference;
import sntinfotech.bizzcityinfo.PayUMoney.BaseApplication;
import sntinfotech.bizzcityinfo.R;
import sntinfotech.bizzcityinfo.Utils.Api;
import sntinfotech.bizzcityinfo.Utils.MyPrefrences;
import sntinfotech.bizzcityinfo.Utils.Util;
import sntinfotech.bizzcityinfo.connection.JSONParser;

import static android.content.Context.MODE_PRIVATE;
import static com.payumoney.core.SdkSession.hashCal;
import static sntinfotech.bizzcityinfo.connection.AppController.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuyPackages extends Fragment {

    private static final int RESULT_OK = -1;
    JSONArray jsonArray;
    GridView gridview;
    Adapter adapter;
    List<HashMap<String,String>> DataList;
    Dialog dialog;

    
    public BuyPackages() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_buy_packages, container, false);
        gridview=(GridView) view.findViewById(R.id.gridview);

        DataList =new ArrayList<>();


        getActivity().setTitle("Buy Credit");

        new  PackagesApi(getActivity()).execute();

       
        //Set Up SharedPref
        //setUpUserDetails();
        
        return view;
    }


    public static void setErrorInputLayout(EditText editText, String msg, TextInputLayout textInputLayout) {
        textInputLayout.setError(msg);
        editText.requestFocus();
    }

    public static boolean isValidEmail(String strEmail) {
        return strEmail != null && android.util.Patterns.EMAIL_ADDRESS.matcher(strEmail).matches();
    }

    public static boolean isValidPhone(String phone) {
        Pattern pattern = Pattern.compile(AppPreference.PHONE_PATTERN);

        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }
    
//    private void setUpUserDetails() {
//        userDetailsPreference = getActivity().getSharedPreferences(AppPreference.USER_DETAILS, MODE_PRIVATE);
////        userEmail = userDetailsPreference.getString(AppPreference.USER_EMAIL, mAppPreference.getDummyEmail());
////        userMobile = userDetailsPreference.getString(AppPreference.USER_MOBILE, mAppPreference.getDummyMobile());
////        email_et.setText(userEmail);
////        mobile_et.setText(userMobile);
////        amount_et.setText(mAppPreference.getDummyAmount());
//      //  restoreAppPref();
//    }

//    private void selectProdEnv() {
//
//        new Handler(getMainLooper()).postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                ((BaseApplication) getApplication()).setAppEnvironment(AppEnvironment.PRODUCTION);
//                editor = settings.edit();
//                editor.putBoolean("is_prod_env", true);
//                editor.apply();
//
//                if (PayUmoneyFlowManager.isUserLoggedIn(getApplicationContext())) {
//                    logoutBtn.setVisibility(View.VISIBLE);
//                } else {
//                    logoutBtn.setVisibility(View.GONE);
//                }
//
//                setupCitrusConfigs();
//            }
//        }, AppPreference.MENU_DELAY);
//    }


    private class PackagesApi extends AsyncTask<String, Void, String> {
        Context context;

        public PackagesApi(Context context) {
            this.context = context;
            dialog=new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Util.showPgDialog(dialog);
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map=new HashMap<>();

                map.put("function","leadPackage");

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


                        jsonArray=jsonObject.getJSONArray("message");
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);

                            HashMap<String,String> map=new HashMap<>();
                            map.put("id",jsonObject1.optString("id").toString());
                            map.put("package_name",jsonObject1.optString("package_name").toString());
                            map.put("creadit_points",jsonObject1.optString("creadit_points").toString());
                            map.put("credit_per_value",jsonObject1.optString("credit_per_value").toString());
                            map.put("total_value",jsonObject1.optString("total_value").toString());
                            map.put("bonus_credit",jsonObject1.optString("bonus_credit").toString());
                            map.put("total_credit",jsonObject1.optString("total_credit").toString());
                            map.put("score",jsonObject1.optString("score").toString());
                            map.put("gst",jsonObject1.optString("gst").toString());
                            map.put("posted_date",jsonObject1.optString("posted_date").toString());
                            map.put("bonus_in_percentage",jsonObject1.optString("bonus_in_percentage").toString());

                            DataList.add(map);

                            adapter=new Adapter();
                            gridview.setAdapter(adapter);
                        }
//
                    }
                    else {
                        Util.errorDialog(context,"Some Error! Please try again...");
                    }
                }
            } catch (JSONException e) {
                Util.errorDialog(context,"Some Error! Please try again...");
                e.printStackTrace();
            }
        }

    }

    class Adapter extends BaseAdapter {
        TextView pName,creditpoints,creditpoints2,price,buynow,discount,totalCredit;
        LayoutInflater inflater;
        RelativeLayout discountTag;
        LinearLayout qImage2;
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
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView=inflater.inflate(R.layout.custonlistview_packages,parent,false);

            pName=(TextView)convertView.findViewById(R.id.pName);
            creditpoints=(TextView)convertView.findViewById(R.id.creditpoints);
            creditpoints2=(TextView)convertView.findViewById(R.id.creditpoints2);
            price=(TextView)convertView.findViewById(R.id.price);
            buynow=(TextView) convertView.findViewById(R.id.buynow);
            discount=(TextView) convertView.findViewById(R.id.discount);
            totalCredit=(TextView) convertView.findViewById(R.id.totalCredit);
            discountTag=(RelativeLayout) convertView.findViewById(R.id.discountTag);
            qImage2=(LinearLayout) convertView.findViewById(R.id.qImage2);

            pName.setText(DataList.get(position).get("package_name")+" Package");
            discount.setText(DataList.get(position).get("bonus_in_percentage")+" % \nExtra");
            creditpoints.setText(DataList.get(position).get("creadit_points")+" Points ");

            totalCredit.setText("Total Credit Points "+DataList.get(position).get("total_credit"));
            int perCredit= (int) Double.parseDouble(DataList.get(position).get("credit_per_value"));
            int points= (int) Double.parseDouble(DataList.get(position).get("creadit_points"));

            price.setText(String.valueOf(perCredit*points));

            if (DataList.get(position).get("bonus_credit").equals("0")){
                discountTag.setVisibility(View.INVISIBLE);
                creditpoints2.setVisibility(View.GONE);
            }
            else{
                creditpoints2.setText("+ "+DataList.get(position).get("bonus_credit")+" Points Extra");
            }


            qImage2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(getActivity(), "open", Toast.LENGTH_SHORT).show();
                   Util.showInfoAlertDialog(getActivity(), "Price", "1 Credits Point =  ₹ 25 ");
                }
            });


            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Intent intent=new Intent(getActivity(), PayActivity.class);
                    try {
                        intent.putExtra("json",jsonArray.get(i).toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    startActivity(intent);



                }
            });
            return convertView;
        }
    }


}
