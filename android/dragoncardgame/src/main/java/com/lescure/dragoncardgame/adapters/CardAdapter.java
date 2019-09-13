package com.lescure.dragoncardgame.adapters;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
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

        switch(card.getAnimate()){
            case GameRules.ANIMATE_ATTACK:
                animateAttack(holder.cvCards, GameRules.ANIM_PLAYERS_ATTACK);
                card.setAnimate(GameRules.ANIMATE_NONE);
                break;
            case GameRules.ANIMATE_DEFEND:
                animateDefend(holder.cvCards);
                card.setAnimate(GameRules.ANIMATE_NONE);
                break;
        }
        final CardView animateView = holder.cvCards;
        Log.w("TAG_CARD", "Card created : " + card.getName());
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardListener != null) {
                    cardListener.onCardClick(card, animateView);
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
        void onCardClick(CardBean cardBean, View view);
    }

    public void animateAttack(View view, int toYDelta){
        Log.w("TAG_ANIMATION", "animation attack : "+view.getId());
        RotateAnimation rotateAnimation = new RotateAnimation(0.0f, 25.0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(100);

        TranslateAnimation translateAnimation = new TranslateAnimation(
                0, 0, 0, toYDelta);
        translateAnimation.setStartOffset(200);
        translateAnimation.setDuration(100);

        AnimationSet set = new AnimationSet(true);
        set.addAnimation(rotateAnimation);
        set.addAnimation(translateAnimation);
        view.startAnimation(set);
    }

    public void animateDefend (View view){
        Log.w("TAG_ANIMATION", "animation defend : "+view.getId());
        TranslateAnimation translateAnimation = new TranslateAnimation(
                -3, 3, 0, 0);
        translateAnimation.setDuration(80);
        translateAnimation.setRepeatCount(5);

        AnimationSet set = new AnimationSet(true);
        set.addAnimation(translateAnimation);
        view.startAnimation(set);
    }
}
