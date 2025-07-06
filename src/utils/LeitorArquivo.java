package utils;

import treeAVL.AVLTree;

import java.io.*;

public class LeitorArquivo {

    public static void processarArquivo(String caminhoEntrada, String caminhoSaida) {
        AVLTree<Integer> arvore = new AVLTree<>();

        try (
                BufferedReader br = new BufferedReader(new FileReader(caminhoEntrada));
                BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoSaida))
        ) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linha = linha.trim();

                if (linha.isEmpty()) continue;

                if (linha.startsWith("I-")) {
                    int valor = Integer.parseInt(linha.substring(2));
                    arvore.inserir(valor);

                } else if (linha.startsWith("R-")) {
                    int valor = Integer.parseInt(linha.substring(2));
                    arvore.remover(valor);

                } else if (linha.equals("P") || linha.equals("P1")) {
                    writer.write("P1: ");
                    arvore.percorrerEmOrdem(dado -> {
                        try {
                            writer.write("Valor (" + dado + ") ; ");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    writer.newLine();

                } else if (linha.equals("P2")) {
                    writer.write("P2: ");
                    arvore.percorrerPosOrdem(dado -> {
                        try {
                            writer.write("Valor (" + dado + ") ; ");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    writer.newLine();

                } else if (linha.equals("P3")) {
                    writer.write("P3: ");
                    arvore.percorrerPreOrdem(dado -> {
                        try {
                            writer.write("Valor (" + dado + ") ; ");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    writer.newLine();

                } else {
                    writer.write("Comando inválido: " + linha);
                    writer.newLine();
                }
            }

            System.out.println("Processamento concluído. Verifique o arquivo: " + caminhoSaida);

        } catch (IOException e) {
            System.err.println("Erro ao ler ou escrever arquivos: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Erro no formato de número: " + e.getMessage());
        }
    }

    // Método principal para testar a leitura
    public static void main(String[] args) {
        String entrada = "src/arvores.txt";
        String saida = "src/saida.txt";
        processarArquivo(entrada, saida);
    }
}