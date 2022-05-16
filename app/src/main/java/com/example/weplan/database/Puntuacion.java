package com.example.weplan.database;

public class Puntuacion {
    private String fecha;
    private int puntos;

    public Puntuacion(String fecha, int puntos) {
        this.fecha = fecha;
        this.puntos = puntos;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }
}
