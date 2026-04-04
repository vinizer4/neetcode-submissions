/*
Palindrome Partitioning

PROBLEMA

Dada uma string InputText,
precisamos dividir essa string em substrings
de forma que TODAS as partes sejam palindromos.

Objetivo:

retornar todas as particoes validas possiveis.

EXEMPLO

Entrada:
inputText = "aab"

Saída:
[
  ["a", "a", "b"],
  ["aa", "b"]
]


IDEIA PRINCIPAL

Queremos tentar todos os possiveis "cortes" na string,
mas só seguimos por um caminho
se a substring escolhida no momento for um palindromo.

Ou seja:

- escolhemos uma substring
- verificamos se ela e palindromo
- se for, adicionamos essa parte a particao atual
- depois tentamos particionar o resto da string

Isso e backtracking.


INTUICAO

Se estamos em um indice startIndex:

- tentamos formar uma substring de startIndex ate endIndex
- se inputText[startIndex...endIndex] for palindromo,
  escolhemos essa substring
- entao a proximo decisao comeca em endIndex + 1

Depois, voltamos e tentamos outras possibilidades.


COMO A RECURSAO FUNCIONA

A funcao recursiva recebe:

- currentStartIndex -> inicio da proxima substring
- currentEndIndex   -> final que estamos expandindo

Ela funciona assim:

1. verifica se inputText[currentStartIndex...currentEndIndex] e palindromo
2. se for:
   - escolhe essa substring
   - reinicia a busca a partir do proximo indice
3. independentemente disse, tambem tenta expandir
   a substring atual, aumentando currentEndIndex

CASO BASE

Se currentEndIndex chegou ao final da string:

- se currentStartIndex tambem chegou ao final,
  significa que particionamos a string inteira corretamente
- entao salvamos a particao atual


ESTRATEGIA

Step 1
Criar:
- allPalindromoPartitions
- currentPartition

Step 2
Usar DFS + backtracking com:
- currentStartIndex
- currentEndIndex

Step 3
Se substring atual for palindromo:
- adicionar a particao
- DFS para o proximo trecho
- backtracking

Step 4
Tambem tentar aumentar o final da substring atual


COMPLEXIDADE

Tempo: O(n * 2ˆn)

Explicacao:

- no pior caso, a string pode ser particionada
  de muitas formas diferentes
- o numero de caminhos e proximo de 2ˆn
- cada verificacao/copia pode custar ate O(n)


Espaco: O(n)

Explicacao:

- a profundidade maxima da recursao é O(n)
- currentPartition pode armazenar ate n substrings
- a checagem de palindromo usa apenas variaveis locais

*/

class Solution {
    fun partition(inputText: String): List<List<String>> {
        
        /*
        STEP 1
        Lista com todas as particoes validas encontradas

        O(1)
        */
        val allPalindromePartitions = mutableListOf<List<String>>()

        /*
        STEP 2
        Particao atual sendo construida
        
        O(1)
        */
        val currentPartition = mutableListOf<String>()

        /*
        STEP 3
        DFS com backtracking.

        currentStartIndex   -> inicio da substring atual
        currentEndIndex     -> final da substring atual        
         */
        fun generatePalindromePartitionsDFS(
            currentStartIndex: Int,
            currentEndIndex: Int
        ) {

            /*
            STEP 4
            Se currentEndIndex chegou ao fim da string,
            verificamos se currentStartIndex tambem chegou.

            Se sim, significa que particionamos a string inteira
            usando apenas palindromos

            toList() -> O(n)
            */
            if (currentEndIndex >= inputText.length) {
                if (currentStartIndex == currentEndIndex) {
                    allPalindromePartitions.add(currentPartition.toList())
                }
                return
            }

            
            /*
            STEP 5
            Se a substring atual for palindromo
            podemos escolhe-la como parte valida.

            O(n) no pior caso por causa da checagem de palindromo
            */
            if (isPalindrome(inputText, currentStartIndex, currentEndIndex)) {

                /*
                STEP 6
                Adicionar a substring palindroma atual
                a particao

                substring() -> O(n)
                add() -> O(1)
                */
                currentPartition.add(
                    inputText.substring(currentStartIndex, currentEndIndex + 1)
                )

                /*
                STEP 7
                Reiniciar a busca a partir do proximo indice,
                pois acabamos de fazer um corte.

                Chamada recursiva
                */
                generatePalindromePartitionsDFS(
                    currentEndIndex + 1,
                    currentEndIndex + 1
                )

                /*
                STEP 8
                Backtracking:
                remover a ultima substring escolhida
                para tentar outras possibilidades

                removeAt(lastIndex) -> O(1)
                */
                currentPartition.removeAt(currentPartition.size - 1)
            }

            /*
            STEP 9
            Tambem tentamos expandir a substring atual,
            aumentando o final dela.

            Isso representa o caso de ainda nao cortar aqui.

            Chamada recursiva
            */
            generatePalindromePartitionsDFS(
                currentStartIndex,
                currentEndIndex + 1
            )
        }

        /*
        STEP 10
        Iniciar DFS a partir do indice 0.

        O(1)
        */
        generatePalindromePartitionsDFS(0, 0)

        /*
        STEP 11
        Retornar todas as particoes validas.

        O(1)
        */
        return allPalindromePartitions
    }

    /*
    STEP 12
    Verificar se inputText[leftIndex...rightIndex] e palindromo.

    Usamos dois ponteiros caminhando para o centro.

    Tempo: O(n) no pior caso
    Espaco: O(1)
    */
    private fun isPalindrome(
        inputText: String,
        leftIndex: Int,
        rightIndex: Int
    ): Boolean {
        var leftPointer = leftIndex
        var rightPointer = rightIndex

        while (leftPointer < rightPointer) {
            if (inputText[leftPointer] != inputText[rightPointer]) {
                return false
            }
            leftPointer++
            rightPointer--
        }

        return true
    }
}
