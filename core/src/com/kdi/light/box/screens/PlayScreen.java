package com.kdi.light.box.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kdi.light.box.LightGame;
import com.kdi.light.box.entities.Item;
import com.kdi.light.box.entities.Player;
import com.kdi.light.box.utils.AssetLoader;
import com.kdi.light.box.backgrounds.BackgroundParallax;
import com.kdi.light.box.utils.Controller;
import com.kdi.light.box.utils.LightContactListener;
import com.kdi.light.box.utils.WorldCreator;

import box2dLight.RayHandler;

public class PlayScreen implements Screen {

    private LightGame game;

    private OrthographicCamera gameCamera;
    private Viewport gameViewPort;
    private float camX;
    private float camY;
    private float tween = .07f;

    public World world;
    public WorldCreator worldCreator;
    public LightContactListener contactListener;

    private Box2DDebugRenderer b2dr;
    private Player player;
    private BackgroundParallax backgroundParallax;

    private RayHandler rayHandler;
    private float ambientLight = .4f;

    public TmxMapLoader mapLoader;
    public TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;

    private Controller controller;

    public PlayScreen(LightGame game) {
        this.game = game;
        gameCamera = new OrthographicCamera();
        gameViewPort = new FitViewport(LightGame.WIDTH / LightGame.PPM, LightGame.HEIGHT / LightGame.PPM, gameCamera);
        gameCamera.position.set(gameViewPort.getWorldWidth() / 2, gameViewPort.getWorldHeight() / 2, 0);
        world = new World(new Vector2(0, -10), true);
        contactListener = new LightContactListener();
        world.setContactListener(contactListener);
        b2dr = new Box2DDebugRenderer();

        rayHandler = new RayHandler(world);
        rayHandler.setAmbientLight(ambientLight);
        rayHandler.setShadows(true);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level1.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1 / LightGame.PPM);

        worldCreator = new WorldCreator(world, map);

        player = new Player(world, rayHandler, worldCreator);
        backgroundParallax = new BackgroundParallax(AssetLoader.backgrounds, gameCamera, player);

        controller = new Controller(game.batch);
    }

    public void update(float dt) {
        world.step(1 / 60f, 6, 2);

        for (Body body : contactListener.getBodiesToRemove()) {
            Item itemToRemove = (Item) body.getUserData();
            worldCreator.removeItem(itemToRemove);
            world.destroyBody(body);
        }
        contactListener.getBodiesToRemove().clear();

        rayHandler.update();

        backgroundParallax.update(dt);

        player.handleInput(controller);
        player.update(dt);

        camX += (player.body.getPosition().x - gameCamera.position.x) * tween;
        camY += (player.body.getPosition().y - gameCamera.position.y) * tween;

        gameCamera.position.set(camX, camY, 0);
        gameCamera.update();

        rayHandler.setCombinedMatrix(gameCamera);
        mapRenderer.setView(gameCamera);
        worldCreator.update(dt);
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(gameCamera.combined);

        backgroundParallax.render(game.batch);

        mapRenderer.render();
        worldCreator.render(game.batch);

        //b2dr.render(world, gameCamera.combined);
        rayHandler.render();

        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();

        if (Gdx.app.getType() == Application.ApplicationType.Android) controller.draw();
    }

    @Override
    public void resize(int width, int height) {
        gameViewPort.update(width, height);
        controller.resize(width, height);
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
}
