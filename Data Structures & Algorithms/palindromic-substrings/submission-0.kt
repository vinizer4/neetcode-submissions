/**
2 * 647. Palindromic Substrings
3 *
4 * --- PERGUNTAS ESCLARECEDORAS ---
5 * 1. O input pode ser uma string vazia? (Pelos exemplos, nao)
6 * 2. Os caracteres podem ser maiusculos? (Apenas minusculos)
7 * 3. O resultado deve contar substrings repetidas? (Sim, cada ocorrencia)
8 *
9 * --- EXPLICACAO DO PROBLEMA ---
10 * Dada uma string s, precisamos retornar quantas substrings
11 * palindromicas existem nela. Uma substring palindromica e um
12 * trecho contiguo que le igual de tras para frente.
13 *
14 * --- PADRAO ALGORITMICO ---
15 * Expand Around Center (Expansao a partir do centro).
16 *
17 * --- IDEIA PRINCIPAL E INTUICAO ---
18 * Para cada possivel centro de palindromo (todo caractere unico
19 * e todo par de caracteres adjacentes), expandimos para os lados
20 * enquanto os caracteres da esquerda e direita forem iguais.
21 * Cada expansao valida conta como um novo palindromo.
22 *
23 * --- O "PITCH" E MULTIPLAS ABORDAGENS ---
24 * Pitch: "Vou iterar por cada possivel centro (2*N-1 centros)
25 * e usar dois ponteiros para expandir para os lados. Cada vez
26 * que os caracteres baterem, contabilizo um palindromo. Isso
27 * resolve o problema em tempo quadratico sem overhead de memoria."
28 * * Brute Force: Gerar todas as substrings (O(N^2)) e checar se
29 * cada uma e palindromo (O(N)). Custa O(N^3) e da TLE para N=1000.
30 * * Solucao Otimizada: Expandir pelo centro, O(N^2), sem DP.
31 *
32 * --- ESTRATEGIA ---
33 * - Passo 1: Inicializo um contador global.
34 * - Passo 2: Faco um laco percorrendo cada indice da string.
35 * - Passo 3: Expando considerando o indice como centro impar.
36 * - Passo 4: Expando considerando o indice como centro par.
37 * - Passo 5: Retorno o somatorio de todas as expansoes validas.
38 *
39 * --- COMPLEXIDADE PREVISTA ---
40 * Tempo: O(N^2) - Para cada um dos N centros, a expansao
41 * pode percorrer ate N caracteres no pior caso. N * N = N^2.
42 * Espaco: O(1) - Apenas variaveis inteiras de contagem e ponteiros.
43 * Por que passa: N=1000, logo O(N^2) gera ate 1 milhao de operacoes.
44 */

class Solution {
    fun countSubstrings(s: String): Int {
        
        /*
        STEP 1
        Vou inicializar a nossa variavel principal que vai
        acumular o total de palindromos encontrados

        O(1)
        */
        var totalPalindromes = 0
        val stringLength = s.length

        /*
        STEP 2
        Vou percorrer cada indice da string.
        O(N^2) total laco O(N) * expansao O(N)
        */
        for (centerIndex in 0 until stringLength) {

            /*
            STEP 3
            Primeiro, tento expandir assumindo que o palindromo
            tem tamanho impar (o centro e apenas uma letra).
            Ex: na palavra "aba", o centro e o 'b'.
            */
            totalPalindromes += expandFromCenter(s, centerIndex, centerIndex)

            /*
            STEP 5
            Depois, tento expandir assumindo que o palindromo
            tem tamanho par (o centro esta entre duas letras).
            Ex: na palavra "abba" o centro e o espaco entre os 'b's
            */ 
            totalPalindromes += expandFromCenter(s, centerIndex, centerIndex + 1)
        }

        /*
        STEP 5
        Apos varres todos os centros possiveis, basta retornar
        o nosso acumulador
        */
        return totalPalindromes
    }

    /*
    Funcao auxiliar dedicada apenas a fazer a expansao
    e retornar quantos palindromos validos achou daquele centro
    */
    private fun expandFromCenter(s: String, leftStart: Int, rightStart: Int): Int {
        var left = leftStart
        var right = rightStart
        var count = 0

        /*
        STEP 6
        Vou afastar os ponteiros do centro.
        Enquanto nao batermos nas bordas da string E e os caracteres
        das pontas forem iguais, significa que achamos um palindromo valido.

        O(N)
        */
        while (left >= 0 && right < s.length && s[left] == s[right]) {
            count++
            left--
            right++
        }

        return count
    }
}
