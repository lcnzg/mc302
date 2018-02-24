import java.util.Random;

import base.Baralho;
import base.Jogada;
import base.Mesa;
import base.ProcessadorJogada;
import base.cartas.Carta;
import base.cartas.TipoCarta;
import base.cartas.magias.Buff;
import util.Util;

public class Main {

	public static void main(String[] args) {

		Random gerador = new Random();

		Baralho baralhoP = new Baralho();
		Baralho baralhoS = new Baralho();

		// Gera baralho aleatorio para ambos os jogadores
		baralhoP.preencheAleatorio(gerador, 30, 15, 15, 15);
		baralhoS.preencheAleatorio(gerador, 30, 15, 15, 15);

		Mesa mesaAtual = new Mesa();

		// Baixar lacaios iniciais
		for (int i = 0; i < 3; i++) {
			(mesaAtual.getLacaiosP()).add(Util.geraCartaAleatoria(gerador, 15, 15, 15, TipoCarta.LACAIO));
			(mesaAtual.getLacaiosS()).add(Util.geraCartaAleatoria(gerador, 15, 15, 15, TipoCarta.LACAIO));
		}

		// Comprar cartas iniciais
		for (int i = 0; i < Util.MAO_INI; i++) {
			(mesaAtual.getMaoP()).add(baralhoP.comprarCarta());
		}

		for (int i = 0; i < Util.MAO_INI + 1; i++) {
			(mesaAtual.getMaoS()).add(baralhoS.comprarCarta());
		}

		// Sacar carta para atacar heroi oponente (sem ser buff)
		Carta card = mesaAtual.sacarCarta('P');		
		while (card instanceof Buff == true){			
			(mesaAtual.getMaoP()).add(card);			
			card = mesaAtual.sacarCarta('P');
		}
		
		Jogada jogada1 = new Jogada(card, null, 'P');
		ProcessadorJogada.processar(jogada1, mesaAtual);
		
		card = mesaAtual.sacarCarta('S');		
		while (card instanceof Buff == true){			
			(mesaAtual.getMaoS()).add(card);			
			card = mesaAtual.sacarCarta('S');
		}
		
		Jogada jogada2 = new Jogada(card, null, 'S');
		ProcessadorJogada.processar(jogada2, mesaAtual);

		// Saca carta e usa em lacaio oponente
		if ((mesaAtual.getLacaiosS()).size() > 0) {
			Jogada jogada3 = new Jogada(mesaAtual.sacarCarta('P'), (mesaAtual.getLacaiosS()).get(0), 'P');
			ProcessadorJogada.processar(jogada3, mesaAtual);
		}

		if ((mesaAtual.getLacaiosP()).size() > 0) {
			Jogada jogada4 = new Jogada(mesaAtual.sacarCarta('S'), (mesaAtual.getLacaiosP()).get(0), 'S');
			ProcessadorJogada.processar(jogada4, mesaAtual);
		}

	}
}
