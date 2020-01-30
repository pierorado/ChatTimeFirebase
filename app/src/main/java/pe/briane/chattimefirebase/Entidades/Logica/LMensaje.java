package pe.briane.chattimefirebase.Entidades.Logica;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import pe.briane.chattimefirebase.Entidades.Firebase.Mensaje;

public class LMensaje {
    private Mensaje mensaje;
    private String key;
    private LUsuario lUsuario;

    public long getCreatedTimetamp(){
        return (long)mensaje.getCreatedTimetamp();
    }
    public LMensaje(Mensaje mensaje, String key) {
        this.mensaje = mensaje;
        this.key = key;
    }

    public Mensaje getMensaje() {
        return mensaje;
    }

    public void setMensaje(Mensaje mensaje) {
        this.mensaje = mensaje;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public LUsuario getlUsuario() {
        return lUsuario;
    }

    public void setlUsuario(LUsuario lUsuario) {
        this.lUsuario = lUsuario;
    }
    public String FechadeCreacionDelMensaje(){
        Date date = new Date(getCreatedTimetamp());
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        return sdf.format(date);
    }
}
