package sntinfotech.bizzcityinfo.Fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sntinfotech.bizzcityinfo.PackagesPremimum;
import sntinfotech.bizzcityinfo.R;
import sntinfotech.bizzcityinfo.Utils.Api;
import sntinfotech.bizzcityinfo.Utils.MyPrefrences;
import sntinfotech.bizzcityinfo.Utils.Util;
import sntinfotech.bizzcityinfo.connection.JSONParser;

/**
 * A simple {@link Fragment} subclass.
 */
public class PremimumFragment extends Fragment {


    public PremimumFragment() {
        // Required empty public constructor
    }
    GridView gridview;
    List<HashMap<String,String>> DataList;
    Adapter adapter;
    Dialog dialog;
    String merKey,merId,salt;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_premimum, container, false);

        gridview=(GridView) view.findViewById(R.id.gridview);
        DataList =new ArrayList<>();
        new ShowPackage(getActivity(), MyPrefrences.getUserID(getActivity())).execute();



        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getActivity(),PackagesPremimum.class);
                intent.putExtra("id",DataList.get(i).get("id"));
                intent.putExtra("package_name",DataList.get(i).get("package_name"));
                intent.putExtra("actual_price",DataList.get(i).get("actual_price"));
                intent.putExtra("total_amount",DataList.get(i).get("total_amount"));
                intent.putExtra("old_price",DataList.get(i).get("old_price"));
                intent.putExtra("gst",DataList.get(i).get("gst"));
                intent.putExtra("gst_amount",DataList.get(i).get("gst_amount"));
                intent.putExtra("merId",merId);
                intent.putExtra("merKey",merKey);
                intent.putExtra("salt",salt);

                startActivity(intent);
            }
        });


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

                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            HashMap<String,String> map=new HashMap<>();
                            map.put("id",jsonObject1.optString("id"));
                            map.put("package_name",jsonObject1.optString("package_name"));
                            map.put("actual_price",jsonObject1.optString("actual_price"));
                            map.put("old_price",jsonObject1.optString("old_price"));
                            map.put("total_amount",jsonObject1.optString("total_amount"));
                            map.put("gst",jsonObject1.optString("gst"));
                            map.put("gst_amount",jsonObject1.optString("gst_amount"));




                            adapter=new Adapter();
                            gridview.setAdapter(adapter);
                            DataList.add(map);
                        }




                        JSONArray jsonArray2=jsonObject.getJSONArray("paymentDetails");
                        final JSONObject jsonObject1=jsonArray2.getJSONObject(0);

                        merId=jsonObject1.optString("memberId").toString();
                        merKey=jsonObject1.optString("merchantKey").toString();
                        salt=jsonObject1.optString("merchantSalt").toString();

                        Log.d("dsfdsgdfgdfghd",merId);
                        Log.d("dsfdsgdfgdfghd",merKey);
                        Log.d("dsfdsgdfgdfghd",salt);


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
        TextView mnthtext1,oldPrice,price1;
        LayoutInflater inflater;
        ImageView lock1,lock2,lock3,lock4,lock5;
        LinearLayout qImage1;
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
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.custom_package, parent, false);
            }
            mnthtext1=(TextView)convertView.findViewById(R.id.mnthtext1);
            oldPrice=(TextView)convertView.findViewById(R.id.oneMonth);
            price1=(TextView)convertView.findViewById(R.id.price1);


            mnthtext1.setText(DataList.get(position).get("package_name"));
            oldPrice.setText("₹ "+DataList.get(position).get("old_price"));
            price1.setText("₹ "+DataList.get(position).get("actual_price"));

            oldPrice.setPaintFlags(oldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);



//            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                    if (Integer.parseInt(DataList.get(i).get("green_lock"))<=0){
//                        Util.errorDialog(getActivity(),"This slot is full !");
//                    }
//                    else {
//                        Fragment fragment = new MatchingLeadsDetails();
//                        Bundle bundle = new Bundle();
//                        try {
//                            bundle.putString("leads", jsonArray.get(i).toString());
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        FragmentManager manager = getActivity().getSupportFragmentManager();
//                        FragmentTransaction transaction = manager.beginTransaction();
//                        fragment.setArguments(bundle);
//                        transaction.replace(R.id.container, fragment).addToBackStack(null).commit();
//                    }
//                }
//            });





//            imageLoader = AppController.getInstance().getImageLoader();
//
//            name.setText(DataList.get(position).getName().toString());
//            image.setImageUrl(DataList.get(position).getDesc().toLowerCase(),imageLoader);

            return convertView;
        }
    }




}
