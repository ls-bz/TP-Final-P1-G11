package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Obstaculo {
	private double x,y,escala,ancho,alto;
	private Image img;
	private Entorno e;
	
	Obstaculo(double x, double y, Entorno e) {
		this.x = x;
		this.y = y;
		this.img = Herramientas.cargarImagen("stone.png");
		this.escala = 0.25; // Se puede cambiar
		this.e = e;
		this.ancho = img.getWidth(e) * this.escala;
		this.alto = img.getHeight(e) * this.escala;
	}
	
	void dibujar() {
		e.dibujarImagen(img, x, y, 0, escala);
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
