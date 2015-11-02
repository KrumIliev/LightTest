package com.kdi.light.box.utils;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.kdi.light.box.LightGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WorldCreator {

    private static final int ID_BLUE = 1;
    private static final int ID_BROWN = 2;
    private static final int ID_GREEN = 3;
    private static final int ID_PINK = 4;
    private static final int ID_YELLOW = 5;
    private static final int ID_TRANS_BLUE = 6;
    private static final int ID_TRANS_BROWN = 7;
    private static final int ID_TRANS_GREEN = 8;
    private static final int ID_TRANS_PINK = 9;
    private static final int ID_TRANS_YELLOW = 10;

    private World world;
    private TiledMap tiledMap;

    private HashMap<Short, List<Body>> allBlocks;
    private TiledMapTileLayer tileLayer;
    private TiledMapTileSet tileSet;

    public WorldCreator(World world, TiledMap tiledMap) {
        this.world = world;
        this.tiledMap = tiledMap;
        tileLayer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
        tileSet = tiledMap.getTileSets().getTileSet("blocks");

        allBlocks = new HashMap<Short, List<Body>>();
        createWorld();
    }

    public void createWorld() {
        BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;

        for (MapObject object : tiledMap.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / LightGame.PPM, (rect.getY() + rect.getHeight() / 2) / LightGame.PPM);
            body = world.createBody(bodyDef);
            shape.setAsBox(rect.getWidth() / 2 / LightGame.PPM, rect.getHeight() / 2 / LightGame.PPM);
            fixtureDef.shape = shape;
            fixtureDef.filter.categoryBits = LightGame.BIT_BLUE;
            body.createFixture(fixtureDef);
            addBlockToList(LightGame.BIT_BLUE, body);
        }

        for (MapObject object : tiledMap.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / LightGame.PPM, (rect.getY() + rect.getHeight() / 2) / LightGame.PPM);
            body = world.createBody(bodyDef);
            shape.setAsBox(rect.getWidth() / 2 / LightGame.PPM, rect.getHeight() / 2 / LightGame.PPM);
            fixtureDef.shape = shape;
            fixtureDef.filter.categoryBits = LightGame.BIT_YELLOW;
            body.createFixture(fixtureDef);
            addBlockToList(LightGame.BIT_YELLOW, body);
        }

        for (MapObject object : tiledMap.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / LightGame.PPM, (rect.getY() + rect.getHeight() / 2) / LightGame.PPM);
            body = world.createBody(bodyDef);
            shape.setAsBox(rect.getWidth() / 2 / LightGame.PPM, rect.getHeight() / 2 / LightGame.PPM);
            fixtureDef.shape = shape;
            fixtureDef.filter.categoryBits = LightGame.BIT_PINK;
            body.createFixture(fixtureDef);
            addBlockToList(LightGame.BIT_PINK, body);
        }

        for (MapObject object : tiledMap.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / LightGame.PPM, (rect.getY() + rect.getHeight() / 2) / LightGame.PPM);
            body = world.createBody(bodyDef);
            shape.setAsBox(rect.getWidth() / 2 / LightGame.PPM, rect.getHeight() / 2 / LightGame.PPM);
            fixtureDef.shape = shape;
            fixtureDef.filter.categoryBits = LightGame.BIT_BROWN;
            body.createFixture(fixtureDef);
            addBlockToList(LightGame.BIT_BROWN, body);
        }

        for (MapObject object : tiledMap.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / LightGame.PPM, (rect.getY() + rect.getHeight() / 2) / LightGame.PPM);
            body = world.createBody(bodyDef);
            shape.setAsBox(rect.getWidth() / 2 / LightGame.PPM, rect.getHeight() / 2 / LightGame.PPM);
            fixtureDef.shape = shape;
            fixtureDef.filter.categoryBits = LightGame.BIT_GREEN;
            body.createFixture(fixtureDef);
            addBlockToList(LightGame.BIT_GREEN, body);
        }
    }

    private void addBlockToList(short bit, Body body) {
        if (allBlocks.containsKey(bit)) {
            allBlocks.get(bit).add(body);
        } else {
            List<Body> bodies = new ArrayList<Body>();
            bodies.add(body);
            allBlocks.put(bit, bodies);
        }
    }

    public void switchBlockTiles(short newBit) {
        TiledMapTileLayer.Cell cell;

        for (short bit : allBlocks.keySet()) {
            for (Body body : allBlocks.get(bit)) {
                cell = tileLayer.getCell((int) (body.getPosition().x * LightGame.PPM / 64), (int) (body.getPosition().y * LightGame.PPM / 64));
                cell.setTile(tileSet.getTile(getTileTextureID(bit, bit == newBit)));
            }
        }
    }

    private int getTileTextureID(short bit, boolean isNew) {
        switch (bit) {
            case LightGame.BIT_BLUE:
                return isNew ? ID_BLUE : ID_TRANS_BLUE;
            case LightGame.BIT_BROWN:
                return isNew ? ID_BROWN : ID_TRANS_BROWN;
            case LightGame.BIT_GREEN:
                return isNew ? ID_GREEN : ID_TRANS_GREEN;
            case LightGame.BIT_PINK:
                return isNew ? ID_PINK : ID_TRANS_PINK;
            case LightGame.BIT_YELLOW:
                return isNew ? ID_YELLOW : ID_TRANS_YELLOW;
            default:
                return isNew ? ID_BLUE : ID_TRANS_BLUE;
        }
    }
}
