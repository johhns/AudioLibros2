package com.developer.johhns.audiolibros2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar menu = findViewById( R.id.barra_ppl ) ;

        if ( menu != null ) {
            setSupportActionBar(menu);
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.encabezado, new SelectorFragmentos())
                .replace(R.id.detalle, new DetalleFragment())
                .commit();
    }

    public void mostrarDetalle(int posicion) {
        DetalleFragment detalle = new DetalleFragment();
        Bundle args = new Bundle();
        args.putInt(DetalleFragment.ARG_ID_LIBRO, posicion);
        detalle.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.detalle, detalle)
                .commit();
        SharedPreferences preferencias = getSharedPreferences( "audiolibros", MODE_PRIVATE ) ;
        SharedPreferences.Editor editor = preferencias.edit() ;
        editor.putInt( "ultimo" , posicion ) ;
        editor.commit() ;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId() ;
        AlertDialog.Builder mensaje = new AlertDialog.Builder(this) ;
        switch ( id ){
            case R.id.menu_preferencias :
                 mensaje.setMessage("Preferecias") ;
                 mensaje.setPositiveButton(android.R.string.ok,null);
                 mensaje.create().show();
                 return true;
            case R.id.menu_acerca:
                mensaje.setMessage("Acerca de") ;
                mensaje.setPositiveButton(android.R.string.ok,null) ;
                mensaje.create().show();
                return true ;
        }
        return super.onOptionsItemSelected(item);
    }

    public void ultimoVisitado(){
        SharedPreferences preferencias = getSharedPreferences("audiolibros",MODE_PRIVATE);
        int id = preferencias.getInt( "ultimo" , -1 ) ;
        if ( id >=0  ) {
            mostrarDetalle(id);
        } else {
            Toast.makeText(this ,"Sin ultima visita" , Toast.LENGTH_LONG).show();
        }
    }

}













