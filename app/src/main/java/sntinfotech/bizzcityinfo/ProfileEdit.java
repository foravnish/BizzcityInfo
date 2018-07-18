package sntinfotech.bizzcityinfo;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import sntinfotech.bizzcityinfo.Fragments.MatchingLeadsDetails;
import sntinfotech.bizzcityinfo.Fragments.Matchingleads;
import sntinfotech.bizzcityinfo.Utils.Api;
import sntinfotech.bizzcityinfo.Utils.MyPrefrences;
import sntinfotech.bizzcityinfo.Utils.Util;
import sntinfotech.bizzcityinfo.connection.JSONParser;

public class ProfileEdit extends AppCompatActivity {

    TextView name,location,location2,pincode,com_name,email,mobile,mobile2,services,userID,editList,phone,profileBack,editProfile;
    TextView pMode,openingTime,openingTime2,closingTime,closingTime2,homedel,txtNo,txtYes,closingDay1;
    Spinner closingDay;
    EditText minOrder,delKm;
    Dialog dialog,dialog1;
    GridView listview;
    ImageView imageView;
    Button submitEdit;
    ProgressDialog progress;

    List<String> offerData=new ArrayList<>();

    List<HashMap<String,String>> AllProducts ;
    List<HashMap<String,String>> AllDays ;

    List<HashMap<String,String>> list=new ArrayList<>();
    GridView expListView;
    ListView lvExp;
    List<String> data=new ArrayList<>();
    List<String> data2=new ArrayList<>();
    Button bubmit;
    String value="";
    String value3="";
    String value2="";
    String value4="";

    String daysData;
    LinearLayout tv4,lay1;
    TextView tv1,tv2,tv3,tv5,tv22,tv32;
    MaterialRatingBar rating;
    TextView totlareview;
    String[] daysVal={"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
    JSONObject jsonObject1;


    TextView txtYes2,txtNo2;
    LinearLayout linerSendonTime;

    private int CalendarHour, CalendarMinute;
    String format;
    Calendar calendar;
    TimePickerDialog timepickerdialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit2);


        Log.d("fdgfdgdfhdghf",getIntent().getStringExtra("data"));

        name=(TextView)findViewById(R.id.name);
        location=(TextView)findViewById(R.id.location);
        location2=(TextView)findViewById(R.id.location2);
        // pincode=(TextView)view.findViewById(R.id.pincode);
        com_name=(TextView)findViewById(R.id.com_name);
        email=(TextView)findViewById(R.id.email);
        phone=(TextView)findViewById(R.id.phone);
        mobile=(TextView)findViewById(R.id.mobile);
        //mobile2=(TextView)view.findViewById(R.id.mobile2);
        // services=(TextView)view.findViewById(R.id.services);
        userID=(TextView)findViewById(R.id.userID);
        closingDay1=(TextView)findViewById(R.id.closingDay1);

        imageView=(ImageView) findViewById(R.id.imageView);
        profileBack=(TextView) findViewById(R.id.profileBack);
        pMode=(TextView) findViewById(R.id.pMode);
        closingTime=(TextView) findViewById(R.id.closingTime);
        closingTime2=(TextView) findViewById(R.id.closingTime2);
        openingTime=(TextView) findViewById(R.id.openingTime);
        openingTime2=(TextView) findViewById(R.id.openingTime2);
        tv1=(TextView) findViewById(R.id.tv1);
        tv2=(TextView) findViewById(R.id.tv2);
        tv22=(TextView) findViewById(R.id.tv22);
        tv3=(TextView) findViewById(R.id.tv3);
        tv32=(TextView) findViewById(R.id.tv32);
        tv5=(TextView) findViewById(R.id.tv5);
        homedel=(TextView) findViewById(R.id.homedel);
        submitEdit=(Button) findViewById(R.id.submitEdit);
        totlareview=(TextView)findViewById(R.id.totlareview);
        expListView = (GridView) findViewById(R.id.lvExp);

        minOrder=(EditText) findViewById(R.id.minOrder);

        txtYes=(TextView) findViewById(R.id.txtYes);
        txtNo=(TextView) findViewById(R.id.txtNo);

