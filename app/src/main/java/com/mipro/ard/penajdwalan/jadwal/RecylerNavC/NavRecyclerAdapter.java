package com.mipro.ard.penajdwalan.jadwal.RecylerNavC;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mipro.ard.penajdwalan.R;
import com.mipro.ard.penajdwalan.jadwal.ListJadwal;
import com.mipro.ard.penajdwalan.jadwal.detail_jadwal;

import java.util.List;

/**
 * Created by ard on 7/26/2016.
 */
public class NavRecyclerAdapter extends RecyclerView.Adapter<ListViewHolderTanggal>{
    private List<ListItemTanggal> listItemTanggals;
    private Context context;
    private int focusedItem;
    public ListJadwal setTgl;

    public NavRecyclerAdapter(Context context, List<ListItemTanggal> listItemTanggals) {
        this.listItemTanggals = listItemTanggals;
        this.context = context;
    }

    @Override
    public ListViewHolderTanggal onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_cal, parent, false);
        final ListViewHolderTanggal holder = new ListViewHolderTanggal(v);


        return holder;
    }

    @Override
    public void onBindViewHolder(final ListViewHolderTanggal holder, int position) {
        ListItemTanggal listItemTanggal = listItemTanggals.get(position);
        holder.itemView.setSelected(focusedItem == position);
        holder.getLayoutPosition();
        holder.tgl_tv.setText(listItemTanggal.getTanggal());
        holder.bulan_tv.setText(listItemTanggal.getBulan());
        final String bulan_num = listItemTanggal.getBln_num();
        holder.rLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTgl = new ListJadwal();
                String tgl = holder.tgl_tv.getText().toString();
                setTgl.setTgl(tgl, bulan_num);
            }
        });


    }

    public void clearAdapter(){
        listItemTanggals.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (null != listItemTanggals ? listItemTanggals.size() : 0);
    }
}
