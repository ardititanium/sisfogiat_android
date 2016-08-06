package com.mipro.ard.penajdwalan.jadwal.RecyclerJadwal.pilihPersonil;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mipro.ard.penajdwalan.R;
import com.mipro.ard.penajdwalan.jadwal.ItemClickListener;
import com.mipro.ard.penajdwalan.jadwal.pilih_personil;

import java.util.List;

/**
 * Created by ard on 7/25/2016.
 */
public class ListRowViewHolderPilihPersonil extends RecyclerView.ViewHolder{
    protected TextView nrp_tv, nama_tv, pangkat_tv, ic_tv, id_jadwal_tv;
    protected CheckBox checkBox;
    protected RelativeLayout rLayout;
    pilih_personil pilihPersonil;
    Context context;

    ItemClickListener itemClickListener;

    public ListRowViewHolderPilihPersonil(View view, pilih_personil pilihPersonil) {
        super(view);
        this.nrp_tv = (TextView) view.findViewById(R.id.nrp_per_select);
        this.nama_tv = (TextView) view.findViewById(R.id.nama_pers_select);
        this.pangkat_tv = (TextView) view.findViewById(R.id.pangkat_pers_select);
        this.ic_tv = (TextView) view.findViewById(R.id.ic_pers_select);
        this.checkBox = (CheckBox) view.findViewById(R.id.checkBox);
        this.id_jadwal_tv = (TextView) view.findViewById(R.id.id_jadwal_pers_select);
        this.pilihPersonil = pilihPersonil;


        this.rLayout = (RelativeLayout) view.findViewById(R.id.layout_holder_pilihpersonil);
        view.setClickable(true);


    }

    public List<ListItemPilihPersonil> getPersonilTerpilih(){
        return getPersonilTerpilih();
    }

}
