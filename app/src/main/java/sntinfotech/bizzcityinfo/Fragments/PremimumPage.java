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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;

import sntinfotech.bizzcityinfo.MainActivity;
import sntinfotech.bizzcityinfo.PackagesPremimum;
import sntinfotech.bizzcityinfo.ProfileAct;
import sntinfotech.bizzcityinfo.ProfileEdit;
import sntinfotech.bizzcityinfo.R;
import sntinfotech.bizzcityinfo.Utils.Api;
import sntinfotech.bizzcityinfo.Utils.MyPrefrences;
import sntinfotech.bizzcityinfo.Utils.Util;
import sntinfotech.bizzcityinfo.connection.JSONParser;


/**
 * A simple {@link Fragment} subclass.
 */
public class PremimumPage extends Fragment {


    public PremimumPage() {
        // Required empty public constructor
    }

    Button joinNow;
    TextView price;
    Dialog dialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_premimum_page, container, false);
        joinNow= (Button) view.findViewById(R.id.joinNow);
        price= (TextView) view.findViewById(R.id.price);


        getActivity().setTitle("Primo");

        new ProfileApi(getActivity(), MyPrefrences.getUserID(getActivity())).execute();
        new ShowPackage(getActivity(), MyPrefrences.getUserID(getActivity())).execute();

        return view;
    }

    private class ShowPackage extends AsyncTask<String, Void, String> {
        Context context;
        String id;
        public ShowPackage(Context context,String id) {
            this.context = context;
            this.id=id;
            dialog=new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Util.showPgDialog(dialog);
        }


        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map=new HashMap<>();

            map.put("function","premiunPackage");

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

                        price.setText(jsonObject1.optString("actual_price")+" /-");


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

    private class ProfileApi extends AsyncTask<String, Void, String> {
        Context context;
        String id;
        public ProfileApi(Context context,String id) {
            this.context = context;
            this.id=id;
            dialog=new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
             //Util.showPgDialog(dialog);
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

                        final JSONArray jsonArray=jsonObject.getJSONArray("message");
                        for (int i=0;i<jsonArray.length();i++){
                            final JSONObject jsonObject1=jsonArray.getJSONObject(i);



                            if (jsonObject1.optString("premium").equalsIgnoreCase("Yes")){
                                joinNow.setText("You are Already Joined");

                            }
                            else if (jsonObject1.optString("premium").equalsIgnoreCase("No")){

                                joinNow.setText("Join Now");
                                joinNow.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                    //                Intent intent=new Intent(getActivity(),PackagesPremimum.class);
                    //                startActivity(intent);

                                        Fragment fragment = new PremimumFragment();
                                        FragmentManager manager = getActivity().getSupportFragmentManager();
                                        FragmentTransaction ft = manager.beginTransaction().addToBackStack(null);
                                        ft.replace(R.id.container, fragment).commit();
                                    }
                                });
                            }


                        }
                    }
                    else {
                        Util.errorDialog(context,jsonObject.optString("message"));
                        joinNow.setText("Some Error Please Try Again!");
                    }
                }
            } catch (JSONException e) {
                Util.errorDialog(context,"Some Error! Please try again...");
                joinNow.setText("Some Error Please Try Again!");
                e.printStackTrace();
            }
        }

    }


}
