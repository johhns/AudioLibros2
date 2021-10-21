package com.developer.johhns.audiolibros2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.developer.johhns.audiolibros2.Fragmentos.DetalleFragment;
import com.developer.johhns.audiolibros2.Fragmentos.SelectorFragmentos;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    final Handler handler =new Handler();
    Button btnPlayMusic ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //  Aplicacion aplicacion = (Aplicacion) getApplication() ;

        btnPlayMusic = findViewById(R.id.btnPlay ) ;
        btnPlayMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( v.getContext() , Reproductor.class ));
            }
        });

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.encabezado, new SelectorFragmentos())
                .replace(R.id.detalle, new DetalleFragment())
                .commit();

        //playMusic() ;

    }

    public void mostrarDetalle(int posicion) {
        DetalleFragment detalle = new DetalleFragment();
        Bundle args = new Bundle();
        args.putInt(DetalleFragment.ARG_ID_LIBRO, posicion);
        detalle.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.detalle, detalle)
                .commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
}













