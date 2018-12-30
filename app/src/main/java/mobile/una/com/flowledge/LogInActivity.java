package mobile.una.com.flowledge;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import mobile.una.com.flowledge.model.Persona;


public class LogInActivity extends AppCompatActivity {
    private List<Persona> listapersona = new ArrayList<Persona>();
    LinearLayout principal , formularioin , formularioregistrar;
    EditText pid,pnombre,correo,pcontra,pcocontra,cedulain2,passwordin;
    Persona p=new Persona();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("LOG IN");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        principal = (LinearLayout) findViewById(R.id.principal);
        formularioin = (LinearLayout) findViewById(R.id.formularioin);
        formularioregistrar=(LinearLayout) findViewById(R.id.formularioregistrar);
        pid=findViewById(R.id.cedula2);
        pnombre=findViewById(R.id.nombre2);
        correo=findViewById(R.id.correo2);
        pcontra=findViewById(R.id.password);
        pcocontra=findViewById(R.id.concontrasena);
        cedulain2=findViewById(R.id.cedula2in);
        passwordin=findViewById(R.id.passwordin);

        inicializarFirebase();
        listaPersona();
    }

public void logIn(View view){
    setTitle("LOG IN");
    principal.setVisibility(View.GONE);
    formularioin.setVisibility(View.VISIBLE);
}


public void registrar(View view){
    setTitle("REGISTRARSE");
    principal.setVisibility(View.GONE);
    formularioregistrar.setVisibility(View.VISIBLE);

}

    public void cancelar(View view){
        setTitle("LOG IN");
        principal.setVisibility(View.VISIBLE);
        formularioregistrar.setVisibility(View.GONE);
        limpiar();
    }

    public void guardar(View view){
        boolean bandera=true;
        for(int i=0;i <= listapersona.size() - 1;i++){
            if(listapersona.get(i).getPid().equals(pid.getText().toString())){
                Toast.makeText(getApplicationContext(), "YA ESTA REGISTRADO", Toast.LENGTH_SHORT).show();
                bandera=false;
            }else{bandera=true;}
        }
        if(bandera){
        p= new Persona(pid.getText().toString(),pnombre.getText().toString(),correo.getText().toString(),pcocontra.getText().toString(),pcocontra.getText().toString());
        databaseReference.child("Persona").child(p.getPid()).setValue(p);
        Toast.makeText(getApplicationContext(), "REGISTRADO CON EXITO", Toast.LENGTH_SHORT).show();
        limpiar();
        startActivity(new Intent(getBaseContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NO_ANIMATION));
        finish();}else{limpiar();}

    }

    public void cancelarin(View view){
        setTitle("LOG IN");
        principal.setVisibility(View.VISIBLE);
        formularioin.setVisibility(View.GONE);
        limpiar();
    }

    public void ingresar(View view){

       // System.out.println("Pruebaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"+listapersona.get(0).toString());

    }

    private void limpiar(){
        pid.setText("");
        pnombre.setText("");
        correo.setText("");
        pcontra.setText("");
        pcocontra.setText("");
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
                System.out.println("PRUEBAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAADE LISTA");
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
