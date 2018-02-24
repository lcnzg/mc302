import java.util.ArrayList;

/**
 * Esta classe representa um Jogador que utiliza 3 tipos de comportamento para o
 * jogo LaMa (Lacaios & Magias).
 *
 * Critério de escolha de comportamento:
 * 
 * O comportamento CONTROLE é utilizado se já existe o numero maximo de lacaios
 * baixados ou se a vida do meu heroi for igual ou menor que 1/3 da vida total.
 * 
 * Caso nao utilize o comportamento anterior, o comportamento AGRESSIVO é
 * utilizado se o turno é 8 ou maior ou se a vida do heroi oponente estiver na
 * metade ou menos.
 * 
 * Caso nao utilize os comportamentos anteriores, o comportamento de CURVA DE
 * MANA é utilizado, ou seja, geralmente no inicio da partida ate o turno 7.
 * 
 * Não considero as cartas que ainda estão no baralho nem memorizo cartas
 * baixadas e analizo o comportamento do meu adversario
 * 
 *
 * As estrategias de cada comportamento a seguir estao ordenadas por prioridade,
 * da maior para menor:
 *
 * Comportamento Agressivo: Para causar dano ao heroi do oponente o mais rapido
 * possivel, caso tenha mana, baixo os lacaios da mao com maior ataque, apos
 * isso utilizo magia de dano direto no heroi oponente, em seguida, magia de
 * area se houverem mais de 1 lacaio oponente, magia de buff no lacaio com menor
 * vida e se sobrar mana, ataco com o poder heroico no heroi oponente. Após
 * isso, ataco o heroi oponente com todos os lacaios disponiveis.
 * 
 * Comportamento Controle: Para obter mais lacaios vivos do que o oponente, caso
 * tenha mana, utilizo magia de area se houverem mais de 1 lacaio oponente, em
 * seguida, magia de alvo nos lacaios do oponente sem disperdicar ataque da
 * carta, e trocas favoraveis entre lacaios (onde sempre obtenho vantagem no
 * ataque, seguindo o modelo do enunciado). Caso sobre mana, utilizo meu poder
 * heroico. Ataco com os lacaios disponiveis o heroi oponente.
 * 
 * Comportamento Curva de Mana: Tenta utilizar toda a mana disponivel no turno.
 * Para isso, procura baixar um lacaio gastando toda a mana disponivel. Se nao
 * conseguir, faz uma combinacao de cartas de lacaio que melhor gastam a mana.
 * Caso sobre mana, utilizo magia de alvo nos lacaios do oponente sem
 * disperdicar ataque da carta, faco trocas favoraveis entre lacaios, onde
 * sempre obtenho vantagem no ataque, utilizo magia de buff no lacaio com menor
 * vida e ataco com o poder heroico no heroi oponente. Ataco com os lacaios
 * disponiveis o heroi oponente.
 *
 * @author Luciano Gigantelli Zago - RA: 182835
 * 
 */
public class JogadorRA182835 extends Jogador {
	private int minhaMana;
	private int minhaVida;
	private int vidaOp;
	private int turno;
	private ArrayList<CartaLacaio> lacaios;
	private ArrayList<CartaLacaio> lacaiosOponente;

	/**
	 * O metodo construtor do JogadorRA182835.
	 * 
	 * @param maoInicial
	 *            Contem a mao inicial do jogador. Deve conter o numero de
	 *            cartas correto dependendo se esta classe Jogador que esta
	 *            sendo construida eh o primeiro ou o segundo jogador da
	 *            partida.
	 * @param primeiro
	 *            Informa se esta classe Jogador que esta sendo construida eh o
	 *            primeiro jogador a iniciar nesta jogada (true) ou se eh o
	 *            segundo jogador (false).
	 */
	public JogadorRA182835(ArrayList<Carta> maoInicial, boolean primeiro) {

		primeiroJogador = primeiro;
		mao = maoInicial;
		lacaios = new ArrayList<CartaLacaio>();
		lacaiosOponente = new ArrayList<CartaLacaio>();
		turno = 0;
	}

