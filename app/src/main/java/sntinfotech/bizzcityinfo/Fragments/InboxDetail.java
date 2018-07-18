package sntinfotech.bizzcityinfo.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sntinfotech.bizzcityinfo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InboxDetail extends Fragment {


    public InboxDetail() {
        // Required empty public constructor
    }

    TextView userName,date,sub,message,name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_inbox_detail, container, false);

        userName= (TextView) view.findViewById(R.id.textView9);
        date= (TextView) view.findViewById(R.id.textView10);
        sub= (TextView) view.findViewById(R.id.textView11);
        message= (TextView) view.findViewById(R.id.textView7);
        name= (TextView) view.findViewById(R.id.textView6);

        getActivity().setTitle("Inbox Details");

        Log.d("dgdfgdfhdhdghd",getArguments().getString("data"));


        try {
            JSONArray jsonArray=new JSONArray(getArguments().getString("data"));
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);

                userName.setText(jsonObject.optString("uname"));
                date.setText(jsonObject.optString("posted_date"));
                sub.setText(jsonObject.optString("email"));
                message.setText(jsonObject.optString("requirement"));
                name.setText(jsonObject.optString("uname"));


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }

}
