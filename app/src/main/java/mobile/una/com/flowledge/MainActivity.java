package mobile.una.com.flowledge;

import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import mobile.una.com.flowledge.model.AreaData;
import mobile.una.com.flowledge.model.Sesion;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {
    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    ViewPager viewPager;
    MenuItem prevMenuItem;
    /*HomeFragment homeFragment;
    QuestionFragment questionFragment;
    ReplyFragment replyFragment;*/
    UserProfileFragment userProfileFragment;

    Sesion s = new Sesion();
    String androidId;
    View headerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    TextView nickname, correobarra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        setTitle("HOME");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navBottomListener);
        //viewPager = findViewById(R.id.viewpager);
        iniciarFirebase();

        Intent intent = getIntent();
        s = (Sesion) intent.getSerializableExtra("S");
        androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);


        //-----------------------------------------------------------------------


        Fragment selectFragment = null;
        Bundle bundle = new Bundle();
        bundle.putSerializable("sesion", (Serializable) s);
        selectFragment = new HomeFragment();
        selectFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                selectFragment).commit();

        //-----------------------------------------------------------------------


        userProfileFragment = new UserProfileFragment();
        new Thread(new Runnable() {
            public void run() {
                // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                // setSupportActionBar(toolbar);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                        MainActivity.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                drawer.addDrawerListener(toggle);
                toggle.syncState();


                //-----------------------------------------------------------------------


                Fragment selectFragment = null;
                Bundle bundle = new Bundle();
                bundle.putSerializable("sesion", (Serializable) s);
                selectFragment = new HomeFragment();
                selectFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectFragment).commit();

                //-----------------------------------------------------------------------

            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                navigationView.setNavigationItemSelectedListener(MainActivity.this);
                headerView = navigationView.getHeaderView(0);
                nickname = headerView.findViewById(R.id.nickname);
                correobarra = headerView.findViewById(R.id.correobarra);
                nickname.setText(s.getNombre());
                correobarra.setText(s.getPid());
            }
        }).start();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();
/*
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: " + position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        setupViewPager(viewPager);
*/
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navBottomListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectFragment = null;
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("sesion", (Serializable) s);
                    switch (item.getItemId()) {
                        case R.id.bottombaritem_home:
                            //viewPager.setCurrentItem(0);
                            selectFragment = new HomeFragment();
                            break;
                        case R.id.bottombaritem_question:
                            //viewPager.setCurrentItem(1);
                            selectFragment = new QuestionFragment();
                            break;
                        /*case R.id.bottombaritem_reply:
                            //viewPager.setCurrentItem(2);
                            selectFragment = new ReplyFragment();
                            break;*/
                        case R.id.bottombaritem_profile:
                            //viewPager.setCurrentItem(3);
                            selectFragment = userProfileFragment;
                            break;
                        default:
                            Toast.makeText(getApplicationContext(), "Opción innecesario", Toast.LENGTH_SHORT).show();
                    }
                    if(selectFragment != null) {
                        selectFragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                selectFragment).commit();
                    }
                    return true;
                }
            };

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.out) {
            cerrarSesion();
        } /*else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        }  else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
/*
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        homeFragment = new HomeFragment();
        questionFragment = new QuestionFragment();
        replyFragment = new ReplyFragment();
        userProfileFragment = new UserProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("sesion", (Serializable) s);
        homeFragment.setArguments(bundle);
        questionFragment.setArguments(bundle);
        replyFragment.setArguments(bundle);
        userProfileFragment.setArguments(bundle);
        viewPagerAdapter.addFragment(homeFragment);
        viewPagerAdapter.addFragment(questionFragment);
        viewPagerAdapter.addFragment(replyFragment);
        viewPagerAdapter.addFragment(userProfileFragment);
        viewPager.setAdapter(viewPagerAdapter);
    }
*/

    private void cerrarSesion() {
        runOnUiThread(new Runnable() {
            public void run() {
                try {
                    databaseReference.child("Sesion").child(s.getPid()).removeValue();
                    Intent intent2 = new Intent(MainActivity.this, SplashActivity.class);
                    startActivity(intent2);
                    MainActivity.this.finish();
                } catch (final Exception ex) {
                    Log.i("---", "Exception in thread");
                }
            }
        });
    }

    private void iniciarFirebase() {
        runOnUiThread(new Runnable() {
            public void run() {
                try {
                    FirebaseApp.initializeApp(getApplicationContext());
                    firebaseDatabase = FirebaseDatabase.getInstance();
                    databaseReference = firebaseDatabase.getReference();
                } catch (final Exception ex) {
                    Log.i("---", "Exception in thread");
                }
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_filtro_busqueda, menu);
        return true;
    }
/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            Toast.makeText(MainActivity.this, "Action clicked", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = userProfileFragment;
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
