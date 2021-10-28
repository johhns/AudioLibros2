package com.developer.johhns.audiolibros2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.developer.johhns.audiolibros2.Fragmentos.DetalleFragment;
import com.developer.johhns.audiolibros2.Fragmentos.SelectorFragmentos;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private boolean dosFragments;
    private boolean enPreferencias=false;
    private DetalleFragment detalleFragment;
    private AdaptadorLibrosFiltro adaptador;
    private AppBarLayout appBarLayout;
    private TabLayout tabs;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private Menu menu;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adaptador = ((Aplicacion) getApplicationContext()).getAdaptador();

        View detalleFragment = findViewById(R.id.detalle_fragment);
        dosFragments = detalleFragment != null && detalleFragment.getVisibility() == View.VISIBLE;
        ponFragmentIzquierdo(new SelectorFragmentos());

        tabs = findViewById( R.id.tabs ) ;
        tabs.addTab( tabs.newTab().setText("Todos") );
        tabs.addTab( tabs.newTab().setText("Nuevos") );
        tabs.addTab( tabs.newTab().setText("Leidos") );
        tabs.setTabMode( TabLayout.MODE_SCROLLABLE );
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch ( tab.getPosition() ) {
                    case 0: //todos
                        adaptador.setNovedad(false);
                        adaptador.setLeido(false);
                        break;
                    case 1 : // nuevos
                        adaptador.setNovedad(true);
                        adaptador.setLeido(false);
                        break;
                    case 2: // leidos
                        adaptador.setNovedad(false);
                        adaptador.setLeido(true);
                        break;
                }
                adaptador.notifyDataSetChanged();
            }
            @Override public void onTabUnselected(TabLayout.Tab tab) {  }
            @Override  public void onTabReselected(TabLayout.Tab tab) { }
        });

        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        Toolbar menu = findViewById( R.id.barra_ppl ) ;
        setSupportActionBar(menu);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this,
                drawer, menu, R.string.drawer_open, R.string.drawer_close);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                onBackPressed();
            }
        });
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById( R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED,navigationView);
// Botón flotante
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


/*
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.encabezado, new SelectorFragmentos())
                .replace(R.id.detalle, new DetalleFragment())
                .commit();
        */
    }



    @Override
    public void onStop() {
        getSupportFragmentManager().popBackStackImmediate();
        if (dosFragments || enPreferencias) //en preferencia
            for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }
            }
        super.onStop();
    }


    public void mostrarDetalle(int id) {
        detalleFragment = (DetalleFragment)  getSupportFragmentManager().findFragmentById(R.id.detalle_fragment);
        if ( dosFragments ) {
            if ( detalleFragment != null ) {
                detalleFragment.obtenerInformacionLibro(id);
            } else {
                Log.i("MAIN-ACTIVITY", "NO SE PUDO OBTENER REFERENCIA AL OBJETO DETALLE-FRAGMENT");
            }
        } else {
            mostrarBarraAmpliada(false); //////////////////
            detalleFragment = new DetalleFragment();
            Bundle args = new Bundle();
            args.putInt(DetalleFragment.ARG_ID_LIBRO, id);
            detalleFragment.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contenedor_pequeno, detalleFragment)
                    .addToBackStack(null).commit();
            SharedPreferences pref = getSharedPreferences( "com.example.audiolibros_internal", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("ultimo", id);
            editor.commit();
        }
    }

/*
    public void mostrarDetalle(int posicion) {
        DetalleFragment detalle = new DetalleFragment();
        Bundle args = new Bundle();
        args.putInt(DetalleFragment.ARG_ID_LIBRO, posicion);
        detalle.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.detalle, detalle)
                .commit();
        SharedPreferences preferencias = getSharedPreferences( "audiolibros", MODE_PRIVATE ) ;
        SharedPreferences.Editor editor = preferencias.edit() ;
        editor.putInt( "ultimo" , posicion ) ;
        editor.commit() ;
    }
    */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        this.menu = menu ;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId() ;
        AlertDialog.Builder mensaje = new AlertDialog.Builder(this) ;
        switch ( id ){
            case R.id.menu_preferencias :
                 mensaje.setMessage("Preferecias") ;
                 mensaje.setPositiveButton(android.R.string.ok,null);
                 mensaje.create().show();
                 return true;
            case R.id.menu_acerca:
                mensaje.setMessage("Acerca de") ;
                mensaje.setPositiveButton(android.R.string.ok,null) ;
                mensaje.create().show();
                return true ;
        }
        return super.onOptionsItemSelected(item);
    }

    public void ultimoVisitado(){
        SharedPreferences preferencias = getSharedPreferences("audiolibros",MODE_PRIVATE);
        int id = preferencias.getInt( "ultimo" , -1 ) ;
        if ( id >=0  ) {
            mostrarDetalle(id);
        } else {
            Toast.makeText(this ,"Sin ultima visita" , Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_todos) {
            adaptador.setGenero("");
            adaptador.notifyDataSetChanged();
        } else if (id == R.id.nav_epico) {
            adaptador.setGenero(Libro.G_EPICO);
            adaptador.notifyDataSetChanged();
        } else if (id == R.id.nav_XIX) {
            adaptador.setGenero(Libro.G_S_XIX);
            adaptador.notifyDataSetChanged();
        } else if (id == R.id.nav_suspense) {
            adaptador.setGenero(Libro.G_SUSPENSO);
            adaptador.notifyDataSetChanged();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(
                R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//         mostrarBarraAmpliada(true);   ///////////////////
            super.onBackPressed();
        }
    }


    public void mostrarBarraAmpliada(boolean mostrar) {
//      if (appBarLayout!=null) {
        appBarLayout.setExpanded(mostrar);
//      }
        toggle.setDrawerIndicatorEnabled(mostrar);
//      tabs.setVisibility(mostrar ? View.VISIBLE : View.GONE);
        if (mostrar) {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            tabs.setVisibility(View.VISIBLE);
        } else {
            tabs.setVisibility(View.GONE);
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }

    public void ponFragmentIzquierdo(Fragment fragment) {
        int idContenedor = dosFragments ? R.id.contenedor_izquierdo : R.id.contenedor_pequeno;
        getSupportFragmentManager().beginTransaction().add(idContenedor, fragment).commit();
    }

    public void reemplazaFragmentIzquierdo(Fragment fragment) {
        int idContenedor = dosFragments ? R.id.contenedor_izquierdo : R.id.contenedor_pequeno;
        getSupportFragmentManager().beginTransaction().replace(idContenedor, fragment)
                .addToBackStack(null).commit();
    }


}













