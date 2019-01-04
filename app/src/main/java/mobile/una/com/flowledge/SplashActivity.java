package mobile.una.com.flowledge;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
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
    DatabaseReference databaseReference2;
    private List<Persona> listapersona = new ArrayList<Persona>();
    private List<Sesion> listapersona2 = new ArrayList<Sesion>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);
        inicializarFirebase();
        listaPersona();
        listapersona2 = Sesion.listAll(Sesion.class);
//        System.out.println("PRUEBAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"+listapersona2.get(0).getPid());


        if(sesion(listapersona2)){
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

    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference2=firebaseDatabase.getReference();
    }

    private void listaPersona(){
        databaseReference2.child("Persona").addValueEventListener(new ValueEventListener() {
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

    private boolean sesion(List<Sesion> listapersona2 ){
        for(int i=0;i <= listapersona.size() - 1;i++){
            if(listapersona.get(i).getPid().equals(listapersona2.get(0).getPid())){
                return true;
            }
        }
        return false;
    }
}