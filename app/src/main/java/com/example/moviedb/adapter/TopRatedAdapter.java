package com.example.moviedb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.helper.Const;
import com.example.moviedb.model.NowPlaying;
import com.example.moviedb.model.TopRated;

import java.util.List;

public class TopRatedAdapter extends RecyclerView.Adapter<TopRatedAdapter.CardViewViewHolder> {

    private Context context;
    private List<TopRated.Results> listTopRated;
    private List<TopRated.Results> getListTopRated(){return listTopRated;}
    public void setListTopRated(List<TopRated.Results> listTopRated){
        this.listTopRated = listTopRated;
    }
    public TopRatedAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public TopRatedAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_top_rated, parent, false);
        return new TopRatedAdapter.CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopRatedAdapter.CardViewViewHolder holder, int position) {
        final TopRated.Results results = getListTopRated().get(position);
        holder.lbl_title.setText(results.getTitle());
        holder.lbl_overview.setText(results.getOverview());
        holder.lbl_release_date.setText(results.getRelease_date());
        holder.lbl_vote_average.setText(String.valueOf(results.getVote_average()));
        Glide.with(context)
                .load(Const.IMG_URL + results.getPoster_path())
                .into(holder.img_poster);
    }

    @Override
    public int getItemCount() {
        return getListTopRated().size();
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        ImageView img_poster;
        TextView lbl_title, lbl_overview, lbl_release_date, lbl_vote_average;
        CardView cv;
        public CardViewViewHolder(@NonNull View itemView) {
            super(itemView);
            img_poster = itemView.findViewById(R.id.img_poster_card_top_rated);
            lbl_title = itemView.findViewById(R.id.lbl_title_card_top_rated);
            lbl_overview = itemView.findViewById(R.id.lbl_overview_card_top_rated);
            lbl_release_date = itemView.findViewById(R.id.lbl_releasedate_card_top_rated);
            lbl_vote_average = itemView.findViewById(R.id.lbl_vote_average_card_top_rated);
            cv = itemView.findViewById(R.id.cv_card_top_rated);
        }
    }
}
