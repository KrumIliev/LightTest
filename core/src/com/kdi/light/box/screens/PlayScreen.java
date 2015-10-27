package com.kdi.light.box.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kdi.light.box.LightGame;
import com.kdi.light.box.entities.Player;

import box2dLight.PointLight;
import box2dLight.RayHandler;

public class PlayScreen implements Screen {

    private OrthographicCamera gameCamera;
    private Viewport gameViewPort;

    private World world;
    private Box2DDebugRenderer b2dr;
    private Player player;

    private RayHandler rayHandler;
    private PointLight pointLight;

    public PlayScreen() {
        gameCamera = new OrthographicCamera();
        gameViewPort = new FitViewport(LightGame.WIDTH / LightGame.PPM, LightGame.HEIGHT / LightGame.PPM, gameCamera);
        gameCamera.position.set(gameViewPort.getWorldWidth(), gameViewPort.getWorldHeight(), 0);
        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();

        player = new Player(world);
        createBox(player.body.getPosition().x - 3, player.body.getPosition().y, 20 / LightGame.PPM, 100 / LightGame.PPM);
        createBox(player.body.getPosition().x + 3, player.body.getPosition().y, 20 / LightGame.PPM, 100 / LightGame.PPM);
        createBox(player.body.getPosition().x, player.body.getPosition().y + 3, 100 / LightGame.PPM, 20 / LightGame.PPM);
        createBox(player.body.getPosition().x, player.body.getPosition().y - 3, 100 / LightGame.PPM, 20 / LightGame.PPM);

        rayHandler = new RayHandler(world);
        rayHandler.setAmbientLight(0.5f);
        pointLight = new PointLight(rayHandler, 100, Color.WHITE, 3 / LightGame.PPM, 0, 0);
        //pointLight.attachToBody(player.body);
    }

    public void update(float dt) {
        world.step(1 / 60f, 6, 2);

        rayHandler.update();

        player.handleInput(dt);

        gameCamera.position.set(player.body.getPosition().x, player.body.getPosition().y, 0);
        gameCamera.update();

        rayHandler.setCombinedMatrix(gameCamera.combined.cpy().scl(LightGame.PPM));
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        b2dr.render(world, gameCamera.combined);
        rayHandler.render();
    }

    @Override
    public void resize(int width, int height) {
        gameViewPort.update(width, height);
    }

    @Override
    public void show() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        world.dispose();
        b2dr.dispose();
        rayHandler.dispose();
    }

    private void createBox(float x, float y, float width, float height) {
        BodyDef bdef = new BodyDef();
        bdef.position.set(x, y);
        bdef.type = BodyDef.BodyType.StaticBody;
        Body body = world.createBody(bdef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width, height);
        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        body.createFixture(fdef);
    }
}
