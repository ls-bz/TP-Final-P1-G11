package juego;


//import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
	private Entorno entorno; // El objeto Entorno que controla el tiempo y otros
	Image background;
	Personaje mago;
	Obstaculo piedras[];
	Enemigo murcielagos[];
	Menu menu;
	
	
	Juego() {
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Proyecto para TP", 800, 600);
		this.background = Herramientas.cargarImagen("background.png");
		// Inicializar lo que haga falta para el juego
		menu = new Menu(entorno);
		mago = new Personaje(entorno.ancho()/2, entorno.alto()/2, entorno);
		
		piedras = new Obstaculo[6];
		piedras[0] = new Obstaculo(100, 100, entorno); 
		piedras[1] = new Obstaculo(100, 500, entorno); 
		piedras[2] = new Obstaculo(500, 100, entorno); 
		piedras[3] = new Obstaculo(500, 500, entorno); 
		piedras[4] = new Obstaculo(210, 210, entorno); 
		piedras[5] = new Obstaculo(390, 390, entorno); 

		murcielagos = new Enemigo[2];
		murcielagos[0] = new Enemigo(500, 420, entorno, 20);
		murcielagos[1] = new Enemigo(600, 0, entorno, 20);
		
		this.entorno.iniciar(); // Inicia el juego!

	}
	public void tick() {
		entorno.dibujarImagen(background, entorno.ancho()/2, entorno.alto()/2, 0, 1.0); // (Imagen, ancho, alto, angulo, escala)
		mago.dibujar();
		
		boolean colisiones = false;
		for(int i=0;i<piedras.length;i++) {
			piedras[i].dibujar();
			if(colision(mago,piedras[i])) {
				colisiones = true;
			}
		}
		for(int k=0;k<murcielagos.length;k++) {
			murcielagos[k].dibujar();
			MovEnemigo(mago, murcielagos[k]);
		}
		menu.dibujar();
		
		if(!colisiones && !colision(mago)) { // else if impide el movimento en diagonal
			if(entorno.estaPresionada(entorno.TECLA_DERECHA) || entorno.estaPresionada('D')) {
				mago.mover(mago.velocidad,0);
			}
			if(entorno.estaPresionada(entorno.TECLA_IZQUIERDA) || entorno.estaPresionada('A')) {
				mago.mover(-mago.velocidad,0);
			}
			if(entorno.estaPresionada(entorno.TECLA_ARRIBA) || entorno.estaPresionada('W')) {
				mago.mover(0,-mago.velocidad);
			}
			if(entorno.estaPresionada(entorno.TECLA_ABAJO) || entorno.estaPresionada('S')) {
				mago.mover(0,mago.velocidad);
			}
		}
		
		
	}
	
	boolean colision(Personaje p) {
		double sigX = p.x;
		double sigY = p.y;
		
		if(entorno.estaPresionada(entorno.TECLA_DERECHA) || entorno.estaPresionada('D')) {
			sigX += p.velocidad;
		}
		if(entorno.estaPresionada(entorno.TECLA_IZQUIERDA) || entorno.estaPresionada('A')) {
			sigX -= p.velocidad;
		}
		if(entorno.estaPresionada(entorno.TECLA_ARRIBA) || entorno.estaPresionada('W')) {
			sigY -= p.velocidad;
		}
		if(entorno.estaPresionada(entorno.TECLA_ABAJO) || entorno.estaPresionada('S')) {
			sigY += p.velocidad;
		}
		return (sigX-p.ancho/2 < 0 || sigX+p.ancho/2 > entorno.ancho()-menu.ancho || sigY-p.alto/2 < 0 || sigY+p.alto/2 > entorno.alto());
	}
	
	
	boolean colision(Personaje p, Obstaculo o) { // Evalua si, en el siguiente movimiento, existe una interseccion.
		double sigX = p.x;
		double sigY = p.y;
		
		if (entorno.estaPresionada(entorno.TECLA_DERECHA) || entorno.estaPresionada('D')) {
	        sigX += p.velocidad;
	        return Math.abs(sigX - o.x) <= (p.ancho / 2.0 + o.ancho / 2.0) && Math.abs(p.y - o.y) <= (p.alto / 2.0 + o.alto / 2.0);
	    }
	    if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA) || entorno.estaPresionada('A')) {
	        sigX -= p.velocidad;
	        return Math.abs(sigX - o.x) <= (p.ancho / 2.0 + o.ancho / 2.0) && Math.abs(p.y - o.y) <= (p.alto / 2.0 + o.alto / 2.0);
	    }
	    if (entorno.estaPresionada(entorno.TECLA_ARRIBA) || entorno.estaPresionada('W')) {
	        sigY -= p.velocidad;
	        return Math.abs(p.x - o.x) <= (p.ancho / 2.0 + o.ancho / 2.0) && Math.abs(sigY - o.y) <= (p.alto / 2.0 + o.alto / 2.0);
	    }
	    if (entorno.estaPresionada(entorno.TECLA_ABAJO) || entorno.estaPresionada('S')) {
	        sigY += p.velocidad;
	        return Math.abs(p.x - o.x) <= (p.ancho / 2.0 + o.ancho / 2.0) && Math.abs(sigY - o.y) <= (p.alto / 2.0 + o.alto / 2.0);
	    }
	    return false;
	}
	
	void MovEnemigo(Personaje p, Enemigo e) {
		if(p.x<e.x) {
			e.mover(-e.velocidad, 0);
		} else if(p.x>e.x){
			e.mover(e.velocidad, 0);
		}
		if(p.y>e.y) {
			e.mover(0,e.velocidad);
		} else if(p.y<e.y){
			e.mover(0,-e.velocidad);
		}
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
