package util;

import java.util.Random;

import util.RandomString;
import base.cartas.Carta;
import base.cartas.HabilidadesLacaio;
import base.cartas.Lacaio;
import base.cartas.TipoCarta;
import base.cartas.magias.Buff;
import base.cartas.magias.Dano;
import base.cartas.magias.DanoArea;

public class Util {

	public static final int MAX_CARDS = 30;
	public static final int MAX_NOME = 2;
	public static final int PODER_HEROI = 30;
	public static final int MAO_INI = 3;

	public static void buffar(Lacaio lac, int a) {
		buffar(lac, a, a);
	}

	public static void buffar(Lacaio lac, int a, int v) {

		if (a > 0) {
			lac.setAtaque(lac.getAtaque() + a);
		}

		if (v > 0) {
			lac.setVidaAtual(lac.getVidaAtual() + v);
			lac.setVidaMaxima(lac.getVidaMaxima() + v);
		}

		if (v > 0 || a > 0) {
			alteraNomeFortalecido(lac);
		}
	}

	private static void alteraNomeFortalecido(Lacaio lac) {
		lac.setNome(lac.getNome() + " Buffed");
	}

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
	 */

	public static Carta geraCartaAleatoria(Random gerador, int maxMana, int maxAtaque, int maxVida, TipoCarta tc) {

		Carta cartaGerada;

		RandomString stringGerador = new RandomString(gerador, MAX_NOME);

		// Escolhe um tipo de carta aleatorio, caso nao especificado
		if (tc == null) {
			tc = TipoCarta.values()[randInt(gerador, 0, 3)];
		}

		// Instancia uma carta de buff aleatoria
		if (tc == TipoCarta.BUFF) {
			cartaGerada = new Buff(stringGerador.nextString(), randInt(gerador, 1, maxMana),
					randInt(gerador, 1, maxAtaque), randInt(gerador, 1, maxVida));
		}

		// Instancia uma carta de dano aleatoria
		else if (tc == TipoCarta.DANO) {
			cartaGerada = new Dano(stringGerador.nextString(), randInt(gerador, 1, maxMana),
					randInt(gerador, 1, maxAtaque));
		}

		// Instancia uma carta de dano em area aleatoria
		else if (tc == TipoCarta.DANO_AREA) {
			cartaGerada = new DanoArea(stringGerador.nextString(), randInt(gerador, 1, maxMana),
					randInt(gerador, 1, maxAtaque));
		}

		// Instancia uma carta lacaio aleatoria
		else {
			int a;
			cartaGerada = new Lacaio(stringGerador.nextString(), randInt(gerador, 1, maxMana),
					randInt(gerador, 1, maxAtaque), a = randInt(gerador, 1, maxVida), a,
					HabilidadesLacaio.values()[randInt(gerador, 0, 2)]);
		}

		return cartaGerada;
	}

	public static int randInt(Random gerador, int min, int max) {
		return gerador.nextInt((max - min) + 1) + min;
	}
}