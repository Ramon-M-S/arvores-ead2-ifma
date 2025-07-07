package treeRedBlack;

public class RedBlackTree<T extends Comparable<T>> {

    private NoRB<T> raiz;
    private final NoRB<T> TNULL;

    public RedBlackTree() {
        TNULL = new NoRB<>(Cor.PRETO);
        TNULL.setEsquerda(null);
        TNULL.setDireita(null);
        TNULL.setPai(null);
        raiz = TNULL;
    }

    // Inserção pública
    public void inserir(T dado) {
        if (dado == null) {
            throw new IllegalArgumentException("Valor nulo não pode ser inserido na árvore.");
        }

        NoRB<T> novoNo = new NoRB<>(dado);
        novoNo.setEsquerda(TNULL);
        novoNo.setDireita(TNULL);
        novoNo.setPai(null);

        NoRB<T> y = null;
        NoRB<T> x = raiz;

        while (x != TNULL) {
            y = x;
            if (dado.compareTo(x.getDado()) < 0) {
                x = x.getEsquerda();
            } else {
                x = x.getDireita();
            }
        }

        novoNo.setPai(y);
        if (y == null) {
            raiz = novoNo;
            System.out.println("Inserido como raiz: " + dado);
        } else if (dado.compareTo(y.getDado()) < 0) {
            y.setEsquerda(novoNo);
            System.out.println("Inserido à esquerda de " + y.getDado() + ": " + dado);
        } else {
            y.setDireita(novoNo);
            System.out.println("Inserido à direita de " + y.getDado() + ": " + dado);
        }

        if (novoNo.getPai() == null) {
            novoNo.setCor(Cor.PRETO);
            return;
        }

        if (novoNo.getPai().getPai() == null) {
            return;
        }

        inserirFixup(novoNo);
    }

    // Rebalanceamento após inserção
    private void inserirFixup(NoRB<T> no) {
        NoRB<T> tio;

        while (no.getPai() != null && no.getPai().getCor() == Cor.VERMELHO) {
            if (no.getPai() == no.getPai().getPai().getDireita()) {
                tio = no.getPai().getPai().getEsquerda();

                if (tio.getCor() == Cor.VERMELHO) {
                    System.out.println("Caso 1: Recolorindo (tio vermelho)");
                    no.getPai().setCor(Cor.PRETO);
                    tio.setCor(Cor.PRETO);
                    no.getPai().getPai().setCor(Cor.VERMELHO);
                    no = no.getPai().getPai();
                } else {
                    if (no == no.getPai().getEsquerda()) {
                        System.out.println("Caso 2: Rotação à direita (interna)");
                        no = no.getPai();
                        rotacaoDireita(no);
                    }
                    System.out.println("Caso 3: Rotação à esquerda (externa)");
                    no.getPai().setCor(Cor.PRETO);
                    no.getPai().getPai().setCor(Cor.VERMELHO);
                    rotacaoEsquerda(no.getPai().getPai());
                }
            } else {
                tio = no.getPai().getPai().getDireita();

                if (tio.getCor() == Cor.VERMELHO) {
                    System.out.println("Caso 1: Recolorindo (tio vermelho)");
                    no.getPai().setCor(Cor.PRETO);
                    tio.setCor(Cor.PRETO);
                    no.getPai().getPai().setCor(Cor.VERMELHO);
                    no = no.getPai().getPai();
                } else {
                    if (no == no.getPai().getDireita()) {
                        System.out.println("Caso 2: Rotação à esquerda (interna)");
                        no = no.getPai();
                        rotacaoEsquerda(no);
                    }
                    System.out.println("Caso 3: Rotação à direita (externa)");
                    no.getPai().setCor(Cor.PRETO);
                    no.getPai().getPai().setCor(Cor.VERMELHO);
                    rotacaoDireita(no.getPai().getPai());
                }
            }

            if (no == raiz) {
                break;
            }
        }

        raiz.setCor(Cor.PRETO);
    }

    // Rotação à esquerda
    private void rotacaoEsquerda(NoRB<T> x) {
        System.out.println("Rotação à esquerda em " + x.getDado());
        NoRB<T> y = x.getDireita();
        x.setDireita(y.getEsquerda());

        if (y.getEsquerda() != TNULL) {
            y.getEsquerda().setPai(x);
        }

        y.setPai(x.getPai());

        if (x.getPai() == null) {
            this.raiz = y;
        } else if (x == x.getPai().getEsquerda()) {
            x.getPai().setEsquerda(y);
        } else {
            x.getPai().setDireita(y);
        }

        y.setEsquerda(x);
        x.setPai(y);
    }

