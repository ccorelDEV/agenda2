package cl.ccorel.agenda2.FragmentosAdministrador;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.lang.ref.Reference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import cl.ccorel.agenda2.MainActivity;
import cl.ccorel.agenda2.MainActivityAdministrador;
import cl.ccorel.agenda2.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the S factory method to
 * create an instance of this fragment.
 **/
public class RegistraProveedor extends Fragment {

    TextView DateRegister;
    EditText Email, Password, Rut, Name, LastName;

    Button Register;

    //para crear la cuenta por medio de un email
    FirebaseAuth auth;

    //animacion en el boton registrar
    ProgressDialog progressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registra_proveedor, container, false);

        DateRegister = view.findViewById(R.id.DateRegister);
        Email = view.findViewById(R.id.Email);
        Password = view.findViewById(R.id.Password);
        Rut = view.findViewById((R.id.RutP));
        Name = view.findViewById(R.id.Name);
        LastName = view.findViewById(R.id.LastName);

        Register = view.findViewById(R.id.Register);
        //inicializando firebase authentication
        auth = FirebaseAuth.getInstance();

        Date date = new Date();
        SimpleDateFormat fecha = new SimpleDateFormat("d' de 'MMMM' del 'yyyy");
        String SFecha = fecha.format(date); //convierte fecha a strng
        DateRegister.setText(SFecha);

        // al click al registrar
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //se convierten a string datos
                String email = Email.getText().toString();
                String pass = Password.getText().toString();
                String rut = Rut.getText().toString();
                String name = Name.getText().toString();
                String lastname = LastName.getText().toString();

                //campos obligatorios
                if (email.equals("") || pass.equals("") || rut.equals("") || name.equals("") || lastname.equals("")){
                        Toast.makeText(getActivity(), "Por favor, complete todos los campos",Toast.LENGTH_SHORT).show();
                }else {
                    //validacion de correo
                    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                        Email.setError("Correo Invalido");
                        Email.setFocusable(true);
                    }
                    else if (pass.length()<6){
                        Password.setError("La contraseña debe ser mayor a 6");
                        Password.setFocusable(true);
                    }else {
                        RegistroProveedor(email, pass);
                    }

                }

            }
        });

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Registrando, espere por favor");
        progressDialog.setCancelable(false);

        return view;
    }
    //metodo para registro de proveedores
    private void RegistroProveedor(String email, String pass) {

        progressDialog.show();
    auth.createUserWithEmailAndPassword(email, pass)
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //si el proveedor se crea correctamente
                if (task.isSuccessful()){
                    progressDialog.dismiss();
                    FirebaseUser user = auth.getCurrentUser();
                    assert user != null;

                    //convierte en cadena los datos de los proveed

                    String UID = user.getUid();
                    String email = Email.getText().toString();
                    String pass = Password.getText().toString();
                    String rut = Rut.getText().toString();
                    String name = Name.getText().toString();
                    String lastname = LastName.getText().toString();

                    //hashmap permite enviar los datos
                    HashMap<Object, Object> Proveedores = new HashMap<>();

                    Proveedores.put("UID",UID);
                    Proveedores.put("EMAIL", email);
                    Proveedores.put("PASSWORD",pass);
                    Proveedores.put("RUT", rut);
                    Proveedores.put("NOMBRE", name);
                    Proveedores.put("LASTNAME", lastname);
                    Proveedores.put("IMAGEN","");

                    //Inicializar firebasedb
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference reference = database.getReference("BD PROVEEDORES");
                    reference.child(UID).setValue(Proveedores);
                    startActivity(new Intent(getActivity(), MainActivityAdministrador.class));
                    Toast.makeText(getActivity() , "Registro Exitoso", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                }
            }
        })
        .addOnFailureListener(new OnFailureListener() {


            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(),""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}