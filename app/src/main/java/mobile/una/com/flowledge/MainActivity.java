package mobile.una.com.flowledge;

import android.content.Intent;
import android.graphics.Color;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import mobile.una.com.flowledge.model.Sesion;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    Sesion s=new Sesion();
    String androidId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        setTitle("HOME");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.NavBot);
        btn1 = (Button) findViewById(R.id.arboles);
        btn2  = (Button) findViewById(R.id.listas);
        btn3= (Button) findViewById(R.id.bd);
        btn4= (Button) findViewById(R.id.redes);
        Intent intent = getIntent();
        s = (Sesion) intent.getSerializableExtra("S");
        androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        /*btn1.setTextColor(Color.parseColor("#9E9E9E"));
        btn2.setTextColor(Color.parseColor("#9E9E9E"));
        btn3.setTextColor(Color.parseColor("#9E9E9E"));
        btn4.setTextColor(Color.parseColor("#9E9E9E"));*/
        btn1.setText(s.getNombre());
        btn1.setBackgroundColor(Color.parseColor("#8080ff"));
        btn2.setBackgroundColor(Color.parseColor("#8080ff"));
        btn3.setBackgroundColor(Color.parseColor("#8080ff"));
        btn4.setBackgroundColor(Color.parseColor("#8080ff"));


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ReplyActivity.class);
                // intent.putExtra("paymentObject", contacts.get(i));
                startActivity(intent);

            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {

                            case R.id.bottombaritem_home:
                                MainActivity.this.finish();
                                Intent intent2 = new Intent(MainActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                intent2.putExtra("S", s);
                                startActivity(intent2);
                                return true;
                            case R.id.bottombaritem_profile:
                                Intent intent3 = new Intent(MainActivity.this, UserActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                intent3.putExtra("S", s);
                                startActivity(intent3);
                                MainActivity.this.finish();

                                return true;

                            case R.id.bottombaritem_reply:
                                Intent intent4 = new Intent(MainActivity.this, ReplyActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                intent4.putExtra("S", s);
                                startActivity(intent4);
                                MainActivity.this.finish();
                                return true;

                            case R.id.bottombaritem_question:
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



}
