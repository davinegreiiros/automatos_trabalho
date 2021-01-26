package gramatica;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import exception.GramaticaException;

public class Variavel {
	private Gramatica gramatica;
	private Character nome;
	private List<String> producoes;
	
	public Variavel(Gramatica gramatica, Character nome) {
		if (Character.isLowerCase(nome) || !Character.isLetter(nome)) {
			throw new GramaticaException("Colocar variaveis maiusculas ou minusculas ");
		}
		this.gramatica  = gramatica;
		this.nome = nome;
		producoes = new ArrayList<>();
	}
	
	private Variavel() {
		
	}
	
	public Character getNome() {
		return nome;
	}
	
	public void adicionarProducao(String producao) {
		for(Character c : producao.toCharArray()) {
			if (Character.isLowerCase(c) && !gramatica.possuiTerminal(c)) {
				throw new GramaticaException("Nao existe um terminal valido para essa gramatica - " + c);
			}
			if (Character.isUpperCase(c) && !gramatica.possuiVariavel(c)) {
				throw new GramaticaException("Nao existe uma variavel valida para essa gramatica - " + c);
			}
		}
		if (!producoes.contains(producao) && !nome.toString().equals(producao)) {
			producoes.add(producao);
		}
		
	}
	
	@Override
	public String toString() {
		String aux = nome + " -> ";
		for(int i = 0; i < producoes.size(); i++) {
			String p = producoes.get(i);
			if(p.isEmpty()) {
				p = "Ø";
			}
			aux += p;
			if(i != producoes.size() - 1) {
				aux += " | ";
			}
		}
		return aux;
		
	}
	
	public boolean temProducao(String prod) {
		for(String s : producoes) {
			if(s.equals(prod)) {
				return true;
			}
		}
		return false;
	}
	
	public void removerProducao(String prod) {
		producoes.remove(prod);
	}
	
	public void substituirProducoesVazias(List<Variavel> fechoVazio) {
		for(Variavel v: fechoVazio) {
			if(v == this) {
				return;
			}
			
			List<String> novaLista = new ArrayList<String>();
			for(int i = 0; i < producoes.size(); i++) {
				String prod = producoes.get(i);
				if(prod.contains(v.getNome().toString())) {
					novaLista.addAll(gerarCombinacoesSemTerminal(prod,v.getNome()));
				}
			}
			for(String prod : novaLista) {
				if(!prod.isEmpty() && !producoes.contains(prod) && !nome.toString().equals(prod)) {
					producoes.add(prod);
				}
			}
		}
		
	}
	
	private Set<String> gerarCombinacoesSemTerminal(String prod, Character terminal) {
		Set<String> combinacoes = new HashSet<String>();
		
		int total = 0;
		for(Character c : prod.toCharArray()) {
			if(c == terminal) {
				total++;
			}
		}
		
		
		for(int j = 1; j <= total; j++) {
			String aux = "";
			int pos = 0;
			for(Character c : prod.toCharArray()) {
				if(c == terminal) {
					pos++;
					if(pos != j) {
						aux += c;
					}
				} else {
					aux += c;
				}
				
			}
			combinacoes.add(aux);
		}
		
		Set<String> novasCombinacoes = new HashSet<String>();
		for(String s : combinacoes) {
			novasCombinacoes.addAll(gerarCombinacoesSemTerminal(s, terminal));
		}
		combinacoes.addAll(novasCombinacoes);
		
		return combinacoes;
	}


	public void substituirProducoesUnitarias(List<Variavel> variaveis) {
		for(Variavel v : variaveis) {
			if(v == this) {
				continue;
			}
			
			if(temProducao(v.getNome().toString())) {
				List<String> producoesParaAdicionar = new ArrayList<>(v.getProducoes());
				producoesParaAdicionar.remove(nome.toString());
				removerProducao(v.getNome().toString());
				for(String s : producoesParaAdicionar) {
					if(!producoes.contains(s) && !nome.toString().equals(s)) {
						producoes.add(s);
					}
				}
			}
		}
	}

	
	

	public List<String> getProducoes(){
		return producoes;
		
	}
	
	
	public void buscarProducoesForaDaFNC(List<String> producoesParaModificar) {
		Pattern p = Pattern.compile("[A-Z]{2}|[a-z]{1}");
		for(String prod : producoes) {
			if(!p.matcher(prod).matches()) {
				if(!producoesParaModificar.contains(prod)) {
					producoesParaModificar.add(prod);
				}
			}
		}
	}
	
	
	public void trocarProducoes(Map<String, String> trocas) {
		List<String> novaLista = new ArrayList<String>();
		for(int i = 0; i < producoes.size(); ) {
			String aux = producoes.remove(i);
			for(String troca : trocas.keySet()) {
				aux = aux.replace(troca, trocas.get(troca));
			}
			novaLista.add(aux);
		}
		for(String prod : novaLista) {
			if(!producoes.contains(prod)) {
				producoes.add(prod);
			}
		}	
	}
	
	
	public boolean temRecursaoAEsquerda() {
		for(String prod : producoes) {
			if(prod.startsWith(nome.toString())) {
				return true;
			}
		}
		return false;
	}
	
	
	public List<String> getRecursoesAEsquerda() {
		List<String> novaLista = new ArrayList<String>();
		for(String prod : producoes) {
			if(prod.startsWith(nome.toString())) {
				novaLista.add(prod);
			}
		}
		return novaLista;
	}
	
	
	public void adicionarProducaoSemValidarVariavel(String producao) {
		for(Character c : producao.toCharArray()) {
			if (Character.isLowerCase(c) && !gramatica.possuiTerminal(c)) {
				throw new GramaticaException("Nao existe um terminal valido para essa gramatica - " + c);
			}
		}
		if(!nome.toString().equals(producao) && !producoes.contains(producao)) {
			producoes.add(producao);
		}
	}
	
	
	public Variavel clone() {
		Variavel v = new Variavel();
		v.nome = nome;
		v.producoes = new ArrayList<>();
		for(String prod : producoes) {
			v.producoes.add(new String(prod));
		}
		return v;
	}
	
	public void setGramatica(Gramatica gramatica) {
		this.gramatica = gramatica;
	}	
	
}

	
	
	
	
	
	
	

