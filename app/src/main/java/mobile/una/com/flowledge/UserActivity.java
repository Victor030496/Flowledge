package mobile.una.com.flowledge;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import mobile.una.com.flowledge.model.Persona;
import mobile.una.com.flowledge.model.Sesion;

import static java.lang.Boolean.TRUE;

public class UserActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private List<Persona> listapersona = new ArrayList<Persona>();
    Sesion s=new Sesion();
    LinearLayout informacion,boton;
    private BottomNavigationView bottomNavigationView;
    TextView pid,pnombre,correo,nickname,correobarra;
    Button Mostrar;
    Persona p=new Persona();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    View headerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        setTitle("USERS");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.NavBot);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        headerView = navigationView.getHeaderView(0);


        informacion = (LinearLayout) findViewById(R.id.informacion);
        boton = (LinearLayout) findViewById(R.id.Boton);
        bottomNavigationView = findViewById(R.id.NavBot);
        bottomNavigationView.getMenu().getItem(4).setChecked(TRUE);
        pid=findViewById(R.id.cedula2);
        pnombre=findViewById(R.id.nombre2);
        correo=findViewById(R.id.correo2);

        nickname = headerView.findViewById(R.id.nickname);
        correobarra= headerView.findViewById(R.id.correobarra);

       // pid=findViewById(R.id.id);
        Intent intent = getIntent();
        s = (Sesion) intent.getSerializableExtra("S");
        inicializarFirebase();
        listarDatos();

        nickname.setText(s.getNombre());
        correobarra.setText(s.getPid());

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {

                            case R.id.bottombaritem_home:
                                Intent intent2 = new Intent(UserActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                intent2.putExtra("S", s);
                                startActivity(intent2);
                                UserActivity.this.finish();
                                return true;
                            case R.id.bottombaritem_profile:
                               Intent intent3 = new Intent(UserActivity.this, UserActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                intent3.putExtra("S", s);
                                startActivity(intent3);
                                UserActivity.this.finish();
                                return true;

                            case R.id.bottombaritem_reply:
                                Intent intent4 = new Intent(UserActivity.this, ReplyActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                intent4.putExtra("S", s);
                                startActivity(intent4);
                                UserActivity.this.finish();
                                return true;

                            case R.id.bottombaritem_question:
                                Intent intent5 = new Intent(UserActivity.this, QuestionActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                intent5.putExtra("S", s);
                                startActivity(intent5);
                                UserActivity.this.finish();
                                return true;

                        }
                        return false;
                    }
                });

    }

    private void listarDatos() {
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


    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
    }


    public void cerrar(View view){
        Intent intent = getIntent();
        s = (Sesion) intent.getSerializableExtra("S");
        databaseReference.child("Sesion").child(s.getPid()).removeValue();
        Intent intent2 = new Intent(UserActivity.this, SplashActivity.class);
        startActivity(intent2);
        UserActivity.this.finish();
    }

    public Persona getUser(){
        Persona p=new Persona();
        for(int i=0;i < listapersona.size() ;i++){
            if(listapersona.get(i).getPid().equals(s.getNombre())){
                p=listapersona.get(i);
                break;
            }
        }

        return  p;
    }

    public void Mostrar(View view){
        Persona pe= getUser();
        informacion.setVisibility(View.VISIBLE);
        boton.setVisibility(View.GONE);
        pid.setText(pe.getPid());
        pnombre.setText(pe.getNombre());
        correo.setText(pe.getCorreo());

    }

    public void  Esconder(View view){
        Persona pe= getUser();
        informacion.setVisibility(View.GONE);
        boton.setVisibility(View.VISIBLE);
        pid.setText(pe.getPid());
        pnombre.setText(pe.getNombre());
        correo.setText(pe.getCorreo());

    }


    //MENU------------------------------------------------------

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
