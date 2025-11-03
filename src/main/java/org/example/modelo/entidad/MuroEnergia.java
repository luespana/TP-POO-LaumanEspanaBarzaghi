package org.example.modelo.entidad;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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

    private static class Block {
        int x, y, w, h, hp;
        Block(int x, int y, int w, int h, int hp) { this.x = x; this.y = y; this.w = w; this.h = h; this.hp = hp; }
    }
}

