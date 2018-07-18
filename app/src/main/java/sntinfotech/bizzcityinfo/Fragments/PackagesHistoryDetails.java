package sntinfotech.bizzcityinfo.Fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import sntinfotech.bizzcityinfo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PackagesHistoryDetails extends Fragment {


    public PackagesHistoryDetails() {
        // Required empty public constructor
    }
    JSONObject jsonObject;
    WebView webView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_packages_history_details, container, false);

        webView = (WebView) view.findViewById(R.id.webview);

        TextView name=(TextView)view.findViewById(R.id.name);
        TextView price=(TextView)view.findViewById(R.id.price);
        TextView pos_date=(TextView)view.findViewById(R.id.pos_date);
        TextView point=(TextView)view.findViewById(R.id.point);
        TextView tax=(TextView)view.findViewById(R.id.tax);
        TextView tamount=(TextView)view.findViewById(R.id.tamount);
        TextView status=(TextView)view.findViewById(R.id.status);
        TextView invoice=(TextView)view.findViewById(R.id.invoice);
        TextView pdf=(TextView)view.findViewById(R.id.pdf);




        try {
            jsonObject=new JSONObject(getArguments().getString("array_data"));

            String insertDate = jsonObject.optString("purchase_date");
            String[] items1 = insertDate.split(" ");
            String d1=items1[0];
            String m1=items1[1];
            String[] items2 = d1.split("-");
            String d =items2[0];
            String m =items2[1];
            String y =items2[2];


            name.setText(jsonObject.optString("packageName"));
            price.setText("₹ "+jsonObject.optString("amount"));
            pos_date.setText(y+"-"+m+"-"+d  +"    "+m1 );
            point.setText(jsonObject.optString("points")+" Credited");
            tax.setText("₹ "+jsonObject.optString("service_tax"));
            tamount.setText("₹ "+jsonObject.optString("total_amount"));
            status.setText(jsonObject.optString("creditDebit"));
            invoice.setText(jsonObject.optString("invoice_number"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

//        Log.d("ffgfgfgdfgdf",getArguments().getString("array_data",""));

        pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tempURL = jsonObject.optString("pdf");

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(tempURL));
                startActivity(browserIntent);

                webView.getSettings().setJavaScriptEnabled(true);
                webView.setVisibility(View.VISIBLE);
                webView.loadUrl(tempURL);
            }
        });
        return view;
    }

}
