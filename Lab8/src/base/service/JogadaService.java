package base.service;

import java.util.List;

import base.Jogada;
import base.Mesa;

public interface JogadaService {

	List<Jogada> criaJogada(Mesa mesa, char c);
}