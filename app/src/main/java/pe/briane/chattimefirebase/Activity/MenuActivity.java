package pe.briane.chattimefirebase.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import pe.briane.chattimefirebase.Entidades.Firebase.Usuario;
import pe.briane.chattimefirebase.Persistencia.UsuarioDAO;
import pe.briane.chattimefirebase.R;
import pe.briane.chattimefirebase.Utilidades.Constantes;

public class MenuActivity extends AppCompatActivity {
    public static final String nombres ="nombre";
    public static final String correos ="correo";
    public static final String claves ="clave";
    private Button btnVerUsuario;
    private Button btnCerrarSesion;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private static final String TAG = "MenuActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        btnVerUsuario=findViewById(R.id.btnVerUsuario);
        btnCerrarSesion=findViewById(R.id.menucerrar);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        final String nombre = getIntent().getStringExtra("nombre");
        final String correo = getIntent().getStringExtra("correo");
        final String clave = getIntent().getStringExtra("clave");


    if (isValidEmail(correo) && validarNombre(nombre)){
        mAuth.createUserWithEmailAndPassword(correo, clave)
                .addOnCompleteListener(MenuActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(MenuActivity.this, "Se registro correctamente.", Toast.LENGTH_SHORT).show();
                            Usuario usuario = new Usuario();
                            usuario.setCorreo(correo);
                            usuario.setNombre(nombre);
                            usuario.setFotoPerfilURL(Constantes.URL_FOTO_POR_DEFECTO);
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            DatabaseReference reference = database.getReference("Usuarios/"+currentUser.getUid());
                            reference.setValue(usuario);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d(TAG, "LA CORREO "+correo);
                            Toast.makeText(MenuActivity.this, "Error al registrarse.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }else {
        Toast.makeText(MenuActivity.this, "Validaciones funcionando.", Toast.LENGTH_SHORT).show();

    }







        btnVerUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MenuActivity.this,VerUsuariosActivity.class);
                startActivity(intent);

            }
        });
       /* btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                returnLogind();
            }
        });*/


    }
    private boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public boolean validarNombre(String nombre){
        return !nombre.isEmpty();
    }
    /*  private void returnLogind(){
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (UsuarioDAO.getInstance().isUsuarioLogeado()){

        }else {
            returnLogind();
        }
    }*/
}
