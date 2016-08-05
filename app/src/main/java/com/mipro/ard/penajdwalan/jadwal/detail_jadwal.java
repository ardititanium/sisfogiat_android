package com.mipro.ard.penajdwalan.jadwal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mipro.ard.penajdwalan.R;
import com.mipro.ard.penajdwalan.edit.edit_kegiatan;
import com.mipro.ard.penajdwalan.json_handler.MyApplication;
import com.mipro.ard.penajdwalan.json_handler.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class detail_jadwal extends AppCompatActivity implements View.OnClickListener{
    TextView namKegiatan, kategori, desk, tglMulai, tglSelesai, jamMulai, jamSelesai, namLokasi, detaiLokasi, alamatLokasi, kota, bagian, satuan;
    ProgressDialog PD;
    ImageButton back_btn, edit_btn;
    String idGiat, status, idJadwal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_jadwal);
        initView();
    }

    public void initView(){
        PD = new ProgressDialog(this);
        PD.setMessage("Loading...");
        PD.setCancelable(false);


        back_btn            = (ImageButton) findViewById(R.id.back_btn_d_jadwal);
        edit_btn            = (ImageButton) findViewById(R.id.edit_btn_d_jadwal);


        namKegiatan         = (TextView) findViewById(R.id.det_nama_d_jadwal);
        kategori            = (TextView) findViewById(R.id.det_kat_d_jadwal);
        desk                = (TextView) findViewById(R.id.det_deskripsi_d_jadwal);
        tglMulai            = (TextView) findViewById(R.id.tglMulai_d_jadwal);
        tglSelesai          = (TextView) findViewById(R.id.tglSelesai_d_jadwal);
        jamMulai            = (TextView) findViewById(R.id.jamMulai_d_jadwal);
        jamSelesai          = (TextView) findViewById(R.id.jamSelesai_d_jadwal);
        namLokasi           = (TextView) findViewById(R.id.det_nmlok_jadwal);
        detaiLokasi         = (TextView) findViewById(R.id.det_dlok_jadwal);
        alamatLokasi        = (TextView) findViewById(R.id.det_al_jadwal);
        kota                = (TextView) findViewById(R.id.det_kota_jadwal);
        bagian              = (TextView) findViewById(R.id.det_bag_jadwal);
        satuan              = (TextView) findViewById(R.id.det_sat_jadwal);


        PD.show();
        Bundle getId = getIntent().getExtras();
        idJadwal = getId.getString("idJadwal");
        idGiat = getId.getString("idGiat");


        String url = "http://" + parser.IP_PUBLIC+"/ditlantas/json/jadwal/detail.php?giat="+idGiat;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        PD.dismiss();
                        try {
                            JSONObject jsonObject = response.getJSONObject("data");
                            JSONArray jsonArray = jsonObject.getJSONArray("kegiatan");
                            JSONObject dataGiat = jsonArray.getJSONObject(0);

                            String namaGiat_str     = dataGiat.getString("namaKegiatan");
                            String kategori_str     = dataGiat.getString("namaKategori");
                            String desk_str         = dataGiat.getString("deskripsi");
                            String tglMulai_str     = dataGiat.getString("tglMulai");
                            String jamMulai_str     = dataGiat.getString("jamMulai").substring(0,8);
                            String tglSelesai_str   = dataGiat.getString("tglSelesai");
                            String jamSelesai_str   = dataGiat.getString("jamSelesai").substring(0,8);
                            String namaLokasi_str   = dataGiat.getString("namaLokasi");
                            String detailLokasi_str = dataGiat.getString("detailLokasi");
                            String alamatLokasi_str = dataGiat.getString("alamatLokasi");
                            String kota_str         = dataGiat.getString("kota");
                            String bagian_str       = dataGiat.getString("bagian");
                            String satuan_str       = dataGiat.getString("namaSatuan");

                            SimpleDateFormat wrF     = new SimpleDateFormat("yyyy-MM-dd");
                            SimpleDateFormat idF     = new SimpleDateFormat("dd - MMM - yyyy");
                            String tglMulai_con      = idF.format(wrF.parse(tglMulai_str));
                            String tglSelesai_con      = idF.format(wrF.parse(tglSelesai_str));

                            namKegiatan.setText(namaGiat_str);
                            kategori.setText(kategori_str);
                            desk.setText(desk_str);
                            tglMulai.setText(tglMulai_con);
                            tglSelesai.setText(tglSelesai_con);
                            jamMulai.setText(jamMulai_str);
                            jamSelesai.setText(jamSelesai_str);
                            namLokasi.setText(namaLokasi_str);
                            detaiLokasi.setText(detailLokasi_str);
                            alamatLokasi.setText(alamatLokasi_str);
                            kota.setText(kota_str);
                            bagian.setText(bagian_str);
                            satuan.setText(satuan_str);

                            back_btn.setOnClickListener(detail_jadwal.this);
                            edit_btn.setOnClickListener(detail_jadwal.this);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            PD.dismiss();
                            Toast.makeText(getApplicationContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Gagal Mengambil Data, Persiksa Koneksi Anda", Toast.LENGTH_SHORT).show();
            }
        });

        MyApplication.getInstance().addToReqQueue(jsonObjectRequest);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_btn_d_jadwal:
                finish();
                break;
            case R.id.edit_btn_d_jadwal:
                new MaterialDialog.Builder(detail_jadwal.this)
                        .title("Pilih...")
                        .items(R.array.menu_edit_jadwal)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                               switch (which){
                                   case 0:
                                       Intent editJadwal = new Intent(detail_jadwal.this, atur_jadwal.class);
                                       startActivity(editJadwal);
                                       break;
                                   case 1:
                                       editKegiatain();
                               }
                            }
                        })
                        .show();
                break;
        }
    }

    public void editKegiatain(){
        Bundle dataGiat = new Bundle();

        dataGiat.putString("id_giat", idGiat);
        dataGiat.putString("nama_giat", namKegiatan.getText().toString());
        dataGiat.putString("lokasi_giat", namLokasi.getText().toString());
        dataGiat.putString("dl_giat", detaiLokasi.getText().toString());
        dataGiat.putString("al_giat", alamatLokasi.getText().toString());
        dataGiat.putString("kota_giat", kota.getText().toString());
        dataGiat.putString("status_giat", status);
        dataGiat.putString("bagian_giat", bagian.getText().toString());
        dataGiat.putString("desk_giat", desk.getText().toString());
        dataGiat.putString("kat_giat", kategori.getText().toString());
        dataGiat.putString("satuan_giat", satuan.getText().toString());

        Intent editKegiatan = new Intent(detail_jadwal.this, edit_kegiatan.class);
        editKegiatan.putExtras(dataGiat);
        startActivity(editKegiatan);

    }
}
