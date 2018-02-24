import java.util.ArrayList;
import java.util.Random;

/**
 * Esta classe representa um Jogador aleatório (realiza jogadas de maneira
 * aleatória) para o jogo LaMa (Lacaios & Magias).
 * 
 * @see java.lang.Object
 * @author Rafael Arakaki - MC302
 */
public class JogadorRA182835New extends Jogador {
	private ArrayList<CartaLacaio> lacaios;
	private ArrayList<CartaLacaio> lacaiosOponente;

	/**
	 * O método construtor do JogadorAleatorio.
	 * 
	 * @param maoInicial
	 *            Contém a mão inicial do jogador. Deve conter o número de
	 *            cartas correto dependendo se esta classe Jogador que está
	 *            sendo construída é o primeiro ou o segundo jogador da
	 *            partida.
	 * @param primeiro
	 *            Informa se esta classe Jogador que está sendo construída é
	 *            o primeiro jogador a iniciar nesta jogada (true) ou se é o
	 *            segundo jogador (false).
	 */
	public JogadorRA182835New(ArrayList<Carta> maoInicial, boolean primeiro) {
		primeiroJogador = primeiro;

		mao = maoInicial;
		lacaios = new ArrayList<CartaLacaio>();
		lacaiosOponente = new ArrayList<CartaLacaio>();

		// Mensagens de depuração:
		System.out.println("*Classe JogadorRA182835New* Sou o " + (primeiro ? "primeiro" : "segundo")
				+ " jogador (classe: JogadorAleatorio)");
		System.out.println("Mao inicial:");
		for (int i = 0; i < mao.size(); i++)
			System.out.println("ID " + mao.get(i).getID() + ": " + mao.get(i));
	}

	/**
	 * Um método que processa o turno de cada jogador. Este método deve
	 * retornar as jogadas do Jogador decididas para o turno atual (ArrayList de
	 * Jogada).
	 * 
	 * @param mesa
	 *            O "estado do jogo" imediatamente antes do início do turno
	 *            corrente. Este objeto de mesa contém todas as informações
	 *            'públicas' do jogo (lacaios vivos e suas vidas, vida dos
	 *            heróis, etc).
	 * @param cartaComprada
	 *            A carta que o Jogador recebeu neste turno (comprada do
	 *            Baralho). Obs: pode ser null se o Baralho estiver vazio ou o
	 *            Jogador possuir mais de 10 cartas na mão.
	 * @param jogadasOponente
	 *            Um ArrayList de Jogada que foram os movimentos utilizados pelo
	 *            oponente no último turno, em ordem.
	 * @return um ArrayList com as Jogadas decididas
	 */
	public ArrayList<Jogada> processarTurno(Mesa mesa, Carta cartaComprada, ArrayList<Jogada> jogadasOponente) {
		int minhaMana, minhaVida;
		if (cartaComprada != null)
			mao.add(cartaComprada);
		if (primeiroJogador) {
			minhaMana = mesa.getManaJog1();
			minhaVida = mesa.getVidaHeroi1();
			lacaios = mesa.getLacaiosJog1();
			lacaiosOponente = mesa.getLacaiosJog2();
			// System.out.println("--------------------------------- Começo de
			// turno pro jogador1");
		} else {
			minhaMana = mesa.getManaJog2();
			minhaVida = mesa.getVidaHeroi2();
			lacaios = mesa.getLacaiosJog2();
			lacaiosOponente = mesa.getLacaiosJog1();
			// System.out.println("--------------------------------- Começo de
			// turno pro jogador2");
		}

		ArrayList<Jogada> minhasJogadas = new ArrayList<Jogada>();

		boolean jogouMagia = false;
		int k = 0;

		// Baixa 1 magia se houver carta e mana disponivel

		// 1 preferencia: magia de dano em area
		if (jogouMagia == false) {
			for (int i = 0; i < mao.size(); i++) {
				Carta card = mao.get(i);

				if (card instanceof CartaMagia && card.getMana() <= minhaMana) {

					if (((CartaMagia) card).getMagiaTipo() == TipoMagia.AREA) {

						// Realiza a jogada
						minhasJogadas.add(new Jogada(TipoJogada.MAGIA, card, null));
						minhaMana -= card.getMana();
						mao.remove(i);
						jogouMagia = true;
						break;
					}
				}
			}
		}

		// 2 preferencia: magia de buff no lacaio
		if (jogouMagia == false) {
			for (int i = 0; i < mao.size(); i++) {
				Carta card = mao.get(i);

				if (card instanceof CartaMagia && card.getMana() <= minhaMana) {

					if (((CartaMagia) card).getMagiaTipo() == TipoMagia.BUFF) {

						// Verifica se ha lacaios para buffar
						if (lacaios.size() > 0) {

							k = 0;
							// Procura lacaio com menor vida para buffar
							for (int j = 0; j < lacaios.size(); j++) {

								if ((lacaios.get(k)).getVidaAtual() > (lacaios.get(j)).getVidaAtual()) {

									k = j;
								}
							}

							// Realiza as jogadas
							minhasJogadas.add(new Jogada(TipoJogada.MAGIA, card, lacaios.get(k)));
							minhaMana -= card.getMana();
							mao.remove(i);
							jogouMagia = true;
							break;
						}

						// Se nao houver lacaios, procura outra carta de magia
						else {
							jogouMagia = false;
							break;
						}
					}
				}
			}
		}

		// 3 preferencia: magia de dano direto no heroi
		if (jogouMagia == false) {
			for (int i = 0; i < mao.size(); i++) {
				Carta card = mao.get(i);

				if (card instanceof CartaMagia && card.getMana() <= minhaMana) {

					if (((CartaMagia) card).getMagiaTipo() == TipoMagia.ALVO) {

						// Apos a jogada, diminui a mana e remove a carta da mao
						minhasJogadas.add(new Jogada(TipoJogada.MAGIA, card, null));
						minhaMana -= card.getMana();
						mao.remove(i);
						jogouMagia = true;
						break;
					}
				}
			}
		}

		// Procura 1 lacaio, se houver carta e mana disponivel
		k = 0;

		for (int i = 0; i < mao.size(); i++) {
			Carta card = mao.get(i);

			// Ajuda a definir um comparativo inicial
			if (!(mao.get(k) instanceof CartaLacaio && (mao.get(k)).getMana() <= minhaMana)) {
				k = i;
			}

			if (card instanceof CartaLacaio && card.getMana() <= minhaMana) {

				// Procura a carta com mais vida para comprar
				if (((CartaLacaio) card).getVidaMaxima() > ((CartaLacaio) mao.get(k)).getVidaMaxima()) {
					k = i;
				}
			}
		}

		// Baixa o lacaio escolhido
		if (mao.get(k) instanceof CartaLacaio && (mao.get(k)).getMana() <= minhaMana) {

			minhasJogadas.add(new Jogada(TipoJogada.LACAIO, (CartaLacaio) mao.get(k), null));
			minhaMana -= (mao.get(k)).getMana();
			mao.remove(k);
		}

		// Ataca o heroi com todos os lacaios disponiveis
		for (int i = 0; i < lacaios.size(); i++) {

			Carta card = lacaios.get(i);
			minhasJogadas.add(new Jogada(TipoJogada.ATAQUE, card, null));
		}

		return minhasJogadas;
	}
}