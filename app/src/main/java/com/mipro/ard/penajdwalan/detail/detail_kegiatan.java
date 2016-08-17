package com.mipro.ard.penajdwalan.detail;

import android.content.Intent;
import android.opengl.Visibility;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mipro.ard.penajdwalan.R;
import com.mipro.ard.penajdwalan.daftar.daftar_kegiatan;
import com.mipro.ard.penajdwalan.edit.edit_kegiatan;
import com.mipro.ard.penajdwalan.jadwal.atur_jadwal;
import com.mipro.ard.penajdwalan.jadwal.detail_jadwal;
import com.mipro.ard.penajdwalan.json_handler.parser;

import org.apache.commons.lang3.text.WordUtils;

public class detail_kegiatan extends AppCompatActivity{
    TextView idKegiatan_tv, namaKegiatan_tv, namaLokasi_tv, detailLokasi_tv, alamatLokasi_tv, status_tv, bagian_tv, deskripsi_tv, namaKategori_tv, namaSatuan_tv, kota_tv;
    Bundle dataGiat;
    ImageButton btn_back;
    Button btn_atur_jadwal, btn_lihat_jadwal, hapus_btn, edit_btn;
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
        btn_lihat_jadwal = (Button) findViewById(R.id.det_btn_lihat);
        hapus_btn = (Button) findViewById(R.id.hapus_pers);
        edit_btn = (Button) findViewById(R.id.edit_pers);


        dataGiat = getIntent().getExtras();


        id_giat = ambilIntent("id_giat");
        nama_giat = ambilIntent("nama_giat");






        namaKegiatan_tv.setText(ambilIntent("nama_giat"));
        namaKategori_tv.setText(ambilIntent("kat_giat"));
        deskripsi_tv.setText(ambilIntent("desk_giat"));
        namaLokasi_tv.setText(ambilIntent("lokasi_giat"));
        detailLokasi_tv.setText(ambilIntent("dl_giat"));
        alamatLokasi_tv.setText(ambilIntent("al_giat"));
        kota_tv.setText(ambilIntent("kota_giat"));
        bagian_tv.setText(ambilIntent("bagian_giat"));
        namaSatuan_tv.setText(ambilIntent("satuan_giat"));



        String status_in = String.valueOf(dataGiat.getCharSequence("status_giat"));


        if (status_in.equals("Selesai")) {
            status_tv.setBackgroundResource(R.drawable.rounded_square);
            hapus_btn.setVisibility(View.GONE);
            btn_atur_jadwal.setVisibility(View.GONE);
            btn_lihat_jadwal.setVisibility(View.VISIBLE);
            btn_lihat_jadwal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent lihatJadwal = new Intent(detail_kegiatan.this, detail_jadwal.class);
                    lihatJadwal.putExtra("idGiat", id_giat);
                    startActivity(lihatJadwal);

                }
            });
        }else {
            btn_lihat_jadwal.setVisibility(View.GONE);
            btn_atur_jadwal.setVisibility(View.VISIBLE);
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


        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle dataGiat = new Bundle();

                dataGiat.putString("id_giat", id_giat);
                dataGiat.putString("nama_giat", namaKegiatan_tv.getText().toString());
                dataGiat.putString("lokasi_giat", namaLokasi_tv.getText().toString());
                dataGiat.putString("dl_giat", detailLokasi_tv.getText().toString());
                dataGiat.putString("al_giat", alamatLokasi_tv.getText().toString());
                dataGiat.putString("kota_giat", kota_tv.getText().toString());
                dataGiat.putString("status_giat", status_tv.getText().toString());
                dataGiat.putString("bagian_giat", bagian_tv.getText().toString());
                dataGiat.putString("desk_giat", deskripsi_tv.getText().toString());
                dataGiat.putString("kat_giat", namaKategori_tv.getText().toString());
                dataGiat.putString("satuan_giat", namaSatuan_tv.getText().toString());

                Intent editInt = new Intent(getApplicationContext(), edit_kegiatan.class);
                editInt.putExtras(dataGiat);
                startActivity(editInt);
            }
        });

        hapus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




        if(parser.AKSES_SHARED_PREF.equals("user")){
            hapus_btn.setVisibility(View.GONE);
            edit_btn.setVisibility(View.GONE);
            btn_atur_jadwal.setVisibility(View.GONE);
        }





    }

    String ambilIntent(String giat){
        giat = String.valueOf(dataGiat.getCharSequence(giat));
        String convert = WordUtils.capitalize(giat);
        return convert;
    }


}
