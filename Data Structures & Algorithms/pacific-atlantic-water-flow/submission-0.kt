/*
Pacific Atlantic Water Flow

PROBLEMA

Temos uma matriz heights onde cada celula representa a altura de um ponto.

A agua pode fluir de uma celula para uma vizinha
(cima, baixo, esquerda, direita)
somente se a celula vizinha tiver altura
menor ou igual a celula atual.

Temos dois oceanos:

- Pacifico: toca a borda de cima e a borda da esquerda
- Atlantico: toca a borda de baixo e a borda da direita

Objetivo:

retornar todas as celulas de onde a agua consegue
chegar aos DOIS oceanos.


IDEIA PRINCIPAL

Pensar "da celula para o oceano" parece natural,
mas gera muito trabalho repetido.

Em vez disso, fazemos o racicionio reverso:

Se a agua pode descer de uma celula alta para uma baixa,
entao, ao inverter o raciocionio,
podemos sair do oceano e "subir" para celular
com altura maior ou igual.

Ou seja

- comecamos DFS a partir das bordas do Pacifico
- comecamos DFS a partir das bordas do Atlantico
- marcamos todas as celulas alcancaveis em cada caso
- a resposta final sao as celulas que aparecem nos dois conjuntos


POR QUE ISSO FUNCIONA?

Se o Pacifico consegue "subir" ate uma celula,
entao essa celula consegue "descer" ate o Pacifico.

Se o Atlantico tambem consegue "subir" ate ela,
entao essa celula consegue chegar aos dois oceanos.


INTUICAO

Em vez de perguntar:

"essa celula consegue chegar ao oceano?"

Perguntamos:

"o oceano consegue alcancar essa celula
seguindo o fluxo reverso?"


REGRA DO DFS REVERSO

Se estamos na celula atual (r, c),
so podemos visitar uma vizinha (nr, nc) se:

heights[nr][nc] >= heights[r][c]

Por que?

Porque estamos andando no sentido inverso do fluxo da agua.
No fluxo normal a agua desce ou fica no mesmo nivel.
No fluxo reverso, so podemos subir ou manter o nivel.


ESTRATEGIA

Step 1
Criar dois conjuntos:
- pacificReachableCells
- atlanticReachableCells

Step 2
Rodar DFS a partir de todas as celulas da borda do Pacifico:
- primeira linha
- primeira coluna

Step 3
Rodar DFS a partir de todas as celulas da borda do Atlantico:
- ultima linha
- ultima coluna

Step 4
No final, toda celula presente nos dois conjuntos
faz parte da resposta.


COMPLEXIDADE

Tempo: O(m * n)

Explicacao:

- cada celula pode ser visitada no maximo uma vez
  no DFS do Pacifico
- e no maximo uma vez no DFS do Atlantico

Espaco: O(m * n)

Explicacao:

- os dois conjuntos podem armazenar ate m * n celulas
- a recursao DFS tambem pode crescer ate esse tamanho

*/

