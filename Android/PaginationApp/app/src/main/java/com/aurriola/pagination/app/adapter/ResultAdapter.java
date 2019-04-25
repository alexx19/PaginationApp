package com.aurriola.pagination.app.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aurriola.pagination.app.R;
import com.aurriola.pagination.app.engine.ModelResult;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alexander Urriola.
 */
public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.PersonViewHolder> {

    private List<ModelResult> modelResults;

    public ResultAdapter(List<ModelResult> modelResults) {
        this.modelResults = modelResults;
    }

    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_result_item, parent, false);//false, para reutilizarce

        return new PersonViewHolder(view);
    }

    public void onBindViewHolder(@NonNull PersonViewHolder holder, int i){
        holder.txtTitle.setText(modelResults.get(i).getTilte());
        holder.txtDetail.setText(modelResults.get(i).getDescription());

        Picasso.get().load(modelResults.get(i).getUrl_img()).placeholder(R.drawable.ic_picture_default).into(holder.img_profile);
    }

    public int getItemCount()
    {
        return modelResults.size();
    }

    public class PersonViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.txtTitle)
        TextView txtTitle;
        @BindView(R.id.txtDetail)
        TextView txtDetail;
        @BindView(R.id.img_profile)
        ImageView img_profile;


        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
