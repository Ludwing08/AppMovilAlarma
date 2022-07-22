package dev.android.appalarma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;

import dev.android.appalarma.clases.GlobalVariables;

public class MainActivity extends AppCompatActivity {

    Button btn;
    EditText user, pass;

    //IniciarSesion?usuario={usuario}&clave={clave}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btnLogin);
        user = findViewById(R.id.txtUser);
        pass = findViewById(R.id.txtPassword);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validar("http://www.aplicacionesabrahm.somee.com/ServiciosApp.svc/UsuarioNombreUsu?nombreUsuario="+user.getText());
                login("http://www.aplicacionesabrahm.somee.com/ServiciosApp.svc/IniciarSesion?usuario="+user.getText()+"&clave="+pass.getText());
            }
        });

    }

    private void abrir(){
        Intent intent = new Intent(this, BotonActivity.class);
        startActivity(intent);
    }

    private void login(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if(response.equals("true") && !GlobalVariables.rolUser.equals("Admin")){
                        GlobalVariables.cedula=user.getText().toString();
                        abrir();
                    }else{
                        Toast.makeText(MainActivity.this, "Usuario o contraseña incorrecta", Toast.LENGTH_SHORT).show();
                    }
                    JSONObject jsonObject = new JSONObject(response);
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

    private void validar(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    GlobalVariables.rolUser=jsonObject.getString("ROL_USU");
                    GlobalVariables.idEmpresa= jsonObject.getString("ID_EMPRESA");
                    GlobalVariables.idUser = jsonObject.getString("ID_USU");
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