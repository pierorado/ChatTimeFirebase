package pe.briane.chattimefirebase.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import pe.briane.chattimefirebase.Entidades.Firebase.Usuario;
import pe.briane.chattimefirebase.Persistencia.UsuarioDAO;
import pe.briane.chattimefirebase.R;

public class MenuActivity extends AppCompatActivity {

    private Button btnVerUsuario;
    private Button btnCerrarSesion;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnVerUsuario=findViewById(R.id.btnVerUsuario);
        btnCerrarSesion=findViewById(R.id.menucerrar);

        btnVerUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MenuActivity.this,VerUsuariosActivity.class);
                startActivity(intent);

            }
        });
        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                returnLogind();
            }
        });


    }
    private void returnLogind(){
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
    }
}
