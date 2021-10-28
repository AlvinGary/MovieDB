package com.example.moviedb.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moviedb.R;
import com.example.moviedb.adapter.TopRatedAdapter;
import com.example.moviedb.helper.ItemClickSupport;
import com.example.moviedb.model.TopRated;
import com.example.moviedb.viewmodel.MovieViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TopRatedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopRatedFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TopRatedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment topRatedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TopRatedFragment newInstance(String param1, String param2) {
        TopRatedFragment fragment = new TopRatedFragment();
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

    private RecyclerView rv_top_rated;
    private MovieViewModel view_model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_rated, container, false);

        rv_top_rated = view.findViewById(R.id.rv_top_rated_fragment);
        view_model = new ViewModelProvider(getActivity()).get(MovieViewModel.class);
        view_model.getTopRated();
        view_model.getResultTopRated().observe(getActivity(), showTopRated);

        return view;
    }

    private Observer<TopRated> showTopRated = new Observer<TopRated>() {
        @Override
        public void onChanged(TopRated topRated) {
            rv_top_rated.setLayoutManager(new LinearLayoutManager(getActivity()));
            TopRatedAdapter adapter = new TopRatedAdapter(getActivity());
            adapter.setListTopRated(topRated.getResults());
            rv_top_rated.setAdapter(adapter);

            ItemClickSupport.addTo(rv_top_rated).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("movieId", ""+ topRated.getResults().get(position).getId());
                    Navigation.findNavController(v).navigate(R.id.action_topRatedFragment_to_MovieDetailsFragment, bundle);
                }
            });
        }
    };

}