        txtYes2=(TextView) findViewById(R.id.txtYes2);
        txtNo2=(TextView) findViewById(R.id.txtNo2);
        linerSendonTime=(LinearLayout) findViewById(R.id.linerSendonTime);

        delKm=(EditText) findViewById(R.id.delKm);
        //closingDay=(Spinner) findViewById(R.id.closingDay);
        tv4=(LinearLayout) findViewById(R.id.tv4);
        lay1=(LinearLayout) findViewById(R.id.lay1);
        rating=(MaterialRatingBar) findViewById(R.id.rating);

        pMode.setText(Html.fromHtml("<u>"+"Manage Payment Mode ?"+"</u>"));
        openingTime.setText(Html.fromHtml("<u>"+"Opening time ?"+"</u>"));
        closingTime.setText(Html.fromHtml("<u>"+"Closing Time ?"+"</u>"));
        homedel.setText(Html.fromHtml("<u>"+"Home Delivery ?"+"</u>"));
        closingDay1.setText(Html.fromHtml("<u>"+"Closing Day"+"</u>"));

        txtYes2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linerSendonTime.setVisibility(View.VISIBLE);
            }
        });


        txtNo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linerSendonTime.setVisibility(View.GONE);
                tv22.setText("");
                tv32.setText("");
            }
        });

//        offerData.clear();
//        offerData.add("Sunday");
//        offerData.add("Monday");
//        offerData.add("Tuesday");
//        offerData.add("Wednesday");
//        offerData.add("Thursday");
//        offerData.add("Friday");
//        offerData.add("Staurday");
//
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.simple_spinner_item, offerData);
//        dataAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
//        closingDay.setAdapter(dataAdapter);

//        closingDay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                daysData=closingDay.getSelectedItem().toString();
//
//            }
//        });

        homedel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv4.setVisibility(View.VISIBLE);
            }
        });

        txtYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lay1.setVisibility(View.VISIBLE);
            }
        });

        txtNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lay1.setVisibility(View.GONE);
                minOrder.setText("");
                delKm.setText("");

            }
        });

//        closingDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                daysData=closingDay.getSelectedItem().toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });


        AllProducts = new ArrayList<>();
        AllDays = new ArrayList<>();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
        else
        {
            //your code

        }

        openingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                calendar = Calendar.getInstance();
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                CalendarMinute = calendar.get(Calendar.MINUTE);


                timepickerdialog = new TimePickerDialog(ProfileEdit.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                if (hourOfDay == 0) {

                                    hourOfDay += 12;

                                    format = " AM";
                                }
                                else if (hourOfDay == 12) {

                                    format = " PM";

                                }
                                else if (hourOfDay > 12) {

                                    hourOfDay -= 12;

                                    format = " PM";

                                }
                                else {

                                    format = " AM";
                                }


                                tv2.setText(hourOfDay + ":" + minute + format);
                            }
                        }, CalendarHour, CalendarMinute, false);
                timepickerdialog.show();




//                Calendar mcurrentTime = Calendar.getInstance();
//                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
//                int minute = mcurrentTime.get(Calendar.MINUTE);
//                TimePickerDialog mTimePicker;
//                mTimePicker = new TimePickerDialog(ProfileEdit.this, new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
//                        tv2.setVisibility(View.VISIBLE);
//                        tv2.setText(selectedHour + ":" + selectedMinute);
////                        tv2.setText(Html.fromHtml("<u>"+selectedHour + ":" + selectedMinute+"</u>"));
//
//                    }
//                }, hour, minute, true);//Yes 24 hour time
//                mTimePicker.setTitle("Opening Time");
//
//                mTimePicker.show();




            }
        });

        openingTime2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                calendar = Calendar.getInstance();
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                CalendarMinute = calendar.get(Calendar.MINUTE);


                timepickerdialog = new TimePickerDialog(ProfileEdit.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                if (hourOfDay == 0) {

                                    hourOfDay += 12;

                                    format = " AM";
                                }
                                else if (hourOfDay == 12) {

                                    format = " PM";

                                }
                                else if (hourOfDay > 12) {

                                    hourOfDay -= 12;

                                    format = " PM";

                                }
                                else {

                                    format = " AM";
                                }


                                tv22.setText(hourOfDay + ":" + minute + format);
                            }
                        }, CalendarHour, CalendarMinute, false);
                timepickerdialog.show();




