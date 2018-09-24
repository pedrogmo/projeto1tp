import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Labirinto
{
	public static void main(String[] args)
	{
		try
		{
			BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in)) ;
			System.out.print("Digite o nome do arquivo a ser lido e sua extensao .txt: ");
			String localArquivo = teclado.readLine();
			FileReader fr_arq = new FileReader(localArquivo);
			BufferedReader arq = new BufferedReader(fr_arq);
			int linhas = Integer.parseInt(arq.readLine().trim());
			int colunas = Integer.parseInt(arq.readLine().trim());
			System.out.println("Linhas: " + linhas + "\nColunas: " + colunas + "\n");
			char[][] matriz = new char[linhas][colunas];
			Pilha<Coordenada> caminho = new Pilha<Coordenada>(colunas * linhas);
			Pilha<Fila<Coordenada>> possibilidades = new Pilha<Fila<Coordenada>>(colunas * linhas);
			Coordenada atual = null;
			//leitura do arquivo texto, atribuição dos valores da matriz
			for (int l = 0; l < linhas; l++)
			{
				String linha = arq.readLine();
				for (int c = 0; c < colunas; c++)
				{
					char valoratual = linha.charAt(c);
					//System.out.print(valoratual + " ");
					matriz[l][c] = valoratual;
					if (c==0 || l==0 || c== colunas - 1 || l == linhas - 1) //se é a borda do labirinto
						if (valoratual == 'E') //se o caractere lido é 'E', início do labirinto
							atual = new Coordenada(l,c);
				}
				System.out.println();
			}
			arq.close();
			if (atual == null)
				throw new Exception("Caractere 'E' do início do labirinto não econtrado.");
			System.out.println("Comeco do labirinto: " + atual.toString());

			Fila<Coordenada> fila;
			boolean acabou = false;
			boolean passouRegressivo = false;
			do
			{
				fila = new Fila<Coordenada>(3);
				//colocar as coordenadas possíveis de movimento na fila
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

				if (!fila.isVazia()) //há lugar para ir, modo progressivo
				{
					if (passouRegressivo)
					{
						if (matriz[lPasso][cPasso] == 'S') //achou saída
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
					else
					{
						matriz[atual.getLinha()][atual.getColuna()] = '*';
					}
					}
					System.out.println("\nModo progressivo");
					atual = fila.getUmItem();
					fila.jogueForaUmItem();
					int lPasso = atual.getLinha();
					int cPasso = atual.getColuna();
					if (matriz[lPasso][cPasso] == 'S') //achou saída
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
					else //não é o saída, é só um espaço em branco. Tem que dar passo
					{
						matriz[lPasso][cPasso] = '*'; //dar passo
						printaMatriz(matriz, linhas, colunas);
						caminho.guarde(atual);
						possibilidades.guarde(fila);
						//printa matriz a cada repetição, para visualização
					}
				}
				else //não há lugar para ir, modo regressivo
				{
					boolean sairRegressivo = false;
					do
					{
						passouRegressivo = true;
						System.out.println("\nModo regressivo");
						atual = caminho.getUmItem(); //desempilha um item de caminho
						caminho.jogueForaUmItem();
						matriz[atual.getLinha()][atual.getColuna()] = ' ';
						printaMatriz(matriz, linhas, colunas);
						fila = possibilidades.getUmItem(); //desempilha fila de possibilidades
						possibilidades.jogueForaUmItem();
						if (!fila.isVazia()) //fila anterior recuperada tem coordenadas para ir
						{
							/*volta para modo progressivo
							System.out.println("\nModo progressivo");
							atual = (Coordenada) fila.getUmItem();
							caminho.guarde(atual);
							possibilidades.guarde(fila);
							matriz[atual.getLinha()][atual.getColuna()] = '*';
							printaMatriz(matriz, linhas, colunas);*/
							atual = (Coordenada) fila.getUmItem();

							sairRegressivo = true;
						}
						if (possibilidades.isVazia()) //se se essgotaram asss possibilidades
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
		for(int l = 0; l < maxl; l++)
		{
			for(int c = 0; c < maxc; c++)
				System.out.print(m[l][c] + " ");
			System.out.println();
		}
	}
}