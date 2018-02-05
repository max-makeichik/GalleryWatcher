package com.mm.gallerywatcher.presentation.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

/**
 * Created by Max Makeychik on 05-Feb-18.
 */
public class AnimatedRecyclerViewAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {

    private final static int ADD_ANIMATION_FADE_DURATION = 200;
    private int lastPosition = -1;

    @Override
    public T onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(T holder, int position) {
        setAnimation(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    /**
     * Apply animation to added views
     *
     * @param viewToAnimate
     * @param position
     */
    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            viewToAnimate.startAnimation(getAnimation());
            lastPosition = position;
        }
    }

    /**
     * @return animation to apply
     */
    private Animation getAnimation() {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(ADD_ANIMATION_FADE_DURATION);
        return anim;
    }

}