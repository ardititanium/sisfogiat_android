package com.mipro.ard.penajdwalan.edit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.mipro.ard.penajdwalan.daftar.daftar_kategori;
import com.mipro.ard.penajdwalan.daftar.daftar_satlantas;
import com.mipro.ard.penajdwalan.json_handler.MyApplication;
import com.mipro.ard.penajdwalan.json_handler.parser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class edit_satlantas extends AppCompatActivity {
    String url = "http://" +parser.IP_PUBLIC+ "/ditlantas/json/satlantas/edit.php";
    String mId, mNama, mAlamat;
    EditText et_id, et_nama, et_alamat;

    TextView title_bar;
    ImageButton m_done_btn, m_back_btn;

    Bundle getSat;



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


        getSat = getIntent().getExtras();

        et_id.setText(getSat.getCharSequence("idSatuan"));
        et_nama.setText(getSat.getCharSequence("namaSatuan"));
        et_alamat.setText(getSat.getCharSequence("alamat"));


        PD = new ProgressDialog(this);
        PD.setMessage("Loading.....");
        PD.setCancelable(false);

    }


    public void insert() {

        PD.show();
        mId = et_id.getText().toString();
        mNama = et_nama.getText().toString();
        mAlamat = et_alamat.getText().toString();

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jPesan = new JSONObject(response);
                            Log.d("datasatlantas", response);
                            boolean pesan = jPesan.names().get(0).equals("success");
                            if(pesan == true){
                                PD.dismiss();
                               new MaterialDialog.Builder(edit_satlantas.this)
                                .content("Perubahan telah disimpan")
                                        .positiveText("Lihat Daftar")
                                        .backgroundColorRes(R.color.success)
                                        .positiveColorRes(R.color.mdtp_white)
                                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                Intent backList = new Intent(edit_satlantas.this, daftar_satlantas.class);
                                                startActivity(backList);
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
                params.put("id", mId);
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
