/*
Find Critical and Pseudo-Critical Edges in Minimum Spanning Tree

PROBLEMA

Temos um grafo não direcionado e ponderado.

Cada aresta é:

[u, v, weight]

Queremos descobrir quais arestas são:

1. Critical edges
2. Pseudo-critical edges

------------------------------------------------

O QUE É UMA MST?

MST = Minimum Spanning Tree

É uma árvore que:

- conecta todos os nós
- não tem ciclos
- tem o menor custo total possível

------------------------------------------------

O QUE É UMA ARESTA CRITICAL?

Uma aresta é critical se:

- ao removê-la,
  a MST fica mais cara
  OU
  o grafo nem consegue mais formar uma MST válida

Em outras palavras:

essa aresta é obrigatória em toda MST ótima.

------------------------------------------------

O QUE É UMA ARESTA PSEUDO-CRITICAL?

Uma aresta é pseudo-critical se:

- ela NÃO é obrigatória
- mas ela pode aparecer em pelo menos uma MST ótima

Ou seja:

existe alguma MST mínima que usa essa aresta.

------------------------------------------------

IDEIA PRINCIPAL

Primeiro calculamos o peso de uma MST normal do grafo.

Depois, para cada aresta, fazemos dois testes:

1. TESTE DE EXCLUSÃO
   Construímos a MST sem usar essa aresta.
   Se o custo aumentar ou o grafo desconectar,
   então essa aresta é critical.

2. TESTE DE INCLUSÃO FORÇADA
   Forçamos essa aresta a entrar primeiro na MST.
   Depois continuamos o Kruskal normalmente.
   Se o custo final continuar igual ao da MST ótima,
   então essa aresta é pseudo-critical.

------------------------------------------------

POR QUE ISSO FUNCIONA?

Teste de exclusão:
- se tirar a aresta piora o melhor resultado,
  então ela era indispensável

Teste de inclusão:
- se eu consigo construir uma MST ótima incluindo essa aresta,
  então ela pode participar de uma MST mínima

------------------------------------------------

POR QUE USAR KRUSKAL?

Kruskal é ótimo para MST porque:

- ordena arestas por peso
- vai adicionando as mais baratas
- usa Union-Find para evitar ciclos

------------------------------------------------

POR QUE USAR UNION-FIND?

Union-Find ajuda a responder rapidamente:

"esses dois nós já estão no mesmo componente?"

Se sim:
- adicionar a aresta criaria ciclo

Se não:
- podemos unir os componentes

------------------------------------------------

COMPLEXIDADE

Tempo: O(E²)

Explicação:

- para cada aresta, fazemos até 2 construções de MST
- cada construção percorre as arestas
- como E <= 200, essa abordagem é aceitável

------------------------------------------------

Espaço: O(V + E)

Explicação:

- armazenamos as arestas com índice original
- Union-Find usa arrays de tamanho V
*/
class UnionFind(totalNodes: Int) {

    /*
    STEP 1
    parentByNode[node] guarda o representante atual do conjunto.

    Inicialmente, cada nó é pai de si mesmo.

    Criação -> O(V)
    */
    private val parentByNode = IntArray(totalNodes) { nodeIndex -> nodeIndex }

    /*
    STEP 2
    componentSizeByRoot[root] guarda o tamanho do componente
    cujo representante é root.

    Criação -> O(V)
    */
    private val componentSizeByRoot = IntArray(totalNodes) { 1 }

    /*
    STEP 3
    Encontrar o representante do nó.

    Usamos path compression para acelerar buscas futuras.

    Tempo amortizado -> quase O(1)
    */
    fun find(node: Int): Int {
        if (parentByNode[node] != node) {
            parentByNode[node] = find(parentByNode[node])
        }
        return parentByNode[node]
    }

    /*
    STEP 4
    Tentar unir dois componentes.

    Retorna:
    - true  -> se a união aconteceu
    - false -> se já estavam no mesmo componente

    Tempo amortizado -> quase O(1)
    */
    fun union(nodeA: Int, nodeB: Int): Boolean {
        val rootA = find(nodeA)
        val rootB = find(nodeB)

        if (rootA == rootB) {
            return false
        }

        /*
        Union by size:
        anexamos o menor componente ao maior
        */
        if (componentSizeByRoot[rootA] > componentSizeByRoot[rootB]) {
            parentByNode[rootB] = rootA
            componentSizeByRoot[rootA] += componentSizeByRoot[rootB]
        } else {
            parentByNode[rootA] = rootB
            componentSizeByRoot[rootB] += componentSizeByRoot[rootA]
        }

        return true
    }

    /*
    STEP 5
    Retorna o tamanho do maior componente.

    Isso ajuda a verificar se todos os nós ficaram conectados.

    maxOrNull() -> O(V)
    */
    fun largestComponentSize(): Int {
        return componentSizeByRoot.maxOrNull() ?: 0
    }
}

