import java.util.ArrayList;
import java.util.Random;

/**
 * Esta classe representa um Jogador aleatório (realiza jogadas de maneira
 * aleatória) para o jogo LaMa (Lacaios & Magias).
 * 
 * @see java.lang.Object
 * @author Rafael Arakaki - MC302
 */
public class JogadorRA182835 extends Jogador {
	private ArrayList<CartaLacaio> lacaios;
	private ArrayList<CartaLacaio> lacaiosOponente;

	/**
	 * O método construtor do JogadorAleatorio.
	 * 
	 * @param maoInicial
	 *            Contém a mão inicial do jogador. Deve conter o número de
	 *            cartas correto dependendo se esta classe Jogador que está
	 *            sendo construída é o primeiro ou o segundo jogador da partida.
	 * @param primeiro
	 *            Informa se esta classe Jogador que está sendo construída é o
	 *            primeiro jogador a iniciar nesta jogada (true) ou se é o
	 *            segundo jogador (false).
	 */
	public JogadorRA182835(ArrayList<Carta> maoInicial, boolean primeiro) {
		primeiroJogador = primeiro;

		mao = maoInicial;
		lacaios = new ArrayList<CartaLacaio>();
		lacaiosOponente = new ArrayList<CartaLacaio>();

		// Mensagens de depuração:
		System.out.println("*Classe JogadorRA182835* Sou o " + (primeiro ? "primeiro" : "segundo")
				+ " jogador (classe: JogadorAleatorio)");
		System.out.println("Mao inicial:");
		for (int i = 0; i < mao.size(); i++)
			System.out.println("ID " + mao.get(i).getID() + ": " + mao.get(i));
	}

	/**
	 * Um método que processa o turno de cada jogador. Este método deve retornar
	 * as jogadas do Jogador decididas para o turno atual (ArrayList de Jogada).
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

		// O laco abaixo cria jogada de baixar 1 magia da mao para a mesa se
		// houver mana disponivel
		for (int i = 0; i < mao.size(); i++) {
			Carta card = mao.get(i);
			if (card instanceof CartaMagia && card.getMana() <= minhaMana) {
				if (((CartaMagia) card).getMagiaTipo() == TipoMagia.BUFF) {
					if (lacaios.size() > 0) {
						minhasJogadas.add(new Jogada(TipoJogada.MAGIA, card, lacaios.get(0)));
					}
					else {
						continue;
					}
				}
				else {
					minhasJogadas.add(new Jogada(TipoJogada.MAGIA, card, null));
				}

				minhaMana -= card.getMana();
				mao.remove(i);
				break;
			}
		}

		// O laco abaixo cria jogada de baixar 1 lacaio da mao para a mesa se
		// houver mana disponivel
		for (int i = 0; i < mao.size(); i++) {
			Carta card = mao.get(i);
			if (card instanceof CartaLacaio && card.getMana() <= minhaMana) {
				minhasJogadas.add(new Jogada(TipoJogada.LACAIO, card, null));
				minhaMana -= card.getMana();
				mao.remove(i);
				break;
			}
		}

		// O laco abaixo cria jogada de atacar com todos os lacaio vivos nao
		// baixados no turno atual
		for (int i = 0; i < lacaios.size(); i++) {
			Carta card = lacaios.get(i);
			minhasJogadas.add(new Jogada(TipoJogada.ATAQUE, card, null));
		}

		return minhasJogadas;
	}
}