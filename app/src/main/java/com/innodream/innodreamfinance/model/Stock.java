package com.innodream.innodreamfinance.model;

import android.support.annotation.NonNull;

import com.innodream.innodreamfinance.model.primitives.Price;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Stock {
    private Ticker ticker;
    private Map<LocalDate,Price> stock_price;
    private Map<LocalDate,Price> sma;
    /* and so on */

    public Stock(Ticker ticker) {
        this.ticker = ticker;
        stock_price = new HashMap<LocalDate, Price>();
        sma = new HashMap<LocalDate, Price>();
    }

    public void add_stock_price (LocalDate date, Price price) {
        this.stock_price.put(date, price);
    }

    public Price getPrice(LocalDate date) {
        return stock_price.get(date);
    }

    public Price getPrice(String date) {
        return stock_price.get(LocalDate.parse(date));
    }


}
