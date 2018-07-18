package sntinfotech.bizzcityinfo.Fragments;


import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
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
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sntinfotech.bizzcityinfo.R;
import sntinfotech.bizzcityinfo.Utils.Api;
import sntinfotech.bizzcityinfo.Utils.MyPrefrences;
import sntinfotech.bizzcityinfo.Utils.Util;
import sntinfotech.bizzcityinfo.connection.JSONParser;

/**
 * A simple {@link Fragment} subclass.
 */
public class Inbox extends Fragment {


    public Inbox() {
        // Required empty public constructor
    }

    List<HashMap<String,String>> AllProducts ;
    GridView expListView;
    Dialog dialog;
    Adapter adapter;
    List<HashMap<String,String>> DataList;
    TextView textview;
    JSONArray jsonArray;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_inbox, container, false);
        AllProducts = new ArrayList<>();
        expListView = (GridView) view.findViewById(R.id.lvExp);
        textview = (TextView) view.findViewById(R.id.textview);

        DataList =new ArrayList<>();
        getActivity().setTitle("Inbox");

        new InboxApi(getActivity()).execute();

//        HashMap<String,String> map=new HashMap<>();
//        for (int i=0;i<20;i++) {
//            map.put("name", "Name");
//            Adapter adapter=new Adapter();
//            expListView.setAdapter(adapter);
//            AllProducts.add(map);
//        }


        return view;
    }

    class Adapter extends BaseAdapter {

        LayoutInflater inflater;
        TextView name,location,message,date,mobileNo;
        LinearLayout call;
        Adapter() {
            inflater = (LayoutInflater) getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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


            convertView=inflater.inflate(R.layout.list_inbox,parent,false);

            name= (TextView) convertView.findViewById(R.id.name);
            location= (TextView) convertView.findViewById(R.id.location);
            message= (TextView) convertView.findViewById(R.id.message);
            date= (TextView) convertView.findViewById(R.id.date);
            mobileNo= (TextView) convertView.findViewById(R.id.mobileNo);
            call= (LinearLayout) convertView.findViewById(R.id.call);

            name.setText(DataList.get(position).get("uname"));
            location.setText(DataList.get(position).get("city"));
            message.setText(DataList.get(position).get("requirement"));
            date.setText(DataList.get(position).get("posted_date"));
            mobileNo.setText("+91 "+DataList.get(position).get("mobile"));
//
//            final Typeface tvFont = Typeface.createFromAsset(getActivity().getAssets(), "muli_bold.ttf");
//            message.setTypeface(tvFont);
//            name.setTypeface(tvFont);
//            mobileNo.setTypeface(tvFont);


            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(getActivity(), "call", Toast.LENGTH_SHORT).show();


                    try
                    {
                        if(Build.VERSION.SDK_INT > 22)
                        {
                            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 101);
                                return;
                            }
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:" + DataList.get(position).get("mobile")));
                            startActivity(callIntent);
                        }
                        else {
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:" + DataList.get(position).get("mobile")));
                            startActivity(callIntent);
                        }
                    }
                    catch (Exception ex)
                    {ex.printStackTrace();
                    }


                }
            });

            return convertView;
        }
    }

    private class InboxApi extends AsyncTask<String, Void, String> {
        Context context;

        public InboxApi(Context context) {
            this.context = context;
            dialog=new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Util.showPgDialog(dialog);
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map=new HashMap<>();

            map.put("function","myEnquery");
            map.put("myId", MyPrefrences.getUserID(getActivity()));

            JSONParser jsonParser=new JSONParser();
            String result =jsonParser.makeHttpRequest(Api.Login,"POST",map);

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

                        textview.setVisibility(View.GONE);
                        expListView.setVisibility(View.VISIBLE);

                        jsonArray=jsonObject.getJSONArray("message");
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);

                            HashMap<String,String> map=new HashMap<>();
                            map.put("id",jsonObject1.optString("id").toString());
                            map.put("current_status",jsonObject1.optString("current_status").toString());
                            map.put("uname",jsonObject1.optString("uname").toString());
                            map.put("mobile",jsonObject1.optString("mobile").toString());
                            map.put("city",jsonObject1.optString("city").toString());
                            map.put("email",jsonObject1.optString("email").toString());
                            map.put("requirement",jsonObject1.optString("requirement").toString());
                            map.put("posted_date",jsonObject1.optString("posted_date").toString());
                            map.put("ip",jsonObject1.optString("ip").toString());


                            DataList.add(map);

                            adapter=new Adapter();
                            expListView.setAdapter(adapter);

//
//                            expListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                @Override
//                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                                    Fragment fragment = new InboxDetail();
//                                    Bundle bundle=new Bundle();
//                                    bundle.putString("data", jsonArray.toString());
//                                    FragmentManager manager = getActivity().getSupportFragmentManager();
//                                    FragmentTransaction ft = manager.beginTransaction().addToBackStack(null);
//                                    fragment.setArguments(bundle);
//                                    ft.replace(R.id.container, fragment).commit();
//
//                                }
//                            });


                        }
//
                    }
                    else {
                       // Util.errorDialog(context,"No message here...");
                        textview.setText("No Message in Inbox");
                        textview.setVisibility(View.VISIBLE);
                        expListView.setVisibility(View.GONE);
                    }
                }
            } catch (JSONException e) {
                Util.errorDialog(context,"Some Error! Please try again...");
                e.printStackTrace();
            }
        }

    }




}
