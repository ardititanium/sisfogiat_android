package com.mipro.ard.penajdwalan.edit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mipro.ard.penajdwalan.Helper.UI_EditText;
import com.mipro.ard.penajdwalan.MainActivity;
import com.mipro.ard.penajdwalan.R;
import com.mipro.ard.penajdwalan.daftar.daftar_personil;
import com.mipro.ard.penajdwalan.detail.detail_personil;
import com.mipro.ard.penajdwalan.json_handler.MyApplication;
import com.mipro.ard.penajdwalan.json_handler.parser;

import org.apache.commons.lang3.text.WordUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class edit_personil extends AppCompatActivity  implements View.OnFocusChangeListener{
    String urlInsert = "http://" + parser.IP_PUBLIC + "/ditlantas/json/personil/edit.php";
    String mNrp, mNama, mJk, mPangkat, mSatuan, mPass, mAkses, mIdSatuan, getnrp;
    EditText et_nrp, et_nama, et_pass;
    TextView title_bar, kelamin_tv, pangkat_tv, satuan_tv, akses_tv;
    ImageButton m_done_btn, m_back_btn;
    ProgressDialog PD;
    UI_EditText et_helper;

    private ArrayList<String> daftar_satuan;
    private ArrayList<String> daftar_id_satuan;
    String[] satuan, idSatuan_array;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_personil);

        et_helper = new UI_EditText();


        title_bar   = (TextView) findViewById(R.id.bar_title);
        m_back_btn  = (ImageButton) findViewById(R.id.kembali_btn);
        m_done_btn  = (ImageButton) findViewById(R.id.search_btn);


        title_bar.setText("EDIT PERSONIL");

        m_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBack();
            }
        });

        m_done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputValidation();
            }
        });

        Bundle getId = getIntent().getExtras();
        getnrp = getId.getString("nrp");

        initViews();

    }

    private void dialogBack() {
        new MaterialDialog.Builder(edit_personil.this)
                .content("Ada Yakin akan membatalkan perubahan")
                .positiveText("YA")
                .negativeText("TIDAK")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        finish();
                    }
                })
                .show();
    }

    void initViews() {
        et_nrp      = (EditText) findViewById(R.id.pers_nrp);
        et_nama     = (EditText) findViewById(R.id.pers_nama);
        et_pass     = (EditText) findViewById(R.id.pers_pas);
        kelamin_tv  = (TextView) findViewById(R.id.tv_pers_jk);
        pangkat_tv  = (TextView) findViewById(R.id.tv_pers_pangkat);
        satuan_tv   = (TextView) findViewById(R.id.tv_pers_satuan);
        akses_tv    = (TextView) findViewById(R.id.tv_pers_akses);

        et_pass.setText("ditlantas");
        loadDataPers(getnrp);

        et_helper.SingleLineNext(et_nrp);
        et_helper.SingleLineNext(et_nama);
        et_helper.SingleLineGo(et_pass);
        et_helper.SingleLineNextTv(pangkat_tv);
        et_helper.SingleLineNextTv(satuan_tv);
        et_helper.SingleLineNextTv(kelamin_tv);
        et_helper.SingleLineNextTv(akses_tv);

        PD = new ProgressDialog(this);
        PD.setMessage("Loading.....");
        PD.setCancelable(false);

        LostFocusInputValidation();

        satuan_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadSatuan();
            }
        });

        kelamin_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setKelamin();
            }
        });

        pangkat_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPangkat();
            }
        });

        akses_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAkses();
            }
        });

    }

    private void setKelamin() {
        new MaterialDialog.Builder(edit_personil.this)
                .title("Pilih Jenis Kelamin...")
                .items(R.array.jk)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        switch (which){
                            case 0:
                                mJk = "L";
                                break;
                            case 1:
                                mJk = "P";
                                break;
                        }
                        kelamin_tv.setText(text);
                    }
                })
                .show();
    }

    public void insert() {
        mNrp        = et_nrp.getText().toString();
        mNama       = et_nama.getText().toString().toUpperCase();
        mSatuan     = mIdSatuan;
        mPangkat    = pangkat_tv.getText().toString();
        mPass       = et_pass.getText().toString();
        mAkses      = akses_tv.getText().toString();

        StringRequest postRequest = new StringRequest(Request.Method.POST, urlInsert,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.names().get(0).equals("pesan")){
                                PD.dismiss();
                                String jPesan = jsonObject.getString("pesan");
                                Toast.makeText(getApplicationContext(), jPesan, Toast.LENGTH_LONG).show();
                            }else {
                                PD.dismiss();
                                et_nrp.setText("");
                                et_nrp.setFocusable(true);
                                et_nama.setText("");
                                kelamin_tv.setText("");
                                satuan_tv.setText("");
                                pangkat_tv.setText("");
                                akses_tv.setText("");
                                et_pass.setText("ditlantas");
                                savedSucces();
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
                        "Terjadi Kesalahan Jarigan", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("nrp", mNrp);
                params.put("nama", mNama);
                params.put("kelamin", mJk);
                params.put("satuan", mSatuan);
                params.put("pangkat", mPangkat);
                params.put("password", mPass);
                params.put("akses", mAkses);

                return params;
            }
        };
        MyApplication.getInstance().addToReqQueue(postRequest);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu_bar, menu);
        return true;

    }

    private void setSatuan(String[] namaSatuan){
        new MaterialDialog.Builder(edit_personil.this)
                .title("Pilih Satuan Asal...")
                .items(namaSatuan)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        satuan_tv.setText(text);
                        mIdSatuan = idSatuan_array[which];
                    }
                })
                .show();

    }

    private void LoadSatuan(){
        daftar_satuan = new ArrayList<>();
        daftar_id_satuan = new ArrayList<>();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, parser.DATA_SAT_SPINNER, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = response.getJSONObject("data");
                            JSONArray jsonArray = jsonObject.getJSONArray("satuan");
                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject item = jsonArray.getJSONObject(i);
                                String namaSatuan = item.getString("namaSatuan");
                                String idSatuan = item.getString("idSatuan");
                                daftar_satuan.add(namaSatuan);
                                daftar_id_satuan.add(idSatuan);

                            }
                            satuan          = daftar_satuan.toArray(new String[0]);
                            idSatuan_array  = daftar_id_satuan.toArray(new String[0]);
                            setSatuan(satuan);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MyApplication.getInstance().addToReqQueue(jsonObjectRequest);
    }

    public void setPangkat(){
        new MaterialDialog.Builder(edit_personil.this)
                .title("Pilih Pangkat...")
                .items(R.array.pangkat)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        pangkat_tv.setText(text);
                    }
                })
                .show();
    }

    public void setAkses(){
        new MaterialDialog.Builder(edit_personil.this)
                .title("Pilih Hak Akses...")
                .items(R.array.akses)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                        akses_tv.setText(text);
                    }
                })
                .show();
    }



    protected void inputValidation(){
        if (et_nrp.getText().length() < 8
                || et_nama.getText().length() < 3
                || kelamin_tv.getText().length() < 3
                || pangkat_tv.getText().length() < 3
                || satuan_tv.getText().length() < 3
                || akses_tv.getText().length() < 0)
        {
            new MaterialDialog.Builder(edit_personil.this)
                    .title("Opss!")
                    .content("Pastikan Tidak ada field yang kosong")
                    .negativeText("SIAP")
                    .show();
        }else {
            insert();
        }
    }


    protected void LostFocusInputValidation(){
        et_nrp.setOnFocusChangeListener(this);
        et_nama.setOnFocusChangeListener(this);
        kelamin_tv.setOnFocusChangeListener(this);
        pangkat_tv.setOnFocusChangeListener(this);
        satuan_tv.setOnFocusChangeListener(this);
        akses_tv.setOnFocusChangeListener(this);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()){
            case R.id.pers_nrp:
                if (et_nrp.getText().length() < 8 && !hasFocus){
                    et_nrp.setError("NRP Min 8 Karakter");
                }
                break;
            case R.id.pers_nama:
                if (et_nama.getText().length() < 3 && !hasFocus){
                    et_nama.setError("Nama Tidak Boleh Kosong");
                }
                break;
        }
    }


    protected void loadDataPers(String nrp){
        String urlLoad = "http://"+ parser.IP_PUBLIC +"/ditlantas/json/personil/view_detail.php?nrp="+nrp;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlLoad, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject  = response.getJSONObject("data");
                            JSONArray jsonArray = jsonObject.getJSONArray("personil");
                            JSONObject dataPers = jsonArray.getJSONObject(0);

                            et_nrp.setText(dataPers.getString("nrp"));
                            et_nama.setText(dataPers.getString("namaLengkap"));
                            pangkat_tv.setText(dataPers.getString("pangkat"));
                            satuan_tv.setText(dataPers.getString("namaSatuan"));
                            akses_tv.setText(dataPers.getString("hakAkses"));
                            et_pass.setText(dataPers.getString("password"));
                            mIdSatuan = dataPers.getString("idSatuan");

                            String kelamin = dataPers.getString("kelamin");
                            if (kelamin.equalsIgnoreCase("L")){
                                kelamin_tv.setText("Laki - Laki");
                                mJk = "L";
                            }else {
                                kelamin_tv.setText("Perempuan");
                                mJk = "P";
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

        MyApplication.getInstance().addToReqQueue(jsonObjectRequest);
    }


    public void savedSucces(){
        new MaterialDialog.Builder(edit_personil.this)
                .content("Personil Berhasil di Tambahkan")
                .positiveText("Lihat")
                .negativeText("Kembali Ke Daftar")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Bundle dataPers = new Bundle();
                        dataPers.putString("nrp", mNrp);
                        Intent intent = new Intent(edit_personil.this, detail_personil.class);
                        intent.putExtras(dataPers);
                        startActivity(intent);
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Intent backDaftar = new Intent(edit_personil.this, daftar_personil.class);
                        startActivity(backDaftar);
                    }
                })
                .show();
    }
}
