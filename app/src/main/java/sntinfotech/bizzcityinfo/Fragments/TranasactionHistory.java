package sntinfotech.bizzcityinfo.Fragments;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
public class TranasactionHistory extends Fragment {

    JSONArray jsonArray;
    Dialog dialog;
    GridView gridview;
    Adapter adapter;
    TextView balance,lowPoint;
    List<HashMap<String,String>> DataList;
    public TranasactionHistory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_tranasaction_history, container, false);

        getActivity().setTitle("Transactions");
        gridview=(GridView) view.findViewById(R.id.gridview);
        balance=(TextView) view.findViewById(R.id.balance);
        lowPoint=(TextView) view.findViewById(R.id.lowPoint);

        try {
            int pointNo= Integer.parseInt(MainActivity.points.getText().toString());
            if (pointNo<=10){
                lowPoint.setVisibility(View.VISIBLE);
            }
            else{
                lowPoint.setVisibility(View.GONE);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }


        balance.setText(MainActivity.points.getText().toString());

        DataList =new ArrayList<>();

        new  PackagesHistoryApi(getActivity()).execute();
        return view;
    }

    private class PackagesHistoryApi extends AsyncTask<String, Void, String> {
        Context context;

        public PackagesHistoryApi(Context context) {
            this.context = context;
            dialog=new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Util.showPgDialog(dialog);
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map=new HashMap<>();

            map.put("function","purchasedHistory");
            map.put("memberId", MyPrefrences.getUserID(getActivity()).toString());

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
                            map.put("purchase_date",jsonObject1.optString("purchase_date").toString());
                            map.put("creditDebit",jsonObject1.optString("creditDebit").toString());
                            map.put("packageName",jsonObject1.optString("packageName").toString());
                            map.put("amount",jsonObject1.optString("amount").toString());
                            map.put("service_tax",jsonObject1.optString("service_tax").toString());
                            map.put("total_amount",jsonObject1.optString("total_amount").toString());
                            map.put("points",jsonObject1.optString("points").toString());
                            map.put("invoice_number",jsonObject1.optString("invoice_number").toString());
                            map.put("admin_comment",jsonObject1.optString("admin_comment").toString());

                            DataList.add(map);

                            adapter=new Adapter();
                            gridview.setAdapter(adapter);
                        }
//
                    }

                    else {
                       // Util.errorDialog(context,jsonObject.optString("message"));
                    }
                }
            } catch (JSONException e) {
                Util.errorDialog(context,"Some Error! Please try again...");
                e.printStackTrace();
            }
        }

    }

    class Adapter extends BaseAdapter {
        TextView Name,date,price,points,status,status2,invoice;
        LinearLayout commentLiner;
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
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView=inflater.inflate(R.layout.custonlistview_pack_tracs,parent,false);

            Name=(TextView)convertView.findViewById(R.id.Name);
            date=(TextView)convertView.findViewById(R.id.date);
            price=(TextView)convertView.findViewById(R.id.price);
            points=(TextView)convertView.findViewById(R.id.points);
            status=(TextView)convertView.findViewById(R.id.status);
            status2=(TextView)convertView.findViewById(R.id.status2);
            invoice=(TextView)convertView.findViewById(R.id.invoice);
            commentLiner=(LinearLayout) convertView.findViewById(R.id.commentLiner);

            String insertDate = DataList.get(position).get("purchase_date");
            String[] items1 = insertDate.split(" ");
            String d1=items1[0];
            String m1=items1[1];
            String[] items2 = d1.split("-");
            String d =items2[0];
            String m =items2[1];
            String y =items2[2];

            Name.setText(DataList.get(position).get("packageName"));
//            date.setText(DataList.get(position).get("purchase_date"));
            date.setText(y+"-"+m+"-"+d  +"    "+m1 );
            price.setText("₹ "+DataList.get(position).get("amount"));
            points.setText(DataList.get(position).get("points")+" Credits");

            if (DataList.get(position).get("creditDebit").equalsIgnoreCase("Credit")){
                status.setTextColor(Color.parseColor("#35a76a"));
            }
            else  if (DataList.get(position).get("creditDebit").equalsIgnoreCase("Debit")){
                status.setTextColor(Color.RED);
            }
            status.setText(DataList.get(position).get("creditDebit"));
            status2.setText("("+DataList.get(position).get("points")+")");

            if (DataList.get(position).get("packageName").equalsIgnoreCase("Admin")){
                commentLiner.setVisibility(View.VISIBLE);
                invoice.setText(DataList.get(position).get("admin_comment"));
                Name.setText("Added from Admin side");
            }

            return convertView;
        }
    }



}
