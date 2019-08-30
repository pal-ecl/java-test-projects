package com.lescure.dragoncardgame.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lescure.dragoncardgame.R;
import com.lescure.dragoncardgame.model.CardBean;

import java.util.ArrayList;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private ArrayList<CardBean> cards;
    CardListener cardListener;


    public CardAdapter(ArrayList<CardBean> cards, CardListener cardListener) {
        this.cards = cards;
        this.cardListener = cardListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup vg, int viewType) {
        View v = LayoutInflater.from(vg.getContext())
                .inflate(R.layout.display_card, vg, false);
        return new CardAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CardBean datum = cards.get(position);
        holder.tvName.setText(datum.getName());
        holder.tvName.setTextColor(datum.getTextColor());
        holder.tvStatus.setText("HP : " + datum.getHp() + " Power : " + datum.getPower());
        holder.tvStatus.setTextColor(datum.getTextColor());
        holder.ivCardImage.setImageResource(R.mipmap.ic_launcher_round);
        Log.w("TAG_CARD", "Card created : " + datum.getName());
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardListener != null) {
                    cardListener.onCardClick(datum);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        View root;
        TextView tvName;
        TextView tvStatus;
        ImageView ivCardImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            root = itemView.findViewById(R.id.root);
            tvName = itemView.findViewById(R.id.tvName);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            ivCardImage = itemView.findViewById(R.id.ivCardImage);
        }
    }

    public interface CardListener {
        void onCardClick(CardBean cardBean);
    }
}
