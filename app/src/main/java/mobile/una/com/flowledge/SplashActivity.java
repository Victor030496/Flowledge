package mobile.una.com.flowledge;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
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
import mobile.una.com.flowledge.model.Sesion;

public class SplashActivity extends Activity {
    private final int DURACION_SPLASH = 1000;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    List<Persona> listapersona = new ArrayList<Persona>();
    Sesion s=new Sesion();
    boolean bandera=false;
    List<String> id;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        //Guardar la informacion de la sesion activa
        s=Sesion.findById(Sesion.class, 1);

        //Inicializar firebase
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();

        //Obtener los datos de firebase
        databaseReference.child("Persona").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listapersona.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                    Persona p = objSnapshot.getValue(Persona.class);
                    listapersona.add(p);

                }

            //Validar que este la sesion activa
                for(int i=0;i <= listapersona.size() - 1;i++){
                    if(s!=null){
                    if(listapersona.get(i).toString().equals(s.toString())){
                        bandera= true;
                        break;
                    }}else{bandera=false;}
                }

                if(bandera){
                    new Handler().postDelayed(new Runnable(){
                        public void run(){
                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        };
                    }, DURACION_SPLASH);
                }else{
                    new Handler().postDelayed(new Runnable(){
                        public void run(){
                            Intent intent = new Intent(SplashActivity.this, LogInActivity.class);
                            startActivity(intent);
                            finish();
                        };
                    }, DURACION_SPLASH);

                }


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

}