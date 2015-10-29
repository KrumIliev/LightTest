package com.kdi.light.box.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kdi.light.box.LightGame;
import com.kdi.light.box.entities.Player;

/**
 * Created by Krum on 10/29/2015.
 */
public class Hud {

    private OrthographicCamera camera;

    private Player player;
    private TextureRegion[] blocks;

    private float blockSize;

    public Hud(Player player) {
        this.player = player;
        blockSize = 64 / LightGame.PPM;

        Texture texture = new Texture(Gdx.files.internal("bricks.png"));
        blocks = new TextureRegion[5];
        for (int i = 0; i < blocks.length; i++) {
            blocks[i] = new TextureRegion(texture, i * 64, 0, 64, 64);
        }

        camera = new OrthographicCamera();
        camera.setToOrtho(false, LightGame.WIDTH / LightGame.PPM, LightGame.HEIGHT / LightGame.PPM);
    }

    public void render(SpriteBatch sb) {

        sb.setProjectionMatrix(camera.combined);
        sb.begin();

        short mask = player.body.getFixtureList().first().getFilterData().maskBits;

        if ((mask & LightGame.BIT_BLUE) != 0) {
            sb.draw(blocks[0],
                    LightGame.WIDTH / LightGame.PPM - blockSize * 2,
                    LightGame.HEIGHT / LightGame.PPM - blockSize * 2,
                    blockSize, blockSize);
        }

        if ((mask & LightGame.BIT_BROUN) != 0) {
            sb.draw(blocks[1],
                    LightGame.WIDTH / LightGame.PPM - blockSize * 2,
                    LightGame.HEIGHT / LightGame.PPM - blockSize * 2,
                    blockSize, blockSize);
        }

        if ((mask & LightGame.BIT_GREEN) != 0) {
            sb.draw(blocks[2],
                    LightGame.WIDTH / LightGame.PPM - blockSize * 2,
                    LightGame.HEIGHT / LightGame.PPM - blockSize * 2,
                    blockSize, blockSize);
        }

        if ((mask & LightGame.BIT_PINK) != 0) {
            sb.draw(blocks[3],
                    LightGame.WIDTH / LightGame.PPM - blockSize * 2,
                    LightGame.HEIGHT / LightGame.PPM - blockSize * 2,
                    blockSize, blockSize);
        }

        if ((mask & LightGame.BIT_YELLOW) != 0) {
            sb.draw(blocks[4],
                    LightGame.WIDTH / LightGame.PPM - blockSize * 2,
                    LightGame.HEIGHT / LightGame.PPM - blockSize * 2,
                    blockSize, blockSize);
        }

        sb.end();
    }
}
