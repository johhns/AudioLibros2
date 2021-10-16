package com.developer.johhns.audiolibros2.Fragmentos;


import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.MediaController;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.developer.johhns.audiolibros2.Aplicacion;
import com.developer.johhns.audiolibros2.Libro;
import com.developer.johhns.audiolibros2.R;

import java.io.IOException;

public class DetalleFragment extends Fragment implements View.OnTouchListener,
        MediaPlayer.OnPreparedListener, MediaController.MediaPlayerControl {

    public static String ARG_ID_LIBRO = "id_libro";
    MediaPlayer mediaPlayer ;
    MediaController mediaController ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View   vista = inflater.inflate(R.layout.fragmento_detalle , container , false ) ;
        Bundle args  = getArguments() ;
        if ( args != null ) {
            int posicion = args.getInt( ARG_ID_LIBRO ) ;
            obtenerInformacionLibro( posicion , vista );
        } else {
            obtenerInformacionLibro( 0 , vista );
        }
        return vista ;
    }

    private void obtenerInformacionLibro(int posicion, View vista) {
        Libro libro = ( (Aplicacion) getActivity().getApplication() ).getListalibros().get(posicion) ;
        ( (TextView) vista.findViewById( R.id.titulo ) ).setText( libro.titulo ) ;
        ( (TextView) vista.findViewById( R.id.autor ) ).setText( libro.autor );
        ( (ImageView) vista.findViewById( R.id.portada ) ).setImageResource( libro.recursoImagen );
        vista.setOnTouchListener( this );
        if ( mediaPlayer != null ) {
            mediaPlayer.release();
        }

        Log.i("DETALLE","URL = " + libro.urlAudio) ;

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);

        mediaController = new MediaController(getActivity());
        Uri audio = Uri.parse( libro.urlAudio ) ;
        try {
            mediaPlayer.setDataSource( getActivity().getBaseContext() , audio );

            mediaPlayer.prepareAsync();

        } catch ( IOException e ){
            Log.e("Audiolibros","ERROR : NO SE PUEDE REPRODUCIR EL AUDIO : " + audio, e) ;
        }
    }

    public void obtenerInformacionLibro(int posicion ) {
        obtenerInformacionLibro( posicion , getView() ) ;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
        mediaController.setMediaPlayer(this);
        mediaController.setAnchorView( getView().findViewById( R.id.detalle ) );
        mediaController.setEnabled(true);
        mediaController.show();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mediaController.show();
        return false;
    }

    @Override
    public void start() {
        mediaPlayer.start();
    }

    @Override
    public void onStop() {
        mediaController.hide();
        try {
            mediaPlayer.stop();
            mediaPlayer.release();
        } catch ( Exception e ){
            Log.e("Audiolibros","ERROR EN MEDIAPLAYER STOP" , e) ;
        }
        super.onStop();
    }

    @Override
    public void pause() {
      mediaPlayer.pause();
    }

    @Override
    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        try {
            return mediaPlayer.getCurrentPosition();
        } catch ( Exception e ) {
            return 0 ;
        }
    }

    @Override
    public void seekTo(int pos) {
       mediaPlayer.seekTo( pos );
    }

    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }
}
