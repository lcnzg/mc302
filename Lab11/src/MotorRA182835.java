import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;

/* Laboratorio 11
* Nome: Luciano Gigantelli Zago
* RA: 182835
*
* Completude: 10
* 
* Implementados:
* Erros ATAQUE: 5, 6, 7, 8
* Erros LACAIO: 1, 2, 3, 4
* Erros MAGIA: 1, 2, 9, 10
* Erros PODER: 2, 11, 12
* 
* Nao implementados:
* INVESTIDA, ATAQUE DUPLO e PROVOCAR
* 
*/
public class MotorRA182835 extends Motor {

	public MotorRA182835(Baralho deck1, Baralho deck2, ArrayList<Carta> mao1, ArrayList<Carta> mao2, Jogador jogador1,
			Jogador jogador2, int verbose, int tempoLimitado, PrintWriter saidaArquivo,
			EnumSet<Funcionalidade> funcionalidadesAtivas) {
		super(deck1, deck2, mao1, mao2, jogador1, jogador2, verbose, tempoLimitado, saidaArquivo,
				funcionalidadesAtivas);
		imprimir("========================");
		imprimir("*** Classe " + this.getClass().getName() + " inicializada.");
		imprimir("Funcionalidade ativas no Motor:");
		imprimir("INVESTIDA: " + (this.funcionalidadesAtivas.contains(Funcionalidade.INVESTIDA) ? "SIM" : "NAO"));
		imprimir("ATAQUE_DUPLO: " + (this.funcionalidadesAtivas.contains(Funcionalidade.ATAQUE_DUPLO) ? "SIM" : "NAO"));
		imprimir("PROVOCAR: " + (this.funcionalidadesAtivas.contains(Funcionalidade.PROVOCAR) ? "SIM" : "NAO"));
		imprimir("========================");
	}

	private int jogador; // 1 == turno do jogador1, 2 == turno do jogador2.
	private int turno;
	private int nCartasHeroi1;
	private int nCartasHeroi2;

	private Mesa mesa;

	// "Apontadores" - Assim podemos programar genericamente os metodos para
	// funcionar com ambos os jogadores
	private ArrayList<Carta> mao;
	private ArrayList<Carta> lacaios;
	private ArrayList<Carta> lacaiosOponente;

	// "Memoria" - Para marcar ataques que so podem ser realizadas uma vez por
	// turno.
	private boolean poderHeroicoUsado;
	private HashSet<Integer> lacaiosAtacaramID;

