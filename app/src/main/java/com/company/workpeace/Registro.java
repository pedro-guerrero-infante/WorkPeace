package com.company.workpeace;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class Registro extends AppCompatActivity {
    TextInputLayout nombre;
    TextInputLayout email;
    TextInputLayout clave;
    Button btnRegistrarse;
    Button btnLogin;

    FirebaseDatabase ruta;
    DatabaseReference referencia;
    FirebaseAuth autenticacion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        nombre = findViewById(R.id.nombreRegistro);
        email = findViewById(R.id.emailRegistro);
        clave = findViewById(R.id.claveRegistro);
        btnRegistrarse = findViewById(R.id.btnRegistro);
        autenticacion = FirebaseAuth.getInstance();

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ruta = FirebaseDatabase.getInstance();
                referencia = ruta.getReference("Usuarios");
                String nombres =nombre.getEditText().getText().toString();
                String mail =email.getEditText().getText().toString();
                String contrasenia =clave.getEditText().getText().toString();

                //Verificar que no esté vacio el nombre
                if(TextUtils.isEmpty(nombres)){
                    Toast.makeText(Registro.this,"Ingresa el Nombre completo",Toast.LENGTH_LONG).show();
                    return;
                }
                //Verificar que no esté vacio el mail
                if(TextUtils.isEmpty(mail)){
                    Toast.makeText(Registro.this,"Ingresa el email",Toast.LENGTH_LONG).show();
                    return;
                }
                //Verificar que no esté vacio la clave
                if(TextUtils.isEmpty(contrasenia)){
                    Toast.makeText(Registro.this,"Ingresa la clave",Toast.LENGTH_LONG).show();
                    return;
                }

                //AUTENTICACION DE USUARIOS NUEVOS


                autenticacion.createUserWithEmailAndPassword(mail, contrasenia)
                        .addOnCompleteListener(Registro.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Registro.this,"Se ha registrado satisfactoriamente....",Toast.LENGTH_LONG).show();
                                } else {
                                    if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                        Toast.makeText(Registro.this,"Ese usuario ya existe",Toast.LENGTH_SHORT).show();
                                    }

                                    else{
                                        Toast.makeText(Registro.this,"No se pudo registrar el usuario",Toast.LENGTH_LONG).show();
                                    }


                                }


                                // ...
                            }
                        });


                UsuariosAux aux = new UsuariosAux(nombres,mail,contrasenia);
                referencia.child(nombres).setValue(aux);
                Intent i = new Intent(Registro.this,Perfil.class);
                startActivity(i);

            }
        });



        // PARA DEVOLVERSE A LA PANTALLA LOGIN
        btnLogin = findViewById(R.id.btnPantallaLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(Registro.this,Login.class);
                startActivity(next);
            }
        });





    }
}