package pe.briane.chattimefirebase.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;
import pe.briane.chattimefirebase.AdapterMensajes;
import pe.briane.chattimefirebase.Entidades.MensajeEnviar;
import pe.briane.chattimefirebase.Entidades.MensajeRecibir;
import pe.briane.chattimefirebase.R;

public class MainActivity extends AppCompatActivity {

    private CircleImageView fotoPerfil;
    private TextView nombre;
    private RecyclerView rvMensajes;
    private EditText txtMensajes;
    private Button btnEnviar;
    private ImageButton btnEnviarFoto;
    private AdapterMensajes adapter;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private static final int PHOTO_SEND=1;
    private  static final int PHOTO_PERFIL=2;
    private String fotoPerfilCadena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fotoPerfil=(CircleImageView) findViewById(R.id.fotoPerfil);
        nombre=(TextView)findViewById(R.id.nombre);
        rvMensajes=(RecyclerView)findViewById(R.id.rvMensajes);
        txtMensajes=(EditText)findViewById(R.id.txtMensaje);
        btnEnviar=(Button)findViewById(R.id.btnEnviar);
        btnEnviarFoto = (ImageButton)findViewById(R.id.btnEnviarFoto);
        database=  FirebaseDatabase.getInstance();
        databaseReference = database.getReference("chat");
        storage=FirebaseStorage.getInstance();
        fotoPerfilCadena="";

        adapter=new AdapterMensajes(this);
        LinearLayoutManager l = new LinearLayoutManager(this);
        rvMensajes.setLayoutManager(l);
        rvMensajes.setAdapter(adapter);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.push().setValue(new MensajeEnviar(txtMensajes.getText().toString(),nombre.getText().toString(),fotoPerfilCadena,"1",ServerValue.TIMESTAMP));
                txtMensajes.setText("");
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
                MensajeRecibir m=dataSnapshot.getValue(MensajeRecibir.class);
                adapter.addMensaje(m);

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

    }

    private void setScrollbar() {
        rvMensajes.scrollToPosition(adapter.getItemCount()-1);
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
                                    MensajeEnviar m = new MensajeEnviar("Piero te ha enviado una foto", nombre.getText().toString(),fotoPerfilCadena, "2", uri.toString(),ServerValue.TIMESTAMP);
                                    databaseReference.push().setValue(m);
                                }
                            });

                        }
                    });
                }else if (requestCode == PHOTO_PERFIL && resultCode == RESULT_OK){
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
                                    MensajeEnviar m = new MensajeEnviar("Piero te ha actualizado su foto perfil", nombre.getText().toString(), fotoPerfilCadena, "2", uri.toString(),ServerValue.TIMESTAMP);
                                    databaseReference.push().setValue(m);
                                    Glide.with(MainActivity.this).load(uri.toString()).into(fotoPerfil);
                                }
                            });

                        }
                    });
                }






        } catch (Exception ex) {
            Toast.makeText(this, "Error" + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
