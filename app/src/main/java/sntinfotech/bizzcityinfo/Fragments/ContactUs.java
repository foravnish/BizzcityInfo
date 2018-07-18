package sntinfotech.bizzcityinfo.Fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import sntinfotech.bizzcityinfo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactUs extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;

    public ContactUs() {
        // Required empty public constructor
    }
    TextView sellerNo,buyerNo;

    private static View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        View view= inflater.inflate(R.layout.fragment_contact_us2, container, false);

        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.fragment_contact_us2, container, false);

        } catch (InflateException e) {
        /* map is already there, just return view as it is */
        }

        sellerNo= (TextView) view.findViewById(R.id.sellerNo);
        buyerNo= (TextView) view.findViewById(R.id.buyerNo);

        TextView enquiryNow=(TextView)view.findViewById(R.id.enquiryNow);
        enquiryNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment= new EnquiryNow();
                FragmentManager manager=getFragmentManager();
                FragmentTransaction ft=manager.beginTransaction().addToBackStack(null);
                ft.replace(R.id.container,fragment).commit();
            }
        });

        getActivity().setTitle("Contact us");

        MapFragment mapFragment1 = (MapFragment)getActivity().getFragmentManager().findFragmentById(R.id.map);
        mapFragment1.getMapAsync(this);

        sellerNo.setOnClickListener(new View.OnClickListener() {
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
                        callIntent.setData(Uri.parse("tel:" + "8766336834"));
                        startActivity(callIntent);
                    }
                    else {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + "8766336834"));
                        startActivity(callIntent);
                    }
                }
                catch (Exception ex)
                {ex.printStackTrace();
                }

            }

        });
        buyerNo.setOnClickListener(new View.OnClickListener() {
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
                        callIntent.setData(Uri.parse("tel:" + "01204136767"));
                        startActivity(callIntent);
                    }
                    else {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + "01204136767"));
                        startActivity(callIntent);
                    }
                }
                catch (Exception ex)
                {ex.printStackTrace();
                }
            }
        });
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(28.659609, 77.368437);
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(28.611847, 77.388483), 12.0f));

        mMap.addMarker(new MarkerOptions().position(sydney).title("BizzCityInfo"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera( CameraUpdateFactory.zoomTo(16));

    }
}
