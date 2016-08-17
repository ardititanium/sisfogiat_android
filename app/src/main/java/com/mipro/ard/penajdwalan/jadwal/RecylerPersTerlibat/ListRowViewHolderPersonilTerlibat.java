package com.mipro.ard.penajdwalan.jadwal.RecylerPersTerlibat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mipro.ard.penajdwalan.R;
import com.mipro.ard.penajdwalan.jadwal.ItemClickListener;
import com.mipro.ard.penajdwalan.jadwal.personil_terlibat;

import java.util.List;

/**
 * Created by ard on 7/25/2016.
 */
public class ListRowViewHolderPersonilTerlibat extends RecyclerView.ViewHolder{
    protected TextView nrp_tv, nama_tv, pangkat_tv, ic_tv, id_jadwal_tv;
    protected CheckBox checkBox;
    protected RelativeLayout rLayout;
    personil_terlibat personilTerlibat;
    Context context;

    ItemClickListener itemClickListener;

    public ListRowViewHolderPersonilTerlibat(View view, personil_terlibat personilTerlibat) {
        super(view);
        this.nrp_tv = (TextView) view.findViewById(R.id.nrp_per_select);
        this.nama_tv = (TextView) view.findViewById(R.id.nama_pers_select);
        this.pangkat_tv = (TextView) view.findViewById(R.id.pangkat_pers_select);
        this.ic_tv = (TextView) view.findViewById(R.id.ic_pers_select);
        this.checkBox = (CheckBox) view.findViewById(R.id.checkBox);
        this.id_jadwal_tv = (TextView) view.findViewById(R.id.id_jadwal_pers_select);
        this.personilTerlibat = personilTerlibat;


        this.rLayout = (RelativeLayout) view.findViewById(R.id.layout_holder_pilihpersonil);
        view.setClickable(true);


    }

    public List<ListItemPersonilTerlibat> getPersonilTerpilih(){
        return getPersonilTerpilih();
    }

}
