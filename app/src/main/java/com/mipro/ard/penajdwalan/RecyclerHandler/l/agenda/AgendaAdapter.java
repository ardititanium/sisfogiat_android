package com.mipro.ard.penajdwalan.RecyclerHandler.l.agenda;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mipro.ard.penajdwalan.R;
import com.mipro.ard.penajdwalan.tambah.tambah_agenda;

import java.util.List;

/**
 * Created by ard on 8/4/2016.
 */
public class AgendaAdapter extends RecyclerView.Adapter<daftarAgendaViewHolder> {
    Context context;
    public List<Agenda> daftarAgendaList;
    tambah_agenda pilihanDialog;

    public AgendaAdapter(List<Agenda> daftarAgendaList, Context context) {
        this.daftarAgendaList = daftarAgendaList;
        this.context = context;
    }

    @Override
    public daftarAgendaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_agenda_temp, parent, false);
        final daftarAgendaViewHolder nam =  new daftarAgendaViewHolder(view);

        return nam;
    }

    @Override
    public void onBindViewHolder(final daftarAgendaViewHolder holder, int position) {
        final Agenda listAgenda = daftarAgendaList.get(position);
        holder.deskripsi.setText(listAgenda.getDeskripsi());
        holder.jamMulai.setText(listAgenda.getJamMulai());
        holder.jamSelesai.setText(listAgenda.getJamSelesai());
        holder.keterangan.setText(listAgenda.getKeterangan());
        holder.durasi.setText(listAgenda.getDurasi());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




            }
        });
    }


    @Override
    public int getItemCount() {
        return daftarAgendaList.size();
    }
}
