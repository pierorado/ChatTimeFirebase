package pe.briane.chattimefirebase.Entidades;

import pe.briane.chattimefirebase.Entidades.Mensaje;

public class MensajeRecibir extends Mensaje {
    private Long hora;

    public MensajeRecibir() {
    }

    public MensajeRecibir(Long hora) {
        this.hora = hora;
    }

    public MensajeRecibir(String mensaje, String nombre, String fotoPerfil, String type_mensaje, String urlFoto, Long hora) {
        super(mensaje, nombre, fotoPerfil, type_mensaje, urlFoto);
        this.hora = hora;
    }

    public Long getHora() {
        return hora;
    }

    public void setHora(Long hora) {
        this.hora = hora;
    }
}
