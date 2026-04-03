/*
Word Search

PROBLEMA

Dado:

- um tabuleiro board com caracteres
- uma string targetWord

Precisamos verificar se targetWord pode ser formada percorrendo o tabuleiro.

Regras:

- podemos mover apenas para:
    - cima
    - baixo
    - esquerda
    - direita
- nao podemos reutilizar a mesma celula
  no mesmo caminho atual

EXEMPLO

board =
[
  ['A','B','C','E'],
  ['S','F','C','S'],
  ['A','D','E','E']
]

targetWord = "ABCCED"

Resposta:
true


IDEIA PRINCIPAL

Para cada celula do tabuleiro,
tentamos comecar a formar a palavra a partir dali.

Se a celula atual combina com a letra esperada:

- marcamos essa celula como visitada
- tentamos continuar a palavra nos 4 vizinhos
- se nenhum caminho funcionar, desfazemos a marcacao
  e voltamos (backtracking)

INTUICAO DO DFS + BACKTRACKING

A funcao recursiva recebe:

- currentRow
- currentColumn
- currentWordIndex

Isso significa:

"estou na posicao (row, col)
e preciso casar a letra targetWord[currentWordIndex]"


CASOS EM QUE O CAMINHO FALHA

1. saimos dos limites do tabuleiro
2. o caractere da celula nao bate com a letra esperada
3. a celula ja foi usada no caminho atual

CASO DE SUCESSO

Se o currentWordIndex == targetWord.length
significa que conseguimos casar todas as letras.


ESTRATEGIA

Step 1
Percorrer todas as celulas do tabuleiro

Step 2
Para cada celula, tentar iniciar a palavra com DFS.

Step 3
No DFS:
- validar limites
- validar caractere
- validar se ja foi visitada

Step 4
Marcar a celula como visitada

Step 5
Explorar os 4 vizinhos

Step 6
Remover a celula do caminho atual (backtracking)

Step 7
Se algum caminho retornar true
a resposta final e true


COMPLEXIDADE

Tempo: O(m * n * 4ˆL)

Onde:
- m = numero de linhas
- n = numero colunas
- L = tamanho da palavra

Explicacao:

- tentamos iniciar a busca em cada celula -> O(m * n)
- a partir de cada celula, no pior caso exploramos
  ate 4 direcoes por caractere da palavra

Em analise mais refinada, depois do primeiro passo
o branching tente a 3, mas o limite superior classico
e O(m * n * 4ˆL)

Espaco: O(L)

Explicacao:

- a profundidade maxima da recursao e o tamanho da palavra
- o conjunto de celulas visitadas no caminho atual
  tambem pode armazenar ate L posicoes

*/

class Solution {
    fun exist(
        board: Array<CharArray>, 
        targetWord: String
    ): Boolean {
        
        /*
        STEP 1
        Dimensoes do tabuleiro

        O(1)
        */
        val totalRows = board.size
        val totalColumns = board[0].size

        /*
        STEP 2
        Conjunto com as celulas usadas no caminho atual

        cada entrada sera: Pair(row, column)

        Empty HashSet creation O(1)
        */
        val visitedCellsInCurrentPath = HashSet<Pair<Int, Int>>()

        /*
        STEP 3
        DFS para tentar formar a palavra.

        currentRow       -> linha atual
        currentColumn    -> coluna atual
        currentWordIndex -> indice da letra que precisamos casar
        */
        fun searchWordBacktrackingDFS(
            currentRow: Int,
            currentColumn: Int,
            currentWordIndex: Int
        ): Boolean {

            /*
            STEP 4
            Se chegamos no final da palavra,
            significa que todas as letras foram encontradas
            
            O(1)
            */
            if (currentWordIndex == targetWord.length) {
                return true
            }

            /*
            STEP 5
            Se saiu dos limites, ja visitou a celula
            ou o caractere nao combina, este caminho falha

            O(1)
            */
            if (
                currentRow < 0 ||
                currentColumn < 0 ||
                currentRow >= totalRows ||
                currentColumn >= totalColumns ||
                board[currentRow][currentColumn] != targetWord[currentWordIndex] ||
                Pair(currentRow, currentColumn) in visitedCellsInCurrentPath
            ) return false

            /*
            STEP 6
            Marcar a celula atual como visitada
            no caminho corrente

            O(1)
             */
            visitedCellsInCurrentPath.add(Pair(currentRow, currentColumn))

            /*
            STEP 7
            Tentar continua a palavra
            nos 4 vizinhos

            Chamadas recursivas
            */
            val wordFound =
                searchWordBacktrackingDFS(currentRow + 1, currentColumn, currentWordIndex + 1) ||
                searchWordBacktrackingDFS(currentRow - 1, currentColumn, currentWordIndex + 1) ||
                searchWordBacktrackingDFS(currentRow, currentColumn + 1, currentWordIndex + 1) ||
                searchWordBacktrackingDFS(currentRow, currentColumn - 1, currentWordIndex + 1)

            /*
            STEP 8
            Backtracking
            remover a celula do caminho atual
            para permitir outras tentativas

            O(1)
            */
            visitedCellsInCurrentPath.remove(Pair(currentRow, currentColumn))

            /*
            STEP 9
            Retornar se algum dos caminhos funcionou

            O(1)
            */
            return wordFound
        }

        /*
        STEP 10
        Tentar iniciar a palavra a partir de cada celula

        Loop -> O(m * m)
        */
        for (rowIndex in 0 until totalRows) {
            for (columnIndex in 0 until totalColumns) {

                /*
                STEP 11
                Se algum ponto inicial funcionar,
                retornamos true imediatamente

                O(1)
                */
                if (searchWordBacktrackingDFS(rowIndex, columnIndex, 0)) {
                    return true
                }
            }
        }

        /*
        STEP 12
        Se nenhum caminho funcionou
        a palavra nao existe no tabuleiro

        O(1)
        */
        return false
    }
}
