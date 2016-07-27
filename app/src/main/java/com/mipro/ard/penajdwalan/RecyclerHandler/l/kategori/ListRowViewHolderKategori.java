package com.mipro.ard.penajdwalan.RecyclerHandler.l.kategori;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mipro.ard.penajdwalan.R;

/**
 * Created by ard on 7/26/2016.
 */
public class ListRowViewHolderKategori extends RecyclerView.ViewHolder {
    protected TextView id_tv;
    protected TextView nama_kat_tv;
    protected RelativeLayout rLayout;


    public ListRowViewHolderKategori(View view) {
        super(view);
        this.id_tv = (TextView) view.findViewById(R.id.id_item_kat);
        this.nama_kat_tv = (TextView) view.findViewById(R.id.nama_item_kat);
        this.rLayout = (RelativeLayout) view.findViewById(R.id.layout_holder_kategori);
        view.setClickable(true);
    }
}
