package sntinfotech.bizzcityinfo;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.payumoney.core.PayUmoneyConfig;
import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;
import com.payumoney.sdkui.ui.utils.ResultModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import sntinfotech.bizzcityinfo.Fragments.MatchingLeadsDetails;
import sntinfotech.bizzcityinfo.Fragments.Matchingleads;
import sntinfotech.bizzcityinfo.PayUMoney.AppEnvironment;
import sntinfotech.bizzcityinfo.PayUMoney.AppPreference;
import sntinfotech.bizzcityinfo.PayUMoney.BaseApplication;
import sntinfotech.bizzcityinfo.Utils.Api;
import sntinfotech.bizzcityinfo.Utils.MyPrefrences;
import sntinfotech.bizzcityinfo.Utils.Util;
import sntinfotech.bizzcityinfo.connection.JSONParser;

import static sntinfotech.bizzcityinfo.connection.AppController.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class PackagesPremimum extends AppCompatActivity {


    public PackagesPremimum() {
        // Required empty public constructor
    }
    TextView threeMonth,oneMonth,sixMonth,twelMonth;
    Dialog dialog;
    RadioGroup radioGroup;
    Button btnSubmit;
    String merKey,merId,salt;
    String packageName;
    private static final int RESULT_OK = -1;
    private PayUmoneySdkInitializer.PaymentParam mPaymentParams;
    private AppPreference mAppPreference;

    ListView gridview;
    List<HashMap<String,String>> DataList;

    TextView totlPrice;

    TextView payNow;
//    Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        View view= inflater.inflate(R.layout.fragment_packages_premimum, container, false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_packages_premimum);

        Log.d("dsfsdfgsdgsdfgsdf",getIntent().getStringExtra("id"));
        Log.d("dsfsdfgsdgsdfgsdf",getIntent().getStringExtra("package_name"));
        Log.d("dsfsdfgsdgsdfgsdf",getIntent().getStringExtra("actual_price"));
        Log.d("dsfsdfgsdgsdfgsdf",getIntent().getStringExtra("merId"));

        LinearLayout backBtn=(LinearLayout) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        TextView packages=(TextView)findViewById(R.id.packages);
        TextView price=(TextView)findViewById(R.id.price);
        TextView points=(TextView)findViewById(R.id.points);
        totlPrice=(TextView)findViewById(R.id.totlPrice);
        TextView bonous=(TextView)findViewById(R.id.bonous);
        TextView totalCredit=(TextView)findViewById(R.id.totalCredit);
        TextView date=(TextView)findViewById(R.id.date);
        TextView netPrice=(TextView)findViewById(R.id.netPrice);
        TextView gst=(TextView)findViewById(R.id.gst);
        TextView discount=(TextView)findViewById(R.id.discount);


//        packages.setText(getIntent().getStringExtra("package_name"));
//        totlPrice.setText(getIntent().getStringExtra("total_amount"));
//        netPrice.setText(getIntent().getStringExtra("actual_price"));
//        gst.setText(getIntent().getStringExtra("gst_amount"));

        packages.setText(getIntent().getStringExtra("package_name"));
        // price.setText("₹ "+jsonObject.optString("credit_per_value")+" per credit");
        //points.setText(jsonObject.optString("creadit_points")+" Points");
        totlPrice.setText(getIntent().getStringExtra("total_amount"));
        //bonous.setText(jsonObject.optString("bonus_credit")+" Points");
        //totalCredit.setText(jsonObject.optString("total_credit")+" Points");
        //  date.setText(jsonObject.optString("posted_date"));
        netPrice.setText(getIntent().getStringExtra("actual_price"));
        gst.setText(getIntent().getStringExtra("gst_amount"));

        double  dis1= Double.parseDouble(getIntent().getStringExtra("old_price"));
        double  dis2= Double.parseDouble(getIntent().getStringExtra("actual_price"));
        double discountVal=dis1-dis2;

        discount.setText("₹ "+discountVal+"\nOFF");

        merKey=getIntent().getStringExtra("merKey");
        merId=getIntent().getStringExtra("merId");
        salt=getIntent().getStringExtra("salt");

        payNow = (TextView) findViewById(R.id.payNow);
//        oneMonth = (TextView) findViewById(R.id.oneMonth);
//        threeMonth = (TextView) findViewById(R.id.threeMonth);
//        sixMonth = (TextView) findViewById(R.id.sixMonth);
//        twelMonth = (TextView) findViewById(R.id.twelMonth);
//        btnSubmit = (Button) findViewById(R.id.btnSubmit);

//        gridview=(ListView) findViewById(R.id.gridview);
//
//        oneMonth.setPaintFlags(oneMonth.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//        threeMonth.setPaintFlags(oneMonth.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//        sixMonth.setPaintFlags(oneMonth.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//        twelMonth.setPaintFlags(oneMonth.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


//        DataList =new ArrayList<>();
//        new ShowPackage(getApplicationContext(), MyPrefrences.getUserID(getApplicationContext())).execute();

        mAppPreference = new AppPreference();


//
        payNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // new PurchasePackage(getApplicationContext(), MyPrefrences.getUserID(getApplicationContext())).execute();

                launchPayUMoneyFlow("", "");

            }
        });

    }






