import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import classes.coordenada.*;
import classes.pilha.*;
import classes.fila.*;

public class Labirinto
{
	public static void main(String[] args)
	{
		try
		{
			//leitura do arquivo
			BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in)) ;
			System.out.print("Digite o nome do arquivo a ser lido e sua extensao .txt: ");
			String localArquivo = teclado.readLine();
			FileReader fr_arq = new FileReader(localArquivo);
			BufferedReader arq = new BufferedReader(fr_arq);
			int linhas = Integer.parseInt(arq.readLine().trim());
			int colunas = Integer.parseInt(arq.readLine().trim());
			if (linhas <= 0 || colunas <= 0)
				throw new Exception("Quantiade de linhas/colunas inválida");
			System.out.println("Linhas: " + linhas + "\nColunas: " + colunas + "\n");
			char[][] matriz = new char[linhas][colunas];
			Pilha<Coordenada> caminho = new Pilha<Coordenada>(colunas * linhas);
			Pilha<Fila<Coordenada>> possibilidades = new Pilha<Fila<Coordenada>>(colunas * linhas);
			Coordenada atual = null;
			//leitura do arquivo texto, atribuicao dos valores da matriz
			for (int l = 0; l < linhas; l++)
			{
				String linha = arq.readLine();
				for (int c = 0; c < colunas; c++)
				{
					char valoratual = linha.charAt(c);
					System.out.print(valoratual + " ");
					matriz[l][c] = valoratual;
					if (c==0 || l==0 || c == colunas - 1 || l == linhas - 1) //se e a borda do labirinto
						if (valoratual == 'E') //se o caractere lido e 'E', inicio do labirinto
							atual = new Coordenada(l,c);
				}
				System.out.println();
			}
			arq.close();
			if (atual == null)//se o valor de atual nao for alterado, nao achou a entrada
				throw new Exception("Caractere 'E' do início do labirinto não econtrado.");
			System.out.println("Comeco do labirinto: " + atual.toString());

			Fila<Coordenada> fila = null; //fila de coordenadas armazenadas e possivelmente tomadas
			//tres variaveis boolean para controle dos loops e dos desvios de codigo:
			boolean acabou = false;//controle se achou a saida
			boolean passouRegressivo = false;//controle se passou pelo modo regressivo
			boolean sairRegressivo = false;//verifica se ja saiu do modo regressivo

			do
			{
				if (!passouRegressivo) 	//na primeira leitura da matriz, e nas outras do modo progressivo, tem que verificar as coordenadas adjacentes à atual
										//no entanto, quando o programa sai do modo regressivo, a fila já tem coordenadas, então não é necessário ler as adjacentes, apenas depois
				{
					fila = new Fila<Coordenada>(3);
					//colocar as coordenadas possiveis de movimento na fila
					int cAtual = atual.getColuna();
					int lAtual = atual.getLinha();
					if (cAtual + 1 < colunas)         //para direita
						if (matriz[lAtual][cAtual + 1] == ' ' || matriz[lAtual][cAtual + 1] == 'S')
							fila.guarde(new Coordenada(lAtual, cAtual + 1));
					if (lAtual + 1 < linhas)          //para baixo
						if (matriz[lAtual + 1][cAtual] == ' ' || matriz[lAtual + 1][cAtual] == 'S')
							fila.guarde(new Coordenada(lAtual + 1, cAtual));
					if (lAtual - 1 >= 0)              //para cima
						if (matriz[lAtual - 1][cAtual] == ' ' || matriz[lAtual - 1][cAtual] == 'S')
							fila.guarde(new Coordenada(lAtual - 1, cAtual));
					if (cAtual - 1 >= 0)         	  //para esquerda
						if (matriz[lAtual][cAtual - 1] == ' ' || matriz[lAtual][cAtual - 1] == 'S')
							fila.guarde(new Coordenada(lAtual, cAtual - 1));
				}
				else //se o programa saiu do modo regressivo
					passouRegressivo = false; //voltou para modo progressivo, para voltar a ler as adjacentes
				if (!fila.isVazia()) //ha lugar para ir, modo progressivo
				{
					System.out.println("\nModo progressivo");
					atual = fila.getUmItem();
					fila.jogueForaUmItem();
					int lPasso = atual.getLinha();
					int cPasso = atual.getColuna();
					if (matriz[lPasso][cPasso] == 'S') //achou saida
					{
						System.out.println("\n\nLabirinto resolvido.");
						acabou = true;
						Pilha<Coordenada> inverso = new Pilha<Coordenada>(colunas * linhas);
						while (!caminho.isVazia())
						{
							inverso.guarde(caminho.getUmItem());
							caminho.jogueForaUmItem();
						}
						System.out.print("Caminho: ");
						while(!inverso.isVazia())
						{
							System.out.print(inverso.getUmItem() + " ");
							inverso.jogueForaUmItem();
						}
						System.out.println("\nSaida: " + atual.toString());
					}
					else //nao e a saida, e so um espaco em branco. Tem que dar passo
					{
						matriz[lPasso][cPasso] = '*'; //dar passo
						printaMatriz(matriz, linhas, colunas);//printa como o labirinto esta
						caminho.guarde(atual);
						possibilidades.guarde(fila);
						//printa matriz a cada repeticao, para visualizacao
					}
				}
				else //nao ha lugar para ir, modo regressivo
				{
					passouRegressivo = true;// afirma que passou pelo regressivo
					sairRegressivo = false;
					do
					{

						System.out.println("\nModo regressivo");
						atual = caminho.getUmItem(); //desempilha um item de caminho
						caminho.jogueForaUmItem();
						matriz[atual.getLinha()][atual.getColuna()] = ' ';
						printaMatriz(matriz, linhas, colunas);
						fila = possibilidades.getUmItem(); //desempilha fila de possibilidades
						possibilidades.jogueForaUmItem();
						if (!fila.isVazia()) //fila anterior recuperada tem coordenadas para ir
							sairRegressivo = true;//afirma que saiu
						if (possibilidades.isVazia()) //se se esgotaram as possibilidades
							throw new Exception("Não foi possível resolver o labirinto");
					}
					while (!sairRegressivo);
				}
			}
			while(!acabou);
		}
		catch (Exception erro)
		{
			System.err.println(erro.getMessage());
		}
	}

	protected static void printaMatriz(char[][] m, int maxl, int maxc)
	{
		if (maxl * maxc < 1000) //só printa matriz se a quantidade de caracteres for menor que 1000, se não o programa não roda
			for(int l = 0; l < maxl; l++)
			{
				for(int c = 0; c < maxc; c++)
					System.out.print(m[l][c] + " ");
				System.out.println();
			}
	}
}