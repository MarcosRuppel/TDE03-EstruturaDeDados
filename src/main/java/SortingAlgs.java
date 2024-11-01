public class SortingAlgs {
    // Implementação do Insertion Sort
    public static Result insertionSort(int[] array) {
        long startTime = System.currentTimeMillis();
        long swaps = 0;
        long iterations = 0;

        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int j = i - 1;
            iterations++; // Contabiliza a iteração do laço principal

            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j--;
                swaps++; // Contabiliza uma troca
                iterations++; // Contabiliza cada iteração do while
            }
            array[j + 1] = key;
        }

        long endTime = System.currentTimeMillis();
        return new Result(endTime - startTime, swaps, iterations);
    }


    public static Result selectionSort(int[] array) {
        long startTime = System.currentTimeMillis();
        long swaps = 0;
        long iterations = 0;

        for (int i = 0; i < array.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < array.length; j++) {
                iterations++; // Contabiliza cada comparação no loop interno
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                // Troca apenas se minIndex foi atualizado
                int temp = array[i];
                array[i] = array[minIndex];
                array[minIndex] = temp;
                swaps++; // Contabiliza uma troca
            }
        }

        long endTime = System.currentTimeMillis();
        return new Result(endTime - startTime, swaps, iterations);
    }

    // Implementação do Heap Sort
    public static Result heapSort(int[] array) {
        long startTime = System.currentTimeMillis();
        long swaps = 0;
        long iterations = 0;

        int n = array.length;

        // Constroi o heap
        for (int i = n / 2 - 1; i >= 0; i--) {
            int[] result = heapify(array, n, i);
            iterations += result[0];
            swaps += result[1];
        }

        // Extração de elementos do heap
        for (int i = n - 1; i >= 0; i--) {
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;
            swaps++;

            int[] result = heapify(array, i, 0);
            iterations += result[0];
            swaps += result[1];
        }

        long endTime = System.currentTimeMillis();
        return new Result(endTime - startTime, swaps, iterations);
    }

    private static int[] heapify(int[] array, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        int swaps = 0;
        int iterations = 0;

        // Comparação com o filho esquerdo
        if (left < n && array[left] > array[largest]) {
            largest = left;
        }
        iterations++;

        // Comparação com o filho direito
        if (right < n && array[right] > array[largest]) {
            largest = right;
        }
        iterations++;

        // Se o maior não for o nó pai
        if (largest != i) {
            int swap = array[i];
            array[i] = array[largest];
            array[largest] = swap;
            swaps++;

            int[] result = heapify(array, n, largest);
            iterations += result[0];
            swaps += result[1];
        }

        // Retorna tanto o número de iterações quanto de trocas como um array
        return new int[] {iterations, swaps};
    }


    // Implementação do Radix Sort
    public static Result radixSort(int[] array) {
        long startTime = System.currentTimeMillis();
        int max = getMax(array);
        long swaps = 0; //Radix não efetua trocas diretas entre posições do array original
        long iterations = 0;

        for (int exp = 1; max / exp > 0; exp *= 10) {
            iterations += countSort(array, exp);
        }

        long endTime = System.currentTimeMillis();
        return new Result(endTime - startTime, swaps, iterations);
    }

    // Implementação do Counting Sort
    public static Result countingSort(int[] array) {
        long startTime = System.currentTimeMillis();
        int max = getMax(array);
        int min = getMin(array); // Lida com arrays contendo valores negativos
        long swaps = 0;
        long iterations = 0;

        int range = max - min + 1;
        int[] count = new int[range];
        int[] output = new int[array.length];

        // Contagem dos elementos
        for (int j : array) {
            count[j - min]++;
            iterations++;
        }

        // Acumulação da contagem
        for (int i = 1; i < range; i++) {
            count[i] += count[i - 1];
            iterations++;
        }

        // Ordenação do array com base na contagem
        for (int i = array.length - 1; i >= 0; i--) {
            output[count[array[i] - min] - 1] = array[i];
            count[array[i] - min]--;
            iterations++;
        }

        // Copiando a saída de volta para o array original
        System.arraycopy(output, 0, array, 0, array.length);

        long endTime = System.currentTimeMillis();
        return new Result(endTime - startTime, swaps, iterations);
    }


    private static int countSort(int[] array, int exp) {
        int n = array.length;
        int[] output = new int[n]; //array de saída
        int[] count = new int[10]; //array de contagem (0 a 9)
        int iterations = 0;

        for (int j : array) {
            count[(j / exp) % 10]++;
            iterations++;
        }

        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
            iterations++;
        }

        for (int i = n - 1; i >= 0; i--) {
            output[count[(array[i] / exp) % 10] - 1] = array[i];
            count[(array[i] / exp) % 10]--;
            iterations++;
        }

        for (int i = 0; i < n; i++) {
            array[i] = output[i];
            iterations++;
        }

        return iterations;
    }

    // Implementação do Bucket Sort
    public static Result bucketSort(int[] array) {
        long startTime = System.currentTimeMillis();
        long swaps = 0;
        long iterations = 0;

        int max = getMax(array);
        int min = getMin(array);
        int bucketCount = (int) Math.sqrt(array.length);

        // Criando buckets como vetores
        int[][] buckets = new int[bucketCount][array.length];
        int[] bucketSizes = new int[bucketCount];

        // Distribuir elementos nos buckets
        for (int num : array) {
            int bucketIndex = (int) ((num - min) / (double) (max - min + 1) * (bucketCount - 1));
            buckets[bucketIndex][bucketSizes[bucketIndex]++] = num;
            iterations++; // Contabiliza cada iteração de distribuição
        }

        // Ordenar cada bucket com Insertion Sort e combinar
        int index = 0;
        for (int i = 0; i < bucketCount; i++) {
            if (bucketSizes[i] > 0) {
                long[] results = bucketInsSort(buckets[i], bucketSizes[i]);
                swaps += results[0];
                iterations += results[1];

                for (int j = 0; j < bucketSizes[i]; j++) {
                    array[index++] = buckets[i][j];
                    iterations++; // Contabiliza cada iteração de combinação dos buckets
                }
            }
        }

        long endTime = System.currentTimeMillis();
        return new Result(endTime - startTime, swaps, iterations);
    }

    // Função auxiliar para ordenar usando Insertion Sort
    private static long[] bucketInsSort(int[] array, int size) {
        long swaps = 0;
        long iterations = 0;

        for (int i = 1; i < size; i++) {
            int key = array[i];
            int j = i - 1;
            iterations++; // Contabiliza a iteração do 'for' de Insertion Sort

            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j--;
                swaps++;
                iterations++; // Contabiliza cada iteração do 'while'
            }
            array[j + 1] = key;
        }
        return new long[] {swaps, iterations}; // Retorna tanto swaps quanto iterations
    }


    // Função auxiliar para obter o máximo do array
    private static int getMax(int[] array) {
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }

    // Função auxiliar para obter o mínimo do array
    private static int getMin(int[] array) {
        int min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < min) {
                min = array[i];
            }
        }
        return min;
    }
}
