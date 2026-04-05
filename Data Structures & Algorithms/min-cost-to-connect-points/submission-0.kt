/*
Min cost to Connect Points

PROBLEMA

Temos uma lista de pontos em um plano 2D.

Cada ponto e:
[x, y]

O custo para conectar dois pontos e a distancia de Manhattan:

|x1 - x2| + |y1 - y2|

Objetivo:

conectar todos os pontos com o menor custo total possivel,
garantindo que exista exatamente um caminho entre qualquer par de pontos.

O QUE SIGNIFICA "EXISTE EXATAMENTE UM CAMINHO"?

Isso significa que a estrutura final deve ser uma arvore:

- todos os pontos conectados
- sem ciclos

Em grafos, isso e exatamente uma:

Minimum Spanning Tree (MST)

O QUE E UMA MST?

MST = Minimum Spanning Tree

E um arvore que:

- conecta todos os nos
- nao tem ciclos
- tem o menor custo total possivel


QUAL ALGORITMO USAR?

Esse problema e um classico de MST.

Podemos resolver com:

- Kruskal
ou
- Prim

Aqui vamos usar:

Prim's Algorithm


INTUICAO DO PRIM

Prim cresce a arvore aos poucos.

A logica e:

1. comecar com qualquer ponto
2. olhar todas as arestas que saem da arvore atual
3. escolher a aresta de menor custo que conecta
   um ponto ja visitado a um ponto ainda nao visitado
4. adicionar esse novo ponto a arvore
5. repetir ate conectar todos os pontos


VISUALIZACAO MENTAL

Pensa assim:

- a arvore comeca com 1 ponto
- depois vai "engolindo" novos pontos
- sempre pelo caminho mais barato possivel


POR QUE A MIN-HEAP AJUDA?

Em cada passo, queremos rapidamente saber:

qual e a aresta mais barata disponivel agora?

A min-heap resolve isso porque sempre entrega
o menor custo no topo.


ESTRATEGIA DESSA SOLUCAO

Step 1
Construir um grafo completo:
cada ponto se conecta com todos os outros,
com peso = distancia de Manhattan

Step 2
Criar:
- visitedPoints -> pontos ja incluidos na MST
- minCostHeap -> heap com pares (cost, point)

Step 3
Comecar do ponto 0 com custo 0

Step 4
Enquanto ainda faltarem pontos:
- pegar o menor custo da heap
- se o ponto ja foi visitado, ignorar
- senao:
    - adicionar custo ao total
    - marcar ponto como visitado
    - colocar na heap os vizinhos ainda nao visitados


POR QUE ISSO FUNCIONA?

A cada passo, escolhemos a menor aresta possivel
que conecta a arvore atual a um novo ponto.

Essa e exatamente a regra do Prim.

Como sempre expandimos usando a conexao mais barata possivel,
o custo total final sera minimo.


COMPLEXIDADE

Tempo: O(nˆ2 log n)

Explicacao:

- existem n pontos
- construimos arestas entre todos os pares -> O(nˆ2)
- cada insercao/remocao da heap cust O(log n)


Espaco: O(nˆ2)

Explicacao:

- armazenamos o grafo completo (adjacency list)
- como cada ponto se conecta com todos os outros,
  o numero total de arestas e O(Nˆ2)

*/

class Solution {
    fun minCostConnectPoints(points: Array<IntArray>): Int {

        /*
        STEP 1
        Quantidade total de pontos.

        O(1)
        */
        val totalPoints = points.size

        /*
        STEP 2
        Construir adjacency list do grafo completo.

        Estrutura:
        pointIndex -> lista de (edgeCost, neighborPoint)

        Como cada ponto pode se conectar com todos os outros,
        construimos todas as arestas possiveis.

        O(nˆ2)
        */
        val adjacencyList = HashMap<Int, MutableList<Pair<Int, Int>>>()

        for (pointAIndex in 0 until totalPoints) {
            val pointAX = points[pointAIndex][0]
            val pointAY = points[pointAIndex][1]

            for (pointBIndex in pointAIndex + 1 until totalPoints) {
                val pointBX = points[pointBIndex][0]
                val pointBY = points[pointBIndex][1]

                /*
                STEP 3
                Calcular distancia de Manhattan entre dois pontos.

                O(1)
                */
                val edgeCost = abs(pointAX - pointBX) + abs(pointAY - pointBY)

                /*
                STEP 4
                Como o grafo e nao direcionado,
                adicionamos a aresta nos dois sentidos.

                O(1) amortizado
                */
                adjacencyList
                    .computeIfAbsent(pointAIndex) { mutableListOf() }
                    .add(edgeCost to pointBIndex)

                adjacencyList
                    .computeIfAbsent(pointBIndex) { mutableListOf() }
                    .add(edgeCost to pointAIndex)
            }
        }

        /*
        STEP 5
        Custo total da MST.

        O(1)
        */
        var minimumTotalCost = 0

        /*
        STEP 6
        Conjunto de pontos ja incluidos na MST.

        visitedPoints guarda os nos ja conectados
        pela arvore minima atual.

        O(1)
        */
        val visitedPoints = mutableSetOf<Int>()

        /*
        STEP 7
        Min-heap com pares:
        (edgeCost, pointIndex)

        A heap sempre nos da o proximo ponto
        mais barato para adicionar a MST.

        O(1)
        */
        val minimumCostHeap = PriorityQueue(
            compareBy<Pair<Int, Int>> { heapEntry -> heapEntry.first }
        )

        /*
        STEP 8
        Comecamos do ponto 0 com custo 0.
        
        Por que custo 0?

        Porque o primeiro ponto nao precisa ser conectado a ninguem antes. Ele e apenasr o inicio da arvore.

        add() -> O(log n)
        */
        minimumCostHeap.add(0 to 0)

        /*
        STEP 9
        Continuar ate incluir todos os pontos na MST.

        O loop termina quando visitedPoints.size == totalPoints
        */
        while (visitedPoints.size < totalPoints) {

            /*
            STEP 10
            Pegar a aresta de menor custo disponivel

            poll() -> O(log n)
            */
            val (currentCost, currentPoint) = minimumCostHeap.poll()

            /*
            STEP 11
            Se o ponto ja foi visitado,
            ignoramos essa entrada.

            Isso acontece porque o mesmo ponto pode entrar
            varias vezes na heap com custos diferentes,
            mas so queremos processa-lo na primeira vez
            em que ele for retirado com o menor custo possivel.

            O(1)
            */
            if (currentPoint in visitedPoints) {
                continue
            }

            /*
            STEP 12
            Adicionar o custo dessa conexao
            ao total da MST

            O(1)
            */
            minimumTotalCost += currentCost

            /*
            STEP 13
            Marcar esse ponto como incluido na MST.

            O(1)
            */
            visitedPoints.add(currentPoint)

            /*
            STEP 14
            Explorar todos os vizinhos do ponto atual.

            Para cada vizinho ainda nao visitado,
            adicionamos a conexao na heap.

            Nao escolhemos imediatamente.
            Apenas colocamos como opcao futura.

            O(grau do no)
            */
            for ((neighborCost, neighborPoint) in adjacencyList[currentPoint] ?: emptyList()) {
                if (neighborPoint !in visitedPoints) {
                    minimumCostHeap.add(neighborCost to neighborPoint)
                }
            }
        }

        /*
        STEP 15
        Quando todos os pontos foram incluidos,
        o custo acumulado e a resposta.

        O(1)
        */
        return minimumTotalCost
    }
}
