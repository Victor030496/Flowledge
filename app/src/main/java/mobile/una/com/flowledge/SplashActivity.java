package mobile.una.com.flowledge;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
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
    List<Sesion> listapersona2 = new ArrayList<Sesion>();
    Sesion s=new Sesion();
    boolean bandera=false;
    List<String> id;
    String androidId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        //Inicializar firebase
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();

        //Guardar los datos en las listas
        databaseReference.child("Persona").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listapersona.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                    Persona p = objSnapshot.getValue(Persona.class);
                    listapersona.add(p);

                }
                databaseReference.child("Sesion").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        listapersona2.clear();
                        for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                            Sesion g = objSnapshot.getValue(Sesion.class);
                            listapersona2.add(g);

                        }

                        for(int i=0;i <= listapersona2.size() - 1;i++){
                            if(listapersona2.get(i).getPid().equals(androidId)){
                                bandera= true;
                            }
                        }


                        if(bandera){
                            bandera=false;
                            for(int i=0;i <= listapersona.size() - 1;i++){
                                for(int j=0;i <= listapersona2.size() - 1;j++) {
                                   // if((listapersona2.get(j).getNombre().equals(""))&&(listapersona2.get(j).getEstado().equals("0"))){ bandera= false;}

                                    if((listapersona.get(i).toString().equals(listapersona2.get(j).getNombre())) && (listapersona2.get(j).getEstado().equals("1"))) {
                                        bandera= true;
                                        s=listapersona2.get(j);
                                        break;
                                    }
                                }

                            }

                            if (bandera) {

                                new Handler().postDelayed(new Runnable() {
                                    public void run() {
                                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                                        intent.putExtra("S", s);
                                        startActivity(intent);
                                        SplashActivity.this.finish();
                                    }

                                    ;
                                }, DURACION_SPLASH);
                            } else {
                                new Handler().postDelayed(new Runnable() {
                                    public void run() {
                                        Intent intent = new Intent(SplashActivity.this, LogInActivity.class);
                                        startActivity(intent);
                                        SplashActivity.this.finish();
                                    }
                                }, DURACION_SPLASH);
                            }
                        }else{
                            s.setPid(androidId);
                            new Handler().postDelayed(new Runnable() {
                                public void run() {
                                    Intent intent = new Intent(SplashActivity.this, LogInActivity.class);
                                    startActivity(intent);
                                    SplashActivity.this.finish();
                                }

                            }, DURACION_SPLASH);
                        }



                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }


}