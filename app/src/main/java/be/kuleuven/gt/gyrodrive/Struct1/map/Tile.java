package be.kuleuven.gt.gyrodrive.Struct1.map;

import android.graphics.Canvas;
import android.graphics.Rect;

public abstract class Tile {

    protected final Rect mapLocationRect;

    public Tile(Rect mapLocationRect) {
        this.mapLocationRect = mapLocationRect;
    }

    public enum TileType {
        WALL_TILE,
        GROUND_TILE,
        END_TILE
    }

    public static Tile getTile(int idxTileType, SpriteSheet spriteSheet, Rect mapLocationRect) {

        switch(TileType.values()[idxTileType]) {

            case END_TILE:
                return  new EndTile(spriteSheet, mapLocationRect);
            case WALL_TILE:
                return new WallTile(spriteSheet, mapLocationRect);
            case GROUND_TILE:
                return new GroundTile(spriteSheet, mapLocationRect);
            default:
                return null;
        }
    }

    public abstract TileType getType();


    public abstract void draw(Canvas canvas);
}
