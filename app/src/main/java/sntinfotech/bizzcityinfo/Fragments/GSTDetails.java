package sntinfotech.bizzcityinfo.Fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import sntinfotech.bizzcityinfo.Login;
import sntinfotech.bizzcityinfo.MainActivity;
import sntinfotech.bizzcityinfo.R;
import sntinfotech.bizzcityinfo.Utils.Api;
import sntinfotech.bizzcityinfo.Utils.MyPrefrences;
import sntinfotech.bizzcityinfo.Utils.Util;
import sntinfotech.bizzcityinfo.connection.JSONParser;

/**
 * A simple {@link Fragment} subclass.
 */
public class GSTDetails extends Fragment {


    public GSTDetails() {
        // Required empty public constructor
    }

    EditText regtAddresst,gstNot,namet;
    Button submitNow;
    Dialog  dialog;
    TextView slogan;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_gstdetails, container, false);
        getActivity().setTitle("GST Details");

        submitNow=(Button)view.findViewById(R.id.submitNow);
        namet=(EditText)view.findViewById(R.id.name);
        gstNot=(EditText)view.findViewById(R.id.gstNo);
        regtAddresst=(EditText)view.findViewById(R.id.regtAddress);
        slogan=(TextView) view.findViewById(R.id.slogan);

        new CheckFillOrNot(getActivity()).execute();

        submitNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(chaeckValidation()){
                     new GSTNOApi(getActivity(),namet.getText().toString(),gstNot.getText().toString(),regtAddresst.getText().toString()).execute();

                    //Toast.makeText(getActivity(), "yes", Toast.LENGTH_SHORT).show();
                }

            }
        });

        slogan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment= new ContactUs();
                FragmentManager manager=getActivity().getSupportFragmentManager();
                FragmentTransaction ft=manager.beginTransaction().addToBackStack(null);
                ft.replace(R.id.container,fragment).commit();
            }
        });
        return view;
    }

    private boolean chaeckValidation() {

        if (TextUtils.isEmpty(namet.getText().toString())){
            namet.setError("oops! Company Name is blank");
            namet.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(gstNot.getText().toString())) {
            gstNot.setError("oops! GST No is blank");
            gstNot.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(regtAddresst.getText().toString())) {
            regtAddresst.setError("oops! Address is blank");
            regtAddresst.requestFocus();
            return false;
        }
        return true;
    }

    private class CheckFillOrNot extends AsyncTask<String, Void, String> {
        Context context;
        String name,gstno,adress;
        public CheckFillOrNot(Context context) {
            this.context = context;

            dialog=new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Util.showPgDialog(dialog);
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map=new HashMap<>();

            map.put("function","memberById");
            map.put("memberId", MyPrefrences.getUserID(getActivity()));


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
                        JSONObject jsonObject1=jsonArray.getJSONObject(0);
                        if (!jsonObject1.optString("gst_company_name").equals("")){

                            namet.setText(jsonObject1.optString("gst_company_name"));
                            gstNot.setText(jsonObject1.optString("gst_no"));
                            regtAddresst.setText(jsonObject1.optString("gst_address"));
                            submitNow.setVisibility(View.GONE);
                            namet.setEnabled(false);
                            gstNot.setEnabled(false);
                            regtAddresst.setEnabled(false);

                            slogan.setVisibility(View.VISIBLE);

                        }

                    }
                    else {
                        Util.errorDialog(context,jsonObject.optString("message"));
                    }
                }
            } catch (JSONException e) {
                Util.errorDialog(context,"Some Error! Please try again...");
                e.printStackTrace();
            }
        }

    }



    private class GSTNOApi extends AsyncTask<String, Void, String> {
        Context context;
        String name,gstno,adress;
        public GSTNOApi(Context context, String name, String gstno, String adress) {
            this.context = context;
            this.name=name;
            this.gstno=gstno;
            this.adress=adress;
            dialog=new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Util.showPgDialog(dialog);
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map=new HashMap<>();

            map.put("function","insertGSTNo");
            map.put("gst_company_name", name.toString());
            map.put("gst_no", gstno.toString());
            map.put("gst_address", adress.toString());
            map.put("memberId", MyPrefrences.getUserID(getActivity()));


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
                        Util.errorDialog(getActivity(),"Your details Submitted Successfully...");

                        namet.setText("");
                        gstNot.setText("");
                        regtAddresst.setText("");

                    }
                    else {
                        Util.errorDialog(context,jsonObject.optString("message"));
                    }
                }
            } catch (JSONException e) {
                Util.errorDialog(context,"Some Error! Please try again...");
                e.printStackTrace();
            }
        }

    }


}
