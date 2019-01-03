package mobile.una.com.flowledge;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import static java.lang.Boolean.TRUE;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;

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



        /*btn1.setTextColor(Color.parseColor("#9E9E9E"));
        btn2.setTextColor(Color.parseColor("#9E9E9E"));
        btn3.setTextColor(Color.parseColor("#9E9E9E"));
        btn4.setTextColor(Color.parseColor("#9E9E9E"));*/
        btn1.setBackgroundColor(Color.parseColor("#8080ff"));
        btn2.setBackgroundColor(Color.parseColor("#8080ff"));
        btn3.setBackgroundColor(Color.parseColor("#8080ff"));
        btn4.setBackgroundColor(Color.parseColor("#8080ff"));

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {

                            case R.id.bottombaritem_home:
                                finish();
                                overridePendingTransition(0, 0);
                                startActivity(new Intent(getBaseContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NO_ANIMATION));
                                overridePendingTransition(0, 0);
                                return true;
                            case R.id.bottombaritem_profile:
                                startActivity(new Intent(getBaseContext(), UsuarioActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NO_ANIMATION));
                                finish();

                                return true;

                            case R.id.bottombaritem_reply:
                                finish();
                                overridePendingTransition(0, 0);
                                startActivity(new Intent(getBaseContext(), ReplyActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NO_ANIMATION));
                                overridePendingTransition(0, 0);
                                return true;

                        }
                        return false;
                    }
                });

    }


}
