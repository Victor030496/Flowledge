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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    List<Sesion> listasesion = new ArrayList<Sesion>();
    Sesion s = new Sesion();
    boolean bandera;
    List<String> id;
    String androidId;
    FirebaseUser firebaseUser;

    /*
        @Override
        protected void onStart() {
            super.onStart();
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            if (firebaseUser != null) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        intent.putExtra("S", s);
                        startActivity(intent);
                        finish();
                    }
                }, DURACION_SPLASH);
            } else {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Intent intent = new Intent(SplashActivity.this, LogInActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, DURACION_SPLASH);
            }
        }
    */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        //Inicializar firebase
        FirebaseApp.initializeApp(this);
        bandera = false;
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        databaseReference.child("Sesion").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listasesion.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Sesion g = objSnapshot.getValue(Sesion.class);
                    listasesion.add(g);

                }

                for (int i = 0; i <= listasesion.size() - 1; i++) {
                    if (listasesion.get(i).getPid().equals(androidId)) {
                        s = listasesion.get(i);
                        bandera = true;
                        break;
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

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
            //
        });


    }


}