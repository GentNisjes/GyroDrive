package be.kuleuven.gt.gyrodrive.Struct1.map;

import android.util.Log;

public class EndMap {
    private boolean[][] endAreas;

    public EndMap(int rows, int columns) {
        endAreas = new boolean[rows][columns];
    }

    public void markEnd(int row, int column) {
        endAreas[row][column] = true;
    }

    public boolean isEnd(int row, int column) {
        Log.d("isEnd", "" + endAreas[row + 1][column + 1]);
        return endAreas[row + 1][column + 1];
    }
    @Override
    public String toString() {
        StringBuilder ends = new StringBuilder();
        for (int i = 0; i < endAreas.length; i++) {
            for (int j = 0; j < endAreas[i].length; j++) {
                ends.append(endAreas[i][j] ? "X" : "O"); // Assuming true represents impassable areas
            }
            ends.append("\n"); // Add a line break after each row
        }
        return ends.toString();
    }
}
