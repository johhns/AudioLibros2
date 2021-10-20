package com.developer.johhns.audiolibros2;

import android.app.Activity;
import android.app.Application;

import java.util.List;

public class Aplicacion extends Application {

    public List<Libro> listaLibros ;

    private AdaptadorLibros adaptador ;


    @Override
    public void onCreate() {
        super.onCreate();

        listaLibros = Libro.ejemploLibros() ;
        adaptador   = new AdaptadorLibros( this , listaLibros ) ;
    }

    public AdaptadorLibros getAdaptador() {
        return adaptador ;
    };

    public List<Libro> getListalibros(){
        return listaLibros ;
    }

}
