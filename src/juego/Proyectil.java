package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Proyectil {
	private int daño,coste;
	private double escala,ancho,alto,x,y;
	private String nombre;
	private Image img;
	private Entorno e;
	private boolean activo;
    private int ticks;
	
	Proyectil(String nombre, int daño, int coste, Entorno e, String imagen, double escala) {
		this.img = Herramientas.cargarImagen(imagen);
		this.daño = daño;
		this.escala = escala;
		this.coste = coste;
		this.e = e;
		this.ancho = img.getWidth(e) * this.escala;
		this.alto = img.getHeight(e) * this.escala;
		this.activo  = false;   // comienza inactivo
        this.ticks = 0;
    }
	
	public void lanzar(double px, double py, int duracion) {
        this.x = px;
        this.y = py;
        this.ticks = duracion;
        this.activo = true;
    }
	
	public void dibujar() {
		if (!activo) return;
		
        e.dibujarImagen(img, x, y, 0, escala);
        ticks--;
        if(ticks<=0) {
            activo = false;
        }
	}
	
	 
	
	public boolean pRadio(double eX, double eY) {
		double disX = eX - this.x;
        double disY = eY - this.y;
        double r = this.ancho/2;
		double r2 = r*r;
        double dis2 = (disX * disX) + (disY * disY);
        return dis2 <= r2;
	}
	
	public boolean estaActivo() {
        return activo;
    }

    public int obtenerCoste() {
        return coste;
    }

    public int obtenerDaño() {
        return daño;
    }
    
    public String obtenerNombre() {
        return nombre;
    }
    
    public double obtenerAncho() {
        return ancho;
    }
	
	public double obtenerAlto() {
        return alto;  
	}
	
	public double obtenerPosX() {
        return x;
    }
	
	public double obtenerPosY() {
        return y;
	}

}
