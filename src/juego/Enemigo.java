package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Enemigo {
	double x,y,escala,ancho,alto,velocidad;
	int daño;
	Image animaciones[];
	Entorno e;
	int animCont = 0;
	public int vidaMaxima=40;
	public int vida;
	
	
	Enemigo(double x, double y, Entorno e, int daño, String[] imagenes) {
		this.x = x;
		this.y = y;
		this.animaciones = new Image[imagenes.length];
        for (int i = 0;i<imagenes.length;i++) {
            this.animaciones[i] = Herramientas.cargarImagen(imagenes[i]);
        }
		this.escala = 1.4; // Se puede cambiar
		this.velocidad = 0.75; // Se puede cambiar
		this.e = e;
		this.ancho = animaciones[0].getWidth(e) * this.escala; // Asume que todas las anim. tienen el mismo tamaño
		this.alto = animaciones[0].getHeight(e) * this.escala;
		this.daño = daño;
		this.vida = this.vidaMaxima;
	}
	
	void dibujar() { 
		Image img = animaciones[animCont];
		e.dibujarImagen(img, x, y, 0, escala);
		
		if(e.numeroDeTick()%18==0 && animCont < animaciones.length-1) {
			animCont++;
		} 
		else if(e.numeroDeTick()%9==0 && animCont >= animaciones.length-1) {
			animCont=0;
		}
	}
	
	void mover(double mH, double mV) { // Movimiento Horizontal y Vertical
		this.x += mH;
		this.y += mV;
	}
	
	boolean estaMuerto() {
		return (this.vida <= 0);
	}
	
	void generacion() {
		
	}
}
