package com.robotordenador;

/**
 * Created by Felipe on 06-12-2017.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.robotordenador.Actors.ActorJugador;
import com.robotordenador.Actors.Graphics;
import com.robotordenador.Actors.MathSquare;
import com.robotordenador.Actors.Menu;
import com.robotordenador.Actors.Movimiento;
import com.robotordenador.Actors.Shape;
import com.robotordenador.Actors.TextActor;
import com.robotordenador.BaseScreen;
import com.robotordenador.MainGame;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Felipe on 02-12-2017.
 */

public class OrderScreen extends BaseScreen {

    private MathSquare[] squares = new MathSquare[16];
    private MathSquare[] copiaSquares = new MathSquare[16];
    private Shape[] shapes = new Shape[16];
    private boolean[] ocupado = new boolean[16];
    private ArrayList<Movimiento> movs = new ArrayList<Movimiento>();

    // lo usaremos para ir viendo c
    private int[] squareValues = new int[16];
    private TextActor textActor, textActor2;
    private TextButton fakeButton;
    private Skin skin;
    private Image image;
    private ActorJugador actorJugador;
    private TextButton gameOver;
    private BitmapFont ft;
    private Image correctIncorrect;
    private Texture mathGameBackground;

    //logic
    private int sum, playerSum, counter;

