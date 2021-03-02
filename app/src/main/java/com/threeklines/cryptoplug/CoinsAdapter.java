package com.threeklines.cryptoplug;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.threeklines.cryptoplug.backside.Coin;

import java.util.List;

public class CoinsAdapter extends RecyclerView.Adapter<CoinsAdapter.ViewHolder> {

    private final List<Coin> coinList;

    public CoinsAdapter(List<Coin> coinList) {
        this.coinList = coinList;
    }

    @NonNull
    @Override
    public CoinsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoinsAdapter.ViewHolder holder, int position) {
        holder.textView.setText(coinList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return coinList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text25);
        }
    }
}
