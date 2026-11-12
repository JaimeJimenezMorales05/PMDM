package com.example.reproductorjaime;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // ----- Ejercicio 1: reproductor -----
    private ExoPlayer player;
    private SeekBar seekBar;
    private TextView tvActual;
    private TextView tvTotal;
    private boolean arrastrando = false; // true mientras el usuario mueve la barra

    // Handler para actualizar la barra cada medio segundo
    private final Handler handler = new Handler(Looper.getMainLooper());

    private final Runnable actualizar = new Runnable() {
        @Override
        public void run() {
            if (!arrastrando) {
                long posicion = player.getCurrentPosition();
                seekBar.setProgress((int) posicion);
                tvActual.setText(formatear(posicion));
            }
            handler.postDelayed(this, 500); // otra vez dentro de 0,5 s
        }
    };

    // ----- Ejercicio 2: efectos de sonido -----
    private SoundPool soundPool;
    private int idEfecto1, idEfecto2, idEfecto3, idEfecto4;
    private int cargados = 0; // cuántos efectos se han cargado ya

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

        // Vistas
        seekBar = findViewById(R.id.seekBar);
        tvActual = findViewById(R.id.tvActual);
        tvTotal = findViewById(R.id.tvTotal);
        Button btnPlay = findViewById(R.id.btnPlay);
        Button btnPausa = findViewById(R.id.btnPausa);

        // 1.2 Crear el reproductor con la canción de res/raw
        player = new ExoPlayer.Builder(this).build();
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.cancion);
        player.setMediaItem(MediaItem.fromUri(uri));
        player.prepare();

        // Play: si la canción ha terminado, vuelve al principio
        btnPlay.setOnClickListener(v -> {
            if (player.getPlaybackState() == Player.STATE_ENDED) {
                player.seekTo(0);
            }
            player.play();
        });

        btnPausa.setOnClickListener(v -> player.pause());

        // 1.3 Cuando está listo, ya sabemos la duración
        player.addListener(new Player.Listener() {
            @Override
            public void onPlaybackStateChanged(int estado) {
                if (estado == Player.STATE_READY) {
                    long duracion = player.getDuration();
                    seekBar.setMax((int) duracion);
                    tvTotal.setText(formatear(duracion));
                }
            }
        });

        // 1.3 El usuario mueve la barra
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar barra) {
                arrastrando = true;
            }

            @Override
            public void onProgressChanged(SeekBar barra, int progreso, boolean delUsuario) {
                if (delUsuario) {
                    tvActual.setText(formatear(progreso));
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar barra) {
                player.seekTo(barra.getProgress()); // salta a ese punto
                arrastrando = false;
            }
        });

        // 2. Efectos de sonido con SoundPool
        Button btnEfecto1 = findViewById(R.id.btnEfecto1);
        Button btnEfecto2 = findViewById(R.id.btnEfecto2);
        Button btnEfecto3 = findViewById(R.id.btnEfecto3);
        Button btnEfecto4 = findViewById(R.id.btnEfecto4);

        AudioAttributes atributos = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(4)
                .setAudioAttributes(atributos)
                .build();

        // Cuando se han cargado los cuatro, se activan los botones
        soundPool.setOnLoadCompleteListener((pool, idSonido, estado) -> {
            if (estado == 0) { // 0 = cargado sin errores
                cargados++;
                if (cargados == 4) {
                    btnEfecto1.setEnabled(true);
                    btnEfecto2.setEnabled(true);
                    btnEfecto3.setEnabled(true);
                    btnEfecto4.setEnabled(true);
                }
            }
        });

        idEfecto1 = soundPool.load(this, R.raw.efecto_aplauso, 1);
        idEfecto2 = soundPool.load(this, R.raw.efecto_campana, 1);
        idEfecto3 = soundPool.load(this, R.raw.efecto_bocina, 1);
        idEfecto4 = soundPool.load(this, R.raw.efecto_risa, 1);

        btnEfecto1.setOnClickListener(v -> soundPool.play(idEfecto1, 1f, 1f, 1, 0, 1f));
        btnEfecto2.setOnClickListener(v -> soundPool.play(idEfecto2, 1f, 1f, 1, 0, 1f));
        btnEfecto3.setOnClickListener(v -> soundPool.play(idEfecto3, 1f, 1f, 1, 0, 1f));
        btnEfecto4.setOnClickListener(v -> soundPool.play(idEfecto4, 1f, 1f, 1, 0, 1f));
    }

    // 1.4 Ciclo de vida
    @Override
    protected void onStart() {
        super.onStart();
        handler.post(actualizar); // la barra vuelve a actualizarse
    }

    @Override
    protected void onStop() {
        super.onStop();
        player.pause();
        handler.removeCallbacks(actualizar); // la barra deja de actualizarse
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
        soundPool.release();
    }

    // 75000 ms -> "01:15"
    private String formatear(long ms) {
        long segundos = ms / 1000;
        return String.format(Locale.getDefault(), "%02d:%02d", segundos / 60, segundos % 60);
    }
}