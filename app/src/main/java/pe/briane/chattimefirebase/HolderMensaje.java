package pe.briane.chattimefirebase;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class HolderMensaje extends RecyclerView.ViewHolder {
    private TextView nombre;
    private TextView mensaje;
    private TextView horas;
    private CircleImageView fotoMensajePerfil;
    private ImageView fotoMensaje;

    public HolderMensaje(@NonNull View itemView) {
        super(itemView);
        nombre=(TextView)itemView.findViewById(R.id.nombreMensaje);
        mensaje=(TextView)itemView.findViewById(R.id.mensajeMensaje);
        horas=(TextView)itemView.findViewById(R.id.horasMensaje);
        fotoMensajePerfil =(CircleImageView) itemView.findViewById(R.id.fotoPerfilMensaje);
        fotoMensaje=(ImageView)itemView.findViewById(R.id.messageFoto);
    }

    public TextView getNombre() {
        return nombre;
    }

    public void setNombre(TextView nombre) {
        this.nombre = nombre;
    }

    public TextView getMensaje() {
        return mensaje;
    }

    public void setMensaje(TextView mensaje) {
        this.mensaje = mensaje;
    }

    public TextView getHoras() {
        return horas;
    }

    public void setHoras(TextView horas) {
        this.horas = horas;
    }

    public CircleImageView getFotoMensajePerfil() {
        return fotoMensajePerfil;
    }

    public void setFotoMensajePerfil(CircleImageView fotoMensajePerfil) {
        this.fotoMensajePerfil = fotoMensajePerfil;
    }

    public ImageView getFotoMensaje() {
        return fotoMensaje;
    }

    public void setFotoMensaje(ImageView fotoMensaje) {
        this.fotoMensaje = fotoMensaje;
    }
}
