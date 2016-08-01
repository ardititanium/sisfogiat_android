package com.mipro.ard.penajdwalan.jadwal.RecyclerJadwalKegiatan;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mipro.ard.penajdwalan.R;

/**
 * Created by ard on 7/26/2016.
 */
public class ListRowViewHolderJadwal extends RecyclerView.ViewHolder {
    protected TextView namaKegiatan, jamMulai, lokasi;
    protected RelativeLayout rLayout;
    public ListRowViewHolderJadwal(View view) {
        super(view);
        this.namaKegiatan = (TextView) view.findViewById(R.id.nama_jadwal_kegiatan);
        this.jamMulai = (TextView) view.findViewById(R.id.waktu_jadwal);
        this.lokasi = (TextView) view.findViewById(R.id.lokasi_jadwal);

        this.rLayout = (RelativeLayout) view.findViewById(R.id.layout_holder_jadwal);
        view.setClickable(true);


    }

}
