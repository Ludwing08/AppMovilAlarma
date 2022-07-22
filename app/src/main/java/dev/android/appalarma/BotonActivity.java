package dev.android.appalarma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;


import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import dev.android.appalarma.clases.GlobalVariables;

import static android.content.ContentValues.TAG;

public class BotonActivity extends AppCompatActivity {

    Date fecha = new Date();
    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    ImageView img;

    private static final String ONESIGNAL_APP_ID = "6a126ec3-b70b-4c2a-87ae-22b582cf7ca5";

    static final String TAG = "BotonActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boton);
        cargarData("http://www.aplicacionesabrahm.somee.com/ServiciosApp.svc/DevolverEmpresa?id="+GlobalVariables.idEmpresa);
        img = findViewById(R.id.imageView2);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarma("http://www.aplicacionesabrahm.somee.com/ServiciosApp.svc/NuevaIncidencia?idUsuario="+GlobalVariables.idUser+"&idEmpresa="+GlobalVariables.idEmpresa+"&nombreEmpresa="+GlobalVariables.nombreEmpresa+"&coordenadasEmpresa="+ GlobalVariables.coordenadas +"&fecha="+date+"&estado=Alerta");
                dialog();
               alerta();
            }
        });

    }


    private void alerta(){
        FirebaseMessaging.getInstance().subscribeToTopic("proyecto")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Subscribed";
                        if (!task.isSuccessful()) {
                            msg = "Subscribe failed";
                        }
                        Log.d(TAG, msg);
                        Toast.makeText(BotonActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void dialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(BotonActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog, null);

        builder.setView(view);

        AlertDialog dialog = builder.create();
        dialog.show();

        Button btnok = view.findViewById(R.id.btnOK);
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    private void alarma(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                cargarData("http://www.aplicacionesabrahm.somee.com/ServiciosApp.svc/DevolverEmpresa?id="+GlobalVariables.idEmpresa);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

    private void cargarData(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    GlobalVariables.coordenadas=jsonObject.getString("COORDENADAS_EMP");
                    GlobalVariables.nombreEmpresa= jsonObject.getString("NOMBRE_EMP");

                }catch (JSONException error){

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.opciones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.opDatos:
                Intent intent = new Intent(this, UsuarioActivity.class);
                startActivity(intent);
                return true;
            case R.id.opEmpresa:
                Intent i = new Intent(this, EmpresaActivity.class);
                startActivity(i);
                return true;
            case R.id.opCerrarSesion:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}