    /* crates and contains the logic of the math game, a simple
    game where you have to get a number by adding the ones below
     */
    public OrderScreen(final MainGame game)
    {
        super(game);
        screenType = 1; // screen without timer
        screenNumber = 12; // screen number
        stage = new Stage(new FitViewport(1050, 600));
        counter = 0;
        score = 0;

        /*
        // timer
        myTimer = new MyTimer(60f, this);
        myTimer.font.getData().setScale(0.4f);
        myTimer.setPosition(20, 700);
        stage.addActor(myTimer);
        */

        // creates menu
        menu = new Menu(this);
        menu.setPosition(950, 480);
        menu.addCaptureListener(new InputListener()
        {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {


                menu.doWhenTouched();

                return true;
            }
        });
        stage.addActor(menu);

        skin = new Skin(Gdx.files.internal("starsoldierui/star-soldier-ui.json")); // part of the button

        // squares containers
        shapes[15] = new Shape();
        stage.addActor(shapes[15]);
        Graphics graphics = shapes[15].graphics();

        graphics.beginLine();
        graphics.setColor(Color.WHITE);
        graphics.rect(850, 200, 130, 130);
        graphics.end();

        // botón para ordenar automaticamente
        TextButton AI = new TextButton("AI", this.skin);
        AI.setPosition(880, 50);
        AI.addCaptureListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                ordena();
            }
        });
        stage.addActor(AI);

        prepareLevel();

    }

    public void prepareLevel()
    {
        final float[] movimientos = new float[2];

        ocupado[15] = false; // inicialmente no hay ningún cuadrado en el expacio auxiliar

        // texto que muestra el número de movimientos efectuados
        textActor = new TextActor(new String("Movimientos: ")+ (int)movimientos[0]);
        textActor.setPosition(100, 530);
        stage.addActor(textActor);

        textActor2 = new TextActor(new String("Coste: ")+ movimientos[1]);
        textActor2.setPosition(500, 530);
        stage.addActor(textActor2);

        // crea los cuadrados y los agrega al stage
        squares = createSquares(sum);
        // se crea una copia del array original por si se quiere reintentar el juego con los mismos números
        for(int i=0; i<squares.length; i++)
        {
            copiaSquares[i] = new MathSquare(squares[i].getValue());
        }



        for(int i=0; i<squares.length-1; i++)
        {

            ocupado[i] = true; // este espacio está ocupado ahora
            if(i<5){ squares[i].setPosition(150*i + 100, 350);}
            else if(i<10){ squares[i].setPosition(150*i - 650, 200); }
            else{ squares[i].setPosition(150*i - 1400, 50); }
            squares[i].setTouchable(Touchable.enabled); // los cuadrados son clickeables
            squares[i].setBounds(squares[i].getX(), squares[i].getY(), squares[i].getWidth(), squares[i].getHeight());


            final int finalI = i;
            /*funcion donde va la lógica de que pasa cuando se toma
            y arrastran los cuadrados
             */
            squares[i].addListener(new DragListener() {

                float originalX;
                float originalY;

                // cuando empieza a arrastrar el cuadrado, se guarda su posición original
                public void dragStart(InputEvent event, float x, float y, int pointer)
                {
                    originalX = squares[finalI].getX();
                    originalY = squares[finalI].getY();
                }

                // cuando se arrastra se cambia su posición (se mueve junto al mouse)
                public void drag(InputEvent event, float x, float y, int pointer) {
                    squares[finalI].moveBy(x - squares[finalI].getWidth() / 2, y - squares[finalI].getHeight() / 2);

                }

                /* cuando el cuadrado deja de ser arrastrado se guarda su posición original y se lo
                 posiciona en el espacio vacío más cercano si es que está lo suficientemente cerca
                 */
                public void dragStop(InputEvent event, float x, float y, int pointer)
                {
                    float distance;

                    // detecta donde estaba al principio el cuadrado
                    int dondeEstaba=15;
                    for(int i=0; i<15; i++) {
                        float a, b;
                        if (i < 5) {
                            a = 150 * i + 100;
                            b = 350;
                        } else if (i < 10) {
                            a = 150 * i - 650;
                            b = 200;
                        } else {
                            a = 150 * i - 1400;
                            b = 50;
                        }
                        if(a==originalX && b==originalY)
                        {
                            dondeEstaba = i;
                        }

                    }

                    boolean flag=true;
                    for(int i=0; i<15 && flag; i++){
                        float a, b;
                        if(i<5){
                            a = 150*i + 100;
                            b = 350;
                            }
                        else if(i<10) {
                            a = 150 * i - 650;
                            b = 200;
                        }
                        else{
                            a= 150*i - 1400;
                            b = 50;
                        }
                        distance = Math.abs(squares[finalI].getX() - a) + Math.abs(squares[finalI].getY()  - b);
                        if(distance<50 && !ocupado[i]) // si está cerca del espacio y además no está tomado
                        {
                            squares[finalI].setPosition(a, b);
                            flag = false;
                            ocupado[i] = true;
                            squareValues[i] = squares[finalI].getValue();
                            squareValues[dondeEstaba] = -1;
                            ocupado[dondeEstaba] = false;
                            movimientos[0]++;
                            movimientos[1] = movimientos[1] + (new Movimiento(dondeEstaba, i, squares[i])).costoDelMovimiento;



                            if(verifica(squareValues))
                            {
                                correctIncorrect = new Image(game.getManager().get("correct.png", Texture.class));
                                correctIncorrect.setPosition(320 - 180, 0);
                                stage.addActor(correctIncorrect);
                                Timer.schedule(new Timer.Task() {

                                    public void run() {

                                        menu.correcto();
                                    }
                                }, 0.4f);
                            }
                        }

                    }

                    distance = Math.abs(squares[finalI].getX() - 850);
                    if(distance<100 && flag && !ocupado[15])
                    {
                        squares[finalI].setPosition(850, 200);
                        ocupado[15] = true;
                        ocupado[dondeEstaba] = false;
                        squareValues[15] = squareValues[dondeEstaba];
                        squareValues[dondeEstaba] = -1;
                        movimientos[0]++;
                        movimientos[1] = movimientos[1] + (new Movimiento(dondeEstaba, 15, squares[15])).costoDelMovimiento;

                    }

                    else if(flag)
                    {
                        squares[finalI].setPosition(originalX, originalY);
                    }

                    // Actualiza los textos con numero de movimientos y sus costos
                    textActor.text = "Movimientos: "+(int)movimientos[0];
                    textActor2.text = "Coste: "+(int)movimientos[1];
                }



            });


            stage.addActor(squares[i]);
        }

    }

    public void reintentar()
    {
        removeSomeActors();


        final float[] movimientos = new float[2];

        ocupado[15] = false; // inicialmente no hay ningún cuadrado en el expacio auxiliar

        // texto que muestra el número de movimientos efectuados
        textActor = new TextActor(new String("Movimientos: ")+ (int)movimientos[0]);
        textActor.setPosition(100, 530);
        stage.addActor(textActor);

        textActor2 = new TextActor(new String("Coste: ")+ movimientos[1]);
        textActor2.setPosition(500, 530);
        stage.addActor(textActor2);

        squares = copiaSquares.clone();

        for(int i=0; i<squares.length-1; i++)
        {

            ocupado[i] = true; // este espacio está ocupado ahora
            if(i<5){ squares[i].setPosition(150*i + 100, 350);}
            else if(i<10){ squares[i].setPosition(150*i - 650, 200); }
            else{ squares[i].setPosition(150*i - 1400, 50); }
            squares[i].setTouchable(Touchable.enabled); // los cuadrados son clickeables
            squares[i].setBounds(squares[i].getX(), squares[i].getY(), squares[i].getWidth(), squares[i].getHeight());


            final int finalI = i;
            /*funcion donde va la lógica de que pasa cuando se toma
            y arrastran los cuadrados
             */
            squares[i].addListener(new DragListener() {

                float originalX;
                float originalY;

                // cuando empieza a arrastrar el cuadrado, se guarda su posición original
                public void dragStart(InputEvent event, float x, float y, int pointer)
                {
                    originalX = squares[finalI].getX();
                    originalY = squares[finalI].getY();
                }

                // cuando se arrastra se cambia su posición (se mueve junto al mouse)
                public void drag(InputEvent event, float x, float y, int pointer) {
                    squares[finalI].moveBy(x - squares[finalI].getWidth() / 2, y - squares[finalI].getHeight() / 2);

                }

                /* cuando el cuadrado deja de ser arrastrado se guarda su posición original y se lo
                 posiciona en el espacio vacío más cercano si es que está lo suficientemente cerca
                 */
                public void dragStop(InputEvent event, float x, float y, int pointer)
                {
                    float distance;

                    // detecta donde estaba al principio el cuadrado
                    int dondeEstaba=15;
                    for(int i=0; i<15; i++) {
                        float a, b;
                        if (i < 5) {
                            a = 150 * i + 100;
                            b = 350;
                        } else if (i < 10) {
                            a = 150 * i - 650;
                            b = 200;
                        } else {
                            a = 150 * i - 1400;
                            b = 50;
                        }
                        if(a==originalX && b==originalY)
                        {
                            dondeEstaba = i;
                        }

                    }

                    boolean flag=true;
                    for(int i=0; i<15 && flag; i++){
                        float a, b;
                        if(i<5){
                            a = 150*i + 100;
                            b = 350;
                        }
                        else if(i<10) {
                            a = 150 * i - 650;
                            b = 200;
                        }
                        else{
                            a= 150*i - 1400;
                            b = 50;
                        }
                        distance = Math.abs(squares[finalI].getX() - a) + Math.abs(squares[finalI].getY()  - b);
                        if(distance<50 && !ocupado[i]) // si está cerca del espacio y además no está tomado
                        {
                            squares[finalI].setPosition(a, b);
                            flag = false;
                            ocupado[i] = true;
                            squareValues[i] = squares[finalI].getValue();
                            squareValues[dondeEstaba] = -1;
                            ocupado[dondeEstaba] = false;
                            movimientos[0]++;
                            movimientos[1] = movimientos[1] + (new Movimiento(dondeEstaba, i, squares[i])).costoDelMovimiento;



                            if(verifica(squareValues))
                            {
                                correctIncorrect = new Image(game.getManager().get("correct.png", Texture.class));
                                correctIncorrect.setPosition(320 - 180, 0);
                                stage.addActor(correctIncorrect);
                                Timer.schedule(new Timer.Task() {

                                    public void run() {

                                        menu.correcto();
                                    }
                                }, 0.4f);
                            }
                        }

                    }

                    distance = Math.abs(squares[finalI].getX() - 850);
                    if(distance<100 && flag && !ocupado[15])
                    {
                        squares[finalI].setPosition(850, 200);
                        ocupado[15] = true;
                        ocupado[dondeEstaba] = false;
                        squareValues[15] = squareValues[dondeEstaba];
                        squareValues[dondeEstaba] = -1;
                        movimientos[0]++;
                        movimientos[1] = movimientos[1] + (new Movimiento(dondeEstaba, 15, squares[15])).costoDelMovimiento;

                    }

                    else if(flag)
                    {
                        squares[finalI].setPosition(originalX, originalY);
                    }

                    // updates textActor
                    textActor.text = "Movimientos: "+(int)movimientos[0];
                    textActor2.text = "Coste: "+(int)movimientos[1];
                }



            });


            stage.addActor(squares[i]);
        }
    }

    /* función para crear cuadrados numerados del 1 al 15 y luego desordenarlos
     */
    public MathSquare[] createSquares(int sum)
    {
        MathSquare[] createdSquares = new MathSquare[16];
        ArrayList<MathSquare> list = new ArrayList();
        ArrayList<Integer> numeros = new ArrayList(); // helps shuffling list

        for(int i=0; i<15; i++)
        {
            numeros.add(i+1);
        }
        Collections.shuffle(numeros);

        for(int i=0; i<15; i++)
        {
            list.add(new MathSquare(numeros.get(i)));
        }
        /*
        // shuffles list so the random numbers that added up result in sum are not the first 3
        Collections.shuffle(list);

        for(int i=0; i<createdSquares.length; i++)
        {
            createdSquares[i] = list.get(i);
        }
        */
        for(int i=0; i<createdSquares.length - 1; i++)
        {
            createdSquares[i] = list.get(i);
            squareValues[i] = list.get(i).getValue();
        }
        squareValues[15] = -1; // inicialmente el espacio auxiliar está desocupado
        createdSquares[15] = new MathSquare(-1);

        return createdSquares;
    }


    //remueve actores, función auxiliar
    private void removeSomeActors()
    {
        for(int i=0; i<squares.length; i++)
        {
            squares[i].remove();
        }

        if(correctIncorrect!=null)
        {
            correctIncorrect.remove();
        }

        textActor.remove();
        textActor2.remove();
    }


    // ordena los cuadrados automaticamente
    private void ordena()
    {

        /* no solo vamos a ordenar, sino que antes vamos a encontrar un camino eficiente para hacerlo
        , para lo cual necesitaremos una copia de los cuadrados ocupados sobre la cual trabajar */
        boolean[] ocupadoCopy = ocupado.clone();
        int costos[] = new int[2];
        int aux[] = new int[2];
        int posicionMinimo = 0;
        costos= recorre(0);
        System.out.print("Costo: "+costos[0]);
        System.out.print("   Movimientos: "+costos[1]);
        System.out.println();
        for(int i=1; i<15; i++)
        {
            aux = recorre(i);
            if(aux[0] < costos[0])
            {
                costos[0] = aux[0];
                posicionMinimo = i;
            }
            System.out.print("Costo: "+aux[0]);
            System.out.print("   Movimientos: "+aux[1]);
            System.out.println();
        }
        System.out.println("El minimo es: "+costos[0]);


        int [] movYcostos = new int[2];
        movYcostos = creaMovimientos(posicionMinimo);
        textActor.text = "Movimientos: "+(int)movYcostos[1];
        textActor2.text = "Coste: "+(int)movYcostos[0];

        int auxx = 0;
        for (Movimiento element: movs) {
            System.out.println("movimiento de: "+(element.posicionOrigen+1)+" a "+(element.posicionDestino+1));
            if(element.posicionOrigen==15)
            {
                System.out.println("Pos: "+element.xDestino+", "+element.yDestino);
            }
            element.realizar(auxx);
            auxx++;
        }



    }

    public int[] recorre(int comienza)
    {
        int[] costoYmovimientos = new int[2];
        costoYmovimientos[0] = 0; // costo (medido en distancia de pixeles recorrida)
        costoYmovimientos[1] = 0; // número de movimientos


        int[] valuesCopy = squareValues.clone();
        int espacioVacio=espacioDesocupado(valuesCopy);
        Movimiento mov;

        // primer movimiento
        if(!verifica(valuesCopy) && (valuesCopy[comienza]!=comienza+1)) // si es que no está arreglado y tampoco el número en su lugar
        {
            swap(valuesCopy, comienza, espacioVacio);
            mov = new Movimiento(comienza, espacioVacio, squares[comienza]);
            espacioVacio = comienza;
            costoYmovimientos[0] += mov.costoDelMovimiento;
            costoYmovimientos[1]++;

        }

        while(!verifica(valuesCopy)){ // mientras no estén ordenados

            // llenamos el espacio vacío con el número que le corresponde


            if(espacioVacio != 15)
            {
                boolean flag = true;
                for(int i=0; i<16 && flag; i++)
                {
                    if(valuesCopy[i]==espacioVacio+1)
                    {
                        flag = false;
                        swap(valuesCopy, espacioVacio, i);
                        mov = new Movimiento(i, espacioVacio, squares[i]);
                        espacioVacio = i;
                        //swap(squares, i, espacioVacio);
                        costoYmovimientos[0] += mov.costoDelMovimiento;
                        costoYmovimientos[1]++;
                    }
                }
            }
            else // se encuentra el primer número fuera de su lugar
            {
                boolean flag = true;
                for(int i=0; i<15 && flag; i++)
                {
                    if(valuesCopy[i] != i+1)
                    {
                        flag = false;
                        swap(valuesCopy, espacioVacio, i);
                        mov = new Movimiento(i, espacioVacio, squares[i]);
                        espacioVacio = i;
                        costoYmovimientos[0] += mov.costoDelMovimiento;
                        costoYmovimientos[1]++;
                    }
                }
            }
        }

        for(int i=0; i<15; i++)
        {
            System.out.print(valuesCopy[i] + "  ");
        }
        return costoYmovimientos;
    }

    public int[] creaMovimientos(int comienza)
    {
        int[] costoYmovimientos = new int[2];
        costoYmovimientos[0] = 0; // costo (medido en distancia de pixeles recorrida)
        costoYmovimientos[1] = 0; // número de movimientos



        int[] valuesCopy = squareValues.clone();
        int espacioVacio=espacioDesocupado(valuesCopy);


        int aux=comienza;
        // primer movimiento
        if(!verifica(valuesCopy) && (valuesCopy[comienza]!=comienza+1)) // si es que no está arreglado y tampoco el número en su lugar
        {
            swap(valuesCopy, comienza, espacioVacio);
            Movimiento mov = new Movimiento(comienza, espacioVacio, squares[comienza]);

            movs.add(mov);

            espacioVacio = comienza;
            costoYmovimientos[0] += mov.costoDelMovimiento;
            costoYmovimientos[1]++;

        }

        while(!verifica(valuesCopy)){ // mientras no estén ordenados

            // llenamos el espacio vacío con el número que le corresponde



            if(espacioVacio != 15)
            {
                boolean flag = true;
                for(int i=0; i<16 && flag; i++)
                {
                    if(valuesCopy[i]==espacioVacio+1)
                    {
                        flag = false;
                        swap(valuesCopy, espacioVacio, i);
                        //swap(squares, i, espacioVacio);
                        Movimiento mov;
                        if(i==15)
                        {
                            mov = new Movimiento(i, espacioVacio, squares[aux]);
                            movs.add(mov);
                        }
                        else
                        {
                            mov = new Movimiento(i, espacioVacio, squares[i]);
                            movs.add(mov);
                        }

                        espacioVacio = i;
                        costoYmovimientos[0] += mov.costoDelMovimiento;
                        costoYmovimientos[1]++;
                    }
                }
            }
            else // se encuentra el primer número fuera de su lugar
            {
                boolean flag = true;
                for(int i=0; i<15 && flag; i++)
                {
                    if(valuesCopy[i] != i+1)
                    {
                        flag = false;
                        swap(valuesCopy, espacioVacio, i);
                        aux=i;
                        //swap(squares, i, espacioVacio);
                        Movimiento mov = new Movimiento(i, espacioVacio, squares[i]);
                        movs.add(mov);
                        espacioVacio = i;
                        costoYmovimientos[0] += mov.costoDelMovimiento;
                        costoYmovimientos[1]++;
                    }
                }
            }
        }

        for(int i=0; i<15; i++)
        {
            System.out.print(valuesCopy[i] + "  ");
        }

        return costoYmovimientos;

    }



    // verifica si los cuadrados están en orden
    public boolean verifica(int[] values)
    {
        if(values[0]==-1)
        {
            return false;
        }
        for(int i=1; i<15; i++)
        {
            if(values[i-1]>values[i])
            {
                return false;
            }
        }
        return true;
    }

    // funcion auxiliar para intercambiar 2 elementos (de tipo entero)
    public static final void swap (int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    // lo mismo pero para objects
    public static final <T> void swap (T[] a, int i, int j) {
        T t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    // función auxiliar, detecta espacio vacío
    public int espacioDesocupado(int[] values)
    {

        for(int i=0; i<values.length; i++)
        {
            if(values[i] == -1)
            {
                return i;
            }
        }
        return 15;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this.stage);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.215f, 0.1254f, 0.3764f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();

    }
}