//                Calendar mcurrentTime = Calendar.getInstance();
//                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
//                int minute = mcurrentTime.get(Calendar.MINUTE);
//                TimePickerDialog mTimePicker;
//                mTimePicker = new TimePickerDialog(ProfileEdit.this, new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
//                        tv2.setVisibility(View.VISIBLE);
//                        tv2.setText(selectedHour + ":" + selectedMinute);
////                        tv2.setText(Html.fromHtml("<u>"+selectedHour + ":" + selectedMinute+"</u>"));
//
//                    }
//                }, hour, minute, true);//Yes 24 hour time
//                mTimePicker.setTitle("Opening Time");
//
//                mTimePicker.show();




            }
        });


        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                calendar = Calendar.getInstance();
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                CalendarMinute = calendar.get(Calendar.MINUTE);


                timepickerdialog = new TimePickerDialog(ProfileEdit.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                if (hourOfDay == 0) {

                                    hourOfDay += 12;

                                    format = " AM";
                                }
                                else if (hourOfDay == 12) {

                                    format = " PM";

                                }
                                else if (hourOfDay > 12) {

                                    hourOfDay -= 12;

                                    format = " PM";

                                }
                                else {

                                    format = " AM";
                                }


                                tv2.setText(hourOfDay + ":" + minute + format);
                            }
                        }, CalendarHour, CalendarMinute, false);
                timepickerdialog.show();

//                Calendar mcurrentTime = Calendar.getInstance();
//                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
//                int minute = mcurrentTime.get(Calendar.MINUTE);
//                TimePickerDialog mTimePicker;
//                mTimePicker = new TimePickerDialog(ProfileEdit.this, new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
//                        tv2.setVisibility(View.VISIBLE);
//                        tv2.setText(selectedHour + ":" + selectedMinute);
////                        tv2.setText(Html.fromHtml("<u>"+selectedHour + ":" + selectedMinute+"</u>"));
//                    }
//                }, hour, minute, true);//Yes 24 hour time
//                mTimePicker.setTitle("Opening Time");
//                mTimePicker.show();

            }
        });

        tv22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                calendar = Calendar.getInstance();
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                CalendarMinute = calendar.get(Calendar.MINUTE);


                timepickerdialog = new TimePickerDialog(ProfileEdit.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                if (hourOfDay == 0) {

                                    hourOfDay += 12;

                                    format = " AM";
                                }
                                else if (hourOfDay == 12) {

                                    format = " PM";

                                }
                                else if (hourOfDay > 12) {

                                    hourOfDay -= 12;

                                    format = " PM";

                                }
                                else {

                                    format = " AM";
                                }


                                tv22.setText(hourOfDay + ":" + minute + format);
                            }
                        }, CalendarHour, CalendarMinute, false);
                timepickerdialog.show();

//                Calendar mcurrentTime = Calendar.getInstance();
//                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
//                int minute = mcurrentTime.get(Calendar.MINUTE);
//                TimePickerDialog mTimePicker;
//                mTimePicker = new TimePickerDialog(ProfileEdit.this, new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
//                        tv2.setVisibility(View.VISIBLE);
//                        tv2.setText(selectedHour + ":" + selectedMinute);
////                        tv2.setText(Html.fromHtml("<u>"+selectedHour + ":" + selectedMinute+"</u>"));
//                    }
//                }, hour, minute, true);//Yes 24 hour time
//                mTimePicker.setTitle("Opening Time");
//                mTimePicker.show();

            }
        });

        closingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                calendar = Calendar.getInstance();
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                CalendarMinute = calendar.get(Calendar.MINUTE);


                timepickerdialog = new TimePickerDialog(ProfileEdit.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                if (hourOfDay == 0) {

                                    hourOfDay += 12;

                                    format = " AM";
                                }
                                else if (hourOfDay == 12) {

                                    format = " PM";

                                }
                                else if (hourOfDay > 12) {

                                    hourOfDay -= 12;

                                    format = " PM";

                                }
                                else {

                                    format = " AM";
                                }


                                tv3.setText(hourOfDay + ":" + minute + format);
                            }
                        }, CalendarHour, CalendarMinute, false);
                timepickerdialog.show();

