package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Obstaculo {
	double x,y,escala,ancho,alto;
	Image img0;
	Entorno e;
	
	Obstaculo(double x, double y, Entorno e) {
		this.x = x;
		this.y = y;
		this.img0 = Herramientas.cargarImagen("stone.png");
		this.escala = 0.15; // Se puede cambiar
		this.e = e;
		this.ancho = img0.getHeight(e) * this.escala;
		this.alto = img0.getWidth(e) * this.escala;
	}
	
	void dibujar() {
		e.dibujarImagen(img0, x, y, 0, escala);
	}
}
