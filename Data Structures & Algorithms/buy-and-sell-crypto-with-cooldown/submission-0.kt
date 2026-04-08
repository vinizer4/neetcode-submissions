/*
309. Best Time to Buy and SEll stock with cooldown

-- EXPLICAO --

Dado um array de precos, posso comprar e vender quantas vezes quiser,
mas apos vender preciso esperar 1 dia para comprar novamente.
Quero maximizar o lucro.

-- PADRAO ALGORITMICO --
Padrao escolhido: Dynamic Programming (DP com 3 estados)
Alternativas: Recursao pura (O(2^N)), DP bottom-up (O(N))
Escolhi DP bottom-up porque e O(N) e consome pouca memoria.

-- IDEIA PRINCIPAL E INTUICAO --
Para cada dia, mantenho 3 estados:
- hold: maior lucro se estou segundando uma acao hoje
- sold: maior lucro se vendi hoje (cooldown amanha)
- rest: maior lucro se estou em descanso hoje (posso comprar)

-- O "PITCH" E MULTIPLAS ABORDAGENS --
Pitch: "Modelando os estados (hold, sold, rest), consigo
transitar entre eles de forma eficiente, evitando recomputacao
e respeitando o cooldown"
Abordagem A: Recursao pura (TLE, O(2^N))
Abordagem B: DP bottom-up (O(N)), vencedora.

-- ESTRATEGIA PASSO A PASSO --
Passo 1: Inicializo os estados para o primeiro dia.
Passo 2: Para cada dia, atualizo os estados baseando
         nas transicoes validas.
Passo 3: No final, retorno o maximo entre sold e rest
         (nao posso terminar segurando acao)

-- COMPLEXIDADE --
Tempo: O(N) - Um loop simples sobre o array de precos.
Espaco: O(1) - Apenas variaveis escalares para os estados
*/

class Solution {
    fun maxProfit(prices: IntArray): Int {

        /* STEP 1
        Inicializo os estados para o primeiro dia.
        - hold: Se eu comprar no primeiro dia, lucro e -prices[0]
        - sold: Nao posso vender no primeiro dia, entao -infinito
        - rest: Se eu nao comprar, lucro e 0
        O(1)
        */
        if (prices.isEmpty()) return 0
        var hold = -prices[0]
        var sold = 0
        var rest = 0

        /* STEP 2
        Para cada dia, atualizo os estados:
        - Novo hold: ou continuo segurando, ou compro hoje (so posso comprar se estava em rest)
        - Novo sold: so posso vender se estava segurando ontem
        - Novo rest: ou continuo em descanso, ou acabei de vender ontem
        */
        for (dayIndex in 1 until prices.size) {
            val previousHold = hold
            val previousSold = sold
            val previousRest = rest


            hold = maxOf(previousHold, previousRest - prices[dayIndex])
            sold = previousHold + prices[dayIndex]
            rest = maxOf(previousRest, previousSold)
        }

        /* STEP 3
        O resultado final e o maximo entre sold e rest, pois
        terminar segurando acao nao e valido (nao realizei lucro)
        O(1)
        */
        return maxOf(sold, rest)
    }
}
