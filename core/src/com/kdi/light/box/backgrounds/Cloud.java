package com.kdi.light.box.backgrounds;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kdi.light.box.LightGame;

import java.util.Random;

/**
 * Created by Krum on 11/3/2015.
 */
public class Cloud {

    private float x;
    private float y;
    private float speed;

    private float dx;
    private float dy;

    private TextureRegion image;
    private OrthographicCamera camera;

    public boolean remove = false;

    public Cloud(TextureRegion image, OrthographicCamera camera) {
        this.image = image;
        this.camera = camera;

        float minY = LightGame.HEIGHT / 2 / LightGame.PPM;
        float maxY = LightGame.HEIGHT / LightGame.PPM;

        Random random = new Random();
        y = random.nextFloat() * (maxY - minY) + minY;
        x = (LightGame.WIDTH + image.getRegionWidth()) / LightGame.PPM;
        speed = random.nextInt(80 - 20) + 20;
    }

    public void update(float dt) {
        x -= (speed / LightGame.PPM) * dt;

        dx = x + camera.position.x - camera.viewportWidth / 2;
        dy = y + camera.position.y - camera.viewportHeight / 2;

        if (x + image.getRegionWidth() / LightGame.PPM < 0) remove = true;
    }

    public void render(SpriteBatch spriteBatch) {
        spriteBatch.begin();
        spriteBatch.draw(image, dx, dy, image.getRegionWidth() / LightGame.PPM, image.getRegionHeight() / LightGame.PPM);
        spriteBatch.end();
    }
}
