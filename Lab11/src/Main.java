import java.awt.EventQueue;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.EnumSet;

/*
 * TRABALHO 2 - CÓDIGO DE EXEMPLO DE UTILIZAÇÃO DO MOTOR
 * Esta função main contém dois códigos de exemplo para chamar o Motor.
 * (1) O primeiro exemplo é para realizar uma simples partida entre duas classes Jogador.
 * (2) O segundo exemplo realiza múltiplas partidas e contabiliza o número de vitórias de cada classe Jogador.
 *
 * OBS: para alternar entre estes modos basta comentar/descomentar as respectivas linhas que chamam os métodos umaPartida() e multiplasPartidas().
 */

/**
* A classe Main é responsável por inicializar o Motor, os Jogadores e (opcionalmente) a Interface Gráfica para a execução de uma ou múltiplas partidas de LaMa (LAcaios & MAgias).
* @author Rafael Arakaki - MC302
*/
public class Main {

	public static void main(String[] args)  {
		// Gera os baralhos com IDs únicos para cada carta de cada Baralho
		Baralho baralho1 = new Baralho(Motor.gerarListaCartasPadrao(0));
		Baralho baralho2 = new Baralho(Motor.gerarListaCartasPadrao(1));
		
		// Determina se o método de embaralhamento dos baralhos será determinístico (true) ou não (false). O modo determinístico pode ajudar na depuração do programa.
		Baralho.setDeterministico(true);
		
		// A verbosidade define se o Motor deve exibir mensagens para cada Jogada feita pelos Jogadores (0=não mostrar mensagens, 1=mostrar mensagens).
		int verbosidade = 0;
		
		// A tempoLimite define se o Motor deve exigir do Jogador que processe suas jogadas dentro de um limite de tempo (0=sem limite, 1=com limite).
		int tempoLimite = 0;
		
		// Saida de arquivo
		PrintWriter saidaArquivo = null;
		
		// Funcionalidade ativas: nenhuma
		EnumSet<Funcionalidade> funcionalidades = EnumSet.noneOf(Funcionalidade.class);
		
		// Funcionalidade ativas: todas
		//EnumSet<Funcionalidade> funcionalidades = EnumSet.of(Funcionalidade.PROVOCAR, Funcionalidade.INVESTIDA, Funcionalidade.ATAQUE_DUPLO);
		
		// Rotina que executa uma partida de LaMa
		//umaPartida(baralho1, baralho2, verbosidade, tempoLimite, saidaArquivo, funcionalidades);
		
		// Rotina que executa múltiplas partidas de LaMa
		multiplasPartidas(baralho1, baralho2, verbosidade, tempoLimite, saidaArquivo, funcionalidades, 10000);
	}
	
