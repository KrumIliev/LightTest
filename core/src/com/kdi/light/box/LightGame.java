package com.kdi.light.box;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kdi.light.box.screens.PlayScreen;

public class LightGame extends Game {

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 768;
    public static final float PPM = 100;

    public static final short BIT_TRANSPERANT = 1;
    public static final short BIT_PLAYER = 2;
    public static final short BIT_BROUN = 4;
    public static final short BIT_BLUE = 8;
    public static final short BIT_PINK = 16;
    public static final short BIT_YELLOW = 32;
    public static final short BIT_GREEN = 64;

    public SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new PlayScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
    }
}
