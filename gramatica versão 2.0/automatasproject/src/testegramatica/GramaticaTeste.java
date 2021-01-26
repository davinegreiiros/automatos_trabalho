package testegramatica;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import gramatica.Gramatica;
import gramatica.grammar2;

import org.junit.Assert;


class GramaticaTeste {

	@Test
	public void TesteDeAceitacao() {
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
	
	
	@Test
	public void Teste2() {
		Gramatica g2 = new Gramatica('S');
		g2.adicionarVariavel('E');
		g2.adicionarVariavel('F');
		g2.adicionarVariavel('G');
		g2.adicionarTerminais('i');
		g2.adicionarTerminais('j');
		g2.adicionarTerminais('g');
		g2.adicionarProducao('S', "iE");
		g2.adicionarProducao('E', "i");
		g2.adicionarProducao('E', "G");
		g2.adicionarProducao('F', "EFE");
		g2.adicionarProducao('G', "g");
		g2.adicionarProducao('G', "");
		
		Assert.assertEquals(g2.toString(),
				"G = ({S, E, F, G}, {i, j, g}, P, S)\n\nP = {S -> iE\nE -> i | G\nF -> EFE\nG -> g | Ø}");
	}
		
		
		
	
}

