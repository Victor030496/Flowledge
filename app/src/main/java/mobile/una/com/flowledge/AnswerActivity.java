package mobile.una.com.flowledge;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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
    Question q;
    Respuesta res;
    TextView nick;
    TextView pregu;
    EditText respuEdit;
    Button enviarRes;
    private List<Persona> listapersona = new ArrayList<Persona>();
    private List<Respuesta> listanswers = new ArrayList<Respuesta>();
    Sesion s;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    int likesAux = 0;
    int aux;
    String aux2;
    String rol = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        // Recuperando datos
        inicializarFirebase();
        listaPersona();
        listaRespuestas();
        Intent intent = getIntent();
        q = (Question) intent.getSerializableExtra("question");

        s = (Sesion) intent.getSerializableExtra("sesion");

        res = new Respuesta();

        listaItems = (ListView) findViewById(R.id.listItems2);
        nick = (TextView) findViewById(R.id.txtNickkk);
        pregu = (TextView) findViewById(R.id.txtPreguuu);
        respuEdit = (EditText) findViewById(R.id.respuuuu);
        enviarRes = (Button) findViewById(R.id.btn_enviarRes);

        nick.setText(q.getUserNickname());
        pregu.setText(q.getDescription());

       /* pruebas = new ArrayList<Question>();
        Question q1 = new Question("samir05", "TCP: protocolo de control de transmision, IP: Protocolo de internet");
        Question q2 = new Question("barco03", "Me parece que esos protocolos tratan sobre transferencia ");
        // Question q3 = new Question(listquestion.get(0).getUserNickname().toString(), listquestion.get(0).getDescription().toString());

        pruebas.add(q1);
        pruebas.add(q2);
        //pruebas.add(q3);

        //fromateaPreguntas();
        Adapter2 adapter = new Adapter2(getApplicationContext(), pruebas);
        listaItems.setAdapter(adapter);*/

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

    public void enviarRespuesta() {
        String respuesta = respuEdit.getText().toString();
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

    // Recuperando respuestas

    private void listaRespuestas() {
        databaseReference.child("Respuestas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listanswers.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    res = objSnapshot.getValue(Respuesta.class);
                    if (res.getPregunta().equals(q.getDescription())) {
                        listanswers.add(res);
                        //Toast.makeText(getContext(), listquestion.get(0).getDescription(), Toast.LENGTH_SHORT).show();
                    }
                }
                // Toast.makeText(getApplicationContext(), getNicknameUser(), Toast.LENGTH_SHORT).show();
                Adapter2 adapter = new Adapter2(AnswerActivity.this, listanswers, getNicknameUser(), rol);
                listaItems.setAdapter(adapter);
                listaItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        final ImageView imagen = (ImageView) view.findViewById(R.id.imglike2);
                        final TextView txtLikes = (TextView) view.findViewById(R.id.txtlikes);
                        final ImageView imagen3 = (ImageView) view.findViewById(R.id.imgTrash2);
                        // Toast.makeText(getApplicationContext(), "vamoo bien", Toast.LENGTH_SHORT).show();

                        // aux = listanswers.get(position).getLikes()+1;
                        //aux2 = String.valueOf(aux);

                        //Respuesta resAux = new Respuesta(listanswers.get(position).getRespuesta(),listanswers.get(position).getNickRespuesta(),listanswers.get(position).getPregunta(),aux);
                        //databaseReference.child("Respuestas").child(listanswers.get(position).getRespuesta()).setValue(resAux);
                        //imagen.setImageResource(R.drawable.cora2);

                        imagen.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                aux = listanswers.get(position).getLikes() + 1;
                                aux2 = String.valueOf(aux);

                                Respuesta resAux = new Respuesta(listanswers.get(position).getRespuesta(), listanswers.get(position).getNickRespuesta(), listanswers.get(position).getPregunta(), aux);
                                databaseReference.child("Respuestas").child(listanswers.get(position).getRespuesta()).setValue(resAux);

                                // txtLikes.setText(aux2);
                                imagen.setImageResource(R.drawable.cora2);
                                txtLikes.setText(aux2);

                            }
                        });
                        //-----------------------------------------------------------------------


                        imagen3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                //String aux =listanswers.get(position).getDescription();
                                databaseReference.child("Respuestas").child(listanswers.get(position).getRespuesta()).removeValue();
                                Toast.makeText(AnswerActivity.this, "Su respuesta ha sido eliminada", Toast.LENGTH_LONG).show();

                            }
                        });


                        //imagen.setImageResource(R.drawable.cora2);


                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    private void listaPersona() {
        databaseReference.child("Persona2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listapersona.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Persona p = objSnapshot.getValue(Persona.class);
                    listapersona.add(p);
                    if (p.getRol().equals("profesor")) {

                        rol = p.getNombre();
                    }
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
        String rol = "";
        for (int i = 0; i < listapersona.size(); i++) {
            if (listapersona.get(i).getPid().equals(s.getNombre())) {
                nombre = listapersona.get(i).getNombre();
                rol = listapersona.get(i).getRol();
                break;
            } else {
            }
        }
        return nombre;
    }


}
