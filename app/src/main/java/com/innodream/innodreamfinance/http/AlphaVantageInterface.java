package com.innodream.innodreamfinance.http;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AlphaVantageInterface {
    @GET("query?function=TIME_SERIES_DAILY")
    Call<Object> getTimeSeriesDaily(@Query("symbol") String ticker, @Query("outputsize") String output_size, @Query("apikey") String api_key );

}
