package com.example.loginjaime;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.materialswitch.MaterialSwitch;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS = "sesion";
    private static final String CLAVE_CORREO = "correo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText etCorreo = findViewById(R.id.etCorreo);
        EditText etPass = findViewById(R.id.etPass);
        MaterialSwitch swRecordar = findViewById(R.id.swRecordar);
        Button btnContinuar = findViewById(R.id.btnContinuar);
        TextView tvMensaje = findViewById(R.id.tvMensaje);

        // Ejercicio 2: al abrir la app, si hay un correo guardado lo escribimos
        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        String correoGuardado = prefs.getString(CLAVE_CORREO, "");
        if (!correoGuardado.isEmpty()) {
            etCorreo.setText(correoGuardado);
            swRecordar.setChecked(true);
            etPass.requestFocus();
        }

        btnContinuar.setOnClickListener(v -> {
            String correo = etCorreo.getText().toString().trim();
            String pass = etPass.getText().toString();

            if (correo.isEmpty()) {
                etCorreo.setError(getString(R.string.error_correo_vacio));
                return;
            }
            if (pass.isEmpty()) {
                etPass.setError(getString(R.string.error_pass_vacia));
                return;
            }

            if (correo.equals("jaime@correo.com") && pass.equals("1234")) {
                // Ejercicio 2: guardar o borrar el correo según el interruptor
                // (la contraseña nunca se guarda)
                SharedPreferences.Editor editor = getSharedPreferences(PREFS, MODE_PRIVATE).edit();
                if (swRecordar.isChecked()) {
                    editor.putString(CLAVE_CORREO, correo);
                } else {
                    editor.remove(CLAVE_CORREO);
                }
                editor.apply();

                // 1.2: abrir el menú pasándole el correo y cerrar el login
                Intent intent = new Intent(this, MenuActivity.class);
                intent.putExtra(MenuActivity.EXTRA_CORREO, correo);
                startActivity(intent);
                finish();
            } else {
                tvMensaje.setText(R.string.msg_incorrecto);
                tvMensaje.setTextColor(getColor(android.R.color.holo_red_dark));
            }
        });
    }
}