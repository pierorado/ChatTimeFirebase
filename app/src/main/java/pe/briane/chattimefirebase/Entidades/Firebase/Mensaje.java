package pe.briane.chattimefirebase.Entidades.Firebase;

import com.google.firebase.database.ServerValue;

public class Mensaje {
    private String mensaje;
    private String keyEmisor;
    private String urlFoto;
    private boolean contieneFoto;
    private Object createdTimetamp;

    public Mensaje() {
        createdTimetamp = ServerValue.TIMESTAMP;
    }

    public boolean isContieneFoto() {
        return contieneFoto;
    }

    public void setContieneFoto(boolean contieneFoto) {
        this.contieneFoto = contieneFoto;
    }

    public void setCreatedTimetamp(Object createdTimetamp) {
        this.createdTimetamp = createdTimetamp;
    }

    public String getKeyEmisor() {
        return keyEmisor;
    }

    public void setKeyEmisor(String keyEmisor) {
        this.keyEmisor = keyEmisor;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public Object getCreatedTimetamp() {
        return createdTimetamp;
    }

}
