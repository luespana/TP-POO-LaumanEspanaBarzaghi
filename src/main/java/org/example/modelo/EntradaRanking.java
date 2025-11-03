package org.example.modelo;

public class EntradaRanking implements Comparable<EntradaRanking> {
    private String nombre;
    private int nivelesSuperados;
    private int puntuacion;

    public EntradaRanking(String nombre, int nivelesSuperados, int puntuacion) {
        this.nombre = nombre;
        this.nivelesSuperados = nivelesSuperados;
        this.puntuacion = puntuacion;
    }

    public String getNombre() {
        return nombre;
    }

    public int getNivelesSuperados() {
        return nivelesSuperados;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    @Override
    public int compareTo(EntradaRanking otra) {
        // Ordenar por puntuación descendente (mayor a menor)
        return Integer.compare(otra.puntuacion, this.puntuacion);
    }
}

