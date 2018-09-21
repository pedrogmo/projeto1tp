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
					System.out.print(valoratual + " ");
					matriz[l][c] = valoratual;
					if (c==0 || l==0 || c== colunas - 1 || l == linhas - 1) //se é a borda do labirinto
						if (valoratual == 'E')
							atual = new Coordenada(l,c);
				}
				System.out.println();
			}
			System.out.println();
			arq.close();
			if (atual == null)
				throw new Exception("Caractere 'E' do início do labirinto não econtrado.");
			System.out.println("Começo do labirinto: " + atual.toString());
			boolean acabou = false;
			Fila<Coordenada> fila;

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
				System.out.println(fila.toString());

				if (!fila.isVazia()) //há lugar para ir, modo progressivo
				{
					System.out.println("\nModo progressivo");
					atual = fila.getUmItem();
					int lPasso = atual.getLinha();
					int cPasso = atual.getColuna();
					if (matriz[lPasso][cPasso] == 'S')
					{
						acabou = true;
						System.out.println("Labirinto resolvido, a saida esta em " + atual.toString());
					}
					else //não é o fim, é só um espaçoem branco. Tem que dar passo
					{
						matriz[lPasso][cPasso] = '*'; //dar passo
						fila.jogueForaUmItem();
						caminho.guarde(atual);
						possibilidades.guarde(fila);
					}
					//printa matriz a cada repetição, para visualização
					for(int l = 0; l < linhas; l++)
					{
						for(int c = 0; c < colunas; c++)
							System.out.print(matriz[l][c]);
						System.out.println();
					}
				}
				else //não há lugar para ir, modo regressivo
				{
					boolean sairRegressivo = false;
					matriz[atual.getLinha()][atual.getColuna()] = ' ';
					do
					{
						System.out.println("Modo regressivo");						
						atual = caminho.getUmItem();
						matriz[atual.getLinha()][atual.getColuna()] = ' ';
						caminho.jogueForaUmItem();
						Fila anterior = possibilidades.getUmItem();
						possibilidades.jogueForaUmItem();
						if (!anterior.isVazia()) //fila anterior recuperada tem coordenadass para ir
						{
							atual = (Coordenada) anterior.getUmItem();
							caminho.guarde(atual);
							possibilidades.guarde(anterior);
							matriz[atual.getLinha()][atual.getColuna()] = '*';
							sairRegressivo = true;
						}
						else if (possibilidades.isVazia()) //se se essgotaram asss possibilidades
							throw new Exception("Não foi possível resolver o labirinto");
						for(int l = 0; l < linhas; l++)
						{
							for(int c = 0; c < colunas; c++)
								System.out.print(matriz[l][c]);
							System.out.println();
						}
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
}