package com.mipro.ard.penajdwalan.jadwal.RecylerPersTerlibat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.mipro.ard.penajdwalan.R;
import com.mipro.ard.penajdwalan.detail.detail_personil;
import com.mipro.ard.penajdwalan.jadwal.personil_terlibat;
import com.mipro.ard.penajdwalan.jadwal.pilih_personil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ard on 7/25/2016.
 */
public class PersonilTerlibatRecyclerAdapter extends RecyclerView.Adapter<ListRowViewHolderPersonilTerlibat> {
    private List<ListItemPersonilTerlibat> listItemPilihPersonilListTerlibat;
    public  List<ListItemPersonilTerlibat> personilTerpilih = new ArrayList<ListItemPersonilTerlibat>();
    private Context context;
    private int focusedItem;
    personil_terlibat personilTerlibat;


    public PersonilTerlibatRecyclerAdapter(Context context, List<ListItemPersonilTerlibat> listItemPilihPersonilListTerlibat) {
        this.listItemPilihPersonilListTerlibat = listItemPilihPersonilListTerlibat;
        this.context = context;
        personilTerlibat = (personil_terlibat) context;
    }

    @Override
    public ListRowViewHolderPersonilTerlibat onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_pilihpersonil, parent, false);
        final ListRowViewHolderPersonilTerlibat holder = new ListRowViewHolderPersonilTerlibat(v, personilTerlibat);

        final TextView nrp_pers_tv = new ListRowViewHolderPersonilTerlibat(v, personilTerlibat).nrp_tv;
        final TextView nama_pers_tv = new ListRowViewHolderPersonilTerlibat(v, personilTerlibat).nama_tv;
        final TextView pangkat_pers_tv = new ListRowViewHolderPersonilTerlibat(v, personilTerlibat).pangkat_tv;
        final TextView ic_pers_tv = new ListRowViewHolderPersonilTerlibat(v, personilTerlibat).ic_tv;
        final TextView id_jadwal = new ListRowViewHolderPersonilTerlibat(v, personilTerlibat).id_jadwal_tv;




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

        holder.rLayout.setOnLongClickListener(personilTerlibat);

        return holder;
    }

    @Override
    public void onBindViewHolder(ListRowViewHolderPersonilTerlibat holder, final int position) {
        ListItemPersonilTerlibat listItemPersonilTerlibat = listItemPilihPersonilListTerlibat.get(position);
        holder.itemView.setSelected(focusedItem == position);
        holder.getLayoutPosition();
        holder.nrp_tv.setText(listItemPersonilTerlibat.getNrp());
        holder.nama_tv.setText(listItemPersonilTerlibat.getNama());
        holder.pangkat_tv.setText(listItemPersonilTerlibat.getPangkat());
        holder.id_jadwal_tv.setText(listItemPersonilTerlibat.getIdjadwal());
        holder.ic_tv.setText(String.valueOf(position+1));

        if(!personilTerlibat.isActionMode){
            holder.checkBox.setVisibility(View.GONE);
        }else {
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.checkBox.setChecked(false);
        }

        holder.checkBox.setChecked(listItemPilihPersonilListTerlibat.get(position).isSelected());
        holder.checkBox.setTag(listItemPilihPersonilListTerlibat.get(position));


        final int pos = position;
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                ListItemPersonilTerlibat personil = (ListItemPersonilTerlibat) cb.getTag();
                personil.setSelected(cb.isChecked());
                listItemPilihPersonilListTerlibat.get(pos).setSelected(cb.isChecked());

                personilTerlibat.preparedSelection(v, pos);


            }
        });







    }

    public void clearAdapter(){
        listItemPilihPersonilListTerlibat.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (null != listItemPilihPersonilListTerlibat ? listItemPilihPersonilListTerlibat.size() : 0);
    }




}
