package gramatica;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class grammar2  extends Gramatica{

		//GRAMATICA DE LETRAS [a-z] && [A-Z]
		
	public grammar2(Gramatica gramatica) {
		Gramatica g = gramatica.clone();
		this.variaveis = g.variaveis;
		this.terminais = g.terminais;
		this.inicial = g.inicial;
		removerProducoesVazias();
		removerProducoesUnitarias();
		while (removerVariaveisInuteis());
	}

	

	private void removerProducoesVazias() {
		List<Variavel> fechoVazio = new ArrayList<>();

		for (Variavel v : variaveis) {
			if (v.temProducao("")) {
				fechoVazio.add(v);
				v.removerProducao("");
			}
		}

		boolean continuar;
		do {
			continuar = false;

			for (Variavel v : variaveis) {
				if (fechoVazio.contains(v)) {
					continue;
				}
				for (Variavel temVazio : fechoVazio) {
					if (v.temProducao(temVazio.toString())) {
						fechoVazio.add(v);
						continuar = true;
					}
				}
			}
		} while (continuar);

		for (Variavel v : variaveis) {
			v.substituirProducoesVazias(fechoVazio);
		}
	}

	private void removerProducoesUnitarias() {
		for (Variavel v : variaveis) {
			v.substituirProducoesUnitarias(variaveis);
		}
	}

	/* Retorna true se encontrou simbolos inuteis ---- @return */
	private boolean removerVariaveisInuteis() {
		return removerVariaveisImpossiveisDeSeChegar() || removerVariaveisQueNaoLevamATerminais();
	}

	/* Retorna true se removeu alguem  ---- @return */
	protected boolean removerVariaveisImpossiveisDeSeChegar() {
		boolean encontrouInutil = false;
		Set<Character> uteis = new HashSet<Character>();
		encontrarVariaveisPossiveisDeChegarRecursivamente(uteis, inicial);
		for (int i = 0; i < variaveis.size(); i++) {
			Variavel v = variaveis.get(i);
			if (!uteis.contains(v.getNome())) {
				variaveis.remove(v);
				i--;
				encontrouInutil = true;
			}
		}
		return encontrouInutil;
	}

	private void encontrarVariaveisPossiveisDeChegarRecursivamente(Set<Character> uteis, Variavel raiz) {
		uteis.add(raiz.getNome());

		for (String prod : raiz.getProducoes()) {
			for (Character c : prod.toCharArray()) {
				Variavel v = buscarVariavel(c);
				if (v != null) {
					if (!uteis.contains(v.getNome())) {
						encontrarVariaveisPossiveisDeChegarRecursivamente(uteis, v);
					}
				}
			}
		}
	}

	/**
	 * Retorna true se removeu alguem
	 * 
	 * @return
	 */
	private boolean removerVariaveisQueNaoLevamATerminais() {
		Set<Character> uteis = new HashSet<Character>();
		verificarRecursivamenteSeChegaATerminal(uteis, "[a-z]*");

		boolean removeAlguem = false;

		for (int i = 0; i < variaveis.size(); i++) {
			Variavel v = variaveis.get(i);
			if (!uteis.contains(v.getNome())) {
				removeAlguem = true;
				variaveis.remove(v);
				i--;
				for (Variavel v2 : variaveis) {
					for (int j = 0; j < v2.getProducoes().size(); j++) {
						String prod = v2.getProducoes().get(j);
						if (prod.contains(v.getNome().toString())) {
							v2.getProducoes().remove(j);
							j--;
						}
					}
				}
			}
		}

		return removeAlguem;
	}

	/* Retorna as variaveis que levam a terminais *  * @param uteis* @param regex * @return*/
	private void verificarRecursivamenteSeChegaATerminal(Set<Character> uteis, String regex) {
		Pattern p = Pattern.compile(regex);
		boolean continuar = false;
		outer: for (Variavel v : variaveis) {
			if (uteis.contains(v.getNome())) {
				continue;
			}
			for (String prod : v.getProducoes()) {
				if (p.matcher(prod).matches()) {
					uteis.add(v.getNome());
					continuar = true;
					continue outer;
				}
			}
		}
		if (continuar) {
			StringBuffer buffer = new StringBuffer("[a-z,");
			for (Character c : uteis) {
				buffer.append(c + ",");
			}
			buffer.append("]*");
			verificarRecursivamenteSeChegaATerminal(uteis, buffer.toString());
		}
	}
	
	
	
	
}
