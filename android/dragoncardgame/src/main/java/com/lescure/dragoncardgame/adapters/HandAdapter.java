package com.lescure.dragoncardgame.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lescure.dragoncardgame.R;
import com.lescure.dragoncardgame.model.CardBean;

import java.util.ArrayList;

public class HandAdapter extends RecyclerView.Adapter<HandAdapter.ViewHolder> {

    private ArrayList<CardBean> cards;
    CardListener cardListener;

    public HandAdapter(ArrayList<CardBean> cards, CardListener cardListener) {
        this.cards = cards;
        this.cardListener = cardListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup vg, int viewType) {
        View v = LayoutInflater.from(vg.getContext()).inflate(R.layout.display_hand, vg, false);
        return new HandAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


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

    public interface CardListener {

    }
}
