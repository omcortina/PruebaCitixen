package com.example.pruebacitixen.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pruebacitixen.BD.Database;

import java.util.ArrayList;
import java.util.List;

public class Noticia {
    private int Id;
    private String Imagen, NombreTitular, Detalle;

    public Noticia() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }

    public String getNombreTitular() {
        return NombreTitular;
    }

    public void setNombreTitular(String nombreTitular) {
        NombreTitular = nombreTitular;
    }

    public String getDetalle() {
        return Detalle;
    }

    public void setDetalle(String detalle) {
        Detalle = detalle;
    }

    public void Save(Context context){
        Database admin = new Database(context, "prueba", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put("nombre_titular", this.NombreTitular);
        registro.put("imagen", this.Imagen);
        registro.put("detalle", this.Detalle);
        db.insert("Noticia", null, registro);
        db.close();
    }

    public static Noticia Find(Context context, int id){
        Database bd = new Database(context, "prueba", null, 1);
        SQLiteDatabase db = bd.getWritableDatabase();

        String sql = "select * from Noticia where id_noticia="+id;
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()){
            Noticia noticia = new Noticia();
            noticia.Id = cursor.getInt(cursor.getColumnIndex("id_noticia"));
            noticia.NombreTitular = cursor.getString(cursor.getColumnIndex("nombre_titular"));
            noticia.Imagen = cursor.getString(cursor.getColumnIndex("imagen"));
            noticia.Detalle = cursor.getString(cursor.getColumnIndex("detalle"));
            return noticia;
        }
        db.close();
        return null;
    }

    public static List<Noticia> FindAll(Context context){
        Database bd = new Database(context, "prueba", null, 1);
        SQLiteDatabase db = bd.getWritableDatabase();
        List<Noticia> lista = new ArrayList<>();

        String sql = "select * from Noticia";
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()){
            do{
                Noticia noticia = new Noticia();
                noticia.Id = cursor.getInt(cursor.getColumnIndex("id_noticia"));
                noticia.NombreTitular = cursor.getString(cursor.getColumnIndex("nombre_titular"));
                noticia.Imagen = cursor.getString(cursor.getColumnIndex("imagen"));
                noticia.Detalle = cursor.getString(cursor.getColumnIndex("detalle"));
                lista.add(noticia);
            }while(cursor.moveToNext());
            db.close();
            return lista;
        }
        db.close();
        return null;
    }
}