	/**
	  * Executa uma única partida de LaMa e diz o vencedor.
	  * 
	  * @param baralho1 Baralho do jogadorA
	  * @param baralho2 Baralho do jogadorB
	  * @param verbosidade Parâmetro do Motor, onde 0=não verboso e 1= verboso.
	  * @param tempoLimite Parâmetro do Motor, onde 0=não ativo e 1= ativo.
	  * @param saidaArquivo Parâmetro do Motor, onde null=não ativo, e do contrário aponta para um PrintWriter.
	  * @param funcionalidades Parâmetro do Motor, conjunto contendo as funcionalidades que devem estar ativas no Motor.
	  */
	private static void umaPartida(Baralho baralho1, Baralho baralho2, int verbosidade, int tempoLimite, PrintWriter saidaArquivo, EnumSet<Funcionalidade> funcionalidades){
		// Embaralha os Baralhos
		baralho1.embaralhar();
		baralho2.embaralhar();
		
		// Declara estruturas que armazenam as cartas da mão
		ArrayList<Carta> Mao1, Mao2;
		Mao1 = new ArrayList<Carta>();
		Mao2 = new ArrayList<Carta>();
		
		// Adiciona cartas à mão de cada jogador com o número de cartas correspondente (para o primeiro Jogador é Motor.cartasIniJogador1 cartas, e para o segundo Jogador é Motor.cartasIniJogador2).
		for(int i = 0; i < Motor.cartasIniJogador1; i++){
			Mao1.add(baralho1.getCartas().get(0));
			baralho1.getCartas().remove(0);
		}
		for(int i = 0; i < Motor.cartasIniJogador2; i++){
			Mao2.add(baralho2.getCartas().get(0));
			baralho2.getCartas().remove(0);
		}
		
		// Inicializa uma cópia para ser enviada ao Jogador das cartas da mão
		@SuppressWarnings("unchecked")
		ArrayList<Carta> mao1clone = (ArrayList<Carta>) UnoptimizedDeepCopy.copy(Mao1);
		@SuppressWarnings("unchecked")
		ArrayList<Carta> mao2clone = (ArrayList<Carta>) UnoptimizedDeepCopy.copy(Mao2);
		
		// Inicializa os Jogadores devidamente (com as cartas da mão e o argumento 'primeiro').
		Jogador jogA = new JogadorAleatorioFuncionalidades(mao1clone, true, funcionalidades);
		Jogador jogB = new JogadorAleatorioFuncionalidades(mao2clone, false, funcionalidades);
		
		// O Motor é construído
		Motor partida = new MotorRA182835(baralho1, baralho2, Mao1, Mao2, jogA, jogB, verbosidade, tempoLimite, saidaArquivo, funcionalidades);
		
		// Verifica o vencedor da partida
		// A partida é executada
		int vencedor;
		try {
			vencedor = partida.executarPartida();
		}
		catch (LamaException e) {
			System.out.println("LamaException numero de erro: "+e.getNumeroErro());
			System.out.println("LamaException mensagem: "+e.getMessage());
			System.out.println("LamaException jogador vencedor: "+(e.getVencedor()==1?"primeiro":"segundo"));
			e.printStackTrace();
			vencedor = e.getVencedor();
		}
		
		if(vencedor == 1)
			System.out.println("O primeiro venceu! (classe "+jogA.getClass().getName()+").");
		else
			System.out.println("O segundo venceu! (classe "+jogB.getClass().getName()+").");
	}
	
