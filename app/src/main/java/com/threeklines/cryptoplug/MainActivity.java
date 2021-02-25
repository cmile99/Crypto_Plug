package com.threeklines.cryptoplug;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.threeklines.cryptoplug.backside.Coin;
import com.threeklines.cryptoplug.backside.DataCenter;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button categories = findViewById(R.id.load_gf);
        Button articles = findViewById(R.id.articles);

        Button coinList = findViewById(R.id.load_coin_list);
        Button coin = findViewById(R.id.coin_info);


        coinList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataCenter.getCoinsList();
            }
        });

        coin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Coin c = new Coin();
                c.setName("Bitcoin");
                c.setSlug("bitcoin");
                DataCenter.getCoinMetadata(c);
            }
        });

        articles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataCenter.getLatestArticles();
            }
        });

        categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataCenter.getCategories();
            }
        });

    }
}