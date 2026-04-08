/*
Longest Palindromic Substring (Maior Substring Palindrômica)

PROBLEMA

Temos:
- inputString -> Uma string contendo letras e números.

Objetivo:
Encontrar o maior "pedaço" contínuo (substring) dentro da 'inputString' 
que seja um palíndromo (leia-se igual de trás para frente).

EXEMPLO

inputString = "ababd"

Resposta:
"bab" ou "aba" (ambas estão corretas, pois têm tamanho 3).

IDEIA PRINCIPAL (Programação Dinâmica)

A forma "força bruta" testaria todas as substrings possíveis, o que
recalcularia as mesmas coisas várias vezes. 
Exemplo: Para saber se "cabac" é palíndromo, basta ver se as pontas 
são iguais ('c' == 'c') E se o miolo ("aba") já era um palíndromo.

Criamos uma tabela (matriz) de memória (DP):
isSubstringPalindrome[startIndex][endIndex] = true (Se for palíndromo).

Regra de Ouro:
A substring é palíndromo SE:
1. As letras das pontas forem iguais.
2. E o miolo também for palíndromo na nossa matriz de memória.

Exceção (Tamanhos 1, 2 e 3):
Se o tamanho for 3 ou menos, basta as pontas serem iguais, pois o
miolo será uma letra só ou não existirá (ex: "a", "aa", "aba").

ESTRATÉGIA (PASSO A PASSO)

Step 1: Criar a tabela 2D preenchida com 'false'.
Step 2: Guardar o tamanho e a posição de início do maior palíndromo.
Step 3: Iterar o índice de início (startIndex) DE TRÁS PARA FRENTE.
Step 4: Iterar o índice de fim (endIndex) do início até o final da string.
Step 5: Checar se as pontas combinam.
Step 6: Checar se é pequena (<= 3) ou se o miolo já é palíndromo na DP.
Step 7: Se sim, marca na tabela como true.
Step 8: Atualiza a variável de maior palíndromo se for um novo recorde.
Step 9: Retorna a fatia (substring) da palavra original.

COMPLEXIDADE GERAL

Tempo: O(n²) -> Usamos dois loops aninhados que percorrem a string.
Espaço: O(n²) -> Criamos uma matriz N x N para memorizar os resultados.
*/

class Solution {
    fun longestPalindrome(inputString: String): String {
        
        /*
        STEP 1
        Guardamos o tamanho da string para uso nos loops.
        Tempo: O(1)
        Espaço: O(1)
        */
        val stringLength = inputString.length

        /*
        STEP 2
        Tabela DP. isSubstringPalindrome[i][j] será true se a 
        substring de 'i' até 'j' for um palíndromo.
        
        Tempo: O(n²) - Inicializar uma matriz n x n.
        Espaço: O(n²) - Ocupa espaço na memória proporcional ao quadrado de n.
        */
        val isSubstringPalindrome = Array(stringLength) { BooleanArray(stringLength) }

        /*
        STEP 3
        Variáveis para rastrear o recorde do maior palíndromo achado.
        
        Tempo: O(1)
        Espaço: O(1)
        */
        var maximumPalindromeLength = 0
        var bestStartIndex = 0

        /*
        STEP 4
        Loop de Fora (startIndex): Vamos de trás para frente.
        Por que? Para saber se "cabac" (tamanho 5) é palíndromo, 
        precisamos que "aba" (tamanho 3) já tenha sido calculado.
        Indo de trás para frente, calculamos os miolos internos primeiro.
        
        Tempo: O(n)
        Espaço: O(1)
        */
        for (startIndex in stringLength - 1 downTo 0) {
            
            /*
            STEP 5
            Loop de Dentro (endIndex): Começa onde o start está e vai 
            até o fim da string. (As substrings sempre crescem para a direita).
            
            Tempo: O(n) - Junto com o loop externo, O(n²) execuções.
            Espaço: O(1)
            */
            for (endIndex in startIndex until stringLength) {
                
                /*
                STEP 6
                Regra 1: As letras das pontas são iguais?
                Tempo: O(1)
                */
                val doEndsMatch = inputString[startIndex] == inputString[endIndex]

                /*
                STEP 7
                Regra 2: A string é pequena (tamanho <= 3) OU 
                o miolo (startIndex+1 até endIndex-1) já está marcado na tabela 
                como verdadeiro?
                
                Tempo: O(1)
                */
                val isStringSmall = (endIndex - startIndex) <= 2
                
                val isInnerSubstringPalindrome = isStringSmall || 
                    isSubstringPalindrome[startIndex + 1][endIndex - 1]

                /*
                STEP 8
                Se atende às regras, achamos um palíndromo!
                Marcamos a tabela e verificamos se quebrou o recorde.
                
                Tempo: O(1)
                Espaço: O(1)
                */
                if (doEndsMatch && isInnerSubstringPalindrome) {
                    isSubstringPalindrome[startIndex][endIndex] = true
                    
                    val currentSubstringLength = endIndex - startIndex + 1
                    
                    if (currentSubstringLength > maximumPalindromeLength) {
                        maximumPalindromeLength = currentSubstringLength
                        bestStartIndex = startIndex
                    }
                }
            }
        }

        /*
        STEP 9
        Recortamos a string original usando o índice inicial salvo e o comprimento.
        
        Tempo: O(n) - A função substring copia os caracteres.
        Espaço: O(n) - Uma nova string é alocada para o retorno.
        */
        val bestEndIndex = bestStartIndex + maximumPalindromeLength
        return inputString.substring(bestStartIndex, bestEndIndex)
    }
}