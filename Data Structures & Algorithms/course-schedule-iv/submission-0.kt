/*
Course Schedule IV

PROBLEMA

Temos:

- totalCourses -> quantidade total de cursos
- prerequisites -> pares [prerequisiteCourse, nextCourse]
- queries -> pares [courseA, courseB]

Se temos:
[prerequisiteCourse, nextCourse]

isso significa:

- prerequisiteCourse deve ser feito antes de nextCourse

Importante:
os pre-requisitos podem ser indiretos.

Exemplo:
se 1 -> 2 e 2 -> 3
entao 1 tambem e pre-requisito de 3

Objetivo:

para cada query [courseA, courseB],
responder se courseA e pre-requisito de courseB.

EXEMPLO

Entrada:
totalCourses = 3
prerequisites = [[1,2],[1,0],[2,0]]
queries = [[1,0],[1,2]]

Saida:
[true, true]


IDEIA PRINCIPAL

Queremos saber, para cada curso,
quais cursos precisam vir antes dele.

Como o grafo nao tem ciclos,
podemos processar os cursos em ordem topologica.

A ideia e:

- quanto processamos um curso,
  todos os seus pre-requisitos ja sao conhecidos
- entao podemos propagar essas informcoes
  para os cursos que dependem dele


INTUICAO DO KAHN's ALGORITHM

Cursos com indegree 0:

- nao dependem de ninguem
- podem ser processados imediatamente

Quando processamos um curso:

- diminuimos o indegree dos seus vizinhos e propagamos:
    - o proprio curso como pre-requisito
    - todos os pre-requistiso dele tambem

Assim cada curso acumula o conjunto completo
de seus pre-requisitos diretos e indiretos.


ESTRATEGIA

Step 1
Construir:
- adjacency list
- indegree array
- prerequisiteSetByCourse

Step 2
Colocar na fila todos os cursos com indegree 0

Step 3
Enquanto a fila nao estiver vazia:
- processar um curso atual
- para cada vizinho:
    - adicionar o curso atual como pre-requisito
    - adicionar todos os pre-requisitos do curso atual
    - diminuir indegree
    - se indegree virar 0, colocar na fila

Step 4
Responder cada query verificando se:
courseA pertence ao conjunto de pre-requisitos de courseB


COMPLEXIDADE

Tempo: O(V * (V + E) + Q)

Onde:
- V = quantidade de cursos
- E = quantidade de relacoes de pre-requisito
- Q = quantidade de queries

Explicacao:

- construimos o grafo em O(E)
- no processo topologico, para cada aresta,
  podemos propagar um conjunto de pre-requisitos
- como cada conjunto pode ter ate V elementos,
  o custo total fica O(V * (V + E))
- depois, responder queries custa O(Q)

Espaço: O(Vˆ2 + E)

Explicacao:

- adjacency list armazena as arestas
- prerequisiteSetByCourse pode armazenar,
  no pior caso, ate V pre-requisitos para cada curso

*/

class Solution {
    fun checkIfPrerequisite(
        totalCourses: Int, 
        prerequisites: Array<IntArray>, 
        queries: Array<IntArray>
    ): List<Boolean> {
        
        /*
        STEP 1
        adjacencyList[course] guarda os cursos
        que dependem dele.

        Exemplo:
        prerequisiteCourse -> nextCourse

        O(V)
        */
        val adjacencyList = Array(totalCourses) { mutableSetOf<Int>() }

        /*
        STEP 2
        prerequisiteSetByCourse[course] guarda todos os cursos
        que sao pre-requisitos diretos ou indiretos dele.

        O(V)
        */
        val prerequisiteSetByCourse = Array(totalCourses) { mutableSetOf<Int>() }

        /*
        STEP 3
        indegreeByCourse[course] guarda quantos pre-requisitos
        diretos ainda faltam ser processados.

        O(V)
        */
        val indegreeByCourse = IntArray(totalCourses)

        /*
        STEP 4
        Construir grafo e indegree.

        Se temos [prerequisiteCourse, nextCourse]:
        prerequisiteCourse -> nextCourse

        Loop -> O(E)
        */
        for (prerequisitePair in prerequisites) {
            val prerequisiteCourse = prerequisitePair[0]
            val nextCourse = prerequisitePair[1]

            adjacencyList[prerequisiteCourse].add(nextCourse)
            indegreeByCourse[nextCourse]++
        }

        /*
        STEP 5
        Fila para Kahn's Algorithm.

        Comecamos com todos os cursos que tem indegree 0,
        ou seja, cursos ja liberados.

        O(V)
        */
        val availableCoursesQueue = ArrayDeque<Int>()

        for (course in 0 until totalCourses) {
            if (indegreeByCourse[course] == 0) {
                availableCoursesQueue.add(course)
            }
        }

        /*
        STEP 6
        Processar cursos em ordem topologica.

        Enquanto houver curso liberado na fila,
        processamos e propagamos seus pre-requisitos.

        Loop -> O(V + E) estruturalmente,
        com propagacao de sets podendo elevar custo total
        */
        while (availableCoursesQueue.isNotEmpty()) {
            val currentCourse = availableCoursesQueue.removeFirst()

            /*
            STEP 7
            Para cada curso que depende do currentCourse,
            propagamos informacoes de pre-requisito.

            Loop total sobre arestas -> O(E)
            */
            for (dependentCourse in adjacencyList[currentCourse]) {

                /*
                STEP 8
                O curso atual e pre-requisito direto
                do curso dependente.

                O(1) amortizado
                */
                prerequisiteSetByCourse[dependentCourse].add(currentCourse)

                /*
                STEP 9
                Todos os pre-requisitos do curso atual
                tambem viram pre-requisitos do curso dependente.

                addAll() -> até O(V)
                */
                prerequisiteSetByCourse[dependentCourse]
                    .addAll(prerequisiteSetByCourse[currentCourse])

                /*
                STEP 10
                Reduzir indegree do curso dependente,
                pois acabamos de processar um dos cursos
                que precisavam vir antes dele.
                
                O(1)
                */
                indegreeByCourse[dependentCourse]--

                /*
                STEP 11
                Se o curso dependente ficou com indegree 0,
                ele ja pode ser processado.
                
                O(1)
                */
                if (indegreeByCourse[dependentCourse] == 0) {
                    availableCoursesQueue.add(dependentCourse)
                }
            }
        }

        /*
        STEP 12
        Responder cada query.

        Para [courseA, courseB],
        verificamos se courseA pertence ao conjunto
        de pre-requisito de courseB.

        map() -> O(Q)
        contains -> O(1) amortizado
        */
        return queries.map { queryPair ->
            val prerequisiteCourse = queryPair[0]
            val targetCourse = queryPair[1]
            
            prerequisiteSetByCourse[targetCourse]
                .contains(prerequisiteCourse)
        }
    }
}
