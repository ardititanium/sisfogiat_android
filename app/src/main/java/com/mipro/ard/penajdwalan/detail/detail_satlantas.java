package com.mipro.ard.penajdwalan.detail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.mipro.ard.penajdwalan.R;

public class detail_satlantas extends AppCompatActivity {
    TextView det_nama_tv, det_alamat_tv, det_id_sat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_satlantas);
        det_id_sat = (TextView) findViewById(R.id.id_sat_det);
        det_nama_tv = (TextView) findViewById(R.id.nama_sat_det);
        det_alamat_tv = (TextView) findViewById(R.id.alamat_sat_det);

        Bundle dataSat = getIntent().getExtras();

        DisplayMetrics dm_satlantas = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm_satlantas);

        int width = dm_satlantas.widthPixels;
        int heigh = dm_satlantas.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(heigh*.6));



        det_id_sat.setText(dataSat.getCharSequence("idSatuan"));
        det_nama_tv.setText(dataSat.getCharSequence("namaSatuan"));
        det_alamat_tv.setText(dataSat.getCharSequence("alamat"));
    }
}
