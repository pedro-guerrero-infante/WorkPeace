package com.company.workpeace;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {
    TextInputLayout correo;
    TextInputLayout psw;
    Button btnRegistro;
    Button btnIniciarSesion;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnRegistro = (Button)findViewById(R.id.btnPantallaRegistro);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesionLogin);
        correo = findViewById(R.id.correoLogin);
        psw = findViewById(R.id.claveLogin);
        auth = FirebaseAuth.getInstance();


        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correos = correo.getEditText().getText().toString();
                String claves = psw.getEditText().getText().toString();


                auth.signInWithEmailAndPassword(correos, claves)
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Login.this,"Bienvenido de vuelta",Toast.LENGTH_SHORT).show();
                                }
                                else if(task.getException() instanceof  FirebaseAuthUserCollisionException){
                                        Toast.makeText(Login.this,"Este usuario ya existe",Toast.LENGTH_SHORT).show();
                                    }
                                else{
                                    Toast.makeText(Login.this,"PAILAS MI PERROO",Toast.LENGTH_SHORT).show();

                                }

                                // ...
                            }
                        });

                Intent sig = new Intent(Login.this,Perfil.class);
                startActivity(sig);
            }
        });







        //CAMBIAR A PANTALLA REGISTRO
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent siguiente = new Intent(Login.this,Registro.class);
                startActivity(siguiente);
            }
        });

    }

}