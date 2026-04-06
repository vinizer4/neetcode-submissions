/*
Walls and Gates

PROBLEMA

Temos uma matriz rooms com tres tipos de valores:

- -1 -> parade / obstaculo
- 0 -> gate (portao)
- INF -> sala vazia

Objetivo:

preencher cada sala vazia com a distancia
ate o gate mais proximo

Se uma sala nao consegue alcancar nenhum gate,
ela deve continuar como INF.

EXEMPLO

Entrada:
[
  [INF, -1,   0, INF],
  [INF, INF, INF, -1],
  [INF, -1, INF, -1],
  [  0, -1, INF, INF]
]

Saída:
[
  [3, -1, 0, 1],
  [2,  2, 1, -1],
  [1, -1, 2, -1],
  [0, -1, 3, 4]
]


IDEIA PRINCIPAL

Se tentarmos rodar BFS a partir de cada sala vazia,
o custo ficaria muito alto.

A solucao correta e:

rodar uma unica BFS comecando de TODOS os gates ao mesmo tempo.

Isso se chama:

Multi-source BFS


POR QUE MULTI-SOURCE BFS FUNCIONA? 

BFS expande em "ondas" de distancia:

- distancia 0
- distancia 1
- distancia 2
- ...

Se colocarmos todos os gates na fila no comeco,
a primeira vez que uma sala vazia for alcancada,
essa distancia sera automaticamente a menor possivel
ate qualquer gate.

Ou seja:

a BFS ja resolve o "gate mais proximo"
sem precisar comparar manualmente.


INTUICAO

Pensa assim:

- todos os gates comecam a expandir ao mesmo tempo
- as ondas se espalham pelo grid
- quando uma sala vazia e alcancada pela primeira vez,
  ela foi alcancada pelo gate mais proximo.


ESTRATEGIA

Step 1
Colocar todos os gates (valor 0) na fila.

Step 2
Fazer BFS normal a partir deles.

Step 3
Quando visitamos um vizinho vazio (INF):
- atualizamos sua distancia
- colocamos ele na fila

Step 4
Como BFS anda em camadas de distancia,
o primeiro valor atribuido a cada sala e o menor possivel


COMPLEXIDADE

Tempo: O(m * n)

Explicacao:

- cada celula entra na fila no maximo uma vez
- cada celula e processada no maximo uma vez

Espaco: O(m * n)

Explicacao:

- a fila pode armazenar varias celular do grid
- no pior caso, pode chegar a O(m * n)

*/


class Solution {
    fun islandsAndTreasure(rooms: Array<IntArray>) {

        /*
        STEP 1
        Guardar a quantidade de linhas e colunas.

        Isso facilita validacoes de limite mais adiante.

        O(1)
        */
        val totalRows = rooms.size
        val totalColumns = rooms[0].size

        /*
        STEP 2
        Criar a fila da BFS.

        Cada elemento da fila sera:
        Pair(row, column)

        Nessa fila vamos colocar inicialmente todos os gates.
        Depois, conforme expandimos, colocamos as salas alcancadas.

        Criacao -> O(1)
        */
        val bfsQueue: ArrayDeque<Pair<Int, Int>> = ArrayDeque()

        /*
        STEP 3
        Percorrer toda a matriz para encontrar os gates.

        Regra:
        - se rooms[row][column] == 0, e um gate
        - todos os gates entram na fila no comeco

        Essa e exatamente a ideia do multi-source BFS:
        comecar de varias fontes ao mesmo tempo.

        Loop -> O(m * n)
        */
        for (rowIndex in 0 until totalRows) {
            for (columnIndex in 0 until totalColumns) {
                if (rooms[rowIndex][columnIndex] == 0) {
                    bfsQueue.addLast(Pair(rowIndex, columnIndex))
                }
            }
        }

        /*
        STEP 4
        Se nao existe nenhum gate,
        nao ha nada para propagar.

        Todas as salas vazias continuarao INF.

        O(1)
        */
        if (bfsQueue.isEmpty()) return

        /*
        STEP 5
        Vetor de direcoes para mover o grid:

        - cima
        - esquerda
        - baixo
        - direita

        Cada direcao e:
        [rowOffSet, ColumnOffSet]

        O(1)
        */
        val directions = arrayOf(
            intArrayOf(-1, 0),
            intArrayOf(0, -1),
            intArrayOf(1, 0),
            intArrayOf(0, 1)
        )

        /*
        STEP 6
        Rodar BFS enquanto houver celulas para processar.

        A fila inicialmente tem todos os gates.
        Depois vai recebendo as salas alcansadas.

        Cada iteracao remove uma celula e tenta expandir
        para seus 4 vizinhos.

        Loop total -> O(m * n)
        */
        while (bfsQueue.isNotEmpty()) {

            /*
            STEP 7
            Remover a proxima celula da fila.

            Ela ja tem em rooms[row][column]
            a menor distancia ate um gate.

            removeFirst() -> O(1)
            */
            val (currentRow, currentColumn) = bfsQueue.removeFirst()

            /*
            STEP 8
            Tentar expandir para os 4 vizinhos.

            Como o grid e nao ponderado e os movimentos tem custo uniforme,
            a distancia do vizinho sera:
            distancia atual + 1

            Loop fixo de 4 direcoes -> O(1)
            */
            for (direction in directions) {

                val neighborRow = currentRow + direction[0]
                val neighborColumn = currentColumn + direction[1]

                /*
                STEP 9
                Validar se o vizinho pode ser processado.

                Casos em que ignoramos:
                1. saiu dos limites da matriz
                2. nao e uma sala vazia (INF)

                Esse segundo ponto e MUITO importante:
                so queremos visitar celulas que ainda nao receberam resposta

                Se a celula ja nao e INF, entao:
                - ou ela e parede (-1)
                - ou ela e gate (0)
                - ou ela ja foi preenchida antes com a menor distancia

                Entao devemos ignorar

                O(1)
                */
                if (
                    neighborRow !in 0 until totalRows ||
                    neighborColumn !in 0 until totalColumns ||
                    rooms[neighborRow][neighborColumn] != Int.MAX_VALUE
                ) continue

                /*
                STEP 10
                Como o vizinho e uma sala vazia ainda nao visitada,
                sua distancia ate o gate mais proximo sera:

                distancia da celula atual + 1

                Por que isso esta correto?

                Porque BFS garante que a primeira vez
                que alcancamos uma celula,
                chegamos nela pelo menor caminho possivel.

                O(1)
                */
                rooms[neighborRow][neighborColumn] =
                    rooms[currentRow][currentColumn] + 1

                /*
                STEP 11
                Colocar o vizinho na fila
                para continuar a expansao a partir dele.

                addLast() -> O(1)
                */
                bfsQueue.addLast(Pair(neighborRow, neighborColumn))
            }
        }
    }
}
