package base.cartas;

import java.util.UUID;

import base.Carta;

public class Lacaio extends Carta {

	private int ataque;
	private int vidaAtual;
	private int vidaMaxima;

	// Metodo Construtor padrao
	public Lacaio(UUID id, String nome, int custoMana, int ataque, int vidaAtual, int vidaMaxima) {

		super(id, nome, custoMana);
		this.ataque = ataque;
		this.vidaAtual = vidaAtual;
		this.vidaMaxima = vidaMaxima;
	}

	public Lacaio(UUID id, String nome, int mana, int ataque, int vida) {

		this(id, nome, mana, ataque, vida, vida);
	}

	// Construtor reduzido
	public Lacaio(UUID id, String nome, int mana) {

		this(id, nome, mana, 0, 0, 0);
	}

	public Lacaio(String nome, int custoMana, int ataque, int vidaAtual, int vidaMaxima) {

		super(nome, custoMana);
		this.ataque = ataque;
		this.vidaAtual = vidaAtual;
		this.vidaMaxima = vidaMaxima;

	}

	// Construtor copia
	public Lacaio(Lacaio lacaio) {

		this(lacaio.getId(), lacaio.getNome(), lacaio.getCustoMana(), lacaio.getAtaque(), lacaio.getVidaAtual(),
				lacaio.getVidaMaxima());
	}

	// Metodos gets e sets
	public int getAtaque() {
		return ataque;
	}

	public void setAtaque(int ataque) {
		this.ataque = ataque;
	}

	public int getVidaAtual() {
		return vidaAtual;
	}

	public void setVidaAtual(int vidaAtual) {
		this.vidaAtual = vidaAtual;
	}

	public int getVidaMaxima() {
		return vidaMaxima;
	}

	public void setVidaMaxima(int vidaMaxima) {
		this.vidaMaxima = vidaMaxima;
	}

	public void usar(Carta alvo) {

		((Lacaio) alvo).setVidaAtual(((Lacaio) alvo).getVidaAtual() - ((Lacaio) alvo).getAtaque());

		if (((Lacaio) alvo).getVidaAtual() < 0) {
			((Lacaio) alvo).setVidaAtual(0);
		}

	}

	@Override
	public String toString() {
		return super.toString() + "Ataque: " + getAtaque() + "\n"
				+ "Vida Atual: " + getVidaAtual() + "\n"
				+ "Vida Maxima: " + getVidaMaxima() + "\n";
	}

}