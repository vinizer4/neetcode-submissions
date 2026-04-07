/*
House Robber II (Roubo a Casas II)

PROBLEMA

Temos:
- nums -> um array onde cada valor representa o dinheiro em uma casa.
- As casas estão organizadas em um CÍRCULO.

Objetivo:
Roubar o valor máximo possível.

Regras:
1. O sistema de alarme dispara se duas casas VIZINHAS forem roubadas.
2. Como as casas estão em círculo, a PRIMEIRA e a ÚLTIMA casa são vizinhas.
   Portanto, você NÃO pode roubar a primeira e a última na mesma noite.

EXEMPLO

nums = [2, 9, 8, 3, 6]

Resposta:
15

Explicação:
Você não pode roubar a casa 0 (valor 2) e a casa 4 (valor 6) juntas.
O melhor caminho é roubar a casa 1 (valor 9) e a casa 4 (valor 6).
Total = 9 + 6 = 15.

IDEIA PRINCIPAL

Como a restrição principal é o formato circular (primeira e última se tocam),
podemos "quebrar" o círculo e transformar esse problema difícil em dois
problemas fáceis e em linha reta (House Robber I).

Se não podemos roubar a 1ª e a última ao mesmo tempo, temos apenas duas 
situações possíveis no melhor cenário:
- Situação A: Nós IGNORAMOS a primeira casa (podemos roubar da 2ª até a última).
- Situação B: Nós IGNORAMOS a última casa (podemos roubar da 1ª até a penúltima).

O resultado final será simplesmente o maior valor entre a Situação A e a B.

OTIMIZAÇÃO DE ESPAÇO (In-Place / Janela Deslizante)

Para resolver uma rua em linha reta, não precisamos criar um array inteiro
para guardar o histórico (DP Clássica).
Ao olhar para uma casa, só precisamos saber:
1. Qual o melhor lucro que eu tinha até DUAS casas atrás? (rob1)
2. Qual o melhor lucro que eu tinha até UMA casa atrás? (rob2)

Usando apenas essas duas variáveis e atualizando-as a cada passo, 
reduzimos o uso de memória de O(n) para O(1).

ESTRATÉGIA (PASSO A PASSO)

Step 1: Lidamos com o caso isolado onde existe apenas 1 casa.
Step 2: Chamamos uma função auxiliar (que resolve a rua reta) ignorando
        a primeira casa.
Step 3: Chamamos a mesma função auxiliar ignorando a última casa.
Step 4: A função auxiliar usa `rob1` e `rob2` para rastrear os valores.
Step 5: Para cada casa na rua, calculamos: compensa mais roubar a casa atual
        somada ao lucro de duas casas atrás (rob1 + atual), ou é melhor pular 
        essa casa e manter o lucro da casa anterior (rob2)?
Step 6: Atualizamos `rob1` e `rob2` para o próximo passo do loop.
Step 7: Retornamos o máximo entre o resultado do Step 2 e do Step 3.

COMPLEXIDADE

Tempo: O(n)
Onde 'n' é o número de casas. Nós percorremos as casas essencialmente 
duas vezes (uma para cada situação), o que é O(2n), simplificado para O(n).

Espaço: O(1)
Em vez de usar `copyOfRange` (que criaria novos arrays e gastaria O(n) de memória),
passamos índices `start` e `end` para a função auxiliar. 
Como usamos apenas duas variáveis inteiras no cálculo, o espaço é O(1) real.
*/

class Solution {
    fun rob(houses: IntArray): Int {
        
        /*
        Step 1
        Se existe apenas 1 casa no bairro, nao ha vizinhos.
        O melhor lucro e rouba-la diretamente.

        Tempo: O(1)
        Espaco: O(1)
        */
        if (houses.size == 1) return houses[0]

        /*
        Step 2
        Cenario A: Ignoramos a cada no indice 0.
        Avaliamos a casa 1 ate a ultima.

        Tempo: O(n) - A funcao auxiliar percorrera (n - 1) elementos.
        Espaco: O(1) - Passamos apenas referencias de indice.
        */
        val scenarioSkipFirst = robLinearStreet(houses, start = 1, end = houses.size - 1)

        /*
        Step 3
        Cenario B: Ignoramos a ultima casa da rua.
        Avaliamos da casa 0 ate a pneultima.

        Tempo: O(n) - A funcao auxiliar percorrea (n - 1) elementos
        Espaco: O(1)
        */
        val scenarioSkipLast = robLinearStreet(houses, start = 0, end = houses.size - 2)

        /*
        STEP 7
        O resultado final sera a estrategia que rendeu mais dinheiro.

        Tempo: O(1) - funcao maxOf com dois inteiros.
        Espaco: O(1)
        */
        return maxOf(scenarioSkipFirst, scenarioSkipLast)
    }

    // Funcao auxiliar que resolve o House Robber classico em linha reta.
    private fun robLinearStreet(houses: IntArray, start: Int, end: Int): Int {

        /*
        Step 4
        'maxTwoHousesBack': Lucro maximo garantido ate 2 casas atras.
        'maxOneHouseBack': Lucro maximo garantido ate 1 casa atras.

        Tempo: O(1)
        Espaco: O(1)
        */
        var maxTwoHousesBack = 0
        var maxOneHouseBack = 0

        for (i in start..end) {
            val currentHouseValue = houses[i]

            /*
            Step 5
            Opcao 1: Roubar a casa atual (exige usar o lucro de 2 casas atras)
            Opcao 2: Pular a casa atual (mantem o lucro de 1 casa atras)

            Tempo: O(1) - operacoes matematicas e de decisao locais.
            Espaco: O(1)
            */
            val maxIfRobCurrent = maxTwoHousesBack + currentHouseValue
            val maxIfSkipCurrent = maxOneHouseBack

            val newMaxLucro = maxOf(maxIfRobCurrent, maxIfSkipCurrent)

            /*
            STEP 6
            Avancamos a "janela deslizante" para o proximo loop.
            O que era "1 casa atras" vira "2 casas atras".
            O que novo lucro descoberto vira o "1 casa atras"

            Tempo: O(1) - Atribuicao de variaveis.
            Espaco: O(1)
            */
            maxTwoHousesBack = maxOneHouseBack
            maxOneHouseBack = newMaxLucro
        }

        return maxOneHouseBack
    }
}
