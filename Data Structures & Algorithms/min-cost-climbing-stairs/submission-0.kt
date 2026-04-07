/*
Min Cost Climbing Stairs (Custo Mínimo para Subir Escadas)

PROBLEMA

Temos:
- cost -> um array onde cost[i] é o preço a pagar para pisar no degrau 'i'.

Objetivo:
Chegar ao "topo" da escada (passar do último índice) gastando o 
mínimo possível.

Regras:
1. Ao pagar o custo do degrau atual, você pode subir 1 ou 2 degraus.
2. Você pode escolher começar no degrau 0 ou no degrau 1.

EXEMPLO

cost = [10, 15, 20]

Resposta:
15

Explicação:
Começamos no índice 1 (custo 15). Pagamos 15 e damos 2 passos, 
atingindo o topo. Custo total = 15.

IDEIA PRINCIPAL

Este é um problema clássico de Programação Dinâmica (DP). 
O segredo da DP é quebrar um problema grande em subproblemas menores.

Em vez de tentar prever o futuro a partir do degrau 0, nós pensamos 
de trás para frente (Bottom-Up):
"Se eu estou no degrau i, qual é o caminho mais barato até o topo 
a partir daqui?"

A resposta é sempre:
O custo do degrau 'i' + o caminho mais barato entre os próximos dois degraus.

OTIMIZAÇÃO DE ESPAÇO (In-Place)

Normalmente, criaríamos um array 'dp' novo para guardar esses valores.
Mas, para economizar memória, podemos sobrescrever o próprio array 'cost' 
recebido (In-Place). Assim, a complexidade de espaço cai de O(n) para O(1).

ESTRATÉGIA (PASSO A PASSO)

Step 1: O custo para sair dos dois últimos degraus e chegar no topo 
        é apenas o próprio custo deles. (Por isso não precisamos alterá-los).
Step 2: Começamos a calcular a partir do antepenúltimo degrau (tamanho - 3) 
        e vamos descendo até o degrau 0.
Step 3: Para cada degrau atual, atualizamos seu valor: 
        Ele passa a valer o seu custo original + o menor valor entre os 
        dois degraus imediatamente à sua frente.
Step 4: Ao final do loop, o índice 0 e o índice 1 conterão o custo 
        total mínimo para chegar ao topo partindo de cada um deles.
Step 5: Como a regra diz que podemos começar no degrau 0 ou 1, retornamos 
        o menor valor entre cost[0] e cost[1].

COMPLEXIDADE

Tempo: O(n)
Onde 'n' é o número de degraus. Visitamos cada degrau exatamente 
uma vez no nosso loop for.

Espaço: O(1)
Como estamos modificando o array 'cost' de entrada diretamente (in-place),
não criamos nenhuma estrutura de dados adicional que cresça com 'n'.
*/

class Solution {
    fun minCostClimbingStairs(cost: IntArray): Int {
        
        /*
        STEP 1
        Guardamos o tamanho total da escada para clareza.
        */
        val totalSteps = cost.size

        /*
        STEP 2
        Começamos do antepenúltimo degrau (totalSteps - 3) e iteramos 
        para trás (downTo) até o primeiro degrau (0).
        
        Por que totalSteps - 3?
        Se o tamanho é 3 (índices 0, 1 e 2), o topo está no "índice 3".
        Do índice 2 (último), com 1 passo chegamos ao topo.
        Do índice 1 (penúltimo), com 2 passos chegamos ao topo.
        Logo, o primeiro degrau que precisa de um "cálculo de decisão" 
        é o índice 0 (3 - 3 = 0).
        */
        for (currentStep in totalSteps - 3 downTo 0) {
            
            /*
            STEP 3
            Verificamos quanto custa chegar ao topo se dermos 1 passo 
            ou se dermos 2 passos a partir de onde estamos.
            */
            val costIfOneStep = cost[currentStep + 1]
            val costIfTwoSteps = cost[currentStep + 2]

            /*
            STEP 4
            Atualizamos (In-Place) o valor do degrau atual.
            Ele agora representa o "Custo Total Mínimo" para chegar ao 
            topo partindo daqui.
            */
            cost[currentStep] += minOf(costIfOneStep, costIfTwoSteps)
        }

        /*
        STEP 5
        Como a regra permite começar do degrau 0 ou do degrau 1, 
        apenas retornamos a opção mais barata entre as duas.
        */
        return minOf(cost[0], cost[1])
    }
}