package com.kdi.light.box.utils;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.kdi.light.box.entities.Item;

import java.util.ArrayList;
import java.util.List;

public class LightContactListener implements ContactListener {

    private List<Body> bodiesToRemove;

    public LightContactListener() {
        super();
        bodiesToRemove = new ArrayList<Body>();
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if (fixA.getUserData() == "player" || fixB.getUserData() == "player") {
            Fixture head = fixA.getUserData() == "player" ? fixA : fixB;
            Fixture object = head == fixA ? fixB : fixA;

            if (object.getUserData() != null && Item.class.isAssignableFrom(object.getUserData().getClass())) {
                ((Item) object.getBody().getUserData()).collect();
                bodiesToRemove.add(object.getBody());
            }
        }
    }

    public List<Body>  getBodiesToRemove () {
        return bodiesToRemove;
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
