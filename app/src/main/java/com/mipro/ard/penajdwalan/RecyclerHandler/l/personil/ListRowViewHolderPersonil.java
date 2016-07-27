package com.mipro.ard.penajdwalan.RecyclerHandler.l.personil;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mipro.ard.penajdwalan.R;

/**
 * Created by ard on 7/25/2016.
 */
public class ListRowViewHolderPersonil extends RecyclerView.ViewHolder {
    protected TextView nrp_tv, nama_tv, kelamin_tv, pangkat_tv, password_tv, hakAkses, satuan_tv;
    protected RelativeLayout rLayout;

    public ListRowViewHolderPersonil(View view) {
        super(view);
        this.nrp_tv = (TextView) view.findViewById(R.id.item_nrp_pers);
        this.nama_tv = (TextView) view.findViewById(R.id.nama_pers);
        this.pangkat_tv = (TextView) view.findViewById(R.id.item_pangkat_pers);
        this.satuan_tv = (TextView) view.findViewById(R.id.item_satuan_pers);
        this.kelamin_tv = (TextView) view.findViewById(R.id.hide_kelamin);
        this.password_tv = (TextView) view.findViewById(R.id.hide_pass);
        this.hakAkses = (TextView) view.findViewById(R.id.hide_akses);

        this.kelamin_tv.setVisibility(View.GONE);
        this.password_tv.setVisibility(View.GONE);
        this.hakAkses.setVisibility(View.GONE);


        this.rLayout = (RelativeLayout) view.findViewById(R.id.layout_holder_personil);
        view.setClickable(true);


    }
}
