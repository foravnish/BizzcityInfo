package sntinfotech.bizzcityinfo.Fragments;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.RequestBody;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import sntinfotech.bizzcityinfo.Login;
import sntinfotech.bizzcityinfo.R;
import sntinfotech.bizzcityinfo.Utils.Api;
import sntinfotech.bizzcityinfo.Utils.MyPrefrences;
import sntinfotech.bizzcityinfo.Utils.Util;
import sntinfotech.bizzcityinfo.connection.JSONParser;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddOffers extends Fragment {


    Spinner spinerCat,offerSpinner,discountSpinner;
    Dialog dialog;
    List<String> data=new ArrayList<>();
    List<String> offerData=new ArrayList<>();
    List<String> discountData=new ArrayList<>();
    List<HashMap<String,String>> All;
    LinearLayout linearDiscount,linearPrice;
    TextView dateFrom,dateTill,imageBtn;
    DatePickerDialog  datePickerDialog;
    Button bubmit;
    EditText headline,descreption,offerPrice,price,coupan;
    ImageView image1;
    String filepath1,fileName1;
    int check = 0;
    ProgressDialog progress;
    String offerval,disVal;
    String offerId="";
    TextView offerPrice0;
    String subCatId;
    public AddOffers() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_offers, container, false);

        spinerCat= (Spinner) view.findViewById(R.id.spinerCat);
       // offerSpinner= (Spinner) view.findViewById(R.id.offerSpinner);
       // discountSpinner= (Spinner) view.findViewById(R.id.discountSpinner);
       // linearDiscount= (LinearLayout) view.findViewById(R.id.linearDiscount);
      //  linearPrice= (LinearLayout) view.findViewById(R.id.linearPrice);
       // dateFrom= (TextView) view.findViewById(R.id.dateFrom);
     //   dateTill= (TextView) view.findViewById(R.id.dateTill);
        bubmit= (Button) view.findViewById(R.id.bubmit);
       // imageBtn= (TextView) view.findViewById(R.id.imageBtn);
        headline= (EditText) view.findViewById(R.id.headline);
       // descreption= (EditText) view.findViewById(R.id.descreption);
       // offerPrice= (EditText) view.findViewById(R.id.offerPrice);
       // offerPrice0= (TextView) view.findViewById(R.id.offerPrice0);
       // coupan= (EditText) view.findViewById(R.id.coupan);
       // price= (EditText) view.findViewById(R.id.price);
      //  image1= (ImageView) view.findViewById(R.id.image1);

        Log.d("dfsdfsdgfsdgdfsg",getArguments().getString("id"));
        Log.d("dfsdfsdgfsdgfdgfddfsg",getArguments().getString("array"));
        Log.d("dfsdfsdgfsdgfdgfddfsg",getArguments().getString("position"));
        Log.d("dfsdfsdgfsdgfdgfddfsg",getArguments().getString("package"));

        try {
//            JSONArray jsonArray=new JSONArray(getArguments().getString("array"));
           // for (int i=0;i<jsonArray.length();i++) {
//                JSONObject jsonObject=jsonArray.getJSONObject(i);
                JSONObject jsonObject=new JSONObject(getArguments().getString("array"));

                headline.setText(jsonObject.optString("heading"));
               // descreption.setText(jsonObject.optString("description"));
                //coupan.setText(jsonObject.optString("coupon_code"));
                //dateFrom.setText(jsonObject.optString("offer_from"));
                //dateTill.setText(jsonObject.optString("offer_to"));
           // }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new ProfileApi(getActivity(), MyPrefrences.getUserID(getActivity())).execute();

        if (getArguments().getString("id").toString()==null){
            offerId="";
        }
        else{
            offerId=getArguments().getString("id").toString();
        }

        All=new ArrayList<>();

       // Log.d("fgdfhbdjnfgj",MyPrefrences.getUserID(getActivity()));
        Log.d("sfsdfsdfdfgdgdgfsdgsd",offerId);


//        offerData.clear();
//        offerData.add("Select Offer Type");
//        offerData.add("Discount");
//        offerData.add("Price");
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, offerData);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        offerSpinner.setAdapter(dataAdapter);


//        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

