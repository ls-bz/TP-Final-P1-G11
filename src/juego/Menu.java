package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Menu {
	private double x,y,escala,ancho,alto;
	private Image img;
	private Entorno e;
	
	Menu(Entorno e) {
		this.e = e;
		this.img = Herramientas.cargarImagen("menu.png");
		this.escala = 1;
		this.ancho = img.getWidth(e) * this.escala;
		this.alto = img.getHeight(e) * this.escala;
		this.x = e.ancho()-this.ancho/2;
		this.y = e.alto()-this.alto/2;
		
		
	}
	
	boolean encima(double x, double y) {
		return false;
		//return Math.abs(sigX - o.x) <= (e.ancho / 2.0 + o.ancho / 2.0) && Math.abs(sigY - o.y) <= (e.alto / 2.0 + o.alto / 2.0);

	}
	
	public double obtenerAncho() {
        return ancho;
    }
	
	public double obtenerAlto() {
        return alto;
    }
	
	void dibujar() {
		e.dibujarImagen(img, x, y, 0, escala);
	}
}