	/**
	 * Um metodo que processa o turno de cada jogador. Este metodo escolhe o
	 * comportamento mais adequado para o o momento do jogo, chamando outros
	 * metodos, e deve retornar as jogadas do Jogador decididas para o turno
	 * atual (ArrayList de Jogada).
	 * 
	 * @param mesa
	 *            O "estado do jogo" imediatamente antes do inicio do turno
	 *            corrente. Este objeto de mesa contem todas as informacoes
	 *            'publicas' do jogo (lacaios vivos e suas vidas, vida dos
	 *            herois, etc).
	 * @param cartaComprada
	 *            A carta que o Jogador recebeu neste turno (comprada do
	 *            Baralho). Obs: pode ser null se o Baralho estiver vazio ou o
	 *            Jogador possuir mais de 10 cartas na mao.
	 * @param jogadasOponente
	 *            Um ArrayList de Jogada que foram os movimentos utilizados pelo
	 *            oponente no ultimo turno, em ordem.
	 * @return um ArrayList com as Jogadas decididas
	 */
	public ArrayList<Jogada> processarTurno(Mesa mesa, Carta cartaComprada, ArrayList<Jogada> jogadasOponente) {

		turno++;

		// Define as variaveis auxiliares
		if (cartaComprada != null) {
			if (mao.size() < 10)
				mao.add(cartaComprada);
		}

		if (primeiroJogador) {
			minhaMana = mesa.getManaJog1();
			minhaVida = mesa.getVidaHeroi1();
			vidaOp = mesa.getVidaHeroi2();
			lacaios = mesa.getLacaiosJog1();
			lacaiosOponente = mesa.getLacaiosJog2();
		} else {
			minhaMana = mesa.getManaJog2();
			minhaVida = mesa.getVidaHeroi2();
			vidaOp = mesa.getVidaHeroi1();
			lacaios = mesa.getLacaiosJog2();
			lacaiosOponente = mesa.getLacaiosJog1();
		}

		ArrayList<Jogada> minhasJogadas = new ArrayList<Jogada>();

		/*
		 * Utiliza comportamento de controle se nao puder mais baixar lacaios na
		 * mesa, ou se a vida do meu heroi estiver menor ou igual a 1/3 da vida
		 * total
		 * 
		 */
		if (lacaios.size() >= 7 || minhaVida <= 10) {
			minhasJogadas.addAll(compControle());
		}

		/*
		 * Caso nao utilize o comportamento anterios, utiliza comportamento
		 * agressivo se atingir o turno 8 ou se a vida do heroi oponente estiver
		 * menor ou igual a metade da vida total
		 * 
		 */
		else if (turno >= 8 || vidaOp <= 15) {
			minhasJogadas.addAll(compAgressivo());
		}
		/*
		 * Caso nao utilizar os comportamentos anteriores, utiliza o
		 * comportamento de curva de mana
		 * 
		 */
		else {
			minhasJogadas.addAll(compMana());
		}

		return minhasJogadas;
	}

