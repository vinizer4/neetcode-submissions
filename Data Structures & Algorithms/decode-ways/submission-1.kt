/*
91. Decode Ways

--- PERGUNTAS ESCLARECEDORAS ---
1. O input pode ser string vazia? (Nao ha exemplo, mas pode)
2. Pode conter zeros? (Sim, precisa tratar "06" como invalido)
3. O resultado deve ser Int? (Sim, cabe em 32 bits)
4. O input contem apenas digitos? (Sim)

--- EXPLICACAO DO PROBLEMA ---
Dado um string de digitos, cada digito ou par de digitos pode ser mapeado para uma letra ('1'->'A', ..., '26'->'Z'). 
O objetivo e contar de quantas formas diferentes podemos decodificar o string inteiro, respeitando que '0' so pode ser parte de '10' ou '20'.

--- PADRAO ALGORITMICO ---
Dynamic Programming (DP) com bottom-up (tabulacao).

--- IDEIA PRINCIPAL E INTUICAO ---
Vamos usar DP onde dp[i] representa o numero de formas de decodificar o prefixo de tamanho i do string.
Para cada posicao, podemos considerar 1 digito (se for valido) e 2 digitos (se for valido entre 10 e 26).
Se o caractere atual for '0', so pode ser parte de '10' ou '20', senao a decodificacao e invalida.

--- O "PITCH" E MULTIPLAS ABORDAGENS ---
Pitch: "Vou usar DP iterativo, onde para cada posicao vejo se posso formar letra com 1 ou 2 digitos, acumulando as formas validas. Isso garante O(N) tempo e O(N) espaco, sem recursao."

Brute Force: Recursao pura testando todos os caminhos, mas repete subproblemas e da TLE para N grande.

Solucao Otimizada: DP bottom-up, sem recursao, apenas array ou duas variaveis.

--- ESTRATEGIA ---
- Passo 1: Tratar caso base (string vazio ou comecando com '0' retorna 0).
- Passo 2: Criar dp de tamanho stringLength+1, dp[0]=1 (string vazia tem 1 forma).
- Passo 3: Para cada posicao, se o digito atual for valido, soma dp[i-1]. Se os dois ultimos digitos formam numero valido (10-26), soma dp[i-2].
- Passo 4: Retornar dp[stringLength].

--- COMPLEXIDADE PREVISTA ---
Tempo: O(N) - Um laco sobre o tamanho da string.
Espaco: O(N) - Array dp, pode ser otimizado para O(1) usando apenas duas variaveis.
Por que passa: N <= 100, O(100) e instantaneo na JVM.
*/

class Solution {
    fun numDecodings(inputString: String): Int {
        /* STEP 1
        Tratar caso base: string vazia ou iniciando com '0' nao tem decodificacao valida.
        [COMPLEXITY]: O(1)
        */
        val stringLength = inputString.length
        if (stringLength == 0 || inputString[0] == '0') return 0

        /* STEP 2
        Criar array dp onde dp[i] representa o numero de formas de decodificar o prefixo de tamanho i.
        Inicializo dp[0]=1 (string vazia tem 1 forma) e dp[1]=1 (se o primeiro digito for valido).
        [COMPLEXITY]: O(N) espaco
        */
        val dp = IntArray(stringLength + 1)
        dp[0] = 1
        dp[1] = 1

        /* STEP 3
        Iterar do segundo caractere ate o final.
        Para cada posicao, verifico se o digito atual e valido (diferente de '0').
        Se sim, adiciono dp[i-1].
        Depois, verifico se os dois ultimos digitos formam um numero entre 10 e 26.
        Se sim, adiciono dp[i-2].
        [COMPLEXITY]: O(N)
        */
        for (currentIndex in 2..stringLength) {
            val oneDigit = inputString.substring(currentIndex - 1, currentIndex)
            val twoDigits = inputString.substring(currentIndex - 2, currentIndex)

            // Se o digito atual for diferente de '0', pode ser decodificado sozinho
            if (oneDigit != "0") {
                dp[currentIndex] += dp[currentIndex - 1]
            }

            // Se os dois ultimos digitos formam numero valido entre 10 e 26
            val twoDigitsValue = twoDigits.toInt()
            if (twoDigitsValue in 10..26) {
                dp[currentIndex] += dp[currentIndex - 2]
            }
        }

        /* STEP 4
        Retornar o total de formas para o string inteiro.
        [COMPLEXITY]: O(1)
        */
        return dp[stringLength]
    }
}

