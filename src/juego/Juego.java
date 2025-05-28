package juego;


import java.awt.Color;
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
	Proyectil poderes;
	Menu menu;
	Color rojo,azul;
	
	int direccion_mago = 2; // El personaje mira hacia abajo por defectoa
	
	
	
	Juego() {
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Proyecto para TP", 800, 600);
		this.background = Herramientas.cargarImagen("background.png");
		// Inicializar lo que haga falta para el juego
		menu = new Menu(entorno);
		mago = new Personaje(entorno.ancho()/2, entorno.alto()/2, entorno);
		poderes = new Proyectil(40,20,1.0);
		
		
		piedras = new Obstaculo[6];
		
		int[] coorX = {100, 100, 500, 500, 210, 390};
		int[] coorY = {100, 500, 100, 500, 210, 390};
		
		
		// GENERAR PIEDRAS
		for (int t = 0;t<piedras.length;t++) {
			piedras[t] = new Obstaculo(coorX[t],coorY[t],entorno);				
		}
		
		rojo = new Color(255,0,0);
		azul = new Color(0,0,255);
		
		String[] anim_murcielago = { "bat1.png", "bat2.png", "bat3.png", "bat2.png"};
		murcielagos = new Enemigo[2];
		murcielagos[0] = new Enemigo(500, 420, entorno, 10, anim_murcielago);
		murcielagos[1] = new Enemigo(600, 0, entorno, 10, anim_murcielago);
		
		this.entorno.iniciar(); // Inicia el juego!

	}
	public void tick() {
		if(mago==null) {
			entorno.dibujarImagen(background, entorno.ancho()/2, entorno.alto()/2, 0, 1.0);
			entorno.cambiarFont("Arial", 30, java.awt.Color.WHITE);
	        entorno.escribirTexto("PERDISTE", 320, 300);
			return;
		}
		
		entorno.dibujarImagen(background, entorno.ancho()/2, entorno.alto()/2, 0, 1.0); // (Imagen, ancho, alto, angulo, escala)
		mago.dibujar(mago.direcciones[direccion_mago]);
		
		if(entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO)) {
			disparar(entorno.mouseX(), entorno.mouseY(), poderes, mago);
		}
		
		
		// VERIFICA SI EL JUGADOR COLISIONA CON ALGUN OBSTACULO
		boolean colisiones = false;
		for(int i=0;i<piedras.length;i++) {
			piedras[i].dibujar();
			if(colision(mago,piedras[i])) {
				colisiones = true;
			}
		}
		
		// GENERA MURCIELAGOS Y DEFINE SU COMPORTAMIENTO
		for(int k=0;k<murcielagos.length;k++) {
			if(murcielagos[k]!=null) {
				murcielagos[k].dibujar();
				MovEnemigo(mago, murcielagos[k]);
				if(colisionEM(murcielagos[k], mago)) {
					mago.recibirDaño(murcielagos[k].daño);
					murcielagos[k] = null;
				}
				
			}
//			if(murcielagos[k].vida<=0) {
//				murcielagos[k] = null;
//			}
		}
		
		
		menu.dibujar();
		
		entorno.dibujarRectangulo(700, 495, 130, 30, 0, rojo);
		entorno.dibujarRectangulo(700, 545, 130, 30, 0, azul);
		
		entorno.cambiarFont("Arial", 18, java.awt.Color.WHITE);
		entorno.escribirTexto("Vida: " +mago.vida+"/"+mago.vidaMaxima, 640, 500);
		entorno.escribirTexto("Magia: " +mago.magia+"/"+mago.magiaMaxima, 640, 550);
		
		
		// Permite el movimiento
		if(!colisiones && !colision(mago)) {
			if(entorno.estaPresionada(entorno.TECLA_ARRIBA) || entorno.estaPresionada('W')) {
				mago.mover(0,-mago.velocidad);
				direccion_mago = 0;
			}
			if(entorno.estaPresionada(entorno.TECLA_IZQUIERDA) || entorno.estaPresionada('A')) {
				mago.mover(-mago.velocidad,0);
				direccion_mago = 1;
			}
			if(entorno.estaPresionada(entorno.TECLA_ABAJO) || entorno.estaPresionada('S')) {
				mago.mover(0,mago.velocidad);
				direccion_mago = 2;
			}
			if(entorno.estaPresionada(entorno.TECLA_DERECHA) || entorno.estaPresionada('D')) {
				mago.mover(mago.velocidad,0);
				direccion_mago = 3;
			}	
		}
		if(mago.vida<=0) {
			mago = null;
		}
	}
	
	
	//Colision entre el jugador y los bordes del entorno
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
	
	
	// Colision entre el jugador y un obstaculo
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
	
	
	//Colision entre un enemigo y el jugador
	boolean colisionEM(Enemigo e, Personaje o) { 
		double sigX = e.x;
		double sigY = e.y;
		
		return Math.abs(sigX - o.x) <= (e.ancho / 2.0 + o.ancho / 2.0) && Math.abs(sigY - o.y) <= (e.alto / 2.0 + o.alto / 2.0);
	}
	
	
	// Seguimiento del enemigo hacia el jugador
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
	
	
	
	void disparar(int x, int y, Proyectil p, Personaje player) {
		if(x>=0 && x<=entorno.ancho()-menu.ancho && y>=0 && y<=entorno.alto() && player.magia-p.coste>=0) {
			player.magia-=p.coste;
			System.out.println("Click");
		}
		
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
