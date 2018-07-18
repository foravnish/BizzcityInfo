package sntinfotech.bizzcityinfo.Fragments;


import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sntinfotech.bizzcityinfo.Fragments.AddOffers;
import sntinfotech.bizzcityinfo.R;
import sntinfotech.bizzcityinfo.Utils.Api;
import sntinfotech.bizzcityinfo.Utils.MyPrefrences;
import sntinfotech.bizzcityinfo.Utils.Util;
import sntinfotech.bizzcityinfo.connection.JSONParser;


/**
 * A simple {@link Fragment} subclass.
 */
public class OfferPostOption extends Fragment {


    public OfferPostOption() {
        // Required empty public constructor
    }

    TextView manageBtn;
    TextView manageBtn2;
    TextView manageBtn3;
    ListView gridview;
   // Adapter adapter;
    List<HashMap<String,String>> DataList;
    ImageView banner;
    ImageView banner2;
    ImageView banner3;
    TextView heading,desc,discount,date;
    TextView heading2,desc2,discount2,date2;
    TextView heading3,desc3,discount3,date3;
    Dialog dialog;
    JSONArray jsonArray;
    String arraData="";
    String arraData2="";
    String arraData3="";
    JSONObject jsonObject1;
    JSONObject jsonObject2;
    JSONObject jsonObject3;
    String member;
    String offerID="";
    String offerID2="";
    String offerID3="";
    RelativeLayout relative1;
    LinearLayout linear1;
    TextView spinerCat;
    SwitchCompat offerOnOff;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_offer_post_option1, container, false);

//        gridview= (ListView) view.findViewById(R.id.gridview);

        getActivity().setTitle("Offer");
        //DataList=new ArrayList<>();
//        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
//        View headerView = layoutInflater.inflate(R.layout.test_header_view, null);

       // heading= (TextView) view.findViewById(R.id.heading);
       // desc= (TextView) view.findViewById(R.id.desc);
       // discount= (TextView) view.findViewById(R.id.discount);
       // date= (TextView) view.findViewById(R.id.date);
        //idval= (TextView) view.findViewById(R.id.idval);
        //banner= (ImageView) view.findViewById(R.id.banner);
        //manageBtn= (TextView) view.findViewById(R.id.manageBtn);

       // heading2= (TextView) view.findViewById(R.id.heading2);
      //  desc2= (TextView) view.findViewById(R.id.desc2);
       // discount2= (TextView) view.findViewById(R.id.discount2);
      //  date2= (TextView) view.findViewById(R.id.date2);
        //idval= (TextView) view.findViewById(R.id.idval);
      //  banner2= (ImageView) view.findViewById(R.id.banner2);
      //  manageBtn2= (TextView) view.findViewById(R.id.manageBtn2);

        heading3= (TextView) view.findViewById(R.id.heading3);
        //desc3= (TextView) view.findViewById(R.id.desc3);
       // discount3= (TextView) view.findViewById(R.id.discount3);
       // date3= (TextView) view.findViewById(R.id.date3);
        //idval= (TextView) view.findViewById(R.id.idval);
      //  banner3= (ImageView) view.findViewById(R.id.banner3);
        manageBtn3= (TextView) view.findViewById(R.id.manageBtn3);
        spinerCat= (TextView) view.findViewById(R.id.spinerCat);
        offerOnOff= (SwitchCompat) view.findViewById(R.id.offerOnOff);

//        relative1= (RelativeLayout) view.findViewById(R.id.relative1);
//        linear1= (LinearLayout) view.findViewById(R.id.linear1);
        new ShowOffer(getActivity()).execute();

      //  gridview.addHeaderView(headerView);

//        banner.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Fragment fragment = new AddOffers();
//                Bundle bundle=new Bundle();
//                bundle.putString("id","");
//                bundle.putString("array","");
//                bundle.putString("position","1");
//                bundle.putString("package","free");
//                FragmentManager manager = getFragmentManager();
//                FragmentTransaction ft = manager.beginTransaction().addToBackStack(null);
//                fragment.setArguments(bundle);
//                ft.replace(R.id.container, fragment).commit();
//            }
//        });
//        manageBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Fragment fragment = new AddOffers();
//                Bundle bundle=new Bundle();
//                bundle.putString("id",offerID);
//                bundle.putString("array",arraData);
//                bundle.putString("position","1");
//                bundle.putString("package","free");
//                FragmentManager manager = getFragmentManager();
//                FragmentTransaction ft = manager.beginTransaction().addToBackStack(null);
//                fragment.setArguments(bundle);
//                ft.replace(R.id.container, fragment).commit();
//            }
//        });
//
//
//        manageBtn2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Fragment fragment = new AddOffers();
//                Bundle bundle=new Bundle();
//                bundle.putString("id",offerID2);
//                bundle.putString("array",arraData2);
//                bundle.putString("position","2");
//                bundle.putString("package","paid");
//                FragmentManager manager = getFragmentManager();
//                FragmentTransaction ft = manager.beginTransaction().addToBackStack(null);
//                fragment.setArguments(bundle);
//                ft.replace(R.id.container, fragment).commit();
//            }
//        });
        manageBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new AddOffers();
                Bundle bundle=new Bundle();
                bundle.putString("id",offerID3);
                bundle.putString("array",arraData3);
                bundle.putString("position","3");
                bundle.putString("package","paid");
                FragmentManager manager = getFragmentManager();
                FragmentTransaction ft = manager.beginTransaction().addToBackStack(null);
                fragment.setArguments(bundle);
                ft.replace(R.id.container, fragment).commit();
            }
        });

