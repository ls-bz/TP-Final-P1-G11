package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Boton {
	private double x,y,escala,ancho,alto;
	private Image img;
	private Entorno e;
	private String nombre;
	
	Boton(double x, double y, Entorno e, String imagen, String nombre) {
		this.x = x;
		this.y = y;
		this.img = Herramientas.cargarImagen(imagen);
		this.escala = 0.35; // Se puede cambiar
		this.e = e;
		this.ancho = img.getWidth(e) * this.escala;
		this.alto = img.getHeight(e) * this.escala;
		this.nombre = nombre;
	}
	
	void dibujar() {
		e.dibujarImagen(img, x, y, 0, escala);
	}
	
	boolean encima(double x, double y) {
		return (x<this.x+this.ancho/2 && x>this.x-this.ancho/2 && y<this.y+this.alto/2 && y>this.y-this.alto/2);
	}
	
	public String obtenerNombre() {
        return nombre;
    }
	
	public double obtenerEscala() {
        return escala;
    }
	
	public void cambiarEscala(double nuevaEscala) {
		escala = nuevaEscala;
	}
}
