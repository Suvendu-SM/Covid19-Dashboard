package com.example.covid19;

import android.animation.ValueAnimator;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Other_Detail extends Fragment {
    private OkHttpClient client;
    private JSONArray jsonArray;
    private Spinner mCountry;
    private TextView mDate;
    private TextView mConfirmed;
    private TextView mRecovered;
    private TextView mDeath;
    private String confirmed;
    private String recovered;
    private String deaths;
    private String date;

    String[] country = { "Worldwide", "Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antigua and Barbuda",
            "Argentina", "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh",
            "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bhutan", "Bolivia", "Bosnia and Herzegovina",
            "Botswana", "Brazil", "Brunei", "Bulgaria", "Burkina Faso", "Burma", "Burundi", "Cabo Verde", "Cambodia",
            "Cameroon", "Canada", "Cayman Islands", "Central African Republic", "Chad", "Channel Islands", "Chile",
            "China", "Colombia", "Comoros", "Congo (Brazzaville)", "Congo (Kinshasa)", "Costa Rica", "Cote d'Ivoire",
            "Croatia", "Cruise Ship", "Cuba", "Curacao", "Cyprus", "Czechia", "Denmark", "Diamond Princess", "Djibouti",
            "Dominica", "Dominican Republic", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea",
            "Estonia", "Eswatini", "Ethiopia", "Faroe Islands", "Fiji", "Finland", "France", "French Guiana", "Gabon",
            "Gambia", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guadeloupe",
            "Guam", "Guatemala", "Guernsey", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Holy See", "Honduras",
            "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy", "Jamaica",
            "Japan", "Jersey", "Jordan", "Kazakhstan", "Kenya", "Korea, South", "Kosovo", "Kuwait", "Kyrgyzstan",
            "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg",
            "MS Zaandam", "Macao SAR", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta",
            "Marshall Islands", "Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico", "Moldova", "Monaco",
            "Mongolia", "Montenegro", "Morocco", "Mozambique", "Namibia", "Nepal", "Netherlands", "New Zealand",
            "Nicaragua", "Niger", "Nigeria", "North Macedonia", "Norway", "Oman", "Others", "Pakistan", "Panama",
            "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal", "Puerto Rico", "Qatar",
            "Reunion", "Romania", "Russia", "Rwanda", "Saint Barthelemy", "Saint Kitts and Nevis", "Saint Lucia",
            "Saint Martin", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe",
            "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia",
            "Solomon Islands", "Somalia", "South Africa", "South Sudan", "Spain", "Sri Lanka", "Sudan", "Suriname",
            "Sweden", "Switzerland", "Syria", "Taiwan*", "Tajikistan", "Tanzania", "Thailand", "Timor-Leste", "Togo",
            "Trinidad and Tobago", "Tunisia", "Turkey", "USA", "Uganda", "Ukraine", "United Arab Emirates",
            "United Kingdom", "Uruguay", "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam", "West Bank and Gaza",
            "Western Sahara", "Yemen", "Zambia", "Zimbabwe" };

    public Other_Detail() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = new OkHttpClient();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_other__detail, container, false);
        mDate = view.findViewById(R.id.Date);
        mCountry = view.findViewById(R.id.Country);
        mConfirmed = view.findViewById(R.id.confirmed);
        mRecovered = view.findViewById(R.id.recovered);
        mDeath = view.findViewById(R.id.death);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        mDate.setText(date);
        ArrayAdapter<String> countries = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item,
                country);
        countries.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCountry.setAdapter(countries);
        mCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final int selected = position;
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (selected == 0) {

                            Request request = new Request.Builder().url("https://covid-19-data.p.rapidapi.com/totals")
                                    .get().addHeader("x-rapidapi-key", "d38a237f8ms8da2d7030ea7fe1d9601snc85ec0ea0")//// modified
                                                                                                                    //// key
                                    .addHeader("x-rapidapi-host", "covid-19-data.p.rapidapi.com").build();
                            try {
                                Response response = client.newCall(request).execute();
                                jsonArray = new JSONArray(response.body().string());
                                confirmed = jsonArray.getJSONObject(0).getString("confirmed");
                                recovered = jsonArray.getJSONObject(0).getString("recovered");
                                deaths = jsonArray.getJSONObject(0).getString("deaths");

                            } catch (IOException | JSONException e) {
                                confirmed = "Not available";
                                recovered = "Not available";
                                deaths = "Not available";
                                e.printStackTrace();
                            }

                        } else {
                            Request request = new Request.Builder()
                                    .url("https://covid-19-data.p.rapidapi.com/country?name=" + country[selected]).get()
                                    .addHeader("x-rapidapi-key", "d38a237f8ms8da2d7030ea7fe1d9601snc85ec0ea0") // modified
                                                                                                               // key
                                    .addHeader("x-rapidapi-host", "covid-19-data.p.rapidapi.com").build();
                            try {
                                Response response = client.newCall(request).execute();
                                jsonArray = new JSONArray(response.body().string());
                                confirmed = jsonArray.getJSONObject(0).getString("confirmed");
                                recovered = jsonArray.getJSONObject(0).getString("recovered");
                                deaths = jsonArray.getJSONObject(0).getString("deaths");

                            } catch (IOException | JSONException e) {
                                confirmed = "Not available";
                                recovered = "Not available";
                                deaths = "Not available";
                                e.printStackTrace();
                            }
                        }

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                try {
                                    ValueAnimator valueAnimator1 = ValueAnimator.ofInt(0, Integer.parseInt(confirmed));
                                    ValueAnimator valueAnimator2 = ValueAnimator.ofInt(0, Integer.parseInt(recovered));
                                    ValueAnimator valueAnimator3 = ValueAnimator.ofInt(0, Integer.parseInt(deaths));
                                    valueAnimator1.setDuration(1000);
                                    valueAnimator2.setDuration(1000);
                                    valueAnimator3.setDuration(1000);
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

                                            mDeath.setText(animation.getAnimatedValue().toString());

                                        }

                                    });
                                    valueAnimator3.start();

                                } catch (Exception e) {
                                    mConfirmed.setText(confirmed);
                                    mRecovered.setText(recovered);
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

    }

}