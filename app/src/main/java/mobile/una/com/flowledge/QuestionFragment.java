package mobile.una.com.flowledge;


import android.support.v4.app.Fragment.*;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
//import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.support.v7.app.AppCompatActivity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mobile.una.com.flowledge.model.AreaData;
import mobile.una.com.flowledge.model.Persona;
import mobile.una.com.flowledge.model.Question;
import mobile.una.com.flowledge.model.Sesion;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionFragment extends Fragment  {
    View v;
    private List<Persona> listapersona = new ArrayList<Persona>();
    ImageView camera;
    Question question;
    // Respuesta r;
    EditText category;
    EditText description;
    Spinner spinner;
    Button send;
    String prueSpinner;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Sesion s = new Sesion();

    public QuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_question, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null){
            s = (Sesion) bundle.getSerializable("sesion");
        }
        this.v = view;
        init();
    }

    @Override
    public void onStart() {
        super.onStart();
        inicializarFirebase();
        listaPersona();
    }

    private void init(){
        camera = (ImageView) v.findViewById(R.id.camara);
        //category = findViewById(R.id.categoria2);
        description = (EditText) v.findViewById(R.id.descripcion2);
        spinner = (Spinner) v.findViewById(R.id.spinner);
        send = (Button) v.findViewById(R.id.btn_enviar);
        String[] letra = {"", "Estructura de datos", "POO", "Bases de Datos", "Redes", "Otros"};
        spinner.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, letra));
        question = new Question("gfdgfd", "gfgd", "gfdgd");
        // some Listeners

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upLoadImage();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                //  Toast.makeText(adapterView.getContext(),
                //        (String) adapterView.getItemAtPosition(pos), Toast.LENGTH_SHORT).show();
                // saveQuestion( (String) adapterView.getItemAtPosition(pos));
                prueSpinner = (String) adapterView.getItemAtPosition(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveQuestion();
                //  Intent intent2 = new Intent(getApplicationContext(), ReplyActivity.class);
                //intent.putExtra("wea", weather);
                //   startActivity(intent2);
            }
        });
    }

    private void inicializarFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void listaPersona() {
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

    public String getNicknameUser() {
        String nombre = "";
        for (int i = 0; i < listapersona.size(); i++) {
            if (listapersona.get(i).getPid().equals(s.getNombre())) {
                nombre = listapersona.get(i).getNombre();
                break;
            } else {
            }
        }
        return nombre;
    }

    public void saveQuestion() {
        // String categoria ;
        String nickname = getNicknameUser();

        // p= new Persona(pid.getText().toString(),pnombre.getText().toString(),correo.getText().toString(),pcontra.getText().toString());
        //databaseReference.child("Persona").child(p.getPid()).setValue(p);

        question.setUserNickname(nickname);
        question.setCategory(prueSpinner);
        question.setDescription(description.getText().toString());
        // question.setRespuesta(respuesta);
        databaseReference.child("Pregunta2").child(question.getDescription()).setValue(question);

        description.setText("");

        Toast.makeText(getContext(), "Haz enviado una pregunta al foro", Toast.LENGTH_SHORT).show();
        // Intent intent2 = new Intent(getActivity(), ReplyFragment.class);
        //intent.putExtra("wea", weather);
       // startActivity(intent2);
        // PROBANDO CAMBIAR DE FRAGMENTS
        Fragment selectFragment = null;
        Bundle bundle = new Bundle();
        bundle.putSerializable("question", (Serializable) question);
      //  bundle.putSerializable("sesion", (Serializable) s);
        selectFragment = new ReplyFragment();
        selectFragment.setArguments(bundle);
       getFragmentManager().beginTransaction().replace(R.id.fragment_container,selectFragment).commit();

       // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
         //       selectFragment).commit();

    }

    public void upLoadImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Seleccione una app"), 10);
    }



}
