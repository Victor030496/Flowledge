package mobile.una.com.flowledge;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.NavBot);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.bottombaritem_home:
                                TextView tv1 = (TextView)findViewById(R.id.Prueba);
                                tv1.setText("Hello this is Home");
                                return true;
                            case R.id.bottombaritem_profile:
                                TextView tv2 = (TextView)findViewById(R.id.Prueba);
                                tv2.setText("Hello this is profile");
                                return true;

                        }
                        return false;
                    }
                });

    }


}
