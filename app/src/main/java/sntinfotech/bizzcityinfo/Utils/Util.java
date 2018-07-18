package sntinfotech.bizzcityinfo.Utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import org.json.JSONObject;

import sntinfotech.bizzcityinfo.Fragments.Matchingleads;
import sntinfotech.bizzcityinfo.R;


/**
 * Created by user on 2/6/2017.
 */

public class Util {
    public static String USER_TYPE = "";
    public static String Distributor = "Distributor";
    public static String Retailer = "Retailer";
    public static long Register_Mobile_Number = 0;
    public static String Wallet_Balance = "Not Available";
    public static int Check_Balance = 0;
    /// recharge related val
    public static String Recharge_Number = "";
    public static String Recharge_Amount = "";
    public static String Recharge_Operator = "";
    //////////////////

    //////////////////////paymentConfirm dialog
    public static void showInfoAlertDialog(final Context context, String title, String details) {
        final Dialog dialog = new Dialog(context);
       // dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.info);
        dialog.setCancelable(true);
        Button ok = (Button) dialog.findViewById(R.id.ok);
        LinearLayout fp_lin = (LinearLayout) dialog.findViewById(R.id.fp_lin);
        fp_lin.setVisibility(View.GONE);
        TextView info = (TextView) dialog.findViewById(R.id.info);
        info.setVisibility(View.GONE);
        TextView titles = (TextView) dialog.findViewById(R.id.title);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ok.getLayoutParams();
        if (title.equalsIgnoreCase("FindPlayer Info")) {
            fp_lin.setVisibility(View.VISIBLE);
            params.addRule(RelativeLayout.BELOW, R.id.fp_lin);

        } else {
            info.setVisibility(View.VISIBLE);
            params.addRule(RelativeLayout.BELOW, R.id.info);

        }
        titles.setText(title);
        info.setText(details);
        dialog.show();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }

    public static void showInfoAlertDialog2(final Context context, String title, String details) {
        final Dialog dialog = new Dialog(context);
        // dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.info2);
        dialog.setCancelable(true);
        Button ok = (Button) dialog.findViewById(R.id.ok);
        LinearLayout fp_lin = (LinearLayout) dialog.findViewById(R.id.fp_lin);
        fp_lin.setVisibility(View.GONE);
        TextView info = (TextView) dialog.findViewById(R.id.info);
        info.setVisibility(View.GONE);
        TextView titles = (TextView) dialog.findViewById(R.id.title);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ok.getLayoutParams();
        if (title.equalsIgnoreCase("FindPlayer Info")) {
            fp_lin.setVisibility(View.VISIBLE);
            params.addRule(RelativeLayout.BELOW, R.id.fp_lin);

        } else {
            info.setVisibility(View.VISIBLE);
            params.addRule(RelativeLayout.BELOW, R.id.info);

        }
        titles.setText(title);
      //  info.setText(details);
        dialog.show();
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }

    public static void errorDialog(Context context, String message) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alertdialogcustom);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView text = (TextView) dialog.findViewById(R.id.msg_txv);
        text.setText(fromHtml(message));
        Button ok = (Button) dialog.findViewById(R.id.btn_ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

//    public static void errorDialog2(final Context context, String message, String tras_id, String operator, String number, String oper_ref, String amount, String balance) {
//        final Dialog dialog = new Dialog(context);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.alertdialogcustom2);
//        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//
//        TextView msg_txv = (TextView) dialog.findViewById(R.id.msg_txv);
//        TextView rsID_txv = (TextView) dialog.findViewById(R.id.rsID_txv);
//        TextView operator_txv = (TextView) dialog.findViewById(R.id.operator_txv);
//        TextView number_txv = (TextView) dialog.findViewById(R.id.number_txv);
//        TextView operator_ref_txv = (TextView) dialog.findViewById(R.id.operator_ref_txv);
//        TextView amount_txv = (TextView) dialog.findViewById(R.id.amount_txv);
//        TextView balance_txv = (TextView) dialog.findViewById(R.id.balance_txv);
//
//        msg_txv.setText(message);
//        rsID_txv.setText("transaction id: ".toUpperCase()+tras_id);
//        operator_txv.setText("operator: ".toUpperCase()+operator);
//        number_txv.setText("number: ".toUpperCase()+number);
//        operator_ref_txv.setText("operator ref: ".toUpperCase()+oper_ref);
//        amount_txv.setText("amount: ".toUpperCase()+amount);
//        balance_txv.setText("your balance: ".toUpperCase()+balance);
//
//
////        Button ok = (Button) dialog.findViewById(R.id.btn_ok);
////        ok.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                dialog.dismiss();
////                        Intent intent = new Intent(context, MainActivity.class);
////                        context.startActivity(intent);
////            }
////        });
//
//        dialog.show();
//    }




    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static Spanned fromHtml(String source) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(source);
        }
    }

    ///////////////show progress dialog for Async Task
    public static void showPgDialog(Dialog dialog) {

        dialog.setContentView(R.layout.dialogprogress);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();


//        progressDialog.setMessage("Please Wait....");
//        progressDialog.show();
    }

    public static void cancelPgDialog(Dialog dialog) {
//        if (progressDialog.isShowing()) {
//            progressDialog.dismiss();
//        }
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }

    }
}
