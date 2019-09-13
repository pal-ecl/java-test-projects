package com.lescure.dragoncardgame.adapters;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.lescure.dragoncardgame.FightActivity;
import com.lescure.dragoncardgame.R;
import com.lescure.dragoncardgame.model.CardBean;

import java.util.ArrayList;

public class DlsHandAdapter extends RecyclerView.Adapter<DlsHandAdapter.ViewHolder> {

    private ArrayList<CardBean> cards;
    CardAdapter.CardListener cardListener;



    public DlsHandAdapter(ArrayList<CardBean> cards) {
        this.cards = cards;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup vg, int viewType) {
        View v = LayoutInflater.from(vg.getContext())
                .inflate(R.layout.display_dls_hand, vg, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CardBean datum = cards.get(position);

        Log.w("TAG_CARD", "Card created : " + datum.getName());
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