    // Rotação à direita
    private void rotacaoDireita(NoRB<T> x) {
        System.out.println("Rotação à direita em " + x.getDado());
        NoRB<T> y = x.getEsquerda();
        x.setEsquerda(y.getDireita());

        if (y.getDireita() != TNULL) {
            y.getDireita().setPai(x);
        }

        y.setPai(x.getPai());

        if (x.getPai() == null) {
            this.raiz = y;
        } else if (x == x.getPai().getDireita()) {
            x.getPai().setDireita(y);
        } else {
            x.getPai().setEsquerda(y);
        }

        y.setDireita(x);
        x.setPai(y);
    }

    // Imprime em ordem Em-Ordem
    public void imprimirEmOrdem() {
        System.out.print("Em-Ordem: ");
        if (raiz == TNULL) {
            System.out.println("[Vazia]");
            return;
        }
        imprimirEmOrdemRec(raiz);
        System.out.println();
    }

    private void imprimirEmOrdemRec(NoRB<T> no) {
        if (no != TNULL) {
            imprimirEmOrdemRec(no.getEsquerda());
            System.out.print(no.getDado() + "(" + simboloCor(no.getCor()) + ") ");
            imprimirEmOrdemRec(no.getDireita());
        }
    }

    // Imprime em ordem Pré-Ordem
    public void imprimirPreOrdem() {
        System.out.print("Pré-Ordem: ");
        if (raiz == TNULL) {
            System.out.println("[Vazia]");
            return;
        }
        imprimirPreOrdemRec(raiz);
        System.out.println();
    }

    private void imprimirPreOrdemRec(NoRB<T> no) {
        if (no != TNULL) {
            System.out.print(no.getDado() + "(" + simboloCor(no.getCor()) + ") ");
            imprimirPreOrdemRec(no.getEsquerda());
            imprimirPreOrdemRec(no.getDireita());
        }
    }

    // Imprime em ordem Pós-Ordem
    public void imprimirPosOrdem() {
        System.out.print("Pós-Ordem: ");
        if (raiz == TNULL) {
            System.out.println("[Vazia]");
            return;
        }
        imprimirPosOrdemRec(raiz);
        System.out.println();
    }

    private void imprimirPosOrdemRec(NoRB<T> no) {
        if (no != TNULL) {
            imprimirPosOrdemRec(no.getEsquerda());
            imprimirPosOrdemRec(no.getDireita());
            System.out.print(no.getDado() + "(" + simboloCor(no.getCor()) + ") ");
        }
    }


    private String simboloCor(Cor cor) {
        return (cor == Cor.VERMELHO) ? "R" : "B";
    }

    public void remover(T dado) {
        if (dado == null) {
            throw new IllegalArgumentException("Valor nulo não pode ser removido.");
        }

        NoRB<T> z = buscarNo(raiz, dado);
        if (z == TNULL) {
            System.out.println("Valor não encontrado: " + dado);
            return;
        }

        NoRB<T> y = z;
        Cor corOriginal = y.getCor();
        NoRB<T> x;

        if (z.getEsquerda() == TNULL) {
            x = z.getDireita();
            transplantar(z, z.getDireita());
        } else if (z.getDireita() == TNULL) {
            x = z.getEsquerda();
            transplantar(z, z.getEsquerda());
        } else {
            y = minimo(z.getDireita());
            corOriginal = y.getCor();
            x = y.getDireita();
            if (y.getPai() == z) {
                x.setPai(y);
            } else {
                transplantar(y, y.getDireita());
                y.setDireita(z.getDireita());
                y.getDireita().setPai(y);
            }
            transplantar(z, y);
            y.setEsquerda(z.getEsquerda());
            y.getEsquerda().setPai(y);
            y.setCor(z.getCor());
        }

        if (corOriginal == Cor.PRETO) {
            removerFixup(x);
        }

        System.out.println("Removido: " + dado);
    }

    // Substitui um nó por outro na árvore
    private void transplantar(NoRB<T> u, NoRB<T> v) {
        if (u.getPai() == null) {
            raiz = v;
        } else if (u == u.getPai().getEsquerda()) {
            u.getPai().setEsquerda(v);
        } else {
            u.getPai().setDireita(v);
        }
        v.setPai(u.getPai());
    }

