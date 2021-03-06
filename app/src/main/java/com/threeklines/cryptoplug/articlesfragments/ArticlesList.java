package com.threeklines.cryptoplug.articlesfragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.threeklines.cryptoplug.R;
import com.threeklines.cryptoplug.adapters.ArticlesAdapter;
import com.threeklines.cryptoplug.backside.Article;
import com.threeklines.cryptoplug.backside.DatabaseHandler;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ArticlesList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArticlesList extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private List<Article> articles;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ArticlesList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ArticlesList.
     */
    // TODO: Rename and change types and number of parameters
    public static ArticlesList newInstance(String param1, String param2) {
        ArticlesList fragment = new ArticlesList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseHandler databaseHandler = new DatabaseHandler(getContext(), "my_db.db", null, 1);
        articles = databaseHandler.queryArticles();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_articles_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.latest_articles);
        ArticlesAdapter adapter = new ArticlesAdapter(articles);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);


        return view;
    }
}