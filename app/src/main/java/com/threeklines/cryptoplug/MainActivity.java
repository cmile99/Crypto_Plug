package com.threeklines.cryptoplug;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.threeklines.cryptoplug.articlesfragments.ArticlesList;
import com.threeklines.cryptoplug.backside.ApiCenter;
import com.threeklines.cryptoplug.backside.DatabaseHandler;
import com.threeklines.cryptoplug.coinfragments.AllCoinsFragment;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Main";
    private static final String DB_NAME = "my_db.db";
    public static final int DB_VERSION = 1;
    private ApiCenter apiCenter;
    private DatabaseHandler databaseHandler;
    private BottomNavigationView bottomNavigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        databaseHandler = new DatabaseHandler(getApplicationContext(), "my_db.db", null, 1);
        ApiCenter apiCenter = new ApiCenter();

        TweetsList tweetsList = new TweetsList();
//        apiCenter.getLatestArticles(databaseHandler);
        executor.execute(() -> {
            apiCenter.getTwitterStatuses("#blockchain", databaseHandler);
            handler.post(() -> {
                tweetsList.setTweetCards(databaseHandler.queryTweetCards());
            });
        });
        //instanciating variables
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        setFragment(new AllCoinsFragment());



        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.coins){
                setFragment(new AllCoinsFragment());
                return true;
            }

            if (item.getItemId() == R.id.twitter){

                setFragment(tweetsList);
                return true;
            }

            if (item.getItemId() == R.id.articles){
               setFragment(new ArticlesList());
               return true;

            }

            if (item.getItemId() == R.id.reddit){
                //ToDo: weitch to reddit
            }
            return false;
        });

    }

    private void setFragment(Fragment fragment){
       FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.coins_frame_layout, fragment).commit();
    }
}