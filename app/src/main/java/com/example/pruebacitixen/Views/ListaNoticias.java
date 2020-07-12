package com.example.pruebacitixen.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;

import com.example.pruebacitixen.Controllers.ListarNoticiasService;
import com.example.pruebacitixen.R;

public class ListaNoticias extends AppCompatActivity {
    public static RecyclerView recyclerView_noticias;
    public static Activity activity_main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_noticias);
        recyclerView_noticias = (RecyclerView) findViewById(R.id.recycler_noticias);
        recyclerView_noticias.setLayoutManager(new LinearLayoutManager(this));
        activity_main = this;

        ListarNoticiasService service = new ListarNoticiasService(ListaNoticias.this);
        service.execute();
    }
}