//        discountData.clear();
//        discountData.add("Select Discount");
//        discountData.add("5%");
//        discountData.add("10%");
//        discountData.add("15%");
//        discountData.add("25%");
//        discountData.add("30%");
//        discountData.add("40%");
//        discountData.add("50%");
//        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, discountData);
//        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        discountSpinner.setAdapter(dataAdapter2);




        bubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkValidation()) {

//
//                    if (offerSpinner.getSelectedItem().toString().equalsIgnoreCase("Select Offer Type")){
//                        offerval="";
//                    }
//                    else if (offerSpinner.getSelectedItem().toString().equalsIgnoreCase("Price")){
//                        disVal="";
//                    }
//                    else if (offerSpinner.getSelectedItem().toString().equalsIgnoreCase("Discount")){
//                       // price.setText("");
//                    }
//                    else{
//                        offerval=offerSpinner.getSelectedItem().toString();
//                    }
//
//
//                    if (discountSpinner.getSelectedItem().toString().equalsIgnoreCase("Select Offer Type")){
//                        disVal="";
//                    }
//                    else{
//                        disVal=discountSpinner.getSelectedItem().toString();
//                    }

                    new SendData(headline.getText().toString()
                          //  descreption.getText().toString(),
                         //   offerSpinner.getSelectedItem().toString(),
                           // discountSpinner.getSelectedItem().toString(),
                          //  price.getText().toString(),
                           // offerPrice.getText().toString(),
                          //  coupan.getText().toString(),
                          //  dateFrom.getText().toString(),
                          //  dateTill.getText().toString()
                    ).execute();

                   // Toast.makeText(getActivity(), "yes", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        imageBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                imageOne(1, 2);
//
//            }
//        });
//        dateFrom.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Calendar c = Calendar.getInstance();
//
//                int mYear = c.get(Calendar.YEAR); // current year
//                int mMonth = c.get(Calendar.MONTH); // current month
//                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
//                datePickerDialog = new DatePickerDialog(getActivity(),
//                        new DatePickerDialog.OnDateSetListener() {
//
//                            @Override
//                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
//                                dateFrom.setText(i2 + "/" + (i1 + 1)+ "/" + i);
//                            }
//
//                        }, mYear, mMonth, mDay);
//                datePickerDialog.show();
//
//            }
//        });
//
//        dateTill.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Calendar c = Calendar.getInstance();
//
//                int mYear = c.get(Calendar.YEAR); // current year
//                int mMonth = c.get(Calendar.MONTH); // current month
//                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
//                datePickerDialog = new DatePickerDialog(getActivity(),
//                        new DatePickerDialog.OnDateSetListener() {
//
//                            @Override
//                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
//                                dateTill.setText(i2 + "/" + (i1 + 1)+ "/" + i);
//                            }
//
//                        }, mYear, mMonth, mDay);
//                datePickerDialog.show();
//
//            }
//        });
//
//        offerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if (offerSpinner.getSelectedItem().toString().equalsIgnoreCase("Select Offer Type")){
////                    Toast.makeText(getActivity(), "Please select offer type", Toast.LENGTH_SHORT).show();
//                    linearPrice.setVisibility(View.GONE);
//                    linearDiscount.setVisibility(View.GONE);
//
//                }
//                else if (offerSpinner.getSelectedItem().toString().equalsIgnoreCase("Discount")){
//                    linearPrice.setVisibility(View.GONE);
//                    linearDiscount.setVisibility(View.VISIBLE);
//                    offerPrice0.setVisibility(View.GONE);
//                    offerPrice.setVisibility(View.GONE);
//                    offerPrice.setText("nil");
//                }
//                else if (offerSpinner.getSelectedItem().toString().equalsIgnoreCase("price")){
//                    linearPrice.setVisibility(View.VISIBLE);
//                    linearDiscount.setVisibility(View.GONE);
//                    offerPrice0.setVisibility(View.VISIBLE);
//                    offerPrice.setVisibility(View.VISIBLE);
//                }
//            }
//
//
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

        return view;
    }

    private void imageOne(final int cam, final int gal) {

        final CharSequence[] items = {"Take from Camera", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                try {
                    if (items[item].equals("Take from Camera")) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, cam);
                    } else if (items[item].equals("Choose from Gallery")) {
                        Intent intent = new Intent(
                                Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        startActivityForResult(
                                Intent.createChooser(intent, "Select File"),
                                gal);
                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "errorrr...", Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.setCancelable(true);
        builder.show();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 2)
                onSelectFromGalleryResult(data, 2);

            else if (requestCode == 1)
                onSelectFromGalleryResult(data, 1);

        }
    }


    private boolean checkValidation() {
        if (TextUtils.isEmpty(headline.getText().toString()))
        {
            headline.setError("Oops! Headline blank");
            headline.requestFocus();
            return false;
        }
//        else if (TextUtils.isEmpty(descreption.getText().toString()))
//        {
//            descreption.setError("Oops! Description blank");
//            descreption.requestFocus();
//            return false;
//        }
//        else if (TextUtils.isEmpty(offerPrice.getText().toString()))
//        {
//            offerPrice.setError("Oops! OfferPrice blank");
//            offerPrice.requestFocus();
//            return false;
//        }
//
//        else if (TextUtils.isEmpty(coupan.getText().toString()))
//        {
//            coupan.setError("Oops! Coupan blank");
//            coupan.requestFocus();
//            return false;
//        }
//
//        else if (TextUtils.isEmpty(dateTill.getText().toString()))
//        {
//            dateTill.setError("Oops! Date Till blank");
//            dateTill.requestFocus();
//            return false;
//        }
//
//        else if (TextUtils.isEmpty(dateFrom.getText().toString()))
//        {
//            dateFrom.setError("Oops! From Till blank");
//            dateFrom.requestFocus();
//            return false;
//        }



        return true;
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void onSelectFromGalleryResult(Intent data, int i) {

        if (i == 2) {
            Uri fileUri = data.getData();
            filepath1 = getPathFromUri(getActivity(), fileUri);
            fileName1 = imagename(getActivity(), fileUri);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), fileUri);
                image1.setImageBitmap(bitmap);
                image1.setVisibility(View.VISIBLE);
                imageBtn.setVisibility(View.GONE);

            } catch (IOException e) {
                e.printStackTrace();
            }
            check = 1;
