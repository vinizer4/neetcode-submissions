/*
Course Schedule II

PROBLEMA

Temos:

- totalCourses -> quantidade total de cursos
- prerequisites -> pares [course, prerequisite]

Se temos:
[courseA, courseB]

isso significa:

- para fazer courseA
  precisamos fazer courseB antes

Objetivo:

retornar um ordem válida para concluir todos os cursos.

Se não for possivel concluir todos
(por cause de ciclo),
devemos retornar array vazio.

EXEMPLO

Entrada:
totalCourses: 4
prerequisites = [[1,0],[2,0],[3,1],[3,2]]

Saida possivel:
[0,1,2,3]

ou:
[0,2,1,3]


IDEIA PRINCIPAL

Esse e um problema classico de:

Topological Sort

Porque queremos uma ordem
em que cada curso apareca
depois de seus pre requisitos


INTUICAO

Se um curso tem indegree = 0,
significa que ele nao depende de ninguem
ou que todos os seus pre-requisitos
ja foram resolvidos.

Entao ele ja pode entrar na resposta.

Quando "fazemos" esse curso:

- removemos sua influencia do grafo
- diminuimos o indegree dos cursos dependentes dele

Se algum desses cursos passar a ter indegree = 0,
ele tambem ja pode ser feito.


COMO ESSA SOLUCAO FUNCIONA?

1. Construimos:
    - adjacency list
    - indegree array

2. Para cada curso com indegree = 0,
   iniciamos DFS

3. No DFS:
   - adicionamos o curso a resposta
   - reduzimos o indegree dos vizinhos
   - se algum vizinho virar indegree 0,
     continuamos DFS nele

4. No final:
   - se visitamos todos os cursos, existe resposta valida
   - senao, ha ciclo


OBSERVACAO IMPORTANTE

Essa abordagem usa a ideia de topological sort
baseada em indegree,
mas faz a propagacao usando DFS recursivo
em vez de fila


COMPLEXIDADE

Tempo: O(V + E)

Onde:
- V = quantidade de cursos
- E = quantidade de relacoes de pre-requisito

Explicacao:

- construimos o grafo em O(E)
- percorremos todos os cursos em O(V)
- cada aresta e processada uma vez

Espaco: O(V + E)

Explicacao:

- adjacency list armazena as arestas
- indegree armazena um valor por curso
- output armazena ate V cursos
- a recursao pode chegar a O(V)

*/

class Solution {
    fun findOrder(
        totalCourses: Int, 
        prerequisites: Array<IntArray>
    ): IntArray {
        
        /*
        STEP 1
        Construir adjacency list.

        adjacencytList[course]
        -> lista de cursos 

        O(V)
        */
        val adjacencyList = Array(totalCourses) {
            mutableListOf<Int>()
        }

        /*
        STEP 2
        indegreeByCourse[course]
        -> quantidade de pre-requisitos ainda pendentes

        O(V)
        */
        val indegreeByCourse = IntArray(totalCourses)

        /*
        STEP 3
        Preencher grafo e indegree.

        Se temos [nextCourse, prerequisiteCourse],
        então:

        prerequisiteCourse -> nextCourse

        E nextCourse ganha +1 em indegree.

        Loop -> O(E)
        */
        for ((nextCourse, prerequisiteCourse) in prerequisites) {
            indegreeByCourse[nextCourse]++
            adjacencyList[prerequisiteCourse].add(nextCourse)
        }

        /*
        STEP 4
        Lista com a ordem final dos cursos.

        O(1)
        */
        val courseOrder = mutableListOf<Int>()

        /*
        STEP 5
        DFS que processa um curso ja liberado
        (ou seja, com indegree 0).

        currentCourse -> curso atual
        */
        fun processAvailableCourseDFS(currentCourse: Int) {

            /*
            STEP 6
            Adicionar o curso a resposta.

            Isso significa que estamos "fazendo" esse curso agora.

            add() -> O(1) amortizado
            */
            courseOrder.add(currentCourse)

            /* 
            STEP 7
            Reduzir o indegree do próprio curso.

            Aqui isso funciona como uma marcacao de que 
            esse curso ja foi processado.

            O(1)
            */
            indegreeByCourse[currentCourse]--

            /*
            STEP 8
            Processar os cursos que dependem do curso atual.

            Para cada vizinho:
            - reduzimos seu indegree
            - se virar 0, ele ja pode ser feito

            Interacao total sobre arestas -> O(E)
            */
            for (dependentCourse in adjacencyList[currentCourse]) {
                indegreeByCourse[dependentCourse]--

                if (indegreeByCourse[dependentCourse] == 0) processAvailableCourseDFS(dependentCourse)
            }
        }

        /*
        STEP 9
        Iniciar DFS a partir de todos os cursos
        que ja comecam com indegree 0.

        Loop -> O(V)
        */
        for (course in 0 until totalCourses) {
            if (indegreeByCourse[course] == 0) {
                processAvailableCourseDFS(course)
            }
        }

        /*
        STEP 10
        Se conseguimos adicionar todos os cursos,
        entao existe ordem valida.

        Caso contrario, ha ciclo.

        O(V)
        */
        return if (courseOrder.size == totalCourses) {
            courseOrder.toIntArray()
        } else {
            intArrayOf()
        }

    }
}
