/*
Generate Parentheses

PROBLEMA

Dado um inteiro pairCount,
precisamos gerar todas as combinações válidas
de parênteses com exatamente pairCount pares.

Uma combinação válida é aquela em que:

- nunca fechamos mais parênteses do que abrimos
- ao final, a quantidade de '(' e ')' é a mesma

------------------------------------------------

EXEMPLO

Entrada:
pairCount = 3

Saída:
[
  "((()))",
  "(()())",
  "(())()",
  "()(())",
  "()()()"
]


IDEIA PRINCIPAL

Em vez de gerar todas as string possiveis
e depois verificar quais sao validas,
vamos construir apenas strings validas desde o inicio.


REGRAS IMPORTANTES

Durante a construcao da string:

1. So podemos adicionar '('
   se ainda tivermos aberturas disponiveis

   openCount < pairCount

2. So podemos adicionar ')'
   se isso nao quebrar a validade da string

   closeCount < openCount

Ou seja:
- nunca podemos fechar antes de abrir
- nunca podemos abrir mais do que pairCount

INTUICAO DO BACKTRACKING

A cada passo, tentamos adicionar:

- '(' se ainda pudermos abrir
- ')' se ainda podermos fechar sem invalidar

Quando usamos exatamente:

- pairCount aberturas
- pairCount fechamentos

entao entramos uma resposta valida completa

POR QUE ISSO FUNCIONA?

Porque toda string construida respeita a validade
em cada etapa.

Assim, evitamos explorar caminhos invalidos
e geramos apenas respostas corretas


ESTRATEGIA

Step 1
Comecar com uma string vazia

Step 2
User backtracking com:
- openCount
- closeCount

Step 3
Se openCount == pairCount e closeCount == pairCount:
- salvar a combinacao atual

Step 4
Se openCount < pairCount:
- adicionar '(' e seguir

Step 5
Se closeCount < openCount:
- adicionar ')' e seguir

Step 6
Apos cada escolha, desfazer a alteracao para explorar outros caminhos


COMPLEXIDADE

Tempo: O(4^n / sqrt(n))

Explicacao:

- o numero de combinacoes validas de parenteses
  e dado pelo n-esimo numero de Catalan
- esse valor e aproximandamente O(4^n / sqrt(n))

Como construimos apenas combinacoes validas,
essa e a complexidade dominante da geracao

Espaco: O(n)

Explicacao:

- a profundidade maxima da recursao 2 * n
- a estrutura usada para construir a string atual
  armazena ate 2 * n caracteres

Ignorando constantes, o espaco e O(n) mais precisamento O(2 * n)
*/

class Solution {
    fun generateParenthesis(pairCount: Int): List<String> {
        
        /*
        STEP 1
        Lista usada para construir a combinacao atual
        caractere por caractere.

        Usamos lista mutavel para facilitar
        add e remove no final

        O(1)
        */
        val currentParentheses = mutableListOf<String>()

        /*
        STEP 2
        Lista com todas as combinacoes validas geradas.

        O(1)
        */
        val allValidCombinations = mutableListOf<String>()

        /*
        STEP 3
        Funcao de backtracking.

        openCount   -> quantidade de ')' usados ate agora
        closeCount  -> quantidade de ')' usado ate agora
        */
        fun generateValidParenthesesBacktracking(
            openCount: Int,
            closeCount: Int
        ) {

            /*
            STEP 4
            caso base:
            se ja usamos exatamente pairCount aberturas
            e pairCount fechamentos,
            entao temos uma combinacao valida completa.

            joinToString() -> O(n)
            */
            if (openCount == pairCount && closeCount == pairCount) {
                allValidCombinations.add(currentParentheses.joinToString(""))
                return
            }

            /*
            STEP 5
            Se ainda podemos abrir mais parenteses,
            escolhemos adicionar '('.

            add() -> O(1)
            */
            if (openCount < pairCount) {
                currentParentheses.add("(")

                /*
                STEP 6
                Descer na recursao apos adicionar '('.
                */
                generateValidParenthesesBacktracking(
                    openCount + 1,
                    closeCount
                )

                /*
                STEP 7
                Backtracking:
                remover o ultimo caractere adicionar para restaurar o estado anterio

                removeAt(lastIndex) -> O(1)
                */
                currentParentheses.removeAt(currentParentheses.lastIndex)
            }

            /*
            STEP 8
            So podemos fechar se tivermos mais aberturas
            do que fechamentos no momento

            isso garante que a string nunca fique invalida

            add() -> O(1)
            */
            if (closeCount < openCount) {
                currentParentheses.add(")")

                /*
                STEP 9
                Descer na recursao apos adicionar ')'.
                */
                generateValidParenthesesBacktracking(
                    openCount,
                    closeCount + 1
                )

                /*
                STEP 10
                Backtracking
                remover o ')' para explorar outros caminhos.

                removeAt(lastIndex) -> O(1)
                */
                currentParentheses.removeAt(currentParentheses.lastIndex)
            }
        }

        /*
        STEP 11
        Iniciar o backtracking com zero aberturas
        e zero fechamentos.

        O(1)
        */
        generateValidParenthesesBacktracking(0, 0)

        /*
        STEP 12
        Retornar todas as combinacoes validas

        O(1)
        */
        return allValidCombinations
    }
}
