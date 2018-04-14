package com.innodream.innodreamfinance.model;

public class Ticker {
    private String ticker;
    private String company_name;

    public Ticker(String ticker, String company_name) {
        if(!Ticker.isValid(ticker, company_name)) {
            throw new IllegalArgumentException("Ticker or company name too long!");
        }
        this.ticker = ticker;
        this.company_name = company_name;
    }

    public String get_ticker() {
        return ticker;
    }

    public String get_company_name() {
        return company_name;
    }

    private static boolean isValid(String ticker, String company_name) {
        return ticker.length() < 30 && company_name.length() < 40;
    }
}
