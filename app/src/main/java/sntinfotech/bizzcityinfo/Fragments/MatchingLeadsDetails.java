package sntinfotech.bizzcityinfo.Fragments;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

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
public class MatchingLeadsDetails extends Fragment {


    public MatchingLeadsDetails() {
        // Required empty public constructor
    }
    Dialog  dialog;
    JSONObject jsonObject;
    double points;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_matching_leads_details, container, false);

        TextView name=(TextView)view.findViewById(R.id.name);
        TextView location=(TextView)view.findViewById(R.id.location);
        TextView pos_date=(TextView)view.findViewById(R.id.pos_date);
        TextView email=(TextView)view.findViewById(R.id.email);
        TextView mobile=(TextView)view.findViewById(R.id.mobile);
        TextView category=(TextView)view.findViewById(R.id.category);
        TextView requires=(TextView)view.findViewById(R.id.requires);
        TextView points2=(TextView)view.findViewById(R.id.points2);
        TextView leads=(TextView)view.findViewById(R.id.leads);
        TextView date=(TextView)view.findViewById(R.id.date);
        TextView time=(TextView)view.findViewById(R.id.time);
        Button buyNow=(Button)view.findViewById(R.id.buyNow);

        getActivity().setTitle("Lead Details");

        Log.d("fgdfgdfhgd",getArguments().getString("leads"));

        try {
                jsonObject=new JSONObject(getArguments().getString("leads"));
                name.setText(jsonObject.optString("uname"));
                location.setText(jsonObject.optString("city"));
                pos_date.setText(jsonObject.optString("posted_date"));
//                email.setText(jsonObject.optString("email"));
                email.setText("xxxxxxxxx@xxxxx");
//                mobile.setText(jsonObject.optString("mobile"));
                mobile.setText("+91 XXXXXXXXX");
                category.setText(jsonObject.optString("subcategory"));
                requires.setText(jsonObject.optString("requirement"));
                points2.setText(jsonObject.optString("point"));

                leads.setText("Lead # "+jsonObject.optString("id"));

            String insertDate = jsonObject.optString("posted_date");
            String[] items1 = insertDate.split(" ");
            String d1=items1[0];
            String m1=items1[1];
            String[] items2 = d1.split("-");
            String d =items2[0];
            String m =items2[1];
            String y =items2[2];

            date.setText(y+"-"+m+"-"+d );
            time.setText(m1 );

        } catch (JSONException e) {
            e.printStackTrace();
        }

        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                points= Double.parseDouble(MainActivity.points.getText().toString());
//                points= 0;

                if (points>0) {
//                    Util.errorDialog(getActivity(),"You have Points");
                    new PurchagesLeadBuyNow(getActivity(), MyPrefrences.getUserID(getActivity()).toString(), jsonObject.optString("id")).execute();
                }
                else{

                    final Dialog dialog = new Dialog(getActivity());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.alertdialogcustom_insuf);
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    Button ok = (Button) dialog.findViewById(R.id.btn_ok);
                    Button cancel = (Button) dialog.findViewById(R.id.cancel);
                    TextView msg_txv = (TextView) dialog.findViewById(R.id.msg_txv);
                    msg_txv.setText("Ooops... low Credit\nBuy Credit Now");
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Fragment fragment = new BuyPackages();
                            FragmentManager manager = getActivity().getSupportFragmentManager();
                            FragmentTransaction ft = manager.beginTransaction().addToBackStack(null);
                            ft.replace(R.id.container, fragment).commit();
                            dialog.dismiss();
                        }
                    });
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();

                    //Util.errorDialog(getActivity(),"You have Insufficient credit point");
                }
            }
        });


        return view;
    }


    private class PurchagesLeadBuyNow extends AsyncTask<String, Void, String> {
        Context context;
        String s,id;
        public PurchagesLeadBuyNow(Context context, String s, String id) {
            this.context = context;
            this.s = s;
            this.id = id;
            dialog=new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Util.showPgDialog(dialog);
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map=new HashMap<>();

            map.put("function","buyLead");
            map.put("memberId", s.toString());
            map.put("leadId", id.toString());

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


//                        Util.errorDialog(getActivity(),"Successfully Purchased....");
                        Fragment fragment= new PurchasedLeads();
                        FragmentManager manager=getActivity().getSupportFragmentManager();
                        FragmentTransaction ft=manager.beginTransaction().addToBackStack(null);
                        ft.replace(R.id.container,fragment).commit();
                        MainActivity.points.setText(jsonObject.optString("points"));
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
