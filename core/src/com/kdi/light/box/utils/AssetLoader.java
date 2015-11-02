package com.kdi.light.box.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class AssetLoader {

    public static Texture blocks;
    public static Texture hud;

    public AssetLoader() {
        blocks = new Texture(Gdx.files.internal("blocks.png"));
        hud = new Texture(Gdx.files.internal("hud.png"));
    }
}
