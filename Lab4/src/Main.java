
import base.*;
import base.cartas.*;
import base.cartas.magias.*;
import util.Util;

public class Main {

	public static void main(String[] args) {

		// Cria um novo baralho
		Baralho bar = new Baralho();

		// Instanciando objetos
		Carta lac1 = new Lacaio("Frodo Bolseiro", 2, 1, 1, 1);
		Carta buf1 = new Buff("Anel", 3, 2, 8);
		Carta dan1 = new Dano("Machado", 4, 7);
		DanoArea dan2 = new DanoArea("Olho de Sauron", 10, 3);

		// Adiciona cartas ao baralho
		bar.adicionarCarta(lac1);
		bar.adicionarCarta(buf1);
		bar.adicionarCarta(dan1);
		bar.adicionarCarta(dan2);

		// Imprime o baralho
		System.out.println(bar);
		
		System.out.println("*******************");
		dan2.usar(lac1);
		
		// Imprime o baralho
		System.out.println(bar);

	}

}