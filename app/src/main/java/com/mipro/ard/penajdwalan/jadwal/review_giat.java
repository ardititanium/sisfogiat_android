package com.mipro.ard.penajdwalan.jadwal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.mipro.ard.penajdwalan.R;
import com.mipro.ard.penajdwalan.json_handler.MyApplication;
import com.mipro.ard.penajdwalan.json_handler.parser;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class review_giat extends AppCompatActivity {

    ImageView validator;

    ProgressDialog PD;
    String url, idJadwal, idGiat, imagePath;
    ImageButton back_btn, accept_btn, reject_btn;
    TextView titlebar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_giat);

        validator = (ImageView) findViewById(R.id.validator_image);
        back_btn = (ImageButton) findViewById(R.id.kembali_btn);
        accept_btn = (ImageButton)findViewById(R.id.accept_btn);
        reject_btn = (ImageButton) findViewById(R.id.reject_btn);
        titlebar = (TextView) findViewById(R.id.bar_title);
        titlebar.setText("REVIEW GIAT");

        PD = new ProgressDialog(this);
        PD.setMessage("Sedang Memuat Gambar");
        PD.setCancelable(false);

        Bundle getId = getIntent().getExtras();
        idJadwal = getId.getString("idJadwal");
        idGiat = getId.getString("idGiat");

        initView();

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
                finish();
            }
        });

        accept_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String param = "ongoing";
                validasi(idJadwal, param);
            }
        });

        reject_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String param = "reject";
                validasi(idJadwal, param);
            }
        });
    }


    private void validasi(String id, String param) {
        String urlValidasi = "http://"+ parser.IP_PUBLIC+"/ditlantas/json/jadwal/update_status.php?id="+id+"&param="+param;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlValidasi,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jpesan = null;
                        try {
                            jpesan = new JSONObject(response);
                            boolean pesan = jpesan.names().get(0).equals("success");
                            if (pesan){
                                UpdateBerhasil();
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
        MyApplication.getInstance().addToReqQueue(stringRequest);

    }

    private void UpdateBerhasil() {
        new MaterialDialog.Builder(review_giat.this)
                .content("Status Kegiatan Behasil diperbaharui")
                .positiveText("OK")
                .canceledOnTouchOutside(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        back();
                    }
                })
                .show();
    }

    public void initView(){
        PD.show();

        url = "http://"+ parser.IP_PUBLIC +"/ditlantas/json/jadwal/view_validator.php?idj="+idJadwal;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        PD.dismiss();

                        try {
                            JSONObject jsonObject = response.getJSONObject("data");
                            JSONArray jsonArray = jsonObject.getJSONArray("validator");
                            JSONObject imageUrl = jsonArray.getJSONObject(0);


                            imagePath = "http://"+parser.IP_PUBLIC+"/ditlantas/"+imageUrl.getString("path");
                            Log.d("ImagePath", imagePath);
                            Picasso.with(getApplicationContext())
                                    .load(imagePath)
                                    .into(validator);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("Salah1", e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error2", error.toString());
            }
        });

        MyApplication.getInstance().addToReqQueue(jsonObjectRequest);
    }

    public void back(){
        Intent backDetail = new Intent(review_giat.this, detail_jadwal.class);
        backDetail.putExtra("idJadwal", idJadwal);
        backDetail.putExtra("idGiat", idGiat);
        startActivity(backDetail);
        finish();
    }

}
