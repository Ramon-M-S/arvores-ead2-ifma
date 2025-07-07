package treeAVL;

public class NoAvl<T extends Comparable<T>> {
    T dado;
    NoAvl<T> esquerda;
    NoAvl<T> direita;
    int altura;

    public NoAvl(T dado) {
        this.dado = dado;
        this.altura = 1;
    }

    public T getDado() { return dado; }
    public NoAvl<T> getEsquerda() { return esquerda; }
    public NoAvl<T> getDireita() { return direita; }
    public int getAltura() { return altura; }

    public void setEsquerda(NoAvl<T> esquerda) { this.esquerda = esquerda; }
    public void setDireita(NoAvl<T> direita) { this.direita = direita; }

    public void atualizarAltura() {
        this.altura = 1 + Math.max(AVLTree.altura(this.esquerda), AVLTree.altura(this.direita));
    }
}
