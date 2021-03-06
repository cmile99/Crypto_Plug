package com.threeklines.cryptoplug;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.threeklines.cryptoplug.adapters.TwitterAdapter;
import com.threeklines.cryptoplug.backside.TweetCard;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TweetsList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TweetsList extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private List<TweetCard> tweetCards;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TweetsList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TweetsList.
     */
    // TODO: Rename and change types and number of parameters
    public static TweetsList newInstance(String param1, String param2) {
        TweetsList fragment = new TweetsList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tweets_list, container, false);
        RecyclerView tweets = view.findViewById(R.id.twitter_list);
        TwitterAdapter adapter = new TwitterAdapter(tweetCards);
        tweets.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        tweets.setAdapter(adapter);
        return view;
    }

    public void setTweetCards(List<TweetCard> tweetCards) {
        this.tweetCards = tweetCards;
    }
}