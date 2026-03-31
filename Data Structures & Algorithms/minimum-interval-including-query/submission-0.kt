/*
Minimum Interval to Include Each Query

PROBLEMA

Temos:

- intervals, onde cada intervalo e [left, right]
- queries, onde cada valor representa um ponto

Para cada query, precisamos encontrar:

o menor intervalo que contenha esse ponto

Ou seja, queremos o menor intervalo [left, right] tal que:

left <= query <= right

Se nao existir intervalo cobrindo a query,
a resposta deve ser -1.

EXEMPLO

intervals = [[1,3],[2,3],[3,7],[6,6]]
queries   = [2,3,1,7,6,8]

Resposta:
[2,2,3,5,1,-1]


IDEIA PRINCIPAL

Para cada query, queremos rapidamente descobrir
qual e o menor intervalo valido.

A solucao eficiente faz isso assim:

1. ordenar os intervalos pelo inicio
2. ordenar as queries
3. conforme as queries aumentam:
   - adicionar todos os intervalor cujo inicio ja e <= query atual
4. remover da heap os intervalos que ja nao cobrem mais a query
5. o topo da min-heap sera sempre o menor intervalo valido

POR QUE ORDENAR AS QUERIES?

Se processarmos queries em ordem crescente,
nao precisamos recomecar do zero para cada uma.

A medida que a query cresce:

- mais intervalos passam a ser elegiveis para entrar
- alguns intervalos antigos deixam de servir

Isso permite fazer um sweep linear com heap.

POR QUE USAR MIN-HEAP?

A heap guarda apenas intervalos que ja comecaram,
e e ordenada pelo tamanho do intervalo

Entao:

- heap.peek() sempre e o menor intervalo disponivel
- se esse intervalo ainda cobre a query,
  ele ja e a resposta


O QUE A HEAP ARMAZENA?

Para cada intervalo, guardamos:

(intervalLengthm intervalEnd)

intervalLength:
-> usado para priorizar o menor intervalo


intervalEnd:
-> usado para saber se o intervalo ainda cobre a query atual


ESTRATEGIA

Step 1
Ordenar intervals pelo inicio.

Step 2
Ordenar queries, mas preservando o indice original

Step 3
Percorrer as queries em ordem crescente.

Step 4
Adicionar a heap todos os intervalos
com inicio <= query atual.

Step 5
Remover da heap todos os intervalos
cujo fim < query atual.

Step 6
Se a heap nao estiver vazia:
- o topo e o menor intervalo valido

Caso contrario:
- resposta = -1

Step 7
Salvar a resposta no indice original da query.


COMPLEXIDADE

Tempo: O(n log n + m log m)

Onde:
- n = quantidade de intervalos
- m = quantidade de queries

Explicacao:

- ordenar os intervalor -> O(n log n)
- ordenar as queries -> O(m log m)
- cada intervalo entra e sai da heap no maximo uma vez
- cada operacao de heap custa O(log n)

Espaco: O(n + m)

Explicacao:

- armazenamos queries com seus indices originais
- a heap pode armazenar ate n intervalos
- o array de resposta tem tamanho m
*/

class Solution {
    fun minInterval(
        intervals: Array<IntArray>, 
        queries: IntArray
    ): IntArray {
        
        /*
        STEP 1
        Ordenar os intervalos pelo valor inicial.

        Assim conseguimos adicionar intervalos
        conforme as queries avancam.

        sortBy() -> O(n log n)
        */
        intervals.sortBy { interval -> interval[0] }

        /*
        STEP 2
        Associar cada query ao seu indice original
        e ordenar pelo valor da query

        Estrutura:
        (queryValue, originalIndex)

        withIndex/map/sortedBy -> O(m log m)
        */
        val sortedQueriesWithOriginalIndex = queries.withIndex()
                .map { indexedValue -> indexedValue.value to indexedValue.index }
                .sortedBy { queryWithIndex -> queryWithIndex.first }

        /*
        STEP 3
        Min-heap ordenada pelo tamanho do intervalo.

        Cada entrada sera:
        Pair(intervalLength, intervalEnd)

        intervalLength  -> usado para escolher o menor intervalo
        intervalEnd     -> usado para validar se ainda cobre a query

        Criacao -> O(1)
        */
        val availableIntervalsMinHeap = PriorityQueue<Pair<Int, Int>>(
            compareBy { heapEntry -> heapEntry.first }
        )

        /*
        STEP 4
        Array de resposta final.

        answerByOriginalQueryIndex[i] guarda a resposta
        da query que estava originalmente na posicao i.

        Criacao -> O(m)
        */
        val answerByOriginalQueryIndex = IntArray(queries.size)

        /*
        STEP 5
        Ponteiro para percorrer os intervalos ja ordenados.

        intervalPointer aponta para o proximo intervalo
        que ainda nao foi considerado

        O(1)
        */
        var intervalPointer = 0

        /*
        STEP 6
        Processar as queries em ordem crescente.

        Loop -> O(m)
        */
        for ((queryValue, originalQueryIndex) in sortedQueriesWithOriginalIndex) {

            /*
            STEP 7
            Adicionar a heap todos os intervalos
            cujo inicio ja e <= query atual

            Isso significa:
            esses intervalos ja comecaram
            e podem potencialmente cobrir a query

            Cada intervalo entra uma vez na heap
            offer() -> O(log n)
            */
            while(
                intervalPointer < intervals.size &&
                intervals[intervalPointer][0] <= queryValue
            ) {
                val intervalStart = intervals[intervalPointer][0]
                val intervalEnd = intervals[intervalPointer][1]
                val intervalLength = intervalEnd - intervalStart + 1

                availableIntervalsMinHeap.offer(intervalLength to intervalEnd)
                intervalPointer++
            }

            /*
            STEP 8
            Remover da heap todos os intervalos
            que nao cobrem mais a query atual.

            Se intervalEnd < queryValue,
            entao esse intervalo terminou antes da query.

            poll() -> O(log n)
            */
            while (
                availableIntervalsMinHeap.isNotEmpty() &&
                availableIntervalsMinHeap.peek().second < queryValue
            ) {
                availableIntervalsMinHeap.poll()
            }

            /*
            STEP 9
            Se a heap nao estiver vazia
            o topo e o menor intervalo valido

            Caso contrario, a resposta e -1

            peek() -> O(1)
            */
            answerByOriginalQueryIndex[originalQueryIndex] =
                if (availableIntervalsMinHeap.isNotEmpty()) {
                    availableIntervalsMinHeap.peek().first
                } else {
                    - 1
                }
        }

        /*
        STEP 10
        Retornar o array final de respostas
        na ordem original das queries

        O(1)
        */
        return answerByOriginalQueryIndex
    }
}
