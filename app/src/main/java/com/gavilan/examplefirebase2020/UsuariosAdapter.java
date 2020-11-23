package com.gavilan.examplefirebase2020;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gavilan.examplefirebase2020.entidades.Usuario;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UsuariosAdapter extends RecyclerView.Adapter<UsuariosAdapter.viewHolderDatos> {
    ArrayList<Usuario> usuarios;
    Usuario usuario;

    public UsuariosAdapter(ArrayList<Usuario> usuarios){
        this.usuarios = usuarios;
    }

    @NonNull
    @Override
    public UsuariosAdapter.viewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.usuario_item,parent,false);
        viewHolderDatos vhd = new viewHolderDatos(view);
        return vhd;
    }

    @Override
    public void onBindViewHolder(@NonNull UsuariosAdapter.viewHolderDatos holder, int position) {
        holder.cargarUsuario(usuarios.get(position));
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    public class viewHolderDatos extends RecyclerView.ViewHolder {
        ImageView imgGenero;
        TextView txtNombreUsuario;
        public viewHolderDatos(@NonNull View itemView) {
            super(itemView);
            //REFERENCIA
            imgGenero = itemView.findViewById(R.id.imgGenero);
            txtNombreUsuario = itemView.findViewById(R.id.txtNombreUsuario);
        }
        public void cargarUsuario(Usuario usuario){
            txtNombreUsuario.setText(usuario.getNombre());
            if(usuario.getGenero().equals("Femenino")){
                Picasso.get().load(R.drawable.woman).into(imgGenero);
            }else if(usuario.getGenero().equals("Masculino")){
                Picasso.get().load(R.drawable.male).into(imgGenero);
            }else{
                Picasso.get().load(R.drawable.question).into(imgGenero);
            }
        }


    }
}
