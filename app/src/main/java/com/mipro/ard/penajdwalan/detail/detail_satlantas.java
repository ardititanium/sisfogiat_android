package com.mipro.ard.penajdwalan.detail;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.mipro.ard.penajdwalan.Helper.UI_EditText;
import com.mipro.ard.penajdwalan.R;
import com.mipro.ard.penajdwalan.daftar.daftar_satlantas;
import com.mipro.ard.penajdwalan.edit.edit_satlantas;
import com.mipro.ard.penajdwalan.json_handler.MyApplication;
import com.mipro.ard.penajdwalan.json_handler.parser;

import org.apache.commons.lang3.text.WordUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class detail_satlantas extends AppCompatActivity {
    TextView det_nama_tv, det_alamat_tv, det_id_sat;
    Button edit_btn, hapus_btn;
    Bundle setSat;
    ImageButton kembali_btn;
    String idParams;

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

        idParams = det_id_sat.getText().toString();
        Log.d("idParams", idParams);

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

        hapus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogHapus();
            }
        });


    }

    public void hapus(){
        String urlHapus = "http://"+parser.IP_PUBLIC+"/ditlantas/json/satlantas/hapus.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, urlHapus,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jPesan = new JSONObject(response);
                            boolean pesan = jPesan.names().get(0).equals("success");
                            if (pesan){
                                hapusBerhasil();
                            }else if(!pesan){
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
                Toast.makeText(getApplicationContext(),
                        "Terjadi Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", idParams);
                return params;
            }
        };

        MyApplication.getInstance().addToReqQueue(postRequest);
    }


    private void hapusBerhasil() {
        new MaterialDialog.Builder(detail_satlantas.this)
                .content("Hapus Berhasil")
                .positiveText("Kembali Ke Daftar")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Intent backDaftar = new Intent(detail_satlantas.this, daftar_satlantas.class);
                        startActivity(backDaftar);
                        finish();
                    }
                })
                .show();
    }

    public void dialogHapus(){
        new MaterialDialog.Builder(detail_satlantas.this)
                .content("Anda Yakin?")
                .positiveText("YA")
                .negativeText("BATAL")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        hapus();
                        Intent intentYa = new Intent(detail_satlantas.this, daftar_satlantas.class);
                        startActivity(intentYa);
                        finish();
                    }
                })
                .show();
    }
}
