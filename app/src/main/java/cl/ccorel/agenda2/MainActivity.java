package cl.ccorel.agenda2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;


import cl.ccorel.agenda2.FragmentosCliente.AgendaHora;
import cl.ccorel.agenda2.FragmentosCliente.EliminaHora;
import cl.ccorel.agenda2.FragmentosCliente.HorasAgendadas;
import cl.ccorel.agenda2.FragmentosCliente.InicioCliente;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    //private lateinit var binding:ActivityMainBinding
   // private ActivityMainBinding binding;

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       //binding = ActivityMainBinding.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //fragment por defecto
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new InicioCliente()).commit();
            navigationView.setCheckedItem(R.id.InicioCliente);
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.InicioCliente:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new InicioCliente()).commit();
                break;

            case R.id.AgendaHora:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AgendaHora()).commit();
                break;

            case R.id.EliminaHora:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EliminaHora()).commit();
                break;

            case R.id.HorasAgendadas:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HorasAgendadas()).commit();
                break;

            case R.id.Salir:
                Toast.makeText(this, "Cerraste Sesion", Toast.LENGTH_SHORT).show();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}