/*
Alien Dictionary

PROBLEMA

Recebemos uma lista de palavras ja ordenadas
de acordo com um alfabeto desconhecido.

Nosso objetivo e descobrir uma ordem valida
dos caractestes desse alfabeto.

Se nao for possivel, devemos retornar "".


IDEIA PRINCIPAL

Quando comparamos duas palavras adjacentes,
a primeira posicao em que elas diferem
nos da uma regra de ordem entre letras.

Exemplo:

"wrt"
"wrf"

As duas primeiras letras sao iguais:
w == w
r == r

Na terceira posicao:
t != f

Isso significa:
t vem antes de f

Então criamos uma aresta:

t -> f


TRANSFORMANDO EM GRAFO

- cara caractere e um no
- cara regra "a vem antes de b" vira aresta:
  a -> b

Depois disse, o problema vira:

"encontrar uma ordem topologica valida do grafo"


QUANDO O INPUT E INVALIDO?

Exemplo:

"abc"
"ab"

Isso e impossivel.

Por que?

Porque se "ab" e prefixo de "abc"
entao "ab" deveria vir antes de "abc",
e nao o contrario.

Nesse caso, retornamos "".


POR QUE USAR DFS?

DFS nos ajuda em duas coisas:

1. detectar ciclos
   Se houver ciclo, nao existe ordem valida

2. montar a ordem topologica
   Adicionamos o caractere apos processar
   todos os seus vizinhos (postorder)

No final, invertendo o resultado,
obtemos a ordem correta.


CONTROLE DE VISITA

Usamos 3 estados implicitos no mapa:

- nao visitado -> nao esta no mapa
- visitado -> valor 1
- visitado completamente -> valor -1

Se durante a DFS encontramos um no com estado 1,
significa que voltamos para um no que ainda esta na pilha de recursao -> ciclo.


COMPLEXIDADE

Tempo: O(N + V + E)

Onde:
- N = soma dos tamanhos das palavras
- V = numero de caracteres unicos
- E = numero de relacoes de ordem entre caracteres

Espaco: O(V + E)

Explicacao:

- o grafo armazena os vizinhos de cada caractere
- o mapa de visita e o resultado usam O(V)
- a pilha de recursao pode chegar a O(V)

*/


