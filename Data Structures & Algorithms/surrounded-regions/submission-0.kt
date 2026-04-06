/*
Surrounded Regions

PROBLEMA

Temos uma matriz board com:

- 'X'
- 'O'

Precisamos capturar todas as regioes de 'O'
que estejam totalmente cercadas por 'X'.

Regra importante:

Uma regiao de 'O' NAO pode ser capturada
se ele tocar a borda do tabuleiro
ou se estiver conectada a algum 'O' da borda

EXEMPLO

Entrada:
[
  ['X','X','X','X'],
  ['X','O','O','X'],
  ['X','X','O','X'],
  ['X','O','X','X']
]

Saída:
[
  ['X','X','X','X'],
  ['X','X','X','X'],
  ['X','X','X','X'],
  ['X','O','X','X']
]

Explicacao:

A regiao do meio foi capturada,
porque estava totalmente cercada.

O 'O' da ultima linha nao foi capturado,
porque toca a borda.


IDEIA PRINCIPAL

Em vez de tentar descobrir diretamente
quais regioes estao cercadas,
fazemos o contrario.

1. encontramos todos os 'O' que NAO podem ser capturados
2. marcamos esses 'O' como seguros
3. no final:
    - todo 'O' que sobrou e cercado -> vira 'X'
    - todo marcador temporario volta para 'O'


POR QUE ISSO FUNCIONA?

A unica forma de uma regiao de 'O'
nao estar carcada
e se ela conseguir chegar ate a borda.

Entao:

- todo 'O' conectado a borda e seguro
- todo 'O' nao conectado a borda e cercado


ESTRATEGIA

Step 1
Percorrer as bordas do tabuleiro.

Step 2
Sempre que encontrar um 'O' na borda,
rodar DFS para marcar toda a regiao conectada como segura.

Usaremos um marcador temporario:
'T'

Step 3
Percorrer toda a matriz:
- 'O' -> cercado -> vira 'X'
- 'T' -> sgeuro -> volta para o 'O'


INTUICAO DO DFS

Se um 'O' da borda e seguro,
entao todos os 'O' conectados a ele tambem sao seguros.

Entao o DFS serve para:

espalhar essa marcacao de seguranca.


COMPLEXIDADE

Tempo: O(m * n)

Explicacao:

- percorremos a borda
- a cada celula pode ser visitada no maximo uma vez no DFS
- depois percorremos a matriz inteira mais um vez

Espaco: O(m * n)

Explicacao:

- no pior caso, a pilha de recursao do DFS
  pode crescer ate o tamanho do tabuleiro

*/

class Solution {
    fun solve(board: Array<CharArray>): Unit {
        
        /*
        STEP 1
        Guardar a quantidade de linhas e colunas.
        
        Isso facilita a validacao de limites
        durante o DFS.

        O(1)
        */
        val totalRows = board.size
        val totalColumns = board[0].size

        /*
        STEP 2
        DFS para marcar como seguras
        todas as celulas 'O' conectadas a borda.

        A logica e:

        - se a celula atual for invalida, paramos
        - se for 'O', marcamos como 'T'
        - depois espalhamos para os 4 vizinhos

        'T' significa:
        "esse 'O' nao pode ser capturado"

        Por que usar 'T'?

        Porque precisamos distinguir:
        - 'O' original ainda nao processado
        - 'O' seguro ja identificado

        Sem esse marcado,
        nao saberiamos o que virar para 'X'
        no final
        */
        fun markBorderConnectedRegionDFS(
            currentRow: Int,
            currentColumn: Int
        ) {

            /*
            STEP 3
            Casos em que devemos parar o DFS:

            1. saiu dos limites do tabuleiro
            2. a celula nao e 'O'

            Se nao e 'O' nao faz parte
            de uma regiao que precisa ser marcada agora.

            O(1)
            */
            if (
                currentRow < 0 ||
                currentColumn < 0 ||
                currentRow == totalRows ||
                currentColumn == totalColumns ||
                board[currentRow][currentColumn] != 'O'
            ) return

            /*
            STEP 4
            Marcar a celula atual como segura.

            Mudamos de 'O' para 'T'.

            Isso impede revisitas infinitas no DFS
            e guarda a informacao de que esse celula
            esta conectada a borda

            O(1)
            */
            board[currentRow][currentColumn] = 'T'

            /*
            STEP 5
            Espalhar DFS para os 4 vizinhos.

            Direcoes:
            - baixo
            - cima
            - direita
            - esquerda

            Chamadas recursivas
            */
            markBorderConnectedRegionDFS(currentRow + 1, currentColumn) // baixo
            markBorderConnectedRegionDFS(currentRow - 1, currentColumn) // cima
            markBorderConnectedRegionDFS(currentRow, currentColumn + 1) // direita
            markBorderConnectedRegionDFS(currentRow, currentColumn - 1) // esquerda
        }

        /*
        STEP 6
        Percorrer todas as linhas e verificar
        a primeira e a ultima coluna.

        Essas duas colunas fazem parte da borda.

        Se encontramos 'O' nelas,
        iniciamos DFS para marcar toda a regiao conectada.

        Loop -> O(m)
        */
        for (rowIndex in 0 until totalRows) {

            // Borda esquerda
            if (board[rowIndex][0] == 'O') {
                markBorderConnectedRegionDFS(rowIndex, 0)
            }

            // Borda direita
            if (board[rowIndex][totalColumns - 1] == 'O') {
                markBorderConnectedRegionDFS(rowIndex, totalColumns - 1)
            }
        }

        /*
        STEP 7
        Percorrer todas as colunas e verificar
        a primeira e a ultima linha.

        Essas duas linhas tambem fazem parte da borda.

        Loop -> O(n) 
        */
        for (columnIndex in 0 until totalColumns) {

            // Borda superior
            if (board[0][columnIndex] == 'O') {
                markBorderConnectedRegionDFS(0, columnIndex)
            }

            // Borda inferior
            if (board[totalRows - 1][columnIndex] == 'O') {
                markBorderConnectedRegionDFS(totalRows - 1, columnIndex)
            }
        }

        /*
        STEP 8
        Agora percorremos toda a matriz para finalizar.

        Regras:
        - se ainda for 'O', entao esta cercado -> vira 'X'
        - se for 'T', entao era seguro -> volta para 'O'

        Essa etapa transforma o tabuleiro
        no resultado final correto.

        Double loop -> O(m * n)
        */
        for (rowIndex in 0 until totalRows) {
            for (columnIndex in 0 until totalColumns) {

                /*
                Se continuou como 'O',
                significa que NAO estava conectada a borda.

                Logo, essa celula esta cercada
                e deve ser capturada.

                O(1)
                */
                if (board[rowIndex][columnIndex] == 'O') {
                    board[rowIndex][columnIndex] = 'X'
                }

                /*
                Se foi marcada como 'T',
                significa que era uma celula segura.

                Restauramos para o 'O'

                O(1)
                */
                else if (board[rowIndex][columnIndex] == 'T') {
                    board[rowIndex][columnIndex] = 'O'
                }
            }
        }
    }
}
