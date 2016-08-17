package com.mipro.ard.penajdwalan.detail;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.mipro.ard.penajdwalan.R;
import com.mipro.ard.penajdwalan.daftar.daftar_personil;
import com.mipro.ard.penajdwalan.daftar.daftar_satlantas;
import com.mipro.ard.penajdwalan.edit.edit_personil;
import com.mipro.ard.penajdwalan.json_handler.MyApplication;
import com.mipro.ard.penajdwalan.json_handler.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class detail_personil extends AppCompatActivity {
    TextView nrp_tv, nama_tv, pangkat_tv, satuan_tv, belum_tv, selesai_tv, total_tv, kelamin_tv, akses_tv;
    Button edit_btn, hapus_btn;
    ImageButton back_btn;
    String kelamin, pass;
    Bundle dataPers;
    String mnrp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_personil);
        back_btn = (ImageButton) findViewById(R.id.kembali_btn);
        nrp_tv = (TextView) findViewById(R.id.nrp_pers);
        nama_tv  =(TextView) findViewById(R.id.nama_pers);
        pangkat_tv = (TextView) findViewById(R.id.pangkat_pers_pop);
        satuan_tv = (TextView) findViewById(R.id.satuan_pers);
        belum_tv = (TextView) findViewById(R.id.giat_draft);
        selesai_tv = (TextView) findViewById(R.id.giat_done);
        total_tv = (TextView) findViewById(R.id.giat_total);
        kelamin_tv = (TextView) findViewById(R.id.kelamin_pers);
        akses_tv = (TextView) findViewById(R.id.akses_pers);
        edit_btn = (Button) findViewById(R.id.edit_pers);
        hapus_btn = (Button) findViewById(R.id.hapus_pers);



        dataPers = getIntent().getExtras();
        mnrp = String.valueOf(dataPers.getCharSequence("nrp"));
        loadDataPers(mnrp);

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

        hapus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogHapus();
            }
        });

    }

    private void editPersonil() {
        Intent editInt = new Intent(getApplicationContext(), edit_personil.class);
        editInt.putExtra("nrp", mnrp);
        startActivity(editInt);
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

                            nrp_tv.setText(dataPers.getString("nrp"));
                            nama_tv.setText(dataPers.getString("namaLengkap"));
                            pangkat_tv.setText(dataPers.getString("pangkat"));
                            satuan_tv.setText(dataPers.getString("namaSatuan"));
                            akses_tv.setText(dataPers.getString("hakAkses"));
                            selesai_tv.setText(dataPers.getString("done"));
                            total_tv.setText(dataPers.getString("total"));

                            int total_int       = Integer.parseInt(total_tv.getText().toString());
                            int selesai_int     = Integer.parseInt(selesai_tv.getText().toString());
                            int belum_int       = total_int - selesai_int;

                            belum_tv.setText(String.valueOf(belum_int));

                            String kelamin = dataPers.getString("kelamin");
                            if (kelamin.equalsIgnoreCase("L")){
                                kelamin_tv.setText("Laki - Laki");
                            }else {
                                kelamin_tv.setText("Perempuan");
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


    public void hapus(){
        String urlHapus = "http://"+parser.IP_PUBLIC+"/ditlantas/json/personil/hapus.php";
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
                params.put("nrp", mnrp);
                return params;
            }
        };

        MyApplication.getInstance().addToReqQueue(postRequest);
    }

    private void hapusBerhasil() {
        new MaterialDialog.Builder(detail_personil.this)
                .content("Hapus Berhasil")
                .positiveText("Kembali Ke Daftar")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Intent backDaftar = new Intent(detail_personil.this, daftar_personil.class);
                        startActivity(backDaftar);
                        finish();
                    }
                })
                .show();
    }

    public void dialogHapus(){
        new MaterialDialog.Builder(detail_personil.this)
                .content("Anda Yakin?")
                .positiveText("YA")
                .negativeText("BATAL")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        hapus();
                        Intent intentYa = new Intent(detail_personil.this, daftar_personil.class);
                        startActivity(intentYa);
                        finish();
                    }
                })
                .show();
    }


}
