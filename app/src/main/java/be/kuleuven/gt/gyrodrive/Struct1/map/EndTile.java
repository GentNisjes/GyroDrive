package be.kuleuven.gt.gyrodrive.Struct1.map;

import android.graphics.Canvas;
import android.graphics.Rect;

import be.kuleuven.gt.gyrodrive.Struct1.map.Sprite;
import be.kuleuven.gt.gyrodrive.Struct1.map.SpriteSheet;

class EndTile extends Tile {
    private final Sprite sprite;

    public TileType getType() {
        return TileType.END_TILE;
    }
    public EndTile(SpriteSheet spriteSheet, Rect mapLocationRect) {
        super(mapLocationRect);
        sprite = spriteSheet.getEndSprite();
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas, mapLocationRect.left, mapLocationRect.top);
    }
}