package pe.briane.chattimefirebase.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import pe.briane.chattimefirebase.R;

public class LoginActivity extends AppCompatActivity {
    private EditText txtContraseña,txtCorreo;
    private Button btnLogin,btnRegistrar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_login);
        txtContraseña=(EditText)findViewById(R.id.idContraseñaLogin);
        txtCorreo=(EditText)findViewById(R.id.idCorreoLogin);
        btnLogin=(Button)findViewById(R.id.idLoginLogin);
        btnRegistrar=(Button)findViewById(R.id.idRegistroLogin);

        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo=txtCorreo.getText().toString();
                if (isValidEmail(correo) && validarContraseña()){
                    String contraseña=txtContraseña.getText().toString();
                    mAuth.createUserWithEmailAndPassword(correo, contraseña)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(LoginActivity.this, "Se logeo correctamente.", Toast.LENGTH_SHORT).show();
                                        newActivity();
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Error al registrarse.", Toast.LENGTH_SHORT).show();

                                    }

                                    // ...
                                }
                            });
                }else {
                    Toast.makeText(LoginActivity.this, "Validaciones funcionando.", Toast.LENGTH_SHORT).show();

                }
            }
        });
    btnRegistrar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(LoginActivity.this,RegistroActivity.class));
        }
    });


    }
    private boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
    public boolean validarContraseña(){
        String contraseña;
        contraseña = txtContraseña.getText().toString();
            if(contraseña.length()>=6 && contraseña.length()<=16){
                return true;
            }else return false;

    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser!=null){
            Toast.makeText(this,"usuario logeado",Toast.LENGTH_SHORT).show();
            newActivity();
        }
    }
    public void newActivity(){
        startActivity(new Intent(LoginActivity.this,MainActivity.class));

    }
}
