/*
Cheapest Flights Within K Stops

PROBLEMA

Temos:

- totalCities -> numero de cidades no mapa
- flights -> array de voos [origem, destino, preco]
- startingCity -> aeroporto de origem
- destinationCity -> aeroporto de destino
- maxStops -> conexoes (paradas) permitidas

Objetivo:

Descobrir o menor preco possivel para viajar da origem
ate o destino fazendo no maximo 'maxStops' paradas.

Regras:

1. Fazer 'k' paradas significa no maximo 'k + 1' voos.
2. Nao podemos usar rotas que excedam as paradas.
3. Se nao existir rota valida, retornar -1.

IDEIA PRINCIPAL

Esse e um problema de menor caminho em grafo com 
restricao de quantidade maxima de arestas (voos).

A melhor abordagem e o algoritmo de:
Bellman-Ford.

POR QUE BELLMAN-FORD FUNCIONA?

Ele processa as arestas (voos) em niveis (camadas):
- iteracao 1: acha menor custo usando ate 1 voo.
- iteracao 2: acha menor custo usando ate 2 voos.
- iteracao K+1: acha menor custo usando ate K+1 voos.

O PROBLEMA DO "RASCUNHO" (ARRAY TEMPORARIO)

Se usarmos um unico array de precos no loop, 
podemos encadear voos acidentalmente e "pular" 
o limite de paradas na mesma iteracao.

Para evitar isso, lemos os dados da rodada passada
(minCostToCity) e anotamos as novas ofertas em um 
"rascunho" temporario (currentLevelMinCost).

ESTRATEGIA

Step 1
Criar array de menor custo inicializado com Infinito.

Step 2
Definir o custo da cidade inicial como 0.

Step 3
Iniciar loop que roda (maxStops + 1) vezes.

Step 4
Criar o rascunho copiando o array principal.

Step 5
Para cada voo disponivel:
- Se origem e inatingivel (Infinito), ignorar.
- Se (custo origem + voo) for menor que o valor 
  no rascunho do destino, atualizar o rascunho.

Step 6
Ao fim dos voos da rodada, passar o rascunho a limpo,
copiando para o array principal.

Step 7
Apos as rodadas, se o destino for infinito, retornar -1.
Senao, retornar o menor custo.

COMPLEXIDADE

Tempo: O(n + m * k)

Onde:
- n = totalCities
- m = quantidade de voos
- k = maxStops

Explicacao:
A inicializacao custa O(n).
O loop roda (k + 1) vezes.
Dentro dele copiamos o array O(n) e iteramos O(m).
Total: O(k * (n + m)), simplificado para O(n + m * k).

Espaco: O(n)

Explicacao:
Dois arrays de tamanho 'n' (principal e rascunho).
*/

class Solution {
    fun findCheapestPrice(
        totalCities: Int, 
        flights: Array<IntArray>, 
        startingCity: Int, 
        destinationCity: Int, 
        maxStops: Int
    ): Int {
        
        /*
        STEP 1
        Inicializar os precos para todas as cidades como
        Infinito (Int.MAX_VALUE). Ainda nao sabemos chegar.

        Criacao -> O(n)
        */
        val minCostToCity = IntArray(totalCities) { Int.MAX_VALUE }

        /*
        STEP 2
        O custo para a cidade de partida e sempre 0.

        O(1)
        */
        minCostToCity[startingCity] = 0

        /*
        STEP 3
        Variavel para deixar claro quantas vezes o loop roda.
        Se maxStops = 1 parada, podemos pegar 2 voos.
        
        O(1)
        */
        val maxFlightsAllowed = maxStops + 1

        /*
        STEP 4
        Rodar o algoritmo maxFlightsAllowed vezes.
        Cada iteracao expande nosso alcance em +1 voo.

        Loop -> Roda (k + 1) vezes
        */
        repeat(maxFlightsAllowed) {

            /*
            STEP 5
            Criar o "rascunho" copiando os dados da rodada
            anterior. Isso impede usar rotas de multiplas
            conexoes dentro do mesmo loop.

            Copia -> O(n)
            */
            val currentLevelMinCost = minCostToCity.copyOf()

            /*
            STEP 6
            Avaliar todas as rotas existentes no mapa.

            Loop interno -> O(m) iteracoes
            */
            for (flight in flights) {
                
                /*
                STEP 7
                Extrair dados para variaveis claras (Clean Code).

                O(1)
                */
                val origin = flight[0]
                val destination = flight[1]
                val ticketPrice = flight[2]

                /*
                STEP 8
                Se a origem tem custo Infinito, nao chegamos nela
                nas rodadas anteriores. Ignorar voo.

                O(1)
                */
                if (minCostToCity[origin] == Int.MAX_VALUE) continue

                /*
                STEP 9
                Calcular o novo custo total: 
                custo ate origem + a passagem nova.

                O(1)
                */
                val costToReachOrigin = minCostToCity[origin]
                val totalCostToDest = costToReachOrigin + ticketPrice

                /*
                STEP 10
                Relaxamento:
                Se o novo custo for menor que o valor no rascunho
                do destino, atualizamos o rascunho!

                O(1)
                */
                if (totalCostToDest < currentLevelMinCost[destination]) {
                    currentLevelMinCost[destination] = totalCostToDest
                }
            }

            /*
            STEP 11
            Fim da rodada. Passamos a limpo jogando os valores 
            do rascunho para o placar principal.

            copyInto -> O(n)
            */
            currentLevelMinCost.copyInto(minCostToCity)
        }

        /*
        STEP 12
        Pegamos o custo registrado para o destino.

        O(1)
        */
        val finalCost = minCostToCity[destinationCity]

        /*
        STEP 13
        Se continuar Infinito, nao ha rotas validas.
        ATENCAO: Retornar -1 (e nao 0) como pede o problema.
        Senao, retornar o menor valor.

        O(1)
        */
        return if (finalCost == Int.MAX_VALUE) -1 else finalCost
    }
}