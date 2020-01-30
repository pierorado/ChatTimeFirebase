package pe.briane.chattimefirebase.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;
import pe.briane.chattimefirebase.Adaptadores.MensajeriaAdaptador;
import pe.briane.chattimefirebase.Entidades.Firebase.Mensaje;
import pe.briane.chattimefirebase.Entidades.Firebase.Usuario;
import pe.briane.chattimefirebase.Entidades.Logica.LMensaje;
import pe.briane.chattimefirebase.Persistencia.UsuarioDAO;
import pe.briane.chattimefirebase.R;

public class MensajeriaActivity extends AppCompatActivity {

    private CircleImageView fotoPerfil;
    private TextView nombre;
    private RecyclerView rvMensajes;
    private EditText txtMensajes;
    private Button btnEnviar , btncerrarSesion;
    private ImageButton btnEnviarFoto;
    private MensajeriaAdaptador adapter;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private static final int PHOTO_SEND=1;
    private  static final int PHOTO_PERFIL=2;
    private String fotoPerfilCadena;
    private FirebaseAuth mAuth;
    private String NOMBRE_USUARIO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensajeria);

        fotoPerfil=(CircleImageView) findViewById(R.id.fotoPerfil);
        nombre=(TextView)findViewById(R.id.nombre);
        rvMensajes=(RecyclerView)findViewById(R.id.rvMensajes);
        txtMensajes=(EditText)findViewById(R.id.txtMensaje);
        btnEnviar=(Button)findViewById(R.id.btnEnviar);
        btncerrarSesion=(Button)findViewById(R.id.cerrarSesion);
        btnEnviarFoto = (ImageButton)findViewById(R.id.btnEnviarFoto);
        database=  FirebaseDatabase.getInstance();

        databaseReference = database.getReference("chat");
        fotoPerfilCadena="";
        nombre.setText(NOMBRE_USUARIO);

        mAuth=FirebaseAuth.getInstance();
        storage=FirebaseStorage.getInstance();
        adapter=new MensajeriaAdaptador(this);
        LinearLayoutManager l = new LinearLayoutManager(this);
        rvMensajes.setLayoutManager(l);
        rvMensajes.setAdapter(adapter);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String MensajeEnviar = txtMensajes.getText().toString();
                if (!MensajeEnviar.isEmpty()){
                    Mensaje mensaje=new Mensaje();
                    mensaje.setMensaje(MensajeEnviar);
                    mensaje.setKeyEmisor(UsuarioDAO.getInstance().getKeyUsuario());
                    databaseReference.push().setValue(mensaje);
                    txtMensajes.setText("");
                }

            }
        });
        btncerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                returnLogind();
            }
        });


        btnEnviarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/jpeg");
                i.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
                startActivityForResult(Intent.createChooser(i,"Seleccione una foto"),PHOTO_SEND);
            }
        });
        fotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/jpeg");
                i.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
                startActivityForResult(Intent.createChooser(i,"Seleccione una foto"),PHOTO_PERFIL);
            }
        });
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                setScrollbar();
            }
        });

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //agragar de base de datos a lista
                Mensaje mensaje=dataSnapshot.getValue(Mensaje.class);
                LMensaje lMensaje=new LMensaje(mensaje,dataSnapshot.getKey());
                adapter.addMensaje(lMensaje);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        verifyStoragePermissions(this);
    }

    private void setScrollbar() {
        rvMensajes.scrollToPosition(adapter.getItemCount()-1);
    }
    public static boolean verifyStoragePermissions(Activity activity) {
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        int REQUEST_EXTERNAL_STORAGE = 1;
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
            return false;
        }else{
            return true;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
                    if (requestCode == PHOTO_SEND && resultCode == RESULT_OK){
                    Uri u = data.getData();
                    storageReference = storage.getReference("image_chat");
                    final StorageReference fotoReference = storageReference.child(u.getLastPathSegment());
                    fotoReference.putFile(u).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fotoReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Mensaje mensaje = new Mensaje();
                                    mensaje.setMensaje("te ha enviado una foto");
                                    mensaje.setUrlFoto(uri.toString());
                                    mensaje.setContieneFoto(true);
                                    mensaje.setKeyEmisor(UsuarioDAO.getInstance().getKeyUsuario());
                                    databaseReference.push().setValue(mensaje);
                                }
                            });

                        }
                    });
                }/*else if (requestCode == PHOTO_PERFIL && resultCode == RESULT_OK){
                    Uri u = data.getData();
                    storageReference = storage.getReference("foto_perfil");
                    final StorageReference fotoReference = storageReference.child(u.getLastPathSegment());
                    fotoReference.putFile(u).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fotoReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    fotoPerfilCadena=uri.toString();
                                    MensajeEnviar m = new MensajeEnviar(NOMBRE_USUARIO+" ha actualizado su foto perfil", nombre.getText().toString(), fotoPerfilCadena, "2", uri.toString(),ServerValue.TIMESTAMP);
                                    databaseReference.push().setValue(m);
                                    Glide.with(MensajeriaActivity.this).load(uri.toString()).into(fotoPerfil);
                                }
                            });

                        }
                    });
                }*/






        } catch (Exception ex) {
            Toast.makeText(this, "Error" + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser!=null){
            btnEnviar.setEnabled(false);
            DatabaseReference reference = database.getReference("Usuarios/"+currentUser.getUid());
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Usuario usuario=dataSnapshot.getValue(Usuario.class);
                    NOMBRE_USUARIO= usuario.getNombre();
                    btnEnviar.setEnabled(true);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }else {
            returnLogind();
        }
    }

    private void returnLogind(){
        startActivity(new Intent(MensajeriaActivity.this,LoginActivity.class));
        finish();
    }

}