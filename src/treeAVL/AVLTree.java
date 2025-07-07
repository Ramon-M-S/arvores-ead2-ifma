package treeAVL;

import utils.AcaoSobreNo;

public class AVLTree<T extends Comparable<T>> {

    private NoAvl<T> raiz;

    public AVLTree() {
        this.raiz = null;
    }

    public enum TipoPercurso {
        EM_ORDEM, PRE_ORDEM, POS_ORDEM
    }

    public void inserir(T dado) {
        if (dado == null) {
            throw new IllegalArgumentException("Não é permitido inserir valores nulos na árvore.");
        }
        this.raiz = inserirRecursivo(this.raiz, dado);
    }

    public void remover(T dado) {
        this.raiz = removerRecursivo(this.raiz, dado);
    }

    public static <T extends Comparable<T>> int altura(NoAvl<T> no) {
        return (no == null) ? 0 : no.getAltura();
    }

    private int max(int a, int b) {
        return Math.max(a, b);
    }

    private int fatorBalanceamento(NoAvl<T> no) {
        return (no == null) ? 0 : altura(no.getEsquerda()) - altura(no.getDireita());
    }

    private NoAvl<T> rotacaoDireita(NoAvl<T> y) {
        NoAvl<T> x = y.getEsquerda();
        NoAvl<T> T2 = x.getDireita();

        x.setDireita(y);
        y.setEsquerda(T2);

        y.atualizarAltura();
        x.atualizarAltura();

        return x;
    }

    private NoAvl<T> rotacaoEsquerda(NoAvl<T> x) {
        NoAvl<T> y = x.getDireita();
        NoAvl<T> T2 = y.getEsquerda();

        y.setEsquerda(x);
        x.setDireita(T2);

        x.atualizarAltura();
        y.atualizarAltura();

        return y;
    }

    private NoAvl<T> inserirRecursivo(NoAvl<T> no, T dado) {
        if (no == null) return new NoAvl<>(dado);

        int cmp = dado.compareTo(no.getDado());
        if (cmp < 0)
            no.setEsquerda(inserirRecursivo(no.getEsquerda(), dado));
        else if (cmp > 0)
            no.setDireita(inserirRecursivo(no.getDireita(), dado));
        else
            return no;

        no.atualizarAltura();
        int balance = fatorBalanceamento(no);

        if (balance > 1 && dado.compareTo(no.getEsquerda().getDado()) < 0)
            return rotacaoDireita(no);

        if (balance < -1 && dado.compareTo(no.getDireita().getDado()) > 0)
            return rotacaoEsquerda(no);

        if (balance > 1 && dado.compareTo(no.getEsquerda().getDado()) > 0) {
            no.setEsquerda(rotacaoEsquerda(no.getEsquerda()));
            return rotacaoDireita(no);
        }

        if (balance < -1 && dado.compareTo(no.getDireita().getDado()) < 0) {
            no.setDireita(rotacaoDireita(no.getDireita()));
            return rotacaoEsquerda(no);
        }

        return no;
    }

    private NoAvl<T> removerRecursivo(NoAvl<T> no, T dado) {
        if (no == null) return null;

        if (dado.compareTo(no.dado) < 0)
            no.esquerda = removerRecursivo(no.esquerda, dado);
        else if (dado.compareTo(no.dado) > 0)
            no.direita = removerRecursivo(no.direita, dado);
        else {
            if (no.esquerda == null || no.direita == null) {
                NoAvl<T> temp = (no.esquerda != null) ? no.esquerda : no.direita;
                if (temp == null)
                    no = null;
                else
                    no = temp;
            } else {
                NoAvl<T> temp = encontrarMenorValor(no.direita);
                no.dado = temp.dado;
                no.direita = removerRecursivo(no.direita, temp.dado);
            }
        }

        if (no == null) return null;

        no.altura = 1 + max(altura(no.esquerda), altura(no.direita));
        int balance = fatorBalanceamento(no);

        if (balance > 1 && fatorBalanceamento(no.esquerda) >= 0)
            return rotacaoDireita(no);

        if (balance > 1 && fatorBalanceamento(no.esquerda) < 0) {
            no.esquerda = rotacaoEsquerda(no.esquerda);
            return rotacaoDireita(no);
        }

        if (balance < -1 && fatorBalanceamento(no.direita) <= 0)
            return rotacaoEsquerda(no);

        if (balance < -1 && fatorBalanceamento(no.direita) > 0) {
            no.direita = rotacaoDireita(no.direita);
            return rotacaoEsquerda(no);
        }

        return no;
    }

    private NoAvl<T> encontrarMenorValor(NoAvl<T> no) {
        NoAvl<T> atual = no;
        while (atual.esquerda != null) {
            atual = atual.esquerda;
        }
        return atual;
    }

    public void percorrerEmOrdem(AcaoSobreNo<T> acao) {
        if (acao == null) throw new IllegalArgumentException("A ação não pode ser nula.");
        percorrerEmOrdemRecursivo(this.raiz, acao);
    }

    private void percorrerEmOrdemRecursivo(NoAvl<T> no, AcaoSobreNo<T> acao) {
        if (no != null) {
            percorrerEmOrdemRecursivo(no.getEsquerda(), acao);
            acao.executar(no.getDado());
            percorrerEmOrdemRecursivo(no.getDireita(), acao);
        }
    }

    public void percorrerPreOrdem(AcaoSobreNo<T> acao) {
        if (acao == null) throw new IllegalArgumentException("A ação não pode ser nula.");
        percorrerPreOrdemRecursivo(this.raiz, acao);
    }

    private void percorrerPreOrdemRecursivo(NoAvl<T> no, AcaoSobreNo<T> acao) {
        if (no != null) {
            acao.executar(no.getDado());
            percorrerPreOrdemRecursivo(no.getEsquerda(), acao);
            percorrerPreOrdemRecursivo(no.getDireita(), acao);
        }
    }

    public void percorrerPosOrdem(AcaoSobreNo<T> acao) {
        if (acao == null) throw new IllegalArgumentException("A ação não pode ser nula.");
        percorrerPosOrdemRecursivo(this.raiz, acao);
    }

    private void percorrerPosOrdemRecursivo(NoAvl<T> no, AcaoSobreNo<T> acao) {
        if (no != null) {
            percorrerPosOrdemRecursivo(no.getEsquerda(), acao);
            percorrerPosOrdemRecursivo(no.getDireita(), acao);
            acao.executar(no.getDado());
        }
    }

    public void imprimirEmOrdem() {
        System.out.print("Em-Ordem: ");
        if (this.raiz == null) {
            System.out.println("[Árvore Vazia]");
            return;
        }
        percorrerEmOrdem(dado -> System.out.print(dado + " "));
        System.out.println();
    }

    public void imprimirPreOrdem() {
        System.out.print("Pré-Ordem: ");
        if (this.raiz == null) {
            System.out.println("[Árvore Vazia]");
            return;
        }
        percorrerPreOrdem(dado -> System.out.print(dado + " "));
        System.out.println();
    }

    public void imprimirPosOrdem() {
        System.out.print("Pós-Ordem: ");
        if (this.raiz == null) {
            System.out.println("[Árvore Vazia]");
            return;
        }
        percorrerPosOrdem(dado -> System.out.print(dado + " "));
        System.out.println();
    }
}
