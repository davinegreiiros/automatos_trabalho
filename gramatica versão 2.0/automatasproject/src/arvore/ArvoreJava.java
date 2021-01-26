package arvore;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ArvoreJava {
	private Node raiz;

	private static class Node {
		String Elem;
		Node Pai;
		List<Node> Filhos;

		public Node(String valor) {
			Elem = valor;
			Pai = null;
			Filhos = new ArrayList<>();
		}
	}

	public ArvoreJava (String elem) {
		raiz = new Node(elem);
	}

	public void inserir(String elem, String paiStr) {
		Node pai = pesquisa(paiStr, raiz);
		if (pai != null) {
			Node novo = new Node(elem);
			novo.Pai = pai;
			pai.Filhos.add(novo);
		}
	}

	private Node pesquisa(String e, Node r) {
		if (r.Elem.equals(e))
			return r;
		for (Node f : r.Filhos) {
			Node aux = pesquisa(e, f);
			if (aux != null)
				return aux;
		}
		return null;
	}

	public void preordem() {
		preordem(raiz);
	}

	private void preordem(Node r) {
		System.out.print(r.Elem + " - ");
		for (Node f : r.Filhos) {
			preordem(f);
		}
	}

	public void largura() {
		Queue<Node> q = new LinkedList<>();

		q.add(raiz);
		while (!q.isEmpty()) {
			Node aux = q.remove();
			System.out.print(aux.Elem + " - ");
			for (Node f : aux.Filhos) {
				q.add(f);
			}
		}
	}

	public int altura(String string) {
		return 0;
	}

	public List<String> ascendentes(String string) {
		Node aux = pesquisa(string, raiz);
		if (aux != null) {
			ascendentes(aux);
		}
		return null;
	}

	private void ascendentes(Node aux) {

	}

	public int contarDescendentes(String string) {
		Node aux = pesquisa(string, raiz);
		if (aux != null) {
			return contarDescendentes(aux);
		}
		return 0;
	}

	public String listarFilhos(String string) {
		Node aux = pesquisa(string, raiz);
		if (aux != null) {
			return listarFilhos(aux);
		}
		return "";
	}

	private String listarFilhos(Node aux) {
		String lista = "";
		for (Node filhos : aux.Filhos) {
			lista += filhos.Elem;
		}
		return lista;
	}

	public List<String> netos(String string) {
		Node aux = pesquisa(string, raiz);
		return listarNetos(aux);
	}

	private List<String> listarNetos(Node aux) {
		List<String> lista = new ArrayList<String>();
		for (Node filhos : aux.Filhos) {
			lista.add(listarFilhos(filhos));
		}
		return lista;
	}

	private int contarDescendentes(Node node) {
		int total = node.Filhos.size();
		for (Node filhos : node.Filhos) {
			return total += contarDescendentes(filhos);
		}
		return total;
	}

	public String tios(String string) {
		Node aux = pesquisa(string, raiz);
		if (aux != null) {
			return tios(aux);
		}
		return "";
	}

	private String tios(Node aux) {
		Node meuPai = aux.Pai;
		Node avo = meuPai.Pai;
		String lista = "";
		for (Node filhosAvo : avo.Filhos) {
			if (filhosAvo != meuPai) {
				lista += filhosAvo;
			}
		}
		return "";
	}

	public List<Node> primos(String string) {
		Node aux = pesquisa(string, raiz);
		return primos(aux);
	}

	private List<Node> primos(Node aux) {
		Node meuPai = aux.Pai;
		Node meuAvo = meuPai.Pai;
		List<Node> lista = new ArrayList<Node>();
		for (Node filhosAvo : meuAvo.Filhos) {
			if (meuPai != meuAvo.Filhos) {
				lista.addAll(filhosAvo.Filhos);
			}
		}
		return null;
	}

	public void mostrar(String string) {
		// TODO Auto-generated method stub

	}
}