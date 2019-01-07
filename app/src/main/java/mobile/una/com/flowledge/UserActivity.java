package mobile.una.com.flowledge;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import mobile.una.com.flowledge.model.Persona;
import mobile.una.com.flowledge.model.Sesion;

import static java.lang.Boolean.TRUE;

public class UserActivity extends AppCompatActivity {

    private List<Persona> listapersona = new ArrayList<Persona>();
    ArrayAdapter<Persona> personaArrayAdapter;
    Sesion s=new Sesion();

    private BottomNavigationView bottomNavigationView;
    EditText pid,pnombre;
    ListView lista;
    Button guardar;
    Persona p=new Persona();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        setTitle("USERS");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        bottomNavigationView = findViewById(R.id.NavBot);
        bottomNavigationView.getMenu().getItem(4).setChecked(TRUE);

        pid=findViewById(R.id.id);
        pnombre=findViewById(R.id.nombre);
        lista=findViewById(R.id.datos);
        guardar=findViewById(R.id.guardar);
        Intent intent = getIntent();
        s = (Sesion) intent.getSerializableExtra("S");
        inicializarFirebase();
        listarDatos();



      /*  guardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                p= new Persona();
                String id=pid.getText().toString();
                String nombre=pnombre.getText().toString();
                //p.setPid(UUID.randomUUID().toString());
                p.setPid(id);
                p.setNombre(nombre);
                databaseReference.child("Persona").child(p.getPid()).setValue(p);
               // databaseReference.child("Persona").setValue(p);


            }
        });*/


        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {

                            case R.id.bottombaritem_home:
                                Intent intent2 = new Intent(UserActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                intent2.putExtra("S", s);
                                startActivity(intent2);
                                UserActivity.this.finish();
                                return true;
                            case R.id.bottombaritem_profile:
                                Intent intent3 = new Intent(UserActivity.this, UserActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                intent3.putExtra("S", s);
                                startActivity(intent3);
                                UserActivity.this.finish();

                                return true;

                            case R.id.bottombaritem_reply:
                                Intent intent4 = new Intent(UserActivity.this, ReplyActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                intent4.putExtra("S", s);
                                startActivity(intent4);
                                UserActivity.this.finish();
                                return true;

                            case R.id.bottombaritem_question:
                                Intent intent5 = new Intent(UserActivity.this, QuestionActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                intent5.putExtra("S", s);
                                startActivity(intent5);
                                UserActivity.this.finish();
                                return true;

                        }
                        return false;
                    }
                });

    }

    private void listarDatos() {
        databaseReference.child("Persona").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listapersona.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                    Persona p = objSnapshot.getValue(Persona.class);
                    listapersona.add(p);

                    personaArrayAdapter= new ArrayAdapter<Persona>(UserActivity.this,android.R.layout.simple_list_item_1,listapersona);
                    lista.setAdapter(personaArrayAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
    }


    public void cerrar(View view){
        Intent intent = getIntent();
        s = (Sesion) intent.getSerializableExtra("S");
        databaseReference.child("Sesion").child(s.getPid()).removeValue();
        Intent intent2 = new Intent(UserActivity.this, SplashActivity.class);
        startActivity(intent2);
        UserActivity.this.finish();
    }

}