//    private class PurchasePackage extends AsyncTask<String, Void, String> {
//        Context context;
//        String id;
//        public PurchasePackage(Context context,String id) {
//            this.context = context;
//            this.id=id;
//            dialog=new Dialog(PackagesPremimum.this);
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//            Util.showPgDialog(dialog);
//        }
//
//
//        @Override
//        protected String doInBackground(String... strings) {
//            HashMap<String,String> map=new HashMap<>();
//
//            map.put("function","purchasePackage");
//
//            JSONParser jsonParser=new JSONParser();
//            String result =jsonParser.makeHttpRequest(Api.Login,"POST",map);
//
//            return result;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//
//            Log.e("response", ": " + s);
//            Util.cancelPgDialog(dialog);
//            try {
//                final JSONObject jsonObject = new JSONObject(s);
//                if (jsonObject != null) {
//                    if (jsonObject.optString("status").equalsIgnoreCase("success")) {
//
//                        JSONArray jsonArray=jsonObject.getJSONArray("message");
//                        for (int i=0;i<jsonArray.length();i++){
//                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
//
////                            name.setText(jsonObject1.optString("c1_fname"));
////                            location.setText(jsonObject1.optString("address")+", "+jsonObject1.optString("location_id"));
////                            pincode.setText(jsonObject1.optString("pincode"));
////                            com_name.setText(jsonObject1.optString("company_name"));
////                            email.setText(jsonObject1.optString("c1_email"));
////                            mobile.setText(jsonObject1.optString("c1_mobile1"));
////                            mobile2.setText(jsonObject1.optString("c1_mobile2"));
////                            userID.setText(MyPrefrences.getUserID(getApplicationContext()));
//
//                        }
//                    }
//
//                    else {
//                        Util.errorDialog(context,jsonObject.optString("message"));
//                    }
//                }
//            } catch (JSONException e) {
//                Util.errorDialog(context,"Some Error! Please try again...");
//                e.printStackTrace();
//            }
//        }
//
//    }

    public static String hashCal(String str) {
        byte[] hashseq = str.getBytes();
        StringBuilder hexString = new StringBuilder();
        try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-512");
            algorithm.reset();
            algorithm.update(hashseq);
            byte messageDigest[] = algorithm.digest();
            for (byte aMessageDigest : messageDigest) {
                String hex = Integer.toHexString(0xFF & aMessageDigest);
                if (hex.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(hex);
            }
        } catch (NoSuchAlgorithmException ignored) {
        }
        return hexString.toString();
    }


    private void launchPayUMoneyFlow(String package_name, String total_value) {

        PayUmoneyConfig payUmoneyConfig = PayUmoneyConfig.getInstance();

        //Use this to set your custom text on result screen button
//        payUmoneyConfig.setDoneButtonText(((EditText) findViewById(R.id.status_page_et)).getText().toString());

        //Use this to set your custom title for the activity
//        payUmoneyConfig.setPayUmoneyActivityTitle(((EditText) findViewById(R.id.activity_title_et)).getText().toString());

        PayUmoneySdkInitializer.PaymentParam.Builder builder = new PayUmoneySdkInitializer.PaymentParam.Builder();

        double amount = 0;
        try {
//            amount = 1;
            amount = Double.parseDouble(totlPrice.getText().toString());
            Log.d("fgdfgdfhdhdh", String.valueOf(Double.parseDouble(totlPrice.getText().toString())));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String txnId = System.currentTimeMillis() + "";
//        String phone = mobile_til.getEditText().getText().toString().trim();
        String phone = MyPrefrences.getMobile(getApplicationContext()).toString();
//        String productName = mAppPreference.getProductInfo();
//        String firstName = mAppPreference.getFirstName();
//        String email = email_til.getEditText().getText().toString().trim();
//        String email = MyPrefrences.getEMAILID(getApplicationContext()).toString();
        String email = "marketing@bizzcityinfo.com";
        String udf1 = "";
        String udf2 = "";
        String udf3 = "";
        String udf4 = "";
        String udf5 = "";
        String udf6 = "";
        String udf7 = "";
        String udf8 = "";
        String udf9 = "";
        String udf10 = "";

        AppEnvironment appEnvironment = ((BaseApplication) getApplication()).getAppEnvironment();
        builder.setAmount(amount)
                .setTxnId(txnId)
                .setPhone(phone)
                .setProductName("BizzCityInfo "+package_name)
                .setFirstName(MyPrefrences.getUSENAME(getApplicationContext()).toString())
                .setEmail(email)
                .setsUrl(appEnvironment.surl())
                .setfUrl(appEnvironment.furl())
                .setUdf1(udf1)
                .setUdf2(udf2)
                .setUdf3(udf3)
                .setUdf4(udf4)
                .setUdf5(udf5)
                .setUdf6(udf6)
                .setUdf7(udf7)
                .setUdf8(udf8)
                .setUdf9(udf9)
                .setUdf10(udf10)
                .setIsDebug(appEnvironment.debug())
//                .setKey(appEnvironment.merchant_Key())
                .setKey(merKey.toString())
//                .setMerchantId(appEnvironment.merchant_ID());
                .setMerchantId(merId.toString());

        Log.d("dfdsfdsfsdfsd",merKey);
        Log.d("dfdsfdsfsdfsd",merId);

        try {
            mPaymentParams = builder.build();

            /*
            * Hash should always be generated from your server side.
            * */
            generateHashFromServer(mPaymentParams);

/*            *//**
             * Do not use below code when going live
             * Below code is provided to generate hash from sdk.
             * It is recommended to generate hash from server side only.
             * *//*
            mPaymentParams = calculateServerSideHashAndInitiatePayment1(mPaymentParams);

           if (AppPreference.selectedTheme != -1) {
                PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams,MainActivity.this, AppPreference.selectedTheme,mAppPreference.isOverrideResultScreen());
            } else {
                PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams,MainActivity.this, R.style.AppTheme_default, mAppPreference.isOverrideResultScreen());
            }*/

        } catch (Exception e) {
            // some exception occurred
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            // payNowButton.setEnabled(true);
        }
    }

    private PayUmoneySdkInitializer.PaymentParam calculateServerSideHashAndInitiatePayment1(final PayUmoneySdkInitializer.PaymentParam paymentParam) {

        StringBuilder stringBuilder = new StringBuilder();
        HashMap<String, String> params = paymentParam.getParams();
        stringBuilder.append(params.get(PayUmoneyConstants.KEY) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.TXNID) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.AMOUNT) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.PRODUCT_INFO) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.FIRSTNAME) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.EMAIL) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF1) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF2) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF3) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF4) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF5) + "||||||");

        AppEnvironment appEnvironment = ((BaseApplication) getApplication()).getAppEnvironment();
