package com.threeklines.cryptoplug.coinfragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.threeklines.cryptoplug.R;
import com.threeklines.cryptoplug.adapters.AllCoinsAdapter;
import com.threeklines.cryptoplug.backside.Coin;
import com.threeklines.cryptoplug.backside.DatabaseHandler;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllCoinsList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllCoinsList extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private SearchView searchView;
    private RecyclerView allCoinsList;
    private DatabaseHandler databaseHandler;
    private List<Coin> allCoins;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AllCoinsList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllCoinsList.
     */
    // TODO: Rename and change types and number of parameters
    public static AllCoinsList newInstance(String param1, String param2) {
        AllCoinsList fragment = new AllCoinsList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHandler = new DatabaseHandler(getContext(), "my_db.db", null, 1);
        allCoins = databaseHandler.queryCoinList();



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_coins_list, container, false);

//        searchView = (SearchView) view.findViewById(R.id.search_coin);
        allCoinsList = view.findViewById(R.id.all_coins_list);
        AllCoinsAdapter adapter = new AllCoinsAdapter(allCoins);
        allCoinsList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        allCoinsList.setAdapter(adapter);

        return view;
    }

}