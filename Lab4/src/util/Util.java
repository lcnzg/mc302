package util;

import base.cartas.Lacaio;

public class Util {

	public static final int MAX_CARDS = 30;

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

}