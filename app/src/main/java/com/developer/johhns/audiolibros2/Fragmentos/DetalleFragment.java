package com.developer.johhns.audiolibros2.Fragmentos;


import android.app.Activity;
import android.media.AudioAttributes;
import android.media.AudioManager;
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
import java.net.URI;

public class DetalleFragment extends Fragment implements View.OnTouchListener,
        MediaPlayer.OnPreparedListener, MediaController.MediaPlayerControl , MediaPlayer.OnErrorListener {

    public static String ARG_ID_LIBRO = "id_libro";
    MediaPlayer mediaPlayer ;
    MediaController mediaController ;
    private Activity actividad;

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        this.actividad   = activity ;
    }

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

        Log.i("DETALLE","URL = **" + libro.urlAudio + "**") ;
        AudioAttributes attr = new AudioAttributes.Builder()
                .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                .build();


        mediaPlayer = new MediaPlayer();
        //Uri
        String audio = libro.urlAudio  ;

        mediaPlayer.setAudioAttributes( attr );
        mediaPlayer.setOnPreparedListener(this);
        mediaController = new MediaController(getActivity());
        try {
            //mediaPlayer.setDataSource( actividad.getBaseContext() , audio );
            mediaPlayer.setDataSource(audio);
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    Log.e("Audiolibros","ERROR : " + String.valueOf(what) + "-" + String.valueOf(extra) ) ;
                    return false;
                }
            });
            mediaPlayer.prepareAsync();
        } catch ( IllegalArgumentException e ){
           e.printStackTrace();
            Log.e("Audiolibros","ERROR : ARGUMENT EXCEPTION : " + audio, e) ;
        } catch ( IllegalStateException e ){
           e.printStackTrace();
            Log.e("Audiolibros","ERROR : ESTADO EXCEPTION : " + audio, e) ;
        } catch ( IOException e ){
            Log.e("Audiolibros","ERROR : IOEXCEPTION : " + audio, e) ;
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
        Log.i("Audiolibros","MEDIAPLAYER ON TOUCH" ) ;
        return false;
    }

    @Override
    public void start() {
        mediaPlayer.start();
        Log.i("Audiolibros","MEDIAPLAYER INICIADO " ) ;
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
        Log.i("Audiolibros","MEDIAPLAYER PAUSADO " ) ;
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
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
        mediaPlayer = null ;
        Log.i("Audiolibros","MEDIAPLAYER DESTROY " ) ;
    }

    @Override
    public void seekTo(int pos) {
       mediaPlayer.seekTo( pos );
        Log.i("Audiolibros","MEDIAPLAYER SEEK TO " ) ;
    }

    @Override
    public boolean isPlaying() {
        Log.i("Audiolibros","MEDIAPLAYER SONANDO " ) ;
        return mediaPlayer.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        Log.i("Audiolibros","MEDIAPLAYER CAN PAUSE " ) ;
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

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Log.e("Audiolibros","ON ERROR : " + what ) ;
        return false;
    }
}
