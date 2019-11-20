package com.robotordenador.Actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Felipe on 03-12-2017.
 */

public class ActorJugador extends Actor {


    private Texture jugador;
    // position stuffs


    // logic stuff


    public ActorJugador(Texture cardd)
    {
        jugador = cardd;
    }

    @Override
    public void act(float delta) {
        super.act(delta);



    }

    @Override
    public void draw(Batch batch, float parentAlpha) {


        batch.draw(jugador, getX(), getY());
    }
}