//        HashMap<String,String> map=new HashMap<>();
//        for (int i=0;i<1;i++){
//            map.put("name","name");
//            DataList.add(map);
//            adapter=new Adapter();
//            gridview.setAdapter(adapter);
//        }

        return view;
    }


    private class ShowOffer extends AsyncTask<String, Void, String> {
        Context context;

        public ShowOffer(Context context) {
            this.context = context;
            dialog=new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Util.showPgDialog(dialog);
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map=new HashMap<>();

            map.put("function","myOfferShow");
            map.put("myId", MyPrefrences.getUserID(getActivity()));

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

                        manageBtn3.setText("Manage Offer");
//                        linear1.setVisibility(View.VISIBLE);
//                        relative1.setVisibility(View.GONE);

                        jsonArray=jsonObject.getJSONArray("message");

                        for (int i=0;i<jsonArray.length();i++) {




//                            if (i == 0) {
//                                //Toast.makeText(context, "1", Toast.LENGTH_SHORT).show();
//                                jsonObject1 = jsonArray.getJSONObject(0);
//                                arraData = jsonObject1.toString();
//                                HashMap<String, String> map = new HashMap<>();
//                                map.put("id", jsonObject1.optString("id").toString());
//                                map.put("heading", jsonObject1.optString("heading").toString());
//                                map.put("description", jsonObject1.optString("description").toString());
//                                map.put("offer_type", jsonObject1.optString("offer_type").toString());
//                                map.put("discount", jsonObject1.optString("discount").toString());
//                                map.put("actual_price", jsonObject1.optString("actual_price").toString());
//                                map.put("offer_price", jsonObject1.optString("offer_price").toString());
//                                map.put("coupon_code", jsonObject1.optString("coupon_code").toString());
//                                map.put("offer_from", jsonObject1.optString("offer_from").toString());
//                                map.put("offer_to", jsonObject1.optString("offer_to").toString());
//                               // map.put("image", jsonObject1.optString("image").toString());
//                                map.put("posted_date", jsonObject1.optString("posted_date").toString());
//                                map.put("membership", jsonObject1.optString("membership").toString());
//
//                                offerID = jsonObject1.optString("id").toString();
//                                heading.setText(jsonObject1.optString("heading").toString());
//                                desc.setText(jsonObject1.optString("description").toString());
//
//                                if (jsonObject1.optString("discount").toString().equalsIgnoreCase("Select Discount")) {
//                                    discount.setText("₹ " + jsonObject1.optString("offer_price").toString());
//                                } else {
//                                    discount.setText(jsonObject1.optString("discount").toString() + " OFF");
//                                }
//                                date.setText(jsonObject1.optString("posted_date").toString());
//                                //Picasso.with(context).load("http://bizzcityinfo.com/offerImage/" + jsonObject1.optString("image").toString()).memoryPolicy(MemoryPolicy.NO_CACHE).into(banner);
//
//                            }
//                            else if (i==1){
//                                jsonObject2 = jsonArray.getJSONObject(1);
//                                arraData2 = jsonObject2.toString();
//                                HashMap<String, String> map = new HashMap<>();
//                                map.put("id", jsonObject2.optString("id").toString());
//                                map.put("heading", jsonObject2.optString("heading").toString());
//                                map.put("description", jsonObject2.optString("description").toString());
//                                map.put("offer_type", jsonObject2.optString("offer_type").toString());
//                                map.put("discount", jsonObject2.optString("discount").toString());
//                                map.put("actual_price", jsonObject2.optString("actual_price").toString());
//                                map.put("offer_price", jsonObject2.optString("offer_price").toString());
//                                map.put("coupon_code", jsonObject2.optString("coupon_code").toString());
//                                map.put("offer_from", jsonObject2.optString("offer_from").toString());
//                                map.put("offer_to", jsonObject2.optString("offer_to").toString());
//                               // map.put("image", jsonObject2.optString("image").toString());
//                                map.put("posted_date", jsonObject2.optString("posted_date").toString());
//                                map.put("membership", jsonObject2.optString("membership").toString());
//
//                                offerID2 = jsonObject2.optString("id").toString();
//                                heading2.setText(jsonObject2.optString("heading").toString());
//                                desc2.setText(jsonObject2.optString("description").toString());
//
//                                if (jsonObject2.optString("discount").toString().equalsIgnoreCase("Select Discount")) {
//                                    discount2.setText("₹ " + jsonObject2.optString("offer_price").toString());
//                                } else {
//                                    discount2.setText(jsonObject2.optString("discount").toString() + " OFF");
//                                }
//                                date2.setText(jsonObject2.optString("posted_date").toString());
//                               // Picasso.with(context).load("http://bizzcityinfo.com/offerImage/" + jsonObject2.optString("image").toString()).memoryPolicy(MemoryPolicy.NO_CACHE).into(banner2);
//
//                            }
//                            else if (i==2){
//                                jsonObject3 = jsonArray.getJSONObject(2);
//                                arraData3 = jsonObject3.toString();
//                                HashMap<String, String> map = new HashMap<>();
//                                map.put("id", jsonObject3.optString("id").toString());
//                                map.put("heading", jsonObject3.optString("heading").toString());
//                                map.put("description", jsonObject3.optString("description").toString());
//                                map.put("offer_type", jsonObject3.optString("offer_type").toString());
//                                map.put("discount", jsonObject3.optString("discount").toString());
//                                map.put("actual_price", jsonObject3.optString("actual_price").toString());
//                                map.put("offer_price", jsonObject3.optString("offer_price").toString());
//                                map.put("coupon_code", jsonObject3.optString("coupon_code").toString());
//                                map.put("offer_from", jsonObject3.optString("offer_from").toString());
//                                map.put("offer_to", jsonObject3.optString("offer_to").toString());
//                                //map.put("image", jsonObject3.optString("image").toString());
//                                map.put("posted_date", jsonObject3.optString("posted_date").toString());
//                                map.put("membership", jsonObject3.optString("membership").toString());
//
//                                offerID3 = jsonObject3.optString("id").toString();
//                                heading3.setText(jsonObject3.optString("heading").toString());
//                                desc3.setText(jsonObject3.optString("description").toString());
//
//                                if (jsonObject3.optString("discount").toString().equalsIgnoreCase("Select Discount")) {
//                                    discount3.setText("₹ " + jsonObject3.optString("offer_price").toString());
//                                } else {
//                                    discount3.setText(jsonObject3.optString("discount").toString() + " OFF");
//                                }
//                                date3.setText(jsonObject3.optString("posted_date").toString());
//                                //Picasso.with(context).load("http://bizzcityinfo.com/offerImage/" + jsonObject3.optString("image").toString()).memoryPolicy(MemoryPolicy.NO_CACHE).into(banner3);
//
//                            }

                            jsonObject3 = jsonArray.getJSONObject(0);
                            arraData3 = jsonObject3.toString();
                            HashMap<String, String> map = new HashMap<>();
                            map.put("id", jsonObject3.optString("id").toString());
                            map.put("subCatName", jsonObject3.optString("subCatName").toString());
                            map.put("heading", jsonObject3.optString("heading").toString());
                            map.put("description", jsonObject3.optString("description").toString());
                            map.put("offer_type", jsonObject3.optString("offer_type").toString());
                            map.put("discount", jsonObject3.optString("discount").toString());
                            map.put("actual_price", jsonObject3.optString("actual_price").toString());
                            map.put("offer_price", jsonObject3.optString("offer_price").toString());
                            map.put("coupon_code", jsonObject3.optString("coupon_code").toString());
                            map.put("offer_from", jsonObject3.optString("offer_from").toString());
                            map.put("offer_to", jsonObject3.optString("offer_to").toString());
                            //map.put("image", jsonObject3.optString("image").toString());
                            map.put("posted_date", jsonObject3.optString("posted_date").toString());
                            map.put("membership", jsonObject3.optString("membership").toString());

                           // offerID3 = jsonObject3.optString("id").toString();

                            heading3.setText(jsonObject3.optString("heading").toString());
                            spinerCat.setText(jsonObject3.optString("subCatName").toString());

                            if (jsonObject3.optString("status").equalsIgnoreCase("1")){
                                offerOnOff.setChecked(true);
                            }
                            else if (jsonObject3.optString("status").equalsIgnoreCase("0"))
                            {
                                offerOnOff.setChecked(false);
                            }


                            offerOnOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                                    new managerOffer(getActivity(),jsonObject3.optString("status"),jsonObject3.optString("id")).execute();

//                                    if (b){
//
//
//                                    }
//                                    else{
//
//                                    }
                                }
                            });


                            // desc3.setText(jsonObject3.optString("description").toString());

