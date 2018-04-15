package com.innodream.innodreamfinance.model.primitives;

public class Price {
    private double price;

    public Price(double price) {
        if(Price.isValid(price)) {
            this.price = price;
        }

    }

    private static boolean isValid(double price){
        if(!(price >= 0.0)){
            throw new IllegalArgumentException();
        }
        return true;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return Double.toString(price);
    }
}
