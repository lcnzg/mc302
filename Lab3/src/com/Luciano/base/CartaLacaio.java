package com.Luciano.base;

public class CartaLacaio {

	private int ID;
	private String nome;
	private int ataque;
	private int vidaAtual;
	private int vidaMaxima;
	private int custoMana;

	// Metodo Construtor padrao
	public CartaLacaio(int ID, String nome, int ataque, int vidaAtual, int vidaMaxima, int mana) {

		this.ID = ID;
		this.nome = nome;
		this.ataque = ataque;
		this.vidaAtual = vidaAtual;
		this.vidaMaxima = vidaMaxima;
		this.custoMana = mana;
	}

	public CartaLacaio(int ID, String nome, int ataque, int vida, int mana) {

		this(ID, nome, ataque, vida, vida, mana);
	}

	// Construtor reduzido
	public CartaLacaio(int ID, String nome, int mana) {

		this(ID, nome, 0, 0, 0, mana);
	}

	// Construtor copia
	public CartaLacaio(CartaLacaio lacaio) {

		this(lacaio.ID, lacaio.nome, lacaio.ataque, lacaio.vidaAtual, lacaio.vidaMaxima, lacaio.custoMana);
	}

	// Metodos gets e sets
	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

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

	public int getCustoMana() {
		return custoMana;
	}

	public void setCustoMana(int custoMana) {
		this.custoMana = custoMana;
	}

	@Override
	public String toString() {

		return getNome() + " (ID: " + getID()+ ")\n"
		+ "Ataque = " + getAtaque() + "\n"
		+ "Vida Atual = " + getVidaAtual() + "\n"
		+ "Vida Maxima = " + getVidaMaxima() + "\n"
		+ "Custo de Mana = " + getCustoMana() + "\n";
	}
}