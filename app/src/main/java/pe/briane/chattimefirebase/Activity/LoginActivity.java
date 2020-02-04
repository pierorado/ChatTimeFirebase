package pe.briane.chattimefirebase.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pe.briane.chattimefirebase.Entidades.Firebase.Usuario;
import pe.briane.chattimefirebase.Persistencia.UsuarioDAO;
import pe.briane.chattimefirebase.R;

public class LoginActivity extends AppCompatActivity implements Response.Listener<JSONObject>,Response.ErrorListener{
    private EditText password,number;
    private Spinner document;
    private Button btnLogin,btnRegistrar;

    RequestQueue rq;
    JsonRequest jrq;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_login);
        document=(Spinner)findViewById(R.id.Spindocument);
        number=(EditText)findViewById(R.id.Editnumber);
        password=(EditText)findViewById(R.id.Editpassword);
        btnLogin=(Button)findViewById(R.id.idLoginLogin);
        rq = Volley.newRequestQueue(this);

        document.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT).show();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciarSesion();
            }
        });
    /*btnRegistrar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(LoginActivity.this,RegistroActivity.class));
        }
    });
        //UsuarioDAO.getInstance().añadirFotoDePerfilALosUsuariosQueNoTienenFoto();
        */


    }

    private void iniciarSesion() {
        String url ="https://www.briane.pe/service/serv_login.php?profile_id_numero="+number.getText().toString()+
                "&clave="+password.getText().toString()+"&tipo="+document.getSelectedItemPosition()+"";
        Log.d("respuesta",url);
        jrq = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        rq.add(jrq);
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this,"Credenciales incorrectas ",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Usuario usuario= new Usuario();

        JSONArray jsonArray= response.optJSONArray("conductores");
        JSONObject jsonObject = null;
        try {
            jsonObject = jsonArray.getJSONObject(0);
            usuario.setNombre(jsonObject.optString("nombre"));
            usuario.setCorreo(jsonObject.optString("correo"));
            usuario.setClave(jsonObject.optString("clave"));

        }catch (JSONException e ){
            e.printStackTrace();
        }
        Intent intent = new Intent(this,MenuActivity.class);
        intent.putExtra(MenuActivity.nombres,usuario.getNombre());
        intent.putExtra(MenuActivity.correos,usuario.getCorreo());
        intent.putExtra(MenuActivity.claves,usuario.getClave());

        startActivity(intent);

    }
}