//            isImage1 = true;
//            isImage2 = false;
//            isImage3 = false;
//            isImage4 = false;
//            isImage5 = false;

        }

        else if (i == 1) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            image1.setImageBitmap(photo);
            image1.setVisibility(View.VISIBLE);
            imageBtn.setVisibility(View.GONE);

            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            Uri tempUri = getImageUri(getActivity(), photo);

            // CALL THIS METHOD TO GET THE ACTUAL PATH
            File finalFile = new File(getRealPathFromURI(tempUri));

            filepath1 = finalFile.toString();

            String filename1 = filepath1.substring(filepath1.lastIndexOf("/") + 1);
            fileName1 = filename1;
            Log.d("dgdfgdfhgdfhd", filepath1.toString());
            Log.d("dgdfgdfhgdfhd", filename1.toString());
//                 System.out.println(mImageCaptureUri);
            check = 1;
//            isImage1 = true;
//            isImage2 = false;
//            isImage3 = false;
//            isImage4 = false;
//            isImage5 = false;
        }
    }

    public static String imagename(Context context, Uri currImageURI) {
        String displayName = "";
        File file = new File(currImageURI.toString());
        String uriString = currImageURI.toString();
        if (uriString.startsWith("content://")) {
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(currImageURI, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    Log.e("display name content", ": " + displayName);
                }
            } finally {
                cursor.close();
            }
        } else if (uriString.startsWith("file://")) {
            displayName = file.getName();
            Log.e("display name file", ": " + displayName);
        }
        Log.e("display name ", ": " + displayName);
        return displayName;
    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getPathFromUri(final Context context, final Uri uri) {
        boolean isAfterKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        ///storage/emulated/0/Download/Amit-1.pdf
        Log.e("Uri Authority ", "uri:" + uri.getAuthority());
        if (isAfterKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            if ("com.android.externalstorage.documents".equals(
                    uri.getAuthority())) {// ExternalStorageProvider
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                } else {
                    return "/stroage/" + type + "/" + split[1];
                }
            } else if ("com.android.providers.downloads.documents".equals(
                    uri.getAuthority())) {// DownloadsProvider
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if ("com.android.providers.media.documents".equals(
                    uri.getAuthority())) {// MediaProvider
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                contentUri = MediaStore.Files.getContentUri("external");
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {//MediaStore
            return getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {// File
            return uri.getPath();
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {
        Cursor cursor = null;
        final String[] projection = {
                MediaStore.Files.FileColumns.DATA
        };
        try {
            cursor = context.getContentResolver().query(
                    uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int cindex = cursor.getColumnIndexOrThrow(projection[0]);
                return cursor.getString(cindex);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }


    private class ProfileApi extends AsyncTask<String, Void, String> {
        Context context;
        String id;
        public ProfileApi(Context context,String id) {
            this.context = context;
            this.id=id;
            dialog=new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Util.showPgDialog(dialog);
        }


        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map=new HashMap<>();

            map.put("function","getBelongingSubCat");
            map.put("sellerId", MyPrefrences.getUserID(getActivity()));

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


                            HashMap<String,String> map=new HashMap<>();
                            map.put("id",jsonObject1.optString("id"));
                            map.put("subcategory",jsonObject1.optString("subcategory"));
                            data.add(jsonObject1.optString("subcategory"));
                            All.add(map);

//                            String insertCat = jsonObject1.optString("subcategory");
//                            String[] items1 = insertCat.split(",");
//
//                            String insertCatID = jsonObject1.optString("subcat");
//                            String[] items12 = insertCatID.split(",");
//
//                            HashMap<String,String> map=new HashMap<>();
//
//                            for(String ssss : items1){
//                                Log.d("vfgvfgdfdfdfdfgbd",ssss);
//
//                                map.put("val",ssss);
//
//                                data.add(ssss);
//                                //services.setText(insertCat);
//                                All.add(map);
//                            }
//
//                            for(String ssss2 : items12){
//                                Log.d("vfgvfgdfdfdfdfgbd",ssss2);
//                                map.put("id",ssss2);
//                                All.add(map);
//                            }


                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, data);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinerCat.setAdapter(dataAdapter);


                            spinerCat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    Log.d("fsdgdfgdfgd", String.valueOf(data.get(i)));

                                    Log.d("sdfsdfgsdgdf",All.get(i).get("id"));
                                    subCatId=All.get(i).get("id").toString();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });


//                            String[] items2 = insertDate.split(" ");

//                            String d1=items1[0];
//                            String m1=items1[1];
//                            String y1=items1[2];
//                            for(String ssss : items2){
//                                Log.d("vfgvfgdfdfdfdfgbd",ssss);
//                            }
//                            String ss =items2[0];
//                            String mm =items2[1];
//                            String hh =items2[2];


//                            Log.d("vfgvfgdfdfdfdfgbd",d1+" "+ m1+" "+y1+"h> "+hh+"m> "+mm+" s> "+ss);
//                            Log.d("vfgvfgdfdfdfdfgbd",d1+" "+m1);
//
//                            JSONArray jsonArray1 =jsonObject1.getJSONArray("subcategory");
//
////                            for (int j=0;j<jsonArray1.length();j++){
//
////                                String s1=jsonObject11.optString("");
//                                Log.d("sfgsdgdfgdfgdfh",jsonArray1.toString());
////                            }
//                            Log.d("dfsdfgsdgfdsgdfg",jsonObject1.optString("subcategory"));
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


    private class SendData extends AsyncTask<String, String, String> {
        JSONParser jsonParser = new JSONParser();

        private static final String TAG_STATUS = "status";
        private static final String TAG_MESSAGE = "msg";

        String heading, desc, offetType, discount, actprice, offprice, coupan, datefrom,datetill;
        HashMap<String, String> params = new HashMap<>();



        //EditText descreption,ageOfProd,headline,min,kmsDone,mobile,emailID;
        SendData(String heading) {
            this.heading = heading;
            this.desc = desc;
            this.offetType = offetType;
            this.discount = discount;
            this.actprice = actprice;
            this.offprice = offprice;
            this.coupan = coupan;
            this.datefrom = datefrom;
            this.datetill = datetill;
            dialog=new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Util.showPgDialog(dialog);
        }

        @Override
        protected void onPreExecute() {
//            progress = new ProgressDialog(getActivity());
//            progress.setCancelable(false);
//            progress.setTitle("Please wait...");
//            progress.show();
        }


        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map=new HashMap<>();


//            map.put("myId",MyPrefrences.getUserID(getActivity()));
//            map.put("cat_id", subCatId);
//            map.put("heading", heading);
//            map.put("description", offetType);
//            map.put("offer_type", offetType);
//            map.put("discount", discount);
//            map.put("actual_price", actprice);
//            map.put("offer_price", offprice);
//            map.put("coupon_code", coupan);
//            map.put("offer_from", dateFrom.getText().toString());
//            map.put("offer_to", dateTill.getText().toString());
//            //map.put("membership", getArguments().getString("package"));
//            map.put("offerId", offerId);


            map.put("myId",MyPrefrences.getUserID(getActivity()));
            map.put("cat_id", subCatId);
            map.put("heading", heading);
            map.put("description", "desc");
            map.put("offer_type", "type");
            map.put("discount", "disc");
            map.put("actual_price", "price1");
            map.put("offer_price", "price2");
            map.put("coupon_code", "coupan");
            map.put("offer_from", "date1");
            map.put("offer_to", "date2");
            map.put("offerId", offerId);


            JSONParser jsonParser=new JSONParser();
            String result =jsonParser.makeHttpRequest("http://bizzcityinfo.com/AndroidApi.php/offerPost?function=offerPost","POST",map);
            return result;
        }

        protected void onPostExecute(String s) {
            String message = "";
            String data = "";

//            if (progress.isShowing())
//                progress.dismiss();


            Log.d("fghfhfghgfh",s);

                Util.cancelPgDialog(dialog);
            try {
                final JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.optString("status").equalsIgnoreCase("success")) {

                    Log.d("dsfsdfsdgdfgdfgd","success");
                    Toast.makeText(getActivity(), "Offer Successfully Posted", Toast.LENGTH_SHORT).show();

                    Fragment fragment = new OfferPostOption();
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = manager.beginTransaction().addToBackStack(null);
                    ft.replace(R.id.container, fragment).commit();


                } else {
//                    Toast.makeText(PostAdd.this, "Error " + json, Toast.LENGTH_LONG).show();
//                    Util.errorDialog(PostAdd.this, json.optString("message"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }



        }

    }


//      this.heading = heading;
//            this.desc = desc;
//            this.offetType = offetType;
//            this.discount = discount;
//            this.actprice = actprice;
//            this.offprice = offprice;
//            this.coupan = coupan;
//            this.datefrom = datefrom;
//            this.datetill = datetill;

    private JSONObject uploadImage(Context context, String filepath1, String fileName1,
                                   String head, String desc1, String offetType, String discount, String actprice, String offprice, String coupan,  TextView dateFrom, TextView dateTill) {

        // sourceFile2= new File("");

        File sourceFile1 = new File(filepath1);

        String result = null;
        Log.e("FindPlayerPageAsync", "File...::::" + sourceFile1 + " : " + sourceFile1.exists());
        Log.e("file name", ": " + fileName1);
        JSONObject jsonObject = null;

        try {

            ////for image
            final MediaType MEDIA_TYPE_PNG = filepath1.endsWith("png") ? MediaType.parse("image/png") : MediaType.parse("image");

            Log.e("file name", ": " + fileName1);

            //   Log.d("fgdgdfgdfgdf1",getIntent().getStringExtra("areatypenum"));

            //EditText headline,descreption,offerPrice,price,coupan;

            RequestBody requestBody = new MultipartBuilder()
                    .type(MultipartBuilder.FORM)
                    .addFormDataPart("myId", MyPrefrences.getUserID(getActivity()))
                    .addFormDataPart("cat_id", "361")
                    .addFormDataPart("heading", head)
                    .addFormDataPart("description",offetType)
                    .addFormDataPart("offer_type", desc1)
                    .addFormDataPart("discount", discount)
                    .addFormDataPart("actual_price", actprice)
                    .addFormDataPart("offer_price", offprice)
                    .addFormDataPart("coupon_code", coupan)
                    .addFormDataPart("offer_from", dateFrom.getText().toString())
                    .addFormDataPart("offer_to", dateTill.getText().toString())
                    .addFormDataPart("membership", getArguments().getString("package"))
                    .addFormDataPart("offerId", offerId)
//                    .addFormDataPart("offerId","19")
                    .addFormDataPart("image", fileName1, RequestBody.create(MEDIA_TYPE_PNG, sourceFile1))

                    .build();

            Log.d("sfsdfsdgfsdgsd",MyPrefrences.getUserID(getActivity()));
            Log.d("sfsdfsdgfsdgsd",offerId);



            com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
//                     .url("http://divpreetsingh.info/app/ManiUploadsImageHere")
                    .header("Authorization", "Client-ID " + "...")
                    .url("http://bizzcityinfo.com/AndroidApi.php/offerPost?function=offerPost")
//                    .url("http://templatestheme.com/demo/tradeone/ws/post_offer.php")
                    // .addHeader("enctype", "multipart/form-data")
                    .post(requestBody)
                    .build();

            OkHttpClient client = new OkHttpClient();
            client.setConnectTimeout(15, TimeUnit.SECONDS);
            client.setWriteTimeout(10, TimeUnit.SECONDS);
            client.setReadTimeout(15, TimeUnit.SECONDS);


            Log.e("request1", ":url:  " + request.urlString() + ", header: " + request.headers() + ", body " + request.body());
            com.squareup.okhttp.Response response = client.newCall(request).execute();
            result = response.body().string();
            Log.e("responseMultipart", ": " + result);
            jsonObject = new JSONObject(result);
            Log.e("result", ": " + result);
            return jsonObject;
        } catch (UnknownHostException | UnsupportedEncodingException e) {
            Log.e("FindPlayerPageAsync", "Error: " + e.getLocalizedMessage());
        } catch (Exception e) {
            Log.e("FindPlayerPageAsync", "Other Error: " + e.getLocalizedMessage());
            Toast.makeText(getActivity(), "Please try again.", Toast.LENGTH_SHORT).show();
        }
        return jsonObject;
    }

}
