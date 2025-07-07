package treeRedBlack;

import java.io.*;

public class RunRedBlack {
    public static void main(String[] args) {
        RedBlackTree<Integer> arvore = new RedBlackTree<>();

        String inputPath = "src/arvores.txt";
        String outputPath = "src/treeRedBlack/saidaRB.txt";

        try (
                BufferedReader reader = new BufferedReader(new FileReader(inputPath));
                BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))
        ) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                linha = linha.trim();

                if (linha.isEmpty()) continue;

                String comando = linha.toUpperCase(); // converte para maiúsculo

                if (comando.startsWith("I-")) {
                    int valor = Integer.parseInt(linha.substring(2));
                    arvore.inserir(valor);

                } else if (comando.startsWith("R-")) {
                    int valor = Integer.parseInt(linha.substring(2));
                    arvore.remover(valor);

                } else if (comando.equals("P") || comando.equals("P1")) {
                    writer.write("P1 (Em-Ordem): ");
                    escreverEmOrdem(arvore, writer);

                } else if (comando.equals("P2")) {
                    writer.write("P2 (Pós-Ordem): ");
                    escreverPosOrdem(arvore, writer);

                } else if (comando.equals("P3")) {
                    writer.write("P3 (Pré-Ordem): ");
                    escreverPreOrdem(arvore, writer);
                }
            }

            System.out.println("Operações concluídas. Verifique o arquivo 'saida.txt'.");

        } catch (IOException e) {
            System.err.println("Erro ao processar os arquivos: " + e.getMessage());
        }
    }

    private static void escreverEmOrdem(RedBlackTree<Integer> arvore, BufferedWriter writer) throws IOException {
        arvore.percorrerEmOrdem(new AcaoTexto() {
            public void executar(String texto) {
                try {
                    writer.write(texto + "; ");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        writer.newLine();
    }

    private static void escreverPreOrdem(RedBlackTree<Integer> arvore, BufferedWriter writer) throws IOException {
        arvore.percorrerPreOrdem(new AcaoTexto() {
            public void executar(String texto) {
                try {
                    writer.write(texto + " ; ");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        writer.newLine();
    }

    private static void escreverPosOrdem(RedBlackTree<Integer> arvore, BufferedWriter writer) throws IOException {
        arvore.percorrerPosOrdem(new AcaoTexto() {
            public void executar(String texto) {
                try {
                    writer.write(texto + " ; ");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        writer.newLine();
    }


}
