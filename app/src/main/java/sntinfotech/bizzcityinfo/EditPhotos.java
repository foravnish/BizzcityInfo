package sntinfotech.bizzcityinfo;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import sntinfotech.bizzcityinfo.Fragments.GSTDetails;
import sntinfotech.bizzcityinfo.Fragments.MatchingLeadsDetails;
import sntinfotech.bizzcityinfo.Fragments.Matchingleads;
import sntinfotech.bizzcityinfo.Utils.Api;
import sntinfotech.bizzcityinfo.Utils.MyPrefrences;
import sntinfotech.bizzcityinfo.Utils.Util;
import sntinfotech.bizzcityinfo.connection.JSONParser;

public class EditPhotos extends AppCompatActivity {

    String filepath1, fileName1 =null;
    ProgressDialog progress;
    TextView profileBack;
    Dialog dialog;

    ImageView image1,img1;
    ImageView image2,img2;
    ImageView image3,img3;
    ImageView image4,img4;

    GridView gridview;
    List<HashMap<String,String>> DataList;
    String id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_photos);
        img1=(ImageView)findViewById(R.id.img1);
        image1=(ImageView)findViewById(R.id.imageV1);
//        img2=(ImageView)findViewById(R.id.img2);
//        image2=(ImageView)findViewById(R.id.image2);
//        img3=(ImageView)findViewById(R.id.img3);
//        image3=(ImageView)findViewById(R.id.image3);
//        img4=(ImageView)findViewById(R.id.img4);
//        image4=(ImageView)findViewById(R.id.image4);
//        image4=(ImageView)findViewById(R.id.image4);

        gridview=(GridView) findViewById(R.id.gridview);

        DataList=new ArrayList<>();

        new getPhotoGallery(getApplicationContext()).execute();

        profileBack=(TextView) findViewById(R.id.profileBack);

        profileBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageOne(1, 2);

            }
        });

    }


    private class DeleteGalary extends AsyncTask<String, Void, String> {
        Context context;
        String name,gstno,adress,id;
        public DeleteGalary(Context context,String id) {
            this.context = context;
            this.name=name;
            this.gstno=gstno;
            this.adress=adress;
            this.id=id;
            dialog=new Dialog(EditPhotos.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Util.showPgDialog(dialog);
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map=new HashMap<>();

            map.put("function","deleteGalleryImage");
            map.put("photoId", id);


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

                        startActivity(new Intent(EditPhotos.this,   EditPhotos.class));
                        finish();
                    }

                }
            } catch (JSONException e) {
                Util.errorDialog(context,"Please connect to the Internet...");
                e.printStackTrace();
            }
        }

    }


    private class getPhotoGallery extends AsyncTask<String, Void, String> {
        Context context;
        String name,gstno,adress;
        public getPhotoGallery(Context context) {
            this.context = context;
            this.name=name;
            this.gstno=gstno;
            this.adress=adress;
            dialog=new Dialog(EditPhotos.this);
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
                final JSONObject jsonObject = new JSONObject(s);
                if (jsonObject != null) {
                    if (jsonObject.optString("status").equalsIgnoreCase("success")) {


//                        gridview.setVisibility(View.VISIBLE);
//                        textView.setVisibility(View.GONE);

                        JSONArray jsonArray=jsonObject.getJSONArray("message");

                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);

//                            num=jsonArray.length();
                            HashMap<String,String> map=new HashMap<>();
                            map.put("id",jsonObject1.optString("id").toString());
                            map.put("photo",jsonObject1.optString("photo").toString());
                            map.put("created",jsonObject1.optString("created").toString());


                            DataList.add(map);

                            Adapter adapter=new Adapter();
                            gridview.setAdapter(adapter);
                        }


//                        if (Double.parseDouble(ver)>Double.parseDouble(latestVersion)){
//
//                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                            builder.setMessage("A newer version of BizzCityInfo is available. Would you like to update?")
//                                    .setCancelable(false)
//                                    .setPositiveButton(Html.fromHtml(getResources().getString(R.string.update1)), new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int id) {
//
//                                            String url = "https://play.google.com/store/apps/details?id=sntinfotech.bizzcityinfo&hl=en";
//                                            Intent i = new Intent(Intent.ACTION_VIEW);
//                                            i.setData(Uri.parse(url));
//                                            startActivity(i);
//                                        }
//                                    })
//                                    .setNegativeButton(Html.fromHtml("<font color='#FF7F27'>NO, THANKS</font>"), new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int id) {
//                                            //  Action for 'NO' Button
//                                            dialog.cancel();
//                                        }
//                                    });
//
//                            AlertDialog alert = builder.create();
//                            alert.setTitle("BizzCityInfo");
//                            alert.show();
//
//                            Button bq = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
//                            bq.setTextColor(Color.parseColor("#026db4"));
//                            Button bq1 = alert.getButton(DialogInterface.BUTTON_POSITIVE);
//                            bq1.setTextColor(Color.parseColor("#026db4"));
//
//                        }


                    }
                    else {
                        //Util.errorDialog(context,jsonObject.optString("message"));

                        gridview.setVisibility(View.GONE);
//                        textView.setVisibility(View.VISIBLE);
//                        textView.setText(jsonObject.optString("message").toString());

                    }
                }
            } catch (JSONException e) {
                Util.errorDialog(context,"Please connect to the Internet...");
                e.printStackTrace();
            }
        }

    }

    class Adapter extends BaseAdapter {
        TextView points,require,name,address,postedDate,subCat,leadNo;
        LayoutInflater inflater;
        ImageView lock1,lock2,lock3,lock4,lock5;
        ImageView image1;
        LinearLayout qImage1;
        LinearLayout edit,deleteed;
        Adapter(){
            inflater=(LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return DataList.size();
        }

        @Override
        public Object getItem(int position) {
            return DataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.custon_list_gallery, parent, false);

            }
            image1= (ImageView) convertView.findViewById(R.id.image1);
            deleteed= (LinearLayout) convertView.findViewById(R.id.deleteed);
            edit= (LinearLayout) convertView.findViewById(R.id.edit);

            Picasso.with(EditPhotos.this).load(DataList.get(position).get("photo")).into(image1);


//            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                    imageOne(1, 2);
//                    id=DataList.get(i).get("id");
//                    Log.d("dfdsgfdgdfghdfhdg",DataList.get(i).get("id"));
//
//
//                }
//            });


            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    imageOne(1, 2);
                    id=DataList.get(position).get("id");
                    Log.d("dfdsgfdgdfghdfhdg",DataList.get(position).get("id"));
                }
            });

            deleteed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    AlertDialog.Builder builder = new AlertDialog.Builder(EditPhotos.this);
                    //Uncomment the below code to Set the message and title from the strings.xml file
                    //builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

                    //Setting message manually and performing action on button click
                    builder.setMessage("Do you want to delete Now?")

                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    new DeleteGalary(getApplicationContext(),DataList.get(position).get("id")).execute();

                                    dialog.cancel();

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //  Action for 'NO' Button
                                    dialog.cancel();
                                }
                            });

                    //Creating dialog box
                    AlertDialog alert = builder.create();
                    //Setting the title manually
                    alert.setTitle("Gallery");
                    alert.show();





                }
            });




