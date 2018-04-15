package com.innodream.innodreamfinance;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.innodream.innodreamfinance.http.AlphaVantageClient;
import com.innodream.innodreamfinance.http.AlphaVantageInterface;
import com.innodream.innodreamfinance.model.Stock;
import com.innodream.innodreamfinance.model.Ticker;
import com.innodream.innodreamfinance.model.primitives.Price;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity {



    private Map<String,Stock> Stock;
    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch(item.getItemId()) {
                case R.id.action_recommendation:
                    transaction.replace(R.id.content, new RecommendationFragment()).commit();
                    return true;
                case R.id.action_alarm:
                    return true;
                case R.id.action_technical_analysis:
                    transaction.replace(R.id.content, new TechnicalAnalysisFragment()).commit();
                    return true;
                case R.id.action_information:
                    return true;
                case R.id.action_portfolio:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavigationItemSelectedListener);

        // Initialize the recommendation fragment
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content, new RecommendationFragment()).commit();

        Stock = new HashMap<>();

        // TEST CODE
        Ticker ticker = new Ticker("ABB.ST", "ABB");
        get_latest_stock_price(ticker, "compact");

    }



    public void get_latest_stock_price(final Ticker company_ticker, String output_size) {
        String API_KEY = getString(R.string.API_KEY);
        AlphaVantageInterface apiService =
                AlphaVantageClient.getClient().create(AlphaVantageInterface.class);

        Call<Object> call = apiService.getTimeSeriesDaily(company_ticker.get_ticker(), output_size, API_KEY);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                JsonParser jsonParser = new JsonParser();
                JsonObject jsonObject = (JsonObject) jsonParser.parse(new Gson().toJson(response.body()));
                jsonObject = jsonObject.getAsJsonObject("Time Series (Daily)");
                if(!Stock.containsKey(company_ticker.get_ticker())){
                    Stock.put(company_ticker.get_ticker(), new Stock(company_ticker));
                }
                Stock stock = Stock.get(company_ticker.get_ticker());
                Set<String> dates = jsonObject.keySet();
                for(String date : dates) {
                    String price_string = jsonObject.getAsJsonObject(date)
                            .getAsJsonPrimitive("4. close").getAsString();
                    Price price = new Price(Double.parseDouble(price_string));
                    stock.add_stock_price(LocalDate.parse(date), price);
                }

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });

    }
}
