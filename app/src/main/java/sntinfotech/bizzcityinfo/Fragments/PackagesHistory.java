package sntinfotech.bizzcityinfo.Fragments;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

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
public class PackagesHistory extends Fragment {
    JSONArray jsonArray;
    Dialog dialog;
    GridView gridview;
    Adapter adapter;
    List<HashMap<String, String>> DataList;
    WebView webView;
    TextView textview;

    public PackagesHistory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_packages_history, container, false);

        gridview = (GridView) view.findViewById(R.id.gridview);
        webView = (WebView) view.findViewById(R.id.webview);
        DataList = new ArrayList<>();
        textview = (TextView) view.findViewById(R.id.textview);

        getActivity().setTitle("Package Purchased");

        new PackagesHistoryApi(getActivity()).execute();
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
            HashMap<String, String> map = new HashMap<>();

            map.put("function", "myPurchasedPackage");
            map.put("memberId", MyPrefrences.getUserID(getActivity()).toString());

            JSONParser jsonParser = new JSONParser();
            String result = jsonParser.makeHttpRequest(Api.Login, "GET", map);

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

                        jsonArray = jsonObject.getJSONArray("message");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            HashMap<String, String> map = new HashMap<>();
                            map.put("purchase_date", jsonObject1.optString("purchase_date").toString());
                            map.put("creditDebit", jsonObject1.optString("creditDebit").toString());
                            map.put("packageName", jsonObject1.optString("packageName").toString());
                            map.put("amount", jsonObject1.optString("amount").toString());
                            map.put("service_tax", jsonObject1.optString("service_tax").toString());
                            map.put("total_amount", jsonObject1.optString("total_amount").toString());
                            map.put("points", jsonObject1.optString("points").toString());
                            map.put("invoice_number", jsonObject1.optString("invoice_number").toString());
                            map.put("pdf", jsonObject1.optString("pdf").toString());

                            DataList.add(map);
                            adapter = new Adapter();
                            gridview.setAdapter(adapter);
                        }

                    }

                    else {
//                        Util.errorDialog(context,jsonObject.optString("message"));
                        textview.setText("No Packages History");
                        textview.setVisibility(View.VISIBLE);
                        gridview.setVisibility(View.GONE);
                    }

                }
            } catch (JSONException e) {
                Util.errorDialog(context, "Some Error! Please try again...");
                e.printStackTrace();
            }
        }

    }

    static class ViewHolder {

        TextView Name, date, price, points, discount;

        TextView pdf;

    }


    class Adapter extends BaseAdapter {
        LayoutInflater inflater;

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
            ViewHolder viewHolder;
            convertView = inflater.inflate(R.layout.custonlistview_pack_history, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.Name = (TextView) convertView.findViewById(R.id.Name);
            viewHolder.date = (TextView) convertView.findViewById(R.id.date);
            viewHolder.price = (TextView) convertView.findViewById(R.id.price);
            viewHolder.points = (TextView) convertView.findViewById(R.id.points);
            viewHolder.pdf = (TextView) convertView.findViewById(R.id.pdf);

            viewHolder.Name.setText(DataList.get(position).get("packageName"));
            viewHolder.date.setText(DataList.get(position).get("purchase_date"));
            viewHolder.price.setText(DataList.get(position).get("total_amount"));
            viewHolder.points.setText(DataList.get(position).get("points") + " Credit Points");

            if (DataList.get(position).get("pdf").toString().equals("")){
                viewHolder.pdf.setVisibility(View.GONE);
            }
            else {
                viewHolder.pdf.setVisibility(View.VISIBLE);
            }
            viewHolder.pdf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String tempURL = DataList.get(position).get("pdf").toString();
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(tempURL));
                    startActivity(browserIntent);
                    //gridview.setVisibility(View.GONE);

                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.setVisibility(View.VISIBLE);
                    webView.loadUrl(tempURL);

                }
            });


//            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                    Fragment fragment = new PackagesHistoryDetails();
//                    Bundle bundle = new Bundle();
//                    try {
//                        bundle.putString("array_data", jsonArray.get(i).toString());
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    FragmentManager manager = getActivity().getSupportFragmentManager();
//                    FragmentTransaction transaction = manager.beginTransaction();
//                    fragment.setArguments(bundle);
//                    transaction.replace(R.id.container, fragment).addToBackStack(null).commit();
//                }
//            });


            return convertView;
        }
    }


}



