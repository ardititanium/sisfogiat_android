package com.mipro.ard.penajdwalan.tambah;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mipro.ard.penajdwalan.MainActivity;
import com.mipro.ard.penajdwalan.R;
import com.mipro.ard.penajdwalan.json_handler.MyApplication;
import com.mipro.ard.penajdwalan.json_handler.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class tambah_kegiatan extends AppCompatActivity {
    String url = "http://" + parser.IP_PUBLIC +"/ditlantas/json/kegiatan/insert.php";
    String mId, mNama, mKategori, mLokasi, mDLokasi, mALokasi, mKota, mStatus, mPelaksana, mBagian, mDesk;
    EditText et_id, et_nama, et_lokasi, et_DLokasi, et_ALokasi, et_kota, et_desk;
    Spinner spin_kat, spin_status, spin_pelaksana, spin_bagian;
    ArrayAdapter status_adapter, bagian_adapter;

    private ArrayList<String> namaSatuan, namaKategori;

    TextView title_bar;
    ImageButton m_done_btn, m_back_btn;
    ProgressDialog PD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_kegiatan);

        namaSatuan = new ArrayList<String>();
        namaKategori = new ArrayList<String>();

        title_bar = (TextView) findViewById(R.id.bar_title);
        m_back_btn = (ImageButton) findViewById(R.id.kembali_btn);
        m_done_btn = (ImageButton) findViewById(R.id.search_btn);

        title_bar.setText("TAMBAH KEGIATAN");

        m_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back_home = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(back_home);
            }
        });

        m_done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
                setID();
            }
        });

        initViews();
        setID();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu_bar, menu);
        return true;

    }

    void initViews() {
        spin_kat = (Spinner) findViewById(R.id.giat_kat);
        spin_bagian = (Spinner) findViewById(R.id.giat_bag_laksana);
        spin_pelaksana = (Spinner) findViewById(R.id.giat_sat_laksana);
        spin_status = (Spinner) findViewById(R.id.giat_status);

        status_adapter = ArrayAdapter.createFromResource(this, R.array.status, android.R.layout.simple_spinner_item);
        status_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        bagian_adapter = ArrayAdapter.createFromResource(this, R.array.bagian, android.R.layout.simple_spinner_item);
        bagian_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin_bagian.setAdapter(bagian_adapter);
        spin_status.setAdapter(status_adapter);

//        getData();

        et_id = (EditText) findViewById(R.id.giat_id);
        et_nama = (EditText) findViewById(R.id.giat_nama);
        et_lokasi = (EditText) findViewById(R.id.giat_n_lokasi);
        et_DLokasi = (EditText) findViewById(R.id.giat_d_lokasi);
        et_ALokasi = (EditText) findViewById(R.id.giat_a_lokasi);
        et_kota = (EditText) findViewById(R.id.giat_kota);
        et_desk = (EditText) findViewById(R.id.giat_desk);




        PD = new ProgressDialog(this);
        PD.setMessage("Loading.....");
        PD.setCancelable(false);

        getDataSatlantas(); //SET SATUAN SPINNER
        getDataKategori(); //SET KATEGORI SPINNER

    }

    public void insert() {

        PD.show();

        mId = et_id.getText().toString();
        mNama = et_nama.getText().toString();
        mLokasi = et_lokasi.getText().toString();
        mDLokasi = et_DLokasi.getText().toString();
        mALokasi = et_ALokasi.getText().toString();
        mKota = et_kota.getText().toString();
        mDesk = et_desk.getText().toString();
        mStatus = spin_status.getSelectedItem().toString();
        mBagian = spin_bagian.getSelectedItem().toString();

        int satuanPos = spin_pelaksana.getSelectedItemPosition() + 1;
        if(String.valueOf(satuanPos).length() == 1) {
            String satuanID = "S0" + String.valueOf(satuanPos);
            mPelaksana = satuanID;
        }else{
            String satuanID = "S" + String.valueOf(satuanPos);
            mPelaksana = satuanID;
        }

        int kategoriPos = spin_kat.getSelectedItemPosition() + 1;
        if(String.valueOf(kategoriPos).length() == 1) {
            String satuanID = "K0" + String.valueOf(satuanPos);
            mKategori = satuanID;
        }else{
            String satuanID = "K" + String.valueOf(satuanPos);
            mKategori = satuanID;
        }

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jPesan = new JSONObject(response);
                            boolean pesan = jPesan.names().get(1).equals("success");

                            if(pesan == true){
                                PD.dismiss();
                                et_nama.setText("");
                                et_lokasi.setText("");
                                et_DLokasi.setText("");
                                et_ALokasi.setText("");
                                et_kota.setText("");
                                et_desk.setText("");
                                Toast.makeText(getApplicationContext(),
                                        "Data Kegiatan Berhasil di Simpan",
                                        Toast.LENGTH_SHORT).show();

                            }else {
                                PD.dismiss();
                                Toast.makeText(getApplicationContext(),
                                        "Terjadi Kesalahan",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                PD.dismiss();
                Toast.makeText(getApplicationContext(),
                        "Terjadi Kesalahan Jaringan" +error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", "K00008");
                params.put("nama-kegiatan", mNama);
                params.put("kategori", mKategori);
                params.put("nama-lokasi", mLokasi);
                params.put("alamat-lokasi", mALokasi);
                params.put("detail-lokasi", mDLokasi);
                params.put("kota", mKota);
                params.put("status", mStatus);
                params.put("satuan", mPelaksana);
                params.put("bagian", mBagian);
                params.put("deskripsi", mDesk);
                return params;
            }
        };

        // Adding request to request queue
        MyApplication.getInstance().addToReqQueue(postRequest);
    }

    public void getDataSatlantas(){
        StringRequest stringRequest = new StringRequest(parser.DATA_SAT_SPINNER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {

                            j = new JSONObject(response);
                            JSONArray result = j.getJSONArray(parser.SATLANTAS_JSON);
                            getSatlantas(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



    public void getDataKategori(){
        StringRequest stringRequest = new StringRequest(parser.DATA_KAT_SPINNER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {

                            j = new JSONObject(response);
                            JSONArray result = j.getJSONArray(parser.KATEGORI_JSON);
                            getKategori(result);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void getSatlantas(JSONArray j){
        for (int i =0; i<j.length(); i++){
            try {
                JSONObject json = j.getJSONObject(i);
                namaSatuan.add(json.getString(parser.NAMA_SATUAN));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        spin_pelaksana.setAdapter(new ArrayAdapter<String>(tambah_kegiatan.this, android.R.layout.simple_spinner_dropdown_item, namaSatuan));

    }


    public void getKategori(JSONArray j){
        for (int i =0; i<j.length(); i++){
            try {
                JSONObject json = j.getJSONObject(i);
                namaKategori.add(json.getString(parser.NAMA_KATEGORI));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        spin_kat.setAdapter(new ArrayAdapter<String>(tambah_kegiatan.this, android.R.layout.simple_spinner_dropdown_item, namaKategori));

    }

    public void setID(){
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.names().get(0).equals("id")){
                        String id = jsonObject.getString("id");
                        et_id.setText(id);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MyApplication.getInstance().addToReqQueue(request);
    }

}
