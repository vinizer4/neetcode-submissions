/*
Word Ladder

PROBLEMA

Temos:

- beginWord -> palavra inicial
- endWord -> palavra final
- wordList -> dicionario de palavras validas

Objetivo:

descobrir o tamanho da menor sequencia de transformacao
de beginWord ate endWord

Regras:

1. So podemos mudar uma letra por vez
2. Cada nova palavra gerada precisa existir em wordList
3. beginWord nao precisa estar em wordList
4. endWord obrigatoriamente precisa estar em wordList,
   senao a resposta e 0

EXEMPLO

beginWord = "hit"
endWord = "cog"
wordList = ["hot","dot","dog","lot","log","cog"]

Resposta:
5

Uma sequência válida é:

hit -> hot -> dot -> dog -> cog

Ela tem 5 palavras.


IDEIA PRINCIPAL

Esse e um problema classico de menor caminho em grafo.

Cada palavra pode ser vista como um no.
Existe uma aresta entre duas palavras se elas diferem em exatamente uma letra.

Queremos o menor numero de passos de beginWord ate endWord.

Como todas as transicoes tem "peso" igual (cada transformacao vale 1),
a melhor abordagem e:

BFS (Breath First Search)


POR QUE BFS FUNCIONA?

BFS visita os nos em camadas:

- primeiro todas as palavras a 1 transformacao de distancia
- depois todas as palavras a 2 transformacoes
- depois todas as palavras a 3 transformacoes
- ...

Entao, a primeira vez que encontramos endWord,
temos certeza de que encontramos o menor caminho.


COMO GERAR OS VIZINHOS?

Em vez de pre-calcular todo o grafo,
vamos gerar os vizinhos "on the fly".

Para uma palavra atual:

- tentamos trocar cada posicao
- por cada letra de 'a' ate 'z'
- se a palavra resultante existir no conjunto,
  ela e um vizinho valido

Exemplo:
"hit"

posicao 0:
ait, bit, cit, ...

posicao 1:
hat, hbt, hct, ...

posicao 2:
hia, hib, hic, ...

Se uma dessa estiver no conjunto,
podemos ir para ela.


COMO MARCAR VISITADO?

Usamos um Set com as palavras disponiveis.

Quando uma palavra entra na fila:

- removemos ela do set

Isso serve ao mesmo tempo para:

- marcar como visitada
- evitar visitar de novo
- manter lookup O(1)


ESTRATEGIA

Step 1
Se endWord nao estiver em wordList, retornar 0.

Step 2
Converter wordList em Set.

Step 3
Criar fila da BFS comecando com beginWord.

Step 4
Processar a fila por niveis.

Step 5
Para cada palavra atual:
- se ela for endWord, retornar a distancia
- gerar todas as palavras vizinhas
- se o vizinho existir no set:
    - remover do set
    - adicionar na fila

Step 6
Se a fila esvaziar sem encontrar endWord, retornar 0.


COMPLEXIDADE

Tempo: O(m^2 * n)

Onde:
- n = quantidade de palavras
- m = tamanho de cada palavra

Explicacao:

Para cada palavra processada:
- tentamos m posicoes
- para cada posicao, tentamos 26 letras
- para construir cada nova palavra, substring pode custar O(m)

Entao o custo fica aproximadamente O(n * m * 26 * m),
ou seja, O(m^2 * n)

Espaco: O(m * n)

Explicacao:

- o set armazena ate n palavras de tamanho m
- a fila tambem pode armazenar varias palavras

*/

