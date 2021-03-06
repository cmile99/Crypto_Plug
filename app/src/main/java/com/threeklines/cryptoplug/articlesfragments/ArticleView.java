package com.threeklines.cryptoplug.articlesfragments;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.threeklines.cryptoplug.R;
import com.threeklines.cryptoplug.backside.Article;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ArticleView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArticleView extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Article article;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ArticleView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ArticleView.
     */
    // TODO: Rename and change types and number of parameters
    public static ArticleView newInstance(String param1, String param2) {
        ArticleView fragment = new ArticleView();
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
        View view = inflater.inflate(R.layout.fragment_article_view, container, false);
        WebView webView = view.findViewById(R.id.article_web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new ArticleBrowser());
        webView.loadUrl(article.getUrl());
        return view;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    private class ArticleBrowser extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }

        @Override
        public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
            if (event.getSource() ==  KeyEvent.KEYCODE_BACK){
                Toast.makeText(getContext(), "Back clicked", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
            return super.shouldOverrideKeyEvent(view, event);
        }
    }
}