//        stringBuilder.append(appEnvironment.salt());
        stringBuilder.append(salt.toString());

        String hash = hashCal(stringBuilder.toString());
        paymentParam.setMerchantHash(hash);

        return paymentParam;
    }

    public void generateHashFromServer(PayUmoneySdkInitializer.PaymentParam paymentParam) {
        //nextButton.setEnabled(false); // lets not allow the user to click the button again and again.

        HashMap<String, String> params = paymentParam.getParams();

        // lets create the post params
        StringBuffer postParamsBuffer = new StringBuffer();
        postParamsBuffer.append(concatParams(PayUmoneyConstants.KEY, params.get(PayUmoneyConstants.KEY)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.AMOUNT, params.get(PayUmoneyConstants.AMOUNT)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.TXNID, params.get(PayUmoneyConstants.TXNID)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.EMAIL, params.get(PayUmoneyConstants.EMAIL)));
        postParamsBuffer.append(concatParams("productinfo", params.get(PayUmoneyConstants.PRODUCT_INFO)));
        postParamsBuffer.append(concatParams("firstname", params.get(PayUmoneyConstants.FIRSTNAME)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF1, params.get(PayUmoneyConstants.UDF1)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF2, params.get(PayUmoneyConstants.UDF2)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF3, params.get(PayUmoneyConstants.UDF3)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF4, params.get(PayUmoneyConstants.UDF4)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF5, params.get(PayUmoneyConstants.UDF5)));

        String postParams = postParamsBuffer.charAt(postParamsBuffer.length() - 1) == '&' ? postParamsBuffer.substring(0, postParamsBuffer.length() - 1).toString() : postParamsBuffer.toString();

        Log.d("fgdfhgdfhdfhd",postParams);

        // lets make an api call
        GetHashesFromServerTask getHashesFromServerTask = new GetHashesFromServerTask();
        getHashesFromServerTask.execute(postParams);
    }
    protected String concatParams(String key, String value) {
        return key + "=" + value + "&";
    }

    private class GetHashesFromServerTask extends AsyncTask<String, String, String> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(PackagesPremimum.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... postParams) {

            String merchantHash = "";
            try {
                //TODO Below url is just for testing purpose, merchant needs to replace this with their server side hash generation url
                URL url = new URL("http://bizzcityinfo.com/getHashCode.php");

                String postParam = postParams[0];

                byte[] postParamsByte = postParam.getBytes("UTF-8");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty("Content-Length", String.valueOf(postParamsByte.length));
                conn.setDoOutput(true);
                conn.getOutputStream().write(postParamsByte);

                InputStream responseInputStream = conn.getInputStream();
                StringBuffer responseStringBuffer = new StringBuffer();
                byte[] byteContainer = new byte[1024];
                for (int i; (i = responseInputStream.read(byteContainer)) != -1; ) {
                    responseStringBuffer.append(new String(byteContainer, 0, i));
                }

                JSONObject response = new JSONObject(responseStringBuffer.toString());
                Log.d("fgdfgdfgdfg",response.toString());
                Iterator<String> payuHashIterator = response.keys();
                while (payuHashIterator.hasNext()) {
                    String key = payuHashIterator.next();
                    switch (key) {
                        /**
                         * This hash is mandatory and needs to be generated from merchant's server side
                         *
                         */
                        case "payment_hash":
                            merchantHash = response.getString(key);
                            break;
                        default:
                            break;
                    }
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return merchantHash;
        }

        @Override
        protected void onPostExecute(String merchantHash) {
            super.onPostExecute(merchantHash);

            progressDialog.dismiss();
            // payNowButton.setEnabled(true);

            Log.d("fgdfgdfgdfg",merchantHash.toString());

            //  merchantHash="35eceef8992006b59b2f46d7ef6ce13b3dcgfhfgj258996b871f863ed7fghfgjhfgee9e76bb357209f04b488afcc6a687f354a13750421e0ec85bcb40006441df530b84831c69b4";

            if (merchantHash.isEmpty() || merchantHash.equals("")) {
                Toast.makeText(getApplicationContext(), "Could not generate hash", Toast.LENGTH_SHORT).show();
            } else {
                mPaymentParams.setMerchantHash(merchantHash);
                if (AppPreference.selectedTheme != -1) {
                    PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams, PackagesPremimum.this, AppPreference.selectedTheme, mAppPreference.isOverrideResultScreen());
                } else {
                    PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams,  PackagesPremimum.this, R.style.AppTheme_default, mAppPreference.isOverrideResultScreen());
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result Code is -1 send from Payumoney activity
        Log.d("gffgdfgfhdhdh","true");
        Log.d("MainActivity123", "request code " + requestCode + " resultcode " + resultCode);
        if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == RESULT_OK && data !=
                null) {
            TransactionResponse transactionResponse = data.getParcelableExtra(PayUmoneyFlowManager
                    .INTENT_EXTRA_TRANSACTION_RESPONSE);

            ResultModel resultModel = data.getParcelableExtra(PayUmoneyFlowManager.ARG_RESULT);

            // Check which object is non-null
            if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {

                String payuResponse = transactionResponse.getPayuResponse();
                String merchantResponse = transactionResponse.getTransactionDetails();


                if (transactionResponse.getTransactionStatus().equals(TransactionResponse.TransactionStatus.SUCCESSFUL)) {
                    //Success Transaction
                    Log.d("responseprint","success "+payuResponse);
                    Log.d("responseprint","success "+merchantResponse);

                    new AlertDialog.Builder(PackagesPremimum.this)
                            .setCancelable(false)
                            .setMessage("Payment Success...")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.dismiss();
                                }
                            }).show();
                    new BuyPackagesApi2(getApplicationContext(),"success","payment_success").execute();

                } else {
                    //Failure Transaction
                    Log.d("responseprint","failed "+payuResponse);

                    new AlertDialog.Builder(PackagesPremimum.this)
                            .setCancelable(false)
                            .setMessage("Payment Failed...")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.dismiss();
                                }
                            }).show();

                    new BuyPackagesApi2(getApplicationContext(),"failed","payment_failed").execute();
                }

                // Response from Payumoney

