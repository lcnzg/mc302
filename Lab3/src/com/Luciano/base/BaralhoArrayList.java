package com.Luciano.base;

import java.util.ArrayList;
import java.util.Collections;

import com.Luciano.util.Util;

public class BaralhoArrayList {

	private ArrayList<CartaLacaio> vetorCartas;

	public BaralhoArrayList() {

		vetorCartas = new ArrayList<CartaLacaio>();

	}

	public void adicionarCarta(CartaLacaio card) {

		// Verifica se nao atingiu o limite de cartas
		if (vetorCartas.size() < Util.MAX_CARDS) {

			vetorCartas.add(card);

		}
	}

	public CartaLacaio comprarCarta() {

		return vetorCartas.remove(vetorCartas.size() - 1);
	}

	public void embaralhar() {

		Collections.shuffle(vetorCartas);

		// Imprime em ordem reversa
		// (decidi nao utilizar o reverse do Collections pois seria menos eficiente)
		for (int i = vetorCartas.size() - 1; i >= 0; i--) {
			System.out.println(vetorCartas.get(i));
		}

	}

}