//                Calendar mcurrentTime = Calendar.getInstance();
//                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
//                int minute = mcurrentTime.get(Calendar.MINUTE);
//                TimePickerDialog mTimePicker;
//                mTimePicker = new TimePickerDialog(ProfileEdit.this, new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
//                        tv3.setVisibility(View.VISIBLE);
//                        tv3.setText(selectedHour + ":" + selectedMinute);
////                        tv3.setText(Html.fromHtml("<u>"+selectedHour + ":" + selectedMinute+"</u>"));
//
//                    }
//                }, hour, minute, true);//Yes 24 hour time
//                mTimePicker.setTitle("Closing Time");
//                mTimePicker.show();

            }
        });

        closingTime2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                calendar = Calendar.getInstance();
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                CalendarMinute = calendar.get(Calendar.MINUTE);


                timepickerdialog = new TimePickerDialog(ProfileEdit.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                if (hourOfDay == 0) {

                                    hourOfDay += 12;

                                    format = " AM";
                                }
                                else if (hourOfDay == 12) {

                                    format = " PM";

                                }
                                else if (hourOfDay > 12) {

                                    hourOfDay -= 12;

                                    format = " PM";

                                }
                                else {

                                    format = " AM";
                                }


                                tv32.setText(hourOfDay + ":" + minute + format);
                            }
                        }, CalendarHour, CalendarMinute, false);
                timepickerdialog.show();

//                Calendar mcurrentTime = Calendar.getInstance();
//                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
//                int minute = mcurrentTime.get(Calendar.MINUTE);
//                TimePickerDialog mTimePicker;
//                mTimePicker = new TimePickerDialog(ProfileEdit.this, new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
//                        tv3.setVisibility(View.VISIBLE);
//                        tv3.setText(selectedHour + ":" + selectedMinute);
////                        tv3.setText(Html.fromHtml("<u>"+selectedHour + ":" + selectedMinute+"</u>"));
//
//                    }
//                }, hour, minute, true);//Yes 24 hour time
//                mTimePicker.setTitle("Closing Time");
//                mTimePicker.show();

            }
        });


        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                calendar = Calendar.getInstance();
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                CalendarMinute = calendar.get(Calendar.MINUTE);


                timepickerdialog = new TimePickerDialog(ProfileEdit.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                if (hourOfDay == 0) {

                                    hourOfDay += 12;

                                    format = " AM";
                                }
                                else if (hourOfDay == 12) {

                                    format = " PM";

                                }
                                else if (hourOfDay > 12) {

                                    hourOfDay -= 12;

                                    format = " PM";

                                }
                                else {

                                    format = " AM";
                                }


                                tv3.setText(hourOfDay + ":" + minute + format);
                            }
                        }, CalendarHour, CalendarMinute, false);
                timepickerdialog.show();

//                Calendar mcurrentTime = Calendar.getInstance();
//                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
//                int minute = mcurrentTime.get(Calendar.MINUTE);
//                TimePickerDialog mTimePicker;
//                mTimePicker = new TimePickerDialog(ProfileEdit.this, new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
//                        tv3.setVisibility(View.VISIBLE);
//                        tv3.setText(selectedHour + ":" + selectedMinute);
////                        tv3.setText(Html.fromHtml("<u>"+selectedHour + ":" + selectedMinute+"</u>"));
//                    }
//                }, hour, minute, true);//Yes 24 hour time
//                mTimePicker.setTitle("Closing Time");
//                mTimePicker.show();

            }
        });

        tv32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                calendar = Calendar.getInstance();
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                CalendarMinute = calendar.get(Calendar.MINUTE);


                timepickerdialog = new TimePickerDialog(ProfileEdit.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                if (hourOfDay == 0) {

                                    hourOfDay += 12;

                                    format = " AM";
                                }
                                else if (hourOfDay == 12) {

                                    format = " PM";

                                }
                                else if (hourOfDay > 12) {

                                    hourOfDay -= 12;

                                    format = " PM";

                                }
                                else {

                                    format = " AM";
                                }


                                tv32.setText(hourOfDay + ":" + minute + format);
                            }
                        }, CalendarHour, CalendarMinute, false);
                timepickerdialog.show();

