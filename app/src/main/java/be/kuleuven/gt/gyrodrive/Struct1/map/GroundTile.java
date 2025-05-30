package be.kuleuven.gt.gyrodrive.Struct1.map;

import android.graphics.Canvas;
import android.graphics.Rect;

import be.kuleuven.gt.gyrodrive.Struct1.map.Sprite;
import be.kuleuven.gt.gyrodrive.Struct1.map.SpriteSheet;

class GroundTile extends Tile {
    private final Sprite sprite;

    public TileType getType() {
        return TileType.GROUND_TILE;
    }
    public GroundTile(SpriteSheet spriteSheet, Rect mapLocationRect) {
        super(mapLocationRect);
        sprite = spriteSheet.getGroundSprite();
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas, mapLocationRect.left, mapLocationRect.top);
    }
}
