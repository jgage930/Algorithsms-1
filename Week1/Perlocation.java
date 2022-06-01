package Week1;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Perlocation {
    boolean[][] grid;

    public Perlocation(int n) {
        // False for blocked site
        grid = new boolean[n][n];
    }

    public void open(int row, int col) {
        grid[row][col] = true;
    }

    public boolean isOpen(int row, int col) {
        if (grid[row][col]) {
            return true;
        }
        return false;
    }
}
