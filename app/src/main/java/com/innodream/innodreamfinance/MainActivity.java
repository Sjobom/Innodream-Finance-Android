package com.innodream.innodreamfinance;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.innodream.innodreamfinance.http.AlphaVantageClient;
import com.innodream.innodreamfinance.http.AlphaVantageInterface;
import com.innodream.innodreamfinance.http.OnHttpReadyCallback;
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

public class MainActivity extends Activity implements OnHttpReadyCallback {

    private MainActivity mainActivity = this;
    private RecyclerView ticker_recycler_view;
    private RecyclerView.Adapter ticker_adapter;
    private RecyclerView.LayoutManager ticker_layout_manager;
    private Map<String,Stock> Stock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inflate_company_recycler_view();
        Ticker ticker = new Ticker("ABB.ST", "ABB");
        get_latest_stock_price(ticker, "compact");

        Stock = new HashMap<>();
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

    private void inflate_company_recycler_view() {
        ticker_recycler_view = findViewById(R.id.ticker_recycler_view);
        ticker_recycler_view.setHasFixedSize(true); // Only for performance improvements

        ticker_layout_manager = new LinearLayoutManager(this);
        ticker_recycler_view.setLayoutManager(ticker_layout_manager);

        // Add lines between the rows
        DividerItemDecoration ticker_divider_decoration = new DividerItemDecoration(
                ticker_recycler_view.getContext(),
                DividerItemDecoration.VERTICAL
        );
        ticker_recycler_view.addItemDecoration(ticker_divider_decoration);

        // Get the tickers
        Ticker[] tickers = new Ticker[3];
        tickers[0] = new Ticker("ABB:ST", "ABB");
        tickers[1] = new Ticker("ERIC-B.ST", "Ericsson B");
        tickers[2] = new Ticker("ALFA.ST", "Alfa Laval");

        ticker_adapter = new TickerAdapter(tickers);

        ticker_recycler_view.setAdapter(ticker_adapter);

    }


    @Override
    public void onHttpReady(String result) {
//        try {
//            JSONObject jsonObject = new JSONObject(result);
//            JSONObject time_series = jsonObject.getJSONObject("Time Series (Daily)");
//            Iterator<String> keys = time_series.keys();
//            String price = time_series.getJSONObject(keys.next()).getString("4. close");
//            resultTextView.setText(price);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }
}
