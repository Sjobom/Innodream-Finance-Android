package com.innodream.innodreamfinance;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.innodream.innodreamfinance.finance.stock;
import com.innodream.innodreamfinance.http.HttpGetRequest;
import com.innodream.innodreamfinance.http.OnHttpReadyCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class MainActivity extends Activity implements OnHttpReadyCallback {

    TextView resultTextView;
    EditText companyNameEditText;
    Button getDataButton;
    private MainActivity mainActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTextView = findViewById(R.id.resultTextView);
        companyNameEditText = findViewById(R.id.CompanyNameEditText);
        getDataButton = findViewById(R.id.getDataButton);
    }

    public void getDataPressed(View v) {
        String company_ticker = companyNameEditText.getText().toString();
        stock.get_latest_stock_price(mainActivity, company_ticker);
    }


    @Override
    public void onHttpReady(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject time_series = jsonObject.getJSONObject("Time Series (Daily)");
            Iterator<String> keys = time_series.keys();
            String price = time_series.getJSONObject(keys.next()).getString("4. close");
            resultTextView.setText(price);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
