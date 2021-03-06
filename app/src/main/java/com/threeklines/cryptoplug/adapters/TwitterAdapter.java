package com.threeklines.cryptoplug.adapters;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.threeklines.cryptoplug.R;
import com.threeklines.cryptoplug.backside.TweetCard;

import java.util.List;

public class TwitterAdapter extends RecyclerView.Adapter<TwitterAdapter.ViewHolder> {

    List<TweetCard> tweetCards;

    public TwitterAdapter(List<TweetCard> tweetCards) {
        this.tweetCards = tweetCards;
    }

    @NonNull
    @Override
    public TwitterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.twitter_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TwitterAdapter.ViewHolder holder, int position) {
        holder.webView.getSettings().setJavaScriptEnabled(true);
        String encodedHtml = Base64.encodeToString(tweetCards.get(position).getTweetUrl().getBytes(),
                Base64.NO_PADDING);
        holder.webView.loadData(encodedHtml, "text/html", "base64");
    }

    @Override
    public int getItemCount() {
        return tweetCards.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        WebView webView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            webView = itemView.findViewById(R.id.tweet_web_view);
        }
    }
}
