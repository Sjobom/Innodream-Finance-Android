package com.innodream.innodreamfinance;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.innodream.innodreamfinance.http.OnHttpReadyCallback;
import com.innodream.innodreamfinance.model.Ticker;

public class MainActivity extends Activity implements OnHttpReadyCallback {

    private MainActivity mainActivity = this;
    private RecyclerView ticker_recycler_view;
    private RecyclerView.Adapter ticker_adapter;
    private RecyclerView.LayoutManager ticker_layout_manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inflate_company_recycler_view();
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
