package com.kdi.light.box.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class AssetLoader {

    public static Texture blocks;
    public static Texture hud;
    public static Texture player;
    public static Texture items;

    public AssetLoader() {
        blocks = new Texture(Gdx.files.internal("blocks.png"));
        hud = new Texture(Gdx.files.internal("hud.png"));
        player = new Texture(Gdx.files.internal("p1_sprites.png"));
        items = new Texture(Gdx.files.internal("coins.png"));
    }
}