//                Calendar mcurrentTime = Calendar.getInstance();
//                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
//                int minute = mcurrentTime.get(Calendar.MINUTE);
//                TimePickerDialog mTimePicker;
//                mTimePicker = new TimePickerDialog(ProfileEdit.this, new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
//                        tv3.setVisibility(View.VISIBLE);
//                        tv3.setText(selectedHour + ":" + selectedMinute);
////                        tv3.setText(Html.fromHtml("<u>"+selectedHour + ":" + selectedMinute+"</u>"));
//                    }
//                }, hour, minute, true);//Yes 24 hour time
//                mTimePicker.setTitle("Closing Time");
//                mTimePicker.show();

            }
        });


        pMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popLocation();


            }
        });

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popLocation();


            }
        });


        closingDay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popDays();
            }
        });

        tv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popDays();
            }
        });

        profileBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        try {
            JSONArray jsonArray=new JSONArray(getIntent().getStringExtra("data"));
            for (int i=0;i<jsonArray.length();i++){

                jsonObject1=jsonArray.getJSONObject(i);


                if (!jsonObject1.optString("c1_fname").equalsIgnoreCase("")) {
                 name.setVisibility(View.VISIBLE);
                    name.setText(jsonObject1.optString("c1_fname"));
                }
                else{
                    name.setVisibility(View.GONE);
                }

                location.setText(jsonObject1.optString("address")+", "+jsonObject1.optString("location_id")+" "+jsonObject1.optString("pincode"));
                location2.setText(jsonObject1.optString("location_id"));
                //pincode.setText(jsonObject1.optString("pincode"));
                com_name.setText(jsonObject1.optString("company_name"));
                email.setText(jsonObject1.optString("c1_email"));
                mobile.setText(jsonObject1.optString("c1_mobile1"));

//                tv3.setText(jsonObject1.optString("closing_time"));
//
//                tv2.setText(jsonObject1.optString("opening_time"));

                tv1.setVisibility(View.VISIBLE);
//                tv1.setText(jsonObject1.optString("payment_mode"));

                tv1.setText("✔ "+jsonObject1.optString("payment_mode").replace(",","\n✔ "));
                tv5.setText("✔ "+jsonObject1.optString("closing_days").replace(",","\n✔ "));
//                tv1.setText(Html.fromHtml("<u>"+jsonObject1.optString("opening_time")+"</u>"));

                minOrder.setText(jsonObject1.optString("min_order_amnt"));
                delKm.setText(jsonObject1.optString("min_order_qty"));
                tv2.setText(jsonObject1.optString("opening_time"));
                tv22.setText(jsonObject1.optString("opening_time2"));
//                tv2.setText(Html.fromHtml("<u>"+jsonObject1.optString("opening_time")+"</u>"));
                tv3.setText(jsonObject1.optString("closing_time"));
                tv32.setText(jsonObject1.optString("closing_time2"));
//                tv3.setText(Html.fromHtml("<u>"+jsonObject1.optString("closing_time")+"</u>"));

                //mobile2.setText(jsonObject1.optString("c1_mobile2"));
                phone.setText(jsonObject1.optString("std_code")+"-"+jsonObject1.optString("phone"));
                userID.setText("Seller Id- "+ MyPrefrences.getUserID(getApplicationContext()));

                totlareview.setText(jsonObject1.optString("ratingUser")+" Rating");

                rating.setRating(Float.parseFloat(jsonObject1.optString("rating")));


                submitEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        new ProfileData(getApplicationContext(),pMode.getText().toString(),tv2.getText().toString(),tv3.getText().toString(),tv22.getText().toString(),tv32.getText().toString(),
                                minOrder.getText().toString(), delKm.getText().toString()).execute();
                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    private class ProfileData extends AsyncTask<String, Void, String> {
        Context context;
        String pMode,openT,closeT,openT2,closeT2,minorder,delKm,closDay;
        public ProfileData(Context context, String pMode, String openT,String closeT,String openT2,String closeT2,String minorder,String delKm ) {
            this.context = context;
            this.pMode=pMode;
            this.openT=openT;
            this.closeT=closeT;
            this.openT2=openT2;
            this.closeT2=closeT2;
            this.minorder=minorder;
            this.delKm=delKm;
            this.closDay=closDay;
            dialog=new Dialog(ProfileEdit.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));Util.showPgDialog(dialog);
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map=new HashMap<>();

            map.put("companyId",MyPrefrences.getUserID(getApplicationContext()));
            map.put("paymentMode", value.toString());
            map.put("opening_time", openT.toString());
            map.put("opening_time2", openT2.toString());
            map.put("closing_time", closeT.toString());
            map.put("closing_time2", closeT2.toString());
            map.put("min_order_amnt", minorder.toString());
            map.put("min_order_qty", delKm.toString());
            map.put("closing_days", value3.toString());


            JSONParser jsonParser=new JSONParser();
            String result =jsonParser.makeHttpRequest("http://bizzcityinfo.com/AndroidApi.php?function=insertProfileDetails","POST",map);

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


                        Toast.makeText(getApplicationContext(), "Profile submit Successfully...", Toast.LENGTH_LONG).show();

                        startActivity(new Intent(ProfileEdit.this,ProfileAct.class));

                    }

                }
            } catch (JSONException e) {
                Util.errorDialog(ProfileEdit.this,"Some Error! Please try again...");
                e.printStackTrace();
            }
        }

    }


    private void popLocation() {


//        dialog=new Dialog(ProfileEdit.this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        dialog.setCancelable(false);
//        Util.showPgDialog(dialog);
//

        dialog1 = new Dialog(ProfileEdit.this);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.alertdialog_paymt);
        dialog1.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        View footerView = ((LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_layout, null, false);

        //TextView text = (TextView) dialog.findViewById(R.id.msg_txv);
        AllProducts = new ArrayList<>();
        lvExp = (ListView) dialog1.findViewById(R.id.lvExp);
        bubmit = (Button) footerView.findViewById(R.id.bubmit);

        lvExp.addFooterView(footerView);

        new PaymentModeAPi(getApplicationContext()).execute();

    }

    private void popDays() {

        dialog1 = new Dialog(ProfileEdit.this);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.alertdialog_days);
        dialog1.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //TextView text = (TextView) dialog.findViewById(R.id.msg_txv);
        AllProducts = new ArrayList<>();
        expListView = (GridView) dialog1.findViewById(R.id.lvExp);
        bubmit = (Button) dialog1.findViewById(R.id.bubmit);


        AllDays.clear();

        for (int i=0;i<7;i++){
            HashMap<String ,String> map=new HashMap<>();
            map.put("id",i+"");
            map.put("day",daysVal[i]);

            AllDays.add(map);

            AdapterDay adapter=new AdapterDay();
            expListView.setAdapter(adapter);
        }

