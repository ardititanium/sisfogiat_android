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
import com.mipro.ard.penajdwalan.daftar.daftar_personil;
import com.mipro.ard.penajdwalan.edit.edit_personil;
import com.mipro.ard.penajdwalan.json_handler.parser;

import org.w3c.dom.Text;

public class detail_personil extends AppCompatActivity {
    TextView nrp_tv, nama_tv, pangkat_tv, satuan_tv, belum_tv, selesai_tv, total_tv, kelamin_tv, akses_tv;
    Button edit_btn, hapus_btn;
    ImageButton back_btn;
    String kelamin, pass;
    Bundle setPers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_personil);
        back_btn = (ImageButton) findViewById(R.id.kembali_btn);
        nrp_tv = (TextView) findViewById(R.id.nrp_pers);
        nama_tv  =(TextView) findViewById(R.id.nama_pers);
        pangkat_tv = (TextView) findViewById(R.id.pangkat_pers_pop);
        satuan_tv = (TextView) findViewById(R.id.satuan_pers);
        belum_tv = (TextView) findViewById(R.id.belum_pop);
        kelamin_tv = (TextView) findViewById(R.id.kelamin_pers);
        akses_tv = (TextView) findViewById(R.id.akses_pers);
        edit_btn = (Button) findViewById(R.id.edit_pers);
        hapus_btn = (Button) findViewById(R.id.hapus_pers);


        Bundle dataPers = getIntent().getExtras();
        setPers = new Bundle();

        pangkat_tv.setText(dataPers.getCharSequence("pangkat"));
        nama_tv.setText(dataPers.getCharSequence("nama"));
        satuan_tv.setText(dataPers.getCharSequence("satuan"));
        nrp_tv.setText(dataPers.getCharSequence("nrp"));
        belum_tv.setText(dataPers.getCharSequence("kelamin"));

        akses_tv.setText(dataPers.getCharSequence("akses"));

        kelamin = String.valueOf(dataPers.getCharSequence("kelamin"));
        if (kelamin.equals("L")){
            kelamin_tv.setText("Laki - Laki");
        }else {
            kelamin_tv.setText("Perempuan");
        }

        pass = String.valueOf(dataPers.getCharSequence("password"));

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(getApplicationContext(), daftar_personil.class);
                startActivity(back);
            }
        });

        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPersonil();
            }
        });


        if (parser.AKSES_SHARED_PREF.equals("user")){
            edit_btn.setVisibility(View.GONE);
            hapus_btn.setVisibility(View.GONE);
        }

    }

    private void editPersonil() {

        setPers.putString("nrp", nrp_tv.getText().toString());
        setPers.putString("nama", nama_tv.getText().toString());
        setPers.putString("pangkat", pangkat_tv.getText().toString());
        setPers.putString("satuan", satuan_tv.getText().toString());
        setPers.putString("kelamin", kelamin);
        setPers.putString("akses", akses_tv.getText().toString());
        setPers.putString("password", pass);
        Intent editInt = new Intent(getApplicationContext(), edit_personil.class);
        editInt.putExtras(setPers);
        startActivity(editInt);
    }


}
