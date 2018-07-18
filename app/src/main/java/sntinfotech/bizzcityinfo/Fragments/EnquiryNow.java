package sntinfotech.bizzcityinfo.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.TextViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import sntinfotech.bizzcityinfo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EnquiryNow extends Fragment {


    public EnquiryNow() {
        // Required empty public constructor
    }

    EditText name,email,mobleNo,city,message;
    Button submit;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_contact_us, container, false);
        name=(EditText)view.findViewById(R.id.name);
        email=(EditText)view.findViewById(R.id.email);
        mobleNo=(EditText)view.findViewById(R.id.mobileNo);
        city=(EditText)view.findViewById(R.id.city);
        message=(EditText)view.findViewById(R.id.message);
        submit=(Button) view.findViewById(R.id.submit);

        getActivity().setTitle("Enquiry Form");
        return view;
    }

}