class Solution {
    fun findCriticalAndPseudoCriticalEdges(
        totalNodes: Int,
        edges: Array<IntArray>
    ): List<List<Int>> {

        /*
        STEP 6
        Adicionar o índice original em cada aresta.

        Nova estrutura:
        [u, v, weight, originalIndex]

        Também ordenamos por peso para usar Kruskal.

        mapIndexed + sort -> O(E log E)
        */
        val sortedEdgesWithOriginalIndex = edges
            .mapIndexed { originalIndex, edge ->
                intArrayOf(edge[0], edge[1], edge[2], originalIndex)
            }
            .sortedBy { edgeWithIndex -> edgeWithIndex[2] }

        /*
        STEP 7
        Calcular o peso da MST original do grafo.

        Essa será a referência para decidir:
        - critical
        - pseudo-critical

        Kruskal -> O(E)
        */
        var originalMstWeight = 0
        val baseUnionFind = UnionFind(totalNodes)

        for (edge in sortedEdgesWithOriginalIndex) {
            val nodeA = edge[0]
            val nodeB = edge[1]
            val edgeWeight = edge[2]

            if (baseUnionFind.union(nodeA, nodeB)) {
                originalMstWeight += edgeWeight
            }
        }

        /*
        STEP 8
        Listas de resposta.

        O(1)
        */
        val criticalEdgeIndices = mutableListOf<Int>()
        val pseudoCriticalEdgeIndices = mutableListOf<Int>()

        /*
        STEP 9
        Testar cada aresta individualmente.

        Loop -> O(E)
        */
        for (candidateEdge in sortedEdgesWithOriginalIndex) {

            val candidateNodeA = candidateEdge[0]
            val candidateNodeB = candidateEdge[1]
            val candidateWeight = candidateEdge[2]
            val candidateOriginalIndex = candidateEdge[3]

            /*
            ==================================================
            TESTE 1: EXCLUIR A ARESTA
            ==================================================

            Se remover essa aresta faz:
            - o custo aumentar
            OU
            - o grafo não conectar todos os nós

            então ela é critical.
            */
            val unionFindWithoutCandidate = UnionFind(totalNodes)
            var mstWeightWithoutCandidate = 0

            for (otherEdge in sortedEdgesWithOriginalIndex) {
                val otherOriginalIndex = otherEdge[3]

                /*
                Ignoramos exatamente a aresta candidata.
                */
                if (otherOriginalIndex == candidateOriginalIndex) {
                    continue
                }

                val otherNodeA = otherEdge[0]
                val otherNodeB = otherEdge[1]
                val otherWeight = otherEdge[2]

                if (unionFindWithoutCandidate.union(otherNodeA, otherNodeB)) {
                    mstWeightWithoutCandidate += otherWeight
                }
            }

            /*
            Se o maior componente não tem tamanho totalNodes,
            então o grafo ficou desconectado.

            Se o peso ficou maior,
            então essa aresta era indispensável.

            largestComponentSize() -> O(V)
            */
            if (
                unionFindWithoutCandidate.largestComponentSize() != totalNodes ||
                mstWeightWithoutCandidate > originalMstWeight
            ) {
                criticalEdgeIndices.add(candidateOriginalIndex)
                continue
            }

            /*
            ==================================================
            TESTE 2: FORÇAR A INCLUSÃO DA ARESTA
            ==================================================

            Queremos saber se essa aresta pode fazer parte
            de alguma MST ótima.
            */
            val unionFindWithCandidate = UnionFind(totalNodes)

            /*
            Forçamos a aresta candidata a entrar primeiro.
            */
            unionFindWithCandidate.union(candidateNodeA, candidateNodeB)
            var mstWeightWithCandidate = candidateWeight

            /*
            Depois continuamos o Kruskal normalmente.
            */
            for (otherEdge in sortedEdgesWithOriginalIndex) {
                val otherNodeA = otherEdge[0]
                val otherNodeB = otherEdge[1]
                val otherWeight = otherEdge[2]

                if (unionFindWithCandidate.union(otherNodeA, otherNodeB)) {
                    mstWeightWithCandidate += otherWeight
                }
            }

            /*
            Se o peso final continua igual ao da MST ótima,
            então essa aresta pode aparecer em alguma MST mínima.

            Logo, ela é pseudo-critical.
            */
            if (mstWeightWithCandidate == originalMstWeight) {
                pseudoCriticalEdgeIndices.add(candidateOriginalIndex)
            }
        }

        /*
        STEP 10
        Retornar:
        [criticalEdges, pseudoCriticalEdges]

        O(1)
        */
        return listOf(criticalEdgeIndices, pseudoCriticalEdgeIndices)
    }
}