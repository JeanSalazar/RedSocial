package com.example.jksa.redsocial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class LoginActivity extends AppCompatActivity {

    private EditText edt_email,edt_contra;
    private ImageView img_escudo;
    private CheckBox cb_recordar;
    private Button btn_ingresar;
    private MaterialDialog materialDialog;      // Objeto que representa el cuadro de Dialogo

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        //Instanciammos los controles de la Actividad
        edt_email = (EditText)findViewById(R.id.edt_email);
        edt_contra = (EditText)findViewById(R.id.edt_contra);
        img_escudo = (ImageView)findViewById(R.id.img_escudo);
        btn_ingresar = (Button)findViewById(R.id.btn_ingresar);
        cb_recordar = (CheckBox)findViewById(R.id.cb_recordar);

        //Efecto Splash al cargar el Login solo para el icono
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.splash);
        img_escudo.startAnimation(animation);

        //Objeto que guardara las credenciales si el usuario asi lo quiere
        SharedPreferences preferences = getSharedPreferences("REDSOCIAL",getApplicationContext().MODE_PRIVATE);

        //con el siguiente metodo verficamos si hay credenciales guardadas
        leerCredenciales();

        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
    }



    /*Este metodo es el encargado para validar el acceso de los Usuarios a la Aplicacion*/
    public void ingresar(View view) throws InterruptedException {
        mostrarDialogoCargar();
        //Entonces verificamos si los campos ingresados estan llenos
        if(verificarCamposLlenos()){
            //Verificamos por mientras el acceso con claves TEMPORALES despues con una BD

            if(isValidEmail(edt_email.getText().toString()) && validarContraseña()){
                String email = edt_email.getText().toString();
                String password = edt_contra.getText().toString();
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information

                                    Toast.makeText(LoginActivity.this,"Se ingreso correctamente",Toast.LENGTH_SHORT).show();
                                    materialDialog.dismiss();   //Detenemos el dialogo de carga
                                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                    intent.putExtra("uid",auth.getUid());
                                    startActivity(intent);
                                    //finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(LoginActivity.this,"Error credenciales incorrectas",Toast.LENGTH_SHORT).show();
                                    materialDialog.dismiss();   //Detenemos el dialogo de carga
                                }
                            }
                        });
            }
        }
    }


    public final static boolean isValidEmail(CharSequence target){
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public boolean validarContraseña(){
        return !(edt_contra.getText().toString()).isEmpty();
    }

    //Metodo del evento del checbox para guardar las credenciales
    public void guardarCredenciales(View view){
        if(cb_recordar.isChecked() && verificarCamposLlenos()){
            guardarCredenciales(edt_email.getText().toString(),edt_contra.getText().toString());
        }else{
            guardarCredenciales("","");
            cb_recordar.setChecked(false);
        }
    }

    //Metodo para guardar el usuario y password en concreto
    private void guardarCredenciales(String codigo,String contra){
        SharedPreferences sharedPreferences = getPreferences(getApplicationContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("USUARIO",codigo);
        editor.putString("PASSWORD",contra);
        editor.commit();
    }

    private void leerCredenciales(){
        SharedPreferences  sharedPreferences = getPreferences(getApplicationContext().MODE_PRIVATE);
        String USUARIO = sharedPreferences.getString("USUARIO","");
        String PASSWORD = sharedPreferences.getString("PASSWORD","");
        edt_email.setText(USUARIO);
        edt_contra.setText(PASSWORD);
        if(USUARIO.length() != 0 && PASSWORD.length()!=0)
            cb_recordar.setChecked(true);
    }

    private boolean verificarCamposLlenos(){
        if(edt_email.length()==0 || edt_contra.length()==0) {
            Toast.makeText(getApplicationContext(), "Tienes que llenar todos los datos, vuelve a intentarlo", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void mostrarDialogoCargar(){
        //Mostramos un Dialogo de Carga para el Usuario
        materialDialog = new MaterialDialog.Builder(this)
                .title("Validando datos")
                .content("Por favor espere")
                .progress(true, 0)
                .contentGravity(GravityEnum.CENTER)
                .widgetColorRes(R.color.colorPrimary)
                .show();

        materialDialog.setCancelable(false);
        materialDialog.setCanceledOnTouchOutside(false);
    }

    public void abrir_registro(View view){
        Intent intent = new Intent(this,RegistroActivity.class);
        startActivity(intent);
    }



}
