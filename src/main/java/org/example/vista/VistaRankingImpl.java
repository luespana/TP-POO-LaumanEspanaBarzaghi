package org.example.vista;

import org.example.app.RankingPanel;

import java.awt.Component;
import java.awt.event.ActionListener;

/**
 * Implementación concreta de IVistaRanking que envuelve RankingPanel.
 * 
 * @author LaumanEspanaBarzaghi
 * @version 1.0
 */
class VistaRankingImpl implements IVistaRanking {
    private final RankingPanel rankingPanel;
    
    VistaRankingImpl(org.example.modelo.Ranking ranking) {
        this.rankingPanel = new RankingPanel(ranking);
    }
    
    @Override
    public void agregarListenerVolver(ActionListener listener) {
        rankingPanel.agregarListenerVolver(listener);
    }
    
    @Override
    public Component getComponent() {
        return rankingPanel;
    }
}

