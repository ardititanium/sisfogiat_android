package com.mipro.ard.penajdwalan.RecyclerHandler.l.personil;

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
public class PersonilRecyclerAdapter extends RecyclerView.Adapter<ListRowViewHolderPersonil> {
    private List<ListItemPersonil> listItemPersonilList;
    private Context context;
    private int focusedItem;

    public PersonilRecyclerAdapter(Context context, List<ListItemPersonil> listItemPersonilList) {
        this.listItemPersonilList = listItemPersonilList;
        this.context = context;
    }

    @Override
    public ListRowViewHolderPersonil onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_personil, parent, false);
        final ListRowViewHolderPersonil holder = new ListRowViewHolderPersonil(v);

        final TextView nrp_pers_tv = new ListRowViewHolderPersonil(v).nrp_tv;
        final TextView nama_pers_tv = new ListRowViewHolderPersonil(v).nama_tv;
        final TextView pangkat_pers_tv = new ListRowViewHolderPersonil(v).pangkat_tv;
        final TextView satuan_pers_tv = new ListRowViewHolderPersonil(v).satuan_tv;
        final TextView kelamin_pers_tv = new ListRowViewHolderPersonil(v).kelamin_tv;
        final TextView hakAkses_pers_tv = new ListRowViewHolderPersonil(v).hakAkses;
        final TextView password_pers_tv = new ListRowViewHolderPersonil(v).password_tv;



        holder.rLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle dataPers = new Bundle();

                String nrp = nrp_pers_tv.getText().toString();
                String nama = nama_pers_tv.getText().toString();
                String pangkat = pangkat_pers_tv.getText().toString();
                String satuan = satuan_pers_tv.getText().toString();
                String kelamin =  kelamin_pers_tv.getText().toString();
                String password = password_pers_tv.getText().toString();
                String akses = hakAkses_pers_tv.getText().toString();

                dataPers.putString("nrp", nrp);
                dataPers.putString("nama", nama);
                dataPers.putString("pangkat", pangkat);
                dataPers.putString("satuan", satuan);
                dataPers.putString("kelamin", kelamin);
                dataPers.putString("password", password);
                dataPers.putString("akses", akses);

                Intent intent = new Intent(context, detail_personil.class);
                intent.putExtras(dataPers);
                context.startActivity(intent);


            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ListRowViewHolderPersonil holder, int position) {
        ListItemPersonil listItemPersonil = listItemPersonilList.get(position);
        holder.itemView.setSelected(focusedItem == position);
        holder.getLayoutPosition();
        holder.nrp_tv.setText(listItemPersonil.getNrp());
        holder.nama_tv.setText(listItemPersonil.getNama());
        holder.pangkat_tv.setText(listItemPersonil.getPangkat());
        holder.satuan_tv.setText(listItemPersonil.getSatuan());
        holder.kelamin_tv.setText(listItemPersonil.getKelamin());
        holder.hakAkses.setText(listItemPersonil.getHakAkses());
        holder.password_tv.setText(listItemPersonil.getPassword());

    }

    public void clearAdapter(){
        listItemPersonilList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (null != listItemPersonilList ? listItemPersonilList.size() : 0);
    }

}
