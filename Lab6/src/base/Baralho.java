package base;

import java.util.ArrayList;
import java.util.Collections;

//import util.Util;

public class Baralho {

	private ArrayList<Carta> vetorCartas;

	public Baralho() {

		vetorCartas = new ArrayList<Carta>();

	}

	public void adicionarCarta(Carta card) {

		// Verifica se nao atingiu o limite de cartas
		// if (vetorCartas.size() < Util.MAX_CARDS) {

		vetorCartas.add(card);

		// }
	}

	public Carta comprarCarta() {

		return vetorCartas.remove(vetorCartas.size() - 1);
	}

	public void embaralhar() {

		Collections.shuffle(vetorCartas);

		// Imprime em ordem reversa
		for (int i = vetorCartas.size() - 1; i >= 0; i--) {
			System.out.println(vetorCartas.get(i));
		}

	}

	@Override
	public String toString() {
		return vetorCartas.toString();
	}

	@Override
	public boolean equals(Object obj) {
		return vetorCartas.equals(((Baralho) obj).vetorCartas);
	}

	@Override
	public int hashCode() {
		return vetorCartas.hashCode();
	}

}