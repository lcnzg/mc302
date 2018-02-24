package base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import base.cartas.Carta;
import util.Util;

public class Baralho {

	private ArrayList<Carta> vetorCartas;

	public Baralho() {
		vetorCartas = new ArrayList<Carta>();
	}

	public void adicionarCarta(Carta card) {

		// Verifica se nao atingiu o limite de cartas
		if (vetorCartas.size() < Util.MAX_CARDS) {
			vetorCartas.add(card);
		}
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

	/**
	 * Metodo que criar certa quantidade de cartas aleatorias no baralho
	 * 
	 * @param gerador
	 *            Objeto da classe random, usado para gerar numeros aleatorios
	 * 
	 * @param tamanho
	 *            Numero de cartas a ser geradas e adicionadas ao baralho
	 * 
	 * @param maxMana
	 *            Numero que contem o teto de mana para as cartas geradas
	 * 
	 * @param maxAtaque
	 *            Numero que contem o teto de ataque para as cartas geradas
	 * 
	 * @param maxVida
	 *            Numero que contem o teto de vida para as cartas geradas
	 *
	 */

	public void preencheAleatorio(Random gerador, int tamanho, int maxMana, int maxAtaque, int maxVida) {
		for (int i = 0; i < Util.MAX_CARDS && i < tamanho; i++) {
			this.adicionarCarta(Util.geraCartaAleatoria(gerador, maxMana, maxAtaque, maxVida, null));
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