package com.developer.johhns.audiolibros2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdaptadorLibros extends RecyclerView.Adapter<AdaptadorLibros.ViewHolder> {


    private LayoutInflater inflater ;
    protected List<Libro> listaLibros ;
    private Context contexto ;
    private View.OnClickListener onClickListener ;

    public AdaptadorLibros(Context contexto ,List<Libro> listaLibros ) {
        inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
        this.listaLibros = listaLibros;
        this.contexto = contexto;
    }

    @NonNull
    @Override
    public AdaptadorLibros.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  vista = inflater.inflate(R.layout.elemento_selector,null) ;
        vista.setOnClickListener( onClickListener );
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorLibros.ViewHolder holder, int position) {
        Libro libro = listaLibros.get(position) ;
        holder.portada.setImageResource( libro.recursoImagen );
        holder.titulo.setText( libro.titulo );
    }

    @Override
    public int getItemCount() {
        return listaLibros.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView portada ;
        public TextView titulo  ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            portada = itemView.findViewById(R.id.portada) ;
            titulo  = itemView.findViewById(R.id.titulo) ;

        }
    }

    public void setOnItemClickListener( View.OnClickListener onClickListener ){
        this.onClickListener = onClickListener ;
    }

}

