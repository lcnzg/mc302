package base.cartas.magias;

import java.util.UUID;

import base.Carta;
import base.cartas.Lacaio;

public class Buff extends Magia {

	private int aumentoEmAtaque;
	private int aumentoEmVida;

	public Buff(UUID id, String nome, int custoMana, int aumentoEmAtaque, int aumentoEmVida) {
		super(id, nome, custoMana);
		this.aumentoEmAtaque = aumentoEmAtaque;
		this.aumentoEmVida = aumentoEmVida;
	}

	public Buff(String nome, int custoMana, int aumentoEmAtaque, int aumentoEmVida) {
		super(nome, custoMana);
		this.aumentoEmAtaque = aumentoEmAtaque;
		this.aumentoEmVida = aumentoEmVida;
	}

	public int getAumentoEmAtaque() {
		return aumentoEmAtaque;
	}

	public void setAumentoEmAtaque(int aumentoEmAtaque) {
		this.aumentoEmAtaque = aumentoEmAtaque;
	}

	public int getAumentoEmVida() {
		return aumentoEmVida;
	}

	public void setAumentoEmVida(int aumentoEmVida) {
		this.aumentoEmVida = aumentoEmVida;
	}

	@Override
	public String toString() {
		return super.toString() + "Aumento em Ataque: " + getAumentoEmAtaque() + "\n"
				+ "Aumento em Vida: " + getAumentoEmVida() + "\n";
	}

	public void usar(Carta alvo) {

		((Lacaio) alvo).setVidaAtual(((Lacaio) alvo).getVidaAtual() + getAumentoEmVida());
		((Lacaio) alvo).setVidaMaxima(((Lacaio) alvo).getVidaMaxima() + getAumentoEmVida());
		((Lacaio) alvo).setAtaque(((Lacaio) alvo).getAtaque() + getAumentoEmAtaque());
	}

}
