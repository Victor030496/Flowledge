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
    TextView pid, pnombre, correo;
    Persona p = new Persona();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Button cerrarButton;

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
    }

    @Override
    public void onStart() {
        super.onStart();
        inicializarFirebase();
        listarDatos();
    }

    private void init() {
        pnombre = v.findViewById(R.id.profile_name);
        Persona p = getUser();
        pnombre.setText(p.getNombre());
        cerrarButton = v.findViewById(R.id.cerrar_sesion);
        cerrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("Sesion").child(s.getPid()).removeValue();
                Intent intent2 = new Intent(getContext(), SplashActivity.class);
                startActivity(intent2);
                getActivity().finish();
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
                init();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public Persona getUser() {
        Persona p = new Persona();
        for (Persona persona : listapersona) {
            if (persona.getPid().equals(s.getNombre())) {
                p = persona;
                break;
            }
        }
        return p;
    }

}