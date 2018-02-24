package com.Luciano.base;

import java.util.Random;
import com.Luciano.util.Util;

public class Baralho {

	CartaLacaio[] vetorCartas;
	int nCartas;
	static Random gerador = new Random();

	public Baralho() {

		vetorCartas = new CartaLacaio[10];
		nCartas = 0;
	}

	public void adicionarCarta(CartaLacaio card) {

		// Verifica se nao atingiu o limite de cartas
		if (nCartas < Util.MAX_CARDS) {

			vetorCartas[nCartas] = card;
			nCartas++;

		}
	}

	public CartaLacaio comprarCarta() {
		nCartas--;

		return vetorCartas[nCartas];
	}

	public void embaralhar() {
		int i, j;

		for (i = 1; i < nCartas; i++) {

			j = gerador.nextInt(i + 1);

			if (i != j) {
				CartaLacaio a = vetorCartas[i];
				CartaLacaio b = vetorCartas[j];
				vetorCartas[i] = b;
				vetorCartas[j] = a;
			}

		}
		// Imprime em ordem reversa
		for (i = nCartas - 1; i >= 0; i--) {
			System.out.println(vetorCartas[i]);
		}
	}
}