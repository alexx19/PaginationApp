package com.aurriola.pagination.app.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.aurriola.pagination.app.R;
import com.aurriola.pagination.app.engine.ModelResult;
import com.aurriola.pagination.app.engine.PicassoTrustAll;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alexander Urriola.
 */
public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.PersonViewHolder> implements Filterable {
    private String TAG = "ResultAdapter";
    private List<ModelResult> modelResults; //Almacena el resultado total del llamado a la API.
    private List<ModelResult> modelResultsFilter; //guardara el resultado de la búsqueda, cuando se utilice el método getFilter()

    public ResultAdapter(List<ModelResult> modelResults) {
        this.modelResults = modelResults;
        this.modelResultsFilter = modelResults;
    }

    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_result_item, parent, false);//false, para reutilizarce

        return new PersonViewHolder(view);
    }

    public void onBindViewHolder(@NonNull PersonViewHolder holder, int i){
        holder.txtTitle.setText(modelResultsFilter.get(i).getTilte());
        holder.txtDetail.setText(modelResultsFilter.get(i).getDescription());
        PicassoTrustAll.getInstance(holder.itemView.getContext()).load(modelResultsFilter.get(i).getUrl_img()).into(holder.img_profile);
    }

    public int getItemCount()
    {
        return modelResultsFilter.size();
    }

    @Override
    public Filter getFilter() {
        return getValueFilter();
    }

    /**
     * Buscar los usuarios dentro del listado ya existente
     * @return respuesta de la busqueda.
     */
    private Filter getValueFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence queryfind) {
                String query = queryfind.toString();
                if (query.isEmpty()) {
                    modelResultsFilter = modelResults;
                }else {
                    List<ModelResult> filteredmodelResult = new ArrayList<>();
                    for (ModelResult row: modelResults)
                    {
                        //condición para validar, si el usuario tiene los caracteres que se buscan.
                        if (row.getTilte().toLowerCase().contains(query.toLowerCase()))
                        {
                            filteredmodelResult.add(row);
                        }
                    }
                    modelResultsFilter = filteredmodelResult;


                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = modelResultsFilter;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                modelResultsFilter = (ArrayList<ModelResult>) filterResults.values;
                Log.d(TAG,modelResultsFilter.toString());

                notifyDataSetChanged();
            }
        };
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
