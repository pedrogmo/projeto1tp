import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Labirinto
{
	public static void main(String[] args)
	{
		try
		{

			FileReader fr_arq = new FileReader("teste1.txt");
			BufferedReader arq = new BufferedReader(fr_arq);
			int linhas = Integer.parseInt(arq.readLine().trim());
			int colunas = Integer.parseInt(arq.readLine().trim());
			System.out.println("Linhas: " + linhas + "\nColunas: " + colunas);

			char[][] matriz = new char[linhas][colunas];
			Pilha<Coordenada> caminho = new Pilha<Coordenada>(colunas * linhas);
			Pilha<Fila<Coordenada>> possibilidades = new Pilha<Fila<Coordenada>>(colunas * linhas);
			Fila<Coordenada> fila = new Fila<Coordenada>(3);
			Coordenada atual = null;

			for (int l = 0; l < linhas; l++)
			{
				String linha = arq.readLine();
				for (int c = 0; c < colunas; c++)
				{
					char valoratual = linha.charAt(c);
					matriz[l][c] = valoratual;
					if (valoratual == 'E')
						atual = new Coordenada(l,c);
				}
			}
			System.out.println("Começo do labirinto: " + atual.toString());			
			arq.close();
		}
		catch (Exception erro)
		{
			System.err.println(erro.getMessage());
		}
	}
}