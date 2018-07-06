package com.example.jksa.redsocial;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jksa.redsocial.Clases.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistroActivity extends AppCompatActivity {

    private EditText nombre,correo,contra,contra_repe;
    private Button btn_registro,btn_salir;

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        nombre = (EditText)findViewById(R.id.edt_registro_nombre);
        correo = (EditText)findViewById(R.id.edt_registro_correo);
        contra = (EditText)findViewById(R.id.edt_registro_contra);
        contra_repe = (EditText)findViewById(R.id.edt_registro_contra_repe);
        btn_registro = (Button)findViewById(R.id.btn_registro);
        btn_salir = (Button)findViewById(R.id.btn_salir);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();



        btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = correo.getText().toString();
                String contraseña = contra.getText().toString();
                if(isValidEmail(email) && validarContraseña() && validarNombre()){
                    auth.createUserWithEmailAndPassword(email, contraseña)
                            .addOnCompleteListener(RegistroActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(RegistroActivity.this,"Se registro correctamente",Toast.LENGTH_SHORT).show();

                                        Usuario u = new Usuario();
                                        u.setCorreo(email);
                                        u.setNombre(nombre.getText().toString());
                                        u.setFoto_perfil("");

                                        FirebaseUser currentUser = auth.getCurrentUser();
                                        DatabaseReference reference = database.getReference("Usuarios/"+currentUser.getUid());
                                        reference.setValue(u);

                                        database = FirebaseDatabase.getInstance();

                                        finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(RegistroActivity.this,"Error al registrarte",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }else{
                    Toast.makeText(RegistroActivity.this,"Los datos Ingresados son Incorrectos",Toast.LENGTH_SHORT).show();
                    limpiarControles();
                }
            }
        });


        btn_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public final static boolean isValidEmail(CharSequence target){
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public boolean validarContraseña(){
        String contraseña,contraseñaRepetida;
        contraseña = contra.getText().toString();
        contraseñaRepetida = contra_repe.getText().toString();

        if(contraseña.equals(contraseñaRepetida)){
            if(contraseña.length()>=6 && contraseña.length()<=16){
                return true;
            }else {
                return false;
            }
        }
        return false;
    }


    public boolean validarNombre(){
        return !(nombre.getText().toString()).isEmpty();
    }


    private void limpiarControles(){
        nombre.setText("");
        correo.setText("");
        contra.setText("");
        contra_repe.setText("");

    }
}
