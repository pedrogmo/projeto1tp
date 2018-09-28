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
			BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Digite o local e o nome do arquivo a ser lido, mais sua extensao .txt: ");
			String localArquivo = teclado.readLine();			
			BufferedReader arq = new BufferedReader(new FileReader(localArquivo));
			int linhas = Integer.parseInt(arq.readLine().trim());
			int colunas = Integer.parseInt(arq.readLine().trim());
			System.out.println("\nLinhas: " + linhas + "\nColunas: " + colunas + "\n");
			char[][] matriz = new char[linhas][colunas];
			for (int l = 0; l < linhas; l++)
			{
				String linha = arq.readLine();
				for (int c = 0; c < colunas; c++)
				{
					char valoratual = linha.charAt(c);				
					matriz[l][c] = valoratual;
				}
			}
			arq.close();
			Labirinto lab = new Labirinto(matriz, linhas, colunas);
			lab.procurarSaida();			
			Coordenada saida = lab.getSaida();
			System.out.println("Labirinto resolvido.");
			System.out.println("Caminho: " + lab.getCaminhoFinal());
			System.out.println("Saida em: " + saida.toString());			
			String arqSaida = localArquivo.substring(0, localArquivo.length() - 4) + ".res.txt";
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