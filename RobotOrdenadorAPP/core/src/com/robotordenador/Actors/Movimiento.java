package com.robotordenador.Actors;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

/**
 * Created by Felipe on 24-12-2018.
 */



public class Movimiento {

    // en la realidad el costo será tomado en tiempo, por ahora son valores ficticios basados en distancia
    public float costoDelMovimiento;
    public static final float costoBase = 100f; // el costo de tomar y dejar el cuadrado

    public int posicionOrigen, posicionDestino;
    public float xOrigen, yOrigen, xDestino, yDestino;
    MathSquare cuadrado;

    public Movimiento(int posicionOrigen, int posicionDestino, MathSquare cuadrado)
    {
        this.posicionOrigen = posicionOrigen;
        this.posicionDestino = posicionDestino;
        this.cuadrado = cuadrado;

        // calcula las coordenadas de origen según la posición que se da
        if(posicionOrigen<5)
        {
            xOrigen = 150*posicionOrigen + 100;
            yOrigen = 350;
        }
        else if(posicionOrigen<10)
        {
            xOrigen = 150*posicionOrigen  - 650;
            yOrigen = 200;
        }
        else if(posicionOrigen<15)
        {
            xOrigen = 150*posicionOrigen - 1400;
            yOrigen = 50;
        }
        else
        {
            xOrigen = 850;
            yOrigen = 200;
        }

        // calcula las coordenadas de destino según la posición que se da
        if(posicionDestino<5)
        {
            xDestino = 150*posicionDestino + 100;
            yDestino = 350;
        }
        else if(posicionDestino<10)
        {
            xDestino = 150*posicionDestino  - 650;
            yDestino = 200;
        }
        else if(posicionDestino<15)
        {
            xDestino = 150*posicionDestino - 1400;
            yDestino = 50;
        }
        else
        {
            xDestino = 850;
            yDestino = 200;
        }
        costoDelMovimiento = costoBase + distancia(xOrigen, xDestino, yOrigen, yDestino);

    }

    // método para realizar movimiento del cuadrado
    public void realizar(int numeroDelay)
    {
        cuadrado.addAction(Actions.sequence(Actions.delay(numeroDelay*1.2F), Actions.moveTo(xDestino, yDestino, 1.2F)));
    }

    public float getCostoDelMovimiento()
    {
        return costoDelMovimiento;
    }


    // método auxiliar para calcular distancia entre 2 coordiandas
    public float distancia(float x1, float x2, float y1, float y2)
    {
        return (float)Math.sqrt( (x1-x2)*(x1-x2) + (y1-y2)*(y1-y2) );
    }
}