class Solution {
    fun ladderLength(
        beginWord: String, 
        endWord: String, 
        wordList: List<String>
    ): Int {
        
        /*
        STEP 1
        Se endWord nao existir no dicionario,
        nunca sera possivel chegar ate ela.
        
        Tambem mantemos a checagem beginWord == endWord
        por seguranca, embora a constraint diga que sao diferentes.

        contains() em List -> O(n)
        */
        if (!wordList.contains(endWord) || beginWord == endWord) return 0

        /*
        STEP 2
        Converter o dicionario em Set para permitir:

        - busca O(1)
        - remocao O(1)

        Esse set tambem vai servir como controle de visitados.

        Criacao -> O(n)
        */
        val availableWords = wordList.toMutableSet()

        /*
        STEP 3
        distanceFromStart vai representar a quantidade de palavras
        no caminho atual da BFS.

        Exemplo:
        beginWord sozinho -> distancia 1 quando processado
        mas aqui comecamos em 0 e incrementamos no inicio de cada nivel.

        O(1)
        */
        var transformationLength = 0

        /*
        STEP 4
        Fila da BFS.

        Comecamos a busca pela beginWord.
        
        O(1)
        */
        val bfsQueue = ArrayDeque<String>().apply {
            add(beginWord)
        }

        /*
        STEP 5
        Rodar BFS enquanto houver palavras para explorar.

        A BFS sera processada por niveis.
        Cada nivel representa palavras que estao
        a mesma distancia da beginWord.
        */
        while (bfsQueue.isNotEmpty()) {

            /*
            STEP 6
            Avancamos um nivel da BFS.

            Isso significa:
            todas as palavras processadas neste bloco
            terao o mesmo numero de transformacoes.
            
            O(1)
            */
            transformationLength++

            /*
            STEP 7
            Processar exatamente o tamanho atual da fila.

            Isso e importante porque queremos isolar o nivel atual.
            Palavras adicionadas durante este loop
            pertencem ao proximo nivel da BFS.

            repeat(currentLevelSize)
            */
            repeat(bfsQueue.size) {

                /*
                STEP 8
                Remover a proxima palavra do nival atual.

                removeFirst() -> O(1)
                */
                val currentWord = bfsQueue.removeFirst()

                /*
                STEP 9
                Se encontramos a palavra final,
                essa e a menor transformacao possivel,
                porque BFS sempre encontra o menor caminho primeiro.

                O(1)
                */
                if (currentWord == endWord) return transformationLength

                /*
                STEP 10
                Para gerar vizinhos, tentamos alterar cada posicao da palavra.

                Exemplo:
                "hit"
                - trocar indice 0
                - trocar indice 1
                - trocar indice 2

                Loop -> O(m)
                */
                for (characterIndex in currentWord.indices) {

                    /*
                    STEP 11
                    Para cada posicao, tentamos todas as letras do alfabeto.

                    Loop -> 26 iteracoes (constante)
                    */
                    for (replacementCharacter in 'a'..'z') {

                        /*
                        STEP 12
                        Se a letra nova for igual a atual,
                        nao faz sentido gera a mesma palavra

                        O(1)
                        */
                        if (currentWord[characterIndex] == replacementCharacter) continue

                        /*
                        STEP 13
                        Gerar a palavra vizinha trocando apenas uma letra.

                        Exemplo:
                        "hit" trocando indice 1 por 'o'
                        -> "hot"

                        substring() pode custar O(m),
                        entao essa etapa pesa na complexidade total.
                        */
                        val neighborWord =
                            currentWord.substring(0, characterIndex) +
                            replacementCharacter +
                            currentWord.substring(characterIndex + 1)

                        /*
                        STEP 14
                        Se o vizinho ainda esta disponivel no set,
                        entao ele e uma palavra valida do dicionario
                        e ainda nao foi visitado.

                        remove() retornar true se a palavra existia.
                        isso e otimo porque:
                        - valida o vizinho
                        - ja marca como visitado
                        em uma unica operacao

                        O(1)
                        */
                        if (availableWords.remove(neighborWord)) {

                            /*
                            STEP 15
                            Adicionar o vizinho na fila
                            para ser processado no proximo nivel da BFS.

                            addLast() -> O(1)
                            */
                            bfsQueue.addLast(neighborWord)
                        }
                    }
                }
            }
        }

        /*
        STEP 16
        Se esgotamos a BFS e nunca encontraomos endWord,
        entao nao existe caminho valido

        O(1)
        */
        return 0
    }
}
