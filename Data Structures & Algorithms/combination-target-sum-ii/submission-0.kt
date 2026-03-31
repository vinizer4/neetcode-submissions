/*
Combination Sum II

PROBLEMA

Dado um array candidateNumbers que pode conter duplicados
e um targetSum,
precisamos retornar todas as combinações únicas
cujos valores somam exatamente targetSum.

Regras:

- cada elemento pode ser usado no máximo uma vez
- a resposta não pode conter combinações duplicadas

------------------------------------------------

EXEMPLO

Entrada:
candidateNumbers = [9,2,2,4,6,1,5]
targetSum = 8

Saída:
[
  [1,2,5],
  [2,2,4],
  [2,6]
]


IDEIA PRINCIPAL

Para cada posicao, temos duas decisoes:

- incluir o numero atual
- nao incluir o numero atual

Mas existe um detalhe importante:
o array pode ter valores duplicados.

Se nao tomarmos cuidado,
vamos gerar combinacoes repetidas.


COMO EVITAR DUPLICADOS?

Primeiro ordenamos o array.

Assim, valores iguais ficam lado a lado.

Exemplo:
[10,1,2,7,6,1,5] -> [1,1,2,5,6,7,10]

Quando decidimos NAO incluir um valor,
tambem pulamos todos os proximos duplicados iguais.

Por que?

Porque excluir o primeiro 1
ou excluir o segundo 1
nesse mesmo nivel de decisao
levaria ao mesmo conjunto de combinacoes

INTUICAO DO BACKTRACKING

A funcao recursiva recebe:

- currentIndex
- currentCombination
- currentSum

Em cada passo:

1. Se currentSum == targetSum
   -> nao vale a pena continuar

2. Se currentSum > targetSum
   -> nao vale a pena continuar

3. Se currentIndex chegou ao fim
   -> nao ha mais escolhas

4. Caso contrario:
   - incluir o numero atual
   - voltar
   - pular duplicados
   - excluir o numero atual

PODA IMPORTANTE

Se currentSum > targetSum,
paramos imediatamente.

Isso evita explorar caminhos impossiveis.

COMPLEXIDADE

Tempo: O(n * 2^n)

Explicacao:

- no pior caso, cada numero gera duas decisoes:
  incluir ou nao incluir
- isso gera uma arvore de recursao proxima de 2^n
- ao salvar uma combinacao valida, copiar a lista custa ate O(n)

Por isso:
Tempo total = O(n * 2^n)

Espaco: O(n)

Explicacao:

- a profundidade maxima da recursao e n
- currentCombination pode armazenar ate n elementos

Esse e o espaco usado pela solucao em si
*/

class Solution {
    fun combinationSum2(
        candidateNumbers: IntArray, 
        targetSum: Int
    ): List<List<Int>> {

        /*
        STEP 1
        Lista com todas as combinacoes validas.

        O(1)
        */
        val allValidCombinations = mutableListOf<List<Int>>()

        /*
        STEP 2
        Ordenar o array para agrupar duplicados.

        Isso e essencial para conseguir pular duplicados
        corretamente no ramo de exclusao

        sort() -> O(n log n)
        */
        candidateNumbers.sort()

        /*
        STEP 3
        Funcao de backtracking.

        currentIndex        -> posicao atual no array
        currentCombination  -> combinacao sendo construida
        currentSum          -> soma atual da combinacao
        */
        fun generateCombinationsBacktracking(
            currentIndex: Int,
            currentCombination: MutableList<Int>,
            currentSum: Int
        ) {

            /*
            STEP 4
            Se a soma atual for exatamente o alvo,
            encontramos uma combinacao valida.

            Precisamos copiar a lista antes de salvar.

            Copia -> O(n)
            */
            if (currentSum == targetSum) {
                allValidCombinations.add(ArrayList(currentCombination))
                return
            }

            /*
            STEP 5
            Se a soma ultrapassou o alvo,
            ou se acabaram os numeros
            encerramos este caminho

            O(1)
            */
            if (
                currentSum > targetSum ||
                currentIndex == candidateNumbers.size
            ) {
                return
            }

            /*
            STEP 6
            Escolher incluir o numero atual.

            add() -> O(1)
            */
            currentCombination.add(candidateNumbers[currentIndex])

            /*
            Descer na recursao usando o proximo indice.

            Usamos currentIndex + 1 porque cada numero
            so pode ser usado uma vez.

            Chamada recursiva
            */
            generateCombinationsBacktracking(
                currentIndex + 1,
                currentCombination,
                currentSum + candidateNumbers[currentIndex]
            )

            /*
            STEP 8
            Backtracking:
            desfazer a escolha anterior.

            removeAt(last) -> O(1)
            */
            currentCombination.removeAt(currentCombination.size - 1)

            /*
            STEP 9
            Pular todos os duplicados consecutivos do valor atual.

            Isso evita gerar combinacoes repetidas
            no ramo de exclusao.

            Exemplo:
            se estamos em um 1 e o proximo tambem e 1,
            excluir o primeiro ou excluir o segundo
            geraria o mesmo conjunto de combinacoes.
            */
            var nextUniqueIndex = currentIndex + 1

            while(
                nextUniqueIndex < candidateNumbers.size &&
                candidateNumbers[nextUniqueIndex] == candidateNumbers[currentIndex]
            ) {
                nextUniqueIndex++
            }

            /*
            STEP 10
            Explorar o caso de nao incluir o numero atual,
            ja pulando os duplicados iguais.

            Chamada recursiva
            */
            generateCombinationsBacktracking(
                nextUniqueIndex,
                currentCombination,
                currentSum
            )
        }

        /*
        STEP 11
        Iniciar o backtracking com:

        - indice 0
        - combinacao vazia
        - soma 0

        O(1)
        */
        generateCombinationsBacktracking(
            0,
            mutableListOf(),
            0
        )

        /*
        STEP 12
        Retornar todas as combinacoes validas

        O(1)
        */
        return allValidCombinations
    }
}
