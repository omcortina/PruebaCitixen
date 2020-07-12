package com.example.pruebacitixen.Recyclers;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pruebacitixen.Models.Noticia;
import com.example.pruebacitixen.R;
import com.example.pruebacitixen.Views.DetalleNoticia;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerNoticias extends RecyclerView.Adapter<RecyclerNoticias.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_titular_noticia, txt_detalle;
        private ImageView img_noticia;
        private CardView card_noticia;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_noticia = itemView.findViewById(R.id.img_noticia_detalle);
            txt_titular_noticia = itemView.findViewById(R.id.txt_titular_noticia);
            card_noticia = itemView.findViewById(R.id.card_noticia);
        }
    }

    public List<Noticia> lista_noticias;
    public Context context;

    public RecyclerNoticias(List<Noticia> lista_noticias, Context context) {
        this.lista_noticias = lista_noticias;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_noticia,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Noticia noticia = lista_noticias.get(position);
        holder.txt_titular_noticia.setText(noticia.getNombreTitular());
        Picasso.get()
                .load(noticia.getImagen())
                //.resize(70,70)
                .placeholder(R.drawable.sin_imagen)
                //.transform(new CropCircleTransformation())
                .into(holder.img_noticia);
        holder.card_noticia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id_noticia = noticia.getId();
                Intent noticia = new Intent(context, DetalleNoticia.class);
                noticia.putExtra("id_noticia", id_noticia);
                context.startActivity(noticia);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista_noticias.size();
    }
}
