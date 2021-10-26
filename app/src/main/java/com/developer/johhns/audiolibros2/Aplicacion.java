package com.developer.johhns.audiolibros2;

import android.app.Activity;
import android.app.Application;

import java.util.List;

public class Aplicacion extends Application {

    public List<Libro> listaLibros ;

    //private AdaptadorLibros adaptador ;
    private AdaptadorLibrosFiltro adaptador ;


    @Override
    public void onCreate() {
        super.onCreate();

        listaLibros = Libro.ejemploLibros() ;
        //adaptador   = new AdaptadorLibros( this , listaLibros ) ;
        adaptador   = new AdaptadorLibrosFiltro( this , listaLibros ) ;
    }

    public AdaptadorLibrosFiltro getAdaptador() {
        return adaptador ;
    };

    public List<Libro> getListalibros(){
        return listaLibros ;
    }

}
