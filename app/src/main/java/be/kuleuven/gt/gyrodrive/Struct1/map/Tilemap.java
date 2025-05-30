package be.kuleuven.gt.gyrodrive.Struct1.map;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

/*import be.kuleuven.gt.gyrodrive.Struct1.GameDisplay;*/

import static be.kuleuven.gt.gyrodrive.Struct1.map.MapLayout.NUMBER_OF_COLUMN_TILES;
import static be.kuleuven.gt.gyrodrive.Struct1.map.MapLayout.NUMBER_OF_ROW_TILES;
import static be.kuleuven.gt.gyrodrive.Struct1.map.MapLayout.TILE_HEIGHT_PIXELS;
import static be.kuleuven.gt.gyrodrive.Struct1.map.MapLayout.TILE_WIDTH_PIXELS;

import be.kuleuven.gt.gyrodrive.Struct1.Constants;

public class Tilemap {

    private final MapLayout mapLayout;
    private Tile[][] tilemap;
    private SpriteSheet spriteSheet;
    private Bitmap mapBitmap;
    private EndMap endMap;
    private wallsMap wallsMap;
    private int screenWidth, screenHeight;

    public Tilemap(SpriteSheet spriteSheet) {
        mapLayout = new MapLayout();
        this.spriteSheet = spriteSheet;
        this.screenHeight = Constants.SCREEN_HEIGHT;
        this.screenWidth = Constants.SCREEN_WIDTH;
        initializeTilemap();
        generateImpassableAreasMap();
        generateEndAreasMap();

        Log.d("Setup", "map setup completed");
    }

    public void generateEndAreasMap(){
        int[][] layout = mapLayout.getLayout();
        endMap = new EndMap(NUMBER_OF_ROW_TILES, NUMBER_OF_COLUMN_TILES);

        for (int i = 0; i < NUMBER_OF_ROW_TILES; i++) {
            for (int j = 0; j < NUMBER_OF_COLUMN_TILES; j++) {
                if (/*layout[i][j] == 1 || */layout[i][j] == 2) {
                    endMap.markEnd(i, j);
                }
            }
        }
        String string = endMap.toString();
        System.out.println(string);
    }

    public void generateImpassableAreasMap() {
        int[][] layout = mapLayout.getLayout();
        wallsMap = new wallsMap(NUMBER_OF_ROW_TILES, NUMBER_OF_COLUMN_TILES);

        for (int i = 0; i < NUMBER_OF_ROW_TILES; i++) {
            for (int j = 0; j < NUMBER_OF_COLUMN_TILES; j++) {
                if (layout[i][j] == 1 || layout[i][j] == 2) {
                    wallsMap.markImpassable(i, j);
                }
            }
        }
        String string = wallsMap.toString();
        System.out.println(string);
    }

    private void initializeTilemap() {
        int[][] layout = mapLayout.getLayout();
        tilemap = new Tile[NUMBER_OF_ROW_TILES][NUMBER_OF_COLUMN_TILES];
        for (int iRow = 0; iRow < NUMBER_OF_ROW_TILES; iRow++) {
            for (int iCol = 0; iCol < NUMBER_OF_COLUMN_TILES; iCol++) {
                tilemap[iRow][iCol] = Tile.getTile(
                    layout[iRow][iCol],
                    spriteSheet,
                    getRectByIndex(iRow, iCol)
                );
            }
        }

        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        mapBitmap = Bitmap.createBitmap(
                NUMBER_OF_COLUMN_TILES*TILE_WIDTH_PIXELS,
                NUMBER_OF_ROW_TILES*TILE_HEIGHT_PIXELS,
                config
        );

        Canvas mapCanvas = new Canvas(mapBitmap);

        for (int iRow = 0; iRow < NUMBER_OF_ROW_TILES; iRow++) {
            for (int iCol = 0; iCol < NUMBER_OF_COLUMN_TILES; iCol++) {
                tilemap[iRow][iCol].draw(mapCanvas);
            }
        }
    }

    private Rect getRectByIndex(int idxRow, int idxCol) {
        return new Rect(
                idxCol*TILE_WIDTH_PIXELS,
                idxRow*TILE_HEIGHT_PIXELS,
                (idxCol + 1)*TILE_WIDTH_PIXELS,
                (idxRow + 1)*TILE_HEIGHT_PIXELS
        );
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(
            mapBitmap, 0, 0, null
        );

    }

    public Tile.TileType getTyleType(float x, float y){
        int Xcolumn = getColumnOrRow(x);
        int Ycolumn = getColumnOrRow(y);
        Tile tileToCheck = tilemap[Xcolumn][Ycolumn];
        return tileToCheck.getType();
    }

    public int getColumnOrRow (float coord){
        /*System.out.println("GetColumnOrRow: " + (int) Math.floor((double) coord / 64.0d));*/
        return ((int) Math.floor((double) coord / 64.0d));
    }

    public boolean isTileImpassable(int row, int column) {
        // Assuming 0 represents a passable tile and any other value represents impassable tiles
        /*return tilemap[row][column].getType() != Tile.TileType.WALL_TILE;*/
        return wallsMap.isImpassable(row, column);
    }

    public boolean isTileEndArea(int row, int column) {
        // Assuming 0 represents a passable tile and any other value represents impassable tiles
        /*return tilemap[row][column].getType() != Tile.TileType.WALL_TILE;*/
        return endMap.isEnd(row, column);
    }

}
