package sntinfotech.bizzcityinfo.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.payumoney.sdkui.ui.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import sntinfotech.bizzcityinfo.Login;
import sntinfotech.bizzcityinfo.MainActivity;
import sntinfotech.bizzcityinfo.R;
import sntinfotech.bizzcityinfo.Splash;
import sntinfotech.bizzcityinfo.Utils.Api;
import sntinfotech.bizzcityinfo.Utils.MyPrefrences;
import sntinfotech.bizzcityinfo.Utils.Util;
import sntinfotech.bizzcityinfo.connection.JSONParser;

public class ChangePwd extends Fragment {



    public ChangePwd() {
        // Required empty public constructor
    }

    Button submit;
    EditText confirm,newPwd,password;
    Dialog dialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_change_pwd, container, false);
        password= (EditText) view.findViewById(R.id.password);
        newPwd= (EditText) view.findViewById(R.id.newPwd);
        confirm= (EditText) view.findViewById(R.id.confirm);

        submit= (Button) view.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checkValidation()){

                    new ChangePassword(getActivity(),password.getText().toString(),newPwd.getText().toString()).execute();


                }
            }
        });
        getActivity().setTitle("Change Password");
        return view;

    }

    private boolean  checkValidation() {
        if (TextUtils.isEmpty(password.getText().toString()))
        {
            password.setError("Oops! Password blank");
            password.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(newPwd.getText().toString()))
        {
            newPwd.setError("Oops! New Password blank");
            newPwd.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(confirm.getText().toString())){
            confirm.setError("Oops! Confirm Password blank");
            confirm.requestFocus();
            return false;
        }
        else if (!confirm.getText().toString().equals(newPwd.getText().toString())){

            Util.errorDialog(getActivity(),"Confirm Password must be same !");
            return false;
        }
        else if (password.getText().toString().length()<3){
            password.setError("Oops! Password length must 3 ");
            password.requestFocus();
            return false;
        }

        else if (newPwd.getText().toString().length()<3){
            newPwd.setError("Oops! New Password length must 3 ");
            newPwd.requestFocus();
            return false;
        }

        else if (confirm.getText().toString().length()<3){
            confirm.setError("Oops! Confirm Password length must 3 ");
            confirm.requestFocus();
            return false;
        }
        return true;
    }


    private class ChangePassword extends AsyncTask<String, Void, String> {
        Context context;
        String oldpass,newPass;
        Dialog dialog2;
        public ChangePassword(Context context, String oldpass, String newPass) {
            this.context = context;
            this.oldpass=oldpass;
            this.dialog2=dialog2;
            this.newPass=newPass;


            dialog=new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Util.showPgDialog(dialog);
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map=new HashMap<>();

            map.put("function","changePassword2");
            map.put("user_id", MyPrefrences.getUserID(getActivity()));
            map.put("oldpassword", oldpass.toString());
            map.put("newpassword", newPass.toString());


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
                        Toast.makeText(context, "Password Successfully Changed...", Toast.LENGTH_SHORT).show();
//                        Util.errorDialog(context,"Password Successfully Changed...");

                        Intent intent =new Intent(getActivity(), MainActivity.class);
                        intent.putExtra("type","0");

                        startActivity(intent);
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

}