	/**
	 * Um metodo privado chamado em processar turno, que escolhe jogadas de
	 * comportamento agressivo. Este metodo deve retornar as jogadas agressivas
	 * escolhidas (ArrayList de Jogada).
	 * 
	 * @return um ArrayList com as Jogadas decididas
	 */
	private ArrayList<Jogada> compAgressivo() {

		ArrayList<Jogada> minhasJogadas = new ArrayList<Jogada>();

		int k = 0;
		int nLacaios = lacaios.size();

		// Procura os lacaios com mais ataque, se houver carta e mana disponivel

		// Nao baixa mais de 7 lacaios a mesa
		if (nLacaios < 7) {

			// Procura a carta com mais ataque para comprar
			for (int i = 0; i < mao.size(); i++) {
				Carta card = mao.get(i);

				// Ajuda a definir um comparativo inicial
				if (!(mao.get(k) instanceof CartaLacaio && (mao.get(k)).getMana() <= minhaMana)) {
					k = i;
				}

				if (card instanceof CartaLacaio && card.getMana() <= minhaMana) {
					if (((CartaLacaio) card).getAtaque() > ((CartaLacaio) mao.get(k)).getAtaque()) {
						k = i;
					}
				}
			}

			// Baixa o lacaio escolhido
			if (mao.size() > 0) {
				if (mao.get(k) instanceof CartaLacaio && (mao.get(k)).getMana() <= minhaMana) {

					minhasJogadas.add(new Jogada(TipoJogada.LACAIO, (CartaLacaio) mao.get(k), null));
					nLacaios++;
					minhaMana -= (mao.get(k)).getMana();
					mao.remove(k);
				}
			}
		}

		// Magia de dano direto no heroi
		for (int i = 0; i < mao.size(); i++) {
			Carta card = mao.get(i);

			if (card instanceof CartaMagia && card.getMana() <= minhaMana) {
				if (((CartaMagia) card).getMagiaTipo() == TipoMagia.ALVO) {

					// Apos a jogada, diminui a mana e remove a carta da mao
					minhasJogadas.add(new Jogada(TipoJogada.MAGIA, card, null));
					minhaMana -= card.getMana();
					mao.remove(i);
					break;
				}
			}
		}

		// Magia de area
		if (lacaiosOponente.size() >= 2) {
			for (int m = 0; m < 5; m++) {
				for (int i = 0; i < mao.size(); i++) {
					Carta card = mao.get(i);

					// Utiliza a magia de area
					if (card instanceof CartaMagia && card.getMana() <= minhaMana) {
						if (((CartaMagia) card).getMagiaTipo() == TipoMagia.AREA) {
							minhasJogadas.add(new Jogada(TipoJogada.MAGIA, card, null));
							minhaMana -= card.getMana();
							mao.remove(i);
							break;
						}
					}
				}
			}
		}

		// Magia de buff no lacaio com menor vida
		for (int i = 0; i < mao.size(); i++) {
			Carta card = mao.get(i);

			if (card instanceof CartaMagia && card.getMana() <= minhaMana) {
				if (((CartaMagia) card).getMagiaTipo() == TipoMagia.BUFF) {

					// Procura lacaio com menor vida para buffar
					if (lacaios.size() > 0) {
						k = 0;
						for (int j = 0; j < lacaios.size(); j++) {
							if ((lacaios.get(k)).getVidaAtual() > (lacaios.get(j)).getVidaAtual()) {
								k = j;
							}
						}

						// Realiza as jogadas
						minhasJogadas.add(new Jogada(TipoJogada.MAGIA, card, lacaios.get(k)));
						minhaMana -= card.getMana();
						mao.remove(i);
						break;
					}
				}
			}
		}

		// Ataca o heroi com o meu poder heroico
		if (minhaMana >= 2) {
			minhasJogadas.add(new Jogada(TipoJogada.PODER, null, null));
			minhaMana -= 2;
		}

		// Ataca o heroi com todos os lacaios disponiveis
		for (int i = 0; i < lacaios.size(); i++) {
			Carta card = lacaios.get(i);
			minhasJogadas.add(new Jogada(TipoJogada.ATAQUE, card, null));
		}

		return minhasJogadas;
	}

