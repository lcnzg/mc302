//import com.Luciano.base.Baralho;
import com.Luciano.base.BaralhoArrayList;
import com.Luciano.base.CartaLacaio;
import com.Luciano.util.Util;

public class Main {

	public static void main(String[] args) {

		// Instanciando objetos
		CartaLacaio lac1 = new CartaLacaio(1, "Frodo Bolseiro", 2, 1, 1);
		CartaLacaio lac2 = new CartaLacaio(2, "Aragorn", 5, 7, 6);
		CartaLacaio lac3 = new CartaLacaio(3, "Legolas", 8, 4, 6);
		CartaLacaio lac4 = new CartaLacaio(4, "Eurico Sampaio", 9, 4, 8);
		CartaLacaio lac5 = new CartaLacaio(5, "Aldonça Calheiros", 10, 6, 7);
		CartaLacaio lac6 = new CartaLacaio(6, "Feliciana Garrido", 11, 9, 3);
		CartaLacaio lac7 = new CartaLacaio(7, "Teodorico Canhão", 12, 3, 2);
		CartaLacaio lac8 = new CartaLacaio(8, "Miguelina Alencar", 13, 5, 7);
		CartaLacaio lac9 = new CartaLacaio(9, "Pascoal Parracho", 14, 6, 6);
		CartaLacaio lac10 = new CartaLacaio(10, "Emídio Carrasqueira", 15, 13, 9);

		//Baralho bar1 = new Baralho();
		BaralhoArrayList bar2 = new BaralhoArrayList();

		// Adiciona cartas no baralho

		/*
		bar1.adicionarCarta(lac1);
		bar1.adicionarCarta(lac2);
		bar1.adicionarCarta(lac3);
		bar1.adicionarCarta(lac4);
		bar1.adicionarCarta(lac5);
		bar1.adicionarCarta(lac6);
		bar1.adicionarCarta(lac7);
		bar1.adicionarCarta(lac8);
		bar1.adicionarCarta(lac9);
		bar1.adicionarCarta(lac10);
		*/

		bar2.adicionarCarta(lac1);
		bar2.adicionarCarta(lac2);
		bar2.adicionarCarta(lac3);
		bar2.adicionarCarta(lac4);
		bar2.adicionarCarta(lac5);
		bar2.adicionarCarta(lac6);
		bar2.adicionarCarta(lac7);
		bar2.adicionarCarta(lac8);
		bar2.adicionarCarta(lac9);

		// Comprar cartas
		System.out.println("Carta comprada:\n");
		// System.out.println(bar1.comprarCarta());
		System.out.println(bar2.comprarCarta());

		// Buffa a carta
		System.out.println("Buffa as cartas:\n");
		Util.buffar(lac9, 5);
		Util.buffar(lac10, 3, -1);

		System.out.println(lac9);
		System.out.println(lac10);

		bar2.adicionarCarta(lac9);
		bar2.adicionarCarta(lac10);

		// Embaralha
		System.out.println("Embaralhando as cartas:\n");
		// bar1.embaralhar();
		bar2.embaralhar();

	}

}