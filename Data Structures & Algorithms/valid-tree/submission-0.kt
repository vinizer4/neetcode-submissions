/*
Graph Valid Tree

PROBLEMA

Temos:

- totalNodes -> quantidade de nos do grafo
- edges -> arestas nao direcionadas

Precisamos verificar se esse grafo forma um arvore valida.

O QUE E UMA ARVORE EM GRAFOS?

Um grafo e uma arvore se:

1. nao possui ciclos
2. e totalmente conectado

Ou seja:

- deve existir caminho entre todos os nos
- nao pode existir mais de um caminho entre dois nos,
  pois isso criaria ciclo


IDEIA PRINCIPAL

Vamos usar DFS para percorrer o grafo e verficiar duas coisas:

1. se existe ciclo
2. se todos os nos foram visitados

Se as duas condicoes foram verdadeiras,
entao o grafo e uma arvore valida.


OBSERVACAO IMPORTANTE

Uma arvore com n nos deve ter exatamente n - 1 arestas.

Entao, se o numero de arestas for maior que n - 1,
ja podemos retornar false imediatamente.

Por que?

Porque arestas demais garantem a existencia de ciclo.


INTUICAO DO DFS

Como o grafo e nao direcionado,
quando estamos em um no e voltamos para o pai,
isso NAO e ciclo.

Exemplo:

0 -- 1

Se estou em 1 e vejo 0 de novo,
isso e normal, porque 0 foi quem me trouxe ate 1.

Por isso a DFS recebe tambem o parentNode.


COMO DETECTAR CICLO?

Se durante a DFS chegarmos a um no que ja foi visitado,
e esse no NAO for o pai da chamada atual,
entao existe ciclo.


ESTRATEGIA

Step 1
Se edges.size > totalNodes - 1, retornar false.

Step 2
Construir adjacency list.

Step 3
Rodar DFS a partir do no 0.

Step 4
Se DFS encontrar ciclo, retornar false.

Step 5
No final, verificar se todos os nos foram visitados.

Se nao foram, o grafo esta desconectado.

Step 6
Retornar true apenas se:
- nao ha ciclo
- todos os nos foram visitados


COMPLEXIDADE

Tempo: O(V + E)

Onde:
- V = quantidade de nos
- E = quantidade de arestas

Explicacao:

- construir adjacency list custa O(E)
- DFS percorre cada no e cada aresta no maximo uma vez


ESPACO: O(V + E)

Explicacao:

- adjacency list armazena as arestas
- visitedSet armazena ate V nos
- a recursao do DFS pode crescer ate O(V)

*/

class Solution {
    fun validTree(
        totalNodes: Int, 
        edges: Array<IntArray>
    ): Boolean {
        
        /*
        STEP 1
        Se existem mais arestas do que o maximo permitido
        para uma arvore com totalNodes nos, ja e invalido.

        Uma arvore com n nos tem exatamente n - 1 arestas.

        Se temos mais do que isso, obrigatoriamente existe ciclo.

        O(1)
        */
        if (edges.size > totalNodes - 1) return false

        /*
        STEP 2
        Construir adjacency list para representar o grafo.

        Como o grafo e nao direcionado,
        adicionamos cada aresta no dois sentidos

        Exemplo:
        [0, 1]

        adjacencyList[0].add(1)
        adjacencyList[1].add(0)

        Criacao do array -> O(V)
        Preenchimento com arestas -> O(E)
        */
        val adjacencyList = Array(totalNodes) {
            mutableListOf<Int>()
        }

        for ((nodeA, nodeB) in edges) {
            adjacencyList[nodeA].add(nodeB)
            adjacencyList[nodeB].add(nodeA)
        }

        /*
        STEP 3
        Conjunto de nos visitados durante a DFS.

        Ele serve para:
        - evitar trabalho repetido
        - detectar ciclos

        O(1)
        */
        val visitedNodes = HashSet<Int>()

        /*
        STEP 4
        DFS para verificar ciclo.

        currentNode -> no atual
        parentNode -> no de onde viemos

        Retorna:
        - true -> subarvore atual e valida ate aqui
        - false -> encontrou ciclo
        */
        fun detectCycleDFS(
            currentNode: Int,
            parentNode: Int
        ): Boolean {

            /*
            STEP 5
            Se o no atual ja tinha sido visitado,
            entao encontramos ele por outro caminho.

            Como a DFS so chega aqui depois de ignorar o pai,
            isso significa ciclo.

            O(1)
            */
            if (currentNode in visitedNodes) return false

            /*
            STEP 6
            Marcar o no atual como visitado.

            O(1)
            */
            visitedNodes.add(currentNode)

            /*
            STEP 7
            Explorar os vizinhos do no atual.

            Para cada vizinho:
            - se for pai, ignoramos
            - se nao for, continuamos DFS

            Iteracao total sobre arestas -> O(E)
            */
            for (neighborNode in adjacencyList[currentNode]) {

                /*
                STEP 8
                Ignorar o pai.

                Em grafo nao direcionado, voltar para o pai
                e o comportamento normal da aresta reversa,
                nao e ciclo

                O(1)
                */
                if (neighborNode == parentNode) continue

                /*
                STEP 9
                Se qualquer DFS dos vizinho retornar false,
                entao ja encontramos um ciclo em algum ponto
                da componente.

                O(1) para retorno;
                custo real esta dentro da chamada recursiva
                */
                if (!detectCycleDFS(neighborNode, currentNode)) return false
            }

            /*
            STEP 10
            Se chegamos ate aqui,
            nao encontramos ciclo na componente atual.

            O(1)
            */
            return true
        }

        /*
        STEP 11
        Iniciar DFS a partir do no 0.

        Por que no 0?

        Porque, se o grafo for realmente uma arvore,
        todos os nos devem ser alcansaveis a partir de qualquer no.

        O(V + E) no total da DFS
        */
        val hasNoCycle = detectCycleDFS(0, -1)

        /*
        STEP 12
        O grafo sera arvore valida apenas se:

        1. nao houver ciclo
        2. todos os nos tiverem sido visitados

        Se visitamos menos que totalNodes,
        o grafo esta desconectado

        O(1)
        */
        return hasNoCycle && visitedNodes.size == totalNodes
    }
}