	/**
	 * Um metodo privado chamado em processar turno, que escolhe jogadas do
	 * comportamento de controle. Este metodo deve retornar as jogadas de
	 * controle escolhidas (ArrayList de Jogada).
	 * 
	 * @return um ArrayList com as Jogadas decididas
	 */
	private ArrayList<Jogada> compControle() {

		ArrayList<Jogada> minhasJogadas = new ArrayList<Jogada>();

		if (lacaiosOponente.size() > 0) {

			// Magia de area
			if (lacaiosOponente.size() >= 2) {
				for (int i = 0; i < mao.size(); i++) {
					Carta card = mao.get(i);
					if (card instanceof CartaMagia && card.getMana() <= minhaMana) {
						if (((CartaMagia) card).getMagiaTipo() == TipoMagia.AREA) {

							// Realiza a jogada
							minhasJogadas.add(new Jogada(TipoJogada.MAGIA, card, null));
							minhaMana -= card.getMana();
							mao.remove(i);

							// Atualiza a situacao dos lacaios do oponente
							for (int j = 0; j < lacaiosOponente.size(); j++) {
								(lacaiosOponente.get(j)).setVidaAtual(
										(lacaiosOponente.get(j)).getVidaAtual() - ((CartaMagia) card).getMagiaDano());
								if ((lacaiosOponente.get(j)).getVidaAtual() <= 0) {
									lacaiosOponente.remove(j);
								}
							}
							break;
						}
					}
				}
			}

			// Magia de alvo
			for (int i = 0; i < mao.size(); i++) {
				Carta card = mao.get(i);
				boolean encontrouLac = false;

				if (card instanceof CartaMagia && card.getMana() <= minhaMana) {

					if (((CartaMagia) card).getMagiaTipo() == TipoMagia.ALVO) {

						for (int j = 0; j < lacaiosOponente.size(); j++) {

							// Nao desperdica magia
							if ((lacaiosOponente.get(j)).getVidaAtual() - ((CartaMagia) card).getMagiaDano() >= -1
									&& (lacaiosOponente.get(j)).getVidaAtual()
											- ((CartaMagia) card).getMagiaDano() <= 0) {
								encontrouLac = true;
							}
						}

						// Realiza a jogada e sai do laco
						if (encontrouLac == true) {
							minhasJogadas.add(new Jogada(TipoJogada.MAGIA, card, null));
							minhaMana -= card.getMana();
							mao.remove(i);

							// Atualiza a situacao dos lacaios do oponente
							for (int j = 0; j < lacaiosOponente.size(); j++) {
								(lacaiosOponente.get(j)).setVidaAtual(
										(lacaiosOponente.get(j)).getVidaAtual() - ((CartaMagia) card).getMagiaDano());
								if ((lacaiosOponente.get(j)).getVidaAtual() <= 0) {
									lacaiosOponente.remove(j);
								}
							}
							break;
						}
					}
				}
			}

			// Trocas favoraveis entre lacaios
			if (lacaios.size() > 0 && lacaiosOponente.size() > 0) {

				for (int j = 0; j < 7; j++) {
					boolean encontrouLac = false;
					CartaLacaio lac, lacOp;
					lac = lacaios.get(0);
					lacOp = lacaiosOponente.get(0);
					int a, b;
					achou: for (b = 0; b < lacaios.size(); b++) {
						lac = lacaios.get(b);
						for (a = 0; a < lacaiosOponente.size(); a++) {
							lacOp = lacaiosOponente.get(a);

							// meu lacaio sobrevive e o do oponente morre
							if ((lacOp).getVidaAtual() - (lac).getAtaque() <= 0
									&& (lac).getVidaAtual() - (lacOp).getAtaque() >= 0) {
								encontrouLac = true;
								break achou;
							}

							// ambos lacaios morrem, mas perco menos mana
							if ((lacOp).getVidaAtual() - (lac).getAtaque() <= 0
									&& (lac).getVidaAtual() - (lacOp).getAtaque() <= 0
									&& (lacOp).getMana() > (lac).getMana()) {
								encontrouLac = true;
								break achou;
							}
						}

						// ambos lacaios morrem, mas o meu estava ferido
						if ((lacOp).getVidaAtual() - (lac).getAtaque() <= 0
								&& (lac).getVidaAtual() - (lacOp).getAtaque() <= 0
								&& (lac).getVidaAtual() < (lac).getVidaMaxima()
								&& (lac).getVidaAtual() < (lacOp).getVidaAtual()) {
							encontrouLac = true;
							break achou;
						}

						// Realiza a jogada
						if (encontrouLac == true) {
							minhasJogadas.add(new Jogada(TipoJogada.ATAQUE, lac, lacOp));

							// Atualiza a vida de ambos os lacaios
							(lacOp).setVidaAtual((lacOp).getVidaAtual() - (lac).getAtaque());
							(lac).setVidaAtual((lacaiosOponente.get(b)).getVidaAtual() - (lacOp).getAtaque());

							if ((lacOp).getVidaAtual() <= 0) {
								lacaiosOponente.remove(lacOp);
							}
							if ((lac).getVidaAtual() <= 0) {
								lacaios.remove(lac);
							}
						}
					}
				}
			}

			// Ataca o heroi com o meu poder heroico
			if (minhaMana >= 2) {
				minhasJogadas.add(new Jogada(TipoJogada.PODER, null, null));
				minhaMana -= 2;
			}

			// Atacar com os demais lacaios no heroi
			for (int i = 0; i < lacaios.size(); i++) {
				if (minhasJogadas.contains(lacaios.get(i))) {
					continue;
				}
				Carta card = lacaios.get(i);
				minhasJogadas.add(new Jogada(TipoJogada.ATAQUE, card, null));
			}
		}

		return minhasJogadas;

	}

