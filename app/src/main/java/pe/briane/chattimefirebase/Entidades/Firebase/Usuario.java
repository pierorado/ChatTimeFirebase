package pe.briane.chattimefirebase.Entidades.Firebase;

import com.google.firebase.database.ServerValue;

public class Usuario {
    private String nombre;
    private String correo;
    private Object createdTimestamp;

    public Usuario() {
        createdTimestamp = ServerValue.TIMESTAMP;
    }

    public Usuario(String nombre, String correo) {
        this.nombre = nombre;
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Object getCreatedTimestamp() {
        return createdTimestamp;
    }


}
