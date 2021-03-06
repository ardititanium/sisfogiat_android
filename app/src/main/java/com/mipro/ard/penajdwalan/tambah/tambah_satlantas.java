package com.mipro.ard.penajdwalan.tambah;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mipro.ard.penajdwalan.MainActivity;
import com.mipro.ard.penajdwalan.R;
import com.mipro.ard.penajdwalan.daftar.daftar_satlantas;
import com.mipro.ard.penajdwalan.json_handler.MyApplication;
import com.mipro.ard.penajdwalan.json_handler.parser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class tambah_satlantas extends AppCompatActivity {
    String url = "http://" +parser.IP_PUBLIC+ "/ditlantas/json/satlantas/insert.php";
    String mId, mNama, mAlamat;
    EditText et_id, et_nama, et_alamat;

    TextView title_bar;
    ImageButton m_done_btn, m_back_btn;



    ProgressDialog PD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_satlantas);

        title_bar = (TextView) findViewById(R.id.bar_title);
        m_back_btn = (ImageButton) findViewById(R.id.kembali_btn);
        m_done_btn = (ImageButton) findViewById(R.id.search_btn);

        title_bar.setText("TAMBAH SATLANTAS");

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
        et_id = (EditText) findViewById(R.id.sat_id);
        et_nama = (EditText) findViewById(R.id.sat_nama);
        et_alamat = (EditText) findViewById(R.id.sat_alamat);


        PD = new ProgressDialog(this);
        PD.setMessage("Loading.....");
        PD.setCancelable(false);

    }


    public void insert() {

        PD.show();

        mNama = et_nama.getText().toString();
        mAlamat = et_nama.getText().toString();

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
                                et_alamat.setText("");
                                new MaterialDialog.Builder(tambah_satlantas.this)
                                        .content("Data Satlantas Berhasil di Simpan")
                                        .positiveText("Tambah Baru")
                                        .negativeText("Lihat Daftar")
                                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                Intent daftarInt = new Intent(tambah_satlantas.this, daftar_satlantas.class);
                                                startActivity(daftarInt);
                                                finish();
                                            }
                                        })
                                        .show();


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
                        "Terjadi Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("nama-satlantas", mNama);
                params.put("alamat", mAlamat);
                return params;
            }
        };

        // Adding request to request queue
        MyApplication.getInstance().addToReqQueue(postRequest);
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