//                            if (jsonObject3.optString("discount").toString().equalsIgnoreCase("Select Discount")) {
//                                discount3.setText("₹ " + jsonObject3.optString("offer_price").toString());
//                            } else {
//                                discount3.setText(jsonObject3.optString("discount").toString() + " OFF");
//                            }
//                            date3.setText(jsonObject3.optString("posted_date").toString());
                        }

//                        else if (jsonArray.length()==2){
//                            //Toast.makeText(context, "2", Toast.LENGTH_SHORT).show();
//                        }
//                        else if (jsonArray.length()==3){
//                            //Toast.makeText(context, "3", Toast.LENGTH_SHORT).show();
//                        }
                        //arraData=jsonArray.toString();
                     //   for (int i=0;i<jsonArray.length();i++){

                            //jsonObject2=jsonArray.getJSONObject(1);
                           // jsonObject3=jsonArray.getJSONObject(2);


                            //arraData2= jsonObject1.toString();
                          //  arraData3= jsonObject1.toString();


                            //offerID2=jsonObject2.optString("id").toString();
                           // offerID3=jsonObject3.optString("id").toString();



                            //idval.setText(jsonObject1.optString("id").toString());


                            //heading2.setText(jsonObject2.optString("heading").toString());
//                            heading3.setText(jsonObject3.optString("heading").toString());

                          //  desc2.setText(jsonObject2.optString("description").toString());
                           // desc3.setText(jsonObject3.optString("description").toString());



