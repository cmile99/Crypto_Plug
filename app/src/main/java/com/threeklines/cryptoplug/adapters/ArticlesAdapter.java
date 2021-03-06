package com.threeklines.cryptoplug.adapters;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.threeklines.cryptoplug.R;
import com.threeklines.cryptoplug.articlesfragments.ArticleView;
import com.threeklines.cryptoplug.backside.Article;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder> {

    static List<Article> articles;

    public ArticlesAdapter(List<Article> articles) {
        ArticlesAdapter.articles = articles;
    }

    @NonNull
    @Override
    public ArticlesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.articles_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticlesAdapter.ViewHolder holder, int position) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                            Picasso.get()
                                    .load(articles.get(position).getImageUrl())
                                    .placeholder(R.drawable.image_loading)
                                    .error(R.drawable.error)
                                    .into(holder.articleImage);

                    }
                });
            }
        });

        holder.title.setText(articles.get(position).getTitle());
        holder.description.setText(articles.get(position).getBody());
        long date = articles.get(position).getDatePublished();
        holder.datePosted.setText(new SimpleDateFormat("yyyy MM dd HH:mm:ss").format(new Date(date)));

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView articleImage;
        TextView title;
        TextView description;
        TextView datePosted;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            articleImage = itemView.findViewById(R.id.article_image);
            title = itemView.findViewById(R.id.article_title);
            description = itemView.findViewById(R.id.article_description);
            datePosted = itemView.findViewById(R.id.article_date_published);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ArticleView articleView = new ArticleView();
            articleView.setArticle(articles.get(getAdapterPosition()));
            ((FragmentActivity)v.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.coins_frame_layout, articleView).commit();
        }
    }
}
