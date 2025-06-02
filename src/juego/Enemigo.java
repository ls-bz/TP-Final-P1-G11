package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Enemigo {
	double x,y,escala,ancho,alto,velocidad;
	private int daño;
	private int vida;
	private Image animaciones[];
	private Entorno e;
	private int animCont = 0;
	
	
	
	Enemigo(double x, double y, Entorno e, int daño, int vidaMax, String[] imagenes) {
		this.x = x;
		this.y = y;
		this.animaciones = new Image[imagenes.length];
        for (int i = 0;i<imagenes.length;i++) {
            this.animaciones[i] = Herramientas.cargarImagen(imagenes[i]);
        }
		this.escala = 1.4; // Se puede cambiar
		this.velocidad = 0.5; // Se puede cambiar
		this.e = e;
		this.ancho = animaciones[0].getWidth(e) * this.escala; // Asume que todas las anim. tienen el mismo tamaño
		this.alto = animaciones[0].getHeight(e) * this.escala;
		this.daño = daño;
		this.vida = vidaMax;
	}
	
	public void dibujar() { 
		Image img = animaciones[animCont];
		e.dibujarImagen(img, x, y, 0, escala);
		
		if(e.numeroDeTick()%18==0 && animCont < animaciones.length-1) {
			animCont++;
		} 
		else if(e.numeroDeTick()%9==0 && animCont >= animaciones.length-1) {
			animCont=0;
		}
	}
	
	void MovEnemigo(double objX, double objY) {
		if(objX<x) {
			mover(-velocidad, 0);
		} else if(objX>x){
			mover(velocidad, 0);
		}
		if(objY>y) {
			mover(0,velocidad);
		} else if(objY<y){
			mover(0,-velocidad);
		}
	}
	
	public void mover(double mH, double mV) { // Movimiento Horizontal y Vertical
		this.x += mH;
		this.y += mV;
	}
	
	public boolean estaMuerto() {
		return (this.vida <= 0);
	}
	
	public int obtenerVida() {
        return vida;
    }
	
	public int obtenerDaño() {
        return daño;
    }
	
	public void recibirDaño(int cantidad) {
		vida -= cantidad;
		if(vida<0) vida=0;
	}
	
	public static int[] coorFuera(double anchoPantalla, double altoPantalla, double anchoMenu) {
	    int x = 0;
	    int y = 0;

	    int borde = (int)(Math.random() * 4);
	    switch (borde) {
	        case 0: 
	            x = -50;
	            y = (int)(Math.random() * altoPantalla);
	            break;
	        case 1: 
	            x = (int) (anchoPantalla - anchoMenu + 50);
	            y = (int)(Math.random() * altoPantalla);
	            break;
	        case 2: 
	            x = (int)(Math.random() * (anchoPantalla - anchoMenu));
	            y = -50;
	            break;
	        case 3: 
	            x = (int)(Math.random() * (anchoPantalla - anchoMenu));
	            y = (int) (altoPantalla + 50);
	            break;
	    }
	    return new int[]{x, y};
	}

}
