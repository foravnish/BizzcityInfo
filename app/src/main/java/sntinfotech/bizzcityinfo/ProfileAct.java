package sntinfotech.bizzcityinfo;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.google.android.gms.internal.in;
import com.payumoney.sdkui.ui.widgets.CirclePageIndicator;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.RequestBody;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import me.relex.circleindicator.CircleIndicator;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import sntinfotech.bizzcityinfo.Fragments.Profile;
import sntinfotech.bizzcityinfo.Utils.Api;
import sntinfotech.bizzcityinfo.Utils.MyPrefrences;
import sntinfotech.bizzcityinfo.Utils.Util;
import sntinfotech.bizzcityinfo.connection.AppController;
import sntinfotech.bizzcityinfo.connection.JSONParser;

public class ProfileAct extends AppCompatActivity {

    TextView name,location,location2,pincode,com_name,email,mobile,mobile2,services,userID,editList,phone,profileBack,editProfile,textPmt,editPhoto,membership,membership2;
    TextView closingday,timing,timing2,timingText,min_distance,min_order;
    Dialog dialog;
    LinearLayout linerPrimo;
    GridView listview;
    ImageView imageView;
    List<HashMap<String,String>> list=new ArrayList<>();
    String filepath1, fileName1 =null;
    ProgressDialog progress;
    String pId;
    ViewPager viewPager2;
    List<HashMap<String,String>> AllBaner;
    //CustomPagerAdapter2 mCustomPagerAdapter2;
    CircleIndicator indicat2;


    RecyclerView mRecyclerView,mRecyclerView3;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.LayoutManager mLayoutManager2;
    RecyclerView.Adapter mAdapter;
    RecyclerView.Adapter mAdapter3;
    MaterialRatingBar rating;
    TextView totlareview;
    LinearLayout linearClosed,linearTime,linearMinOrd,linearPmt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);

        name=(TextView)findViewById(R.id.name);
        location=(TextView)findViewById(R.id.location);
        location2=(TextView)findViewById(R.id.location2);
        // pincode=(TextView)view.findViewById(R.id.pincode);
        com_name=(TextView)findViewById(R.id.com_name);
        email=(TextView)findViewById(R.id.email);
        phone=(TextView)findViewById(R.id.phone);
        mobile=(TextView)findViewById(R.id.mobile);
        totlareview=(TextView)findViewById(R.id.totlareview);
        //mobile2=(TextView)view.findViewById(R.id.mobile2);
        // services=(TextView)view.findViewById(R.id.services);
       // userID=(TextView)findViewById(R.id.userID);
        editList=(TextView)findViewById(R.id.editList);
        listview=(GridView) findViewById(R.id.listview);
        imageView=(ImageView) findViewById(R.id.imageView);
        profileBack=(TextView) findViewById(R.id.profileBack);
        editPhoto=(TextView) findViewById(R.id.editPhoto);
        editProfile=(TextView) findViewById(R.id.editProfile);
        membership=(TextView) findViewById(R.id.membership);
        membership2=(TextView) findViewById(R.id.membership2);
        textPmt=(TextView) findViewById(R.id.textPmt);
        viewPager2=(ViewPager) findViewById(R.id.slider2);
        indicat2=(CircleIndicator) findViewById(R.id.indicat2);
        linerPrimo=(LinearLayout) findViewById(R.id.linerPrimo);
        rating=(MaterialRatingBar) findViewById(R.id.rating);

        closingday=(TextView) findViewById(R.id.closingday);
        timing=(TextView) findViewById(R.id.timing);

        timingText=(TextView) findViewById(R.id.timingText);
        timing2=(TextView) findViewById(R.id.timing2);

        min_distance=(TextView) findViewById(R.id.min_distance);
        min_order=(TextView) findViewById(R.id.min_order);
        linearClosed=(LinearLayout) findViewById(R.id.linearClosed);
        linearTime=(LinearLayout) findViewById(R.id.linearTime);
        linearMinOrd=(LinearLayout) findViewById(R.id.linearMinOrd);
        linearPmt=(LinearLayout) findViewById(R.id.linearPmt);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view2);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        // RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);



        profileBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        editPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ProfileAct.this,   EditPhotos.class));
            }
        });