//        for (int i=0;i<jsonArray.length();i++){
//
//
//            JSONObject  jsonObject1=jsonArray.getJSONObject(i);
//
//            HashMap<String,String> map=new HashMap<>();
//            map.put("id",jsonObject1.optString("id"));
//            map.put("title",jsonObject1.optString("title"));
//            map.put("image",jsonObject1.optString("image"));
//            AllProducts.add(map);
//
//            Adapter adapter=new Adapter();
//            expListView.setAdapter(adapter);
//
//
//        }

        dialog1.show();

    }


    private class PaymentModeAPi extends AsyncTask<String, Void, String> {
        Context context;
        public PaymentModeAPi(Context context) {
            this.context = context;
            dialog=new Dialog(ProfileEdit.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            Util.showPgDialog(dialog);
            dialog1.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> map=new HashMap<>();

            map.put("function","getPaymentModes");
            map.put("sellerId",MyPrefrences.getUserID(getApplicationContext()));

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

                            JSONObject  jsonObject1=jsonArray.getJSONObject(i);

                            HashMap<String,String> map=new HashMap<>();
                            map.put("id",jsonObject1.optString("id"));
                            map.put("title",jsonObject1.optString("title"));
                            map.put("image",jsonObject1.optString("image"));
                            AllProducts.add(map);

                            Adapter adapter=new Adapter();
                            lvExp.setAdapter(adapter);

                        }

                    }
                    else {
                        //Util.errorDialog(context,jsonObject.optString("message"));

                    }
                }
            } catch (JSONException e) {
                Util.errorDialog(ProfileEdit.this,"Please connect to the Internet...");
                e.printStackTrace();
            }
        }
    }


    class Adapter extends BaseAdapter {
        TextView require,name,address,postedDate,subCat,leadNo;
        LayoutInflater inflater;
        ImageView lock1,lock2,lock3,lock4,lock5;
        LinearLayout qImage1;
        CheckBox textviwe;
        Adapter(){
            inflater=(LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return AllProducts.size();
        }

        @Override
        public Object getItem(int position) {
            return AllProducts.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.custon_pmt_list, parent, false);
            }
            textviwe=(CheckBox)convertView.findViewById(R.id.textviwe);

            Log.d("dsfsdfgdsgdfgdfg",jsonObject1.optString("payment_mode"));


            data.clear();
            data2.clear();
            textviwe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Log.d("sdfsdfsdfgsgs",AllProducts.get(position).get("id"));
                    data.add(AllProducts.get(position).get("id"));
                    data2.add(AllProducts.get(position).get("title"));
                }
            });





            textviwe.setText(AllProducts.get(position).get("title"));

            bubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    value=data.toString().replace("[","").replace("]","").replace(" ","");
                    value2=data2.toString().replace("[","").replace("]","").replace(" ","");

                    tv1.setText("✔ "+value2.replace(",","\n✔ "));
                    //tv1.setText("✔ "+jsonObject1.optString("payment_mode").replace(",","\n✔ "));
