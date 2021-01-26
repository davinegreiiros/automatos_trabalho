package testegramatica;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import gramatica.Gramatica;
import gramatica.grammar2;

import org.junit.Assert;


class GramaticaTeste {

	@Test
	public void TesteDeAce() {
		Gramatica g = new Gramatica('S');
		g.adicionarVariavel('B');
		g.adicionarVariavel('C');
		g.adicionarVariavel('D');
		g.adicionarTerminais('a');
		g.adicionarTerminais('b');
		g.adicionarTerminais('d');
		g.adicionarProducao('S', "aB");
		g.adicionarProducao('B', "b");
		g.adicionarProducao('B', "C");
		g.adicionarProducao('C', "BDB");
		g.adicionarProducao('D', "d");
		g.adicionarProducao('D', "");

		Assert.assertEquals(g.toString(),
				"G = ({S, B, C, D}, {a, b, d}, P, S)\n\nP = {S -> aB\nB -> b | C\nC -> BDB\nD -> d | Ø}");
	}
	
}
