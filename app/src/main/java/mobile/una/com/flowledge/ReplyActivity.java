package mobile.una.com.flowledge;

/**
 * Created by Luis Bogantes on 28/12/2018.
 */

import android.support.v7.app.AppCompatActivity;
//package mobile.una.com.flowledge;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.design.widget.BottomNavigationView;
        import android.support.v7.app.AppCompatActivity;
        import android.view.MenuItem;
        import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
        import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

        import java.util.ArrayList;
        import java.util.List;
        import java.util.UUID;

import mobile.una.com.flowledge.MainActivity;
import mobile.una.com.flowledge.R;
import mobile.una.com.flowledge.UserActivity;
import mobile.una.com.flowledge.model.Persona;
import mobile.una.com.flowledge.model.Question;
import mobile.una.com.flowledge.model.Sesion;

import static java.lang.Boolean.TRUE;
public class ReplyActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Sesion s=new Sesion();
    private List<Question> listquestion = new ArrayList<Question>();
   Question question;
    // Variables de la clase
    private ArrayList<TitularItems> Items;
    private Adaptador Adaptador;
    private ListView listaItems;

    private ArrayList<Question> pruebas;
    ImageView cora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
     //   bottomNavigationView = findViewById(R.id.NavBot);
       // bottomNavigationView.getMenu().getItem(2).setChecked(TRUE);
        Intent intent = getIntent();
        s = (Sesion) intent.getSerializableExtra("S");
       question = new Question();

//--------------------------------------------------------------
        inicializarFirebase();
        listaPreguntas();

        // Vinculamos el objeto ListView con el objeto del archivo XML
        listaItems = (ListView)findViewById(R.id.listItems);
        cora =(ImageView)findViewById(R.id.imglike);




     /*   pruebas = new ArrayList<Question>();
        Question q1 = new Question("samir05","que es un bst?");
        Question q2 = new Question("barco03","como se recorre un arbol?");
       // Question q3 = new Question(listquestion.get(0).getUserNickname().toString(), listquestion.get(0).getDescription().toString());


        pruebas.add(q1);
        pruebas.add(q2);
        //pruebas.add(q3);

        //fromateaPreguntas();
        Adapter adapter = new Adapter(getApplicationContext(),pruebas);
        listaItems.setAdapter(adapter);*/















        // some listeners
    /*    bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {

                            case R.id.bottombaritem_home:
                                Intent intent2 = new Intent(ReplyActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                intent2.putExtra("S", s);
                                startActivity(intent2);
                                ReplyActivity.this.finish();
                                return true;
                            case R.id.bottombaritem_profile:
                                Intent intent3 = new Intent(ReplyActivity.this, UserActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                intent3.putExtra("S", s);
                                startActivity(intent3);
                                ReplyActivity.this.finish();

                                return true;

                            case R.id.bottombaritem_reply:
                                Intent intent4 = new Intent(ReplyActivity.this, ReplyActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                intent4.putExtra("S", s);
                                startActivity(intent4);
                                ReplyActivity.this.finish();
                                return true;

                            case R.id.bottombaritem_question:
                                Intent intent5 = new Intent(ReplyActivity.this, QuestionActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                intent5.putExtra("S", s);
                                startActivity(intent5);
                                ReplyActivity.this.finish();
                                return true;
                        }
                        return false;
                    }
                });*/

    }





    @Override
    protected void onStart() {

       super.onStart();
     /*   pruebas = new ArrayList<Question>();
        Question q1 = new Question(listquestion.get(0).getUserNickname(), listquestion.get(0).getDescription());
        Question q2 = new Question("barco03","como se recorre un arbol?");


        pruebas.add(q1);
        pruebas.add(q2);

        //fromateaPreguntas();
        Adapter adapter = new Adapter(getApplicationContext(),pruebas);
        listaItems.setAdapter(adapter);*/



    }














    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
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
                    listquestion.add(question);
                    Toast.makeText(getApplicationContext(), listquestion.get(0).getDescription(), Toast.LENGTH_SHORT).show();

                }


                Adapter adapter = new Adapter(getApplicationContext(),listquestion);


                listaItems.setAdapter(adapter);



                listaItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                       final ImageView imagen =(ImageView) view.findViewById(R.id.imglike);
                        final ImageView imagen2 =(ImageView) view.findViewById(R.id.imgcomenta);
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
                                Toast.makeText(getApplicationContext(), "vamoo bien", Toast.LENGTH_SHORT).show();
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


    public void fromateaPreguntas(){

        for(int i =0; i < listquestion.size();i++) {

            question = new Question(listquestion.get(i).getUserNickname(), listquestion.get(i).getDescription());
            pruebas.add(question);


        }

    }



    // Método cargar Items
    private void loadItems(){
        Items = new ArrayList<>(); // Creamos un objeto ArrayList de tipo TitularItems

// Agregamos elementos al ArrayList


        TitularItems ito = new TitularItems("usuario", "vcvcxvcxc");
        Items.add(ito);
     //   Items.add(new TitularItems("Desempeño", "Descripción de Desempeño", this.getResources().getIdentifier("camara", "drawable", this.getPackageName())));
       // Items.add(new TitularItems("Google Plus", "Descripción de Google Plus", this.getResources().getIdentifier("camara", "drawable", this.getPackageName())));

// Creamos un nuevo Adaptador y le pasamos el ArrayList
       Adaptador = new Adaptador(this, Items);
// Desplegamos los elementos en el ListView
        listaItems.setAdapter(Adaptador);
    }









}
