package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Enemigo {
	double x,y,escala,ancho,alto,velocidad;
	int daño;
	Boolean direccion;
	Image img;
	Entorno e;
	
	Enemigo(double x, double y, Entorno e, int daño) {
		this.x = x;
		this.y = y;
		this.direccion = false;
		this.img = Herramientas.cargarImagen("bat.png");
		this.escala = 0.1; // Se puede cambiar
		this.velocidad = 0.5; // Se puede cambiar
		this.e = e;
		this.ancho = img.getWidth(e) * this.escala;
		this.alto = img.getHeight(e) * this.escala;
		this.daño = daño;
	}
	
	void dibujar() { 
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
