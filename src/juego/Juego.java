package juego;


import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
	private Entorno entorno; // El objeto Entorno que controla el tiempo y otros
	Image background;
	boolean ganar = false;
	
	Personaje mago;
	int direccion_mago = 2; // El personaje mira hacia abajo por defecto
	
	Proyectil proyectiles[];
	String[] anim_poderes = {"agua.png","fuego.png"}; // Imagenes de los hechizos lanzados
	
	Obstaculo piedras[];
	
	String[] anim_murcielago = {"bat1.png", "bat2.png", "bat3.png", "bat2.png"}; // Imagenes de animaciones del murcielagos
	Enemigo murcielagos[];
	int mEliminados = 0; // Cantidad de murcielagos eliminados
	int maxMurcielagos = 50; // Cantidad máxima de murcielagos
	int maxMSimultaneos = 10; // Cantidad máxima de murcielagos en pantalla
	
	Menu menu;
	Boton botones[];
	String[] poderes = {"poder1.png", "poder2.png"}; // Imagenes de los botones
	int botonActivo=-1; // Indica cual boton está activo, por defecto, ninguno
	
	// COLORES
	Color rojo = new Color(255,0,0);
	Color azul = new Color(0,0,255);
	Color marron = new Color(62,50,35);

	
	Juego() {
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Proyecto para TP", 800, 600);
		this.background = Herramientas.cargarImagen("background.png");
		// Inicializar lo que haga falta para el juego
		
		// CREAR PERSONAJE
		mago = new Personaje(entorno.ancho()/2, entorno.alto()/2, entorno);
		
		// GENERAR PIEDRAS
		piedras = new Obstaculo[6];
		
		int[] coorX = {100, 100, 500, 500, 210, 390};
		int[] coorY = {100, 500, 100, 500, 210, 390};
		
		for (int t = 0;t<piedras.length;t++) {
			piedras[t] = new Obstaculo(coorX[t],coorY[t],entorno);				
		}
		
		// CREAR MENU
		menu = new Menu(entorno);

		// CREAR BOTONES
		botones = new Boton[2];
		botones[0] = new Boton(700, 100, entorno, poderes[0], "Bomba de agua");
		botones[1] = new Boton(700, 225, entorno, poderes[1], "Bomba de fuego");

		// CREAR PODERES
		proyectiles = new Proyectil[botones.length];
		proyectiles[0] = new Proyectil(botones[0].obtenerNombre(), 40, 0, entorno, anim_poderes[0], 0.15);
		proyectiles[1] = new Proyectil(botones[1].obtenerNombre(), 60, 10, entorno, anim_poderes[1], 0.55);
		
		// DEFINIR EL ARREGLO DE MURCIELAGOS
		murcielagos = new Enemigo[maxMurcielagos];
		if(maxMurcielagos < maxMSimultaneos) maxMSimultaneos=maxMurcielagos;
		
		// INICIAR EL JUEGO
		this.entorno.iniciar(); 

	}
	
	public void tick() {
		// DIBUJAR FONDO
		entorno.dibujarImagen(background, entorno.ancho()/2, entorno.alto()/2, 0, 1.0);
		// CONDICIONES DE FIN DE JUEGO
		if(mago==null) {
			entorno.cambiarFont("Times New Roman", 100, java.awt.Color.RED);
	        entorno.escribirTexto("PERDISTE", 160, 325);
			return;
		}
		else if(ganar){
			entorno.cambiarFont("Times New Roman", 100, java.awt.Color.RED);
	        entorno.escribirTexto("GANASTE", 160, 325);
			return;
		}
		
		// DIBUJAR PERSONAJE
		mago.dibujar(mago.obtenerDirecciones(direccion_mago));
		
		// REGENERACION NATURAL DE MAGIA
		if(entorno.numeroDeTick()%50==0) mago.ganarMagia(1);
		
		// VERIFICA SI EL JUGADOR COLISIONA CON ALGUN OBSTACULO
		boolean colisiones = false;
		for (Obstaculo piedra : piedras) {
		    piedra.dibujar();
		    if (colision(mago, piedra)) {
		        colisiones = true;
		    }
		}
		// MOVIMIENTO DEL PERSONAJE
		if(!colisiones && !colision(mago)) {
			if(entorno.estaPresionada(entorno.TECLA_ARRIBA) || entorno.estaPresionada('W')) {
				mago.mover(0,-mago.obtenerVelocidad());
				direccion_mago = 0;
			}
			if(entorno.estaPresionada(entorno.TECLA_IZQUIERDA) || entorno.estaPresionada('A')) {
				mago.mover(-mago.obtenerVelocidad(),0);
				direccion_mago = 1;
			}
			if(entorno.estaPresionada(entorno.TECLA_ABAJO) || entorno.estaPresionada('S')) {
				mago.mover(0,mago.obtenerVelocidad());
				direccion_mago = 2;
			}
			if(entorno.estaPresionada(entorno.TECLA_DERECHA) || entorno.estaPresionada('D')) {
				mago.mover(mago.obtenerVelocidad(),0);
				direccion_mago = 3;
			}	
		}
		
		
		// LANZAMIENTO DE HECHIZOS
		if (botonActivo >= 0 
	        && entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO)
	        && mouseEnPantalla(entorno.mouseX(), entorno.mouseY())
	        && mago.obtenerMagia()-proyectiles[botonActivo].obtenerCoste()>=0) {

	        mago.perderMagia(proyectiles[botonActivo].obtenerCoste());
	        
	        proyectiles[botonActivo].lanzar(entorno.mouseX(), entorno.mouseY(), 10);
	        for (int k = 0; k < murcielagos.length; k++) { 
	            if (murcielagos[k] != null && proyectiles[botonActivo].pRadio(murcielagos[k].x, murcielagos[k].y)) {
	            	murcielagos[k].recibirDaño(proyectiles[botonActivo].obtenerDaño());
	            }
	        }
			        
		}
	    
	    // GENERACION DE MURCIELAGOS
	    int vivos = 0;
	    for (int i = 0; i < murcielagos.length; i++) {
	        if (murcielagos[i] != null) vivos++;
	    }
	    if (entorno.numeroDeTick() % 20 == 0 && (vivos + mEliminados) < maxMurcielagos) {
	        int cM = 0;
	        boolean genM = false;
	        while (cM < maxMSimultaneos && !genM) {
	            if (murcielagos[cM] == null) {
	                int[] pos = Enemigo.coorFuera(entorno.ancho(), entorno.alto(), menu.obtenerAncho());
	                murcielagos[cM] = new Enemigo(pos[0], pos[1], entorno, 10, 40, anim_murcielago);
	                genM = true;
	            }
	            cM++;
	        }
	    }
	    
	    // COMPORTAMIENTO DE LOS MURCIELAGOS
	    for (int k = 0; k < murcielagos.length; k++) {
	        if (murcielagos[k] != null) {
	        	murcielagos[k].dibujar();
	        	murcielagos[k].MovEnemigo(mago.obtenerPosX(), mago.obtenerPosY());
	            if (colisionEM(murcielagos[k], mago)) {
	                mago.recibirDaño(murcielagos[k].obtenerDaño());
	                murcielagos[k] = null;
	            }
	            if(murcielagos[k] != null && murcielagos[k].estaMuerto()) {
					murcielagos[k] = null;
					mEliminados++;
				}
	        }
	    }
		
	    // DIBUJAR HECHIZOS
	    for (Proyectil p : proyectiles) { 
	        if (p.estaActivo()) {
	            p.dibujar();
	        }
	    }
	    
	    // DIBUJAR PANEL
		menu.dibujar(); 
		
		// DIBUJAR BOTON Y DEFINIR COMPORTAMIENTO
		if(entorno.sePresiono('1')) botonActivo = 0;
		if(entorno.sePresiono('2')) botonActivo = 1;
		for(int b=0;b<botones.length;b++) {
			botones[b].dibujar();
			if(entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO) && botones[b].encima(entorno.mouseX(), entorno.mouseY())) {
				botonActivo = b;
			}
			
		}
		for (int b = 0; b < botones.length; b++) {
			if (b == botonActivo) {
				botones[b].cambiarEscala(0.32);;
			} else {
				botones[b].cambiarEscala(0.35);;
			}
		}
		if(botonActivo>=0) {
			entorno.cambiarFont("Times New Roman", 22, marron, 1);
			entorno.escribirTexto(botones[botonActivo].obtenerNombre(), 625, 450);
		}
		
		// DIBUJAR TEXTO Y BARRAS DE VIDA Y MAGIA
		double barraVida = (mago.obtenerVida() / (double)mago.obtenerVidaMax()) * 130;
		double barraMagia = (mago.obtenerMagia() / (double)mago.obtenerMagiaMax()) * 130;
		
		entorno.dibujarRectangulo(700-(130-barraVida)/2, 495, barraVida, 30, 0, rojo);
		entorno.dibujarRectangulo(700-(130-barraMagia)/2, 545, barraMagia, 30, 0, azul);
		
		entorno.cambiarFont("Arial", 18, java.awt.Color.WHITE);
		entorno.escribirTexto("Vida: " + mago.obtenerVida() + "/" + mago.obtenerVidaMax(), 640, 500);
		entorno.escribirTexto("Magia: " + mago.obtenerMagia() + "/" + mago.obtenerMagiaMax(), 640, 550);
		entorno.cambiarFont("Arial", 18, java.awt.Color.RED);
		entorno.escribirTexto("Murcuelagos eliminados: "+mEliminados+"/"+maxMurcielagos, 20, 580);
		
		// CONDICION DE PERDER
		if(mago.obtenerVida()<=0) {
			mago = null;
		}
		
		// CONDICION DE GANAR
		if(mEliminados==maxMurcielagos) ganar = true;
	}
	
	
	// COLISION ENTRE EL JUGADOR Y EL ENTORNO
	boolean colision(Personaje p) {
		double sigX = p.obtenerPosX();
		double sigY = p.obtenerPosY();
		
		if(entorno.estaPresionada(entorno.TECLA_DERECHA) || entorno.estaPresionada('D')) {
			sigX += p.obtenerVelocidad();
		}
		if(entorno.estaPresionada(entorno.TECLA_IZQUIERDA) || entorno.estaPresionada('A')) {
			sigX -= p.obtenerVelocidad();
		}
		if(entorno.estaPresionada(entorno.TECLA_ARRIBA) || entorno.estaPresionada('W')) {
			sigY -= p.obtenerVelocidad();
		}
		if(entorno.estaPresionada(entorno.TECLA_ABAJO) || entorno.estaPresionada('S')) {
			sigY += p.obtenerVelocidad();
		}
		return (sigX-p.obtenerAncho()/2 < 0 || sigX+p.obtenerAncho()/2 > entorno.ancho()-menu.obtenerAncho() || sigY-p.obtenerAlto()/2 < 0 || sigY+p.obtenerAlto()/2 > entorno.alto());
	}
	
	
	// Colision entre el jugador y un obstaculo
	boolean colision(Personaje p, Obstaculo o) { // Evalua si, en el siguiente movimiento, existe una interseccion.
		double sigX = p.obtenerPosX();
		double sigY = p.obtenerPosY();
		
		if (entorno.estaPresionada(entorno.TECLA_DERECHA) || entorno.estaPresionada('D')) {
	        sigX += p.obtenerVelocidad();
	        return Math.abs(sigX - o.obtenerPosX()) <= (p.obtenerAncho() / 2.0 + o.obtenerAncho() / 2.0) && Math.abs(p.obtenerPosY() - o.obtenerPosY()) <= (p.obtenerAlto() / 2.0 + o.obtenerAlto() / 2.0);
	    }
	    if (entorno.estaPresionada(entorno.TECLA_IZQUIERDA) || entorno.estaPresionada('A')) {
	        sigX -= p.obtenerVelocidad();
	        return Math.abs(sigX - o.obtenerPosX()) <= (p.obtenerAncho() / 2.0 + o.obtenerAncho() / 2.0) && Math.abs(p.obtenerPosY() - o.obtenerPosY()) <= (p.obtenerAlto() / 2.0 + o.obtenerAlto() / 2.0);
	    }
	    if (entorno.estaPresionada(entorno.TECLA_ARRIBA) || entorno.estaPresionada('W')) {
	        sigY -= p.obtenerVelocidad();
	        return Math.abs(p.obtenerPosX() - o.obtenerPosX()) <= (p.obtenerAncho() / 2.0 + o.obtenerAncho() / 2.0) && Math.abs(sigY - o.obtenerPosY()) <= (p.obtenerAlto() / 2.0 + o.obtenerAlto() / 2.0);
	    }
	    if (entorno.estaPresionada(entorno.TECLA_ABAJO) || entorno.estaPresionada('S')) {
	        sigY += p.obtenerVelocidad();
	        return Math.abs(p.obtenerPosX() - o.obtenerPosX()) <= (p.obtenerAncho() / 2.0 + o.obtenerAncho() / 2.0) && Math.abs(sigY - o.obtenerPosY()) <= (p.obtenerAlto() / 2.0 + o.obtenerAlto() / 2.0);
	    }
	    return false;
	}
	
	
	//Colision entre un enemigo y el jugador
	boolean colisionEM(Enemigo e, Personaje p) { 
		double sigX = e.x;
		double sigY = e.y;
		
		return Math.abs(sigX-p.obtenerPosX())<=(e.ancho/2 + p.obtenerAncho()/2) && Math.abs(sigY-p.obtenerPosY())<=(e.alto/2 + p.obtenerAlto()/2);
	}
	
	boolean mouseEnPantalla(double x, double y) {
		return (x>=0 && x<=entorno.ancho()-menu.obtenerAncho() && y>=0 && y<=entorno.alto());
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
