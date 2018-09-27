import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.OutPutStream;
import java.io.PrintStream;
import java.io.FileWriter;
import java.io.IOException;

import classes.coordenada.Coordenada;
import classes.pilha.Pilha;
import classes.fila.Fila;
import classes.labirinto.Labirinto;

public class Programa
{
	public static void main(String[] args)
	{
		try
		{			
			/*BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Digite o nome do arquivo a ser lido, mais sua extensao .txt: ");
			String localArquivo = teclado.readLine();*/
			String localArquivo = "teste5.txt";
			BufferedReader arq = new BufferedReader(new FileReader("testes//" + localArquivo));
			int linhas = Integer.parseInt(arq.readLine().trim());
			int colunas = Integer.parseInt(arq.readLine().trim());
			System.out.println("Linhas: " + linhas + "\nColunas: " + colunas + "\n");
			char[][] matriz = new char[linhas][colunas];
			for (int l = 0; l < linhas; l++)
			{
				String linha = arq.readLine();
				for (int c = 0; c < colunas; c++)
				{
					char valoratual = linha.charAt(c);
					System.out.print(valoratual + " ");
					matriz[l][c] = valoratual;
				}
				System.out.println();
			}
			arq.close();
			Labirinto lab = new Labirinto(matriz, linhas, colunas);
			lab.procurarSaida();
			
			Coordenada saida = lab.getSaida();
			System.out.println("Labirinto resolvido.");
			System.out.print("Caminho: ");
			Pilha<Coordenada> inverso = lab.getInversoDeCaminho();
			while(!inverso.isVazia())
			{
				System.out.print(inverso.getUmItem() + " ");
				inverso.jogueForaUmItem();
			}
			System.out.println("\nSaida em: " + saida.toString());
			
			String arqSaida = "resultado//" + localArquivo.substring(0, localArquivo.length() - 4) + ".res.txt";
			PrintStream resultado = new PrintStream(arqSaida);
			resultado.println(lab.toString());
			resultado.close();
			System.out.println("Solucao do labirinto no arquivo texto: " + arqSaida);
		}
		catch (Exception erro)
		{
			System.err.println(erro.getMessage());
		}
	}
}