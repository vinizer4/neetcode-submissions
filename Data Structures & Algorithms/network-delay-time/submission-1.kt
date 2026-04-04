/*
Network Delay Time

PROBLEMA

Temos:

- directedEdges, onde cada aresta [sourceNode, targetNode, travelTime]
- totalNodes, quantidade total de nós
- startNode, nó de origem do sinal

Objetivo:

descobrir quanto tempo o sinal leva
para alcançar TODOS os nós da rede.

Se algum nó não puder ser alcançado,
a resposta deve ser -1.


EXEMPLO

Entrada:
directedEdges = [[1,2,1], [2,3,1], [1,4,4], [3,4,1]]
totalNodes = 4
startNode = 1

Saída:
3

Explicação:

1 -> 2 = 1
1 -> 2 -> 3 = 2
1 -> 2 -> 3 -> 4 = 3

O último nó recebe o sinal no tempo 3.


IDEIA PRINCIPAL

Esse é o problema clássico de menor caminho
a partir de uma origem para todos os outros nós,
com pesos não negetivos.

A ferramenta correta é:

Dijkstra's Algorithm


INTUIÇÃO DO DIJKSTRA

A ideia é sempre expandir primeiro
o nó que atualmente pode ser alcancado
com o menor tempo conhecido.

Usamos uma min-heap para isso.

Quando um no sai ad heap pela primeira vez:

- esse tempo ja e o menor tempo possivel para chegar nele
- entao podemos marcar esse no como finalizado

Depois disso:
- exploramos seus vizinhos
- adicionamos novos tempos candidatos na heap


POR QUE ISSO FUNCIONA?

Como todos os pesos sao não negativos:

- se um no foi retirado da min-heap com menor tempo,
  nao existe caminho melhor para ele depois

Essa e a propriedade central do Dijkstra.


ESTRATEGIA

Step 1
Construir uma adjacency list:
sourceNode -> lista de (neighborNode, edgeWeight)

Step 2
Criar uma min-heap com:
(currentTime, currentNode)

comecamos com:
(0, startNode)

Step 3
Usar um set visitedNodes
para marcar os nós ja finalizados.

Step 4
Enquanto a heap nao estiver vazia:
- pegar o no com menor tempo
- se ja foi visitado, ignorar
- marcar como visitado
- atualizar o tempo atual
- colocar os visinhos na heap com tempo acumulado

Step 5
Se visitamos todos os nos:
- retornar o maior tempo finalizado

Caso contrario:
- retornar -1


COMPLEXIDADE

Tempo: O(E log V)

Onde:
- V = quantidade de nos
- E = quantidade de arestas

Explicacao:

- cada aresta pode gerar insercao na heap
- cada operacao na heap custa O(log V)

Espaco: O(V + E)

Explicacao:

- adjacency list armazena as arestas
- visitedNodes armazena os nos visitados
- a heap tambem pode armazenar multiplos estados

*/

class Solution {
    fun networkDelayTime(
        directedEdges: Array<IntArray>, 
        totalNodes: Int, 
        startNode: Int)
    : Int {
        
        /*
        STEP 1
        Construir adjacency list.

        Estrutura:
        node -> lista de (neighborNode, edgeWeight)

        O(E)
        */
        val adjacencyList = HashMap<Int, MutableList<Pair<Int, Int>>>()

        for ((sourceNode, targetNode, travelTime) in directedEdges) {
            adjacencyList
                .computeIfAbsent(sourceNode) { mutableListOf() }
                .add(Pair(targetNode, travelTime))
        }

        /*
        STEP 2
        Min-heap para processar sempre
        o proximo no com menor tempo acumulado.

        Estrutura:
        Pair(currentTime, currentNode)

        Criacao -> O(1)
        */
        val minTimeHeap = PriorityQueue<Pair<Int, Int>>(
            compareBy { it.first }
        )

        /*
        STEP 3
        Comecar do no inicial no tempo 0
        
        offer() -> O(log V)
        */
        minTimeHeap.offer(Pair(0, startNode))

        /*
        STEP 4
        Conjunto de nos ja finalizados pelo Dijkstra.

        O(1)
        */
        val visitedNodes = HashSet<Int>()

        /*
        STEP 5
        Armazena o maior tempo entre os nos finalizados.

        Esse sera o tempo em que o ultimo no recebe o sinal.

        O(1)
        */
        var maxSignalTime = 0


        /*
        STEP 6
        Processar nos enquanto houver candidatos na heap.

        Loop -> ate esvaziar a heap
        */        
        while (minTimeHeap.isNotEmpty()) {
            
            /*
            STEP 7
            Pegar o no com menor tempo acumulado.

            poll() -> O(log V)
            */
            val (currentTime, currentNode) = minTimeHeap.poll()

            /*
            STEP 8
            Se o no ja foi finalizado antes,
            ignoramos esta entrada da heap.

            O(1)
            */
            if (currentNode in visitedNodes) {
                continue
            }

            /*
            STEP 9
            Marcar o no como visitado/finalizado.

            O(1)
            */
            visitedNodes.add(currentNode)

            /*
            STEP 10
            Atualizar o maior tempo alcancado ate agora.

            Como os nos saem em ordem crescente de tempo,
            esse valor no final sera a resposta.

            O(1)
            */
            maxSignalTime = currentTime

            /*
            STEP 11
            Explorar vizinhos do no atual

            Para cada vizinho ainda nao finalizado:
            adicionar novo tempo acumulado na heap.

            Iteracao total sobre arestas -> O(E)
            */
            adjacencyList[currentNode]?.forEach { (neighborNode, edgeWeight) ->
                if (neighborNode !in visitedNodes) {
                    minTimeHeap.offer(
                        Pair(currentTime + edgeWeight, neighborNode)
                    )
                }
            }
        }

        /*
        STEP 12
        Se todos os nos foram alcancados,
        retornar o maior tempo final.

        Caso contrario, existe no inalcancavel

        O(1)
        */
        return if (visitedNodes.size == totalNodes) {
            maxSignalTime
        } else {
            -1
        }

    }
}
