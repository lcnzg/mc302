package base.controle;

import static util.Util.*;

import java.util.List;
import java.util.Random;
import base.Baralho;
import base.Jogada;
import base.Mesa;
import base.cartas.TipoCarta;
import base.service.BaralhoService;
import base.service.JogadaService;
import base.service.MesaService;
import base.service.ProcessadorService;
import base.service.impl.BaralhoServiceImpl;
import base.service.impl.JogadaServiceAgressivaImpl;
import base.service.impl.MesaServiceImpl;
import base.service.impl.ProcessadorServiceImpl;

public class Controle {
	private Mesa mesa;
	private Baralho baralhoP;
	private Baralho baralhoS;
	private Random gerador;
	private ProcessadorService processadorService;
	private JogadaService jogadaService;
	private BaralhoService baralhoService;
	private MesaService mesaService;

	public Controle() {
		this.baralhoP = new Baralho();
		this.baralhoS = new Baralho();
		this.mesa = new Mesa();
		gerador = new Random();
		jogadaService = new JogadaServiceAgressivaImpl();
		baralhoService = new BaralhoServiceImpl();
		mesaService = new MesaServiceImpl();
		processadorService = new ProcessadorServiceImpl();
	}

	public void executa() {

		try {
			baralhoP.addCartas(baralhoService.preencheAleatorio(gerador, MAX_CARDS, MAX_MANA, MAX_ATAQUE, MAX_VIDA));
			baralhoS.addCartas(baralhoService.preencheAleatorio(gerador, MAX_CARDS, MAX_MANA, MAX_ATAQUE, MAX_VIDA));

			mesa = mesaService.adicionaLacaios(mesa, gerador, TipoCarta.LACAIO);
			mesa = mesaService.addMaoInicial(mesa, baralhoP, baralhoS, MAO_INI);

			fim: do {
				List<Jogada> jogadas1 = jogadaService.criaJogada(mesa, 'P');
				List<Jogada> jogadas2 = jogadaService.criaJogada(mesa, 'S');

				for (Jogada jogada1 : jogadas1) {
					if (processadorService.processar(jogada1, mesa)) {
						System.out.println("###### " + jogada1.getAutor() + " venceu!");
						break fim;
					}
				}

				for (Jogada jogada2 : jogadas2) {
					if (processadorService.processar(jogada2, mesa)) {
						System.out.println("###### " + jogada2.getAutor() + " venceu!");
						break fim;
					}
				}

			} while (true);

		} catch (Exception e) {
			System.out.println(e);
		} finally {
			System.out.println("\nPartida encerrada");
		}
	}
}