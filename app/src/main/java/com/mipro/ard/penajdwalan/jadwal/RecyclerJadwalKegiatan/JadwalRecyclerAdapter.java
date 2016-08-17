package com.mipro.ard.penajdwalan.jadwal.RecyclerJadwalKegiatan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mipro.ard.penajdwalan.R;
import com.mipro.ard.penajdwalan.detail.detail_kegiatan;
import com.mipro.ard.penajdwalan.jadwal.ListJadwal;
import com.mipro.ard.penajdwalan.jadwal.detail_jadwal;

import java.util.List;

/**
 * Created by ard on 7/26/2016.
 */
public class JadwalRecyclerAdapter extends RecyclerView.Adapter<ListRowViewHolderJadwal>{
    private List<ListItemJadwal> listItemJadwals;
    private Context context;
    private int focusedItem;
    ListJadwal ctx;

    public JadwalRecyclerAdapter(Context context, List<ListItemJadwal> listItemJadwals) {
        this.listItemJadwals = listItemJadwals;
        this.context = context;
    }

    @Override
    public ListRowViewHolderJadwal onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_jadwal, parent, false);
        final ListRowViewHolderJadwal holder = new ListRowViewHolderJadwal(v);

        final TextView idJadwal_tv = new ListRowViewHolderJadwal(v).idJadwal;
        final TextView idGiat_tv = new ListRowViewHolderJadwal(v).idKegiatan;

        holder.rLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idJadwal = idJadwal_tv.getText().toString();
                String idKegiatan = idGiat_tv.getText().toString();
                Intent intent = new Intent(context, detail_jadwal.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("idJadwal", idJadwal);
                intent.putExtra("idGiat", idKegiatan);
                context.startActivity(intent);


            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ListRowViewHolderJadwal holder, int position) {
        ListItemJadwal listItemJadwal = listItemJadwals.get(position);
        holder.itemView.setSelected(focusedItem == position);
        holder.getLayoutPosition();

        holder.namaKegiatan.setText(listItemJadwal.getNamaKegiatan());
        holder.jamMulai.setText(listItemJadwal.getJamMulai());
        holder.lokasi.setText(listItemJadwal.getNamaLokasi());
        holder.idJadwal.setText(listItemJadwal.getIdJadwal());
        holder.idKegiatan.setText(listItemJadwal.getIdKegiatan());

        holder.idJadwal.setVisibility(View.GONE);
        holder.idKegiatan.setVisibility(View.GONE);

    }

    public void clearAdapter(){
        listItemJadwals.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (null != listItemJadwals ? listItemJadwals.size() : 0);
    }
}
