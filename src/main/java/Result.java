// Estrutura para armazenar os resultados
public class Result {
    long time;
    long swaps;
    long iterations;

    Result(long time, long swaps, long iterations) {
        this.time = time;
        this.swaps = swaps;
        this.iterations = iterations;
    }

    void print() {
        System.out.println("Time: " + time + "\nSwaps: " + swaps + "\nIterations: " + iterations);
    }
}