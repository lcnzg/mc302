package base.service;

import java.util.Random;

import base.Baralho;
import base.Mesa;
import base.cartas.TipoCarta;
import base.exception.MesaNulaException;
import base.exception.ValorInvalidoException;

public interface MesaService {

	public Mesa adicionaLacaios(Mesa mesa, Random gerador, TipoCarta tipo) throws MesaNulaException;
	public Mesa addMaoInicial(Mesa mesa, Baralho baralhoP, Baralho baralhoS, int qtd) throws ValorInvalidoException;	
}