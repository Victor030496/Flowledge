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
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.EditText;
import android.widget.ImageView;
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

        import mobile.una.com.flowledge.MainActivity;
        import mobile.una.com.flowledge.R;
        import mobile.una.com.flowledge.UserActivity;
        import mobile.una.com.flowledge.model.Persona;
import mobile.una.com.flowledge.model.Question;

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




 //---------------------------------------------------------------------------------//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        setTitle("Preguntas");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        // recovering widgets
        bottomNavigationView = findViewById(R.id.NavBot);
        camera = findViewById(R.id.camara);
        category = findViewById(R.id.categoria2);
        description = findViewById(R.id.descripcion2);


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

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {

                            case R.id.bottombaritem_home:
                                startActivity(new Intent(getBaseContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NO_ANIMATION));
                                finish();
                                return true;
                            case R.id.bottombaritem_profile:
                                finish();
                                overridePendingTransition(0, 0);
                                startActivity(new Intent(getBaseContext(), UserActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NO_ANIMATION));
                                overridePendingTransition(0, 0);
                                return true;

                            case R.id.bottombaritem_reply:
                                finish();
                                overridePendingTransition(0, 0);
                                startActivity(new Intent(getBaseContext(), ReplyActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NO_ANIMATION));
                                overridePendingTransition(0, 0);
                                return true;

                        }
                        return false;
                    }
                });

    }




    public void saveQuestion(){



        // question.setUserNickname();



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

}
