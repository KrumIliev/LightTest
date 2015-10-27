package com.kdi.light.box.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.kdi.light.box.LightGame;

public class Player {

    public Body body;

    public Player(World world) {
        createPlayerBody(world);
        body.setLinearDamping(20f);
    }

    public void handleInput(float dt) {
        float x = 0;
        float y = 0;


        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            y += 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            y -= 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            x += 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            x -= 1;
        }

        if (x != 0) body.setLinearVelocity(x * 350 * dt, body.getLinearVelocity().y);
        if (y != 0) body.setLinearVelocity(body.getLinearVelocity().x, y * 350 * dt);
    }

    private void createPlayerBody(World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(0, 0);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(30 / LightGame.PPM, 30 / LightGame.PPM);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
    }
}
