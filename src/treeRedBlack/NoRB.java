package treeRedBlack;

public class NoRB<T extends Comparable<T>> {
    private T dado;
    private Cor cor;
    private NoRB<T> esquerda;
    private NoRB<T> direita;
    private NoRB<T> pai;

    // Construtor para novos nós (sempre vermelhos)
    public NoRB(T dado) {
        if (dado == null) throw new IllegalArgumentException("Valor nulo não permitido");
        this.dado = dado;
        this.cor = Cor.VERMELHO;
    }

    // Construtor para nó sentinela (TNULL)
    public NoRB(Cor cor) {
        this.dado = null;
        this.cor = cor;
    }

    // Getters
    public T getDado() { return dado; }
    public Cor getCor() { return cor; }
    public NoRB<T> getEsquerda() { return esquerda; }
    public NoRB<T> getDireita() { return direita; }
    public NoRB<T> getPai() { return pai; }

    // Setters (pacote ou protegidos para controle)
    void setCor(Cor cor) { this.cor = cor; }
    void setEsquerda(NoRB<T> esquerda) { this.esquerda = esquerda; }
    void setDireita(NoRB<T> direita) { this.direita = direita; }
    void setPai(NoRB<T> pai) { this.pai = pai; }
}