    // Encontra o nó com menor valor na subárvore
    private NoRB<T> minimo(NoRB<T> no) {
        while (no.getEsquerda() != TNULL) {
            no = no.getEsquerda();
        }
        return no;
    }

    // Busca o nó que contém o dado desejado
    private NoRB<T> buscarNo(NoRB<T> no, T dado) {
        if (no == TNULL || dado.equals(no.getDado())) {
            return no;
        }
        if (dado.compareTo(no.getDado()) < 0) {
            return buscarNo(no.getEsquerda(), dado);
        } else {
            return buscarNo(no.getDireita(), dado);
        }
    }

    private void removerFixup(NoRB<T> x) {
        NoRB<T> w;

        while (x != raiz && x.getCor() == Cor.PRETO) {
            if (x == x.getPai().getEsquerda()) {
                w = x.getPai().getDireita();
                if (w.getCor() == Cor.VERMELHO) {
                    w.setCor(Cor.PRETO);
                    x.getPai().setCor(Cor.VERMELHO);
                    rotacaoEsquerda(x.getPai());
                    w = x.getPai().getDireita();
                }

                if (w.getEsquerda().getCor() == Cor.PRETO && w.getDireita().getCor() == Cor.PRETO) {
                    w.setCor(Cor.VERMELHO);
                    x = x.getPai();
                } else {
                    if (w.getDireita().getCor() == Cor.PRETO) {
                        w.getEsquerda().setCor(Cor.PRETO);
                        w.setCor(Cor.VERMELHO);
                        rotacaoDireita(w);
                        w = x.getPai().getDireita();
                    }

                    w.setCor(x.getPai().getCor());
                    x.getPai().setCor(Cor.PRETO);
                    w.getDireita().setCor(Cor.PRETO);
                    rotacaoEsquerda(x.getPai());
                    x = raiz;
                }
            } else {
                w = x.getPai().getEsquerda();
                if (w.getCor() == Cor.VERMELHO) {
                    w.setCor(Cor.PRETO);
                    x.getPai().setCor(Cor.VERMELHO);
                    rotacaoDireita(x.getPai());
                    w = x.getPai().getEsquerda();
                }

                if (w.getDireita().getCor() == Cor.PRETO && w.getEsquerda().getCor() == Cor.PRETO) {
                    w.setCor(Cor.VERMELHO);
                    x = x.getPai();
                } else {
                    if (w.getEsquerda().getCor() == Cor.PRETO) {
                        w.getDireita().setCor(Cor.PRETO);
                        w.setCor(Cor.VERMELHO);
                        rotacaoEsquerda(w);
                        w = x.getPai().getEsquerda();
                    }

                    w.setCor(x.getPai().getCor());
                    x.getPai().setCor(Cor.PRETO);
                    w.getEsquerda().setCor(Cor.PRETO);
                    rotacaoDireita(x.getPai());
                    x = raiz;
                }
            }
        }

        x.setCor(Cor.PRETO);
    }


    // Métodos auxiliares

    public NoRB<T> getRaiz() {
        return raiz;
    }

    private String formatarNo(NoRB<T> no) {
        String cor = (no.getCor() == Cor.VERMELHO) ? "V" : "P";
        return  no.getDado() + "(" + cor + ")" ;
    }
    public void percorrerEmOrdem(AcaoTexto acao) {
        percorrerEmOrdemRec(raiz, acao);
    }

    private void percorrerEmOrdemRec(NoRB<T> no, AcaoTexto acao) {
        if (no != TNULL) {
            percorrerEmOrdemRec(no.getEsquerda(), acao);
            acao.executar(formatarNo(no));
            percorrerEmOrdemRec(no.getDireita(), acao);
        }
    }

    public void percorrerPreOrdem(AcaoTexto acao) {
        percorrerPreOrdemRec(raiz, acao);
    }

    private void percorrerPreOrdemRec(NoRB<T> no, AcaoTexto acao) {
        if (no != TNULL) {
            acao.executar(formatarNo(no));
            percorrerPreOrdemRec(no.getEsquerda(), acao);
            percorrerPreOrdemRec(no.getDireita(), acao);
        }
    }

    public void percorrerPosOrdem(AcaoTexto acao) {
        percorrerPosOrdemRec(raiz, acao);
    }

    private void percorrerPosOrdemRec(NoRB<T> no, AcaoTexto acao) {
        if (no != TNULL) {
            percorrerPosOrdemRec(no.getEsquerda(), acao);
            percorrerPosOrdemRec(no.getDireita(), acao);
            acao.executar(formatarNo(no));
        }
    }



}
