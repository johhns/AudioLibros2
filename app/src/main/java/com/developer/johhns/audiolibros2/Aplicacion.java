package com.developer.johhns.audiolibros2;

import android.app.Activity;
import android.app.Application;
import android.graphics.Bitmap;
import android.util.LruCache;

import androidx.annotation.Nullable;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import java.util.List;

public class Aplicacion extends Application {

    public List<Libro> listaLibros ;

    //private AdaptadorLibros adaptador ;
    private AdaptadorLibrosFiltro adaptador ;

    private static RequestQueue colaPeticiones ;
    private static ImageLoader  lectorImagenes ;


    @Override
    public void onCreate() {
        super.onCreate();

        listaLibros    = Libro.ejemploLibros() ;
        ///adaptador   = new AdaptadorLibros( this , listaLibros ) ;
        adaptador      = new AdaptadorLibrosFiltro( this , listaLibros ) ;
        colaPeticiones = Volley.newRequestQueue(this);
        lectorImagenes = new ImageLoader(colaPeticiones, new ImageLoader.ImageCache() {

            private final LruCache<String,Bitmap> cache = new LruCache<String,Bitmap>(10);

            @Nullable
            @Override
            public Bitmap getBitmap(String url) {
                return cache.get( url ) ;
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put( url , bitmap ) ;
            }
        }) ;
    }

    public AdaptadorLibrosFiltro getAdaptador() {
        return adaptador ;
    };

    public List<Libro> getListalibros(){
        return listaLibros ;
    }

    public static RequestQueue getColaPeticiones() {
        return colaPeticiones;
    }

    public static void setColaPeticiones(RequestQueue colaPeticiones) {
        Aplicacion.colaPeticiones = colaPeticiones;
    }

    public static ImageLoader getLectorImagenes() {
        return lectorImagenes;
    }

    public static void setLectorImagenes(ImageLoader lectorImagenes) {
        Aplicacion.lectorImagenes = lectorImagenes;
    }
}
