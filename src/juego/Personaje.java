package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Personaje {
	double x,y,escala,ancho,alto,velocidad;
	Image img,iW,iA,iS,iD;
	String[] direcciones= {"Arriba","Izquierda","Abajo","Derecha"};;
	Entorno e;
	public int vida,magia;
	public int magiaMaxima=100;
	public int vidaMaxima=100;
	
	
	Personaje(double x, double y, Entorno e) {
		this.x = x;
		this.y = y;
		this.iW = Herramientas.cargarImagen("wizardW.png");
		this.iA = Herramientas.cargarImagen("wizardA.png");
		this.iS = Herramientas.cargarImagen("wizardS.png");
		this.iD = Herramientas.cargarImagen("wizardD.png");

		this.img = iA;
		this.escala = 1; // Se puede cambiar
		this.velocidad = 1.2; // Se puede cambiar
		this.e = e;
		this.ancho = img.getWidth(e) * this.escala;
		this.alto = img.getHeight(e) * this.escala;
		this.vida = this.vidaMaxima;
		this.magia = this.magiaMaxima;
		
	}
	
	void dibujar(String direccion) { // Dibujar al personaje en funcion de la direccion
		if(direccion.equals(direcciones[0])) {
			img = iW;
		} else if(direccion.equals(direcciones[1])) {
			img = iA;
		} else if(direccion.equals(direcciones[2])) {
			img = iS;
		} else if(direccion.equals(direcciones[3])) {
			img = iD;
		}
		e.dibujarImagen(img, x, y, 0, escala);
	}
	
	void mover(double mH, double mV) { // Movimiento Horizontal y Vertical
		this.x += mH;
		this.y += mV;
	}
	
	void recibirDaño(int cantidad) {
		vida -= cantidad;
		if(vida<0) vida=0;
	}
}