//                    tv1.setText(Html.fromHtml("<u>"+value2+"</u>"));
                    Log.d("fgdgdfgdfgdfgdfg",value.toString());



                    dialog1.dismiss();


                }
            });


//            if (jsonObject1.optString("payment_mode").contains(textviwe.getText().toString())){
//                textviwe.setChecked(true);
//
//            }
//            else{
//                textviwe.setChecked(false);
//            }


            return convertView;
        }
    }



    class AdapterDay extends BaseAdapter {
        TextView require,name,address,postedDate,subCat,leadNo;
        LayoutInflater inflater;
        ImageView lock1,lock2,lock3,lock4,lock5;
        LinearLayout qImage1;
        CheckBox textviwe;
        AdapterDay(){
            inflater=(LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return AllDays.size();
        }

        @Override
        public Object getItem(int position) {
            return AllDays.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.custon_pmt_list, parent, false);
            }
            textviwe=(CheckBox)convertView.findViewById(R.id.textviwe);

            Log.d("dfdsfgvsdgfdgd",AllDays.get(position).get("id"));
            data.clear();
            data2.clear();
            textviwe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Log.d("sdfsdfsdfgsgs",AllDays.get(position).get("day"));
                    data.add(AllDays.get(position).get("id"));
                    data2.add(AllDays.get(position).get("day"));
                }
            });



            textviwe.setText(AllDays.get(position).get("day"));

            bubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    value3=data.toString().replace("[","").replace("]","").replace(" ","");
                    value4=data2.toString().replace("[","").replace("]","").replace(" ","");
                    tv5.setText("✔ "+value4.replace(",","\n✔ "));

//                    tv5.setText(Html.fromHtml("<u>"+value2+"</u>"));
                    Log.d("fgdgdfgdfgdfgdfg",value3.toString());

                    dialog1.dismiss();

//

                }
            });





            return convertView;
        }
    }




}
