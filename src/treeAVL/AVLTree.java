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



    /**
     * Insere um novo valor recursivamente na árvore AVL.
     * Após a inserção, verifica e corrige possíveis desbalanceamentos aplicando rotações.
     *
     * @param no   Nó atual durante a recursão
     * @param dado Valor a ser inserido
     * @return Nó atualizado após inserção e possíveis rotações
     */
    private NoAvl<T> inserirRecursivo(NoAvl<T> no, T dado) {
        // Caso base: posição correta encontrada (nó nulo), cria novo nó com o dado
        if (no == null) {
            return new NoAvl<>(dado);
        }

        // Decide em qual lado inserir com base na comparação
        int comparacao = dado.compareTo(no.getDado());
        if (comparacao < 0) {
            // Inserir na subárvore esquerda
            no.setEsquerda(inserirRecursivo(no.getEsquerda(), dado));
        } else if (comparacao > 0) {
            // Inserir na subárvore direita
            no.setDireita(inserirRecursivo(no.getDireita(), dado));
        } else {
            // Valor duplicado (não é inserido na árvore)
            return no;
        }

        // Atualiza a altura do nó atual
        no.atualizarAltura();

        // Calcula o fator de balanceamento
        int balance = fatorBalanceamento(no);

        // Verifica os 4 casos de desbalanceamento e aplica a rotação correta:

        // Caso Esquerda-Esquerda (LL)
        if (balance > 1 && dado.compareTo(no.getEsquerda().getDado()) < 0) {
            return rotacaoDireita(no);
        }

        // Caso Direita-Direita (RR)
        if (balance < -1 && dado.compareTo(no.getDireita().getDado()) > 0) {
            return rotacaoEsquerda(no);
        }

        // Caso Esquerda-Direita (LR)
        if (balance > 1 && dado.compareTo(no.getEsquerda().getDado()) > 0) {
            no.setEsquerda(rotacaoEsquerda(no.getEsquerda()));
            return rotacaoDireita(no);
        }

        // Caso Direita-Esquerda (RL)
        if (balance < -1 && dado.compareTo(no.getDireita().getDado()) < 0) {
            no.setDireita(rotacaoDireita(no.getDireita()));
            return rotacaoEsquerda(no);
        }

        // Retorna o nó (atualizado e possivelmente rebalanceado)
        return no;
    }


    /**
     * Inicia o processo de remoção de um elemento na árvore AVL.
     * Chama o método recursivo e atualiza a raiz com o novo nó retornado.
     *
     * @param dado Valor a ser removido da árvore
     */
    public void remover(T dado) {
        this.raiz = removerRecursivo(this.raiz, dado);
    }

    /**
     * Encontra o nó com o menor valor em uma subárvore (nó mais à esquerda).
     * Usado para encontrar o sucessor in-order durante a remoção.
     *
     * @param no Raiz da subárvore
     * @return Nó com o menor valor (mais à esquerda)
     */
    private NoAvl<T> encontrarMenorValor(NoAvl<T> no) {
        NoAvl<T> atual = no;
        while (atual.esquerda != null) {
            atual = atual.esquerda;
        }
        return atual;
    }

    /**
     * Método recursivo responsável por remover um valor da árvore AVL.
     * Além de remover, ele garante que a árvore continue balanceada após a operação.
     *
     * @param no   Nó atual da recursão
     * @param dado Valor a ser removido
     * @return Nó atualizado após remoção e balanceamento
     */
    private NoAvl<T> removerRecursivo(NoAvl<T> no, T dado) {
        // Caso base: valor não encontrado na árvore
        if (no == null) {
            return null;
        }

        // Navega pela árvore para localizar o nó a ser removido
        if (dado.compareTo(no.dado) < 0) {
            no.esquerda = removerRecursivo(no.esquerda, dado);
        } else if (dado.compareTo(no.dado) > 0) {
            no.direita = removerRecursivo(no.direita, dado);
        } else {
            // Nó encontrado — agora vamos removê-lo

            // CASO 1: Nó com zero ou um filho
            if (no.esquerda == null || no.direita == null) {
                NoAvl<T> temp = (no.esquerda != null) ? no.esquerda : no.direita;

                if (temp == null) {
                    // Nó folha: apenas remove
                    no = null;
                } else {
                    // Um filho: substitui o nó atual pelo filho
                    no = temp;
                }
            } else {
                // CASO 2: Nó com dois filhos
                // Encontra o sucessor in-order (menor da subárvore direita)
                NoAvl<T> temp = encontrarMenorValor(no.direita);

                // Substitui o valor atual pelo valor do sucessor
                no.dado = temp.dado;

                // Remove o sucessor agora duplicado
                no.direita = removerRecursivo(no.direita, temp.dado);
            }
        }

        // Se a árvore ficou vazia após a remoção
        if (no == null) {
            return null;
        }

        // Atualiza a altura do nó atual
        no.altura = 1 + max(altura(no.esquerda), altura(no.direita));

        // Calcula o fator de balanceamento
        int balance = fatorBalanceamento(no);

        // Verifica os quatro casos possíveis de desbalanceamento e corrige:

        // Caso Esquerda-Esquerda (LL)
        if (balance > 1 && fatorBalanceamento(no.esquerda) >= 0) {
            return rotacaoDireita(no);
        }

        // Caso Esquerda-Direita (LR)
        if (balance > 1 && fatorBalanceamento(no.esquerda) < 0) {
            no.esquerda = rotacaoEsquerda(no.esquerda);
            return rotacaoDireita(no);
        }

        // Caso Direita-Direita (RR)
        if (balance < -1 && fatorBalanceamento(no.direita) <= 0) {
            return rotacaoEsquerda(no);
        }

        // Caso Direita-Esquerda (RL)
        if (balance < -1 && fatorBalanceamento(no.direita) > 0) {
            no.direita = rotacaoDireita(no.direita);
            return rotacaoEsquerda(no);
        }

        // Retorna o nó (possivelmente reestruturado)
        return no;
    }

}