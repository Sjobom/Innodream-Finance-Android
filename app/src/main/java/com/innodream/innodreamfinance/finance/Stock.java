package com.innodream.innodreamfinance.finance;

import android.util.Log;

import com.innodream.innodreamfinance.http.HttpGetRequest;
import com.innodream.innodreamfinance.http.OnHttpReadyCallback;

public class stock {
    public static void get_latest_stock_price(OnHttpReadyCallback activity, String company_ticker) {
        HttpGetRequest httpGetRequest = new HttpGetRequest(activity);
        String api_url = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol={COMPANY_NAME}&apikey=SFZ2D9HYHGJNX02R";
        api_url = api_url.replace("{COMPANY_NAME}", company_ticker);
        Log.d("API URL ", api_url);
        httpGetRequest.execute(api_url);
    }

}
