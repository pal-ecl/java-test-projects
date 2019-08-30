package com.lescure.toulousesacchiens;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lescure.toulousesacchiens.model.DogBagPlaceBean;
import com.lescure.toulousesacchiens.model.RecordBean;

import java.util.ArrayList;

public class BagDogPlaceAdapter extends RecyclerView.Adapter<BagDogPlaceAdapter.ViewHolder> {

    private ArrayList<RecordBean> data;
    OnBagDogPlaceAdapterListener listener;

    public BagDogPlaceAdapter(ArrayList<RecordBean> data, OnBagDogPlaceAdapterListener listener) {
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup vg, int viewType) {
        View v = LayoutInflater.from(vg.getContext()).inflate(R.layout.row_dog_bag, vg, false);
        return new BagDogPlaceAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final RecordBean datum = data.get(position);

        Log.w("TAG_ADAPTER", "creating row");
        holder.tvDogBagPlace.setText("Lieu : " + datum.getFields().getIntitule());
        holder.tvDogBagNbr.setText("Numéro de distributeur : " + datum.getFields().getNumero());
        holder.tvDogBagLat.setText("Latitude : " + datum.getFields().getGeo_point_2d()[1]);
        holder.tvDogBagLong.setText("Longitude : " + datum.getFields().getGeo_point_2d()[0]);
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onRecordClick(datum.getFields());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvDogBagPlace;
        TextView tvDogBagNbr;
        TextView tvDogBagLat;
        TextView tvDogBagLong;
        View root;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDogBagPlace = itemView.findViewById(R.id.tvDogBagPlace);
            tvDogBagNbr = itemView.findViewById(R.id.tvDogBagNbr);
            tvDogBagLat = itemView.findViewById(R.id.tvDogBagLat);
            tvDogBagLong = itemView.findViewById(R.id.tvDogBagLong);
            root = itemView.findViewById(R.id.root);
        }
    }

    public interface OnBagDogPlaceAdapterListener {
        void onRecordClick(DogBagPlaceBean dogBagPlaceBean);
    }

}