	@Override
	public int executarPartida() throws LamaException {
		vidaHeroi1 = vidaHeroi2 = 30;
		manaJogador1 = manaJogador2 = 1;
		nCartasHeroi1 = cartasIniJogador1;
		nCartasHeroi2 = cartasIniJogador2;
		ArrayList<Jogada> movimentos = new ArrayList<Jogada>();
		int noCardDmgCounter1 = 1;
		int noCardDmgCounter2 = 1;
		turno = 1;

		for (int k = 0; k < 60; k++) {
			imprimir("\n=== TURNO " + turno + " ===\n");
			// Atualiza mesa (com copia profunda)
			@SuppressWarnings("unchecked")
			ArrayList<CartaLacaio> lacaios1clone = (ArrayList<CartaLacaio>) UnoptimizedDeepCopy.copy(lacaiosMesa1);
			@SuppressWarnings("unchecked")
			ArrayList<CartaLacaio> lacaios2clone = (ArrayList<CartaLacaio>) UnoptimizedDeepCopy.copy(lacaiosMesa2);
			mesa = new Mesa(lacaios1clone, lacaios2clone, vidaHeroi1, vidaHeroi2, nCartasHeroi1 + 1, nCartasHeroi2,
					turno > 10 ? 10 : turno, turno > 10 ? 10 : (turno == 1 ? 2 : turno));

			manaJogador1 = mesa.getManaJog1();
			manaJogador2 = mesa.getManaJog2();

			// Apontadores para jogador1
			mao = maoJogador1;
			lacaios = lacaiosMesa1;
			lacaiosOponente = lacaiosMesa2;
			jogador = 1;

			// Processa o turno 1 do Jogador1
			imprimir("\n----------------------- Comeco de turno para Jogador 1:");
			long startTime, endTime, totalTime;

			// Copia profunda de jogadas realizadas.
			@SuppressWarnings("unchecked")
			ArrayList<Jogada> cloneMovimentos1 = (ArrayList<Jogada>) UnoptimizedDeepCopy.copy(movimentos);

			startTime = System.nanoTime();
			if (baralho1.getCartas().size() > 0) {
				if (nCartasHeroi1 >= maxCartasMao) {
					movimentos = jogador1.processarTurno(mesa, null, cloneMovimentos1);
					comprarCarta(); // carta descartada
				} else
					movimentos = jogador1.processarTurno(mesa, comprarCarta(), cloneMovimentos1);
			} else {
				imprimir("Fadiga: O Heroi 1 recebeu " + noCardDmgCounter1
						+ " de dano por falta de cartas no baralho. (Vida restante: " + (vidaHeroi1 - noCardDmgCounter1)
						+ ").");
				vidaHeroi1 -= noCardDmgCounter1++;
				if (vidaHeroi1 <= 0) {
					// Jogador 2 venceu
					imprimir(
							"O jogador 2 venceu porque o jogador 1 recebeu um dano mortal por falta de cartas! (Dano : "
									+ (noCardDmgCounter1 - 1) + ", Vida Heroi 1: " + vidaHeroi1 + ")");
					return 2;
				}
				movimentos = jogador1.processarTurno(mesa, null, cloneMovimentos1);
			}
			endTime = System.nanoTime();
			totalTime = endTime - startTime;
			if (tempoLimitado != 0 && totalTime > 3e8) { // 300ms

				// Jogador 2 venceu, Jogador 1 excedeu limite de tempo
				return 2;
			} else
				imprimir("Tempo usado em processarTurno(): " + totalTime / 1e6 + "ms");

			// Comeca a processar jogadas do Jogador 1
			this.poderHeroicoUsado = false;
			this.lacaiosAtacaramID = new HashSet<Integer>();
			for (int i = 0; i < movimentos.size(); i++) {
				processarJogada(movimentos.get(i));
			}

			if (vidaHeroi2 <= 0) {
				// Jogador 1 venceu
				return 1;
			}

			// Atualiza mesa (com copia profunda)
			@SuppressWarnings("unchecked")
			ArrayList<CartaLacaio> lacaios1clone2 = (ArrayList<CartaLacaio>) UnoptimizedDeepCopy.copy(lacaiosMesa1);
			@SuppressWarnings("unchecked")
			ArrayList<CartaLacaio> lacaios2clone2 = (ArrayList<CartaLacaio>) UnoptimizedDeepCopy.copy(lacaiosMesa2);
			mesa = new Mesa(lacaios1clone2, lacaios2clone2, vidaHeroi1, vidaHeroi2, nCartasHeroi1, nCartasHeroi2 + 1,
					turno > 10 ? 10 : turno, turno > 10 ? 10 : (turno == 1 ? 2 : turno));

			// Apontadores para jogador2
			mao = maoJogador2;
			lacaios = lacaiosMesa2;
			lacaiosOponente = lacaiosMesa1;
			jogador = 2;

			// Processa o turno 1 do Jogador2
			imprimir("\n\n----------------------- Comeco de turno para Jogador 2:");

			// Copia profunda de jogadas realizadas.
			@SuppressWarnings("unchecked")
			ArrayList<Jogada> cloneMovimentos2 = (ArrayList<Jogada>) UnoptimizedDeepCopy.copy(movimentos);

			startTime = System.nanoTime();

			if (baralho2.getCartas().size() > 0) {
				if (nCartasHeroi2 >= maxCartasMao) {
					movimentos = jogador2.processarTurno(mesa, null, cloneMovimentos2);
					comprarCarta(); // carta descartada
				} else
					movimentos = jogador2.processarTurno(mesa, comprarCarta(), cloneMovimentos2);
			} else {
				imprimir("Fadiga: O Heroi 2 recebeu " + noCardDmgCounter2
						+ " de dano por falta de cartas no baralho. (Vida restante: " + (vidaHeroi2 - noCardDmgCounter2)
						+ ").");
				vidaHeroi2 -= noCardDmgCounter2++;
				if (vidaHeroi2 <= 0) {
					// Vitoria do jogador 1
					imprimir(
							"O jogador 1 venceu porque o jogador 2 recebeu um dano mortal por falta de cartas! (Dano : "
									+ (noCardDmgCounter2 - 1) + ", Vida Heroi 2: " + vidaHeroi2 + ")");
					return 1;
				}
				movimentos = jogador2.processarTurno(mesa, null, cloneMovimentos2);
			}

			endTime = System.nanoTime();
			totalTime = endTime - startTime;
			if (tempoLimitado != 0 && totalTime > 3e8) { // 300ms
				// Limite de tempo pelo jogador 2. Vitoria do jogador 1.
				return 1;
			} else
				imprimir("Tempo usado em processarTurno(): " + totalTime / 1e6 + "ms");

			this.poderHeroicoUsado = false;
			this.lacaiosAtacaramID = new HashSet<Integer>();
			for (int i = 0; i < movimentos.size(); i++) {
				processarJogada(movimentos.get(i));
			}
			if (vidaHeroi1 <= 0) {
				// Vitoria do jogador 2
				return 2;
			}
			turno++;
		}

		// Nunca vai chegar aqui dependendo do numero de rodadas
		imprimir("Erro: A partida nao pode ser determinada em mais de 60 rounds. Provavel BUG.");
		throw new LamaException(-1, null, "Erro desconhecido. Mais de 60 turnos sem termino do jogo.", 0);
	}