//                        if (jsonObject2.optString("discount").toString().equalsIgnoreCase("Select Discount")){
//                            discount2.setText("₹ "+jsonObject2.optString("offer_price").toString());
//                        }
//                        else{
//                            discount2.setText(jsonObject2.optString("discount").toString()+" OFF");
//                        }

//                        if (jsonObject3.optString("discount").toString().equalsIgnoreCase("Select Discount")){
//                            discount3.setText("₹ "+jsonObject3.optString("offer_price").toString());
//                        }
//                        else{
//                            discount3.setText(jsonObject1.optString("discount").toString()+" OFF");
//                        }


                           // date2.setText(jsonObject1.optString("posted_date").toString());
                          //  date3.setText(jsonObject3.optString("posted_date").toString());


                           // Picasso.with(context).load("http://bizzcityinfo.com/offerImage/"+jsonObject2.optString("image").toString()).memoryPolicy(MemoryPolicy.NO_CACHE).into(banner2);
                          //  Picasso.with(context).load("http://bizzcityinfo.com/offerImage/"+jsonObject3.optString("image").toString()).memoryPolicy(MemoryPolicy.NO_CACHE).into(banner2);
//                            adapter=new Adapter();
//                            gridview.setAdapter(adapter);
                       // }
//
                    }
                    else if (jsonObject.optString("status").equalsIgnoreCase("failure")){

                        if (jsonObject.optString("message").equalsIgnoreCase("Records Not Available")){
                            manageBtn3.setText("Add Offer");
                        }
                        else {
                            arraData = "";
                            arraData2 = "";
                            arraData2 = "";
                        }
                        //linear1.setVisibility(View.GONE);
                       // relative1.setVisibility(View.VISIBLE);

                        //  Util.errorDialog(context,"Records Not Available...");
                    }
                }
            } catch (JSONException e) {
                Util.errorDialog(context,"Some Error! Please try again...");
                e.printStackTrace();
            }
        }

    }



    private class managerOffer extends AsyncTask<String, Void, String> {
        Context context;
        String status,id;

        public managerOffer(Context context,String status,String  id) {
            this.context = context;
            this.status=status;
            this.id=id;
            dialog=new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            //Util.showPgDialog(dialog);
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map=new HashMap<>();

            map.put("function","manageOfferStatus");
            map.put("offerId", id);
            if (status.equalsIgnoreCase("0")){
                map.put("status","off" );
            }
            else if (status.equalsIgnoreCase("1")) {
                map.put("status", "on");
            }

            JSONParser jsonParser=new JSONParser();
            String result =jsonParser.makeHttpRequest(Api.Login,"GET",map);

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.e("response", ": " + s);
            //Util.cancelPgDialog(dialog);

            new ShowOffer(getActivity()).execute();

        }

    }


    class Adapter extends BaseAdapter {
        TextView points,require,name,address,postedDate,subCat,leadNo;
        LayoutInflater inflater;
        ImageView lock1,lock2,lock3,lock4,lock5;
        LinearLayout linerAddoffer;
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
                convertView = inflater.inflate(R.layout.custum_offer_list, parent, false);
            }

            linerAddoffer= (LinearLayout) convertView.findViewById(R.id.linerAddoffer);

            //linerAddoffer.setClickable(false);

            linerAddoffer.setEnabled(false);

         //   Log.d("sfsdfgsdgsdfgsdfg",member);

            linerAddoffer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(), "click", Toast.LENGTH_SHORT).show();
                }
            });


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
