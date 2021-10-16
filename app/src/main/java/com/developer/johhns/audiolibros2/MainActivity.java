package com.developer.johhns.audiolibros2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.encabezado, new SelectorFragmentos())
                .replace(R.id.detalle, new DetalleFragment())
                .commit();

        playMusic() ;

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

    private void playMusic(){
        final Thread audioThread = new Thread(new Runnable() {
            @Override
            public void run() {
                final MediaPlayer mediaPlayer = new MediaPlayer();

                Uri myUri = Uri.parse("http://mmoviles.upv.es/audiolibros/kappa.mp3");
                try {
                    mediaPlayer.setDataSource(getBaseContext(), myUri);
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        btnPlayMusic.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (mediaPlayer.isPlaying()) {
                                    mediaPlayer.pause();
                                } else {
                                    if (mediaPlayer.getCurrentPosition() > 0)
                                        mediaPlayer.seekTo(0);
                                    mediaPlayer.start();
                                }
                            }
                        });
                    }
                });
            }
        });
    }


}













