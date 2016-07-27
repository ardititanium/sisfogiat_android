package com.mipro.ard.penajdwalan;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mipro.ard.penajdwalan.daftar.daftar_kategori;
import com.mipro.ard.penajdwalan.daftar.daftar_kegiatan;
import com.mipro.ard.penajdwalan.daftar.daftar_personil;
import com.mipro.ard.penajdwalan.daftar.daftar_satlantas;
import com.mipro.ard.penajdwalan.tambah.*;
import com.github.clans.fab.FloatingActionButton;



public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fabOnClick();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
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

        } else if (id == R.id.nav_d_agenda) {
            Intent add_agenda = new Intent(this.getApplicationContext(), tambah_agenda.class);
            startActivity(add_agenda);

        } else if (id == R.id.nav_d_surat) {
            Intent add_surat = new Intent(this.getApplicationContext(), tambah_surat_perintah.class);
            startActivity(add_surat);

        } else if (id == R.id.nav_d_laporan) {
            Intent add_laporan = new Intent(this.getApplicationContext(), tambah_laporan.class);
            startActivity(add_laporan);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void fabOnClick(){
        FloatingActionButton fab_satlantas = (FloatingActionButton) findViewById(R.id.fab_a_satlantas);
        FloatingActionButton fab_personil = (FloatingActionButton) findViewById(R.id.fab_a_personil);
        FloatingActionButton fab_kategori = (FloatingActionButton) findViewById(R.id.fab_a_kategori);
        FloatingActionButton fab_kegiatan = (FloatingActionButton) findViewById(R.id.fab_a_kegiatan);
        FloatingActionButton fab_agenda = (FloatingActionButton) findViewById(R.id.fab_a_agenda);
        FloatingActionButton fab_surat = (FloatingActionButton) findViewById(R.id.fab_a_surat);
        FloatingActionButton fab_laporan = (FloatingActionButton) findViewById(R.id.fab_a_laporan);

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

        fab_agenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add_agenda = new Intent(getApplicationContext(), tambah_agenda.class);
                startActivity(add_agenda);
            }
        });

        fab_surat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add_surat = new Intent(getApplicationContext(), tambah_surat_perintah.class);
                startActivity(add_surat);
            }
        });

        fab_laporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add_laporan= new Intent(getApplicationContext(), tambah_laporan.class);
                startActivity(add_laporan);
            }
        });
    }
}
