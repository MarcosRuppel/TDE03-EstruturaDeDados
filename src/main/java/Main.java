/**
 * Programa que executa diferentes algoritmos de ordenação de vetores, exibindo os dados de performance
 * após a execução. Parte do TDE03 da disciplina de Resolução de Problemas Estruturados
 * em Computação.
 * @version 2.2(REV4)
 * @author Enzo Curcio Stival, Hiann Wonsowicz Padilha, Marcos Paulo Ruppel
 * @since 2024-11-01
 */

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int[] sizes = {1000, 10000, 100000, 500000, 1000000}; // Dimensões dos vetores de inteiros a serem ordenados
        int rounds = 5; // Qtd de rounds da ordenação, para calcular as médias
        long seed = 42;  // Semente para resultados replicáveis

        for (int size : sizes) {
            System.out.println("Array Size: " + size);
            long totalTime = 0;
            long totalSwaps = 0;
            long totalIterations = 0;

            for (int i = 0; i < rounds; i++) {
                int[] array = generateRandomArray(size, seed+i);
                /* Aqui deve ser especificado qual algoritmo de ordenação contido em SortingAlgs a ser executado */
                Result result = SortingAlgs.countingSort(array.clone()); // substitua para rodar outro algoritmo
                totalTime += result.time;
                totalSwaps += result.swaps;
                totalIterations += result.iterations;
            }
            System.out.println("Average Time: " + (totalTime / rounds) + " ms");
            System.out.println("Average Swaps: " + (totalSwaps / rounds));
            System.out.println("Average Iterations: " + (totalIterations / rounds));
            System.out.println();
        }
    }

    // Função para gerar array aleatório usando a seed
    public static int[] generateRandomArray(int size, long seed) {
        int[] array = new int[size];
        Random random = new Random(seed);
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(size);
        }
        return array;
    }
}
