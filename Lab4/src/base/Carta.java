package base;

import java.util.UUID;

public class Carta {

	private UUID id;
	private String nome;
	private int custoMana;

	public Carta(String nome, int custoMana) {

		this.nome = nome;
		this.custoMana = custoMana;
		this.id = UUID.randomUUID();
	}

	public Carta(UUID id, String nome, int custoMana) {

		this(nome, custoMana);
		this.id = id;
	}

	public int getCustoMana() {
		return custoMana;
	}

	public void setCustoMana(int custoMana) {
		this.custoMana = custoMana;
	}

	public UUID getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {

		return getNome() + " (ID: " + getId() + ")\n"
		+ "Custo de Mana: " + getCustoMana() + "\n";
	}

	// Metodo vezio
	public void usar(Carta alvo) {

	}

}
