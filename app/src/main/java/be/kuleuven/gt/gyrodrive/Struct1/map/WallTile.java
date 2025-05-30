package be.kuleuven.gt.gyrodrive.Struct1.map;

import android.graphics.Canvas;
import android.graphics.Rect;

class WallTile extends Tile {
    private final Sprite sprite;

    public TileType getType() {
        return TileType.WALL_TILE;
    }
    public WallTile(SpriteSheet spriteSheet, Rect mapLocationRect) {
        super(mapLocationRect);
        sprite = spriteSheet.getWallSprite();
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas, mapLocationRect.left, mapLocationRect.top);
    }
}
