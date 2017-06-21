package com.week1;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Created by eccspro on 28/05/17.
 */
public class Percolation {

    private WeightedQuickUnionUF uf;
    private boolean [] openSites;
    private int N;
    private int numOpenSites;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if(n <= 0) throw new IllegalArgumentException("invalid grid size");
        this.N = n;
        uf = new WeightedQuickUnionUF(N * N + 2);
        openSites = new boolean[N * N];
        numOpenSites = 0;
        for(int i = 1; i <=N; i++) {
            uf.union(0, i);
        }

        for(int i = (N * N - N) + 1; i <= N; i++) {
            uf.union(N + 1, i);
        }
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) throws IllegalAccessException {
        if(! isValidIndex(row, col)) {
            throw new IllegalAccessException("invalid row or column index");
        }
        int index = findIndex(row, col);
        // open site at index
        openSites[index] = true; numOpenSites++;
        for(int i = 1; i <= N; i++) {
            for(int j = 1; j <= N; j++) {
                if(isValidIndex(i - 1, j - 1) && isOpen(i - 1, j - 1)) {
                    union(i - 1,j - 1, i, j);
                }
                if(isValidIndex(i - 1, j + 1) && isOpen(i - 1, j + 1)) {
                    union(i - 1, j + 1, i, j);
                }
                if(isValidIndex(i, j - 1) && isOpen(i, j - 1)) {
                    union(i, j - 1, i, j);
                }
                if(isValidIndex(i, j +  1) && isOpen(i, j + 1)) {
                    union(i, j + 1, i, j);
                }
                if(isValidIndex(i - 1, j) && isOpen(i - 1, j)) {
                    union(i - 1, j, i , j);
                }
                if(isValidIndex(i + 1, j -1) && isOpen(i + 1, j - 1)) {
                    union(i + 1, j - 1, i, j);
                }
                if(isValidIndex(i + 1, j) && isOpen(i + 1, j)) {
                    union(i + 1, j, i, j);
                }
                if(isValidIndex(i + 1, j + 1) && isOpen(i + 1, j + 1)) {
                    union(i + 1, j + 1, i, j);
                }
            }
        }

    }

    private void union(int row1, int col1, int row2, int col2) {
        int p = findIndex(row1, col1);
        int q = findIndex(row2, col2);

        if(!uf.connected(p,q)) {
            uf.union(p, q);
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
       return openSites[findIndex(row, col)];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        return uf.connected(findIndex(row, col), 0);
    }

    // number of open sites
    public int numberOfOpenSites()  {
        return numOpenSites;
    }

    // does the system percolate?
    public boolean percolates()  {
        return uf.connected(0, N + 1);
    }

    private boolean isValidIndex(int row, int col) {
        if(row <= 0 || row > N || col <= 0 || col > N) return false;
        return true;
    }

    private int findIndex(int row, int col) {
        return  (row - 1) * N + (col);
    }

    public static void main(String[] args) {

    }


}