	/**
	  * Executa múltiplas partidas de LaMa, sendo metade das partidas com jogadorA como primeiro e jogadorB como segundo,
	  * e a outra metade invertendo-se o esquema (jogadorB como primeiro e jogadorA como segundo).
	  * 
	  * @param baralho1 Baralho do jogadorA
	  * @param baralho2 Baralho do jogadorB
	  * @param verbosidade Parâmetro do Motor, onde 0=não verboso e 1= verboso.
	  * @param tempoLimite Parâmetro do Motor, onde 0=não ativo e 1= ativo.
	  * @param saidaArquivo Parâmetro do Motor, onde null=não ativo, e do contrário aponta para um PrintWriter.
	  * @param funcionalidades Parâmetro do Motor, conjunto contendo as funcionalidades que devem estar ativas no Motor.
	  * @param n Número de partidas a serem executadas (deve ser um número par).
	  */
	private static void multiplasPartidas(Baralho baralho1, Baralho baralho2, int verbosidade, int tempoLimite, PrintWriter saidaArquivo, EnumSet<Funcionalidade> funcionalidades, int n){
		int a = 0, b = 0;
		
		if(n%2 == 1)
			throw new RuntimeException ("Número de múltiplas partidas deve ser um número par (para que seja justo). Número informado: "+n);
		
		boolean moeda = true;
		// São revezadas as partidas com jogA como primeiro e jogB como segundo.
		// E depois partidas com jogB como primeiro e jogA como segundo.
		for(int p = 0; p < 2; p++){
			for(int w = 0; w < n/2; w++){
				// Realiza uma cópia dos Baralhos para uma partida
				Baralho deck1 = (Baralho) UnoptimizedDeepCopy.copy(baralho1);
				Baralho deck2 = (Baralho) UnoptimizedDeepCopy.copy(baralho2);
				
				// Embaralha os Baralhos
				deck1.embaralhar();
				deck2.embaralhar();
				
				// Declara estruturas que armazenam as cartas da mão
				ArrayList<Carta> Mao1, Mao2;
				Mao1 = new ArrayList<Carta>();
				Mao2 = new ArrayList<Carta>();
				
				// Adiciona cartas à mão de cada jogador com o número de cartas correspondente (para o primeiro Jogador é Motor.cartasIniJogador1 cartas, e para o segundo Jogador é Motor.cartasIniJogador2).
				for(int i = 0; i < (moeda?Motor.cartasIniJogador1:Motor.cartasIniJogador2); i++){
					Mao1.add(deck1.getCartas().get(0));
					deck1.getCartas().remove(0);
				}
				for(int i = 0; i < (moeda?Motor.cartasIniJogador2:Motor.cartasIniJogador1); i++){
					Mao2.add(deck2.getCartas().get(0));
					deck2.getCartas().remove(0);
				}
				
				// Inicializa uma cópia para ser enviada ao Jogador das cartas da mão
				@SuppressWarnings("unchecked")
				ArrayList<Carta> mao1clone = (ArrayList<Carta>) UnoptimizedDeepCopy.copy(Mao1);
				@SuppressWarnings("unchecked")
				ArrayList<Carta> mao2clone = (ArrayList<Carta>) UnoptimizedDeepCopy.copy(Mao2);
				
				// Inicializa os Jogadores devidamente (com as cartas da mão e o argumento 'primeiro').
				Jogador jogA = new JogadorAleatorioFuncionalidades(mao1clone, moeda, funcionalidades);
				Jogador jogB = new JogadorAleatorioFuncionalidades(mao2clone, !moeda, funcionalidades);
				
				// O Motor é construído
				Motor partida;
				if(moeda)
					partida = new MotorRA182835(deck1, deck2, Mao1, Mao2, jogA, jogB, verbosidade, tempoLimite, saidaArquivo, funcionalidades);
				else
					partida = new MotorRA182835(deck2, deck1, Mao2, Mao1, jogB, jogA, verbosidade, tempoLimite, saidaArquivo, funcionalidades);
				
				// A partida é executada
				int vencedor;
				try {
					vencedor = partida.executarPartida();
				}
				catch (LamaException e) {
					System.out.println("LamaException numero de erro: "+e.getNumeroErro());
					System.out.println("LamaException mensagem: "+e.getMessage());
					System.out.println("LamaException jogador vencedor: "+(e.getVencedor()==1?"primeiro":"segundo"));
					e.printStackTrace();
					vencedor = e.getVencedor();
				}
				
				// Cria-se um contador para ver qual jogador venceu mais
				if( (moeda && vencedor == 1) || (!moeda && vencedor == 2) ){
					System.out.println("O jogador A venceu! (classe "+jogA.getClass().getName()+"), que é o "+ (moeda?"primeiro":"segundo") +" jogador desta partida.");
					a++;
				}
				else{
					System.out.println("O jogador B venceu! (classe "+jogB.getClass().getName()+"), que é o "+ (moeda?"segundo":"primeiro") +" jogador desta partida.");
					b++;
				}
			}
			// Muda as posições dos jogadores! O jogador1 passa a ser o segundo e o jogador2 passa a ser o primeiro da partida do LaMa.
			moeda = !moeda;
		}
		
		System.out.println("JogA wins: "+a);
		System.out.println("JogB wins: "+b);
		return;
	}
}

