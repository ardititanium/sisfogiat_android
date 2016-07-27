package com.mipro.ard.penajdwalan.RecyclerHandler.l.satlantas;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mipro.ard.penajdwalan.R;

/**
 * Created by ard on 7/25/2016.
 */
public class ListRowViewHolderSatlantas extends RecyclerView.ViewHolder {
    protected TextView ic_tv;
    protected TextView nama_tv;
    protected TextView alamat_tv;

    protected RelativeLayout rLyout;


    public ListRowViewHolderSatlantas(View view){
        super(view);
        this.ic_tv = (TextView) view.findViewById(R.id.pangkat_pers_pop);
        this.nama_tv = (TextView) view.findViewById(R.id.nama_sat);
        this.alamat_tv = (TextView) view.findViewById(R.id.alamat_sat);



        this.rLyout = (RelativeLayout) view.findViewById(R.id.layout_holder);
        view.setClickable(true);
    }
}
