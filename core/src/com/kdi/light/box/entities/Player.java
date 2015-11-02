package com.kdi.light.box.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.kdi.light.box.LightGame;
import com.kdi.light.box.utils.Controller;
import com.kdi.light.box.utils.WorldCreator;

import box2dLight.PointLight;
import box2dLight.RayHandler;

public class Player extends Sprite {

    private final float MAX_SPEED = 4f;
    private final float INPULSE = 1f;
    private final float JUMP_HEIGHT = 8f;

    private final int SPRITE_RADIUS = 64;
    private final int COLLISION_RADIUS = 32;

    private final float START_X = 2;
    private final float START_Y = 3;

    public float wight;
    public float height;

    public Body body;
    public TextureRegion sprite;
    private PointLight pointLight;
    private WorldCreator worldCreator;

    public Player(World world, RayHandler rayHandler, WorldCreator worldCreator) {
        super(new Texture(Gdx.files.internal("player.png")));
        this.worldCreator = worldCreator;

        wight = SPRITE_RADIUS / LightGame.PPM;
        height = SPRITE_RADIUS / LightGame.PPM;

        createPlayerBody(world);

        pointLight = new PointLight(rayHandler, 100, Color.BLUE, 300 / LightGame.PPM, 0, 0);
        pointLight.setSoftnessLength(0.5f);
        pointLight.setXray(false);
        pointLight.attachToBody(body);
        pointLight.setContactFilter(body.getFixtureList().first().getFilterData());

        sprite = new TextureRegion(getTexture(), 0, 0, SPRITE_RADIUS, SPRITE_RADIUS);

        setBounds(START_X, START_Y, wight, height);
        setRegion(sprite);
    }

    public void update() {
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
    }

    public void handleInput(Controller controller) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || (controller.jumpPressed && Gdx.input.justTouched())) {
            body.applyLinearImpulse(new Vector2(0, JUMP_HEIGHT), body.getWorldCenter(), true);
        }
        if ((Gdx.input.isKeyPressed(Input.Keys.RIGHT) || controller.rightPressed) && body.getLinearVelocity().x <= MAX_SPEED) {
            body.applyLinearImpulse(new Vector2(INPULSE, 0), body.getWorldCenter(), true);
        }
        if ((Gdx.input.isKeyPressed(Input.Keys.LEFT) || controller.leftPressed) && body.getLinearVelocity().x >= -MAX_SPEED) {
            body.applyLinearImpulse(new Vector2(-INPULSE, 0), body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || (controller.colorPressed && Gdx.input.justTouched())) {
            switchBlocks();
            controller.updateColorButton(body.getFixtureList().first().getFilterData().maskBits);
        }
    }

    private void createPlayerBody(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(START_X, START_Y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(COLLISION_RADIUS / LightGame.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = LightGame.BIT_PLAYER;
        fixtureDef.filter.maskBits = LightGame.BIT_BLUE;
        body.createFixture(fixtureDef);
        worldCreator.switchBlockTiles(LightGame.BIT_BLUE);
    }

    private void switchBlocks() {
        Filter filter = body.getFixtureList().first().getFilterData();
        short bits = filter.maskBits;

        if ((bits & LightGame.BIT_BLUE) != 0) {
            bits &= ~LightGame.BIT_BLUE;
            bits |= LightGame.BIT_BROWN;
            pointLight.setColor(Color.BROWN);
        } else if ((bits & LightGame.BIT_BROWN) != 0) {
            bits &= ~LightGame.BIT_BROWN;
            bits |= LightGame.BIT_GREEN;
            pointLight.setColor(Color.GREEN);
        } else if ((bits & LightGame.BIT_GREEN) != 0) {
            bits &= ~LightGame.BIT_GREEN;
            bits |= LightGame.BIT_PINK;
            pointLight.setColor(Color.PINK);
        } else if ((bits & LightGame.BIT_PINK) != 0) {
            bits &= ~LightGame.BIT_PINK;
            bits |= LightGame.BIT_YELLOW;
            pointLight.setColor(Color.YELLOW);
        } else if ((bits & LightGame.BIT_YELLOW) != 0) {
            bits &= ~LightGame.BIT_YELLOW;
            bits |= LightGame.BIT_BLUE;
            pointLight.setColor(Color.BLUE);
        }

        filter.maskBits = bits;
        body.getFixtureList().first().setFilterData(filter);
        pointLight.setContactFilter(filter);
        worldCreator.switchBlockTiles(bits);
    }
}
