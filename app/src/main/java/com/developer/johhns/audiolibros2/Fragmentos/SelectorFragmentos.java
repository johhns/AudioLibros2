package com.developer.johhns.audiolibros2.Fragmentos;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.developer.johhns.audiolibros2.AdaptadorLibros;
import com.developer.johhns.audiolibros2.Aplicacion;
import com.developer.johhns.audiolibros2.Libro;
import com.developer.johhns.audiolibros2.MainActivity;
import com.developer.johhns.audiolibros2.R;
import com.google.android.material.snackbar.Snackbar;

public class SelectorFragmentos extends Fragment {

    private Activity        actividad;
    private RecyclerView    recyclerView;
    private AdaptadorLibros adaptadorLibros;
    private Context         contexto ;
    private DetalleFragment detalleFragment ;

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        this.actividad   = activity ;
        Aplicacion app  = (Aplicacion) actividad.getApplication();
        adaptadorLibros = app.getAdaptador();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_selector, container, false);
        this.actividad  = getActivity();

        setHasOptionsMenu(true);

        recyclerView = vista.findViewById(R.id.recView01);
        recyclerView.setLayoutManager(new GridLayoutManager(this.contexto, 2));
        recyclerView.setAdapter(adaptadorLibros);
        adaptadorLibros.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ( (MainActivity) actividad ).mostrarDetalle(recyclerView.getChildAdapterPosition(v)) ;
            }
        });

        adaptadorLibros.setOnItemLongClickListener( new View.OnLongClickListener() {
            public boolean onLongClick( final View v ) {
                final int id = recyclerView.getChildAdapterPosition(v);
                CharSequence[] opciones = { "Compartir" , "Borrar" , "Insertar" } ;
                AlertDialog.Builder menu = new AlertDialog.Builder( actividad ) ;
                menu.setItems(opciones, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int opcion) {
                        switch (opcion) {
                            case 0 : // compartir
                                Libro libro =  ((Aplicacion) actividad.getApplication()).listaLibros.get(id);
                                Intent intent = new Intent( Intent.ACTION_SEND );
                                intent.setType( "text/plain" );
                                intent.putExtra( Intent.EXTRA_SUBJECT , libro.titulo ) ;
                                intent.putExtra( Intent.EXTRA_TEXT , libro.urlAudio ) ;
                                startActivity( Intent.createChooser( intent , "Compartir" ) );
                                break;
                            case 1:
                                ((Aplicacion) actividad.getApplication()).listaLibros.remove(id);
                                adaptadorLibros.notifyDataSetChanged();
                                break;
                            case 2:
                                Libro libro2 =  ((Aplicacion) actividad.getApplication()).listaLibros.get(id);
                                ((Aplicacion) actividad.getApplication()).listaLibros.add( libro2 ) ;
                                adaptadorLibros.notifyDataSetChanged();
                                break;

                        }
                    }
                }) ;
                menu.create().show();
                return true ;
            }


        } );

        return vista;
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_selector,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId() ;
        switch ( id ){
            case R.id.menu_ultimo:
                ( (MainActivity) actividad).ultimoVisitado();
                return true ;
            case R.id.menu_buscar:
                return true ;
        }
        return super.onOptionsItemSelected(item);
    }
}
