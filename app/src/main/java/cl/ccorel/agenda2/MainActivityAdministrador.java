package cl.ccorel.agenda2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import cl.ccorel.agenda2.FragmentosAdministrador.CreaServicio;
import cl.ccorel.agenda2.FragmentosAdministrador.EditaServicio;
import cl.ccorel.agenda2.FragmentosAdministrador.EliminaServicio;
import cl.ccorel.agenda2.FragmentosAdministrador.InicioServicio;
import cl.ccorel.agenda2.FragmentosAdministrador.RegistraProveedor;


public class MainActivityAdministrador extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_administrador);

        Toolbar toolbar = findViewById(R.id.toolbarA);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout_A);

        NavigationView navigationView = findViewById(R.id.nav_viewA);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //llamado a login usuarios
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();


        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerA,
                    new InicioServicio()).commit();
            navigationView.setCheckedItem(R.id.InicioServicio);
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.InicioServicio:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerA, new InicioServicio()).commit();
                break;

            case R.id.CreaServicio:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerA, new CreaServicio()).commit();
                break;

            case R.id.EditaServicio:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerA, new EditaServicio()).commit();
                 break;

            case R.id.EliminaServicio:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerA, new EliminaServicio()).commit();
                break;

            case R.id.Salir:
                CerrarSesion();
                break;
            //temporal borrar, debe estar en su propio fragment
            case R.id.RegistraProveedor:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_containerA, new RegistraProveedor()).commit();
                break;


        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void ComprobandoInicioSesion() {
        if (user!= null) {
            //si el proveedor a iniciado sesion
            Toast.makeText(this,"Se ha iniciado sesion",Toast.LENGTH_SHORT).show();
        }else{
            //sino a inciado sesion es pq usuarios es cliente
            startActivity(new Intent(MainActivityAdministrador.this, MainActivity.class));
            finish();
        }

    }

    private void CerrarSesion() {
        firebaseAuth.signOut();
        startActivity(new Intent(MainActivityAdministrador.this,MainActivity.class));
        Toast.makeText(this, "Sesion Cerrada Exitosamente", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        ComprobandoInicioSesion();
        super.onStart();
    }
}