	/**
	 * Um metodo privado chamado em processar turno, que escolhe jogadas de
	 * curva de mana. Este metodo deve retornar as jogadas de curva de mana
	 * escolhidas (ArrayList de Jogada).
	 * 
	 * @return um ArrayList com as Jogadas decididas
	 */
	private ArrayList<Jogada> compMana() {

		ArrayList<Jogada> minhasJogadas = new ArrayList<Jogada>();
		int nLacaios = lacaios.size();

		// Procura os lacaios com mana igual a disponivel e baixa da mao
		if (nLacaios < 7) {
			for (int i = 0; i < mao.size(); i++) {
				Carta card = mao.get(i);
				if (card instanceof CartaLacaio && card.getMana() == minhaMana) {
					minhasJogadas.add(new Jogada(TipoJogada.LACAIO, (CartaLacaio) card, null));
					nLacaios++;
					minhaMana -= card.getMana();
					mao.remove(i);
					break;
				}
			}
		}

		// Seleciona a melhor combinacao de lacaios em relacao a mana gasta
		ArrayList<Carta> listaEscolhida = new ArrayList<Carta>();
		ArrayList<Carta> soLacaios = new ArrayList<Carta>();

		// Cria uma lista de lacaios disponiveis para baixar
		for (int i = 0; i < mao.size(); i++) {
			if (mao.get(i) instanceof CartaLacaio && (mao.get(i)).getMana() <= minhaMana) {
				soLacaios.add(mao.get(i));
			}
		}

		// Metodo recursivo que escolhe a melhor combinacao de Lacaio
		melhorEscolha(soLacaios, listaEscolhida, minhaMana, soLacaios.size());

		// Baixa os lacaios escolhidos
		for (int i = 0; i < listaEscolhida.size(); i++) {
			if (nLacaios < 7) {
				minhasJogadas.add(new Jogada(TipoJogada.LACAIO, (CartaLacaio) listaEscolhida.get(i), null));
				nLacaios++;
				minhaMana -= (listaEscolhida.get(i)).getMana();
				mao.remove(listaEscolhida.get(i));
			}
		}

		// Magia de alvo
		for (int a = 0; a < 2; a++) {
			for (int i = 0; i < mao.size(); i++) {
				Carta card = mao.get(i);
				boolean encontrouLac = false;
				if (card instanceof CartaMagia && card.getMana() <= minhaMana) {
					if (((CartaMagia) card).getMagiaTipo() == TipoMagia.ALVO) {
						for (int j = 0; j < lacaiosOponente.size(); j++) {

							// Nao desperdica mana na magia
							if ((lacaiosOponente.get(j)).getVidaAtual() - ((CartaMagia) card).getMagiaDano() <= 0
									&& (lacaiosOponente.get(j)).getMana() < ((CartaMagia) card).getMana()) {
								encontrouLac = true;
							}
						}

						// Realiza a jogada
						if (encontrouLac == true) {
							minhasJogadas.add(new Jogada(TipoJogada.MAGIA, card, null));
							minhaMana -= card.getMana();
							mao.remove(i);

							// Atualiza a situacao dos lacaios do oponente
							for (int j = 0; j < lacaiosOponente.size(); j++) {
								(lacaiosOponente.get(j)).setVidaAtual(
										(lacaiosOponente.get(j)).getVidaAtual() - ((CartaMagia) card).getMagiaDano());
								if ((lacaiosOponente.get(j)).getVidaAtual() <= 0) {
									lacaiosOponente.remove(j);
								}
							}
							break;
						}
					}
				}
			}
		}

		// Trocas favoraveis entre lacaios
		if (lacaios.size() > 0 && lacaiosOponente.size() > 0) {
			for (int j = 0; j < 7; j++) {
				boolean encontrouLac = false;
				CartaLacaio lac, lacOp;
				lac = lacaios.get(0);
				lacOp = lacaiosOponente.get(0);
				int a, b;
				achou: for (b = 0; b < lacaios.size(); b++) {
					lac = lacaios.get(b);
					for (a = 0; a < lacaiosOponente.size(); a++) {
						lacOp = lacaiosOponente.get(a);

						// meu lacaio sobrevive e o do oponente morre
						if ((lacOp).getVidaAtual() - (lac).getAtaque() <= 0
								&& (lac).getVidaAtual() - (lacOp).getAtaque() >= 0) {
							encontrouLac = true;
							break achou;
						}

						// ambos lacaios morrem, mas perco menos mana
						if ((lacOp).getVidaAtual() - (lac).getAtaque() <= 0
								&& (lac).getVidaAtual() - (lacOp).getAtaque() <= 0
								&& (lacOp).getMana() > (lac).getMana()) {
							encontrouLac = true;
							break achou;
						}
					}

					// ambos lacaios morrem, mas o meu estava ferido
					if ((lacOp).getVidaAtual() - (lac).getAtaque() <= 0
							&& (lac).getVidaAtual() - (lacOp).getAtaque() <= 0
							&& (lac).getVidaAtual() < (lac).getVidaMaxima()
							&& (lac).getVidaAtual() < (lacOp).getVidaAtual()) {
						encontrouLac = true;
						break achou;
					}

					// Realiza a jogada
					if (encontrouLac == true) {
						minhasJogadas.add(new Jogada(TipoJogada.ATAQUE, lac, lacOp));

						// Atualiza a vida de ambos os lacaios
						lacOp.setVidaAtual((lacOp).getVidaAtual() - lac.getAtaque());
						lac.setVidaAtual((lacaiosOponente.get(b)).getVidaAtual() - lacOp.getAtaque());
						if (lacOp.getVidaAtual() <= 0) {
							lacaiosOponente.remove(lacOp);
						}
						if (lac.getVidaAtual() <= 0) {
							lacaios.remove(lac);
						}
					}
				}
			}
		}

		// Magia de buff no lacaio com menor vida
		for (int i = 0; i < mao.size(); i++) {
			Carta card = mao.get(i);

			if (card instanceof CartaMagia && card.getMana() <= minhaMana) {
				if (((CartaMagia) card).getMagiaTipo() == TipoMagia.BUFF) {

					// Procura lacaio com menor vida para buffar
					if (lacaios.size() > 0) {
						int k = 0;
						for (int j = 0; j < lacaios.size(); j++) {
							if ((lacaios.get(k)).getVidaAtual() > (lacaios.get(j)).getVidaAtual()) {
								k = j;
							}
						}

						// Realiza as jogadas
						minhasJogadas.add(new Jogada(TipoJogada.MAGIA, card, lacaios.get(k)));
						minhaMana -= card.getMana();
						mao.remove(i);
						break;
					}
				}
			}
		}

		// Ataca o heroi com o meu poder heroico
		if (minhaMana >= 2) {
			minhasJogadas.add(new Jogada(TipoJogada.PODER, null, null));
			minhaMana -= 2;
		}

		// Atacar com os demais lacaios no heroi
		for (int i = 0; i < lacaios.size(); i++) {
			Carta card = lacaios.get(i);

			if (minhasJogadas.contains(card)) {
				continue;
			}

			minhasJogadas.add(new Jogada(TipoJogada.ATAQUE, card, null));
		}

		return minhasJogadas;
	}

