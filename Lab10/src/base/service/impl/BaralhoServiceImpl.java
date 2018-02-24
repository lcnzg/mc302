package base.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import base.cartas.Carta;
import base.exception.BaralhoVazioException;
import base.service.BaralhoService;
import base.service.CartaService;
import util.Util;

public class BaralhoServiceImpl implements BaralhoService{

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
	 * @return Lista de cartas geradas
	 *
	 */

	public List<Carta> preencheAleatorio(Random gerador, int tamanho, int maxMana, int maxAtaque, int maxVida) {
		CartaService cartaService = new CartaServiceImpl();
		ArrayList<Carta> lista = new ArrayList<Carta>();		
		
		for (int i = 0; i < Util.MAX_CARDS && i < tamanho; i++) {			
			lista.add(cartaService.geraCartaAleatoria(gerador, maxMana, maxAtaque, maxVida, null));
		}
		
		if (lista.size()==0){
			throw new BaralhoVazioException("Baralho Vazio");
		}
		
		return lista;
	}	
}