package treeAVL;

import java.io.*;

public class RunAVL {
    public static void main(String[] args) {
        AVLTree<Integer> arvore = new AVLTree<>();

        String inputPath = "src/arvores.txt";
        String outputPath = "src/saida.txt";

        try (
                BufferedReader reader = new BufferedReader(new FileReader(inputPath));
                BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))
        ) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                linha = linha.trim();

                if (linha.isEmpty()) continue;

                if (linha.startsWith("I-")) {
                    int valor = Integer.parseInt(linha.substring(2));
                    arvore.inserir(valor);

                } else if (linha.startsWith("R-")) {
                    int valor = Integer.parseInt(linha.substring(2));
                    arvore.remover(valor);

                } else if (linha.equalsIgnoreCase("P") || linha.equalsIgnoreCase("P1")) {
                    writer.write("P1: ");
                    arvore.percorrerEmOrdem(dado -> {
                        try {
                            writer.write("Valor (" + dado + ") ; ");
                        } catch (IOException e) {
                            throw new RuntimeException("Erro ao escrever no arquivo.", e);
                        }
                    });
                    writer.newLine();

                } else if (linha.equalsIgnoreCase("P2")) {
                    writer.write("P2: ");
                    arvore.percorrerPosOrdem(dado -> {
                        try {
                            writer.write("Valor (" + dado + ") ; ");
                        } catch (IOException e) {
                            throw new RuntimeException("Erro ao escrever no arquivo.", e);
                        }
                    });
                    writer.newLine();

                } else if (linha.equalsIgnoreCase("P3")) {
                    writer.write("P3: ");
                    arvore.percorrerPreOrdem(dado -> {
                        try {
                            writer.write("Valor (" + dado + ") ; ");
                        } catch (IOException e) {
                            throw new RuntimeException("Erro ao escrever no arquivo.", e);
                        }
                    });
                    writer.newLine();
                }
            }

            System.out.println("Operações concluídas. Verifique o arquivo 'saida.txt'.");

        } catch (IOException e) {
            System.err.println("Erro ao processar os arquivos: " + e.getMessage());
        }
    }
}
