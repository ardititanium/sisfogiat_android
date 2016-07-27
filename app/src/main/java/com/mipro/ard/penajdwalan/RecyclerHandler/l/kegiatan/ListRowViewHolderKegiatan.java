package com.mipro.ard.penajdwalan.RecyclerHandler.l.kegiatan;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mipro.ard.penajdwalan.R;

/**
 * Created by ard on 7/26/2016.
 */
public class ListRowViewHolderKegiatan extends RecyclerView.ViewHolder {
    protected TextView nama_giat_tv, kategori_giat_tv, lokasi_giat_tv, ic_giat_tv, status_ic;
    protected TextView id_tv, dLokasi_tv, aLokasi_tv, kota_tv, status_tv, bagian_tv, desk_tv, satuan_tv;
    protected RelativeLayout rLayout;
    public ListRowViewHolderKegiatan(View view) {
        super(view);
        this.nama_giat_tv = (TextView) view.findViewById(R.id.nama_giat_item);
        this.kategori_giat_tv = (TextView) view.findViewById(R.id.kat_giat_item);
        this.lokasi_giat_tv = (TextView) view.findViewById(R.id.lokasi_giat_item);
        this.ic_giat_tv = (TextView) view.findViewById(R.id.no_giat_item);
        this.status_ic = (TextView) view.findViewById(R.id.status_indicator_giat);

        this.id_tv = (TextView) view.findViewById(R.id.hide_id_kegiatan);
        this.dLokasi_tv = (TextView) view.findViewById(R.id.hide_dl_item);
        this.aLokasi_tv = (TextView) view.findViewById(R.id.hide_alamat_lokasi_item);
        this.kota_tv = (TextView) view.findViewById(R.id.hide_kota_item);
        this.status_tv = (TextView) view.findViewById(R.id.hide_status_item);
        this.bagian_tv = (TextView) view.findViewById(R.id.hide_bagian_item);
        this.desk_tv = (TextView) view.findViewById(R.id.hide_desk_item);
        this.satuan_tv = (TextView) view.findViewById(R.id.hide_satuan_item);

        this.id_tv.setVisibility(View.GONE);
        this.dLokasi_tv.setVisibility(View.GONE);
        this.aLokasi_tv.setVisibility(View.GONE);
        this.kota_tv.setVisibility(View.GONE);
        this.status_tv.setVisibility(View.GONE);
        this.bagian_tv.setVisibility(View.GONE);
        this.desk_tv.setVisibility(View.GONE);
        this.satuan_tv.setVisibility(View.GONE);

        this.rLayout = (RelativeLayout) view.findViewById(R.id.layout_holder_kegiatan);
        view.setClickable(true);


    }

}
