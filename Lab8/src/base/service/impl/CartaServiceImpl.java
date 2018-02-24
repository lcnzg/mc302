package base.service.impl;

import java.util.Random;

import base.cartas.Carta;
import base.cartas.HabilidadesLacaio;
import base.cartas.Lacaio;
import base.cartas.TipoCarta;
import base.cartas.magias.Buff;
import base.cartas.magias.Dano;
import base.cartas.magias.DanoArea;
import base.service.CartaService;
import util.RandomString;
import util.Util;

public class CartaServiceImpl implements CartaService {

	RandomString stringGerador;
	HabilidadesLacaio habilidade;
	TipoCarta escolhido;

	/**
	 * Metodo que criar uma carta aleatoria
	 * 
	 * @param gerador
	 *            Objeto da classe random, usado para gerar numeros aleatorios
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
	 * @param tc
	 *            Tipo da carta a ser gerada (null: aleatorio)
	 * 
	 * @return cartaGerada Carta gerada aleatoriamente
	 *
	 */

	public Carta geraCartaAleatoria(Random gerador, int maxMana, int maxAtaque, int maxVida, TipoCarta tc) {

		escolhido = tc;
		habilidade = HabilidadesLacaio.values()[randInt(gerador, 0, 2)];
		Carta cartaGerada;
		stringGerador = new RandomString(gerador, Util.MAX_NOME);

		// Escolhe um tipo de carta aleatorio, caso nao especificado
		if (escolhido == null) {
			escolhido = TipoCarta.values()[randInt(gerador, 0, 3)];
		}

		// Instancia uma carta de buff aleatoria
		if (escolhido == TipoCarta.BUFF) {
			cartaGerada = new Buff(stringGerador.nextString(), randInt(gerador, 1, maxMana),
					randInt(gerador, 1, maxAtaque), randInt(gerador, 1, maxVida));
		}

		// Instancia uma carta de dano aleatoria
		else if (escolhido == TipoCarta.DANO) {
			cartaGerada = new Dano(stringGerador.nextString(), randInt(gerador, 1, maxMana),
					randInt(gerador, 1, maxAtaque));
		}

		// Instancia uma carta de dano em area aleatoria
		else if (escolhido == TipoCarta.DANO_AREA) {
			cartaGerada = new DanoArea(stringGerador.nextString(), randInt(gerador, 1, maxMana),
					randInt(gerador, 1, maxAtaque));
		}

		// Instancia uma carta lacaio aleatoria
		else {
			int a;
			cartaGerada = new Lacaio(stringGerador.nextString(), randInt(gerador, 1, maxMana),
					randInt(gerador, 1, maxAtaque), a = randInt(gerador, 1, maxVida), a, habilidade);
		}

		return cartaGerada;
	}

	public int randInt(Random gerador, int min, int max) {
		return gerador.nextInt((max - min) + 1) + min;
	}
}