	protected void processarJogada(Jogada umaJogada) throws LamaException {

		int cartaID = -1;

		if (umaJogada.getCartaJogada() != null) {
			cartaID = umaJogada.getCartaJogada().getID();
		}

		switch (umaJogada.getTipo()) {

		case ATAQUE:
			if (lacaios.contains(umaJogada.getCartaJogada())) {
				imprimir("JOGADA: Um ataque do ID=" + umaJogada.getCartaJogada().getID() + " ("
						+ umaJogada.getCartaJogada().getNome() + ") no ID="
						+ (umaJogada.getCartaAlvo() == null ? "Heroi " + (jogador == 1 ? 2 : 1)
								: Integer.valueOf(umaJogada.getCartaAlvo().getID())));

				// Erro se atacar mais de uma vez por turno
				if (lacaiosAtacaramID.contains(new Integer(umaJogada.getCartaJogada().getID()))) {
					String erroMensagem = "Erro: Nao se pode atacar com um lacaio mais de uma vez por turno. ID="
							+ cartaID + ".";
					throw new LamaException(7, umaJogada, erroMensagem, jogador == 1 ? 2 : 1);
				}
				lacaiosAtacaramID.add(new Integer(umaJogada.getCartaJogada().getID()));
				int lacAlvoID = umaJogada.getCartaAlvo() == null ? -1 : umaJogada.getCartaAlvo().getID();

				// Erro lacaio recem baixado
				if (((CartaLacaio) lacaios.get(lacaios.indexOf(umaJogada.getCartaJogada()))).getTurno() == turno) {
					String erroMensagem = "Erro: Lacaio nao pode atacar no mesmo turno que entrou na mesa. ID Lacaio="
							+ cartaID + ".";
					throw new LamaException(6, umaJogada, erroMensagem, jogador == 1 ? 2 : 1);
				}
				if (lacAlvoID == -1) {
					decVidaHeroi(((CartaLacaio) lacaios.get(lacaios.indexOf(umaJogada.getCartaJogada()))).getAtaque(),
							jogador);
				} else {
					if (lacaiosOponente.contains(umaJogada.getCartaAlvo()) == false) {
						String erroMensagem = "Erro: Lacaio invalido de alvo do ataque. ID Alvo=" + lacAlvoID
								+ ". ID Origem=" + cartaID + ".";
						throw new LamaException(8, umaJogada, erroMensagem, jogador == 1 ? 2 : 1);
					}

					int lacAtaque1 = ((CartaLacaio) lacaios.get(lacaios.indexOf(umaJogada.getCartaJogada())))
							.getAtaque();
					int lacAtaque2 = ((CartaLacaio) lacaiosOponente
							.get(lacaiosOponente.indexOf(umaJogada.getCartaAlvo()))).getAtaque();

					decVidaLac(lacAlvoID, lacaiosOponente, lacAtaque1, umaJogada, jogador == 1 ? 2 : 1);
					decVidaLac(cartaID, lacaios, lacAtaque2, umaJogada, jogador);
				}
			} else {

				// Erro se lacaio atacante e invalido
				String erroMensagem = "Erro: Lacaio atacante invalido. ID=" + cartaID + ".";
				throw new LamaException(5, umaJogada, erroMensagem, jogador == 1 ? 2 : 1);
			}

			break;

		case LACAIO:
			imprimir("JOGADA: O lacaio_id=" + cartaID + " foi baixado para a mesa.");

			// Verifica se mesa esta cheia de lacaios
			if (lacaios.size() >= 7) {
				String erroMensagem = "Erro: Tentou-se baixar a carta_id=" + cartaID + "; carta_nome="
						+ umaJogada.getCartaJogada().getNome() + ", porem ja existem 7 cartas na mesa";
				throw new LamaException(4, umaJogada, erroMensagem, jogador == 1 ? 2 : 1);
			}

			// Verifica se existe carta para baixar
			if (mao.contains(umaJogada.getCartaJogada())) {
				Carta lacaioBaixado = mao.get(mao.indexOf(umaJogada.getCartaJogada()));

				// Verifica se realmente e uma carta lacaio
				if (lacaioBaixado instanceof CartaLacaio == false) {
					String erroMensagem = "Erro: Tentou-se baixar a carta_id=" + cartaID + "; carta_nome="
							+ umaJogada.getCartaJogada().getNome() + " como lacaio, mas e carta de magia";
					throw new LamaException(3, umaJogada, erroMensagem, jogador == 1 ? 2 : 1);
				}

				// Diminui a mana
				if (jogador == 1) {
					if (lacaioBaixado.getMana() <= manaJogador1) {
						manaJogador1 -= lacaioBaixado.getMana();
					} else {
						String erroMensagem = "Erro: Tentou-se realizar jogada do tipo: "
								+ umaJogada.getTipo().toString() + " porem a mana necessaria era: "
								+ umaJogada.getCartaJogada().getMana() + " e a mana disponivel era: " + manaJogador1
								+ "";
						throw new LamaException(2, umaJogada, erroMensagem, jogador == 1 ? 2 : 1);
					}

				} else {
					if (lacaioBaixado.getMana() <= manaJogador2) {
						manaJogador2 -= lacaioBaixado.getMana();
					} else {
						String erroMensagem = "Erro: Tentou-se realizar jogada do tipo: "
								+ umaJogada.getTipo().toString() + " porem a mana necessaria era: "
								+ umaJogada.getCartaJogada().getMana() + " e a mana disponivel era: " + manaJogador2
								+ "";
						throw new LamaException(2, umaJogada, erroMensagem, jogador == 1 ? 2 : 1);
					}
				}

				((CartaLacaio) lacaioBaixado).setTurno(turno);
				lacaios.add(lacaioBaixado); // lacaio adicionado a mesa
				mao.remove(umaJogada.getCartaJogada()); // retira da mao

				// Erro: nao existe lacaio para baixar
			} else {
				String erroMensagem = "Erro: Tentou-se usar a carta_id=" + cartaID
						+ ", porem esta carta nao existe na mao. IDs de cartas na mao: ";
				for (Carta card : mao) {
					erroMensagem += card.getID() + ", ";
				}
				imprimir(erroMensagem);
				throw new LamaException(1, umaJogada, erroMensagem, jogador == 1 ? 2 : 1);
			}

			break;

		case MAGIA:
			if ((umaJogada.getCartaJogada() instanceof CartaMagia)) {

				// Magia de ALVO
				if (((CartaMagia) umaJogada.getCartaJogada()).getMagiaTipo() == TipoMagia.ALVO) {
					imprimir("JOGADA: Uma magia usada de id=" + umaJogada.getCartaJogada().getID()
							+ " mirando no lacaio_id= "
							+ (umaJogada.getCartaAlvo() == null ? "Heroi " + (jogador == 1 ? 2 : 1)
									: Integer.valueOf(umaJogada.getCartaAlvo().getID())));

					// Magia de AREA
				} else if (((CartaMagia) umaJogada.getCartaJogada()).getMagiaTipo() == TipoMagia.AREA) {
					imprimir("JOGADA: Uma magia usada de id=" + umaJogada.getCartaJogada().getID()
							+ " de dano em area.");
				} else {
					imprimir("JOGADA: Uma magia usada de id=" + umaJogada.getCartaJogada().getID() + " de buff.");
				}

				CartaMagia magia = null;

				// Verifica se possui carta na mao
				if (mao.contains(umaJogada.getCartaJogada())) {
					magia = (CartaMagia) umaJogada.getCartaJogada();
					mao.remove(umaJogada.getCartaJogada());
					if (jogador == 1) {
						nCartasHeroi1 -= 1;
					} else
						nCartasHeroi2 -= 1;
				}

				else {

					// Erro se tentar usar carta que nao possui na mao
					String erroMensagem = "Tentou usar carta que nao possui na mao. ID carta=:" + cartaID
							+ ". IDs de cartas na mao= ";
					for (Carta card : mao) {
						erroMensagem += card.getID() + ", ";
					}
					throw new LamaException(1, umaJogada, erroMensagem, jogador == 1 ? 2 : 1);
				}

				// Diminui a mana e lanca possiveis erros
				if (jogador == 1) {
					if (magia.getMana() <= manaJogador1) {
						manaJogador1 -= magia.getMana();
					} else {
						String erroMensagem = "Erro: Tentou-se realizar jogada do tipo: "
								+ umaJogada.getTipo().toString() + " porem a mana necessaria era: "
								+ umaJogada.getCartaJogada().getMana() + " e a mana disponivel era: " + manaJogador1
								+ "";
						throw new LamaException(2, umaJogada, erroMensagem, jogador == 1 ? 2 : 1);
					}
				} else {
					if (magia.getMana() <= manaJogador2) {
						manaJogador2 -= magia.getMana();
					} else {
						String erroMensagem = "Erro: Tentou-se realizar jogada do tipo: "
								+ umaJogada.getTipo().toString() + " porem a mana necessaria era: "
								+ umaJogada.getCartaJogada().getMana() + " e a mana disponivel era: " + manaJogador2
								+ "";
						throw new LamaException(2, umaJogada, erroMensagem, jogador == 1 ? 2 : 1);
					}
				}

				// Realiza o ataque de ALVO
				if (magia.getMagiaTipo() == TipoMagia.ALVO) {
					int alvo = umaJogada.getCartaAlvo() == null ? -1 : umaJogada.getCartaAlvo().getID();
					if (alvo == -1) {
						decVidaHeroi(magia.getMagiaDano(), jogador);
					} else {
						decVidaLac(alvo, lacaiosOponente, magia.getMagiaDano(), umaJogada, jogador == 1 ? 2 : 1);
					}

					// Realiza o ataque em AREA
				} else if (magia.getMagiaTipo() == TipoMagia.AREA) {
					imprimir("Usando a magia: " + magia.getNome() + " em area ...");
					ArrayList<Integer> alvos = new ArrayList<Integer>();
					for (Carta card : lacaiosOponente)
						alvos.add(new Integer(card.getID()));
					for (Integer alvo : alvos) {
						decVidaLac(alvo.intValue(), lacaiosOponente, magia.getMagiaDano(), umaJogada,
								jogador == 1 ? 2 : 1);
					}
					decVidaHeroi(magia.getMagiaDano(), jogador);

					// Uso do BUFF
				} else {
					// Erro: carta de buff sem alvo
					if (umaJogada.getCartaAlvo() == null) {
						String erroMensagem = "Erro: Tentou usar carta de buff, mas sem alvo! ID buff: " + cartaID;
						throw new LamaException(10, umaJogada, erroMensagem, jogador == 1 ? 2 : 1);
					}

					int alvoID = umaJogada.getCartaAlvo().getID();
					int valorBuff = magia.getMagiaDano();
					
					// Erro: carta de buff com alvo invalido
					if (lacaios.contains(umaJogada.getCartaAlvo()) == false) {						
						String erroMensagem = "Erro: Tentativa de buffar em lacaio invalido. ID Lacaio alvo=" + alvoID
								+ ". Alvos validos: ";
						for (Carta card : lacaios) {
							erroMensagem += card.getID() + ", ";
						}
						throw new LamaException(10, umaJogada, erroMensagem, jogador == 1 ? 2 : 1);
					}

					for (int i = 0; i < lacaios.size(); i++) {
						if (((CartaLacaio) lacaios.get(i)).getID() == alvoID) {
							int lacVida = ((CartaLacaio) lacaios.get(i)).getVidaAtual() + valorBuff;
							((CartaLacaio) lacaios.get(i)).setVidaAtual(lacVida);
							int lacVidaMax = ((CartaLacaio) lacaios.get(i)).getVidaMaxima() + valorBuff;
							((CartaLacaio) lacaios.get(i)).setVidaMaxima(lacVidaMax);
							int lacAtaque = ((CartaLacaio) lacaios.get(i)).getAtaque() + valorBuff;
							((CartaLacaio) lacaios.get(i)).setAtaque(lacAtaque);

							imprimir("Lacaio id=" + alvoID + " (" + ((CartaLacaio) lacaios.get(i)).getNome()
									+ ") buffado +" + valorBuff + "/+" + valorBuff + " (antes="
									+ (lacAtaque - valorBuff) + "/" + (lacVida - valorBuff) + ". buff=+" + valorBuff
									+ "+/" + valorBuff + ". Agora=" + lacAtaque + "/" + lacVida + ").");
							break;
						}
					}
				}

				// Carta nao era de magia
			} else {
				String erroMensagem = "Tentou usar uma jogada MAGIA, mas a cartaJogada nao era magia. ID="
						+ umaJogada.getCartaJogada().getID() + "; NOME= " + umaJogada.getCartaJogada().getNome() + "";
				throw new LamaException(9, umaJogada, erroMensagem, jogador == 1 ? 2 : 1);
			}
			break;

		case PODER:
			imprimir("JOGADA: Um poder heroico usado no ID=" + (umaJogada.getCartaAlvo() == null
					? "Heroi " + (jogador == 1 ? 2 : 1) : Integer.valueOf(umaJogada.getCartaAlvo().getID())));

			// Erro se nao tiver mana
			if (jogador == 1) {
				if (manaJogador1 < 2) {
					String erroMensagem = "Erro: Tentou-se realizar jogada do tipo: " + umaJogada.getTipo().toString()
							+ " porem a mana necessaria era: 2 e a mana disponivel era: " + manaJogador1 + "";
					throw new LamaException(2, umaJogada, erroMensagem, jogador == 1 ? 2 : 1);
				}
				manaJogador1 -= 2;
			} else {
				if (manaJogador2 < 2) {
					String erroMensagem = "Erro: Tentou-se realizar jogada do tipo: " + umaJogada.getTipo().toString()
							+ " porem a mana necessaria era: 2 e a mana disponivel era: " + manaJogador2 + "";
					throw new LamaException(2, umaJogada, erroMensagem, jogador == 1 ? 2 : 1);
				}
				manaJogador2 -= 2;
			}

			// Erro se oponente for invalido
			if (lacaiosOponente.contains(umaJogada.getCartaAlvo()) == false && umaJogada.getCartaAlvo() != null) {
				String erroMensagem = "Alvo do poder heroico invalido. ID=" + (umaJogada.getCartaAlvo() == null
						? "Heroi " + (jogador == 1 ? 2 : 1) : Integer.valueOf(umaJogada.getCartaAlvo().getID()));
				throw new LamaException(12, umaJogada, erroMensagem, jogador == 1 ? 2 : 1);
			}

			// Erro se ja usou o poder heroico no mesmo turno
			if (poderHeroicoUsado) {
				String erroMensagem = "Nao pode utilizar mais de um poder heroico por turno. ID="
						+ (umaJogada.getCartaAlvo() == null ? "Heroi " + (jogador == 1 ? 2 : 1)
								: Integer.valueOf(umaJogada.getCartaAlvo().getID()));
				throw new LamaException(11, umaJogada, erroMensagem, jogador == 1 ? 2 : 1);
			}
			poderHeroicoUsado = true;

			int alvo = umaJogada.getCartaAlvo() == null ? -1 : umaJogada.getCartaAlvo().getID();
			if (alvo == -1) {
				decVidaHeroi(1, jogador);
			} else {
				int lacAtaque = decVidaLac(alvo, lacaiosOponente, 1, umaJogada, jogador == 1 ? 2 : 1);
				decVidaHeroi(lacAtaque, jogador == 1 ? 2 : 1);
			}
			break;

		default:
			break;
		}
		return;
	}

