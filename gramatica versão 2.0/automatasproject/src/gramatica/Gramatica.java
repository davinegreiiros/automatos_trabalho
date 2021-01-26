package gramatica;

import java.util.ArrayList;
import java.util.List;

import exception.GramaticaException;

public class Gramatica {
	//GRAMATICA PARA LER " (),{},[],/,\"
	
	protected List<Variavel> variaveis;

	protected List<Character> terminais;

	protected Variavel inicial;
	
	public Gramatica(Character variavelInicial) {
		variaveis = new ArrayList<>();
		terminais = new ArrayList<>();
		inicial = new Variavel(this, variavelInicial);
		variaveis.add(inicial);
	}
	
	
	protected Gramatica() {
		
	}
	
	
	public void adicionarVariavel(Character nome) {
		if(buscarVariavel(nome) == null) {
			variaveis.add(new Variavel(this,nome));
		}else {
			throw new GramaticaException("Já existe uma variavel que se chama" + nome + ".");
		}
	}
		
	public void adicionarTerminais(Character nome) {
		if (Character.isUpperCase(nome) && Character.isLetter(nome)) {
			throw new GramaticaException("Terminal nao pode ser uma letra maiuscula ou miniscula");
		}
		if (!terminais.contains(nome)) {
			terminais.add(nome);
		} else {
			throw new GramaticaException("Já existe um terminal que se chama " + nome + ".");
		}
	}
	
	public void adicionarProducao(Character variavel, String producao) {
		for (Variavel v : variaveis) {
			if (v.getNome().equals(variavel)) {
				v.adicionarProducao(producao);
				return;
			}
		}
	}
	
	public boolean possuiTerminal(Character c) {
		for (Character aux : terminais) {
			if (aux.equals(c)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean possuiVariavel(Character c) {
		for (Variavel aux : variaveis) {
			if (aux.getNome().equals(c)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		String aux = "G = ({";
		for (int i = 0; i < variaveis.size(); i++) {
			aux += variaveis.get(i).getNome();
			if (i != variaveis.size() - 1) {
				aux += ", ";
			}
		}
		aux += "}, {";
		for (int i = 0; i < terminais.size(); i++) {
			aux += terminais.get(i);
			if (i != terminais.size() - 1) {
				aux += ", ";
			}
		}
		aux += "}, P, " + inicial.getNome() + ")\n\nP = {";

		for (int i = 0; i < variaveis.size(); i++) {
			aux += variaveis.get(i);
			if (i != variaveis.size() - 1) {
				aux += "\n";
			}
		}

		aux += "}";

		return aux;
	}
	
	public Variavel buscarVariavel(Character c) {
		for (Variavel v : variaveis) {
			if (v.getNome().equals(c)) {
				return v;
			}
		}
		return null;
	}
	
	@Override
	public Gramatica clone() {
		Gramatica g = new Gramatica();
		g.variaveis = new ArrayList<>();
		g.terminais = new ArrayList<>();
		g.inicial = inicial.clone();
		g.variaveis.add(g.inicial);

		for (char c : terminais) {
			g.terminais.add(c);
		}

		for (Variavel v : variaveis) {
			if (v != inicial) {
				g.variaveis.add(v.clone());
			}
		}

		for (Variavel v : g.variaveis) {
			v.setGramatica(g);
		}
		return g;
	}
	
	public void removerProducao(Character variavel, String producao) {
		Variavel v = buscarVariavel(variavel);
		if (v != null) {
			if (producao.trim().equals("Ø")) {
				producao = "";
			}
			v.removerProducao(producao);
		}
	}
	
	public void removerVariavel(Character variavel) {
		Variavel var = buscarVariavel(variavel);
		if (var == inicial) {
			throw new GramaticaException("Impossível remover a variável inicial.");
		}
		variaveis.remove(var);
		for (Variavel v : variaveis) {
			for (int i = 0; i < v.getProducoes().size(); i++) {
				String prod = v.getProducoes().get(i);
				if (prod.contains(variavel.toString())) {
					v.getProducoes().remove(i);
					i--;
				}
			}
		}
	}

	public void removerTerminal(Character terminal) {
		terminais.remove(terminal);
		for (Variavel v : variaveis) {
			for (int i = 0; i < v.getProducoes().size(); i++) {
				String prod = v.getProducoes().get(i);
				if (prod.contains(terminal.toString())) {
					v.getProducoes().remove(i);
					i--;
				}
			}
		}
	}
	
public List<Variavel> getVariaveis(){
	return variaveis;
}

public List<Character> getTerminais(){
	return terminais;
}
	
}
