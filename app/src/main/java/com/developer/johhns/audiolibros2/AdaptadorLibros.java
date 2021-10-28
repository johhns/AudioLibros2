package com.developer.johhns.audiolibros2;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.util.List;

public class AdaptadorLibros extends RecyclerView.Adapter<AdaptadorLibros.ViewHolder> {


    private LayoutInflater inflater ;
    protected List<Libro> listaLibros ;
    private Context contexto ;
    private View.OnClickListener onClickListener ;
    private View.OnLongClickListener onLongClickListener ;

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
        vista.setOnLongClickListener(onLongClickListener);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorLibros.ViewHolder holder, int position) {
        Libro libro = listaLibros.get(position) ;
        //holder.portada.setImageResource( libro.recursoImagen );
        holder.titulo.setText( libro.titulo );
        Aplicacion aplicacion = (Aplicacion) contexto.getApplicationContext() ;
        aplicacion.getLectorImagenes().get( libro.getUrlImagen() , new ImageLoader.ImageListener(){
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                Bitmap bitmap = response.getBitmap();
                if (bitmap != null) {
                    holder.portada.setImageBitmap(bitmap);
                    Palette palette = Palette.from(bitmap).generate();
                    holder.itemView.setBackgroundColor(palette.getLightMutedColor(0));
                    holder.titulo.setBackgroundColor(palette.getLightVibrantColor(0));
                    holder.portada.invalidate();
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                holder.portada.setImageResource(R.drawable.books);
            }

        } );
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

    public void setOnItemLongClickListener( View.OnLongClickListener onLongClickListener ){
        this.onLongClickListener = onLongClickListener ;
    }

}