class Solution {
    fun pacificAtlantic(heights: Array<IntArray>): List<List<Int>> {
        
        /*
        STEP 1
        Guardar dimensoes da matriz

        O(1)
        */
        val totalRows = heights.size
        val totalColumns = heights[0].size

        /*
        STEP 2
        Conjunto de celulas que conseguem chegar ao Pacifico
        usando o raciocinio reverso.

        Estrutura:
        Pair(row, column)

        O(1)
        */
        val pacificReachableCells = HashSet<Pair<Int, Int>>()

        /*
        STEP 3
        Conjunto de celulas que conseguem chegar ao Atlantico
        usando o raciocinio reverso.

        O(1)
        */
        val atlanticReachableCells = HashSet<Pair<Int, Int>>()

        /*
        STEP 4
        DFS reverso.

        currentRow / currentColumn:
        celula atual

        visitedCells:
        conjunto que estamos preenchendo
        (Pacifico ou Atlantico)

        previousHeight:
        altura da celula de onde viemos

        A regra principal e:
        so podemos continuar se a celula atual tiver altura
        >= previousHeight

        Isso representa "subir" ou manter altura
        no fluxo reverso.
        */
        fun markReachableCellsDFS(
            currentRow: Int,
            currentColumn: Int,
            visitedCells: HashSet<Pair<Int, Int>>,
            previousHeight: Int
        ) {

            /*
            STEP 5
            Criar coordenada da celula atual.

            O(1)
            */
            val currentCell = currentRow to currentColumn

            /*
            STEP 6
            Casos em que o DFS deve parar:

            1. ja visitamos essa celula nesse mesmo oceano
            2. saiu dos limites da matriz
            3. a altura atual e menor que a altura anterior

            Esse terceiro ponto e o coracao da solucao.

            Se heights[current] < previousHeight,
            entao podemos "subir" ate essa celula
            no fluxo reverso.

            O(1)
            */
            if (
                currentCell in visitedCells ||
                currentRow < 0 ||
                currentColumn < 0 ||
                currentRow == totalRows ||
                currentColumn == totalColumns ||
                heights[currentRow][currentColumn] < previousHeight
            ) return

            /*
            STEP 7
            Marcar a celula atual como alcancavel
            por esse oceano

            O(1)
            */
            visitedCells.add(currentCell)

            /*
            STEP 8
            Guardar a altura atual.

            Essa altura sera usada como previousHeight
            nas proximas chamadas recursivas.

            O(1)
            */
            val currentHeight = heights[currentRow][currentColumn]

            /*
            STEP 9
            Continuar DFS nas 4 direcoes.

            Como estamos em grid,
            podemos andar para:
            - baixo
            - cima
            - direita
            - esquerda

            Cada chamada tenta expandir a regiao alcancavel.

            Chamadas recursivas
            */
            markReachableCellsDFS(
                currentRow + 1,
                currentColumn,
                visitedCells,
                currentHeight
            ) // baixo

            markReachableCellsDFS(
                currentRow - 1,
                currentColumn,
                visitedCells,
                currentHeight
            ) // cima

            markReachableCellsDFS(
                currentRow,
                currentColumn + 1,
                visitedCells,
                currentHeight
            ) // direita

            markReachableCellsDFS(
                currentRow,
                currentColumn - 1,
                visitedCells,
                currentHeight
            ) // esquerda
        }

        /*
        STEP 10
        Iniciar DFS a partir das bordas do Pacifico e do Atlantico.

        Para cada coluna:
        - linha 0 pertence ao Pacifico
        - ultima linha pertence ao Atlantico

        Loop -> O(n)
        */
        for (columnIndex in 0 until totalColumns) {
            markReachableCellsDFS(
                0,
                columnIndex,
                pacificReachableCells,
                heights[0][columnIndex]
            )

            markReachableCellsDFS(
                totalRows - 1,
                columnIndex,
                atlanticReachableCells,
                heights[totalRows - 1][columnIndex]
            )
        }
        
        /*
        STEP 11
        Para cada linha:
        - coluna 0 pertence ao Pacifico
        - ultima coluna pertence ao Atlantico

        Loop -> O(m)
        */
        for (rowIndex in 0 until totalRows) {
            markReachableCellsDFS(
                rowIndex,
                0,
                pacificReachableCells,
                heights[rowIndex][0]
            )

            markReachableCellsDFS(
                rowIndex,
                totalColumns - 1,
                atlanticReachableCells,
                heights[rowIndex][totalColumns - 1]
            )
        }

        /*
        STEP 12
        Construir resposta final.
        
        Uma celula entra na resposta se estiver
        nos dois conjuntos:
        - alcancavel pelo Pacifico
        - alcancavel pelo Atlantico

        Double loop -> O(m * n)
        */
        return (0 until totalRows).flatMap { rowIndex ->
            (0 until totalColumns).mapNotNull { columnIndex ->
                val currentCell = rowIndex to columnIndex

                if (
                    currentCell in pacificReachableCells &&
                    currentCell in atlanticReachableCells
                ) {
                    listOf(rowIndex, columnIndex)
                } else {
                    null
                }
            }

        }
    }
}
