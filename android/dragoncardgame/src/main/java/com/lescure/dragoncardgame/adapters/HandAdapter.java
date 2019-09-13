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
import com.lescure.dragoncardgame.model.GameRules;

import java.util.ArrayList;

public class HandAdapter extends RecyclerView.Adapter<HandAdapter.ViewHolder> {

    private ArrayList<CardBean> cards;
    CardAdapter.CardListener cardListener;



    public HandAdapter(ArrayList<CardBean> cards, CardAdapter.CardListener cardListener) {
        this.cards = cards;
        this.cardListener = cardListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup vg, int viewType) {
        View v = LayoutInflater.from(vg.getContext())
                .inflate(R.layout.display_hand, vg, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CardBean card = cards.get(position);
        holder.tvName.setText(card.getName());
        holder.tvPower.setText(Integer.toString(card.getPower()));
        holder.tvHP.setText(Integer.toString(card.getHp()));
        holder.tvEffect.setText(card.getEffect());
        holder.ivCardImage.setColorFilter(card.getDragonColor());

        switch (card.getStatusAwake()){
            case GameRules.STATUS_AWAKENING_READY:
                holder.tvPower.setTextColor(Color.BLACK);
                holder.tvHP.setTextColor(Color.BLACK);
                holder.tvName.setTextColor(Color.BLACK);
                holder.ivCardImage.setImageResource(R.mipmap.ic_dragon);
                break;
            default:
                holder.tvPower.setTextColor(Color.GRAY);
                holder.tvHP.setTextColor(Color.GRAY);
                holder.tvName.setTextColor(Color.GRAY);
                holder.ivCardImage.setImageResource(R.mipmap.ic_dragon_sleep);
        }

        if (card.isAttacker()) {
            holder.cvCards.setCardBackgroundColor(Color.YELLOW);
        }else{
            switch (card.getStatusHealth()) {
                case GameRules.STATUS_HEALTH_POISONED:
                    holder.cvCards.setCardBackgroundColor(Color.GREEN);
                    break;
                default:
                    holder.cvCards.setCardBackgroundColor(Color.parseColor("#46392F"));
            }
        }

        Log.w("TAG_CARD", "Card created : " + card.getName());
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardListener != null) {
                    cardListener.onCardClick(card, v);
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
        TextView tvName,tvPower, tvHP, tvEffect;
        ImageView ivCardImage;
        CardView cvCards;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            root = itemView.findViewById(R.id.root);
            tvName = itemView.findViewById(R.id.tvName);
            tvPower = itemView.findViewById(R.id.tvPower);
            tvHP = itemView.findViewById(R.id.tvHP);
            tvEffect = itemView.findViewById(R.id.tvEffect);
            ivCardImage = itemView.findViewById(R.id.ivCardImage);
            cvCards = itemView.findViewById(R.id.cvCards);
        }
    }

    public interface CardListener {
        void onCardClick(CardBean cardBean, View view);
    }
}
