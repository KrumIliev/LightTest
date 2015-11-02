package com.kdi.light.box.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.kdi.light.box.LightGame;
import com.kdi.light.box.utils.AssetLoader;
import com.kdi.light.box.utils.Controller;
import com.kdi.light.box.utils.WorldCreator;

import box2dLight.PointLight;
import box2dLight.RayHandler;

public class Player extends Sprite {

    public enum State {FALLING, JUMPING, STANDING, RUNNING}

    private final float MAX_SPEED = 4f;
    private final float INPULSE = 1f;
    private final float JUMP_HEIGHT = 8f;

    private final int SPRITE_WIDTH = 100;
    private final int SPRITE_HEIGHT = 138;

    private final float START_X = 2;
    private final float START_Y = 3;

    public State currentState;
    public State previousState;
    private boolean runningRight;
    private float stateTimer;

    public Body body;
    private PointLight pointLight;
    private WorldCreator worldCreator;

    private TextureRegion playerJump;
    private TextureRegion playerHurt;
    private Animation playerRun;
    private Animation playerIdle;

    public Player(World world, RayHandler rayHandler, WorldCreator worldCreator) {
        super(AssetLoader.player);
        this.worldCreator = worldCreator;

        currentState = State.STANDING;
        previousState = State.STANDING;

        stateTimer = 0;
        runningRight = true;

        createPlayer(world, rayHandler);
        initTexturesAndAnimation();

        setBounds(START_X, START_Y, SPRITE_WIDTH / LightGame.PPM, SPRITE_HEIGHT / LightGame.PPM);
        setRegion(playerJump);
    }

    public void update(float dt) {
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();

        TextureRegion region;

        switch (currentState) {
            case JUMPING:
            case FALLING:
                region = playerJump;
                break;
            case RUNNING:
                region = playerRun.getKeyFrame(stateTimer, true);
                break;
            case STANDING:
            default:
                region = playerIdle.getKeyFrame(stateTimer, true);
                break;
        }

        if ((body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        } else if ((body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    public State getState() {
        if ((body.getLinearVelocity().y > 0 && currentState == State.JUMPING) || (body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.JUMPING;

        if (body.getLinearVelocity().y < -0.5) return State.FALLING;
        if (body.getLinearVelocity().x != 0) return State.RUNNING;

        return State.STANDING;
    }

    public void handleInput(Controller controller) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || (controller.jumpPressed && Gdx.input.justTouched())) {
            body.applyLinearImpulse(new Vector2(0, JUMP_HEIGHT), body.getWorldCenter(), true);
            currentState = State.JUMPING;
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

    private void switchBlocks() {
        Filter filter = body.getFixtureList().first().getFilterData();
        short bits = filter.maskBits;
        short worldBit = LightGame.BIT_BLUE;

        if ((bits & LightGame.BIT_BLUE) != 0) {
            bits &= ~LightGame.BIT_BLUE;
            bits |= LightGame.BIT_BROWN;
            pointLight.setColor(Color.BROWN);
            worldBit = LightGame.BIT_BROWN;
        } else if ((bits & LightGame.BIT_BROWN) != 0) {
            bits &= ~LightGame.BIT_BROWN;
            bits |= LightGame.BIT_GREEN;
            pointLight.setColor(Color.GREEN);
            worldBit = LightGame.BIT_GREEN;
        } else if ((bits & LightGame.BIT_GREEN) != 0) {
            bits &= ~LightGame.BIT_GREEN;
            bits |= LightGame.BIT_PINK;
            pointLight.setColor(Color.PINK);
            worldBit = LightGame.BIT_PINK;
        } else if ((bits & LightGame.BIT_PINK) != 0) {
            bits &= ~LightGame.BIT_PINK;
            bits |= LightGame.BIT_YELLOW;
            pointLight.setColor(Color.YELLOW);
            worldBit = LightGame.BIT_YELLOW;
        } else if ((bits & LightGame.BIT_YELLOW) != 0) {
            bits &= ~LightGame.BIT_YELLOW;
            bits |= LightGame.BIT_BLUE;
            pointLight.setColor(Color.BLUE);
            worldBit = LightGame.BIT_BLUE;
        }

        filter.maskBits = bits;
        body.getFixtureList().first().setFilterData(filter);
        body.getFixtureList().get(1).setFilterData(filter);
        pointLight.setContactFilter(filter);
        worldCreator.switchBlockTiles(worldBit);
    }

    private void createPlayer(World world, RayHandler rayHandler) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(START_X, START_Y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(42 / LightGame.PPM);
        shape.setPosition(new Vector2(0, -25 / LightGame.PPM));

        CircleShape shape1 = new CircleShape();
        shape1.setRadius(42 / LightGame.PPM);
        shape1.setPosition(new Vector2(0, -15 / LightGame.PPM));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = LightGame.BIT_PLAYER;
        fixtureDef.filter.maskBits = LightGame.BIT_BLUE | LightGame.BIT_ITEM;
        body.createFixture(fixtureDef).setUserData("player");

        fixtureDef.shape = shape1;
        body.createFixture(fixtureDef).setUserData("player");

        worldCreator.switchBlockTiles(LightGame.BIT_BLUE);

        pointLight = new PointLight(rayHandler, 100, Color.BLUE, 300 / LightGame.PPM, 0, 0);
        pointLight.setSoftnessLength(0.5f);
        pointLight.setXray(false);
        pointLight.attachToBody(body);
        pointLight.setContactFilter(body.getFixtureList().first().getFilterData());
    }

    private void initTexturesAndAnimation() {
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 2; i < 4; i++) {
            frames.add(new TextureRegion(getTexture(), i * SPRITE_WIDTH, 0, SPRITE_WIDTH, SPRITE_HEIGHT));
        }

        playerIdle = new Animation(0.3f, frames);
        frames.clear();

        for (int i = 4; i < 6; i++) {
            frames.add(new TextureRegion(getTexture(), i * SPRITE_WIDTH, 0, SPRITE_WIDTH, SPRITE_HEIGHT));
        }
        playerRun = new Animation(0.1f, frames);

        playerHurt = new TextureRegion(getTexture(), 0, 0, SPRITE_WIDTH, SPRITE_HEIGHT);
        playerJump = new TextureRegion(getTexture(), SPRITE_WIDTH, 0, SPRITE_WIDTH, SPRITE_HEIGHT);
    }
}
