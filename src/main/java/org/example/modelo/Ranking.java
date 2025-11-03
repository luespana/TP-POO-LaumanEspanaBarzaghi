package org.example.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ranking {
    private static final int MAX_ENTRADAS = 5;
    private List<EntradaRanking> entradas;

    public Ranking() {
        this.entradas = new ArrayList<>();
    }

    public void agregarEntrada(String nombre, int nivelesSuperados, int puntuacion) {
        entradas.add(new EntradaRanking(nombre, nivelesSuperados, puntuacion));
        Collections.sort(entradas);
        
        // Mantener solo las top 5
        if (entradas.size() > MAX_ENTRADAS) {
            entradas = entradas.subList(0, MAX_ENTRADAS);
        }
    }

    public boolean esPuntuacionValida(int puntuacion) {
        // Verifica si la puntuación es suficiente para entrar en el ranking
        if (entradas.size() < MAX_ENTRADAS) {
            return true;
        }
        // Si hay 5 entradas, verifica si la puntuación es mayor que la más baja
        return puntuacion > entradas.get(entradas.size() - 1).getPuntuacion();
    }

    public List<EntradaRanking> getTop5() {
        return new ArrayList<>(entradas);
    }

    public int getCantidadEntradas() {
        return entradas.size();
    }
}
