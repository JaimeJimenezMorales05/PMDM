package com.example.loginjaime;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.loginjaime.R;
import com.google.android.material.materialswitch.MaterialSwitch;

public class MainActivity extends AppCompatActivity {

    private int intentos = 0;

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
        Button btnContinuar = findViewById(R.id.btnContinuar);
        TextView tvMensaje = findViewById(R.id.tvMensaje);
        MaterialSwitch swRecordar = findViewById(R.id.swRecordar);

        btnContinuar.setOnClickListener(v -> {
            String correo = etCorreo.getText().toString();
            String pass = etPass.getText().toString();

            // 1.4: campos vacíos
            if (correo.isEmpty()) {
                etCorreo.setError(getString(R.string.error_correo_vacio));
                return;
            }
            if (pass.isEmpty()) {
                etPass.setError(getString(R.string.error_pass_vacia));
                return;
            }

            // 1.2 y 1.3: comprobación
            if (correo.equals("jaime@gmail.com") && pass.equals("1234")) {
                intentos = 0;
                if (swRecordar.isChecked()) {
                    tvMensaje.setText(R.string.msg_recordado);
                } else {
                    tvMensaje.setText(R.string.msg_correcto);
                }
                tvMensaje.setTextColor(getColor(R.color.verde));
            } else {
                intentos++;
                if (intentos >= 3) {
                    btnContinuar.setEnabled(false);
                    tvMensaje.setText(R.string.msg_demasiados);
                    Toast.makeText(this, R.string.msg_demasiados, Toast.LENGTH_SHORT).show();
                } else {
                    tvMensaje.setText(R.string.msg_incorrecto);
                }
                tvMensaje.setTextColor(getColor(R.color.rojo));
            }
        });
    }
}