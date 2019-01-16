package mobile.una.com.flowledge;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
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

import mobile.una.com.flowledge.model.AreaData;
import mobile.una.com.flowledge.model.Question;
import mobile.una.com.flowledge.model.Sesion;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReplyFragment extends Fragment {
    View v;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Sesion s = new Sesion();
    Question q = new Question();
    private List<Question> listquestion = new ArrayList<Question>();
    Question question;
    // Variables de la clase
    private ArrayList<TitularItems> Items;
    private Adaptador Adaptador;
    private ListView listaItems;

    private ArrayList<Question> pruebas;
    ImageView cora;
     TextView titleforo;

    public ReplyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.activity_list, container, false);
        Bundle bundle = getArguments();
        if(bundle != null){
            q = (Question) bundle.getSerializable("question");
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
        listaPreguntas();
        /*   pruebas = new ArrayList<Question>();
        Question q1 = new Question(listquestion.get(0).getUserNickname(), listquestion.get(0).getDescription());
        Question q2 = new Question("barco03","como se recorre un arbol?");


        pruebas.add(q1);
        pruebas.add(q2);

        //fromateaPreguntas();
        Adapter adapter = new Adapter(getApplicationContext(),pruebas);
        listaItems.setAdapter(adapter);*/
    }

    private void init() {
        question = new Question();
        listaItems = (ListView) v.findViewById(R.id.listItems);
        cora = (ImageView) v.findViewById(R.id.imglike);
        titleforo = (TextView) v.findViewById(R.id.txtTitulo);

       titleforo.setText("Preguntas relacionadas con "+q.getCategory());


    }

    private void inicializarFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void listaPreguntas() {
        databaseReference.child("Pregunta").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listquestion.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    question = objSnapshot.getValue(Question.class);
                    if(question.getCategory().equals(q.getCategory())) {
                        listquestion.add(question);
                        //Toast.makeText(getContext(), listquestion.get(0).getDescription(), Toast.LENGTH_SHORT).show();
                    }
                }
                Adapter adapter = new Adapter(getContext(), listquestion);
                listaItems.setAdapter(adapter);
                listaItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final ImageView imagen = (ImageView) view.findViewById(R.id.imglike);
                        final ImageView imagen2 = (ImageView) view.findViewById(R.id.imgcomenta);
                        // Toast.makeText(getApplicationContext(), "vamoo bien", Toast.LENGTH_SHORT).show();
                        imagen.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                imagen.setImageResource(R.drawable.cora2);
                            }
                        });
                        //-----------------------------------------------------------------------

                        imagen2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(getContext(), "vamoo bien", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getContext(), AnswerActivity.class);
                                //intent.putExtra("wea", weather);
                                startActivity(intent);
                            }
                        });

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    public void fromateaPreguntas() {
        for (int i = 0; i < listquestion.size(); i++) {
            question = new Question(listquestion.get(i).getUserNickname(), listquestion.get(i).getDescription());
            pruebas.add(question);
        }

    }

    // Método cargar Items
    private void loadItems() {
        Items = new ArrayList<>(); // Creamos un objeto ArrayList de tipo TitularItems
        // Agregamos elementos al ArrayList
        TitularItems ito = new TitularItems("usuario", "vcvcxvcxc");
        Items.add(ito);
        //   Items.add(new TitularItems("Desempeño", "Descripción de Desempeño", this.getResources().getIdentifier("camara", "drawable", this.getPackageName())));
        // Items.add(new TitularItems("Google Plus", "Descripción de Google Plus", this.getResources().getIdentifier("camara", "drawable", this.getPackageName())));
        // Creamos un nuevo Adaptador y le pasamos el ArrayList
        Adaptador = new Adaptador(getActivity(), Items);
        // Desplegamos los elementos en el ListView
        listaItems.setAdapter(Adaptador);
    }

}
