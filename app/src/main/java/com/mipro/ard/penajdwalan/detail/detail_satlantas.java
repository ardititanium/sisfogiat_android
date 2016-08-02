package com.mipro.ard.penajdwalan.detail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mipro.ard.penajdwalan.R;
import com.mipro.ard.penajdwalan.edit.edit_satlantas;
import com.mipro.ard.penajdwalan.json_handler.parser;

import org.apache.commons.lang3.text.WordUtils;

public class detail_satlantas extends AppCompatActivity {
    TextView det_nama_tv, det_alamat_tv, det_id_sat;
    Button edit_btn, hapus_btn;
    Bundle setSat;
    ImageButton kembali_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_satlantas);
        det_id_sat = (TextView) findViewById(R.id.id_sat_det);
        det_nama_tv = (TextView) findViewById(R.id.nama_sat_det);
        det_alamat_tv = (TextView) findViewById(R.id.alamat_sat_det);
        edit_btn = (Button) findViewById(R.id.edit_sat);
        hapus_btn = (Button) findViewById(R.id.hapus_sat);
        kembali_btn = (ImageButton) findViewById(R.id.kembali_btn);

        Bundle dataSat = getIntent().getExtras();
        setSat = new Bundle();

        det_id_sat.setText(dataSat.getCharSequence("idSatuan"));
        det_nama_tv.setText(dataSat.getCharSequence("namaSatuan"));
        det_alamat_tv.setText(dataSat.getCharSequence("alamat"));

        final String detNama = WordUtils.capitalize(det_nama_tv.getText().toString());
        final String detAlamat = WordUtils.capitalizeFully(det_alamat_tv.getText().toString());


        if (parser.AKSES_SHARED_PREF.equals("user")){
            edit_btn.setVisibility(View.GONE);
            hapus_btn.setVisibility(View.GONE);
        }




        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSat.putString("idSatuan", det_id_sat.getText().toString());
                setSat.putString("namaSatuan", detNama);
                setSat.putString("alamat", detAlamat);

                Intent editInt = new Intent(getApplicationContext(), edit_satlantas.class);
                editInt.putExtras(setSat);
                startActivity(editInt);


            }
        });

        kembali_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
