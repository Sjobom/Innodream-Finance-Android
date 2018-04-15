package com.innodream.innodreamfinance;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innodream.innodreamfinance.model.Ticker;


public class RecommendationFragment extends Fragment {
    private RecyclerView ticker_recycler_view;
    private RecyclerView.Adapter ticker_adapter;
    private RecyclerView.LayoutManager ticker_layout_manager;

    public RecommendationFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recommendation, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inflate_company_recycler_view();
    }

    private void inflate_company_recycler_view() {
        ticker_recycler_view = getView().findViewById(R.id.ticker_recycler_view);
        ticker_recycler_view.setHasFixedSize(true); // Only for performance improvements

        ticker_layout_manager = new LinearLayoutManager(getContext());
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
    public void onAttach(Context context) {
        super.onAttach(context);

    }

}
