package com.developer.johhns.audiolibros2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.developer.johhns.audiolibros2.Fragmentos.DetalleFragment;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  Aplicacion aplicacion = (Aplicacion) getApplication() ;
    }

    public void mostrarDetalle( int posicion ){
        Toast.makeText(this,"Click : " + posicion,Toast.LENGTH_LONG).show();
        /*
        DetalleFragment detalle = (DetalleFragment) getFragmentManager().findFragmentById(R.id.detalle_fragment) ;
        if ( detalle != null ) {
            detalle.obtenerInformacionLibro( posicion );
        } else {
            Fragment nuevoFrag = new DetalleFragment();
            Bundle args = new Bundle() ;
            args.putInt( DetalleFragment.ARG_ID_LIBRO, posicion );
            nuevoFrag.setArguments( args );
            FragmentTransaction transaction = getFragmentManager().beginTransaction().replace(R.id.detalle_fragment ,nuevoFrag);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        */
    }

}