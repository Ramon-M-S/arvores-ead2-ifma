package treeAVL;

public class AVLTree<T extends Comparable<T>> {

    // Classe Nó
    static class NoAvl<T extends Comparable<T>> {
        private T dado;
        private NoAvl<T> esquerda;
        private NoAvl<T> direita;
        private int altura;

        public NoAvl(T dado) {
            this.dado = dado;
            this.altura = 1; // altura de uma folha é 1
        }

        public T getDado() { return dado; }
        public NoAvl<T> getEsquerda() { return esquerda; }
        public NoAvl<T> getDireita() { return direita; }
        public int getAltura() { return altura; }


        private void setEsquerda(NoAvl<T> esquerda) { this.esquerda = esquerda; }
        private void setDireita(NoAvl<T> direita) { this.direita = direita; }

        /**
         *  nó atualizar sua altura.
         */
        private void atualizarAltura() {
            this.altura = 1 + Math.max(AVLTree.altura(this.esquerda), AVLTree.altura(this.direita));
        }
    }

    // Classe AVLTree
    private NoAvl<T> raiz;

    public AVLTree() {
        this.raiz = null;
    }


    public void inserir(T dado) {
        if (dado == null) {
            throw new IllegalArgumentException("Não é permitido inserir valores nulos na árvore.");
        }
        this.raiz = inserirRecursivo(this.raiz, dado);
    }

    // Métodos auxiliares

    private static <T extends Comparable<T>> int altura(NoAvl<T> no) {
        return (no == null) ? 0 : no.getAltura();
    }

    private int max(int a, int b) {
        return Math.max(a, b);
    }

    private int fatorBalanceamento(NoAvl<T> no) {
        return (no == null) ? 0 : altura(no.getEsquerda()) - altura(no.getDireita());
    }


    /**
     * Realiza uma rotação simples à direita em um nó desequilibrado.
     *
     * Essa rotação é usada quando o filho esquerdo do nó (y) está mais pesado,
     * ou seja, ocorreu uma inserção na subárvore esquerda do filho esquerdo.
     *
     * Exemplo visual da rotação:
     *         y                          x
     *        /                          \
     *       x           =>              y
     *        \                        /
     *        T2                     T2
     *
     * @param y Nó desbalanceado
     * @return Novo nó raiz após a rotação (x)
     */
    private NoAvl<T> rotacaoDireita(NoAvl<T> y) {
        NoAvl<T> x = y.getEsquerda();     // x se torna a nova raiz após a rotação
        NoAvl<T> T2 = x.getDireita();     // Subárvore que será realocada

        x.setDireita(y);                  // y se torna filho direito de x
        y.setEsquerda(T2);                // T2 se torna filho esquerdo de y

        y.atualizarAltura();              // Atualiza altura de y
        x.atualizarAltura();              // Atualiza altura de x

        return x;                         // Retorna nova raiz após rotação
    }


    /**
     * Realiza uma rotação simples à esquerda em um nó desequilibrado.
     *
     * Essa rotação é usada quando o filho direito do nó (x) está mais pesado,
     * ou seja, ocorreu uma inserção na subárvore direita do filho direito.
     *
     * Exemplo visual da rotação:
     *      x                             y
     *       \                           /
     *        y         =>             x
     *       /                           \
     *     T2                            T2
     *
     * @param x Nó desbalanceado
     * @return Novo nó raiz após a rotação (y)
     */
    private NoAvl<T> rotacaoEsquerda(NoAvl<T> x) {
        NoAvl<T> y = x.getDireita();     // y se torna a nova raiz após a rotação
        NoAvl<T> T2 = y.getEsquerda();   // Subárvore que será realocada

        y.setEsquerda(x);                // x se torna filho esquerdo de y
        x.setDireita(T2);                // T2 se torna filho direito de x

        x.atualizarAltura();             // Atualiza altura de x
        y.atualizarAltura();             // Atualiza altura de y

        return y;                        // Retorna nova raiz após rotação
    }



    private NoAvl<T> inserirRecursivo(NoAvl<T> no, T dado) {
        if (no == null) {
            return new NoAvl<>(dado);
        }

        int comparacao = dado.compareTo(no.getDado());
        if (comparacao < 0) {
            no.setEsquerda(inserirRecursivo(no.getEsquerda(), dado));
        } else if (comparacao > 0) {
            no.setDireita(inserirRecursivo(no.getDireita(), dado));
        } else {
            return no; // Duplicado
        }

        no.atualizarAltura();

        int balance = fatorBalanceamento(no);

        // Casos de rebalanceamento
        if (balance > 1 && dado.compareTo(no.getEsquerda().getDado()) < 0) { // Esquerda-Esquerda
            return rotacaoDireita(no);
        }
        if (balance < -1 && dado.compareTo(no.getDireita().getDado()) > 0) { // Direita-Direita
            return rotacaoEsquerda(no);
        }
        if (balance > 1 && dado.compareTo(no.getEsquerda().getDado()) > 0) { // Esquerda-Direita
            no.setEsquerda(rotacaoEsquerda(no.getEsquerda()));
            return rotacaoDireita(no);
        }
        if (balance < -1 && dado.compareTo(no.getDireita().getDado()) < 0) { // Direita-Esquerda
            no.setDireita(rotacaoDireita(no.getDireita()));
            return rotacaoEsquerda(no);
        }

        return no;
    }
}