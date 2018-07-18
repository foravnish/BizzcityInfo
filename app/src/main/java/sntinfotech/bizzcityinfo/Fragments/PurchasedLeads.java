package sntinfotech.bizzcityinfo.Fragments;


import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
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
public class PurchasedLeads extends Fragment {


    public PurchasedLeads() {
        // Required empty public constructor
    }
    Dialog dialog;
    GridView gridview;
    Adapter adapter;
    List<HashMap<String,String>> DataList;
    JSONArray jsonArray;
    TextView textview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view=inflater.inflate(R.layout.fragment_purchased_leads, container, false);

        gridview=(GridView) view.findViewById(R.id.gridview);
        textview = (TextView) view.findViewById(R.id.textview);

        getActivity().setTitle("Purchased Leads");
        DataList =new ArrayList<>();

        new  PurchasedLead(getActivity()).execute();

        return view;
    }

    private class PurchasedLead extends AsyncTask<String, Void, String> {
        Context context;
        String mobileNo,password;
        public PurchasedLead(Context context) {
            this.context = context;
            dialog=new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Util.showPgDialog(dialog);
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map=new HashMap<>();

            map.put("function","purchaseLead");
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

                        textview.setVisibility(View.GONE);
                        gridview.setVisibility(View.VISIBLE);

                        jsonArray=jsonObject.getJSONArray("message");
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);

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
                            map.put("mobile",jsonObject1.optString("mobile").toString());
                            map.put("posted_date",jsonObject1.optString("posted_date").toString());
                            DataList.add(map);


                            adapter=new Adapter();
                            gridview.setAdapter(adapter);
                        }
//
                    }
                    else {
                        // Util.errorDialog(context,"No message here...");
                        textview.setText("Purchased Lead Not Available");
                        textview.setVisibility(View.VISIBLE);
                        gridview.setVisibility(View.GONE);
                    }
                }
            } catch (JSONException e) {
                Util.errorDialog(context,"Some Error! Please try again...");
                e.printStackTrace();
            }
        }

    }


    static class ViewHolder {

        TextView points,require,name,address,date,leadNo;
        LayoutInflater inflater;
        TextView calls;

    }



    class Adapter extends BaseAdapter {
        LayoutInflater inflater;
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
            ViewHolder viewHolder;
            convertView=inflater.inflate(R.layout.custonlistview_purch,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.require=(TextView)convertView.findViewById(R.id.require);
            viewHolder.name=(TextView)convertView.findViewById(R.id.name);
            viewHolder.address=(TextView)convertView.findViewById(R.id.address);
            viewHolder.points=(TextView)convertView.findViewById(R.id.points);
            viewHolder.calls=(TextView)convertView.findViewById(R.id.calls);
            viewHolder.date=(TextView)convertView.findViewById(R.id.date);
            viewHolder.leadNo=(TextView)convertView.findViewById(R.id.leadNo);

            String insertDate = DataList.get(position).get("posted_date");
            String[] items1 = insertDate.split(" ");
            String d1=items1[0];
            String m1=items1[1];
            String[] items2 = d1.split("-");
            String d =items2[0];
            String m =items2[1];
            String y =items2[2];

            viewHolder.require.setText(DataList.get(position).get("requirement"));
            viewHolder.name.setText(DataList.get(position).get("uname"));
            viewHolder.address.setText(DataList.get(position).get("city"));
            viewHolder.points.setText(DataList.get(position).get("point"));
            viewHolder.date.setText(y+"-"+m+"-"+d  +"    "+m1 );
            viewHolder.leadNo.setText("Lead No # "+DataList.get(position).get("id"));
            viewHolder.calls.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

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


            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Fragment  fragment=new PurchasedLeadDetails();
                    Bundle bundle=new Bundle();
                    try {
                        bundle.putString("leads",jsonArray.get(i).toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    FragmentManager manager=getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction=manager.beginTransaction();
                    fragment.setArguments(bundle);
                    transaction.replace(R.id.container,fragment).addToBackStack(null).commit();


                }
            });





//            imageLoader = AppController.getInstance().getImageLoader();
//
//            name.setText(DataList.get(position).getName().toString());
//            image.setImageUrl(DataList.get(position).getDesc().toLowerCase(),imageLoader);

            return convertView;
        }
    }



}
