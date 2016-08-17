package com.mipro.ard.penajdwalan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.clans.fab.FloatingActionMenu;
import com.mipro.ard.penajdwalan.daftar.daftar_kategori;
import com.mipro.ard.penajdwalan.daftar.daftar_kegiatan;
import com.mipro.ard.penajdwalan.daftar.daftar_laporan;
import com.mipro.ard.penajdwalan.daftar.daftar_personil;
import com.mipro.ard.penajdwalan.daftar.daftar_satlantas;
import com.mipro.ard.penajdwalan.jadwal.ListJadwal;
import com.mipro.ard.penajdwalan.jadwal.daftar_jadwal;
import com.mipro.ard.penajdwalan.json_handler.MyApplication;
import com.mipro.ard.penajdwalan.json_handler.parser;
import com.mipro.ard.penajdwalan.tambah.*;
import com.github.clans.fab.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView nrp_tv, nama_tv, coba;
    String nrp_params;
    String nrp_str, nama_str, satuan_str, pangkat_str, jk_str, pass_str, akses_str;
    FloatingActionMenu fam;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        coba = (TextView) findViewById(R.id.coba_nama);
        fam = (FloatingActionMenu) findViewById(R.id.fab_menu);



        fabOnClick();


        Intent getNrp = getIntent();
        nrp_params = getNrp.getStringExtra("nrp");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        nrp_tv = (TextView) header.findViewById(R.id.nrp_user);
        nama_tv = (TextView) header.findViewById(R.id.namaUser);


        SharedPreferences sharedPreferences = getSharedPreferences(parser.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String nrp_login = sharedPreferences.getString(parser.NRP_SHARED_PREF, "Not Available");
        String url = "http://"+ parser.IP_PUBLIC +"/ditlantas/json/personil/view_detail.php?nrp="+nrp_login;
        Log.d("URL", url);
        nrp_tv.setText(nrp_login);
        getDataUser(url);







    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menuLogut) {
            logout();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_d_satlantas) {
            Intent add_satlantas = new Intent(this.getApplicationContext(), daftar_satlantas.class);
            startActivity(add_satlantas);
        } else if (id == R.id.nav_d_personil) {
            Intent add_personil = new Intent(this.getApplicationContext(), daftar_personil.class);
            startActivity(add_personil);

        } else if (id == R.id.nav_d_kategori) {
            Intent add_kategori = new Intent(this.getApplicationContext(), daftar_kategori.class);
            startActivity(add_kategori);

        } else if (id == R.id.nav_d_kegiatan) {
            Intent add_kegiatan = new Intent(this.getApplicationContext(), daftar_kegiatan.class);
            startActivity(add_kegiatan);
        } else if (id == R.id.nav_d_laporan) {
            Intent add_laporan = new Intent(this.getApplicationContext(), daftar_laporan.class);
            startActivity(add_laporan);
        }else if(id == R.id.nav_k_jadwal){
            Intent list_jadwal = new Intent(this.getApplicationContext(), ListJadwal.class);
            startActivity(list_jadwal);
        }




        nrp_tv = (TextView) findViewById(R.id.nrp_user);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void fabOnClick(){
        FloatingActionButton fab_satlantas = (FloatingActionButton) findViewById(R.id.fab_a_satlantas);
        FloatingActionButton fab_personil = (FloatingActionButton) findViewById(R.id.fab_a_personil);
        FloatingActionButton fab_kategori = (FloatingActionButton) findViewById(R.id.fab_a_kategori);
        FloatingActionButton fab_kegiatan = (FloatingActionButton) findViewById(R.id.fab_a_kegiatan);


        fab_satlantas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add_satlantas = new Intent(getApplicationContext(), tambah_satlantas.class);
                startActivity(add_satlantas);
            }
        });

        fab_personil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add_personil = new Intent(getApplicationContext(), tambah_personil.class);
                startActivity(add_personil);
            }
        });

        fab_kategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add_kat = new Intent(getApplicationContext(), tambah_kategori.class);
                startActivity(add_kat);
            }
        });

        fab_kegiatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add_kegiatan = new Intent(getApplicationContext(), tambah_kegiatan.class);
                startActivity(add_kegiatan);
            }
        });

    }


    public void logout(){
        new MaterialDialog.Builder(MainActivity.this)
                .content("Anda yakin akan Logout")
                .positiveText("YA")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        SharedPreferences sharedPreferences = getSharedPreferences(parser.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putBoolean(parser.LOGGEDIN_SHARED_PREF, false);
                        editor.putString(parser.NRP_SHARED_PREF, "");
                        editor.commit();

                        Intent logout = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(logout);
                        finish();

                    }
                })
                .negativeText("TIDAK")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                    }
                })
                .show();
    }

    public void getDataUser(String url_source){

        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.GET, url_source, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = response.getJSONObject("data");
                            JSONArray jsonArray = jsonObject.getJSONArray("personil");
                            JSONObject dataPers = jsonArray.getJSONObject(0);
                            String jk_temp, satuan_temp;



                            nrp_str     = dataPers.getString("nrp");
                            nama_str    = dataPers.getString("namaLengkap");
                            pangkat_str = dataPers.getString("pangkat");
                            pass_str    = dataPers.getString("password");
                            akses_str   = dataPers.getString("hakAkses");
                            satuan_str  = dataPers.getString("namaSatuan");

                            nama_tv.setText(nama_str);

                            if (akses_str.equals("user")){
                                fam.setVisibility(View.GONE);
                            }

                            parser.AKSES_SHARED_PREF = akses_str;
                            parser.NRP_ON_GIAT = nrp_str;


                            jk_temp = dataPers.getString("kelamin");
                            if (jk_temp.equalsIgnoreCase("L")){
                                jk_str =  "Laki - Laki";
                            }else {
                                jk_str = "Perempuan";
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

        coba.setText("adasdsda");

    }


}
