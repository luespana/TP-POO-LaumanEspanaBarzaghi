package org.example.modelo.entidad;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa un muro de energía defensivo que protege al jugador.
 * 
 * <p>Los muros están compuestos por bloques individuales que pueden ser
 * destruidos por impactos. Cada bloque tiene puntos de vida (HP) que se
 * reducen con cada impacto hasta ser completamente destruido.
 * 
 * <p>Los muros se crean mediante un método estático de fábrica que divide
 * el área en bloques pequeños para permitir destrucción parcial.
 * 
 * @author LaumanEspanaBarzaghi
 * @version 1.0
 */
public class MuroEnergia {
    private final List<Block> blocks;

    private MuroEnergia(List<Block> blocks) {
        this.blocks = blocks;
    }

    public static MuroEnergia createBlock(int x, int y, int width, int height, int hp) {
        List<Block> list = new ArrayList<>();
        int cell = 6;
        for (int yy = y; yy < y + height; yy += cell) {
            for (int xx = x; xx < x + width; xx += cell) {
                list.add(new Block(xx, yy, cell - 1, cell - 1, hp));
            }
        }
        return new MuroEnergia(list);
    }

    public void render(Graphics2D g) {
        g.setColor(new Color(0, 180, 0));
        for (Block b : blocks) {
            if (b.hp > 0) g.fillRect(b.x, b.y, b.w, b.h);
        }
    }

    public boolean hit(Rectangle r) {
        for (Block b : blocks) {
            if (b.hp > 0 && r.intersects(new Rectangle(b.x, b.y, b.w, b.h))) {
                b.hp--;
                return true;
            }
        }
        return false;
    }

    /**
     * Obtiene los bloques del muro para renderizado.
     * 
     * @return Lista de bloques con sus datos (x, y, width, height, hp)
     */
    public List<BloqueDatos> getBloques() {
        List<BloqueDatos> resultado = new ArrayList<>();
        for (Block b : blocks) {
            resultado.add(new BloqueDatos(b.x, b.y, b.w, b.h, b.hp));
        }
        return resultado;
    }
    
    /**
     * Clase de datos para exponer información de los bloques sin exponer la clase interna.
     */
    public static class BloqueDatos {
        private final int x, y, w, h, hp;
        public BloqueDatos(int x, int y, int w, int h, int hp) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
            this.hp = hp;
        }
        public int getX() { return x; }
        public int getY() { return y; }
        public int getWidth() { return w; }
        public int getHeight() { return h; }
        public int getHp() { return hp; }
    }
    
    private static class Block {
        int x, y, w, h, hp;
        Block(int x, int y, int w, int h, int hp) { this.x = x; this.y = y; this.w = w; this.h = h; this.hp = hp; }
    }
}

