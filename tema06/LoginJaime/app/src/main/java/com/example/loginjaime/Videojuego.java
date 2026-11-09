package com.example.loginjaime;

public class Videojuego {

    private String titulo;
    private String plataforma;
    private int imagen;   // el recurso de la imagen: R.drawable.algo

    public Videojuego(String titulo, String plataforma, int imagen) {
        this.titulo = titulo;
        this.plataforma = plataforma;
        this.imagen = imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public int getImagen() {
        return imagen;
    }
}