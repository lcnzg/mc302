package base.service.impl;

import java.util.Random;
import static util.Util.*;

import base.Baralho;
import base.Mesa;
import base.cartas.TipoCarta;
import base.exception.MesaNulaException;
import base.exception.ValorInvalidoException;
import base.service.CartaService;
import base.service.MesaService;

public class MesaServiceImpl implements MesaService{

	public Mesa adicionaLacaios(Mesa mesa, Random gerador, TipoCarta tipo) throws MesaNulaException{
		
		if (mesa == null){
			throw new MesaNulaException("Mesa Nula");
		}
		
		CartaService cartaService = new CartaServiceImpl();
	
		for (int i = 0; i < MAX_LACAIOS; i++) {
			mesa.getLacaiosP()
					.add(cartaService.geraCartaAleatoria(gerador, MAX_MANA, MAX_ATAQUE, MAX_VIDA, TipoCarta.LACAIO));
			mesa.getLacaiosS()
					.add(cartaService.geraCartaAleatoria(gerador, MAX_MANA, MAX_ATAQUE, MAX_VIDA, TipoCarta.LACAIO));
		}
		
		return mesa;
	}
	
	public Mesa addMaoInicial(Mesa mesa, Baralho baralhoP, Baralho baralhoS, int qtd) throws ValorInvalidoException{
		
		for (int i = 0; i < qtd; i++) {
			mesa.getMaoP().add(baralhoP.comprar());
			mesa.getMaoS().add(baralhoS.comprar());
		}
		
		if (mesa.getMaoP().size() < 3){
			throw new ValorInvalidoException("MaoP com tamanho " +mesa.getMaoP().size());
		}
		
		if (mesa.getMaoS().size() < 3){
			throw new ValorInvalidoException("MaoS com tamanho " +mesa.getMaoS().size());
		}
		
		return mesa;
	}	
}