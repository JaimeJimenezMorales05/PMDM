package com.example.loginjaime;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

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

        // 1. Buscamos las vistas por su id
        EditText etCorreo = findViewById(R.id.etCorreo);
        EditText etPass = findViewById(R.id.etPass);
        Button btnContinuar = findViewById(R.id.btnContinuar);
        TextView tvMensaje = findViewById(R.id.tvMensaje);

        // 2. Al pulsar el botón (con un lambda)
        btnContinuar.setOnClickListener(v -> {
            String correo = etCorreo.getText().toString();
            String pass = etPass.getText().toString();

            // 3. Comparamos con equals, nunca con ==
            if (correo.equals("jaime@gmail.com") && pass.equals("1234")) {
                tvMensaje.setText(R.string.msg_error);
                tvMensaje.setTextColor(getColor(R.color.verde));
            } else {
                tvMensaje.setText(R.string.msg_error);
                tvMensaje.setTextColor(getColor(R.color.rojo));
            }
        });
    }
}