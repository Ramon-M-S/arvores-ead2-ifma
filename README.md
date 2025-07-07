# 🌳 Projeto de Árvores Balanceadas - Algoritmos e Estruturas de Dados II (IFMA - Monte Castelo)

## 🎯 Sobre o Projeto

Este projeto foi desenvolvido como parte da **3ª nota da disciplina Algoritmos e Estruturas de Dados II**, do curso de **Sistemas de Informação – IFMA Campus Monte Castelo**, utilizando a linguagem **Java**.

O principal objetivo foi implementar manualmente duas árvores balanceadas: **AVL** e **Rubro-Negra**, sem o uso de bibliotecas prontas da linguagem. Além disso, a aplicação realiza operações a partir de um arquivo de entrada, simulando inserções, remoções e percursos.

---

## ✅ Funcionalidades Implementadas

As duas árvores implementadas oferecem suporte às seguintes operações:

| Operação       | Descrição |
|----------------|-----------|
| Inserção       | Insere um elemento, aplicando as regras de balanceamento da árvore correspondente. |
| Remoção        | Remove um elemento, ajustando a estrutura e mantendo as propriedades da árvore. |
| P1 - Em Ordem  | Imprime os elementos seguindo o percurso em ordem. |
| P2 - Pós-Ordem | Imprime os elementos seguindo o percurso pós-ordem. |
| P3 - Pré-Ordem | Imprime os elementos seguindo o percurso pré-ordem. |

Cada impressão exibe também a **cor de cada nó**, no formato:  
Valor(50); Cor(PRETO)  
Valor(30); Cor(VERMELHO)  
...

---

## 📁 Leitura via Arquivo

As operações são lidas de um arquivo chamado `arvores.txt`, onde cada linha segue o formato:  
I-50 # Inserir o valor 50  
R-30 # Remover o valor 30  
P1 # Imprimir em ordem  
P2 # Imprimir pós-ordem  
P3 # Imprimir pré-ordem  

A aplicação executa os comandos **sequencialmente**, processando-os em **ambas as árvores** (AVL e Rubro-Negra).

---

## 📝 Manuais de Correções

Foram desenvolvidos **dois manuais em PDF**, um para cada árvore:

- `manual_AVL.pdf`
- `manual_RubroNegra.pdf`

Cada manual contém um **catálogo ilustrado de todas as possíveis correções** que podem ocorrer durante as operações de inserção e remoção, com imagens **produzidas manualmente pelo discente**.

---

## 💻 Estrutura do Projeto
src/
│
├── arvores.txt            ← entrada de comandos  
├── configArvores.txt      ← este guia de uso  
│  
├── utils/  
│   ├── AcaoSobreNo.java   ← interface para AVL  
│   ├── LeitorArquivo.java ← (opcional para leitura auxiliar)  
│  
├── treeAVL/  
│   ├── AVLTree.java       ← implementação AVL  
│   ├── NoAvl.java         ← nó AVL  
│   ├── RunAVL.java        ← classe main  
│   ├── saidaAVL.txt       ← saída das operações  
│  
├── treeRedBlack/  
│   ├── RedBlackTree.java  ← implementação Red-Black  
│   ├── NoRB.java          ← nó Red-Black  
│   ├── Cor.java           ← enum de cor  
│   ├── AcaoTexto.java     ← interface para percursos  
│   ├── RunRedBlack.java   ← classe main  
│   ├── saidaRB.txt        ← saída das operações  
└── README.md    
