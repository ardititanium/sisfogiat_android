package com.mipro.ard.penajdwalan.jadwal.RecyclerJadwal.pilihPersonil;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mipro.ard.penajdwalan.R;

/**
 * Created by ard on 7/25/2016.
 */
public class ListRowViewHolderPilihPersonil extends RecyclerView.ViewHolder {
    protected TextView nrp_tv, nama_tv, pangkat_tv, ic_tv;
    protected RelativeLayout rLayout;

    public ListRowViewHolderPilihPersonil(View view) {
        super(view);
        this.nrp_tv = (TextView) view.findViewById(R.id.nrp_per_select);
        this.nama_tv = (TextView) view.findViewById(R.id.nama_pers_select);
        this.pangkat_tv = (TextView) view.findViewById(R.id.pangkat_pers_select);
        this.ic_tv = (TextView) view.findViewById(R.id.ic_pers_select);


        this.rLayout = (RelativeLayout) view.findViewById(R.id.layout_holder_pilihpersonil);
        view.setClickable(true);


    }
}
