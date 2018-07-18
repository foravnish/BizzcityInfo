package sntinfotech.bizzcityinfo.Fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.payumoney.sdkui.ui.widgets.CirclePageIndicator;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import sntinfotech.bizzcityinfo.MainActivity;
import sntinfotech.bizzcityinfo.ProfileAct;
import sntinfotech.bizzcityinfo.ProfileEdit;
import sntinfotech.bizzcityinfo.R;
import sntinfotech.bizzcityinfo.Utils.Api;
import sntinfotech.bizzcityinfo.Utils.Const;
import sntinfotech.bizzcityinfo.Utils.MyPrefrences;
import sntinfotech.bizzcityinfo.Utils.Util;
import sntinfotech.bizzcityinfo.connection.JSONParser;

/**
 * A simple {@link Fragment} subclass.
 */
public class Deshboard extends Fragment {


    public Deshboard() {
        // Required empty public constructor
    }

    TextView points,membershipType,purLead,profile,buyLead,matchLead,trans,inbox,expiry;
    Dialog dialog;
    LinearLayout  myEnquiry,matchinLay,purchaLay,memberShip,transct;
    ViewPager viewPager2;
    CircleIndicator indicat;
    CustomPagerAdapter2 mCustomPagerAdapter2;
    List<Const> AllBaner   = new ArrayList<>();
    int currentPage = 0;
    RelativeLayout rela1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_deshboard2, container, false);
        getActivity().setTitle("My Dashboard");


        viewPager2 = (ViewPager) view.findViewById(R.id.slider2);
        indicat = (CircleIndicator)view.findViewById(R.id.indicator);
        rela1 = (RelativeLayout) view.findViewById(R.id.rela1);

        dialog=new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
      //  Util.showPgDialog(dialog);


        inbox=(TextView)view.findViewById(R.id.inbox);
        points=(TextView)view.findViewById(R.id.points);
        membershipType=(TextView)view.findViewById(R.id.membershipType);
        purLead=(TextView)view.findViewById(R.id.purLead);
        profile=(TextView)view.findViewById(R.id.profile);
        buyLead=(TextView)view.findViewById(R.id.buyLead);
        matchLead=(TextView)view.findViewById(R.id.matchLead);
        trans=(TextView)view.findViewById(R.id.trans);
        expiry=(TextView)view.findViewById(R.id.expiry);

        myEnquiry=(LinearLayout) view.findViewById(R.id.myEnquiry);
        matchinLay=(LinearLayout) view.findViewById(R.id.matchinLay);
        purchaLay=(LinearLayout) view.findViewById(R.id.purchaLay);
        memberShip=(LinearLayout) view.findViewById(R.id.memberShip);
        transct=(LinearLayout) view.findViewById(R.id.transct);

        myEnquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new Inbox();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction().addToBackStack(null);
                ft.replace(R.id.container, fragment).commit();
            }
        });
        matchinLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new Matchingleads();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction().addToBackStack(null);
                ft.replace(R.id.container, fragment).commit();
            }
        });

        purchaLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new PurchasedLeads();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction().addToBackStack(null);
                ft.replace(R.id.container, fragment).commit();
            }
        });

        memberShip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new PremimumPage();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction().addToBackStack(null);
                ft.replace(R.id.container, fragment).commit();
            }
        });

        transct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new TranasactionHistory();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction().addToBackStack(null);
                ft.replace(R.id.container, fragment).commit();
            }
        });


        new  Dashboard(getActivity()).execute();

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(),ProfileAct.class));
            }
        });

        buyLead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fragment fragment = new BuyPackages();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction().addToBackStack(null);
                ft.replace(R.id.container, fragment).commit();
            }
        });
        return view;
    }

    private class Dashboard extends AsyncTask<String, Void, String> {
        Context context;
        public Dashboard(Context context) {
            this.context = context;
            dialog=new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            try {
                Util.showPgDialog(dialog);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map=new HashMap<>();

            map.put("function","sellerSideMenueCountings");
            map.put("sellerId", MyPrefrences.getUserID(getActivity()));



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

                       JSONObject jsonObject1=jsonObject.getJSONObject("message");

                        inbox.setText(jsonObject1.optString("inbox"));
                        points.setText(jsonObject1.optString("credit_points"));
                        purLead.setText(jsonObject1.optString("purchased_leads"));
                        matchLead.setText(jsonObject1.optString("matching_leads"));
                        trans.setText(jsonObject1.optString("purchased_leads"));

                        if (jsonObject1.optString("premium").equalsIgnoreCase("No")){

                            membershipType.setText("Free");
                            expiry.setVisibility(View.GONE);
                        }
                        else if (jsonObject1.optString("premium").equalsIgnoreCase("Yes")){
                            membershipType.setText("Primo");
                            expiry.setVisibility(View.VISIBLE);
                            expiry.setText("Expiry - "+jsonObject1.optString("premiumExpiryDate"));
//
                        }


                        JSONArray jsonArray = jsonObject1.getJSONArray("bannerImages");

                        if (jsonArray.length()==0){

                            rela1.setVisibility(View.GONE);
                        }
                        else {


                            rela1.setVisibility(View.VISIBLE);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject2 = jsonArray.getJSONObject(i);

                                AllBaner.add(new Const(jsonObject2.optString("id"), jsonObject2.optString("bannerPhoto"), null, null, null, null, null, null, null, null));

                                mCustomPagerAdapter2 = new CustomPagerAdapter2(getActivity());
                                viewPager2.setAdapter(mCustomPagerAdapter2);
                                indicat.setViewPager(viewPager2);
                                mCustomPagerAdapter2.notifyDataSetChanged();

                            }


                            final Handler handler = new Handler();
                            final Runnable update = new Runnable() {
                                public void run() {
                                    if (currentPage == AllBaner.size()) {
                                        currentPage = 0;
                                    }
                                    viewPager2.setCurrentItem(currentPage++);
                                }
                            };
                            new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    handler.post(update);
                                }
                            }, 100, 5000);
                        }

                    }

                }
            } catch (JSONException e) {
                Util.cancelPgDialog(dialog);
                Util.errorDialog(context,"Please connect to the Internet...");
                e.printStackTrace();
            }
        }

    }


    class CustomPagerAdapter2 extends PagerAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;

        public CustomPagerAdapter2(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return AllBaner.size();
        }



        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((RelativeLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View itemView = mLayoutInflater.inflate(R.layout.page_item, container, false);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);

            Log.d("dfgsdgdgdfgdf",AllBaner.get(position).getCatid().toString());

            Picasso.with(getActivity()).load(AllBaner.get(position).getCatid().toString()).into(imageView);


//            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
//
//            imageView.setImageUrl(AllBaner.get(position).getPhoto().toString().replace(" ","%20"),imageLoader);
//
//
//            imageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (AllBaner.get(position).getOrgby().toString().isEmpty() ) {
//
//                        //  Toast.makeText(getActivity(), "blank", Toast.LENGTH_SHORT).show();
//                    }
//                    else{
////
//                    }
//                }
//            });

            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((RelativeLayout) object);
        }
    }



}
