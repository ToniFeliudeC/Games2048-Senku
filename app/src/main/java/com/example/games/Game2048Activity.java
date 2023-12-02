package com.example.games;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Game2048Activity extends AppCompatActivity {

    private GestureDetector gestureDetector;
    private ArrayList<ArrayList<Integer>> game2048Board;
    private GridLayout gameGridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2048);

        this.gameGridLayout = findViewById(R.id.game2048Grid);

        // Inicializar el tablero del juego
        this.game2048Board = new ArrayList<ArrayList<Integer>>();
        this.game2048Board.add(new ArrayList<>(Arrays.asList(0, 0, 0, 2)));
        this.game2048Board.add(new ArrayList<>(Arrays.asList(0, 0, 0, 0)));
        this.game2048Board.add(new ArrayList<>(Arrays.asList(0, 0, 0, 0)));
        this.game2048Board.add(new ArrayList<>(Arrays.asList(0, 0, 0, 0)));

/*        TextView bestScore = findViewById(R.id.best_score_id);*/

        // String numero = Integer.toString(game2048Board.get(0).get(0));

        // bestScore.setText(numero);


        // Configurar el Gesture Detector
        gestureDetector = new GestureDetector(this, new SwipeGestureListener());

        // Configurar el Listener de Swipe en el GridLayout
        gameGridLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });

        Button backButton = findViewById(R.id.buttonBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Game2048Activity.this, MainActivity.class));
            }
        });
    }

    // Clase interna para manejar los gestos de deslizamiento
    private class SwipeGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float diffX = e2.getX() - e1.getX();
            float diffY = e2.getY() - e1.getY();

            if (Math.abs(diffX) > Math.abs(diffY)) {
                // Deslizamiento horizontal
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        // Deslizamiento hacia la derecha
                        swipeRight();
                    } else {
                        // Deslizamiento hacia la izquierda
                        swipeLeft();
                    }
                    return true;
                }
            } else {
                // Deslizamiento vertical
                if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        // Deslizamiento hacia abajo
                        swipeDown();
                    } else {
                        // Deslizamiento hacia arriba
                        swipeUp();
                    }
                    return true;
                }
            }

            return false;
        }
    }


    // Métodos para mergear la línea con 2048
    public static ArrayList<Integer> merge(ArrayList<Integer> line) {

        ArrayList<Integer> allZerosToTheRight = new ArrayList<>();
        for (Integer value : line) {
            if (value != 0) {
                allZerosToTheRight.add(value);
            }
        }

        while (allZerosToTheRight.size() < line.size()) {
            allZerosToTheRight.add(0);
        }

        mergeEqualNeighbours(allZerosToTheRight);

        ArrayList<Integer> result = new ArrayList<>();
        for (Integer value : allZerosToTheRight) {
            if (value != 0) {
                result.add(value);
            }
        }

        while (result.size() < line.size()) {
            result.add(0);
        }

        return result;
    }

    private static void mergeEqualNeighbours(ArrayList<Integer> array) {
        for (int i = 1; i < array.size(); i++) {
            if (array.get(i).equals(array.get(i - 1))) {
                array.set(i - 1, array.get(i) * 2);
                array.set(i, 0);
            }
        }
    }

    //////////////////////////////////////////////////////////////

    // Métodos para manejar los gestos de deslizamiento
    private void swipeRight() {

        this.game2048Board = Game2048Utils.rotateLeft(this.game2048Board);
        this.game2048Board = Game2048Utils.rotateLeft(this.game2048Board);
        for (int i = 0; i < this.game2048Board.size(); i++) {
            ArrayList<Integer> mergedLine = merge(this.game2048Board.get(i));
            // Actualizar la línea en el tablero
            for (int j = 0; j < this.game2048Board.get(i).size(); j++) {
                this.game2048Board.get(i).set(j, mergedLine.get(j));
            }
        }

        addRandomNumber();

        this.game2048Board = Game2048Utils.rotateRight(this.game2048Board);
        this.game2048Board = Game2048Utils.rotateRight(this.game2048Board);
        int childIndex = 0;
        for (ArrayList<Integer> line : this.game2048Board) {
            for (Integer cell : line) {
                TextView existingView = (TextView) gameGridLayout.getChildAt(childIndex);
                existingView.setText(Integer.toString(cell));
                childIndex++;
            }
        }
    }

    private void swipeLeft() {
        // Lógica para deslizar hacia la izquierda
        // Actualizar el tablero y la interfaz gráfica
        for (ArrayList<Integer> line : this.game2048Board) {
            ArrayList<Integer> mergedLine = merge(line);
            // Actualizar la línea en el tablero
            for (int i = 0; i < line.size(); i++) {
                line.set(i, mergedLine.get(i));
            }
        }

        addRandomNumber();

        // Actualizar las vistas en el GridLayout
        int childIndex = 0;
        for (ArrayList<Integer> line : this.game2048Board) {
            for (Integer cell : line) {
                // Obtener la vista existente en el GridLayout
                TextView existingView = (TextView) gameGridLayout.getChildAt(childIndex);
                // Actualizar el contenido de la vista
                existingView.setText(Integer.toString(cell));
                childIndex++;
            }
        }

    }

    private void swipeDown() {
        this.game2048Board = Game2048Utils.rotateRight(this.game2048Board);
        for (int i = 0; i < this.game2048Board.size(); i++) {
            ArrayList<Integer> mergedLine = merge(this.game2048Board.get(i));
            // Actualizar la línea en el tablero
            for (int j = 0; j < this.game2048Board.get(i).size(); j++) {
                this.game2048Board.get(i).set(j, mergedLine.get(j));
            }
        }

        addRandomNumber();

        this.game2048Board = Game2048Utils.rotateLeft(this.game2048Board);
        int childIndex = 0;
        for (ArrayList<Integer> line : this.game2048Board) {
            for (Integer cell : line) {
                TextView existingView = (TextView) gameGridLayout.getChildAt(childIndex);
                existingView.setText(Integer.toString(cell));
                childIndex++;
            }
        }
    }


    private void swipeUp() {
        this.game2048Board = Game2048Utils.rotateLeft(this.game2048Board);
        for (int i = 0; i < this.game2048Board.size(); i++) {
            ArrayList<Integer> mergedLine = merge(this.game2048Board.get(i));
            // Actualizar la línea en el tablero
            for (int j = 0; j < this.game2048Board.get(i).size(); j++) {
                this.game2048Board.get(i).set(j, mergedLine.get(j));
            }
        }

        addRandomNumber();

        this.game2048Board = Game2048Utils.rotateRight(this.game2048Board);
        int childIndex = 0;
        for (ArrayList<Integer> line : this.game2048Board) {
            for (Integer cell : line) {
                TextView existingView = (TextView) gameGridLayout.getChildAt(childIndex);
                existingView.setText(Integer.toString(cell));
                childIndex++;
            }
        }
    }

    private void addRandomNumber() {
        ArrayList<Integer> availablePositions = new ArrayList<>();
        
        for (int i = 0; i < game2048Board.size(); i++) {
            for (int j = 0; j < game2048Board.get(i).size(); j++) {
                if (game2048Board.get(i).get(j) == 0) {
                    availablePositions.add(i * game2048Board.size() + j);
                }
            }
        }
        if (!availablePositions.isEmpty()) {
            int randomPositionIndex = (int) (Math.random() * availablePositions.size());
            int position = availablePositions.get(randomPositionIndex);
            
            int row = position / game2048Board.size();
            int col = position % game2048Board.size();
            
            int newNumber = Math.random() < 0.9 ? 2 : 4;
            game2048Board.get(row).set(col, newNumber);
            
            int childIndex = row * game2048Board.size() + col;
            TextView cellView = (TextView) gameGridLayout.getChildAt(childIndex);
            cellView.setText(Integer.toString(newNumber));
        }
    }
}

