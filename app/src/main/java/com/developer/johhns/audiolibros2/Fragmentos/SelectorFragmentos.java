package com.developer.johhns.audiolibros2.Fragmentos;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.developer.johhns.audiolibros2.AdaptadorLibros;
import com.developer.johhns.audiolibros2.Aplicacion;
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
        recyclerView = vista.findViewById(R.id.recView01);
        recyclerView.setLayoutManager(new GridLayoutManager(this.contexto, 2));
        recyclerView.setAdapter(adaptadorLibros);
        adaptadorLibros.setOnItemClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ( (MainActivity) actividad ).mostrarDetalle(recyclerView.getChildAdapterPosition(v)) ;
            }
        });
        return vista;
    }


}
