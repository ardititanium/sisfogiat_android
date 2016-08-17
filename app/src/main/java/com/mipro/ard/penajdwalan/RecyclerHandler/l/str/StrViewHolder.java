package com.mipro.ard.penajdwalan.RecyclerHandler.l.str;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.mipro.ard.penajdwalan.R;

/**
 * Created by ard on 8/12/2016.
 */
public class StrViewHolder extends RecyclerView.ViewHolder {
    public ImageView strThumb;


    public StrViewHolder(View itemView) {
        super(itemView);
        strThumb = (ImageView) itemView.findViewById(R.id.str_thumb);
    }
}
