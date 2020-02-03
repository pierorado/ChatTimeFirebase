package pe.briane.chattimefirebase.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import pe.briane.chattimefirebase.Entidades.Logica.LMensaje;
import pe.briane.chattimefirebase.Entidades.Logica.LUsuario;
import pe.briane.chattimefirebase.Holder.MensajeriaHolder;
import pe.briane.chattimefirebase.Persistencia.UsuarioDAO;
import pe.briane.chattimefirebase.R;

public class MensajeriaAdaptador extends RecyclerView.Adapter<MensajeriaHolder> {
    List<LMensaje> listMensaje =new ArrayList<>();
    private Context c;

    public MensajeriaAdaptador(Context c) {
        this.c = c;
    }
    public int addMensaje(LMensaje lMensaje){
        listMensaje.add(lMensaje);
        int posicion = listMensaje.size()-1;
        notifyItemInserted(listMensaje.size());
        return posicion;
    }
    public void actualizarMensaje(int posicion,LMensaje lMensaje){
        listMensaje.set(posicion,lMensaje);
        notifyItemChanged(posicion);
    }

    @NonNull
    @Override
    public MensajeriaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType==1){
            view=LayoutInflater.from(c).inflate(R.layout.card_view_mensajes_emisor,parent,false);
        }else {
            view=LayoutInflater.from(c).inflate(R.layout.card_view_mensajes_receptor,parent,false);
        }

        return new MensajeriaHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MensajeriaHolder holder, int position) {

        LMensaje lMensaje = listMensaje.get(position);
        LUsuario lUsuario = lMensaje.getlUsuario();
        if (lUsuario!=null){
            holder.getNombre().setText(lUsuario.getUsuario().getNombre());
            Glide.with(c).load(lUsuario.getUsuario().getFotoPerfilURL()).into(holder.getFotoMensajePerfil());

        }

        holder.getMensaje().setText(lMensaje.getMensaje().getMensaje());
        if (lMensaje.getMensaje().isContieneFoto()){
            holder.getFotoMensaje().setVisibility(View.VISIBLE);
            holder.getMensaje().setVisibility(View.VISIBLE);
            Glide.with(c).load(lMensaje.getMensaje().getUrlFoto()).into(holder.getFotoMensaje());

        }else {
            holder.getFotoMensaje().setVisibility(View.GONE);
            holder.getMensaje().setVisibility(View.VISIBLE);
        }


        holder.getHoras().setText(lMensaje.FechadeCreacionDelMensaje());

    }

    @Override
    public int getItemCount() {
        return listMensaje.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (listMensaje.get(position).getlUsuario()!=null){
            if (listMensaje.get(position).getlUsuario().getKey().equals(UsuarioDAO.getInstance().getKeyUsuario())){
                return 1;
            }else {
                return -1;
            }
        }else {
            return -1;
        }

        //return super.getItemViewType(position);
    }
}
