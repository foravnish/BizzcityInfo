package sntinfotech.bizzcityinfo.Fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import sntinfotech.bizzcityinfo.R;
import sntinfotech.bizzcityinfo.Utils.MyPrefrences;

/**
 * A simple {@link Fragment} subclass.
 */
public class PurchasedLeadDetails extends Fragment {


    public PurchasedLeadDetails() {
        // Required empty public constructor
    }
    JSONObject jsonObject;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view=inflater.inflate(R.layout.fragment_purchased_lead_details, container, false);

        TextView name=(TextView)view.findViewById(R.id.name);
        TextView location=(TextView)view.findViewById(R.id.location);
        TextView pos_date=(TextView)view.findViewById(R.id.pos_date);
        TextView email=(TextView)view.findViewById(R.id.email);
        TextView mobile=(TextView)view.findViewById(R.id.mobile);
        TextView category=(TextView)view.findViewById(R.id.category);
        TextView requires=(TextView)view.findViewById(R.id.requires);
        TextView points2=(TextView)view.findViewById(R.id.points2);
        TextView leads=(TextView)view.findViewById(R.id.leads);
        Button callNow=(Button)view.findViewById(R.id.callNow);
        TextView date=(TextView)view.findViewById(R.id.date);
        TextView time=(TextView)view.findViewById(R.id.time);
        Log.d("fgdfgdfhgd",getArguments().getString("leads"));

        getActivity().setTitle("Purchased Details");
        try {
            jsonObject=new JSONObject(getArguments().getString("leads"));
            name.setText(jsonObject.optString("uname"));
            location.setText(jsonObject.optString("city"));
            pos_date.setText(jsonObject.optString("posted_date"));
            if (jsonObject.optString("email").equals("")){
                email.setText("N/A");
            }
            else{
                email.setText(jsonObject.optString("email"));
            }
//            email.setText("xxxxxxxxx@gmail.com");
                mobile.setText(jsonObject.optString("mobile"));
//            mobile.setText("XXXXXXXXX");
            category.setText(jsonObject.optString("subcat_id"));
            requires.setText(jsonObject.optString("requirement"));
            points2.setText(jsonObject.optString("point"));
            leads.setText("Lead # "+jsonObject.optString("id"));
            date.setText(jsonObject.optString("posted_date"));

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
        callNow.setOnClickListener(new View.OnClickListener() {
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
                        callIntent.setData(Uri.parse("tel:" + jsonObject.optString("mobile")));
                        startActivity(callIntent);
                    }
                    else {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + jsonObject.optString("mobile")));
                        startActivity(callIntent);
                    }
                }
                catch (Exception ex)
                {ex.printStackTrace();
                }


               // Toast.makeText(getActivity(), "calling", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