//        membership.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent =new Intent(ProfileAct.this, MainActivity.class);
//                startActivity(intent);
//
//            }
//        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
        else
        {
            //your code

        }


        AllBaner=new ArrayList<>();
        new ProfileApi(getApplicationContext(), MyPrefrences.getUserID(getApplicationContext())).execute();
        new GalleryApi(getApplicationContext(), MyPrefrences.getUserID(getApplicationContext())).execute();

    }

    private class ProfileApi extends AsyncTask<String, Void, String> {
        Context context;
        String id;
        public ProfileApi(Context context,String id) {
            this.context = context;
            this.id=id;
            dialog=new Dialog(ProfileAct.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
           // Util.showPgDialog(dialog);
        }


        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map=new HashMap<>();

            map.put("function","memberById");
            map.put("memberId", id.toString());

            Log.d("dfsdfsdgdfgd",id.toString());
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

                        final JSONArray jsonArray=jsonObject.getJSONArray("message");
                        for (int i=0;i<jsonArray.length();i++){
                            final JSONObject jsonObject1=jsonArray.getJSONObject(i);


                            if (!jsonObject1.optString("c1_fname").equalsIgnoreCase("")) {
                                name.setVisibility(View.VISIBLE);
                                name.setText(jsonObject1.optString("c1_fname"));
                            }
                            else{
                                name.setVisibility(View.GONE);
                            }


                            if (!jsonObject1.optString("payment_mode").equalsIgnoreCase("")) {
                                linearPmt.setVisibility(View.VISIBLE);

                            }
                            else{
                                linearPmt.setVisibility(View.GONE);
                            }


                            if (!jsonObject1.optString("min_order_amnt").equalsIgnoreCase("")) {
                                linearMinOrd.setVisibility(View.VISIBLE);

                            }
                            else{
                                linearMinOrd.setVisibility(View.GONE);
                            }

                            if (!jsonObject1.optString("opening_time").equalsIgnoreCase("")) {
                                linearTime.setVisibility(View.VISIBLE);

                            }
                            else{
                                linearTime.setVisibility(View.GONE);
                            }

                            if (!jsonObject1.optString("closing_days").equalsIgnoreCase("")) {
                                linearClosed.setVisibility(View.VISIBLE);

                            }
                            else{
                                linearClosed.setVisibility(View.GONE);
                            }

                            location.setText(jsonObject1.optString("address")+", "+jsonObject1.optString("location_id")+" "+jsonObject1.optString("pincode"));
                            location2.setText(jsonObject1.optString("location_id"));
                            //pincode.setText(jsonObject1.optString("pincode"));
                            com_name.setText(jsonObject1.optString("company_name"));
                            email.setText(jsonObject1.optString("c1_email"));
                            mobile.setText(jsonObject1.optString("c1_mobile1"));
                            textPmt.setText(jsonObject1.optString("payment_mode").replace(",","\n"));
                            //mobile2.setText(jsonObject1.optString("c1_mobile2"));
                            phone.setText(jsonObject1.optString("std_code")+"-"+jsonObject1.optString("phone"));
                            //userID.setText("My Id- "+MyPrefrences.getUserID(getApplicationContext()));
                            totlareview.setText(jsonObject1.optString("ratingUser")+" Rating");

                            if (!jsonObject1.optString("rating").equalsIgnoreCase("")) {
                                rating.setRating(Float.parseFloat(jsonObject1.optString("rating")));
                            }

                            pId=jsonObject1.optString("id");

                            Picasso.with(context).load(jsonObject1.optString("companyLogo")).into(imageView);

                            closingday.setText(jsonObject1.optString("closing_days"));
                            timing.setText(""+jsonObject1.optString("opening_time")+"  - "+jsonObject1.optString("closing_time"));

                            timing2.setText(""+jsonObject1.optString("opening_time2")+" - "+jsonObject1.optString("closing_time2"));
                            min_distance.setText("Max Distance  "+jsonObject1.optString("min_order_qty")+" km");
                            min_order.setText("Min Price ₹  "+jsonObject1.optString("min_order_amnt"));

                            if (jsonObject1.optString("opening_time2").equalsIgnoreCase("")){
                                timingText.setVisibility(View.GONE);
                            }
                            else{
                                timingText.setVisibility(View.VISIBLE);
                            }

                            editProfile.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent  intent=new Intent(ProfileAct.this,ProfileEdit.class);
                                    intent.putExtra("data",jsonArray.toString());
                                    startActivity(intent);
                                }
                            });

                            if (jsonObject1.optString("premium").equalsIgnoreCase("Yes")){
                                membership.setText("PREMIUM");
                                membership2.setText("Expiry : " +jsonObject1.optString("premiumExpiryDate"));

                            }
                            else if (jsonObject1.optString("premium").equalsIgnoreCase("No")){

                                membership.setText("FREE");
                                membership2.setText("Upgrade to Primo");

                                linerPrimo.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {


                                        Intent intent =new Intent(ProfileAct.this, MainActivity.class);
                                        intent.putExtra("type","1");

                                        startActivity(intent);

                                    }
                                });
                            }




                            String str = jsonObject1.optString("subcategory");

