package com.kdi.light.box.backgrounds;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.kdi.light.box.entities.Player;
import com.kdi.light.box.utils.AssetLoader;

import java.util.Random;

public class BackgroundParallax {

    private OrthographicCamera camera;

    private Array<Background> backgrounds;
    private Array<Cloud> clouds;
    private TextureRegion[] cloudTextures;

    private float timer;
    private int nextCloud;
    private Random random;

    public BackgroundParallax(Texture image, OrthographicCamera cam, Player player) {
        this.camera = cam;

        backgrounds = new Array<Background>();
        backgrounds.add(new Background(new TextureRegion(image, 0, 768 * 2, 1280, 768), cam, player, false, true, 0));
        backgrounds.add(new Background(new TextureRegion(image, 0, 768, 1280, 768), cam, player, false, true, 40));
        backgrounds.add(new Background(new TextureRegion(image, 0, 0, 1280, 768), cam, player, false, true, 60));

        cloudTextures = new TextureRegion[6];
        int index = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                cloudTextures[index] = new TextureRegion(AssetLoader.clouds, j * 230, i * 130, 230, 130);
                index++;
            }
        }

        clouds = new Array<Cloud>();
        random = new Random();
        timer = 0;
        nextCloud = random.nextInt(80 - 20) + 20;
    }

    public void update(float dt) {
        for (Background background : backgrounds) {
            background.update(dt);
        }

        timer = timer + (2 * dt);
        if (timer > nextCloud) {
            clouds.add(new Cloud(cloudTextures[random.nextInt(5)], camera));
            timer = 0;
            nextCloud = random.nextInt(40 - 20) + 20;
        }

        for (Cloud cloud : clouds) {
            if (cloud.remove) {
                clouds.removeValue(cloud, true);
            } else {
                cloud.update(dt);
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (Background background : backgrounds) {
            background.render(batch);
        }

        for (Cloud cloud : clouds) {
            cloud.render(batch);
        }
    }
}