/*  -------- Lista de cartas (criadas em Motor.gerarListaCartasPadrao()) --------------
 * Obs: Cartas do jogadorA possuem IDs < 100 e cartas do jogadorB possuem IDs >= 100.
 * Exemplo: as duas cartas Gnomo no Baralho do jogadorA são IDs 0 e 1. As duas cartas Gnomo no Baralho do JogadorB são IDs 100 e 101.
 * 
 * 																								// [custo de mana] (ataque/vida)
		Carta c1 = new CartaLacaio(player*100+0,    "Gnomo",				1, 2, 1, 1, TipoEfeito.NADA, -1); // [1] (2/1)
		Carta c2 = new CartaLacaio(player*100+2,    "Guerreiro Orc",		2, 3, 2, 2, TipoEfeito.NADA, -1); // [2] (3/2)
		Carta c3 = new CartaLacaio(player*100+4,    "Guerreiro Espadachim",	2, 2, 3, 3, TipoEfeito.NADA, -1); // [2] (2/3)
		Carta c4 = new CartaLacaio(player*100+6,    "Mestre Orc",			3, 4, 2, 2, TipoEfeito.NADA, -1); // [3] (4/2)
		Carta c5 = new CartaLacaio(player*100+8,    "Filhote de Dragão",	3, 3, 4, 4, TipoEfeito.NADA, -1); // [3] (3/4)
		Carta c6 = new CartaLacaio(player*100+10,   "Cavaleiro",			3, 3, 1, 1, TipoEfeito.INVESTIDA, -1); // [3] (3/1) (efeito: INVESTIDA)
		Carta c7 = new CartaLacaio(player*100+12,   "Gigante de Pedra",		4, 4, 5, 5, TipoEfeito.NADA, -1); // [4] (4/5)
		Carta c8 = new CartaLacaio(player*100+14,   "Arqueira Experiente",	4, 3, 5, 5, TipoEfeito.ATAQUE_DUPLO, -1); // [4] (3/5) (efeito: ATAQUE DUPLO)
		Carta c9 = new CartaLacaio(player*100+16,   "Mestre Espadachim",   	5, 3, 9, 9, TipoEfeito.PROVOCAR, -1); // [5] (3/9) (efeito: PROVOCAR)
		Carta c10 = new CartaLacaio(player*100+18,  "Mestre Mago",          5, 7, 3, 3, TipoEfeito.NADA, -1); // [5] (7/3)
		Carta c11 = new CartaLacaio(player*100+20,  "Dragão",               7, 7, 7, 7, TipoEfeito.NADA, -1); // [7] (7/7)
		
		Carta z1 = new CartaMagia(player*100+22,   "Rajada Congelante",    	3, TipoMagia.ALVO, 3); // [2] 3 dano no alvo
		Carta z2 = new CartaMagia(player*100+24,   "Raio",					5, TipoMagia.ALVO, 7); // [5] 7 dano no alvo
		Carta z3 = new CartaMagia(player*100+26,   "Benção dos Deuses",		2, TipoMagia.BUFF, 2); // [2] +2/+2 para um lacaio (+2 ataque, +2 vida)
		Carta z4 = new CartaMagia(player*100+28,   "Mininova",				7, TipoMagia.AREA, 4); // [7] 4 dano em area
		
		Carta c1_2 = new CartaLacaio(player*100+1,  "Gnomo",				1, 2, 1, 1, TipoEfeito.NADA, -1);
		Carta c2_2 = new CartaLacaio(player*100+3,  "Guerreiro Orc",		2, 3, 2, 2, TipoEfeito.NADA, -1);
		Carta c3_2 = new CartaLacaio(player*100+5,  "Guerreiro Espadachim",	2, 2, 3, 3, TipoEfeito.NADA, -1);
		Carta c4_2 = new CartaLacaio(player*100+7,  "Mestre Orc",			3, 4, 2, 2, TipoEfeito.NADA, -1);
		Carta c5_2 = new CartaLacaio(player*100+9,  "Filhote de Dragão",	3, 3, 4, 4, TipoEfeito.NADA, -1);
		Carta c6_2 = new CartaLacaio(player*100+11, "Cavaleiro",			3, 3, 1, 1, TipoEfeito.NADA, -1);
		Carta c7_2 = new CartaLacaio(player*100+13, "Gigante de Pedra",		4, 4, 5, 5, TipoEfeito.NADA, -1);
		Carta c8_2 = new CartaLacaio(player*100+15, "Arqueira Experiente",	4, 3, 5, 5, TipoEfeito.NADA, -1);
		Carta c9_2 = new CartaLacaio(player*100+17, "Mestre Espadachim",   	5, 3, 9, 9, TipoEfeito.NADA, -1);
		Carta c10_2 = new CartaLacaio(player*100+19,"Mestre Mago",          5, 7, 3, 3, TipoEfeito.NADA, -1);
		Carta c11_2 = new CartaLacaio(player*100+21,"Dragão",               7, 7, 7, 7, TipoEfeito.NADA, -1);
		
		Carta z1_2 = new CartaMagia(player*100+23, "Rajada Congelante",    	3, TipoMagia.ALVO, 3);
		Carta z2_2 = new CartaMagia(player*100+25, "Raio",					5, TipoMagia.ALVO, 7);
		Carta z3_2 = new CartaMagia(player*100+27, "Benção dos Deuses",		2, TipoMagia.BUFF, 2);
		Carta z4_2 = new CartaMagia(player*100+29, "Mininova",				7, TipoMagia.AREA, 4);
*/
