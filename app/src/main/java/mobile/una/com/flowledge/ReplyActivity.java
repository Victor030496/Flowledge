package mobile.una.com.flowledge;

/**
 * Created by Luis Bogantes on 28/12/2018.
 */

import android.support.v7.app.AppCompatActivity;
//package mobile.una.com.flowledge;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.design.widget.BottomNavigationView;
        import android.support.v7.app.AppCompatActivity;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ListView;
        import android.widget.TextView;

        import com.google.firebase.FirebaseApp;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

        import java.util.ArrayList;
        import java.util.List;
        import java.util.UUID;

import mobile.una.com.flowledge.MainActivity;
import mobile.una.com.flowledge.R;
import mobile.una.com.flowledge.UserActivity;
import mobile.una.com.flowledge.model.Persona;

        import static java.lang.Boolean.TRUE;
public class ReplyActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        setTitle("Respuestas");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);
        bottomNavigationView = findViewById(R.id.NavBot);
        bottomNavigationView.getMenu().getItem(2).setChecked(TRUE);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {

                            case R.id.bottombaritem_home:
                                startActivity(new Intent(getBaseContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NO_ANIMATION));
                                finish();
                                return true;
                            case R.id.bottombaritem_profile:
                                finish();
                                overridePendingTransition(0, 0);
                                startActivity(new Intent(getBaseContext(), UserActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NO_ANIMATION));
                                overridePendingTransition(0, 0);
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
