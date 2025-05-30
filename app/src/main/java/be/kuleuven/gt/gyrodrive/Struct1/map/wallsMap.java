package be.kuleuven.gt.gyrodrive.Struct1.map;

import android.util.Log;

public class wallsMap {
private boolean[][] impassableAreas;

public wallsMap(int rows, int columns) {
        impassableAreas = new boolean[rows][columns];
        }

public void markImpassable(int row, int column) {
        impassableAreas[row][column] = true;
        }

public boolean isImpassable(int row, int column) {
        Log.d("isImpassable", "row: " + row + " column: " + column);
        return impassableAreas[row + 1][column + 1];
}
@Override
public String toString() {
        StringBuilder walls = new StringBuilder();
        for (int i = 0; i < impassableAreas.length; i++) {
                for (int j = 0; j < impassableAreas[i].length; j++) {
                        walls.append(impassableAreas[i][j] ? "X" : "O"); // Assuming true represents impassable areas
                }
                walls.append("\n"); // Add a line break after each row
        }
        return walls.toString();
}
}
