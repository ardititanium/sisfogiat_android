package com.mipro.ard.penajdwalan.RecyclerHandler.l.satlantas;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mipro.ard.penajdwalan.R;
import com.mipro.ard.penajdwalan.detail.detail_satlantas;

import java.util.List;

/**
 * Created by ard on 7/25/2016.
 */
public class SatlantasRecyclerAdapter extends RecyclerView.Adapter<ListRowViewHolderSatlantas> {
    private List<ListItemSatlantas> listItemListSatlantas;
    private Context context;
    private int focusedItem;

    public SatlantasRecyclerAdapter(Context context, List<ListItemSatlantas> listItemListSatlantas) {
        this.listItemListSatlantas = listItemListSatlantas;
        this.context = context;
    }

    @Override
    public ListRowViewHolderSatlantas onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_satlantas, parent, false);
        final ListRowViewHolderSatlantas holder = new ListRowViewHolderSatlantas(v);

        final TextView nama_sat_tv = new ListRowViewHolderSatlantas(v).nama_tv;
        final TextView alamat_sat_tv = new ListRowViewHolderSatlantas(v).alamat_tv;
        final TextView id_sat_tv = new ListRowViewHolderSatlantas(v).ic_tv;
        holder.rLyout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle datasat = new Bundle();
                String id_sat = id_sat_tv.getText().toString();
                String nama_sat = nama_sat_tv.getText().toString();
                String alamat_sat = alamat_sat_tv.getText().toString();

                Intent intent = new Intent(context, detail_satlantas.class);
                datasat.putString("idSatuan", id_sat);
                datasat.putString("namaSatuan", nama_sat);
                datasat.putString("alamat", alamat_sat);
                intent.putExtras(datasat);
                context.startActivity(intent);

            }
        });

        return holder;

    }

    @Override
    public void onBindViewHolder(final ListRowViewHolderSatlantas holder, int position) {
        ListItemSatlantas listItemSatlantas = listItemListSatlantas.get(position);
        holder.itemView.setSelected(focusedItem == position);
        holder.getLayoutPosition();

        holder.nama_tv.setText(listItemSatlantas.getNama_sat());
        holder.alamat_tv.setText(listItemSatlantas.getAlamat_sat());
        holder.ic_tv.setText(String.valueOf(position+1));
        holder.ic_tv.setText(listItemSatlantas.getIc_sat());
        int posisi = position+1;


        if (posisi % 5 == 2 ){
                holder.ic_tv.setBackgroundResource(R.drawable.rounded_square_b);
        }else if(posisi % 4 == 2){
            holder.ic_tv.setBackgroundResource(R.drawable.rounded_square_y);
        }else if(posisi % 2 == 1){
            holder.ic_tv.setBackgroundResource(R.drawable.rounded_square);
        }else {
            holder.ic_tv.setBackgroundResource(R.drawable.rounded_square_g);

        }
    }

    public void clearAdapter(){
        listItemListSatlantas.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (null != listItemListSatlantas ? listItemListSatlantas.size() : 0);
    }
}
