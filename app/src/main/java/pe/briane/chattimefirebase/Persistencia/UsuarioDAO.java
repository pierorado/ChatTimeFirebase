package pe.briane.chattimefirebase.Persistencia;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import pe.briane.chattimefirebase.Entidades.Firebase.Usuario;
import pe.briane.chattimefirebase.Entidades.Logica.LUsuario;
import pe.briane.chattimefirebase.Utilidades.Constantes;

public class UsuarioDAO {
    private FirebaseDatabase database;
    private static UsuarioDAO usuarioDAO;
    private DatabaseReference referenceUsuarios;

    public  static UsuarioDAO getInstance(){
        if (usuarioDAO==null) usuarioDAO=new UsuarioDAO();
        return usuarioDAO;
    }
    private UsuarioDAO(){
        database=FirebaseDatabase.getInstance();
        referenceUsuarios=database.getReference(Constantes.NODO_USUARIOS);
    }
    public  String getKeyUsuario(){
        return FirebaseAuth.getInstance().getUid();
    }
    public void añadirfotodeperfilausuariossinfotodeperfil(){
        referenceUsuarios.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<LUsuario> IUsuariosLista = new ArrayList<>();
                for (DataSnapshot chilDataSnapShot : dataSnapshot.getChildren()){
                    Usuario usuario=chilDataSnapShot.getValue(Usuario.class);
                    LUsuario lUsuario = new LUsuario(chilDataSnapShot.getKey(),usuario);
                    IUsuariosLista.add(lUsuario);
                }
                for (LUsuario lUsuario : IUsuariosLista){
                    if (lUsuario.getUsuario().getFotoPerfilURL()==null){
                        referenceUsuarios.child(lUsuario.getKey()).child("fotoPerfilURL").setValue(Constantes.URL_FOTO_POR_DEFECTO);
                    }
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
