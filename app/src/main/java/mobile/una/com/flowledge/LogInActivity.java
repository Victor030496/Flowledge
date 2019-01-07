package mobile.una.com.flowledge;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mobile.una.com.flowledge.model.Persona;
import mobile.una.com.flowledge.model.Sesion;


public class LogInActivity extends AppCompatActivity {
    private List<Persona> listapersona = new ArrayList<Persona>();
    LinearLayout principal , formularioin , formularioregistrar;
    EditText pid,pnombre,correo,pcontra,pcocontra,cedulain2,passwordin;
    Persona p=new Persona();
    Sesion s=new Sesion();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String androidId;

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
        androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

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
                break;
            }else{bandera=true;}
        }
        if(bandera){
            bandera=validacionregistro();
        if(bandera){
        p= new Persona(pid.getText().toString(),pnombre.getText().toString(),correo.getText().toString(),pcontra.getText().toString());
            databaseReference.child("Persona").child(p.getPid()).setValue(p);
            s.setPid(androidId);
            s.setNombre(pid.getText().toString());
            s.setEstado("1");
            databaseReference.child("Sesion").child(s.getPid()).setValue(s);

        Toast.makeText(getApplicationContext(), "REGISTRADO CON EXITO", Toast.LENGTH_SHORT).show();
        limpiar();
            Intent intent2 = new Intent(LogInActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NO_ANIMATION);
            intent2.putExtra("S", s);
            startActivity(intent2);
            LogInActivity.this.finish();
        }}else{ limpiar();}

    }

    public void cancelarin(View view){
        setTitle("LOG IN");
        principal.setVisibility(View.VISIBLE);
        formularioin.setVisibility(View.GONE);
        limpiar();
    }

    public void ingresar(View view){
        boolean bandera=false;
        for(int i=0;i <= listapersona.size() - 1;i++){
            if((listapersona.get(i).getPid().equals(cedulain2.getText().toString())) && (listapersona.get(i).getContra().equals(passwordin.getText().toString()))){
                Toast.makeText(getApplicationContext(), "INGRESO EXITOSO", Toast.LENGTH_SHORT).show();
                s.setPid(androidId);
                s.setNombre(cedulain2.getText().toString());
                s.setEstado("1");
                databaseReference.child("Sesion").child(s.getPid()).setValue(s);
                bandera=true;
                break;
            }else{
                Toast.makeText(getApplicationContext(), "ID O CONTRASENA INCORRECTOS", Toast.LENGTH_SHORT).show();}
        }
        if(bandera){
            limpiar();
            Intent intent2 = new Intent(LogInActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NO_ANIMATION);
            intent2.putExtra("S", s);
            startActivity(intent2);
            LogInActivity.this.finish();
        }else{
            limpiar();
        }

    }

    private void limpiar(){
        pid.setText("");
        pnombre.setText("");
        correo.setText("");
        pcontra.setText("");
        pcocontra.setText("");
        cedulain2.setText("");
        passwordin.setText("");
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

    private boolean validacionregistro(){
        if(pid.getText().toString().equals("") || pnombre.getText().toString().equals("") ||correo.getText().toString().equals("") ||pcocontra.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "ESPACIOS VACIOS", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Patrón para validar el email
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher mather = pattern.matcher(correo.getText().toString());

        if (mather.find() == true) {
            String[] parts = correo.getText().toString().split("@");
            String part1 = parts[0];
            String part2 = parts[1];
            if((!part2.equals("est.una.ac.cr")) && (!part2.equals("una.cr"))){
                Toast.makeText(LogInActivity.this,"SOLO CORREOS INSTITUCIONALES DE LA UNA", Toast.LENGTH_SHORT).show();return false;
            }else { System.out.println("El email ingresado es válido.");
            }

        } else {
            Toast.makeText(LogInActivity.this,"EL CORREO ES INVALIDO", Toast.LENGTH_SHORT).show();return false;
        }

        if(!pcontra.getText().toString().equals(pcocontra.getText().toString())){
            Toast.makeText(getApplicationContext(), "LAS CONTRASENAS SON DIFERENTES", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