	private Carta comprarCarta() {
		if (this.jogador == 1) {
			if (baralho1.getCartas().size() <= 0)
				throw new RuntimeException("Erro: Nao ha mais cartas no baralho para serem compradas.");
			Carta nova = baralho1.comprarCarta();
			mao.add(nova);
			nCartasHeroi1++;
			return (Carta) UnoptimizedDeepCopy.copy(nova);
		} else {
			if (baralho2.getCartas().size() <= 0)
				throw new RuntimeException("Erro: Nao ha mais cartas no baralho para serem compradas.");
			Carta nova = baralho2.comprarCarta();
			mao.add(nova);
			nCartasHeroi2++;
			return (Carta) UnoptimizedDeepCopy.copy(nova);
		}
	}

	private int decVidaLac(int alvoID, ArrayList<Carta> lacaios, int dano, Jogada jogada, int jogador)
			throws LamaException {

		// Laco para procurar lacaio a ser atacado
		for (int i = 0; i < lacaios.size(); i++) {
			if (((CartaLacaio) lacaios.get(i)).getID() == alvoID) {
				int lacVida = ((CartaLacaio) lacaios.get(i)).getVidaAtual();
				int lacAtaque = ((CartaLacaio) lacaios.get(i)).getAtaque();

				// Lacaio oponente perde vida, mas nao morre
				if (dano < lacVida) {
					((CartaLacaio) lacaios.get(i)).setVidaAtual(lacVida - dano);
					imprimir("Lacaio id=" + alvoID + " (" + ((CartaLacaio) lacaios.get(i)).getNome()
							+ ") sofreu dano mas esta vivo (Vida antes=" + lacVida + ". dano=" + dano + ". Vida agora="
							+ (lacVida - dano) + ").");
				} else {

					// Lacaio oponente morre
					imprimir("Lacaio id=" + alvoID + " (" + ((CartaLacaio) lacaios.get(i)).getNome()
							+ ") sofreu dano e morreu. (Vida antes=" + lacVida + ". dano=" + dano + ").");
					lacaios.remove(i);
				}
				return lacAtaque;
			}
		}

		// Erro ao atacar lacaio invalido
		String erroMensagem = "Erro: Tentativa de causar dano em lacaio invalido. ID Lacaio alvo=" + alvoID
				+ ". Alvos validos: ";
		for (Carta card : lacaios) {
			erroMensagem += card.getID() + ", ";
		}
		imprimir(erroMensagem);
		throw new LamaException(1, jogada, erroMensagem, jogador == 1 ? 2 : 1);
	}

	// Realiza o dano no heroi inimigo
	private void decVidaHeroi(int dano, int jogador) {
		if (jogador == 1) {
			imprimir("O heroi 2 tomou " + dano + " de dano (vida restante: " + vidaHeroi2 + ").");
			vidaHeroi2 -= dano;
		} else {
			imprimir("O heroi 1 tomou " + dano + " de dano (vida restante: " + vidaHeroi1 + ").");
			vidaHeroi1 -= dano;
		}
	}
}