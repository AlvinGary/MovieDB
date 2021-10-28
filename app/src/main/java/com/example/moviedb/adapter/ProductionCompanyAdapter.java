package com.example.moviedb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.helper.Const;
import com.example.moviedb.model.Movies;
import com.example.moviedb.model.NowPlaying;

import java.util.List;

public class ProductionCompanyAdapter extends RecyclerView.Adapter<ProductionCompanyAdapter.ViewHolder> {

    private Context context;
    private List<Movies.ProductionCompanies> listCompany;
    private List<Movies.ProductionCompanies> getListCompany(){return listCompany;}
    public void setListCompany(List<Movies.ProductionCompanies> listCompany){
        this.listCompany = listCompany;
    }
    public ProductionCompanyAdapter(Context context){
        this.context = context;
    }
    public Context getContext(){
        return context;
    }

    @NonNull
    @Override
    public ProductionCompanyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_companies, parent, false);
        return new ProductionCompanyAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductionCompanyAdapter.ViewHolder holder, int position) {
        final Movies.ProductionCompanies results = getListCompany().get(position);
        Glide.with(context)
                .load(Const.IMG_URL + results.getLogo_path())
                .into(holder.img_company);
        holder.img_company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, results.getName() , Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return getListCompany().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img_company;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_company = itemView.findViewById(R.id.img_card_company);
        }
    }
}
