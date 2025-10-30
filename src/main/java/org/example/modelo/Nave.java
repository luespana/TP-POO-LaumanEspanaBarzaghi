package org.example.modelo;

public abstract class Nave {
    protected int posX;
    protected int posY;
    protected int velocidad;
    protected String direccion;
    protected int bordeIzquierdo;
    protected int bordeDerecho;
    
    public abstract void mover();
    
    public abstract void cambiarDireccion();
    
    public abstract void disparar();
    
    public abstract void recibirImpacto();
    
    public abstract void comprobarColision();
}