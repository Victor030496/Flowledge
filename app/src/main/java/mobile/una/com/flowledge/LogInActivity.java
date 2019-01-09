package mobile.una.com.flowledge;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mobile.una.com.flowledge.model.Persona;
import mobile.una.com.flowledge.model.Sesion;

public class LogInActivity extends AppCompatActivity {
    private List<Persona> listapersona = new ArrayList<Persona>();
    LinearLayout principal, formularioregistrar;
    Persona p = new Persona();
    Sesion s = new Sesion();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String androidId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("LOG IN");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        principal = (LinearLayout) findViewById(R.id.principal);
        formularioregistrar = (LinearLayout) findViewById(R.id.formularioregistrar);
        androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        inicializarFirebase();
        listaPersona();

        final TextInputLayout passwordTextInput = findViewById(R.id.password_text_input);
        final TextInputEditText passwordEditText = findViewById(R.id.passwordin);
        final TextInputLayout cedulaTextInput = findViewById(R.id.cedulaText);
        final TextInputEditText cedulaEditText = findViewById(R.id.cedula2in);
        MaterialButton nextButton = findViewById(R.id.next_button);
        MaterialButton buttonRegistro = findViewById(R.id.registro_button);

        final TextInputLayout cedulaFormTextInput = findViewById(R.id.cedulaFormText);
        final TextInputEditText cedulaFormEditText = findViewById(R.id.cedula2);
        final TextInputLayout nombreTextInput = findViewById(R.id.nombreFormText);
        final TextInputEditText nombreEditText = findViewById(R.id.nombre2);
        final TextInputLayout correoTextInput = findViewById(R.id.correoFormText);
        final TextInputEditText correoEditText = findViewById(R.id.correo2);
        final TextInputLayout contrasenaTextInput = findViewById(R.id.contrasenaFormText);
        final TextInputEditText contrasenaEditText = findViewById(R.id.password);
        final TextInputLayout conContrasenaTextInput = findViewById(R.id.concontrasenaFormText);
        final TextInputEditText conContrasenaEditText = findViewById(R.id.concontrasena);
        MaterialButton registrarFormButton = findViewById(R.id.guardar);
        MaterialButton buttonCancelar = findViewById(R.id.button_cancelar);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean bandera = false;
                for (Persona persona : listapersona) {
                    if ((persona.getPid().equals(cedulaEditText.getText().toString())) && (persona.getContra().equals(passwordEditText.getText().toString()))) {
                        passwordTextInput.setError(null);
                        Toast.makeText(getApplicationContext(), "Ingreso exitoso", Toast.LENGTH_SHORT).show();
                        s.setPid(androidId);
                        s.setNombre(cedulaEditText.getText().toString());
                        s.setEstado("1");
                        databaseReference.child("Sesion").child(s.getPid()).setValue(s);
                        bandera = true;
                        break;
                    }
                }
                if (bandera) {
                    Intent intent2 = new Intent(LogInActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent2.putExtra("S", s);
                    startActivity(intent2);
                    LogInActivity.this.finish();
                } else {
                    passwordTextInput.setError(getString(R.string.shr_error_password));
                }
            }
        });

        registrarFormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean bandera = true;

                bandera = validacionregistro(cedulaFormTextInput, cedulaFormEditText,
                        nombreTextInput, nombreEditText, correoTextInput, correoEditText,
                        contrasenaTextInput, contrasenaEditText, conContrasenaTextInput, conContrasenaEditText);
                if (bandera) {
                    p = new Persona(cedulaFormEditText.getText().toString(), nombreEditText.getText().toString(),
                            correoEditText.getText().toString(), contrasenaEditText.getText().toString());
                    databaseReference.child("Persona").child(p.getPid()).setValue(p);
                    s.setPid(androidId);
                    s.setNombre(cedulaEditText.getText().toString());
                    s.setEstado("1");
                    databaseReference.child("Sesion").child(s.getPid()).setValue(s);
                    Toast.makeText(getApplicationContext(), "Registro exitoso", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LogInActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    intent.putExtra("S", s);
                    startActivity(intent);
                    LogInActivity.this.finish();
                }
            }
        });

        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelar(view, cedulaFormTextInput, nombreTextInput, correoTextInput,
                        contrasenaTextInput, conContrasenaTextInput);
            }
        });

        buttonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrar(view, passwordTextInput);
            }
        });

    }

    // Set an error if the password is less than 8 characters.
    private boolean isPasswordValid(@Nullable Editable text) {
        return text != null && text.length() >= 8;
    }

    public void registrar(View view, TextInputLayout passwordTextInput) {
        principal.setVisibility(View.GONE);
        formularioregistrar.setVisibility(View.VISIBLE);
        passwordTextInput.setError(null);
    }

    public void cancelar(View view, TextInputLayout cedulaFormTextInput, TextInputLayout nombreTextInput,
                         TextInputLayout correoTextInput, TextInputLayout contrasenaTextInput,
                         TextInputLayout conContrasenaTextInput) {
        principal.setVisibility(View.VISIBLE);
        formularioregistrar.setVisibility(View.GONE);
        cedulaFormTextInput.setError(null);
        nombreTextInput.setError(null);
        correoTextInput.setError(null);
        contrasenaTextInput.setError(null);
        conContrasenaTextInput.setError(null);
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void listaPersona() {
        databaseReference.child("Persona").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listapersona.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()) {
                    Persona p = objSnapshot.getValue(Persona.class);
                    listapersona.add(p);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private boolean validacionregistro(TextInputLayout cedulaFormTextInput, TextInputEditText cedulaFormEditText,
                                       TextInputLayout nombreTextInput, TextInputEditText nombreEditText,
                                       TextInputLayout correoTextInput, TextInputEditText correoEditText,
                                       TextInputLayout contrasenaTextInput, TextInputEditText contrasenaEditText,
                                       TextInputLayout conContrasenaTextInput, TextInputEditText conContrasenaEditText) {
        boolean flag = true;
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = null;
        cedulaFormTextInput.setError(null);
        nombreTextInput.setError(null);
        correoTextInput.setError(null);
        contrasenaTextInput.setError(null);
        conContrasenaTextInput.setError(null);
        if (cedulaFormEditText.getText().toString().equals("")) {
            cedulaFormTextInput.setError("Campo obligatorio *");
            flag = false;
        }else{
            for (Persona persona : listapersona) {
                if (persona.getPid().equals(cedulaFormEditText.getText().toString())) {
                    cedulaFormTextInput.setError("Cédula existente");
                    flag = false;
                    break;
                }
            }
        }
        if (nombreEditText.getText().toString().equals("")) {
            nombreTextInput.setError("Campo obligatorio *");
            flag = false;
        }
        if (contrasenaEditText.getText().toString().equals("")) {
            contrasenaTextInput.setError("Campo obligatorio *");
            flag = false;
        }
        if (conContrasenaEditText.getText().toString().equals("")) {
            conContrasenaTextInput.setError("Campo obligatorio *");
            flag = false;
        }
        if (!contrasenaEditText.getText().toString().equals(conContrasenaEditText.getText().toString())) {
            conContrasenaTextInput.setError("Contraseña diferente");
            flag = false;
        }
        if (correoEditText.getText().toString().equals("")) {
            correoTextInput.setError("Campo obligatorio *");
            flag = false;
        } else {
            mather = pattern.matcher(correoEditText.getText().toString());
            if (mather.find()) {
                String[] parts = correoEditText.getText().toString().split("@");
                String part1 = parts[0];
                String part2 = parts[1];
                if ((!part2.equals("est.una.ac.cr")) && (!part2.equals("una.cr"))) {
                    correoTextInput.setError("Solo correos institucionales");
                    flag = false;
                }
            } else {
                correoTextInput.setError("Correo no válido");
                flag = false;
            }
        }
        return flag;
    }
}
