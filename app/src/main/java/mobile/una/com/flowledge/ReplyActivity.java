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
import mobile.una.com.flowledge.model.Sesion;

import static java.lang.Boolean.TRUE;
public class ReplyActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Sesion s=new Sesion();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        setTitle("Respuestas");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);
        bottomNavigationView = findViewById(R.id.NavBot);
        bottomNavigationView.getMenu().getItem(2).setChecked(TRUE);
        Intent intent = getIntent();
        s = (Sesion) intent.getSerializableExtra("S");

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {

                            case R.id.bottombaritem_home:
                                Intent intent2 = new Intent(ReplyActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                intent2.putExtra("S", s);
                                startActivity(intent2);
                                ReplyActivity.this.finish();
                                return true;
                            case R.id.bottombaritem_profile:
                                Intent intent3 = new Intent(ReplyActivity.this, UserActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                intent3.putExtra("S", s);
                                startActivity(intent3);
                                ReplyActivity.this.finish();

                                return true;

                            case R.id.bottombaritem_reply:
                                Intent intent4 = new Intent(ReplyActivity.this, ReplyActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                intent4.putExtra("S", s);
                                startActivity(intent4);
                                ReplyActivity.this.finish();
                                return true;

                            case R.id.bottombaritem_question:
                                Intent intent5 = new Intent(ReplyActivity.this, QuestionActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                intent5.putExtra("S", s);
                                startActivity(intent5);
                                ReplyActivity.this.finish();
                                return true;
                        }
                        return false;
                    }
                });

    }



}
