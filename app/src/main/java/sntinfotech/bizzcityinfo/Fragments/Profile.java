package sntinfotech.bizzcityinfo.Fragments;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sntinfotech.bizzcityinfo.MainActivity;
import sntinfotech.bizzcityinfo.R;
import sntinfotech.bizzcityinfo.Utils.Api;
import sntinfotech.bizzcityinfo.Utils.MyPrefrences;
import sntinfotech.bizzcityinfo.Utils.Util;
import sntinfotech.bizzcityinfo.connection.JSONParser;

/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {



    TextView name,location,location2,pincode,com_name,email,mobile,mobile2,services,userID,editList,phone;
    Dialog  dialog;
    GridView listview;
    ImageView imageView;
    List<HashMap<String,String>> list=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile1, container, false);

        getActivity().setTitle("Profile");

        name=(TextView)view.findViewById(R.id.name);
        location=(TextView)view.findViewById(R.id.location);
        location2=(TextView)view.findViewById(R.id.location2);
       // pincode=(TextView)view.findViewById(R.id.pincode);
        com_name=(TextView)view.findViewById(R.id.com_name);
        email=(TextView)view.findViewById(R.id.email);
        phone=(TextView)view.findViewById(R.id.phone);
        mobile=(TextView)view.findViewById(R.id.mobile);
        //mobile2=(TextView)view.findViewById(R.id.mobile2);
       // services=(TextView)view.findViewById(R.id.services);
        userID=(TextView)view.findViewById(R.id.userID);
        editList=(TextView)view.findViewById(R.id.editList);
        listview=(GridView) view.findViewById(R.id.listview);
        imageView=(ImageView) view.findViewById(R.id.imageView);




        new  ProfileApi(getActivity(),MyPrefrences.getUserID(getActivity())).execute();

        return view;

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

                            name.setText(jsonObject1.optString("c1_fname"));
                            location.setText(jsonObject1.optString("address")+", "+jsonObject1.optString("location_id")+" "+jsonObject1.optString("pincode"));
                            location2.setText(jsonObject1.optString("location_id"));
                            //pincode.setText(jsonObject1.optString("pincode"));
                            com_name.setText(jsonObject1.optString("company_name"));
                            email.setText(jsonObject1.optString("c1_email"));
                            mobile.setText(jsonObject1.optString("c1_mobile1"));
                            //mobile2.setText(jsonObject1.optString("c1_mobile2"));
                            phone.setText(jsonObject1.optString("std_code")+"-"+jsonObject1.optString("phone"));
                            userID.setText("My Id- "+MyPrefrences.getUserID(getActivity()));

                            Picasso.with(context).load(jsonObject1.optString("companyLogo")).into(imageView);


                            String str = jsonObject1.optString("subcategory");

//                            String[] items1 = insertCat.split(",");
//
//                            String insertCatID = jsonObject1.optString("subcat");
//                            String[] items12 = insertCatID.split(",");
//
//                            for(String ssss : items1){
//                                Log.d("vfgvfgdfdfdfdfgbd",ssss);
//                                services.setText(insertCat);
//                            }
//
//                            for(String ssss2 : items12){
//                                Log.d("vfgvfgdfdfdfdfgbd",ssss2);
//
//                            }

                            String[] words=str.split(",");//Separating the word using delimiter Comma and stored in an array
                            for(int k=0;k<words.length;k++)
                            {
                                Log.d("dsfsdfsdgfsdgdsf",words[k]);
//           list.add(words[k]);

                                HashMap<String,String>map=new HashMap<>();
                                map.put("text",words[k]);
                                Adapter adapter=new Adapter();
                                listview.setAdapter(adapter);
                                list.add(map);
                            }


                            editList.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {


//                                    Fragment fragment = new ProfileEdit();
//                                    Bundle bundle=new Bundle();
//                                   // bundle.putString("");
//                                    FragmentManager manager = getActivity().getSupportFragmentManager();
//                                    FragmentTransaction ft = manager.beginTransaction().addToBackStack(null);
//                                    ft.replace(R.id.container, fragment).commit();
                                }
                            });




//                            String[] items2 = insertDate.split(" ");

//                            String d1=items1[0];
//                            String m1=items1[1];
//                            String y1=items1[2];
//                            for(String ssss : items2){
//                                Log.d("vfgvfgdfdfdfdfgbd",ssss);
//                            }
//                            String ss =items2[0];
//                            String mm =items2[1];
//                            String hh =items2[2];


//                            Log.d("vfgvfgdfdfdfdfgbd",d1+" "+ m1+" "+y1+"h> "+hh+"m> "+mm+" s> "+ss);
//                            Log.d("vfgvfgdfdfdfdfgbd",d1+" "+m1);
//
//                            JSONArray jsonArray1 =jsonObject1.getJSONArray("subcategory");
//
////                            for (int j=0;j<jsonArray1.length();j++){
//
////                                String s1=jsonObject11.optString("");
//                                Log.d("sfgsdgdfgdfgdfh",jsonArray1.toString());
////                            }
//                            Log.d("dfsdfgsdgfdsgdfg",jsonObject1.optString("subcategory"));
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


    class Adapter extends BaseAdapter {

        LayoutInflater inflater;
        TextView textView1;

        Adapter() {
            inflater = (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView=inflater.inflate(R.layout.list_s_list2,parent,false);
            textView1= (TextView) convertView.findViewById(R.id.textView1);
            textView1.setText(list.get(position).get("text"));

            Typeface face=Typeface.createFromAsset(getActivity().getAssets(), "muli_bold.ttf");
            textView1.setTypeface(face);

            return convertView;
        }
    }



}
