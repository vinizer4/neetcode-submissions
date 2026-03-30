/*
Permutations

PROBLEMA

Dado um array nums com inteiros unicos,
precisamos retornar todas as permutacoes possiveis

Uma permutacao e uma ordem diferente dos mesmos elementos

EXEMPLO

Entrada:
nums = [1, 2, 3]

Saída:
[
  [1,2,3],
  [1,3,2],
  [2,1,3],
  [2,3,1],
  [3,1,2],
  [3,2,1]
]


IDEIA PRINCIPAL

Vamos construir as permutacoes a partir de permutacoes menores.

Se tivermos a permutacao menor:
[2,3]

e quisermos inserir o numero 1,
podemos coloca-lo em todas as posicoes possiveis:

- [1,2,3]
- [2,1,3]
- [2,3,1]

Fazendo isso para todas as permutacoes menores,
geramos todas as permutacoes do array completo.


INTUICAO DA RECURSAO

A funcao funciona assim:

1. pega o primeiro numero
2. calcula recursivamente todas as permutacoes do restante
3. insere o primeiro numero em todas as posicoes
   de cada permutacao menor

CASO BASE

Se o array estiver vazio,
a unica permutacao possivel e: []

Isso e necessario para a recursao funcionar,
porque permite comecar a montar as listas de volta


ESTRATEGIA

Step 1
Se nums estiver vazio, retornar [[]].

Step 2
Calcular recursivamente as permutacoes
do subarray sem o primeiro elemento.

Step 3
Para cara permutacao menor:
- Inserir o primeiro numero em cada posicao possivel

Step 4
Retornar todas as novas permutacoes geradas.


COMPLEXIDADE

Tempo: O(n! * n^2)

Explicacao:

- existem n! permutacoes no total
- para cada permutacao, fazemos insercoes/copias de listas
- cada copia/insercao pode custar ate O(n)

De forma mais detalhada:
- gerar todas as permutacoes ja exige n!
- para cada uma, inserimos em ate n posicoes
- cada insercao em lista custa ate O(n)

Por isso essa abordagem recursiva com copias
fica em O(n! * n^2)

Espaco auxiliar:
depende da profundidade de recursao e das listas intermediarias

Espaco da saida:
O(n! * n)

porque armazenamos n! permutacoes,
cada uma com tamanho n
*/


class Solution {
    fun permute(inputNumbers: IntArray): List<List<Int>> {
        
        /*
        STEP 1
        Caso base:
        se o array estiver vazio,
        retornamos uma lista contendo uma lista vazia

        Isso representa a unica permutacao possivel
        de um conjunto vazio

        O(1)
        */
        if (inputNumbers.isEmpty()) {
            return listOf(listOf())
        }

        /*
        STEP 2
        Pegar recursivamente as permutacoes
        dos numeros restantes.
        
        Exemplo:
        se inputNumbers = [1,2,3],
        chamamos permute ([2,3])

        sliceArray() -> O(n)
        chamada recursiva
        */
        val smallerPermutations = permute(
            inputNumbers.sliceArray(1 until inputNumbers.size)
        )

        /*
        STEP 3
        Lista que armazenara as novas permutacoes

        O(1)
        */
        val allPermutations = mutableListOf<List<Int>>()

        /*
        STEP 4
        Para cada permutacao menor,
        inserir o primeiro numero em todas as posicoes possiveis.

        Loop enterno -> quantidade de permutacoes menores
        */
        for (smallerPermutation in smallerPermutations) {

            /*
            STEP 5
            Inserir o primeiro numero em cada posicao
            de 0 ate o tamanho da permutacao.

            Se smallerPermutation tem tamanho m,
            existem m + 1 posicoes possiveis.

            Loop interno -> O(n)
            */
            for (insertPosition in 0..smallerPermutation.size) {

                /*
                STEP 6
                Fazer uma copia mutavel da permutacao atual,
                para nao alterar a original.

                O(n)
                */
                val permutationCopy = smallerPermutation.toMutableList()

                /*
                STEP 7
                Inserir o primeiro numero do array original
                na posicao atual

                add(index, element) -> O(n)
                */
                permutationCopy.add(insertPosition, inputNumbers[0])

                /*
                STEP 8
                Adicionar a nova permutacao ao resultado.

                add() -> O(1) amortizado
                */
                allPermutations.add(permutationCopy)
            }
        }

        /*
        STEP 9
        Retornar todas as permutacoes geradas.

        O(1)
        */
        return allPermutations
    }
}
