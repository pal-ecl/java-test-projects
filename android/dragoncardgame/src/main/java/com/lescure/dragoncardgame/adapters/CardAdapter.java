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
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CardBean datum = cards.get(position);
        holder.tvName.setText(datum.getName());
        holder.tvPower.setText(Integer.toString(datum.getPower()));
        holder.tvHP.setText(Integer.toString(datum.getHp()));
        holder.tvEffect.setText(datum.getEffect());
        holder.ivCardImage.setColorFilter(datum.getDragonColor());

        switch (datum.getStatusAwake()){
            case FightActivity.STATUS_AWAKENING_READY:
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

        if (datum.isAttacker()) {
            holder.cvCards.setCardBackgroundColor(Color.YELLOW);
        }else{
            switch (datum.getStatusHealth()) {
                case FightActivity.STATUS_HEALTH_POISONED:
                    holder.cvCards.setCardBackgroundColor(Color.GREEN);
                    break;
                default:
                    holder.cvCards.setCardBackgroundColor(Color.WHITE);
            }
        }

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
        TextView tvName,tvPower, tvHP, tvEffect;
        ImageView ivCardImage;
        CardView cvCards;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            root = itemView.findViewById(R.id.root);
            tvName = itemView.findViewById(R.id.tvName);
            tvPower = itemView.findViewById(R.id.tvPower);
            tvEffect = itemView.findViewById(R.id.tvEffect);
            tvHP = itemView.findViewById(R.id.tvHP);
            ivCardImage = itemView.findViewById(R.id.ivCardImage);
            cvCards = itemView.findViewById(R.id.cvCards);
        }
    }

    public interface CardListener {
        void onCardClick(CardBean cardBean);
    }
}
