package mobile.una.com.flowledge;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import mobile.una.com.flowledge.model.Respuesta;
import mobile.una.com.flowledge.model.Sesion;

/**
 * Created by Luis Bogantes on 08/01/2019.
 */

public class AnswerActivity extends AppCompatActivity {

    private ArrayList<Question> pruebas;
    private ListView listaItems;
    Question q ;
    Respuesta res;
    TextView nick;
    TextView pregu;
    EditText respuEdit;
    Button enviarRes;
    private List<Persona> listapersona = new ArrayList<Persona>();
    Sesion s ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        // Recuperando datos
        inicializarFirebase();
        listaPersona();
        Intent intent = getIntent();
        q = (Question) intent.getSerializableExtra("question");

         s =  (Sesion) intent.getSerializableExtra("sesion");

         res = new Respuesta();

        listaItems = (ListView) findViewById(R.id.listItems2);
        nick = (TextView) findViewById(R.id.txtNickkk);
        pregu = (TextView) findViewById(R.id.txtPreguuu);
        respuEdit = (EditText) findViewById(R.id.respuuuu);
        enviarRes = (Button) findViewById(R.id.btn_enviarRes);


        nick.setText(q.getUserNickname());
        pregu.setText(q.getDescription());

        pruebas = new ArrayList<Question>();
        Question q1 = new Question("samir05", "TCP: protocolo de control de transmision, IP: Protocolo de internet");
        Question q2 = new Question("barco03", "Me parece que esos protocolos tratan sobre transferencia ");
        // Question q3 = new Question(listquestion.get(0).getUserNickname().toString(), listquestion.get(0).getDescription().toString());

        pruebas.add(q1);
        pruebas.add(q2);
        //pruebas.add(q3);

        //fromateaPreguntas();
        Adapter2 adapter = new Adapter2(getApplicationContext(), pruebas);
        listaItems.setAdapter(adapter);


        enviarRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviarRespuesta();
                //  Intent intent2 = new Intent(getApplicationContext(), ReplyActivity.class);
                //intent.putExtra("wea", weather);
                //   startActivity(intent2);
            }
        });
    }





    public void enviarRespuesta(){

      String respuesta =   respuEdit.getText().toString();
      String nicknameResp = getNicknameUser();
      String pregunt = pregu.getText().toString();
      int likes = 0;

        res.setRespuesta(respuesta);
        res.setNickRespuesta(nicknameResp);
        res.setPregunta(pregunt);
        res.setLikes(likes);
      databaseReference.child("Respuestas").child(res.getRespuesta()).setValue(res);

      respuEdit.setText("");



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

    private void inicializarFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
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

}
