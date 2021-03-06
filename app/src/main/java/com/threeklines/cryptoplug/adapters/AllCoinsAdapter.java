package com.threeklines.cryptoplug.adapters;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.threeklines.cryptoplug.R;
import com.threeklines.cryptoplug.backside.ApiCenter;
import com.threeklines.cryptoplug.backside.Coin;
import com.threeklines.cryptoplug.coinfragments.CoinMoreInformation;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AllCoinsAdapter extends RecyclerView.Adapter<AllCoinsAdapter.ViewHolder> {
    static List<Coin> allCoins;
    private int i;
    private int totalWatchlistcount;


    public AllCoinsAdapter(List<Coin> allCoins) {
        AllCoinsAdapter.allCoins = allCoins;
    }


    @NonNull
    @Override
    public AllCoinsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_coins_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllCoinsAdapter.ViewHolder holder, int position) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                Coin coin = new ApiCenter().getCoinMetadata(allCoins.get(position));
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (coin != null){
                            Picasso.get()
                                    .load(coin.getLogo())
                                    .placeholder(R.drawable.image_loading)
                                    .error(R.drawable.error)
                                    .into(holder.coinLogo);
                        } else holder.coinLogo.setImageResource(R.drawable.image_loading);

                    }
                });
            }
        });


        holder.coinName.setText(allCoins.get(position).getName());
        holder.coinPrice.setText(String.format("Price: $ %.4f", allCoins.get(position).getPrice()));
        holder.coinH1Change.setText(String.format("Change H1: %.2f", allCoins.get(position).getPercentageChange1h()) +  "%");
        holder.coinh24Change.setText(String.format("Change D1: %.2f", allCoins.get(position).getPercentageChange24h()) +  "%");
        holder.coin7DChange.setText(String.format("Change W1: %.2f", allCoins.get(position).getPercentageChange7d()) +  "%");
    }

    private void setFragment(Fragment fragment){

    }

    @Override
    public int getItemCount() {
        return allCoins.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView coinLogo;
        TextView coinName;
        TextView coinPrice;
        TextView coinH1Change;
        TextView coinh24Change;
        TextView coin7DChange;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            coinLogo = itemView.findViewById(R.id.list_coin_logo);
            coinName = itemView.findViewById(R.id.list_coin_name);
            coinPrice = itemView.findViewById(R.id.list_coin_price);
            coinH1Change = itemView.findViewById(R.id.list_h1_change);
            coinh24Change = itemView.findViewById(R.id.list_h24_change);
            coin7DChange = itemView.findViewById(R.id.list_7d_change);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
//            ApiCenter center = new ApiCenter();
//            center.getCoinMetadata(allCoins.get(getAdapterPosition()), new DatabaseHandler(v.getContext(),"my_db.db", null, 1));

//            ((FragmentActivity)v.getContext()).getParent().getIntent()
            PopupMenu p = new PopupMenu(v.getContext(), v);
            p.getMenuInflater().inflate(R.menu.context_menu_all_coins, p .getMenu());
            p.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId() == R.id.view_coin){
                        CoinMoreInformation fragment = new CoinMoreInformation();
                        fragment.setCoin(allCoins.get(getAdapterPosition()));
                        ((FragmentActivity)v.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.coins_frame_layout, fragment).commit();
                    }
//                    ApiCenter center = new ApiCenter();
//                    center.getCoinMetadata(allCoins.get(getAdapterPosition()), new DatabaseHandler(v.getContext(),"my_db.db", null, 1));
                    return true;
                }
            });
            p.show();

        }


    }


}
