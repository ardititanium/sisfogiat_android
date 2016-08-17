package com.mipro.ard.penajdwalan.jadwal;

import android.app.ProgressDialog;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class detail_jadwal extends AppCompatActivity implements View.OnClickListener{
    TextView namKegiatan, kategori, desk, tglMulai, tglSelesai, jamMulai, jamSelesai, namLokasi, detaiLokasi, alamatLokasi, kota, bagian, satuan, indiator;
    ProgressDialog PD;
    ImageButton back_btn, edit_btn;
    String idGiat, status, idJadwal, namaGiatStr, tglMulai_send, tglSelesai_send, jamMulai_send, jamSelesai_send;

    ImageButton btn_pers_terlibat, btn_agenda, btn_sp, validasi_btn;
    List<String>  lisnrp = new ArrayList<>();
    String url, status_jadwal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_jadwal);
        PD = new ProgressDialog(this);
        PD.setMessage("Loading...");
        PD.setCancelable(false);
        PD.show();
        initView();

    }

    public void initView(){



        back_btn            = (ImageButton) findViewById(R.id.back_btn_d_jadwal);
        edit_btn            = (ImageButton) findViewById(R.id.edit_btn_d_jadwal);
        validasi_btn        = (ImageButton) findViewById(R.id.btn_validation);
        validasi_btn.setVisibility(View.GONE);


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
        indiator            = (TextView) findViewById(R.id.status_detail_jadwal);

        btn_pers_terlibat   = (ImageButton) findViewById(R.id.detail_pers_terlibat);
        btn_sp              = (ImageButton) findViewById(R.id.detail_surat_perintah);
        btn_agenda          = (ImageButton) findViewById(R.id.detail_agenda);


        btn_pers_terlibat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lihatPesonil();
            }
        });

        btn_sp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lihatSP();
            }
        });

        btn_agenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lihatAgenda();
            }
        });



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
                            status_jadwal           = dataGiat.getString("jstatus");


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
                            indiator.setText(status_jadwal.toUpperCase());


                            getNrp();
                            statusIndikator();

                            namaGiatStr = namKegiatan.getText().toString();
                            tglMulai_send = tglMulai.getText().toString();
                            tglSelesai_send = tglSelesai.getText().toString();
                            jamMulai_send = jamMulai.getText().toString();
                            jamSelesai_send = jamSelesai.getText().toString();

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

    private void statusIndikator() {
        if (status_jadwal.equals("draft")){
            kategori.setBackgroundResource(R.drawable.rounded_square_d);
        }else if (status_jadwal.equals("review")){
            kategori.setBackgroundResource(R.drawable.rounded_square_y);
        }else if (status_jadwal.equals("ongoing")){
            kategori.setBackgroundResource(R.drawable.rounded_square_b);
        }else if (status_jadwal.equals("done")){
            kategori.setBackgroundResource(R.drawable.rounded_square_g);
        }else if (status_jadwal.equals("batal")){
            kategori.setBackgroundResource(R.drawable.rounded_square);
        }else if (status_jadwal.equals("reject")){
            kategori.setBackgroundResource(R.drawable.rounded_square);
        }else if (status_jadwal.equals("pending")){
            kategori.setBackgroundResource(R.drawable.rounded_square);
        }
    }

    private void lihatAgenda() {

    }

    private void lihatSP() {
        Intent intent = new Intent(getApplicationContext(), lihat_str.class);
        intent.putExtra("idJadwal", idJadwal);
        startActivity(intent);
    }

    private void lihatPesonil() {
        Intent intent = new Intent(getApplicationContext(), personil_terlibat.class);
        intent.putExtra("idJadwal", idJadwal);
        startActivity(intent);
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
                                       Bundle setGiat = new Bundle();

                                       setGiat.putString("id_giat", idGiat);
                                       setGiat.putString("nama_giat", namaGiatStr);
                                       setGiat.putString("tgl_mulai", tglMulai_send);
                                       setGiat.putString("tgl_selesai", tglSelesai_send);
                                       setGiat.putString("jam_mulai", jamMulai_send);
                                       setGiat.putString("jam_selesai", jamSelesai_send);

                                       Intent editJadwal = new Intent(detail_jadwal.this, edit_jadwal.class);
                                       editJadwal.putExtras(setGiat);
                                       startActivity(editJadwal);

                                       break;
                                   case 1:
                                       editKegiatain();
                                       break;
                                   case 4:

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


    public void getNrp(){
        url = "http://"+ parser.IP_PUBLIC+ "/ditlantas/json/persterlibat/view.php?idj="+idJadwal;
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                PD.dismiss();
                try {
                    JSONObject jPesan = response.getJSONObject("data");
                    JSONArray jsonArray = jPesan.getJSONArray("personil");
                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject listPers = jsonArray.getJSONObject(i);
                        lisnrp.add(listPers.getString("nrp"));
                    }
                    validasi();
                    review();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                PD.dismiss();
                Toast.makeText(getApplicationContext(),
                        "Terjadi Kesalahan Jarigan", Toast.LENGTH_SHORT).show();


            }
        });
        MyApplication.getInstance().addToReqQueue(jsonObjReq);
    }

    public void validasi(){
        try {
            SimpleDateFormat tgl_tz_indo        = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat jam_tz_indo        = new SimpleDateFormat("HH:mm");
            SimpleDateFormat tgl_unformat       = new SimpleDateFormat("dd - MMM - yyyy");
            SimpleDateFormat jam_unformat          = new SimpleDateFormat("HH:mm:SS");
            tgl_tz_indo.setTimeZone(TimeZone.getTimeZone("Asia/Makassar"));
            jam_tz_indo.setTimeZone(TimeZone.getTimeZone("Asia/Makassar"));
            jam_unformat.setTimeZone(TimeZone.getTimeZone("Asia/Makassar"));

            Date ambil_tgl       = new Date();

            String tgl_sekarang     = tgl_tz_indo.format(ambil_tgl);
            String jam_sekarang     = jam_tz_indo.format(ambil_tgl);

            Date tgl_mulai_date     = tgl_unformat.parse(tglMulai.getText().toString());
            Date jam_mulai_date     = jam_unformat.parse(jamMulai.getText().toString());
            Date tgl_selesai_date   = tgl_unformat.parse(tglSelesai.getText().toString());
            Date jam_selesai_date   = jam_unformat.parse(jamSelesai.getText().toString());
            Date contoh_jam_date    = jam_unformat.parse("11:00:00");
            String contoh_jam       = jam_tz_indo.format(contoh_jam_date);


            String tgl_mulai_string     = tgl_tz_indo.format(tgl_mulai_date);
            String jam_mulai_string     = jam_tz_indo.format(jam_mulai_date);
            String tgl_selesai_string   = tgl_tz_indo.format(tgl_selesai_date);
            String jam_selesai_string   = jam_tz_indo.format(jam_selesai_date);


////            Log.d("LihatTanggal     ", "Tanggal Sekarang    = "+tgl_sekarang);
//            Log.d("LihatJam         ", "Jam Sekarang        = "+contoh_jam);
////            Log.d("LihatTglMulai    ", "Tanggal Mulai    = "+tgl_mulai_string);
//            Log.d("LihatJamMulai    ", "Jam Mulai        = "+jam_mulai_string);
////            Log.d("LihatTglSelesai    ", "Tanggal Selesai    = "+tgl_mulai_string);
//            Log.d("LihatJamSelesai    ", "Jam Selesai        = "+jam_selesai_string);


            String banding_tgl_mulai    = String.valueOf(tgl_sekarang.compareTo(tgl_mulai_string));
            String banding_tgl_selesai  = String.valueOf(tgl_selesai_string.compareTo(tgl_sekarang));
            String banding_jam_mulai    = String.valueOf(jam_sekarang.compareTo(jam_mulai_string));
            String banding_jam_selsai   = String.valueOf(jam_sekarang.compareTo(jam_selesai_string));



            if (Integer.parseInt(banding_tgl_mulai) == 0
                    && Integer.parseInt(banding_tgl_selesai) >= 0
                    && Integer.parseInt(banding_jam_mulai) >= 0
                    && Integer.parseInt(banding_jam_selsai) <= 0
                    && status_jadwal.equals("draft")
                    )
            {
                validasi_btn.setVisibility(View.VISIBLE);
                validasi_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent validasi = new Intent(getApplicationContext(), upload_validtor.class);
                        validasi.putExtra("idJadwal", idJadwal);
                        validasi.putExtra("idGiat", idGiat);
                        startActivity(validasi);
                        finish();
                    }
                });
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void review(){
        if (status_jadwal.equals("review") && parser.AKSES_SHARED_PREF.equals("admin")){
            validasi_btn.setImageResource(R.drawable.eye);
            validasi_btn.setVisibility(View.VISIBLE);
            validasi_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent review = new Intent(getApplicationContext(), review_giat.class);
                    review.putExtra("idJadwal", idJadwal);
                    review.putExtra("idGiat", idGiat);
                    startActivity(review);
                    finish();
                }
            });
        }
    }
}
