package com.threeklines.cryptoplug.coinfragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.threeklines.cryptoplug.GlideApp;
import com.threeklines.cryptoplug.R;
import com.threeklines.cryptoplug.backside.ApiCenter;
import com.threeklines.cryptoplug.backside.Coin;
import com.threeklines.cryptoplug.backside.DatabaseHandler;
import com.threeklines.cryptoplug.backside.DbContract;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CoinMoreInformation#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CoinMoreInformation extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Coin coin;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CoinMoreInformation() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CoinMoreInformation.
     */
    // TODO: Rename and change types and number of parameters
    public static CoinMoreInformation newInstance(String param1, String param2) {
        CoinMoreInformation fragment = new CoinMoreInformation();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        View view = inflater.inflate(R.layout.fragment_coin_more_information, container, false);
        ImageView logo = view.findViewById(R.id.coin_info_logo);
        executor.execute(new Runnable() {
            @Override
            public void run() {

                DatabaseHandler Dhandler = new DatabaseHandler(getContext(), DbContract.DB_NAME, null, 1);
                ApiCenter apiCenter = new ApiCenter();

                Coin c = apiCenter.getCoinMetadata(coin, Dhandler);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(getContext(), c.getLogo(), Toast.LENGTH_SHORT).show();
                        GlideApp.with(Objects.requireNonNull(getContext()))
                                .load(c.getLogo())
                                .placeholder(R.drawable.image_loading)
                                .error(R.drawable.error)
                                .into(logo);
                    }
                });
            }
        });

//        GlideApp.with(Objects.requireNonNull(getContext()))
//                .load(coin.getLogo())
//                .placeholder(R.drawable.image)
//                .error(R.drawable.error)
//                .into(logo);
        TextView name = view.findViewById(R.id.coin_info_name);
        name.setText(coin.getName());
        TextView price = view.findViewById(R.id.coin_info_price);
        price.setText("Price: " + String.format("%.4f", coin.getPrice()));
        TextView changeH1 = view.findViewById(R.id.coin_info_h1);
        changeH1.setText("Change H1: " + String.format("%.2f", coin.getPercentageChange1h()) + "%");
        TextView change24H = view.findViewById(R.id.coin_info_h24);
        change24H.setText("Change H24: " + String.format("%.2f", coin.getPercentageChange24h()) + "%");
        TextView change7d = view.findViewById(R.id.coin_info_7d);
        change7d.setText("Change 7D: " + String.format("%.2f", coin.getPercentageChange7d()) + "%");
        TextView symbol = view.findViewById(R.id.coin__info_symbol);
        symbol.setText("Symbol: " + coin.getSymbol());
        TextView category = view.findViewById(R.id.coin_info_category);
        category.setText("Category: " + coin.getCategory());
        TextView slug = view.findViewById(R.id.coin_info_slug);
        slug.setText("Slug: " + coin.getSlug());
        TextView circulatingSuppy = view.findViewById(R.id.coin_info_circulating_supply);
        circulatingSuppy.setText("Circulating Supply: " + coin.getCirculatingSupply());
        TextView totalSupply = view.findViewById(R.id.coin_info_total_supply);
        totalSupply.setText("Total Supply: " + coin.getTotalSuppy());
        TextView maxSupply = view.findViewById(R.id.coin_info_max_supply);
        maxSupply.setText("Max Supply: " + coin.getMaxSupply());
        TextView volume24 = view.findViewById(R.id.coin_info_volume24);
        volume24.setText("24 Volume: " + coin.getVolume24h());
        TextView marketCap = view.findViewById(R.id.coin_info_markert_cap);
        marketCap.setText("Market Cap: " + coin.getMarketCap());
        return view;
    }

    public void setCoin(Coin coin) {
        this.coin = coin;
    }

}