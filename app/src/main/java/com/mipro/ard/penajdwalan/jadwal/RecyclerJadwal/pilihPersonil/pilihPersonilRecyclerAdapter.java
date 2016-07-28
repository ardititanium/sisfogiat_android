package com.mipro.ard.penajdwalan.jadwal.RecyclerJadwal.pilihPersonil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mipro.ard.penajdwalan.R;
import com.mipro.ard.penajdwalan.detail.detail_personil;

import java.util.List;

/**
 * Created by ard on 7/25/2016.
 */
public class pilihPersonilRecyclerAdapter extends RecyclerView.Adapter<ListRowViewHolderPilihPersonil> {
    private List<ListItemPilihPersonil> listItemPilihPersonilList;
    private Context context;
    private int focusedItem;

    public pilihPersonilRecyclerAdapter(Context context, List<ListItemPilihPersonil> listItemPilihPersonilList) {
        this.listItemPilihPersonilList = listItemPilihPersonilList;
        this.context = context;
    }

    @Override
    public ListRowViewHolderPilihPersonil onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_pilihpersonil, parent, false);
        final ListRowViewHolderPilihPersonil holder = new ListRowViewHolderPilihPersonil(v);

        final TextView nrp_pers_tv = new ListRowViewHolderPilihPersonil(v).nrp_tv;
        final TextView nama_pers_tv = new ListRowViewHolderPilihPersonil(v).nama_tv;
        final TextView pangkat_pers_tv = new ListRowViewHolderPilihPersonil(v).pangkat_tv;
        final TextView ic_pers_tv = new ListRowViewHolderPilihPersonil(v).ic_tv;




        holder.rLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle dataPers = new Bundle();

                String nrp = nrp_pers_tv.getText().toString();
                String nama = nama_pers_tv.getText().toString();
                String pangkat = pangkat_pers_tv.getText().toString();
                String ic = ic_pers_tv.getText().toString();

                dataPers.putString("nrp", nrp);
                dataPers.putString("nama", nama);
                dataPers.putString("pangkat", pangkat);
                dataPers.putString("ic", ic);


                Intent intent = new Intent(context, detail_personil.class);
                intent.putExtras(dataPers);
                context.startActivity(intent);


            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ListRowViewHolderPilihPersonil holder, int position) {
        ListItemPilihPersonil listItemPilihPersonil = listItemPilihPersonilList.get(position);
        holder.itemView.setSelected(focusedItem == position);
        holder.getLayoutPosition();
        holder.nrp_tv.setText(listItemPilihPersonil.getNrp());
        holder.nama_tv.setText(listItemPilihPersonil.getNama());
        holder.pangkat_tv.setText(listItemPilihPersonil.getPangkat());
        holder.ic_tv.setText(String.valueOf(position+1));


    }

    public void clearAdapter(){
        listItemPilihPersonilList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (null != listItemPilihPersonilList ? listItemPilihPersonilList.size() : 0);
    }

}
