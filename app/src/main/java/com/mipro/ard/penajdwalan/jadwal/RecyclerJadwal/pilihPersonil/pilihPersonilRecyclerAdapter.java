package com.mipro.ard.penajdwalan.jadwal.RecyclerJadwal.pilihPersonil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.mipro.ard.penajdwalan.R;
import com.mipro.ard.penajdwalan.detail.detail_personil;
import com.mipro.ard.penajdwalan.jadwal.ItemClickListener;
import com.mipro.ard.penajdwalan.jadwal.pilih_personil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ard on 7/25/2016.
 */
public class pilihPersonilRecyclerAdapter extends RecyclerView.Adapter<ListRowViewHolderPilihPersonil> {
    private List<ListItemPilihPersonil> listItemPilihPersonilList;
    public  List<ListItemPilihPersonil> personilTerpilih = new ArrayList<ListItemPilihPersonil>();
    private Context context;
    private int focusedItem;
    pilih_personil pilihPersonil;


    public pilihPersonilRecyclerAdapter(Context context, List<ListItemPilihPersonil> listItemPilihPersonilList) {
        this.listItemPilihPersonilList = listItemPilihPersonilList;
        this.context = context;
        pilihPersonil = (pilih_personil) context;
    }

    @Override
    public ListRowViewHolderPilihPersonil onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_pilihpersonil, parent, false);
        final ListRowViewHolderPilihPersonil holder = new ListRowViewHolderPilihPersonil(v, pilihPersonil);

        final TextView nrp_pers_tv = new ListRowViewHolderPilihPersonil(v, pilihPersonil).nrp_tv;
        final TextView nama_pers_tv = new ListRowViewHolderPilihPersonil(v, pilihPersonil).nama_tv;
        final TextView pangkat_pers_tv = new ListRowViewHolderPilihPersonil(v, pilihPersonil).pangkat_tv;
        final TextView ic_pers_tv = new ListRowViewHolderPilihPersonil(v, pilihPersonil).ic_tv;
        final TextView id_jadwal = new ListRowViewHolderPilihPersonil(v, pilihPersonil).id_jadwal_tv;




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

        holder.rLayout.setOnLongClickListener(pilihPersonil);

        return holder;
    }

    @Override
    public void onBindViewHolder(ListRowViewHolderPilihPersonil holder, final int position) {
        ListItemPilihPersonil listItemPilihPersonil = listItemPilihPersonilList.get(position);
        holder.itemView.setSelected(focusedItem == position);
        holder.getLayoutPosition();
        holder.nrp_tv.setText(listItemPilihPersonil.getNrp());
        holder.nama_tv.setText(listItemPilihPersonil.getNama());
        holder.pangkat_tv.setText(listItemPilihPersonil.getPangkat());
        holder.id_jadwal_tv.setText(listItemPilihPersonil.getIdjadwal());
        holder.ic_tv.setText(String.valueOf(position+1));

        if(!pilihPersonil.isActionMode){
            holder.checkBox.setVisibility(View.GONE);
        }else {
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.checkBox.setChecked(false);
        }

        holder.checkBox.setChecked(listItemPilihPersonilList.get(position).isSelected());
        holder.checkBox.setTag(listItemPilihPersonilList.get(position));


        final int pos = position;
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                ListItemPilihPersonil personil = (ListItemPilihPersonil) cb.getTag();
                personil.setSelected(cb.isChecked());
                listItemPilihPersonilList.get(pos).setSelected(cb.isChecked());

                pilihPersonil.preparedSelection(v, pos);


            }
        });







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
