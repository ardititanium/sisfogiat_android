package com.mipro.ard.penajdwalan.RecyclerHandler.l.agenda;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mipro.ard.penajdwalan.R;

/**
 * Created by ard on 8/4/2016.
 */
public class daftarAgendaViewHolder extends RecyclerView.ViewHolder {
    TextView deskripsi, jamMulai, jamSelesai, keterangan, durasi;
    RelativeLayout relativeLayout;

    public daftarAgendaViewHolder(View itemView) {
        super(itemView);
        this.deskripsi      = (TextView) itemView.findViewById(R.id.desk_agenda_tv);
        this.jamMulai       = (TextView) itemView.findViewById(R.id.jamMulai_agenda_tv);
        this.jamSelesai     = (TextView) itemView.findViewById(R.id.jamSelesai_agenda_tv);
        this.keterangan     = (TextView) itemView.findViewById(R.id.ket_agenda_tv);
        this.durasi         = (TextView) itemView.findViewById(R.id.durasi_agenda_tv);
        this.relativeLayout = (RelativeLayout) itemView.findViewById(R.id.rec_item_agenda);
        itemView.setClickable(true);
    }
}
