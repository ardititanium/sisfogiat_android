package com.mipro.ard.penajdwalan.jadwal.RecylerNavC;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mipro.ard.penajdwalan.R;

/**
 * Created by ard on 7/26/2016.
 */
public class ListViewHolderTanggal extends RecyclerView.ViewHolder {
    protected TextView tgl_tv, bulan_tv;
    public RelativeLayout rLayout;
    public ListViewHolderTanggal(View view) {
        super(view);
        this.tgl_tv = (TextView) view.findViewById(R.id.tgl_nav);
        this.bulan_tv = (TextView) view.findViewById(R.id.bln_nav);


        this.rLayout = (RelativeLayout) view.findViewById(R.id.calender_container);
        view.setClickable(true);


    }

}
