package base;

import java.util.stream.Collectors;

import base.cartas.Carta;
import base.cartas.HabilidadesLacaio;
import base.cartas.Lacaio;
import base.cartas.magias.Buff;
import base.cartas.magias.Dano;
import base.cartas.magias.DanoArea;

public class ProcessadorJogada {

	public ProcessadorJogada() {

	}

	/**
	 * Metodo que recebe a jogada realizada e a mesa do jogo e processa as
	 * mudancas da jogada nos atributos da mesa
	 * 
	 * @param jog
	 *            Jogada a ser processada
	 * 
	 * @param mes
	 *            Mesa que contem os dados atualizados da partida
	 *
	 */
	public static void processar(Jogada jog, Mesa mes) {

		// Se for o primeiro jogador
		if (jog.getAutor() == 'P') {
			mes.decMana((jog.getJogada()).getCustoMana(), 'P');

			// Se for uma carta de Dano de Area
			if (jog.getJogada() instanceof DanoArea) {
				((DanoArea) (jog.getJogada())).usar(mes.getLacaiosP());
				mes.decPoderHeroi(((DanoArea) jog.getJogada()).getDano(), 'S');
			}

			// Se for uma carta de Dano
			else if (jog.getJogada() instanceof Dano) {

				// Usar streams para procurar lacaio oponente com PROVOCAR
				if ((mes.getLacaiosS()).size() > 0) {
					if (mes.getLacaiosS().stream()
							.filter(lac -> ((Lacaio) lac).getHabilidade() == HabilidadesLacaio.PROVOCAR).findFirst()
							.isPresent() == true) {
						Carta card = mes.getLacaiosS().stream()
								.filter(lac -> ((Lacaio) lac).getHabilidade() == HabilidadesLacaio.PROVOCAR).findFirst()
								.get();

						(jog.getJogada()).usar(card);
					}
				}

				// Ataca normalmente
				else {
					if (jog.getAlvo() != null) {
						(jog.getJogada()).usar(jog.getAlvo());
					} else {
						mes.decPoderHeroi(((Dano) jog.getJogada()).getDano(), 'S');

					}
				}
			}
		}

		// Se for o segundo jogador
		else if (jog.getAutor() == 'S') {
			mes.decMana((jog.getJogada()).getCustoMana(), 'S');

			// Se for uma carta de Dano de Area
			if (jog.getJogada() instanceof DanoArea) {
				((DanoArea) (jog.getJogada())).usar(mes.getLacaiosS());
				mes.decPoderHeroi(((DanoArea) jog.getJogada()).getDano(), 'P');
			}

			// Se for uma carta de Dano
			else if (jog.getJogada() instanceof Dano) {

				// Usar streams para procurar lacaio oponente com PROVOCAR
				if ((mes.getLacaiosP()).size() > 0) {
					if (mes.getLacaiosP().stream()
							.filter(lac -> ((Lacaio) lac).getHabilidade() == HabilidadesLacaio.PROVOCAR).findFirst()
							.isPresent() == true) {
						Carta card = mes.getLacaiosP().stream()
								.filter(lac -> ((Lacaio) lac).getHabilidade() == HabilidadesLacaio.PROVOCAR).findFirst()
								.get();

						(jog.getJogada()).usar(card);
					}
				}

				// Ataca normalmente
				else {

					if (jog.getAlvo() != null) {
						(jog.getJogada()).usar(jog.getAlvo());
					} else {
						mes.decPoderHeroi(((Dano) jog.getJogada()).getDano(), 'P');
					}
				}
			}
		}

		// Se for uma carta de Buff
		if (jog.getJogada() instanceof Buff) {
			if (jog.getAlvo() instanceof Lacaio) {
				(jog.getJogada()).usar(jog.getAlvo());
			}
		}

		// Se for uma carta de Lacaio
		else if (jog.getJogada() instanceof Lacaio) {

			// Troca de EXAUSTAO para INVESTIDA
			if (((Lacaio) (jog.getJogada())).getHabilidade() == HabilidadesLacaio.EXAUSTAO) {
				((Lacaio) (jog.getJogada())).setHabilidade(HabilidadesLacaio.INVESTIDA);
			}

			// Nao e exaustao e o alvo e nulo
			else if (jog.getAlvo() == null && jog.getAutor() == 'S') {
				mes.decPoderHeroi(((Lacaio) jog.getJogada()).getAtaque(), 'P');
			}

			else if (jog.getAlvo() == null) {
				mes.decPoderHeroi(((Lacaio) jog.getJogada()).getAtaque(), 'S');
			}

			// Se nao for EXAUSTAO, ataca o alvo
			else {
				(jog.getJogada()).usar(jog.getAlvo());
			}
		}

		// Remove lacaios de ambos jogadores com vida <= 0 (com stream)
		(mes.getLacaiosP()).removeAll(mes.getLacaiosP().stream().filter(lac -> ((Lacaio) lac).getVidaAtual() <= 0)
				.collect(Collectors.toList()));
		(mes.getLacaiosS()).removeAll(mes.getLacaiosS().stream().filter(lac -> ((Lacaio) lac).getVidaAtual() <= 0)
				.collect(Collectors.toList()));

		// Imprime log
		System.out.println("");
		System.out.println("Autor: " + jog.getAutor());
		System.out.println("Carta da jogada: " + jog.getJogada());
		System.out.println("Carta alvo: " + jog.getAlvo());
		if (jog.getAutor() == 'S') {
			System.out.println("Qtd lacaios oponente: " + (mes.getLacaiosP()).size());
			System.out.println("Poder heroico oponente: " + mes.getPoderHeroiP());
		} else {
			System.out.println("Qtd lacaios oponente: " + (mes.getLacaiosS()).size());
			System.out.println("Poder heroico oponente: " + mes.getPoderHeroiS());
		}
	}
}