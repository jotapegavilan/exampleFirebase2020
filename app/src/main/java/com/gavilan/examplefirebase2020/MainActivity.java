package com.gavilan.examplefirebase2020;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.gavilan.examplefirebase2020.entidades.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    EditText txtNombre;
    Spinner spGenero;
    Button btnCrear, btnActualizar, btnEliminar;
    RecyclerView recyclerUsuarios;
    // FIREBASE
    FirebaseDatabase database;
    DatabaseReference reference;
    // AYUDANTES PARA CARGAR RECYCLER
    ArrayList<Usuario> arrayListUsuaios;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // REFERENCIAS
        txtNombre = findViewById(R.id.txtNombre);
        spGenero = findViewById(R.id.spGenero);
        btnCrear = findViewById(R.id.btnCrear);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnEliminar = findViewById(R.id.btnEliminar);
        recyclerUsuarios = findViewById(R.id.recyclerUsuarios);
        //CONFIGURAR RECYLCER
        recyclerUsuarios.setLayoutManager(new LinearLayoutManager(this));
        //INICIALIZAR EL ARRAYLIST
        arrayListUsuaios = new ArrayList<>();
        // Conexión a firebase
        conectarFirebase();
        //CARGAR RECYCLER
        cargarRecycler();

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // CAPTURAR LOS DATOS DE LA UI
                String id = UUID.randomUUID().toString();
                String nombre = txtNombre.getText().toString().trim();
                String genero = spGenero.getSelectedItem().toString();
                // GENERAR EL OBJETO USUARIO
                Usuario usuario = new Usuario(id,nombre,genero);
                insertarUsuario(usuario);
            }
        });
    }

    public void conectarFirebase(){
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        Toast.makeText(this,"Conectado a firebase",Toast.LENGTH_LONG).show();
    }

    public void insertarUsuario(Usuario u){
        if(u != null){
            reference.child("usuarios").child(u.getId()).setValue(u, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    Toast.makeText(getApplicationContext(), "Usuario creado",Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void cargarRecycler(){
        reference.child("usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayListUsuaios.clear();
                for(DataSnapshot dato : snapshot.getChildren()){
                    usuario = dato.getValue(Usuario.class);
                    arrayListUsuaios.add(usuario);
                }
                // CREACIÓN DEL ADAPTADOR
                UsuariosAdapter adapter = new UsuariosAdapter(arrayListUsuaios);
                // ESTABLECER DICHO ADAPTADOR EN EL RECYCLER
                recyclerUsuarios.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}