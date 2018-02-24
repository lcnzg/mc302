package io;

import io.Escritor;
import java.io.IOException;

public interface ILaMaSerializable {
	void escreveAtributos(Escritor fw) throws IOException;
}