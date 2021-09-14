package cl.ccorel.agenda2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.jar.Attributes;

public class InicioSesion extends AppCompatActivity {

    EditText Email, Password;
    Button Acceder;

    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        ActionBar actionBar = getSupportActionBar(); //crear el actionbar
        assert actionBar != null;                   // confirmar que el actionbar no sea nulo
        actionBar.setTitle("Inicio Sesion");        //le asignamos un titulo
        actionBar.setDisplayHomeAsUpEnabled(true);  //se habilita boton de retroceso
        actionBar.setDisplayShowHomeEnabled(true);

        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);
        Acceder = findViewById(R.id.Acceder);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(InicioSesion.this);
        progressDialog.setMessage("Ingresando espere por favor");
        progressDialog.setCancelable(false);

        Acceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //se convierten a string datos
                String email = Email.getText().toString();
                String pass = Password.getText().toString();

                //validacion de correo
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Email.setError("Correo Invalido");
                    Email.setFocusable(true);
                } else if (pass.length() < 6) {
                    Password.setError("La contraseña debe ser mayor a 6");
                    Password.setFocusable(true);
                } else {
                    LogeoAdministradores(email, pass);
                }

            }
        });
    }
    private void LogeoAdministradores(String email, String pass) {
        progressDialog.show();
        progressDialog.setCancelable(false);
        firebaseAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(InicioSesion.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //si el inicio fue exitoso
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            //determina desde donde estamos (luego del login) y hacai donde nos lleva
                            startActivity(new Intent(InicioSesion.this, MainActivityAdministrador.class));
                            Toast.makeText(InicioSesion.this, "Bienvenido(a)" + user.getEmail(), Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            progressDialog.dismiss();
                            UsuarioInvalido();

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                UsuarioInvalido();
            }
        });

    }


    private void UsuarioInvalido() {
        AlertDialog.Builder builder = new AlertDialog.Builder(InicioSesion.this);
        builder.setCancelable(false);
        builder.setTitle("Ha ocurrido un error!");
        builder.setMessage("Verifique si el correo o contraseña sean los correctos")
                .setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return InicioSesion.super.onSupportNavigateUp();
    }

}