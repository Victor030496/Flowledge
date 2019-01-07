package mobile.una.com.flowledge;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static java.lang.Boolean.TRUE;

/**
 * Created by Luis Bogantes on 06/01/2019.
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
import android.widget.Spinner;
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

public class QuestionActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private List<Persona> listapersona = new ArrayList<Persona>();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ImageView camera;
    Question question;
    EditText category;
    EditText description;
    Sesion s=new Sesion();
    Spinner spinner;
    Button send;
    String prueSpinner;



 //---------------------------------------------------------------------------------//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        setTitle("PREGUNTAS");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        // recovering widgets
        bottomNavigationView = findViewById(R.id.NavBot);
        camera = (ImageView) findViewById(R.id.camara);
        //category = findViewById(R.id.categoria2);
        description = (EditText) findViewById(R.id.descripcion2);
        spinner = (Spinner) findViewById(R.id.spinner);
        send = (Button) findViewById(R.id.btn_enviar);
        String[] letra = {" ------","Listas","Bases de Datos","Redes","Otros"};
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, letra));

        Intent intent = getIntent();
        s = (Sesion) intent.getSerializableExtra("S");


        //-------------------------------------------------------------------------//
        bottomNavigationView.getMenu().getItem(1).setChecked(TRUE);
        question = new Question();
        inicializarFirebase();
        listaPersona();

        // some Listeners

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                upLoadImage();

            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id)
            {
                Toast.makeText(adapterView.getContext(),
                        (String) adapterView.getItemAtPosition(pos), Toast.LENGTH_SHORT).show();
               // saveQuestion( (String) adapterView.getItemAtPosition(pos));
                prueSpinner = (String) adapterView.getItemAtPosition(pos);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {    }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             saveQuestion();
            }
        });




        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {

                            case R.id.bottombaritem_home:
                                Intent intent2 = new Intent(QuestionActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                intent2.putExtra("S", s);
                                startActivity(intent2);
                                QuestionActivity.this.finish();
                                return true;
                            case R.id.bottombaritem_profile:
                                Intent intent3 = new Intent(QuestionActivity.this, UserActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                intent3.putExtra("S", s);
                                startActivity(intent3);
                                QuestionActivity.this.finish();

                                return true;

                            case R.id.bottombaritem_reply:
                                Intent intent4 = new Intent(QuestionActivity.this, ReplyActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                intent4.putExtra("S", s);
                                startActivity(intent4);
                                QuestionActivity.this.finish();
                                return true;

                            case R.id.bottombaritem_question:
                                Intent intent5 = new Intent(QuestionActivity.this, QuestionActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                intent5.putExtra("S", s);
                                startActivity(intent5);
                                QuestionActivity.this.finish();
                                return true;

                        }
                        return false;
                    }
                });

    }






    public void saveQuestion(){
         String categoria ;
         String nickname= getNicknameUser();

       // p= new Persona(pid.getText().toString(),pnombre.getText().toString(),correo.getText().toString(),pcontra.getText().toString());
        //databaseReference.child("Persona").child(p.getPid()).setValue(p);

        question.setUserNickname(nickname);
        question.setCategory(prueSpinner);
        question.setDescription(description.getText().toString());

        databaseReference.child("Pregunta").child(question.getUserNickname()).setValue(question);

        Toast.makeText(getApplicationContext(), "Haz enviado una pregunta al foro", Toast.LENGTH_SHORT).show();

    }



    public void upLoadImage(){

        Intent  intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Seleccione una app"),10);
    }


    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
    }


    private void listaPersona(){
        databaseReference.child("Persona").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listapersona.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                    Persona p = objSnapshot.getValue(Persona.class);
                    listapersona.add(p);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public String getNicknameUser(){
        String nombre="";
        for(int i=0;i < listapersona.size() ;i++){
            if(listapersona.get(i).getPid().equals(s.getNombre())){
                 nombre=listapersona.get(i).getNombre();


                break;
            }else{ }
        }
    return  nombre;

    }

}
