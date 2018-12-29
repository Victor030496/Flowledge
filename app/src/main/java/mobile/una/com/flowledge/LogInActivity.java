package mobile.una.com.flowledge;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;


public class LogInActivity extends AppCompatActivity {
    LinearLayout principal , formularioin , formularioregistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("LOG IN");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        principal = (LinearLayout) findViewById(R.id.principal);
        formularioin = (LinearLayout) findViewById(R.id.formularioin);
        formularioregistrar=(LinearLayout) findViewById(R.id.formularioregistrar);
    }

public void logIn(View view){
    setTitle("LOG IN");
    principal.setVisibility(View.GONE);
    formularioin.setVisibility(View.VISIBLE);
}


public void registrar(View view){
    setTitle("REGISTRARSE");
    principal.setVisibility(View.GONE);
    formularioregistrar.setVisibility(View.VISIBLE);
}

    public void cancelar(View view){
        setTitle("LOG IN");
        principal.setVisibility(View.VISIBLE);
        formularioregistrar.setVisibility(View.GONE);
    }

    public void guardar(View view){
       // setTitle("REGISTRASE");
      //  principal.setVisibility(View.GONE);
      //  formularioregistrar.setVisibility(View.VISIBLE);
    }

    public void cancelarin(View view){
        setTitle("LOG IN");
        principal.setVisibility(View.VISIBLE);
        formularioin.setVisibility(View.GONE);
    }

    public void ingresar(View view){
        // setTitle("REGISTRASE");
        //  principal.setVisibility(View.GONE);
        //  formularioregistrar.setVisibility(View.VISIBLE);
    }
}
