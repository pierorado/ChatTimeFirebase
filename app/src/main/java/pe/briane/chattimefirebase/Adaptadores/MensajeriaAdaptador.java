package pe.briane.chattimefirebase.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pe.briane.chattimefirebase.Entidades.Logica.LMensaje;
import pe.briane.chattimefirebase.Holder.MensajeriaHolder;
import pe.briane.chattimefirebase.R;

public class MensajeriaAdaptador extends RecyclerView.Adapter<MensajeriaHolder> {
    List<LMensaje> listMensaje =new ArrayList<>();
    private Context c;

    public MensajeriaAdaptador(Context c) {
        this.c = c;
    }
    public void addMensaje(LMensaje lMensaje){
        listMensaje.add(lMensaje);
        notifyItemInserted(listMensaje.size());
    }

    @NonNull
    @Override
    public MensajeriaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.card_view_mensajes,parent,false);

        return new MensajeriaHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MensajeriaHolder holder, int position) {
        LMensaje lMensaje = listMensaje.get(position);
        holder.getNombre().setText(lMensaje.getlUsuario().getUsuario().getNombre());
        holder.getMensaje().setText(lMensaje.getMensaje().getMensaje());
        if (lMensaje.getMensaje().isContieneFoto()){
            holder.getFotoMensaje().setVisibility(View.VISIBLE);
            holder.getMensaje().setVisibility(View.VISIBLE);
            Glide.with(c).load(lMensaje.getMensaje().getUrlFoto()).into(holder.getFotoMensaje());

        }else {
            holder.getFotoMensaje().setVisibility(View.GONE);
            holder.getMensaje().setVisibility(View.VISIBLE);
        }
        Glide.with(c).load(lMensaje.getlUsuario().getUsuario().getFotoPerfilURL()).into(holder.getFotoMensajePerfil());


        holder.getHoras().setText(lMensaje.FechadeCreacionDelMensaje());

    }

    @Override
    public int getItemCount() {
        return listMensaje.size();
    }
}
