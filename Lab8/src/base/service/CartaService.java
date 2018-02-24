package base.service;

import java.util.Random;

import base.cartas.Carta;
import base.cartas.TipoCarta;

public interface CartaService {

	public Carta geraCartaAleatoria(Random gerador, int maxMana, int maxAtaque, int maxVida, TipoCarta tc);

	public int randInt(Random gerador, int min, int max);
}