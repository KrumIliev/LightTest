package com.kdi.light.box.utils;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.kdi.light.box.LightGame;
import com.kdi.light.box.screens.PlayScreen;

public class WorldCreator {

    public WorldCreator(PlayScreen screen) {
        BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;

        for (MapObject object : screen.map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / LightGame.PPM, (rect.getY() + rect.getHeight() / 2) / LightGame.PPM);
            body = screen.world.createBody(bodyDef);
            shape.setAsBox(rect.getWidth() / 2 / LightGame.PPM, rect.getHeight() / 2 / LightGame.PPM);
            fixtureDef.shape = shape;
            fixtureDef.filter.categoryBits = LightGame.BIT_BLUE;
            body.createFixture(fixtureDef);
        }

        for (MapObject object : screen.map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / LightGame.PPM, (rect.getY() + rect.getHeight() / 2) / LightGame.PPM);
            body = screen.world.createBody(bodyDef);
            shape.setAsBox(rect.getWidth() / 2 / LightGame.PPM, rect.getHeight() / 2 / LightGame.PPM);
            fixtureDef.shape = shape;
            fixtureDef.filter.categoryBits = LightGame.BIT_YELLOW;
            body.createFixture(fixtureDef);
        }

        for (MapObject object : screen.map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / LightGame.PPM, (rect.getY() + rect.getHeight() / 2) / LightGame.PPM);
            body = screen.world.createBody(bodyDef);
            shape.setAsBox(rect.getWidth() / 2 / LightGame.PPM, rect.getHeight() / 2 / LightGame.PPM);
            fixtureDef.shape = shape;
            fixtureDef.filter.categoryBits = LightGame.BIT_PINK;
            body.createFixture(fixtureDef);
        }

        for (MapObject object : screen.map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / LightGame.PPM, (rect.getY() + rect.getHeight() / 2) / LightGame.PPM);
            body = screen.world.createBody(bodyDef);
            shape.setAsBox(rect.getWidth() / 2 / LightGame.PPM, rect.getHeight() / 2 / LightGame.PPM);
            fixtureDef.shape = shape;
            fixtureDef.filter.categoryBits = LightGame.BIT_BROUN;
            body.createFixture(fixtureDef);
        }

        for (MapObject object : screen.map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / LightGame.PPM, (rect.getY() + rect.getHeight() / 2) / LightGame.PPM);
            body = screen.world.createBody(bodyDef);
            shape.setAsBox(rect.getWidth() / 2 / LightGame.PPM, rect.getHeight() / 2 / LightGame.PPM);
            fixtureDef.shape = shape;
            fixtureDef.filter.categoryBits = LightGame.BIT_GREEN;
            body.createFixture(fixtureDef);
        }
    }
}
