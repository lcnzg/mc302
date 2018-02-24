package io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Escritor {
	private File arquivo;
	private FileWriter escreve;
	
	public Escritor() throws IOException{		
		arquivo = new File("arquivo.txt");
		escreve = new FileWriter(arquivo, true);
	}

	// Escreve um atributo no arquivo seguindo as especificacoes do formato
	// padrao
	public void escreveAtributo(String nomeAtributo, String valor) throws IOException {
		escreve.write(""+nomeAtributo+" " +valor+ "\n");
	}

	// Adiciona uma linha ao arquivo indicando o inicio/termino da especificação
	// de um objeto
	public void escreveDelimObj(String nomeObj) throws IOException {		
		escreve.write("obj "+nomeObj+"\n");
	}

	// Fecha o arquivo onde os dados foram escritos
	public void finalizar() throws IOException {			
		escreve.close();
	}
}