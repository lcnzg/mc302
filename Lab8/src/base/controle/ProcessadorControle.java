package base.controle;

import base.Jogada;
import base.Mesa;
import base.service.ProcessadorService;
import base.service.impl.ProcessadorServiceImpl;

public class ProcessadorControle {

	private ProcessadorService ps;

	public ProcessadorControle() {
		ps = new ProcessadorServiceImpl();
	}

	public ProcessadorControle(ProcessadorService ps) {
		this.ps = ps;
	}

	public boolean processar(Jogada jogada, Mesa mesa) {		
		return ps.processar(jogada, mesa);
	}
}