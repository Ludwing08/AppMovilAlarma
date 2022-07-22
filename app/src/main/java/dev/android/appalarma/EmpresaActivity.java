package dev.android.appalarma;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import dev.android.appalarma.clases.GlobalVariables;

public class EmpresaActivity extends AppCompatActivity {

    TextView nombre, direccion, telefono;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa);

        nombre = findViewById(R.id.txtNombreEmpresa);
        direccion = findViewById(R.id.txtDireccionEmpresa);
        telefono = findViewById(R.id.txtTelefonoEmpresa);

        cargarData("http://www.aplicacionesabrahm.somee.com/ServiciosApp.svc/DevolverEmpresa?id="+GlobalVariables.idEmpresa);
    }

    private void cargarData(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    nombre.setText(jsonObject.getString("NOMBRE_EMP"));
                    direccion.setText(jsonObject.getString("DIRECCION_EMP"));
                    telefono.setText(jsonObject.getString("TELEFONO_EMP"));
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
}