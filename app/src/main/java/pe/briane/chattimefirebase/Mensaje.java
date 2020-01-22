package pe.briane.chattimefirebase;

public class Mensaje {
    private String mensaje;
    private String nombre;
    private String fotoPerfil;
    private String type_mensaje;
    private String horas;
    private String urlFoto;

    public Mensaje() {
    }


    public Mensaje(String mensaje, String nombre, String fotoPerfil, String type_mensaje, String horas, String urlFoto) {
        this.mensaje = mensaje;
        this.nombre = nombre;
        this.fotoPerfil = fotoPerfil;
        this.type_mensaje = type_mensaje;
        this.horas = horas;
        this.urlFoto = urlFoto;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String getType_mensaje() {
        return type_mensaje;
    }

    public void setType_mensaje(String type_mensaje) {
        this.type_mensaje = type_mensaje;
    }

    public String getHoras() {
        return horas;
    }

    public void setHoras(String horas) {
        this.horas = horas;
    }
}
