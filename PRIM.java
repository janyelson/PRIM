import java.util.Scanner;
import java.util.Arrays;

public class PRIM {
	public static void main(String args[]) {

		Scanner scan = new Scanner(System.in);
		int[][] mat; //Mapeamento das distâncias utilizando Matriz de Adj.
		No[] v; //Array de Vértices
		No[] q; 
		No r = null;

		int n; //Nº de Vértices

		String linha = "";

		linha = scan.nextLine();
		n = Integer.parseInt(linha.split("\\s+")[0]);

		mat = new int[n][];
		v = new No[n];
		q = new No[n];
		int aux = 0;

		//Inicia o mapeamento com 0.
		//Coloca todos os vértices de 0 á n-1 em V e cópia V para Q
		for(int i = 0; i < n; i++) {
			mat[i] = new int[n];
			Arrays.fill(mat[i], 0);
			
			v[i] = new No(i);
			q[i] = v[i];
		}

		//Coloca v0 como vertice inicial
		v[0].setChave(0);
		//r = v[0];

		//Ler no arquivo as distâncias e cria a matriz de adjacencia
		for(int i=0; i < n; ++i){
			if(i != (n-1)) linha = scan.nextLine();
			for(int j=i+1; j < n; ++j){
				int valor = Integer.parseInt(linha.split("\\s+")[aux]);
				mat[i][j] = valor;
				mat[j][i] = valor;
				aux++;
        	}
        	aux = 0;
		}

		scan.close();

		No u;
		int tam = (n-1);

		//faz a iteração até que a heap estiver vazia
		for(int j = tam; j > 0; j--) {

			u = retiraMinimo(q, tam);
			swap(q, u.verticeId, j); //Coloca o vertice retirado para o final do array
			tam--;//Faz agora para (tam - 1) não contando com o elemento retirado

			//Para cada vertice (vi), verifica vi esta na lista de prioridade e se a distancia para o vertice 'u' retirado da lista
			//é menor que o valor guardado(chave) do vi
			for(int i = 0; i < n; i++) {
				if(v[i].verticeId != u.verticeId) {//Nao precisa disso ja que o vertice verificado ja foi retirado
					if(pertence(q, v[i], n) && (mat[u.verticeId][v[i].verticeId] < v[i].getChave())) {
						v[i].setPai(u);
						v[i].setChave(mat[u.verticeId][v[i].verticeId]);
					}
				}
			}
		}

		int soma = 0;
		//Faz o somatorio de todos os valores armazenado nos vertices 
		for(int i = 0; i < n; i++) {
			soma += v[i].getChave();
		}

		System.out.println("Valor: " + soma);
	}

	//Seleciona o vertice com a chave minimaS 
	public static No retiraMinimo(No[] q, int n) {

		No min = q[0];
		for(int i = 1; i < n; i++) {
			if(q[i].getChave() < min.getChave()) {
				min = q[i];
			}
		}

		return min;
	}

	
	//Troca de posição de 2 vertices no array q 
	public static void swap(No[] q, int i, int menor) {
		No t = q[i];
		q[i] = q[menor];
		q[menor] = t;
	}

	//Verifica se o vertice v está no array q
	public static boolean pertence(No[] q, No v, int n) {
		for(int i = 0; i < n; i++) {
			if(q[i].verticeId == v.verticeId) {
				return true;
			}
		}

		return false;
	}
}

class No {

	public int verticeId;
	private int chave;
	private No pai;

	public No(int indice) {
		this.verticeId = indice;
		pai = null;
		chave = Integer.MAX_VALUE;
	}

	public int getChave() {
		return this.chave;
	}

	public No getPai() {
		return this.pai;
	}

	public void setChave(int i) {
		this.chave = i;
	}

	public void setPai(No pai) {
		this.pai = pai;
	}
}