class Solution {
    fun foreignDictionary(words: Array<String>): String {

        /*
        STEP 1
        Criar o grafo com todos os caracteres existentes,
        mesmo que algum caractere nao tenha arestas.

        Isso e importante porque um caractere pode aparecer
        no resultado final mesmo sem depender de ninguem
        e sem apontar para ninguem.

        Estrutura:
        character -> conjunto de vizinhos que devem vir depois dele

        Exemplo:
        t -> {f}
        significa: t vem antes de f

        Loop total sobre todos os caracteres -> O(N)
        */
        val adjacencyList = HashMap<Char, HashSet<Char>>()

        for (word in words) {
            for (character in word) {
                adjacencyList.putIfAbsent(character, hashSetOf())
            }
        }

        /*
        STEP 2
        Comparar palavras adjacentes para descobrir regras de ordem.

        So precisamos comparar palavras vizinhas,
        porque a lista ja esta ordenada.

        Exemplo:
        se word1 vem antes de word2,
        a primeira diferenca entre elas define a ordem.

        Loop sobre palavras -> O(numberOfWords)
        */
        for (wordIndex in 0 until words.size - 1) {
            val currentWord = words[wordIndex]
            val nextWord = words[wordIndex + 1]

            /*
            STEP 3
            So faz sentido comparar ate o tamanho da menor palavra

            O(1)
            */
            val minimumLength = minOf(currentWord.length, nextWord.length)

            /*
            STEP 4
            Caso invalido:
            se currentWord for maior que nextWord
            e nextWord for prefixo de currentWord,
            entao a ordenacao e impossivel.

            Exemplo invalido:
            "abc" antes de "ab"

            substring() aqui funciona porque o limite e pequeno,
            mas conceitualmente estamos verificando prefixo.

            O(minimumLength)
            */
            if (
                currentWord.length > nextWord.length &&
                currentWord.substring(0, minimumLength) ==
                nextWord.substring(0, minimumLength)
            ) return ""

            /*
            STEP 5
            Encontrar a primeira posicao onde as palavras diferem.

            Essa primeira direnca e a unica que importa.

            Exemplo:
            "wrt"
            "wrf"

            primeira diferenca:
            t != f

            entao:
            t -> f

            Depois disso damos break,
            porque o restante da palavra nao importa
            para a ordem lexicografica.

            Loop -> O(minimumLength)
            */
            for (characterIndex in 0 until minimumLength) {
                if (currentWord[characterIndex] != nextWord[characterIndex]) {
                    adjacencyList[currentWord[characterIndex]]
                        ?.add(nextWord[characterIndex])
                    break
                }
            }
        }

        /*
        STEP 6
        Mapa para controlar o estado de visita dos caracteres.

        Convencao:
        - ausente no mapa -> nao visitado
        - 1 -> visitando agora (na pilha da recursao)
        - -1 -> ja processado completamente

        O(1)
        */
        val visitStateByCharacter = HashMap<Char, Int>()

        /*
        STEP 7
        Lista que armazenara os caracteres
        na ordem de termino da DFS.

        Depois vamos inverter essa lista.

        O(1)
        */
        val topologicalOrderReversed = mutableListOf<Char>()

        /*
        STEP 8
        DFS para detectar ciclo e construir odem topologica.

        Retorno:
        - true -> encontrou ciclo
        - false -> sem ciclo a partir desse no
        */
        fun detectCycleAndBuildOrderDFS(currentCharacter: Char): Boolean {

            /*
            STEP 9
            Se o caractere ja tem estado registrado,
            temos dois casos:

            1. estado == 1
               significa que ele esta sendo visitado agora
               e encontramos ele de novo
               -> isso e um ciclo

            2. estado == -1
               significa que ja foi totalmente processado
               -> nao precisa processar de novo

            O(1)
            */
            if (currentCharacter in visitStateByCharacter) {
                return visitStateByCharacter[currentCharacter] == 1
            }

            /*
            STEP 10
            Marcar o caracter como "visitando".

            Isso significa:
            ele entrou na pilha atual da recursao

            O(1)
            */
            visitStateByCharacter[currentCharacter] = 1

            /*
            STEP 11
            Explorar todos os vizinhos.

            Se qualquer vizinho levar o ciclo,
            propagamos true imediatamente.

            Iteracao total sobre arestas -> O(E)
            */
            for (neighborCharacter in adjacencyList[currentCharacter] ?: emptySet()) {
                if (detectCycleAndBuildOrderDFS(neighborCharacter)) {
                    return true
                }
            }
            
            /*
            STEP 12
            Se chegamos aqui,
            todos os vizinhos foram processados sem ciclo.

            Entao podemos marcar o caractere
            como totalmente finalizado

            O(1)
            */
            visitStateByCharacter[currentCharacter] = -1

            /*
            STEP 13
            Adicionar o caractere no resultado
            APOS processar seus vizinhos.

            Isso e postorder.

            Por que funciona?

            Se temos:
            a -> b

            entao b precisa ser processado antes de a
            na DFS pos-ordem.

            Depois, ao inverter a lista,
            obtemos:
            a antes de b

            add() -> O(1)
            */
            topologicalOrderReversed.add(currentCharacter)

            /*
            STEP 14
            Sem ciclo encontrado.

            O(1)
            */
            return false
        }

        /*
        STEP 15
        Rodar DFS para todos os caracteres do grafo.

        Isso e importante porque o grafo pode ter
        componentes desconectados.

        Exemplo:
        a -> b
        c isolado

        Precisamos incluir todos no resultado

        Loop -> O(V)
        */
        for (character in adjacencyList.keys) {
            if (detectCycleAndBuildOrderDFS(character)) {
                return ""
            }
        }

        /*
        STEP 16
        A lista foi construida em pos-ordem,
        entao precisamos inverter.

        joinToString("")  transforma a lista final em string.

        reversed() -> O(V)
        joinToString() -> O(v)
        */
        return topologicalOrderReversed
                .reversed()
                .joinToString("")
    }
}
