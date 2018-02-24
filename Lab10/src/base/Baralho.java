package base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import base.cartas.Carta;
import base.service.CartaService;
import base.service.impl.CartaServiceImpl;
import util.Util;

public class Baralho {

	private ArrayList<Carta> vetorCartas;
	CartaService cartaService;

	public Baralho() {
		vetorCartas = new ArrayList<Carta>();
		cartaService = new CartaServiceImpl();
	}

	public void adicionarCarta(Carta card) {

		// Verifica se nao atingiu o limite de cartas
		if (vetorCartas.size() < Util.MAX_CARDS) {
			vetorCartas.add(card);
		}
	}
	
	public void addCartas(List<Carta> cartas) {

			vetorCartas.addAll(cartas);
	}
	

	public Carta comprar() {

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