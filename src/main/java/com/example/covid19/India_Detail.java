package com.example.covid19;

import android.animation.ValueAnimator;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
 import java.text.ParseException;
import java.text.SimpleDateFormat;
 import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class India_Detail extends Fragment {
    private OkHttpClient client;
     private JSONArray jsonArray;
     private Spinner mState;
    private EditText mDate;
    private TextView mConfirmed;
    private TextView mRecovered;
    private TextView mDeath;
    private TextView mActive;
    private String confirmed;
    private String recovered;
    private String deaths;
    private String active;
    private String sdate;
    private  int mPosition;
    private String yesterdayAsString;
     String []mProvinces={"Andaman and Nicobar Islands","Andhra Pradesh","Arunachal Pradesh","Assam","Bihar","Chandigarh","Chhattisgarh",
            "Dadar Nagar Haveli","Dadra and Nagar Haveli and Daman and Diu","Delhi","Goa","Gujarat","Haryana","Himachal Pradesh",
            "Jammu and Kashmir","Jharkhand","Karnataka","Kerala","Ladakh","Lakshadweep","Madhya Pradesh","Maharashtra","Manipur",
            "Meghalaya","Mizoram","Nagaland","Odisha","Puducherry","Punjab","Rajasthan","Sikkim","Tamil Nadu","Telangana","Tripura",
             "Uttar Pradesh","Uttarakhand","West Bengal"};

    public India_Detail() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         client=new OkHttpClient();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_india__detail, container, false);
        mState=view.findViewById(R.id.india);
        mConfirmed=view.findViewById(R.id.confirmed);
        mRecovered=view.findViewById(R.id.recovered);
        mDeath=view.findViewById(R.id.death);
        mDate=view.findViewById(R.id.Date);
        mActive=view.findViewById(R.id.active);
         return  view;
    }

    @Override
    public void onStart() {
        super.onStart();
        ArrayAdapter<String> india_state= new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,mProvinces);
        india_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mState.setAdapter(india_state);
        sdate= new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()).format(new Date());
         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date date = dateFormat.parse(sdate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, -1);
            yesterdayAsString = dateFormat.format(calendar.getTime());
            mDate.setText(yesterdayAsString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        mState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                mPosition=position;
                 final Thread t=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Request request = new Request.Builder()
                                .url("https://covid-19-statistics.p.rapidapi.com/reports?region_province="+mProvinces[position]+"&iso=IND&region_name=India&date="+mDate.getText()+"&q=India")
                                .get()
                                .addHeader("x-rapidapi-key", "d38a2378f8msh8da92d7030ea7fep1d9601jsnc850e1c0eaa0")
                                .addHeader("x-rapidapi-host", "covid-19-statistics.p.rapidapi.com")
                                .build();
                        try {
                            Response response = client.newCall(request).execute();
                            JSONObject jsonObject1=new JSONObject(response.body().string());
                            jsonArray=jsonObject1.getJSONArray("data");
                            confirmed= jsonArray.getJSONObject(0).get("confirmed").toString();
                            recovered= jsonArray.getJSONObject(0).get("recovered").toString();
                            deaths= jsonArray.getJSONObject(0).get("deaths").toString();
                            active=jsonArray.getJSONObject(0).get("active").toString();
                         } catch (IOException | JSONException e) {
                            confirmed="Not available";
                            recovered="Not available";
                            active="Not available";
                            deaths="Not available";
                            e.printStackTrace();
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    ValueAnimator valueAnimator1 = ValueAnimator.ofInt(0, Integer.parseInt(confirmed));
                                    ValueAnimator valueAnimator2 = ValueAnimator.ofInt(0, Integer.parseInt(recovered));
                                    ValueAnimator valueAnimator3 = ValueAnimator.ofInt(0, Integer.parseInt(active));
                                    ValueAnimator valueAnimator4 = ValueAnimator.ofInt(0, Integer.parseInt(deaths));
                                    valueAnimator1.setDuration(1000);
                                    valueAnimator2.setDuration(1000);
                                    valueAnimator3.setDuration(1000);
                                    valueAnimator4.setDuration(1000);
                                    valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                        @Override
                                        public void onAnimationUpdate(ValueAnimator animation) {
                                            mConfirmed.setText(animation.getAnimatedValue().toString());
                                        }
                                    });
                                    valueAnimator1.start();
                                    valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                        @Override
                                        public void onAnimationUpdate(ValueAnimator animation) {
                                            mRecovered.setText(animation.getAnimatedValue().toString());
                                        }
                                    });
                                    valueAnimator2.start();
                                    valueAnimator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                        @Override
                                        public void onAnimationUpdate(ValueAnimator animation) {
                                            mActive.setText(animation.getAnimatedValue().toString());
                                        }
                                    });
                                    valueAnimator3.start();
                                    valueAnimator4.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                        @Override
                                        public void onAnimationUpdate(ValueAnimator animation) {
                                            mDeath.setText(animation.getAnimatedValue().toString());
                                        }
                                    }); valueAnimator4.start();

                                }catch (Exception e){
                                    mConfirmed.setText(confirmed);
                                    mRecovered.setText(recovered);
                                    mActive.setText(active);
                                    mDeath.setText(deaths);
                                }

                            }
                        });
                    }
                });
                t.start();

             }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                  final Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Request request = new Request.Builder()
                                .url("https://covid-19-statistics.p.rapidapi.com/reports?region_province=" + mProvinces[mPosition] + "&iso=IND&region_name=India&date=" + mDate.getText() + "&q=India")
                                .get()
                                .addHeader("x-rapidapi-key", "d38a2378f8msh8da92d7030ea7fep1d9601jsnc850e1c0eaa0")
                                .addHeader("x-rapidapi-host", "covid-19-statistics.p.rapidapi.com")
                                .build();
                        try {
                            Response response = client.newCall(request).execute();
                            JSONObject jsonObject1 = new JSONObject(response.body().string());
                            jsonArray = jsonObject1.getJSONArray("data");
                            confirmed = jsonArray.getJSONObject(0).get("confirmed").toString();
                            recovered = jsonArray.getJSONObject(0).get("recovered").toString();
                            deaths = jsonArray.getJSONObject(0).get("deaths").toString();
                            active = jsonArray.getJSONObject(0).get("active").toString();
                        } catch (IOException | JSONException e) {
                            confirmed = "Not available";
                            recovered = "Not available";
                            active = "Not available";
                            deaths = "Not available";
                            e.printStackTrace();
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    ValueAnimator valueAnimator1 = ValueAnimator.ofInt(0, Integer.parseInt(confirmed));
                                    ValueAnimator valueAnimator2 = ValueAnimator.ofInt(0, Integer.parseInt(recovered));
                                    ValueAnimator valueAnimator3 = ValueAnimator.ofInt(0, Integer.parseInt(active));
                                    ValueAnimator valueAnimator4 = ValueAnimator.ofInt(0, Integer.parseInt(deaths));
                                    valueAnimator1.setDuration(1000);
                                    valueAnimator2.setDuration(1000);
                                    valueAnimator3.setDuration(1000);
                                    valueAnimator4.setDuration(1000);
                                    valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                        @Override
                                        public void onAnimationUpdate(ValueAnimator animation) {
                                            mConfirmed.setText(animation.getAnimatedValue().toString());
                                        }
                                    });
                                    valueAnimator1.start();
                                    valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                        @Override
                                        public void onAnimationUpdate(ValueAnimator animation) {
                                            mRecovered.setText(animation.getAnimatedValue().toString());
                                        }
                                    });
                                    valueAnimator2.start();
                                    valueAnimator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                        @Override
                                        public void onAnimationUpdate(ValueAnimator animation) {
                                            mActive.setText(animation.getAnimatedValue().toString());
                                        }
                                    });
                                    valueAnimator3.start();
                                    valueAnimator4.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                        @Override
                                        public void onAnimationUpdate(ValueAnimator animation) {
                                            mDeath.setText(animation.getAnimatedValue().toString());
                                        }
                                    }); valueAnimator4.start();

                                }catch (Exception e){
                                    mConfirmed.setText(confirmed);
                                    mRecovered.setText(recovered);
                                    mActive.setText(active);
                                    mDeath.setText(deaths);
                                }

                            }
                        });
                    }
                });
                t.start();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}