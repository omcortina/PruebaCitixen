package com.example.pruebacitixen.Controllers;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.pruebacitixen.BD.Database;
import com.example.pruebacitixen.Models.Noticia;
import com.example.pruebacitixen.Recyclers.RecyclerNoticias;
import com.example.pruebacitixen.Views.ListaNoticias;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ListarNoticiasService extends AsyncTask<Void,Void,String> {
    Context context;
    ProgressDialog progressDialog;
    Boolean error;

    public ListarNoticiasService(Context context) {
        this.context = context;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(this.context,"Noticias","Validando informacion...");
    }

    protected String doInBackground(Void... voids) {

        String uri = "https://newsapi.org/v2/top-headlines?country=co&apiKey=5b73e5db4c3148239441d92dbbb2285a";
        URL url = null;
        try {
            url = new URL(uri);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setReadTimeout(15000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);

            int response_code = urlConnection.getResponseCode();
            if (response_code == HttpURLConnection.HTTP_OK){

                BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String linea = "";
                while ((linea = br.readLine()) != null){
                    sb.append(linea);
                    break;
                }
                br.close();

                String json = "";
                json = sb.toString();

                JSONObject jo = null;
                jo = new JSONObject(json);

                Database admin = new Database(context, "prueba", null, 1);
                SQLiteDatabase db = admin.getWritableDatabase();

                db.execSQL("DELETE FROM Noticia");
                db.close();

                JSONArray json_noticias = null;
                json_noticias = jo.getJSONArray("articles");

                for (int i=0; i<json_noticias.length();i++){
                    JSONObject json_noticia = json_noticias.getJSONObject(i);

                    Noticia noticia = new Noticia();
                    noticia.setNombreTitular(json_noticia.getString("title"));
                    noticia.setImagen(json_noticia.getString("urlToImage"));
                    noticia.setDetalle(json_noticia.getString("content"));
                    noticia.Save(this.context);
                }
                this.error = false;
                return "ok";
            }

        } catch (MalformedURLException e) {
            error = true;
            return "Error de ruta: "+e.getMessage();
        } catch (IOException e) {
            error = true;
            return "Error de conexion a la ruta: "+e.getMessage();
        } catch (JSONException e) {
            error = true;
            return "Error con los parametros del json de envio: "+e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "La URL no es valida";
        }
        return "Ocurrió un error al realizar la solicitud";
    }

    protected void onPostExecute(String respuesta) {
        super.onPostExecute(respuesta);
        progressDialog.dismiss();
        if (respuesta.equals("ok")){
            ListaNoticias.activity_main.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    List<Noticia> lista_noticias = Noticia.FindAll(context);
                    RecyclerNoticias adapter = new RecyclerNoticias(lista_noticias, context);
                    ListaNoticias.recyclerView_noticias.setAdapter(adapter);
                }
            });
        }else{
            Toast.makeText(context, "No se cargaron las noticias correctamente", Toast.LENGTH_SHORT).show();
        }
    }
}
