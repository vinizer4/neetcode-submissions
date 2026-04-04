/*
N-Queens

PROBLEMA

Dado um numero n,
precisamos posicionar n rainhas em um tabuleiro n x n
de forma que nenhuma rainha ataque outra.

Regras de ataque

- mesma linha
- mesma coluna
- mesma diagonal

Objetivo:

retornar todas as configuracoes validas.

EXEMPLO (n = 4)

Saída:
[
  [".Q..","...Q","Q...","..Q."],
  ["..Q.","Q...","...Q",".Q.."]
]


IDEIA PRINCIPAL

Colocamos uma rainha por linha.

Para cada linha:
- tentamos colocar a rainha em cada coluna
- so colocamos se a posicao for segura

COMO SABER SE E SEGURO?

Usamos 3 conjuntos (HashSet):

1. usedColumns
    -> colunas ja ocupadas

2. usedPositiveDiagonals (row + col)
    -> diagonais com inclinacao /

3. usedNegativeDiagonals (row - col)
    -> diagonais com inclinacao \

Isso permite verificar conflitos em O(1)


INTUICAO

Para cada linha:

- tentamos todas as colunas
- se a posicao nao tiver conflito:
    - colocamos a rainha
    - avancamos para a proxima linha
- se nao funcionar, voltamos (backtracking)


ESTRATEGIA

Step 1
Criar:
- board (matrix n x n)
- sets para colunas e diagonais

Step 2
Fazer backtracking linha por linha

Step 3
Para cada coluna:
- verificar se a posicao e valida
- se for:
    - marcar sets
    - colocar 'Q''
    - ir para proxima linha

Step 4
Se r == n:
- salvar solucao

Step 5
Backtracking:
- remover rainha
- limpar sets


COMPLEXIDADE

Tempo: O(n!)

Explicacao:

- cada linha tenta varias colunas
- o numero de combinacoes validas cresce de forma fatorial

Espaco: O(nˆ2)

Explicacao:

- usamos um tabuleiro n x n
- os sets armazenam o maximo n elementos
*/

class Solution {
    fun solveNQueens(boardSize: Int): List<List<String>> {
        
        /*
        STEP 1
        Conjuntos para controle de conflitos

        O(1)
        */
        val usedColumns = HashSet<Int>()
        val usedPositiveDiagonals = HashSet<Int>() // r + c
        val usedNegativeDiagonals = HashSet<Int>() // r - c

        /*
        STEP 2
        Lista com todas as solucoes

        O(1)
        */
        val allValidBoards = mutableListOf<List<String>>()

        /*
        STEP 3
        Tabuleiro inicial com '.''

        O(nˆ2)
        */
        val chessBoard = Array(boardSize) { CharArray(boardSize) { '.' } }

        /*
        STEP 4
        Backtracking por linha.

        currentRow -> linha atual
        */
        fun placeQueensBacktracking(currentRow: Int) {
            
            /*
            STEP 5
            Se colocamos todas as rainhas,
            salvamos o tabuleiro atual.

            joinToString() -> O(n)
            map() -> O(n)

            Total -> O(nˆ2)
            */
            if (currentRow == boardSize) {
                allValidBoards.add(chessBoard.map { it.joinToString("") })
                return
            }

            /*
            STEP 6
            Tentar cada coluna na linha atual.

            Loop -> O(n)
            */
            for (currentColumn in 0 until boardSize) {

                /*
                STEP 7
                Verificar conflitos

                O(1)
                */
                if (
                    currentColumn in usedColumns ||
                    (currentRow + currentColumn) in usedPositiveDiagonals ||
                    (currentRow - currentColumn) in usedNegativeDiagonals
                ) continue

                /*
                STEP 8
                Marcar posicao como ocupada.

                O(1)
                */
                usedColumns.add(currentColumn)
                usedPositiveDiagonals.add(currentRow + currentColumn)
                usedNegativeDiagonals.add(currentRow - currentColumn)

                /*
                STEP 9
                Colocar rainha no tabuleiro.

                O(1)
                */
                chessBoard[currentRow][currentColumn] = 'Q'

                /*
                STEP 10
                Ir para proxima linha

                Chamada recursiva
                */
                placeQueensBacktracking(currentRow + 1)

                /*
                STEP 11
                Backtracking:
                remover rainhar e limpar marcacoes

                O(1)
                */
                usedColumns.remove(currentColumn)
                usedPositiveDiagonals.remove(currentRow + currentColumn)
                usedNegativeDiagonals.remove(currentRow - currentColumn)

                chessBoard[currentRow][currentColumn] = '.'
            }
        }

        /*
        STEP 12
        Iniciar backtracking da linha 0.

        O(1)
        */
        placeQueensBacktracking(0)

        /*
        STEP 13
        Retornar todas as solucoes

        O(1)
        */
        return allValidBoards
    }
}
