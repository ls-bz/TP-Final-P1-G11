package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Menu {
	double x,y,escala,ancho,alto;
	Image img;
	Entorno e;
	
	Menu(Entorno e) {
		this.e = e;
		this.img = Herramientas.cargarImagen("menu.png");
		this.escala = 1;
		this.ancho = img.getWidth(e) * this.escala;
		this.alto = img.getHeight(e) * this.escala;
		this.x = e.ancho()-this.ancho/2;
		this.y = e.alto()-this.alto/2;
		
		
	}
	
	void dibujar() {
		e.dibujarImagen(img, x, y, 0, escala);
	}
}
