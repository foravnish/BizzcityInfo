package sntinfotech.bizzcityinfo;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import sntinfotech.bizzcityinfo.Utils.Api;
import sntinfotech.bizzcityinfo.Utils.MyPrefrences;
import sntinfotech.bizzcityinfo.Utils.Util;
import sntinfotech.bizzcityinfo.connection.JSONParser;

public class Login extends AppCompatActivity {

    Button login;
    EditText mobileNo,password;
    Dialog  dialog;
    TextView forgotPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login=(Button)findViewById(R.id.login);
        mobileNo=(EditText)findViewById(R.id.mobileNo);
        password=(EditText)findViewById(R.id.password);

        forgotPwd= (TextView) findViewById(R.id.forgotPwd);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkValidation()){
                  new LoginApi(Login.this,mobileNo.getText().toString(),password.getText().toString()).execute();
                }
            }
        });

        forgotPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(Login.this);
//                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.otp_dialog);

                final EditText mobile_edit= (EditText) dialog.findViewById(R.id.mobile_edit);
                Button submit=(Button)dialog.findViewById(R.id.submit);
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (TextUtils.isEmpty(mobile_edit.getText().toString())){

                            Toast.makeText(Login.this, "Please Enter Mobile No.", Toast.LENGTH_SHORT).show();
                        }
                        else if (!(mobile_edit.getText().length() ==10)){
                            Toast.makeText(Login.this, "Please Enter 10 digit Mobile No.", Toast.LENGTH_SHORT).show();
                        }
                        else {

                            new sendMobileNo(Login.this,mobile_edit.getText().toString(),dialog).execute();

                        }
                    }
                });

                dialog.show();

            }


        });
    }

    private boolean  checkValidation() {
        if (TextUtils.isEmpty(mobileNo.getText().toString()))
        {
            mobileNo.setError("Oops! Mobile field blank");
            mobileNo.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(password.getText().toString()))
        {
            password.setError("Oops! Password field blank");
            password.requestFocus();
            return false;
        }
        else if (mobileNo.getText().toString().length()<10){
            mobileNo.setError("Oops! Mobile no. must 10 digits");
            mobileNo.requestFocus();
            return false;
        }

        return true;
    }



    private class LoginApi extends AsyncTask<String, Void, String> {
        Context context;
        String mobileNo,password;
        public LoginApi(Context context, String mobileNo, String password) {
            this.context = context;
            this.mobileNo=mobileNo;
            this.password=password;
            dialog=new Dialog(Login.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Util.showPgDialog(dialog);
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map=new HashMap<>();

            map.put("function","login");
            map.put("email", mobileNo.toString());
            map.put("password", password.toString());


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
                            Log.d("dfdsgsg",jsonObject1.optString("id").toString());
                            MyPrefrences.setUserLogin(context, true);
                            MyPrefrences.setUserID(getApplicationContext(),jsonObject1.optString("id").toString());
                            MyPrefrences.setCatID(getApplicationContext(),jsonObject1.optString("cat_id").toString());
                            MyPrefrences.setSCatID(getApplicationContext(),jsonObject1.optString("subcat").toString());
                            MyPrefrences.setUSENAME(getApplicationContext(),jsonObject1.optString("company_name").toString());
                            MyPrefrences.setEMAILID(getApplicationContext(),jsonObject1.optString("c1_email").toString());
                            MyPrefrences.setMobile(getApplicationContext(),jsonObject1.optString("c1_mobile1").toString());



                            Intent intent=new Intent(Login.this,MainActivity.class);
                            intent.putExtra("type","0");
                            startActivity(intent);
                            finish();

                        }
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



    private class sendMobileNo extends AsyncTask<String, Void, String> {
        Context context;
        String mobileNo,password;
        Dialog dialog3;
        public sendMobileNo(Context context, String mobileNo, Dialog dialog3) {
            this.context = context;
            this.mobileNo=mobileNo;
            this.dialog3=dialog3;
            Login.this.dialog =new Dialog(Login.this);
            Login.this.dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            Login.this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Util.showPgDialog(Login.this.dialog);
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map=new HashMap<>();

            map.put("function","forgetPasswordOtp");
            map.put("mobileNumber", mobileNo.toString());
            //map.put("password", password.toString());


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


//                        JSONArray jsonArray=jsonObject.getJSONArray("message");
                        JSONObject jsonObject1=jsonObject.getJSONObject("message");
                        String otpresponse=jsonObject1.optString("otp");


                        dialog3.dismiss();
                        otpVerfy(mobileNo.toString(),otpresponse);

                    }
                    else {
                        Util.errorDialog(context,jsonObject.optString("message"));
                    }
                }
            } catch (JSONException e) {
                Util.cancelPgDialog(dialog);
                Util.errorDialog(context,"Some Error! Please try again...");
                e.printStackTrace();
            }
        }

    }




    private void otpVerfy(final String mob, final String otp) {

        final Dialog dialog2 = new Dialog(Login.this);
//                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2.setContentView(R.layout.otp_dialog_verfy);
        dialog2.setCancelable(false);

        final EditText otp_edit= (EditText) dialog2.findViewById(R.id.otp_edit);
        TextView recieve= (TextView) dialog2.findViewById(R.id.recieve);
        recieve.setText("Sent OTP on "+mob);
        Button submit2=(Button)dialog2.findViewById(R.id.submit2);
        submit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(otp_edit.getText().toString())){

                    Toast.makeText(Login.this, "Please Enter OTP", Toast.LENGTH_SHORT).show();
                }

                else {
                     if (otp.equalsIgnoreCase(otp_edit.getText().toString())){
                         newpassword(mob);
                         dialog2.dismiss();
                    }
                    else {
                         Util.errorDialog(Login.this,"Enter Correct OTP");
                     }

                }
            }
        });

        dialog2.show();

    }
    private void newpassword(final String mob) {

        final Dialog dialog2 = new Dialog(Login.this);
//                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2.setContentView(R.layout.new_password);

        final EditText password= (EditText) dialog2.findViewById(R.id.password);
        final EditText passwordConfirm= (EditText) dialog2.findViewById(R.id.passwordConfirm);

        Button submitPassword=(Button)dialog2.findViewById(R.id.submitPassword);
        submitPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(password.getText().toString())) {

                    Util.errorDialog(Login.this,"Please Enter New Password");
                }
                else if (TextUtils.isEmpty(passwordConfirm.getText().toString())) {

                    Util.errorDialog(Login.this,"Please Enter Confirm Password");
                }
                else if (!password.getText().toString().equals(passwordConfirm.getText().toString())){

                    Util.errorDialog(Login.this,"Confirm Password must be same !");
                }
                else {

                    new CreatePassword(Login.this,password.getText().toString(),dialog2,mob).execute();

                }
            }
        });

        dialog2.show();
    }


    private class CreatePassword extends AsyncTask<String, Void, String> {
        Context context;
        String password,mob;
        Dialog dialog2;
        public CreatePassword(Context context, String password, Dialog dialog2, String mob) {
            this.context = context;
            this.password=password;
            this.dialog2=dialog2;
            this.mob=mob;


            dialog=new Dialog(Login.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Util.showPgDialog(dialog);
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map=new HashMap<>();

            map.put("function","changePassword");
            map.put("mobileNumber", mob.toString());
            map.put("newPassword", password.toString());
            //map.put("password", password.toString());


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

                        Util.errorDialog(context,"Password Successfully Created...");
                        dialog2.dismiss();

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
