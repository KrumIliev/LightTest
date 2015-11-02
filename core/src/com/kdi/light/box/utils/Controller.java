package com.kdi.light.box.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kdi.light.box.LightGame;

public class Controller {

    private Viewport viewport;
    private Stage stage;

    public boolean leftPressed;
    public boolean rightPressed;
    public boolean jumpPressed;
    public boolean colorPressed;

    private TextureRegion[] colorButtonStates;

    private Image leftButton;
    private Image rightButton;
    private Image jumpButton;
    private Image colorButton;

    public Controller(SpriteBatch batch) {
        viewport = new FitViewport(LightGame.WIDTH, LightGame.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        colorButtonStates = new TextureRegion[5];
        for (int i = 0; i < colorButtonStates.length; i++) {
            colorButtonStates[i] = new TextureRegion(AssetLoader.hud, i * 160, 0, 160, 160);
        }

        initButtons();

        Table table = new Table();
        table.bottom();
        table.setFillParent(true);

        table.add(leftButton).expandX().padBottom(50).padLeft(50);
        table.add(rightButton).expandX().padBottom(50).padRight(300);
        table.add(jumpButton).expandX().padBottom(50);
        table.add(colorButton).expandX().padBottom(50).padRight(50);

        stage.addActor(table);
    }

    public void draw() {
        stage.draw();
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    private void initButtons() {
        leftButton = new Image(new TextureRegion(AssetLoader.hud, 0, 160, 160, 160));
        leftButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = false;
            }
        });

        rightButton = new Image(new TextureRegion(AssetLoader.hud, 160, 160, 160, 160));
        rightButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = false;
            }
        });

        jumpButton = new Image(new TextureRegion(AssetLoader.hud, 320, 160, 160, 160));
        jumpButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                jumpPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                jumpPressed = false;
            }
        });

        colorButton = new Image(colorButtonStates[0]);
        colorButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                colorPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                colorPressed = false;
            }
        });
    }

    public void updateColorButton(short bits) {
        if ((bits & LightGame.BIT_BLUE) != 0) {
            colorButton.setDrawable(new SpriteDrawable(new Sprite(colorButtonStates[0])));
        }

        if ((bits & LightGame.BIT_BROWN) != 0) {
            colorButton.setDrawable(new SpriteDrawable(new Sprite(colorButtonStates[1])));
        }

        if ((bits & LightGame.BIT_GREEN) != 0) {
            colorButton.setDrawable(new SpriteDrawable(new Sprite(colorButtonStates[2])));
        }

        if ((bits & LightGame.BIT_PINK) != 0) {
            colorButton.setDrawable(new SpriteDrawable(new Sprite(colorButtonStates[3])));
        }

        if ((bits & LightGame.BIT_YELLOW) != 0) {
            colorButton.setDrawable(new SpriteDrawable(new Sprite(colorButtonStates[4])));
        }
    }
}
