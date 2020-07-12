package com.example.pruebacitixen.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pruebacitixen.Models.Noticia;
import com.example.pruebacitixen.R;
import com.squareup.picasso.Picasso;

public class DetalleNoticia extends AppCompatActivity {
    private Noticia noticia;
    private TextView txt_detalle_noticia, txt_titular_noticia_detalle;
    private ImageView img_noticia_detalle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_noticia);
        txt_titular_noticia_detalle = findViewById(R.id.txt_titular_noticia_detalle);
        txt_detalle_noticia = findViewById(R.id.txt_detalle_noticia);
        img_noticia_detalle = findViewById(R.id.img_noticia_detalle);
        int id_noticia = getIntent().getIntExtra("id_noticia", 0);
        if(id_noticia != 0){
            noticia = Noticia.Find(this, id_noticia);
            txt_titular_noticia_detalle.setText(noticia.getNombreTitular());
            txt_detalle_noticia.setText(noticia.getDetalle());
            Picasso.get()
                    .load(noticia.getImagen())
                    //.resize(70,70)
                    .placeholder(R.drawable.sin_imagen)
                    //.transform(new CropCircleTransformation())
                    .into(img_noticia_detalle);
        }
    }
}