//                            String[] items1 = insertCat.split(",");
//
//                            String insertCatID = jsonObject1.optString("subcat");
//                            String[] items12 = insertCatID.split(",");
//
//                            for(String ssss : items1){
//                                Log.d("vfgvfgdfdfdfdfgbd",ssss);
//                                services.setText(insertCat);
//                            }
//
//                            for(String ssss2 : items12){
//                                Log.d("vfgvfgdfdfdfdfgbd",ssss2);
//
//                            }

                            String[] words=str.split(",");//Separating the word using delimiter Comma and stored in an array
                            for(int k=0;k<words.length;k++)
                            {
                                Log.d("dsfsdfsdgfsdgdsf",words[k]);
//           list.add(words[k]);

                                HashMap<String,String>map=new HashMap<>();
                                map.put("text",words[k]);
                                Adapter adapter=new Adapter();
                                listview.setAdapter(adapter);
                                list.add(map);
                            }


                            editList.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {


                                    imageOne(1, 2);

//                                    Fragment fragment = new ProfileEdit();
//                                    Bundle bundle=new Bundle();
//                                   // bundle.putString("");
//                                    FragmentManager manager = getActivity().getSupportFragmentManager();
//                                    FragmentTransaction ft = manager.beginTransaction().addToBackStack(null);
//                                    ft.replace(R.id.container, fragment).commit();
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
                        Util.errorDialog(ProfileAct.this,jsonObject.optString("message"));
                    }
                }
            } catch (JSONException e) {

                Util.errorDialog(ProfileAct.this,"Some Error! Please try again...");
                e.printStackTrace();
            }
        }

    }


    private class GalleryApi extends AsyncTask<String, Void, String> {
        Context context;
        String id;
        public GalleryApi(Context context,String id) {
            this.context = context;
            this.id=id;
            dialog=new Dialog(ProfileAct.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Util.showPgDialog(dialog);
        }


        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map=new HashMap<>();

            map.put("function","getBusinessGalleryImages");
            map.put("companyId", MyPrefrences.getUserID(getApplicationContext()));

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
                AllBaner.clear();
                final JSONObject jsonObject = new JSONObject(s);
                if (jsonObject != null) {
                    if (jsonObject.optString("status").equalsIgnoreCase("success")) {

                        final JSONArray jsonArray=jsonObject.getJSONArray("message");
                        for (int i=0;i<jsonArray.length();i++){
                            final JSONObject jsonObject1=jsonArray.getJSONObject(i);


                            HashMap<String,String> map=new HashMap<>();
                            map.put("id",jsonObject1.optString("id"));
                            map.put("photo",jsonObject1.optString("photo"));

                            //AllBaner.add(map);


//                            mCustomPagerAdapter2=new CustomPagerAdapter2(getApplicationContext());
//                            viewPager2.setAdapter(mCustomPagerAdapter2);
//                            indicat2.setViewPager(viewPager2);
//                            mCustomPagerAdapter2.notifyDataSetChanged();



                            mAdapter = new HLVAdapter(getApplicationContext());

                            mRecyclerView.setAdapter(mAdapter);
                            mAdapter.notifyDataSetChanged();
                            AllBaner.add(map);


//                            pId=jsonObject1.optString("id");

                          //  Picasso.with(context).load(jsonObject1.optString("companyLogo")).into(imageView);


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
                      //  Util.errorDialog(context,jsonObject.optString("message"));
                    }
                }
            } catch (JSONException e) {
                Util.errorDialog(ProfileAct.this,"Some Error! Please try again...");
                e.printStackTrace();
            }
        }

    }

    class Adapter extends BaseAdapter {

        LayoutInflater inflater;
        TextView textView1;

        Adapter() {
            inflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView=inflater.inflate(R.layout.list_s_list2,parent,false);
            textView1= (TextView) convertView.findViewById(R.id.textView1);
            textView1.setText(list.get(position).get("text"));

//            Typeface face=Typeface.createFromAsset(getApplicationContext().getAssets(), "muli_bold.ttf");
//            textView1.setTypeface(face);

            return convertView;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(ProfileAct.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void imageOne(final int cam, final int gal) {

        final CharSequence[] items = {"Take from Camera", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileAct.this);
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
                    Toast.makeText(getApplicationContext(), "errorrr...", Toast.LENGTH_LONG).show();
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void onSelectFromGalleryResult(Intent data, int i) {

        if (i == 2) {
            Uri fileUri = data.getData();
            filepath1 = getPathFromUri(getApplicationContext(), fileUri);
            fileName1 = imagename(getApplicationContext(), fileUri);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), fileUri);
                imageView.setImageBitmap(bitmap);
//                image1.setVisibility(View.VISIBLE);
//                img1.setVisibility(View.GONE);



                new ProfilePic().execute();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }


       else if (i == 1) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
//            image1.setVisibility(View.VISIBLE);
//            img1.setVisibility(View.GONE);

            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            Uri tempUri = getImageUri(getApplicationContext(), photo);

            // CALL THIS METHOD TO GET THE ACTUAL PATH
            File finalFile = new File(getRealPathFromURI(tempUri));

            filepath1 = finalFile.toString();

            String filename1 = filepath1.substring(filepath1.lastIndexOf("/") + 1);
            fileName1 = filename1;
            Log.d("dgdfgdfhgdfhd", filepath1.toString());
            Log.d("dgdfgdfhgdfhd", filename1.toString());

            new ProfilePic().execute();
//                 System.out.println(mImageCaptureUri);
//            check = 1;
//            isImage1 = true;
//            isImage2 = false;
//            isImage3 =false;
//            isImage4 = false;
//            isImage5 = false;
        }

    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
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



    private class ProfilePic extends AsyncTask<String, String, JSONObject> {
        JSONParser jsonParser = new JSONParser();

        private static final String TAG_STATUS = "status";
        private static final String TAG_MESSAGE = "msg";

        String descreption, ageOfProd, headline, min, kmsDone, mobile, emailID, brand;
        HashMap<String, String> params = new HashMap<>();

        //EditText descreption,ageOfProd,headline,min,kmsDone,mobile,emailID;
        ProfilePic() {
            this.descreption = descreption;
            this.ageOfProd = ageOfProd;
            this.headline = headline;
            this.min = min;
            this.kmsDone = kmsDone;
            this.mobile = mobile;
            this.emailID = emailID;
            this.brand = brand;

        }

        @Override
        protected void onPreExecute() {
            progress = new ProgressDialog(ProfileAct.this);
            progress.setCancelable(false);
            progress.setTitle("Please wait...");
            progress.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            JSONObject jsonObject = null;
            try {

                jsonObject = uploadImage(ProfileAct.this, filepath1, fileName1);



//                if (isImage1 == true) {
//
//                }
//                if (isImage2 == true) {
//                    jsonObject = uploadImage(PostAdd.this, filepath1, fileName1, filepath2, fileName2,
//                            descreption, ageOfProd, headline, min, kmsDone, mobile, emailID, brand);
//                }
//                if (isImage3 == true) {
//                    jsonObject = uploadImage(PostAdd.this, filepath1, fileName1, filepath2, fileName2, filepath3, fileName3,
//                            descreption, ageOfProd, headline, min, kmsDone, mobile, emailID, brand);
//                }
//                if (isImage4 == true) {
//                    jsonObject = uploadImage(PostAdd.this, filepath1, fileName1, filepath2, fileName2, filepath3, fileName3, filepath4, fileName4,
//                            descreption, ageOfProd, headline, min, kmsDone, mobile, emailID, brand);
//                }
//                if (isImage5 == true) {
//                    jsonObject = uploadImage(PostAdd.this, filepath1, fileName1, filepath2, fileName2, filepath3, fileName3, filepath4, fileName4, filepath5, fileName5,
//                            descreption, ageOfProd, headline, min, kmsDone, mobile, emailID, brand);
//                }

                if (jsonObject != null) {

                    return jsonObject;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(JSONObject json) {
            String message = "";
            String data = "";

            if (progress.isShowing())
                progress.dismiss();

            if (json != null) {


                if (json.optString("status").equalsIgnoreCase("success")) {


                    Toast.makeText(getApplicationContext(), "Profile Upload Successfully..", Toast.LENGTH_LONG).show();
//                    try {
//
////                        Log.d("fgdgdfghdfhdf", "car");
////                        double amnt = Double.parseDouble(amounts.getText().toString().replace("₹ ", ""));
////                        MyPrefrences.setPostingId(getApplicationContext(), json.getString("paymentId"));
////                        MyPrefrences.setPostingId2(getApplicationContext(), json.getString("posting_id"));
//                        Log.d("rupee", String.valueOf(amnt));
//
////                            errorDialog2(ProfileAct.this, json.getString("message"), json.getString("posting_id"));
//
//                        //Toast.makeText(PostAdd.this, ""+jsonObject.optString("message") + message, Toast.LENGTH_SHORT).show();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }

                } else {
//                    Toast.makeText(PostAdd.this, "Error " + json, Toast.LENGTH_LONG).show();
                    Util.errorDialog(ProfileAct.this, json.optString("message"));
                }
            }
        }

    }


    private JSONObject uploadImage(Context context, String filepath1, String fileName1) {

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

            RequestBody requestBody = new MultipartBuilder()
                    .type(MultipartBuilder.FORM)
                    .addFormDataPart("companyId", pId.toString())
//                    .addFormDataPart("paymentMode", "")
//                    .addFormDataPart("opening_time", "")
//                    .addFormDataPart("closing_time", "")
//                    .addFormDataPart("min_order_amnt", "")
//                    .addFormDataPart("min_order_qty", "")
//                    .addFormDataPart("closing_days", "")

                    .addFormDataPart("image", fileName1, RequestBody.create(MEDIA_TYPE_PNG, sourceFile1))


                    .build();

            Log.d("dfdsgsdgdfgdfh",pId);

//            Log.d("fvfgdgdfhgghfhgdfh", amounts.getText().toString().replace("₹ ", ""));
//            Log.d("fvfgdgdfhgdfhqwdfs",amounts.getText().toString().replace("₹ ", ""));

            com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
//                     .url("http://divpreetsingh.info/app/ManiUploadsImageHere")
                    .header("Authorization", "Client-ID " + "...")
                    .url("http://bizzcityinfo.com/AndroidApi.php?function=insertProfileDetails")
//                    .url("http://templatestheme.com/demo/tradeone/ws/post_offer.php")
                    // .addHeader("enctype", "multipart/form-data")
                    .post(requestBody)
                    .build();


            OkHttpClient client = new OkHttpClient();
            client.setConnectTimeout(15, TimeUnit.SECONDS);
            client.setWriteTimeout(15, TimeUnit.SECONDS);
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
            Toast.makeText(getApplicationContext(), "Please try again.", Toast.LENGTH_SHORT).show();
        }
        return jsonObject;
    }

    class CustomPagerAdapter2 extends PagerAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;

        public CustomPagerAdapter2(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return AllBaner.size();
        }



        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((RelativeLayout) object);
        }



        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View itemView = mLayoutInflater.inflate(R.layout.page_item, container, false);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);

            Picasso.with(mContext).load(AllBaner.get(position).get("photo")).into(imageView);


//            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
//
//            imageView.setImageUrl(AllBaner.get(position).getPhoto().toString().replace(" ","%20"),imageLoader);


//            imageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (AllBaner.get(position).getOrgby().toString().isEmpty() ) {
//
//                        //  Toast.makeText(getActivity(), "blank", Toast.LENGTH_SHORT).show();
//                    }
//                    else{
////                        Toast.makeText(getActivity(), AllBaner.get(position).getOrgby().toString(), Toast.LENGTH_SHORT).show();
//
//
//                        Intent intent=new Intent(getActivity(), WebViewOpen.class);
//                        intent.putExtra("link",AllBaner.get(position).getOrgby().toString());
//                        startActivity(intent);
//
////                        Fragment fragment=new WebViewOpen();
////                        Bundle bundle=new Bundle();
////                        bundle.putString("link",AllBaner.get(position).getOrgby().toString());
////                        FragmentManager manager=getActivity().getSupportFragmentManager();
////                        FragmentTransaction ft=manager.beginTransaction();
////                        fragment.setArguments(bundle);
////                        ft.replace(R.id.content_frame,fragment).addToBackStack(null).commit();
//                    }
//                }
//            });

            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((RelativeLayout) object);
        }
    }


    public class HLVAdapter extends RecyclerView.Adapter<HLVAdapter.ViewHolder> {

        ArrayList<String> alName;
        ArrayList<Integer> alImage;
        Context context;

        public HLVAdapter(Context context) {
            super();
            this.context = context;
            this.alName = alName;
            this.alImage = alImage;
        }

        @Override
        public HLVAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.list_gallery, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(HLVAdapter.ViewHolder viewHolder, int i) {


//            ImageLoader imageLoader = AppController.getInstance().getImageLoader();
//            viewHolder.imgThumbnail.setImageUrl(AllBaner.get(i).get("photo"),imageLoader);
            Log.d("fdssdffdfdfdsdfsdfs",AllBaner.get(i).get("photo"));

            Picasso.with(context).load(AllBaner.get(i).get("photo")).into(viewHolder.imgThumbnail);


//            viewHolder.actPrice.setPaintFlags(viewHolder.actPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


           // final Typeface tvFont = Typeface.createFromAsset(getApplicationContext().getAssets(), "muli_bold.ttf");

        }

        @Override
        public int getItemCount() {
            return AllBaner.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

            public ImageView imgThumbnail;

            //private ItemClickListener clickListener;

            public ViewHolder(View itemView) {
                super(itemView);
                imgThumbnail = (ImageView) itemView.findViewById(R.id.s1_15);
//                tvSpecies = (TextView) itemView.findViewById(R.id.tv_species);
//                actPrice = (TextView) itemView.findViewById(R.id.actPrice);
//                desc = (TextView) itemView.findViewById(R.id.desc);
//                oldPrice = (TextView) itemView.findViewById(R.id.oldPrice);
                itemView.setOnClickListener(this);
                itemView.setOnLongClickListener(this);
            }

            public TextView tvSpecies,act_price,oldPrice,actPrice,desc;

            @Override
            public void onClick(View view) {

//

            }

            @Override
            public boolean onLongClick(View view) {
                return false;
            }

//
        }

    }




}