//                new AlertDialog.Builder(PayActivity.this)
//                        .setCancelable(false)
//                        .setMessage("Payu's Data : " + payuResponse + "\n\n\n Merchant's Data: " + merchantResponse)
//                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int whichButton) {
//                                dialog.dismiss();
//                            }
//                        }).show();

            } else if (resultModel != null && resultModel.getError() != null) {
                Log.d(TAG, "Error response : " + resultModel.getError().getTransactionResponse());
            } else {
                Log.d(TAG, "Both objects are null!");
            }
        }
    }


    private class BuyPackagesApi2 extends AsyncTask<String, Void, String> {
        Context context;
        String id,status,pymt_status;

        public BuyPackagesApi2(Context context,String status,String pymt_status) {
            this.context = context;
            this.id =id;
            this.status =status;
            this.pymt_status =pymt_status;
            dialog=new Dialog(PackagesPremimum.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Util.showPgDialog(dialog);
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map=new HashMap<>();

            map.put("function","bannerPackage");
            map.put("packageId",getIntent().getStringExtra("id"));
            map.put("sellerId",MyPrefrences.getUserID(getApplicationContext()));
            map.put("paymentId",status);
            map.put("paymentStatus",status);
            map.put("responseJson",status);
            map.put("transactionId",status);
            map.put("transactionAmount",totlPrice.getText().toString());

            Log.d("dsfsdfsdgsdgfsdgsd",totlPrice.getText().toString());
            Log.d("dsfsdfsdgsdgfsdgsd",getIntent().getStringExtra("id"));

            JSONParser jsonParser=new JSONParser();
            String result =jsonParser.makeHttpRequest(Api.Login,"POST" +
                    "",map);

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
//                        Util.errorDialog(PackagesPremimum.this,jsonObject.optString("message"));
                        Util.errorDialog(context,"You have purchased Premium");
                    }
                    else if (jsonObject.optString("status").equalsIgnoreCase("failure")) {
                       // Util.errorDialog(PackagesPremimum.this,jsonObject.optString("message"));
                        Util.errorDialog(context,"Transaction Failed...");

                    }
                    else {
                        Util.errorDialog(PackagesPremimum.this,jsonObject.optString("message"));
                    }
                }
            } catch (JSONException e) {
                Util.errorDialog(PackagesPremimum.this,"Some Error! Please try again...");
                e.printStackTrace();
            }
        }

    }


}
