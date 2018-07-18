package sntinfotech.bizzcityinfo.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sntinfotech.bizzcityinfo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TermAndCondition extends Fragment {


    public TermAndCondition() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_term_and_condition, container, false);
        getActivity().setTitle("Terms & Conditions");

        return view;
    }



}
