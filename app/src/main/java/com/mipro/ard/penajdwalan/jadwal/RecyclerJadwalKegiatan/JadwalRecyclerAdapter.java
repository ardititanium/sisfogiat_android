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

import java.util.List;

/**
 * Created by ard on 7/26/2016.
 */
public class JadwalRecyclerAdapter extends RecyclerView.Adapter<ListRowViewHolderJadwal>{
    private List<ListItemJadwal> listItemJadwals;
    private Context context;
    private int focusedItem;

    public JadwalRecyclerAdapter(Context context, List<ListItemJadwal> listItemJadwals) {
        this.listItemJadwals = listItemJadwals;
        this.context = context;
    }

    @Override
    public ListRowViewHolderJadwal onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_jadwal, parent, false);
        final ListRowViewHolderJadwal holder = new ListRowViewHolderJadwal(v);

        holder.rLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle dataGiat = new Bundle();


                Intent intent = new Intent(context, detail_kegiatan.class);
                intent.putExtras(dataGiat);
                context.startActivity(intent);


            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ListRowViewHolderJadwal holder, int position) {
        ListItemJadwal listItemKegiatan = listItemJadwals.get(position);
        holder.itemView.setSelected(focusedItem == position);
        holder.getLayoutPosition();

        holder.namaKegiatan.setText(listItemKegiatan.getNamaKegiatan());
        holder.jamMulai.setText(listItemKegiatan.getJamMulai());
        holder.lokasi.setText(listItemKegiatan.getNamaLokasi());

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
