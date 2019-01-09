package mobile.una.com.flowledge;

import android.content.Intent;
import android.graphics.Color;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import mobile.una.com.flowledge.model.AreaData;
import mobile.una.com.flowledge.model.Sesion;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{
    private BottomNavigationView bottomNavigationView;
    Sesion s=new Sesion();
    String androidId;
    View headerView;
    TextView nickname,correobarra;

    RecyclerView mRecyclerView;
    List<AreaData> mFlowerList;
    AreaData mFlowerData;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        setTitle("HOME");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarFirebase();

        Intent intent = getIntent();
        s = (Sesion) intent.getSerializableExtra("S");
        androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.NavBot);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        headerView = navigationView.getHeaderView(0);

        nickname = headerView.findViewById(R.id.nickname);
        correobarra= headerView.findViewById(R.id.correobarra);
        nickname.setText(s.getNombre());
        correobarra.setText(s.getPid());

        mRecyclerView = findViewById(R.id.recyclerview);
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        llenarAreas();
        MyAdapter myAdapter = new MyAdapter(MainActivity.this, mFlowerList);
        mRecyclerView.setAdapter(myAdapter);


        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {

                            case R.id.bottombaritem_home:
                                MainActivity.this.finish();
                                finishAffinity();
                                Intent intent2 = new Intent(MainActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                intent2.putExtra("S", s);
                                startActivity(intent2);
                                return true;
                            case R.id.bottombaritem_profile:
                                finishAffinity();
                                Intent intent3 = new Intent(MainActivity.this, UserActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                intent3.putExtra("S", s);
                                startActivity(intent3);
                                MainActivity.this.finish();

                                return true;

                            case R.id.bottombaritem_reply:
                                finishAffinity();
                                Intent intent4 = new Intent(MainActivity.this, ReplyActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                intent4.putExtra("S", s);
                                startActivity(intent4);
                                MainActivity.this.finish();
                                return true;

                            case R.id.bottombaritem_question:
                                finishAffinity();
                                Intent intent5 = new Intent(MainActivity.this, QuestionActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                intent5.putExtra("S", s);
                                startActivity(intent5);
                                MainActivity.this.finish();
                                return true;

                        }
                        return false;
                    }
                });


    }
    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();
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
        }else if (id == R.id.out) {
            Intent intent = getIntent();
            s = (Sesion) intent.getSerializableExtra("S");
            databaseReference.child("Sesion").child(s.getPid()).removeValue();
            Intent intent2 = new Intent(MainActivity.this, SplashActivity.class);
            startActivity(intent2);
            MainActivity.this.finish();

        } /*else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        }  else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void llenarAreas(){
        mFlowerList = new ArrayList<>();
        mFlowerData = new AreaData("Bases de datos",R.drawable.bases);
        mFlowerList.add(mFlowerData);
        mFlowerData = new AreaData("Estructura de datos",R.drawable.estructuras);
        mFlowerList.add(mFlowerData);
        mFlowerData = new AreaData("POO",R.drawable.poo);
        mFlowerList.add(mFlowerData);
        mFlowerData = new AreaData("Redes",R.drawable.redes);
        mFlowerList.add(mFlowerData);

    }

}
