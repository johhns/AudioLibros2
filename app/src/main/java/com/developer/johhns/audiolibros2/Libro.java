package com.developer.johhns.audiolibros2;

import java.util.ArrayList;
import java.util.List;

public class Libro {

        public  String  titulo   ;
        public  String  autor    ;
        //public  int     recursoImagen ;
        public  String  urlAudio ;
        private String  urlImagen;
        public  String  genero   ;
        public  Boolean novedad  ;
        public  Boolean leido    ;

        public static final String G_TODOS = "Todos los generos" ;
        public static final String G_EPICO = "Poema epico " ;
        public static final String G_S_XIX = "Literatura siglo XIX " ;
        public static final String G_SUSPENSO = "Suspenso " ;
        public static final String[] G_ARRAY = new String[] { G_TODOS , G_EPICO , G_S_XIX , G_SUSPENSO } ;

        public Libro() {
        }

        public Libro(String titulo, String autor, String urlImagen, String urlAudio, String genero, Boolean novedad, Boolean leido) {
            this.titulo = titulo;
            this.autor = autor;
            this.urlImagen = urlImagen;
            this.urlAudio = urlAudio;
            this.genero = genero;
            this.novedad = novedad;
            this.leido = leido;
        }

        public static List<Libro> ejemploLibros(){
            final String SERVIDOR = "http://mmoviles.upv.es/audiolibros/" ;
            List<Libro> libros = new ArrayList<Libro>();
            libros.add( new Libro( "Kappa", "Akutagawa", SERVIDOR + "kappa.jpg" ,  SERVIDOR + "kappa.mp3", Libro.G_S_XIX, false,false ) ) ;
            libros.add( new Libro( "Avecilla", "Alas Clarin", SERVIDOR + "avecilla.jpg" , SERVIDOR + "avecilla.mp3", Libro.G_S_XIX, true,false ) ) ;
            libros.add( new Libro( "Divina comedia", "Dante", SERVIDOR + "divina_comedia.jpg" , SERVIDOR + "divina_comedia.mp3", Libro.G_EPICO, true,false ) ) ;
            libros.add( new Libro( "Viejo Pancho", "Alonso y Trelles", SERVIDOR + "viejo_pancho.jpg" , SERVIDOR + "viejo_pancho.mp3", Libro.G_S_XIX, false,false ) ) ;
            libros.add( new Libro( "Cancion de Rolando", "Anonimo", SERVIDOR + "cancion_rolando.jpg" , SERVIDOR + "cancion_rolando.mp3", Libro.G_EPICO, true,true ) ) ;
            libros.add( new Libro( "Matrimonio de sabuesos", "Agata Cristi", SERVIDOR + "matrim_sabuesos.jpg" , SERVIDOR + "matrim_sabuesos.mp3", Libro.G_SUSPENSO, false,true ) ) ;
            libros.add( new Libro( "La Iliada", "Homero", SERVIDOR + "la_iliada.jpg" , SERVIDOR + "la_iliada.mp3", Libro.G_EPICO, true,false ) ) ;
            return libros ;
        }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public String getUrlAudio() {
        return urlAudio;
    }

    public void setUrlAudio(String urlAudio) {
        this.urlAudio = urlAudio;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Boolean getNovedad() {
        return novedad;
    }

    public void setNovedad(Boolean novedad) {
        this.novedad = novedad;
    }

    public Boolean getLeido() {
        return leido;
    }

    public void setLeido(Boolean leido) {
        this.leido = leido;
    }

}
