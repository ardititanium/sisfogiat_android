package com.mipro.ard.penajdwalan.RecyclerHandler.l.kegiatan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mipro.ard.penajdwalan.R;
import com.mipro.ard.penajdwalan.detail.detail_kegiatan;
import com.mipro.ard.penajdwalan.detail.detail_personil;

import java.util.List;
import org.apache.commons.lang3.text.WordUtils;

/**
 * Created by ard on 7/26/2016.
 */
public class KegiatanRecyclerAdapter extends RecyclerView.Adapter<ListRowViewHolderKegiatan>{
    private List<ListItemKegiatan> listItemListKegiatan;
    private Context context;
    private int focusedItem;

    public KegiatanRecyclerAdapter(Context context, List<ListItemKegiatan> listItemListKegiatan) {
        this.listItemListKegiatan = listItemListKegiatan;
        this.context = context;
    }

    @Override
    public ListRowViewHolderKegiatan onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_kegiatan, parent, false);
        final ListRowViewHolderKegiatan holder = new ListRowViewHolderKegiatan(v);


        final TextView id_giat_tv = new ListRowViewHolderKegiatan(v).id_tv;
        final TextView nama_giat_tv = new ListRowViewHolderKegiatan(v).nama_giat_tv;
        final TextView lokasi_giat_tv = new ListRowViewHolderKegiatan(v).lokasi_giat_tv;
        final TextView dl_giat_tv = new ListRowViewHolderKegiatan(v).dLokasi_tv;
        final TextView al_giat_tv = new ListRowViewHolderKegiatan(v).aLokasi_tv;
        final TextView kota_giat_tv = new ListRowViewHolderKegiatan(v).kota_tv;
        final TextView status_giat_tv = new ListRowViewHolderKegiatan(v).status_tv;
        final TextView bagian_giat_tv = new ListRowViewHolderKegiatan(v).bagian_tv;
        final TextView desk_giat_tv = new ListRowViewHolderKegiatan(v).desk_tv;
        final TextView kat_giat_tv = new ListRowViewHolderKegiatan(v).kategori_giat_tv;
        final TextView satuan_giat_tv = new ListRowViewHolderKegiatan(v).satuan_tv;

        holder.rLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle dataGiat = new Bundle();

                String id_giat          = id_giat_tv.getText().toString();
                String nama_giat        = nama_giat_tv.getText().toString();
                String lokasi_giat      = lokasi_giat_tv.getText().toString();
                String dl_giat          = dl_giat_tv.getText().toString();
                String al_giat          = al_giat_tv.getText().toString();
                String kota_giat        = kota_giat_tv.getText().toString();
                String status_giat      = status_giat_tv.getText().toString();
                String bagian_giat      = bagian_giat_tv.getText().toString();
                String desk_giat        = desk_giat_tv.getText().toString();
                String kat_giat         = kat_giat_tv.getText().toString();
                String satuan_giat      = satuan_giat_tv.getText().toString();

                dataGiat.putString("id_giat", id_giat);
                dataGiat.putString("nama_giat", nama_giat);
                dataGiat.putString("lokasi_giat", lokasi_giat);
                dataGiat.putString("dl_giat", dl_giat);
                dataGiat.putString("al_giat", al_giat);
                dataGiat.putString("kota_giat", kota_giat);
                dataGiat.putString("status_giat", status_giat);
                dataGiat.putString("bagian_giat", bagian_giat);
                dataGiat.putString("desk_giat", desk_giat);
                dataGiat.putString("kat_giat", kat_giat);
                dataGiat.putString("satuan_giat", satuan_giat);

                Intent intent = new Intent(context, detail_kegiatan.class);
                intent.putExtras(dataGiat);
                context.startActivity(intent);


            }
        });







        return holder;
    }

    @Override
    public void onBindViewHolder(ListRowViewHolderKegiatan holder, int position) {
        ListItemKegiatan listItemKegiatan = listItemListKegiatan.get(position);
        holder.itemView.setSelected(focusedItem == position);
        holder.getLayoutPosition();

        String ic_letter = listItemKegiatan.getNamaKegiatan();
        char icon_char = ic_letter.charAt(0);
        String icon_s = String.valueOf(icon_char).toUpperCase();

        holder.ic_giat_tv.setText(icon_s);
            holder.id_tv.setText(listItemKegiatan.getIdKegiatan());
        holder.nama_giat_tv.setText(listItemKegiatan.getNamaKegiatan());
        holder.kategori_giat_tv.setText(listItemKegiatan.getNamaKategori());
        holder.lokasi_giat_tv.setText(listItemKegiatan.getNamaLokasi());
        holder.status_tv.setText(listItemKegiatan.getStatus());
        holder.desk_tv.setText(listItemKegiatan.getDeskripsi());
        holder.dLokasi_tv.setText(listItemKegiatan.getDetailLokasi());
        holder.aLokasi_tv.setText(listItemKegiatan.getAlamatLokasi());
        holder.kota_tv.setText(listItemKegiatan.getKota());
        holder.bagian_tv.setText(listItemKegiatan.getBagian());
        holder.satuan_tv.setText(listItemKegiatan.getNamaSatuan());






        String status_in = holder.status_tv.getText().toString();
        if(status_in.equals("Belum")){
            holder.status_ic.setBackgroundResource(R.drawable.rounded_square_g);
        }else {
            holder.status_ic.setBackgroundResource(R.drawable.rounded_square);
        }
    }

    public void clearAdapter(){
        listItemListKegiatan.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (null != listItemListKegiatan ? listItemListKegiatan.size() : 0);
    }
}