//            imageLoader = AppController.getInstance().getImageLoader();
//
//            name.setText(DataList.get(position).getName().toString());
//            image.setImageUrl(DataList.get(position).getDesc().toLowerCase(),imageLoader);

            return convertView;
        }
    }



    private void imageOne(final int cam, final int gal) {

        final CharSequence[] items = {"Take from Camera", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(EditPhotos.this);
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
                image1.setImageBitmap(bitmap);
                image1.setVisibility(View.VISIBLE);
                img1.setVisibility(View.GONE);


                new UplloadImageSingle().execute();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        else if (i == 1) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            image1.setImageBitmap(photo);
            image1.setVisibility(View.VISIBLE);
            img1.setVisibility(View.GONE);

            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            Uri tempUri = getImageUri(getApplicationContext(), photo);

            // CALL THIS METHOD TO GET THE ACTUAL PATH
            File finalFile = new File(getRealPathFromURI(tempUri));

            filepath1 = finalFile.toString();

            String filename1 = filepath1.substring(filepath1.lastIndexOf("/") + 1);
            fileName1 = filename1;
            Log.d("dgdfgdfhgdfhd", filepath1.toString());
            Log.d("dgdfgdfhgdfhd", filename1.toString());

            new UplloadImageSingle().execute();
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


    private class UplloadImageSingle extends AsyncTask<String, String, JSONObject> {
        JSONParser jsonParser = new JSONParser();

        private static final String TAG_STATUS = "status";
        private static final String TAG_MESSAGE = "msg";

        String descreption, ageOfProd, headline, min, kmsDone, mobile, emailID, brand;
        HashMap<String, String> params = new HashMap<>();

        //EditText descreption,ageOfProd,headline,min,kmsDone,mobile,emailID;
        UplloadImageSingle() {
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
            progress = new ProgressDialog(EditPhotos.this);
            progress.setCancelable(false);
            progress.setTitle("Please wait...");
            progress.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            JSONObject jsonObject = null;
            try {

                jsonObject = uploadImage(EditPhotos.this, filepath1, fileName1);

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


                    Toast.makeText(getApplicationContext(), "Image Upload Successfully..", Toast.LENGTH_LONG).show();

                    startActivity(new Intent(EditPhotos.this,   EditPhotos.class));
                    finish();
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
                    Util.errorDialog(EditPhotos.this, json.optString("message"));
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
                    .addFormDataPart("companyId", MyPrefrences.getUserID(getApplicationContext()))
                    .addFormDataPart("photoId", id)



                    .addFormDataPart("image", fileName1, RequestBody.create(MEDIA_TYPE_PNG, sourceFile1))
                    .build();

            Log.d("dfdsgsdgdfgdfh",id);

//            Log.d("fvfgdgdfhgghfhgdfh", amounts.getText().toString().replace("₹ ", ""));
//            Log.d("fvfgdgdfhgdfhqwdfs",amounts.getText().toString().replace("₹ ", ""));

            com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
//                     .url("http://divpreetsingh.info/app/ManiUploadsImageHere")
                    .header("Authorization", "Client-ID " + "...")
                    .url("http://bizzcityinfo.com/AndroidApi.php?function=insertGalleryPhoto")
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


}
