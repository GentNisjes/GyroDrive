package be.kuleuven.gt.gyrodrive.Struct1.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.nfc.Tag;
import android.util.Log;

import be.kuleuven.gt.gyrodrive.R;
import be.kuleuven.gt.gyrodrive.Struct1.Constants;

public class SpriteSheet {
    private static final int SPRITE_WIDTH_PIXELS = 64 /*= Math.round((float) Constants.SCREEN_WIDTH/24) + 1*/;
    private static final int SPRITE_HEIGHT_PIXELS = 64/* = Math.round((float) Constants.SCREEN_HEIGHT/10) + 1*/;
    private Bitmap bitmap;
    private String spriteSize;

    public SpriteSheet(Context context) {
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.tiles3, bitmapOptions);
        Log.d(spriteSize, "SpriteSheet: HEIGHT = " + SPRITE_HEIGHT_PIXELS + " , WIDTH = " + SPRITE_WIDTH_PIXELS);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public Sprite getWallSprite() {
        return getSpriteByIndex(0, 0);
    }

    public Sprite getGroundSprite() {
        return getSpriteByIndex(0, 1);
    }

    public Sprite getEndSprite(){return getSpriteByIndex(0,2);}

    private Sprite getSpriteByIndex(int idxRow, int idxCol) {
        return new Sprite(this, new Rect(
                idxCol*SPRITE_WIDTH_PIXELS,
                idxRow*SPRITE_HEIGHT_PIXELS,
                (idxCol + 1)*SPRITE_WIDTH_PIXELS,
                (idxRow + 1)*SPRITE_HEIGHT_PIXELS
        ));
    }
}
