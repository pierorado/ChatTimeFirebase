package pe.briane.chattimefirebase.Entidades.Logica;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import pe.briane.chattimefirebase.Entidades.Firebase.Usuario;

public class LUsuario {
    private String key;
    private Usuario usuario;

    public LUsuario(String key, Usuario usuario) {
        this.key = key;
        this.usuario = usuario;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public long getCreatedTimestampLong(){
        return (long)usuario.getCreatedTimestamp();

    }
    public String obtenerFechaDeCreacion(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
        Date date=new Date(getCreatedTimestampLong());
        return simpleDateFormat.format(date);
    }


}
