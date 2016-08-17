package com.mipro.ard.penajdwalan.RecyclerHandler.l.Laporan;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mipro.ard.penajdwalan.R;
import com.mipro.ard.penajdwalan.jadwal.ListJadwal;
import com.mipro.ard.penajdwalan.jadwal.detail_jadwal;

import java.util.List;

/**
 * Created by ard on 7/26/2016.
 */
public class LaporanRecyclerAdapter extends RecyclerView.Adapter<ListRowViewHolderLaporan>{
    private List<ListItemLaporan> listItemLaporan;
    private Context context;
    private int focusedItem;
    ListJadwal ctx;

    public LaporanRecyclerAdapter(Context context, List<ListItemLaporan> listItemLaporan) {
        this.listItemLaporan = listItemLaporan;
        this.context = context;
    }

    @Override
    public ListRowViewHolderLaporan onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_laporan, parent, false);
        final ListRowViewHolderLaporan holder = new ListRowViewHolderLaporan(v);

        final TextView idJadwal_tv = new ListRowViewHolderLaporan(v).idJadwal;
        final TextView idGiat_tv = new ListRowViewHolderLaporan(v).idKegiatan;

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
    public void onBindViewHolder(ListRowViewHolderLaporan holder, int position) {
        ListItemLaporan listItemLaporan = this.listItemLaporan.get(position);
        holder.itemView.setSelected(focusedItem == position);
        holder.getLayoutPosition();

        holder.namaKegiatan.setText(listItemLaporan.getNamaKegiatan());
        holder.idJadwal.setText(listItemLaporan.getIdJadwal());
        holder.idKegiatan.setText(listItemLaporan.getIdKegiatan());
        holder.waktu.setText(listItemLaporan.getWaktu());
        holder.peserta.setText(listItemLaporan.getPeserta());
        holder.satuan.setText(listItemLaporan.getNamaSatuan());


        holder.idJadwal.setVisibility(View.GONE);
        holder.idKegiatan.setVisibility(View.GONE);

    }

    public void clearAdapter(){
        listItemLaporan.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (null != listItemLaporan ? listItemLaporan.size() : 0);
    }
}
