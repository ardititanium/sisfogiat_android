package com.mipro.ard.penajdwalan.detail;

import android.content.Intent;
import android.opengl.Visibility;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mipro.ard.penajdwalan.R;
import com.mipro.ard.penajdwalan.daftar.daftar_kegiatan;
import com.mipro.ard.penajdwalan.jadwal.atur_jadwal;

import org.apache.commons.lang3.text.WordUtils;

public class detail_kegiatan extends AppCompatActivity {
    TextView idKegiatan_tv, namaKegiatan_tv, namaLokasi_tv, detailLokasi_tv, alamatLokasi_tv, status_tv, bagian_tv, deskripsi_tv, namaKategori_tv, namaSatuan_tv, kota_tv;
    Bundle dataGiat;
    ImageButton btn_back;
    Button btn_atur_jadwal;
    String id_giat, nama_giat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kegiatan);

        btn_back = (ImageButton) findViewById(R.id.kembali_btn);
        namaKegiatan_tv = (TextView) findViewById(R.id.det_nama_giat);
        namaKategori_tv = (TextView) findViewById(R.id.det_kat_giat);
        deskripsi_tv = (TextView) findViewById(R.id.det_desk_giat);
        namaLokasi_tv = (TextView) findViewById(R.id.det_nmlok_giat);
        detailLokasi_tv = (TextView) findViewById(R.id.det_dlok_giat);
        alamatLokasi_tv = (TextView) findViewById(R.id.det_al_giat);
        status_tv = (TextView) findViewById(R.id.det_status_giat);
        bagian_tv = (TextView) findViewById(R.id.det_bag_giat);
        namaSatuan_tv = (TextView) findViewById(R.id.det_sat_giat);
        kota_tv = (TextView) findViewById(R.id.det_kota_giat);
        btn_atur_jadwal = (Button) findViewById(R.id.det_btn_atur);


        dataGiat = getIntent().getExtras();


        id_giat = ambilIntent("id_giat");
        nama_giat = ambilIntent("nama_giat");


        namaKegiatan_tv.setText(ambilIntent("nama_giat"));
        namaKategori_tv.setText(ambilIntent("kat_giat"));
        deskripsi_tv.setText(ambilIntent("dek_giat"));
        namaLokasi_tv.setText(ambilIntent("lokasi_giat"));
        detailLokasi_tv.setText(ambilIntent("dl_giat"));
        alamatLokasi_tv.setText(ambilIntent("al_giat"));
        kota_tv.setText(ambilIntent("kota_giat"));
        bagian_tv.setText(ambilIntent("bagian_giat"));
        namaSatuan_tv.setText(ambilIntent("satuan_giat"));



        String status_in = String.valueOf(dataGiat.getCharSequence("status_giat"));


        if (status_in.equals("Selesai")) {
            status_tv.setBackgroundResource(R.drawable.rounded_square);
            btn_atur_jadwal.setVisibility(View.GONE);
        }else {
            status_tv.setBackgroundResource(R.drawable.rounded_square_g);
            btn_atur_jadwal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle setGiat = new Bundle();
                    setGiat.putString("id_giat", id_giat);
                    setGiat.putString("nama_giat", nama_giat);

                    Intent aturGiat = new Intent(getApplicationContext(), atur_jadwal.class);
                    aturGiat.putExtras(setGiat);
                    startActivity(aturGiat);

                }
            });
        }

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent backInt = new Intent(getApplicationContext(), daftar_kegiatan.class);
                startActivity(backInt);
            }
        });




    }

    String ambilIntent(String giat){
        giat = String.valueOf(dataGiat.getCharSequence(giat));
        String convert = WordUtils.capitalize(giat);
        return convert;
    }
}
