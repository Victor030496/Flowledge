package mobile.una.com.flowledge;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

import mobile.una.com.flowledge.model.Question;

/**
 * Created by Luis Bogantes on 08/01/2019.
 */

public class AnswerActivity extends AppCompatActivity {

    private ArrayList<Question> pruebas;
    private ListView listaItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        listaItems = (ListView) findViewById(R.id.listItems2);

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
    }

}
