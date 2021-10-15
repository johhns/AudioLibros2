package com.developer.johhns.audiolibros2;

import android.app.Activity;
import android.app.Application;

import java.util.List;

public class Aplicacion extends Application {

    private List<Libro> listalibros ;

    private AdaptadorLibros adaptador ;


    @Override
    public void onCreate() {
        super.onCreate();

        listalibros = Libro.ejemploLibros() ;
        adaptador   = new AdaptadorLibros( this , listalibros ) ;
    }

    public AdaptadorLibros getAdaptador() {
        return adaptador ;
    };

    public List<Libro> getListalibros(){
        return listalibros ;
    }

}
