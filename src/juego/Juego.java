package juego;


//import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	Image background;
	
	// Variables y métodos propios de cada grupo
	// ...
	Personaje mago;
	Obstaculo piedra;
	
	
	Juego() {
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Proyecto para TP", 800, 600);
		this.background = Herramientas.cargarImagen("background.png");
	
		// Inicializar lo que haga falta para el juego
		mago = new Personaje(entorno.ancho()/2, entorno.alto()/2, entorno);
		this.piedra = new Obstaculo(320, 150, entorno);
		// Inicia el juego!
		this.entorno.iniciar();
		

	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y 
	 * por lo tanto es el método más importante de esta clase. Aquí se debe 
	 * actualizar el estado interno del juego para simular el paso del tiempo 
	 * (ver el enunciado del TP para mayor detalle).
	 */
	public void tick() {
		
		entorno.dibujarImagen(background, entorno.ancho()/2, entorno.alto()/2, 0, 1.0); // (Imagen, ancho, alto, angulo, escala)
		piedra.dibujar();
		mago.dibujar();
		
		if(!colision(mago,piedra) && !colision(mago)) { // else if impide el movimento en diagonal
			if(entorno.estaPresionada(entorno.TECLA_DERECHA) || entorno.estaPresionada('D')) {
				mago.mover(mago.velocidad,0);
			}
			else if(entorno.estaPresionada(entorno.TECLA_IZQUIERDA) || entorno.estaPresionada('A')) {
				mago.mover(-mago.velocidad,0);
			}
			else if(entorno.estaPresionada(entorno.TECLA_ARRIBA) || entorno.estaPresionada('W')) {
				mago.mover(0,-mago.velocidad);
			}
			else if(entorno.estaPresionada(entorno.TECLA_ABAJO) || entorno.estaPresionada('S')) {
				mago.mover(0,mago.velocidad);
			}
		}

		// Procesamiento de un instante de tiempo
		
	}
	
	
	
	boolean colision(Personaje p) { // Misma logica que el anterior
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
		return (sigX-p.ancho/2 < 0 || sigX+p.ancho/2 > 800 || sigY-p.alto/2 < 0 || sigY+p.alto/2 > 600);
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
	
	@SuppressWarnings("unused")
	public static void main(String[] args)
	{
		Juego juego = new Juego();
	}
}
