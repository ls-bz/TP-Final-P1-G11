package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Personaje {
	double x,y,escala,ancho,alto,velocidad;
	Boolean direccion;
	Image img;
	Entorno e;
	
	Personaje(double x, double y, Entorno e) {
		this.x = x;
		this.y = y;
		this.direccion = false;
		this.img = Herramientas.cargarImagen("wizard.png");
		this.escala = 0.15; // Se puede cambiar
		this.velocidad = 2; // Se puede cambiar
		this.e = e;
		this.ancho = img.getHeight(e) * this.escala;
		this.alto = img.getWidth(e) * this.escala;
		
	}
	
	void dibujar() { // Dibujar al personaje en funcion de la direccion
		if(!this.direccion) {
			e.dibujarImagen(img, x, y, 0, escala);
		}
//		else {
//			e.dibujarImagen(img, x, y, escala);
//		}
	}
	
	void mover(double mH, double mV) { // Movimiento Horizontal y Vertical
		this.x += mH;
		this.y += mV;
	}
}
