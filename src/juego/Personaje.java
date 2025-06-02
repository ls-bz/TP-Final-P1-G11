package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Personaje {
	private double x,y,escala,ancho,alto,velocidad;
	private Image img,iW,iA,iS,iD;
	private String[] direcciones= {"Arriba","Izquierda","Abajo","Derecha"};;
	private Entorno e;
	private int vida,magia;
	private int magiaMaxima=100;
	private int vidaMaxima=100;
	
	
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
	
	public String obtenerDirecciones(int i) {
		if(i<0) i=0;
		if(i>3) i=3;
	    return direcciones[i];
	}
	
	void mover(double mH, double mV) { // Movimiento Horizontal y Vertical
		this.x += mH;
		this.y += mV;
	}
	
	void recibirDaño(int cantidad) {
		vida -= cantidad;
		if(vida<0) vida=0;
	}
	
	public int obtenerVida() {
        return vida;
    }
	
	public int obtenerMagia() {
        return magia;
    }
	
	public int obtenerVidaMax() {
        return vidaMaxima;
    }
	
	public int obtenerMagiaMax() {
        return magiaMaxima;
    }
	
	public void perderMagia(int coste) {
        this.magia -= coste;
        if(this.magia < 0) this.magia=0;
    }
	
	public void ganarMagia(int ganancia) {
        this.magia += ganancia;
        if(this.magia > this.magiaMaxima) this.magia=this.magiaMaxima;
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
	
	public double obtenerVelocidad() {
        return velocidad;
	}
	
	
	boolean sinMagia() {
		if(this.magia<=0) {
			this.magia=0;
			return true;
		}
		return false;
	}
}
