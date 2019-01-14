package mobile.una.com.flowledge;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import mobile.una.com.flowledge.model.Persona;
import mobile.una.com.flowledge.model.Question;
import mobile.una.com.flowledge.model.Sesion;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserProfileFragment extends Fragment {
    View v;
    private List<Persona> listapersona = new ArrayList<Persona>();
    Sesion s = new Sesion();
    LinearLayout informacion, boton;
    private BottomNavigationView bottomNavigationView;
    TextView pid, pnombre, correo;
    Button mostrarButton;
    Button cerrarButton;
    Button esconderButton;
    Persona p = new Persona();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public UserProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_userprofile, container, false);
        Bundle bundle = getArguments();
        if(bundle != null){
            s = (Sesion) bundle.getSerializable("sesion");
        }
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.v = view;
        init();
    }

    @Override
    public void onStart() {
        super.onStart();
        inicializarFirebase();
        listarDatos();
    }

    private void init() {
        informacion = (LinearLayout) v.findViewById(R.id.informacion);
        boton = (LinearLayout) v.findViewById(R.id.Boton);
        pid = v.findViewById(R.id.cedula2);
        pnombre = v.findViewById(R.id.nombre2);
        correo = v.findViewById(R.id.correo2);
        cerrarButton = v.findViewById(R.id.cerrar_button);
        mostrarButton = v.findViewById(R.id.mostrar_button);
        esconderButton = v.findViewById(R.id.esconder_button);
        cerrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("Sesion").child(s.getPid()).removeValue();
                Intent intent2 = new Intent(getContext(), SplashActivity.class);
                startActivity(intent2);
                getActivity().finish();
            }
        });
        mostrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Persona pe = getUser();
                informacion.setVisibility(View.VISIBLE);
                boton.setVisibility(View.GONE);
                pid.setText(pe.getPid());
                pnombre.setText(pe.getNombre());
                correo.setText(pe.getCorreo());
            }
        });
        esconderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Persona pe = getUser();
                informacion.setVisibility(View.GONE);
                boton.setVisibility(View.VISIBLE);
                pid.setText(pe.getPid());
                pnombre.setText(pe.getNombre());
                correo.setText(pe.getCorreo());
            }
        });
    }

    private void inicializarFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void listarDatos() {
        databaseReference.child("Persona").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listapersona.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Persona p = objSnapshot.getValue(Persona.class);
                    listapersona.add(p);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public Persona getUser() {
        Persona p = new Persona();
        for (int i = 0; i < listapersona.size(); i++) {
            if (listapersona.get(i).getPid().equals(s.getNombre())) {
                p = listapersona.get(i);
                break;
            }
        }
        return p;
    }

}
