package com.kdi.light.box.utils;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kdi.light.box.LightGame;
import com.kdi.light.box.entities.Player;

public class Background {

    private TextureRegion image;
    private OrthographicCamera cam;
    private Player player;

    private float x;
    private float y;

    private float dx;
    private float dy;

    private int numDrawX;
    private int numDrawY;

    public Background(TextureRegion image, OrthographicCamera cam, Player player) {
        this.image = image;
        this.cam = cam;
        this.player = player;

        numDrawX = (int) ((LightGame.WIDTH / LightGame.PPM) / (image.getRegionWidth() / LightGame.PPM) + 4);
        numDrawY = (int) ((LightGame.HEIGHT / LightGame.PPM) / (image.getRegionHeight() / LightGame.PPM) + 4);
    }

    public void update(float dt) {
        x -= (40 * player.body.getLinearVelocity().x / LightGame.PPM) * dt;
        y -= (40 * player.body.getLinearVelocity().y / LightGame.PPM) * dt;

        if (x + image.getRegionWidth() / LightGame.PPM < 0) x = 0;
        if (x - image.getRegionWidth() / LightGame.PPM > 0) x = 0;
        if (y + image.getRegionHeight() / LightGame.PPM < 0) y = 0;
        if (y - image.getRegionWidth() / LightGame.PPM > 0) y = 0;

        dx = (this.x + cam.position.x - cam.viewportWidth / 2) - (image.getRegionWidth() / LightGame.PPM) * 2;
        dy = (this.y + cam.position.y - cam.viewportHeight / 2) - (image.getRegionHeight() / LightGame.PPM) * 2;
    }

    public void render(SpriteBatch sb) {
        sb.begin();

        for (int row = 0; row < numDrawY; row++) {
            for (int col = 0; col < numDrawX; col++) {
                sb.draw(image,
                        dx + col * (image.getRegionWidth() / LightGame.PPM),
                        dy + row * (image.getRegionHeight() / LightGame.PPM),
                        image.getRegionWidth() / LightGame.PPM,
                        image.getRegionHeight() / LightGame.PPM);
            }
        }

        sb.end();
    }
}
