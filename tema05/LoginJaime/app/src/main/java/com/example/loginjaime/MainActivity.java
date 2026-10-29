package com.example.loginjaime;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

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

        ArrayList<Videojuego> videojuegos = new ArrayList<>();
        videojuegos.add(new Videojuego("The Legend of Zelda", "Nintendo Switch", R.drawable.ic_mando));
        videojuegos.add(new Videojuego("God of War", "PlayStation 5", R.drawable.ic_consola));
        videojuegos.add(new Videojuego("Valorant", "PC", R.drawable.ic_pc));
        videojuegos.add(new Videojuego("Pokémon GO", "Móvil", R.drawable.ic_movil));
        videojuegos.add(new Videojuego("Super Smash Bros", "Nintendo Switch", R.drawable.ic_mando));
        videojuegos.add(new Videojuego("Spider-Man 2", "PlayStation 5", R.drawable.ic_consola));
        videojuegos.add(new Videojuego("Fortnite", "PC", R.drawable.ic_pc));
        videojuegos.add(new Videojuego("Subway Surfers", "Móvil", R.drawable.ic_movil));
        videojuegos.add(new Videojuego("Splatoon 3", "Nintendo Switch", R.drawable.ic_mando));
        videojuegos.add(new Videojuego("Cyberpunk 2077", "PC", R.drawable.ic_pc));

        RecyclerView rvLista = findViewById(R.id.rvLista);
        rvLista.setLayoutManager(new LinearLayoutManager(this));
        rvLista.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvLista.setLayoutManager(new LinearLayoutManager(this));
        rvLista.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rvLista.setAdapter(new AdaptadorVideojuegos(videojuegos));
        rvLista.setAdapter(new AdaptadorVideojuegos(videojuegos));
    }
}