	/**
	 * Um metodo privado de classe, recursivo, baseado no algoritmo da mochila
	 * binaria, chamado em curva de mana, que escolhe a melhor combinacao de
	 * cartas. Este metodo utiliza uma ArrayList de entrada e outra de saida,
	 * com as cartas possiveis e a melhor combinacao escolhidas, respectivamente
	 * (ArrayLists de Carta).
	 * 
	 * @param soLacaios
	 *            ArrayList que contem CartaLacaio e que lista as cartas
	 *            disponiveis para baixar
	 *
	 * @param listaEscolhida
	 *            ArrayList de saida, que contem a melhor combinacao das cartas
	 *            dentre a mana disponivel
	 * 
	 * @param manaMax
	 *            Numero maximo de mana disponivel para baixar as cartas
	 *
	 * @param n
	 *            Parametro que controla as chamadas recursivas. Inicia com o
	 *            tamanho da ArrayList de entrada (soLacaios)
	 * 
	 * @return int com a mana utilizada
	 */
	private static int melhorEscolha(ArrayList<Carta> soLacaios, ArrayList<Carta> listaEscolhida, int manaMax, int n) {

		// Se nao houver elementos para verificar, retorna 0
		if (n == 0 || manaMax == 0)
			return 0;

		// Caso a Carta tenha mana maior do que a permitida, chama o metodo
		// recursivo para as cartas anteriores
		if (soLacaios.get(n - 1).getMana() > manaMax) {
			ArrayList<Carta> sublista = new ArrayList<Carta>();
			int manaTotal = melhorEscolha(soLacaios, sublista, manaMax, n - 1);
			listaEscolhida.addAll(sublista);
			return manaTotal;
		}

		// Caso a carta ter mana menor do que a manaMax, separa a lista em 2
		// grupos incluindo e excluindo a carta atual, e analisa a combinacao
		// separadamente, depois as compara e escolhe a que mais se encaixa
		else {
			ArrayList<Carta> melhorEscolha1 = new ArrayList<Carta>();
			ArrayList<Carta> melhorEscolha2 = new ArrayList<Carta>();
			int mana1 = melhorEscolha(soLacaios, melhorEscolha1, manaMax, n - 1);
			int mana2 = soLacaios.get(n - 1).getMana()
					+ melhorEscolha(soLacaios, melhorEscolha2, (manaMax - soLacaios.get(n - 1).getMana()), n - 1);

			// Se a combinacao de maior mana for a 2a, adiciona a listaEscolhida
			if (mana2 > mana1) {
				listaEscolhida.addAll(melhorEscolha2);
				listaEscolhida.add(soLacaios.get(n - 1));
				return mana2;
			}

			// Se a combinacao de maior mana for a 1a, adiciona a listaEscolhida
			else {
				listaEscolhida.addAll(melhorEscolha1);
				return mana1;
			}
		}
	}
}
