package cl.ccorel.agenda2.FragmentosCliente;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import cl.ccorel.agenda2.InicioSesion;
import cl.ccorel.agenda2.R;

public class InicioCliente extends Fragment{

    Button Acceder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inicio_cliente, container, false);

        Acceder =  view.findViewById(R.id.Acceder);

        Acceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //v.1
                //startActivity(new Intent(getActivity(), InicioSesion.class));
                //v.02
                Intent intent = new Intent(getActivity(),InicioSesion.class);
                startActivity(intent);
            }
        });
        return view;
    }
}