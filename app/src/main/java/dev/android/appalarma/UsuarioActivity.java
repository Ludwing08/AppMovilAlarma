package dev.android.appalarma;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import dev.android.appalarma.clases.GlobalVariables;

public class UsuarioActivity extends AppCompatActivity {

    TextView nombre, apellido, cedula, telefono, rol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
        nombre = findViewById(R.id.txtNombreFrag);
        apellido = findViewById(R.id.txtApeFrag);
        cedula = findViewById(R.id.txtCedFrag);
        telefono = findViewById(R.id.txtTelFrag);
        rol = findViewById(R.id.txtRolFrag);

        cargarDatos("http://www.aplicacionesabrahm.somee.com/ServiciosApp.svc/UsuarioNombreUsu?nombreUsuario="+ GlobalVariables.cedula);
    }

    private void cargarDatos(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    cedula.setText(jsonObject.getString("CEDULA_USU"));
                    nombre.setText(jsonObject.getString("NOMBRE_CLI"));
                    apellido.setText(jsonObject.getString("APELLIDO_CLI"));
                    telefono.setText(jsonObject.getString("TELEFONO_CLI"));
                    rol.setText(jsonObject.getString("ROL_USU"));
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