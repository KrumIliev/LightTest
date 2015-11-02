package com.kdi.light.box.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.kdi.light.box.LightGame;

public class Item extends Sprite {

    private float x;
    private float y;

    private int size;

    private int animationFrames;
    private int yOffset;
    private float animationSpeed = 0.1f;
    private Animation animation;
    private float stateTimer;

    private World world;

    private int points;

    public Item(Texture texture, float x, float y, int size, World world, int animationFrames, int yOffset, int points) {
        super(texture);
        this.x = x;
        this.y = y;
        this.size = size;
        this.world = world;
        this.animationFrames = animationFrames;
        this.yOffset = yOffset;
        this.points = points;

        createBody();
        initAnimation();

        setBounds(x, y, size / LightGame.PPM, size / LightGame.PPM);
        setRegion(animation.getKeyFrame(stateTimer, true));
    }

    public void update(float dt) {
        setRegion(getFrame(dt));
    }

    public void collect() {
        System.out.println("item collected");
    }

    private void createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x + size / 2 / LightGame.PPM, y + size / 2 / LightGame.PPM);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(size / 2 / LightGame.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = LightGame.BIT_ITEM;
        fixtureDef.filter.maskBits = LightGame.BIT_PLAYER;
        body.createFixture(fixtureDef).setUserData(this);
        body.setUserData(this);
    }

    private void initAnimation() {
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < animationFrames; i++) {
            frames.add(new TextureRegion(getTexture(), i * size, yOffset, size, size));
        }
        animation = new Animation(animationSpeed, frames);
    }

    public TextureRegion getFrame(float dt) {
        TextureRegion region;
        region = animation.getKeyFrame(stateTimer, true);
        stateTimer += dt;
        return region;
    }

    public int getPoints() {
        return points;
    }
}
