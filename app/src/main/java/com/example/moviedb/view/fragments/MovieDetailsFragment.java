package com.example.moviedb.view.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.adapter.ProductionCompanyAdapter;
import com.example.moviedb.helper.Const;
import com.example.moviedb.model.Movies;
import com.example.moviedb.view.activities.MovieDetailsActivity;
import com.example.moviedb.viewmodel.MovieViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MovieDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieDetailsFragment newInstance(String param1, String param2) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
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
            dialog = ProgressDialog.show(getActivity(), "", "Loading...", true);
            dialog.show();
        }
    }

    private MovieViewModel viewModel;
    private TextView lbl_movie_id, lbl_title, lbl_overview, lbl_release_date, lbl_genre, lbl_tagline, lbl_avg_vote, lbl_vote_count, lbl_popularity;
    private ImageView img_poster, img_backdrop;
    private RecyclerView rv_logo_companies;
    private String movie_id = "", movie_genre = "";
    private ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);
        lbl_movie_id = view.findViewById(R.id.lbl_movie_id_movie_details_fragment);
        lbl_title = view.findViewById(R.id.lbl_title_movie_details_fragment);
        lbl_overview = view.findViewById(R.id.lbl_overview_movie_details_fragment);
        lbl_release_date = view.findViewById(R.id.lbl_release_date_movie_details_fragment);
        lbl_genre = view.findViewById(R.id.lbl_genre_movie_details_fragment);
        lbl_tagline = view.findViewById(R.id.lbl_tagline_movie_details_fragment);
        lbl_avg_vote = view.findViewById(R.id.lbl_avg_vote_movie_details_fragment);
        lbl_vote_count = view.findViewById(R.id.lbl_vote_count_movie_details_fragment);
        lbl_popularity = view.findViewById(R.id.lbl_popularity_movie_details_fragment);
        img_poster = view.findViewById(R.id.img_poster_movie_details_fragment);
        img_backdrop = view.findViewById(R.id.img_backdrop_movie_details_fragment);
        rv_logo_companies = view.findViewById(R.id.rv_logo_companies_movie_details_fragment);

        String movieId = getArguments().getString("movieId").toString();
        lbl_movie_id.setText("ID : "+movieId);
        viewModel = new ViewModelProvider(getActivity()).get(MovieViewModel.class);
        viewModel.getMovieById(movieId);
        viewModel.getResultGetMovieById().observe(getActivity(), showMovieDetails);

        return view;
    }

    private Observer<Movies> showMovieDetails = new Observer<Movies>() {
        @Override
        public void onChanged(Movies movies) {
            rv_logo_companies.setLayoutManager(new GridLayoutManager(getActivity(), 1, RecyclerView.HORIZONTAL, false));
            ProductionCompanyAdapter adapter = new ProductionCompanyAdapter(getActivity());
            adapter.setListCompany(movies.getProduction_companies());
            rv_logo_companies.setAdapter(adapter);
            dialog.dismiss();

            String title = movies.getTitle();
            String overview = movies.getOverview();
            String release_date = movies.getRelease_date();
            String tagline = movies.getTagline();
            Integer vote_count = movies.getVote_count();
            Double avg_vote = movies.getVote_average();
            Double popularity = movies.getPopularity();
            String img_path = Const.IMG_URL + movies.getPoster_path().toString();
            String backdrop = Const.IMG_URL + movies.getBackdrop_path().toString();

            lbl_title.setText(title);
            lbl_overview.setText(overview);
            lbl_release_date.setText(release_date);
            lbl_avg_vote.setText(avg_vote.toString());
            lbl_vote_count.setText(vote_count.toString());
            lbl_tagline.setText(tagline);
            lbl_popularity.setText(popularity.toString());
            Glide.with(getActivity()).load(backdrop).into(img_backdrop);
            Glide.with(getActivity()).load(img_path).into(img_poster);
            for (int i = 0; i<movies.getGenres().size(); i++){
                if(i == movies.getGenres().size() - 1){
                    movie_genre += movies.getGenres().get(i).getName();
                }else{
                    movie_genre += movies.getGenres().get(i).getName() + ", ";
                }
            }
            lbl_genre.setText(movie_genre);

        }
    };

}