package base.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import base.Jogada;
import base.Mesa;
import base.cartas.Carta;
import base.cartas.Lacaio;
import base.cartas.magias.Buff;
import base.cartas.magias.Dano;
import base.cartas.magias.DanoArea;
import base.service.JogadaService;

public class JogadaServiceAgressivaImpl implements JogadaService {

	/**
	 * Metodo que cria uma lista de Jogadas decididas no modo agressivo
	 * 
	 * @param mesa
	 *            Mesa atual, que contem todos os atributos do jogo no instante
	 *            em que se criou a jogada
	 * 
	 * @param c
	 *            Char que identifica o jogador que criou a jogada
	 * 
	 * @return Lista de Jogadas decididas
	 *
	 */

	public List<Jogada> criaJogada(Mesa mesa, char c) {

		ArrayList<Carta> mao;
		ArrayList<Carta> lacaios = new ArrayList<Carta>();
		ArrayList<Carta> lacaiosOponente = new ArrayList<Carta>();
		int minhaMana;
		// int minhaVida;
		// int vidaOp;

		if (c == 'P') {
			mao = mesa.getMaoP();
			minhaMana = mesa.getManaP();
			// minhaVida = mesa.getPoderHeroiP();
			// vidaOp = mesa.getPoderHeroiS();
			lacaios = mesa.getLacaiosP();
			lacaiosOponente = mesa.getLacaiosP();
		} else {
			mao = mesa.getMaoS();
			minhaMana = mesa.getManaP();
			// minhaVida = mesa.getPoderHeroiS();
			// vidaOp = mesa.getPoderHeroiP();
			lacaios = mesa.getLacaiosP();
			lacaiosOponente = mesa.getLacaiosP();
		}

		ArrayList<Jogada> minhasJogadas = new ArrayList<Jogada>();

		int k = 0;
		int nLacaios = lacaios.size();
		Carta card;

		// Procura os lacaios com mais ataque, se houver carta e mana disponivel

		// Nao baixa mais de 7 lacaios a mesa
		if (nLacaios < 7) {
			int manaAux = minhaMana;

			// Encontra a carta com mais ataque para comprar atraves de streams
			// e comparator
			card = mao.stream().filter(aux -> aux instanceof Lacaio).filter(aux -> aux.getCustoMana() <= manaAux)
					.max(new LacaioComparator()).orElse(null);

			// Baixa o lacaio escolhido
			if (mao.size() > 0) {
				if (card instanceof Lacaio && card.getCustoMana() <= minhaMana) {
					minhasJogadas.add(new Jogada((Lacaio) card, null, c));
					nLacaios++;
					minhaMana -= card.getCustoMana();
					mao.remove(card);
				}
			}
		}

		int manaAux = minhaMana;

		// Encontra a carta com mais ataque para comprar atraves de streams
		// e comparator
		card = mao.stream().filter(aux -> aux instanceof Dano).filter(aux -> aux.getCustoMana() <= manaAux)
				.max(new DanoComparator()).orElse(null);

		// Magia de dano direto no heroi
		// Apos a jogada, diminui a mana e remove a carta da mao
		if (card instanceof Dano && card.getCustoMana() <= minhaMana) {
			minhasJogadas.add(new Jogada(card, null, c));
			minhaMana -= card.getCustoMana();
			mao.remove(card);
		}

		// Magia de area
		if (lacaiosOponente.size() >= 2) {
			for (int i = 0; i < mao.size(); i++) {
				card = mao.get(i);

				// Utiliza a magia de area
				if (card instanceof DanoArea && card.getCustoMana() <= minhaMana) {
					minhasJogadas.add(new Jogada(card, null, c));
					minhaMana -= card.getCustoMana();
					mao.remove(i);
					break;
				}
			}
		}

		// Magia de buff no lacaio com menor vida
		for (int i = 0; i < mao.size(); i++) {
			card = mao.get(i);

			if (card instanceof Buff && card.getCustoMana() <= minhaMana) {

				// Pode-se usar COMPARATOR - vida lacaio
				// Procura lacaio com menor vida para buffar
				if (lacaios.size() > 0) {
					k = 0;
					for (int j = 0; j < lacaios.size(); j++) {
						if (((Lacaio) lacaios.get(k)).getVidaAtual() > ((Lacaio) lacaios.get(j)).getVidaAtual()) {
							k = j;
						}
					}

					// Realiza as jogadas
					minhasJogadas.add(new Jogada(card, lacaios.get(k), c));
					minhaMana -= card.getCustoMana();
					mao.remove(i);
					break;
				}
			}
		}

		// Ataca o heroi com todos os lacaios disponiveis
		for (int i = 0; i < lacaios.size(); i++) {
			card = lacaios.get(i);
			minhasJogadas.add(new Jogada(card, null, c));
		}

		return minhasJogadas;
	}

	public class DanoComparator implements Comparator<Carta> {
		public int compare(Carta c1, Carta c2) {
			return ((Dano) c1).getDano() > ((Dano) c2).getDano() ? +1
					: ((Dano) c1).getDano() < ((Dano) c2).getDano() ? -1 : 0;
		}
	}

	public class LacaioComparator implements Comparator<Carta> {
		public int compare(Carta c1, Carta c2) {
			return ((Lacaio) c1).getAtaque() > ((Lacaio) c2).getAtaque() ? +1
					: ((Lacaio) c1).getAtaque() < ((Lacaio) c2).getAtaque() ? -1 : 0;
		}
	}
}