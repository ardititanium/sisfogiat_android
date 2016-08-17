package com.mipro.ard.penajdwalan.RecyclerHandler.l.Laporan;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mipro.ard.penajdwalan.R;

/**
 * Created by ard on 7/26/2016.
 */
public class ListRowViewHolderLaporan extends RecyclerView.ViewHolder {
    protected TextView namaKegiatan, waktu, lokasi, idJadwal, idKegiatan, satuan, peserta;
    protected RelativeLayout rLayout;
    public ListRowViewHolderLaporan(View view) {
        super(view);
        this.namaKegiatan = (TextView) view.findViewById(R.id.nama_jadwal_kegiatan);
        this.idJadwal = (TextView) view.findViewById(R.id.idJadwal_kegiatan);
        this.idKegiatan = (TextView) view.findViewById(R.id.idKegiatan_kegiatan);
        this.waktu = (TextView) view.findViewById(R.id.waktu_jadwal);
        this.satuan = (TextView) view.findViewById(R.id.pelaksana_giat);
        this.peserta = (TextView) view.findViewById(R.id.peserta_giat);
        this.rLayout = (RelativeLayout) view.findViewById(R.id.layout_holder_jadwal);
        view.setClickable(true);
    }

}
