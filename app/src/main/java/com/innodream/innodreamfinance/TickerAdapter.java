package com.innodream.innodreamfinance;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.innodream.innodreamfinance.model.Ticker;

public class TickerAdapter extends RecyclerView.Adapter<TickerAdapter.ViewHolder>{
    private Ticker[] ticker_data_set;

    public TickerAdapter(Ticker[] ticker_data_set) {
        this.ticker_data_set = ticker_data_set;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView company_name;

        public ViewHolder(View view) {
            super(view);
            company_name = (TextView) view.findViewById(R.id.company_name_in_list);
        }
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public TickerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View company_name_list_row = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.company_name_list_row, parent, false);
        return new ViewHolder(company_name_list_row);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull TickerAdapter.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.company_name.setText(ticker_data_set[position].get_company_name());
    }

    @Override
    public int getItemCount() {
        return ticker_data_set.length;
    }
}
