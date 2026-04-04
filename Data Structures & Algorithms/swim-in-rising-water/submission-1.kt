/*
Swin in Rising Water

PROBLEMA

Temos uma matriz quadrada elevationGrid,
onde cada celula representa a altura daquele ponto.

A agua sobre com o tempo:

- no tempo t, o nivel da agua e t

Podemos nos mover para uma celula vizinha
(apenas cima, baixo, esquerda, direita)
se a altura da celula atual e da proxima
for menor ou igual ao nivel da agua no tempo t.

Objetivo:

sair de (0, 0) e chegar em (n - 1, n - 1)
no menor tempo possivel.

EXEMPLO

Entrada:
[
    [0,1],
    [2,3]
]

Saida:
3

Explicacao:
Para alcancar a celula final [1][1],
a agua precisa subir ate 3.


IDEIA PRINCIPAL

O custo de um caminho NÃO e a soma das alturas.

O custo real de um caminho e:

a maior altura encontrada ao longo dele

Por que?

Porque a agua precisa subir pelo menos ate essa altura
para que todo o caminho fique acessivel.

Entao o problema vira:

Encontrar um caminho do inicio ao fim
que minimize a maior altura encontrada no trajeto.


CONEXAO COM DIJKSTRA

No Dijkstra classico:

- o custo ate um no costuma ser a soma dos pesos

Aqui:

- o custo ate uma celula e o maior valor visto ate ela

Mesmo assim, a ideia do Dijkstra continua valida:

sempre expandimos a celula com o menor "tempo necessario ate agora"


INTUICAO

Se estamos em uma celula com tempo atual currentTime,
e queremos ir para a celula vizinha com altura h:

o novo tempo sera:

max(currentTime, h)

Porque:

- se h for menor que currentTime,
  ja temos agua suficiente
- se h for maior,
  precisamos esperar a agua subir ate h


ESTRATEGIA

Step 1
Usar uma min-heap com estados:
(timeNeeded, row, col)

Step 2
Comecar da celula inicial:
(grid[0][0], 0, 0)

Step 3
Sempre retirar da heap
a celula com menor tempo necessario

Step 4
Se for a celula final, retornar esse tempo

Step 5
Para cada vizinho valido:
- calcular newTime = max(currentTime, neighborHeight)
- adicionar na heap

Step 6
Usar visited para nao processar a mesma celula mais de uma vez


COMPLEXIDADE

Tempo: O(nˆ2 log n)

Explicacao:

- existem nˆ2 celulas
- cada celula pode entrar na heap
- operacoes de heap custam O(log(nˆ2)) = O(log n)

Espaco: O(nˆ2)

Explicacao:

- usamos visited para ate nˆ2 celulas
- a heap tambem pode armazenar ate nˆ2 estados
*/

class Solution {
    fun swimInWater(elevationGrid: Array<IntArray>): Int {
        
        /*
        STEP 1
        Tamanho da matriz

        O(1)
        */
        val gridSize = elevationGrid.size
        
        /*
        STEP 2
        Direcoes possiveis
        direita, esquerda, baixo, cima

        O(1)
        */
        val directions = listOf(
            Pair(0, 1), // direita
            Pair(0, -1), // esquerda
            Pair(1, 0), // baixo
            Pair(-1, 0) // cima
        )

        /*
        STEP 3
        Min-heap com estados:
        Pair(timeNeeded, Pair(row, col))

        timeNeeded representa o menor tempo conhecido
        para alcancar aquela celula ate agora

        Criacao -> O(1)
        */
        val minTimeHeap = PriorityQueue(
            compareBy<Pair<Int, Pair<Int, Int>>> { it.first }
        )

        /*
        STEP 4
        Comecar pela celula inicial

        O tempo inicial e a propria altura da celula [0][0],
        porque nao podemos sequer ficar nela antes desse nivel.

        offer() -> O(log(nˆ2))
        */
        minTimeHeap.offer(Pair(elevationGrid[0][0], Pair(0,0)))

        /*
        STEP 5
        Conjunto de celulas ja processadas.

        O(1)
        */
        val visitedCells = HashSet<Pair<Int, Int>>()

        /*
        STEP 6
        Marcar a celula inicial como visitada.

        O(1)
        */
        visitedCells.add(Pair(0, 0))

        /*
        STEP 7
        Processar a heap enquanto houver estados disponiveis.

        Loop -> ate esvaziar a heap
        */
        while (minTimeHeap.isNotEmpty()) {

            /*
            STEP 8
            Retirar a celula com menor tempo necessario

            poll() -> O(log(nˆ2))
            */
            val (currentTime, currentPosition) = minTimeHeap.poll()
            val (currentRow, currentColumn) = currentPosition

            /*
            STEP 9
            Se chegamos no destino,
            esse e o menor tempo possivel

            O(1)
            */
            if (currentRow == gridSize - 1 && currentColumn == gridSize - 1) {
                return currentTime
            }

            /*
            STEP 10
            Explorar os 4 vizinhos

            Loop fixo de 4 direcoes -> O(1)
            */
            for ((rowOffSet, columnOffSet) in directions) {

                val neighborRow = currentRow + rowOffSet
                val neighborColumn = currentColumn + columnOffSet
                val neighborPosition = Pair(neighborRow, neighborColumn)

                /*
                STEP 11
                Ignorar vizinhos invalidos ou ja visitados

                O(1)
                */
                if (
                    neighborRow !in 0 until gridSize ||
                    neighborColumn !in 0 until gridSize ||
                    neighborPosition in visitedCells
                ) {
                    continue
                }

                /*
                STEP 12
                Marcar vizinho como visitado.

                O(1)
                */
                visitedCells.add(neighborPosition)

                /*
                STEP 13
                O tempo necessario para alcancar o vizinho
                e o maximo entre:
                - o tempo atual do caminho
                - a altura da celula vizinha

                O(1)
                */
                val nextTime = maxOf(
                    currentTime,
                    elevationGrid[neighborRow][neighborColumn]
                )

                /*
                STEP 14
                Inserir vizinho na heap.

                offer() -> O(log(nˆ2))
                */
                minTimeHeap.offer(Pair(nextTime, neighborPosition))
            }
        }

        /*
        STEP 15
        Teoricamente sempre devemos encontrar resposta
        se o grid for valido, mas mantemos esse retorno por seguranca.

        O(1)
        */
        return -1
    }
}
