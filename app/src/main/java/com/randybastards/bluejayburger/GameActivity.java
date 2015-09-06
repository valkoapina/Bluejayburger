package com.randybastards.bluejayburger;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.GridLayout;

public class GameActivity extends Activity {

    private TicTacToeView[] ticTacToeViews;
    private GridLayout gameBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        gameBoard = (GridLayout) findViewById(R.id.gameBoard);
        createViews();
        gameBoard.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {

                        final int MARGIN = 5;

                        int pWidth = gameBoard.getWidth();
                        int pHeight = gameBoard.getHeight();
                        int numOfCol = gameBoard.getColumnCount();
                        int numOfRow = gameBoard.getRowCount();
                        int w = pWidth / numOfCol;
                        int h = pHeight / numOfRow;

                        for (int yPos = 0; yPos < numOfRow; yPos++) {
                            for (int xPos = 0; xPos < numOfCol; xPos++) {
                                GridLayout.LayoutParams params =
                                        (GridLayout.LayoutParams) ticTacToeViews[yPos * numOfCol + xPos].getLayoutParams();
                                params.width = w - 2 * MARGIN;
                                params.height = h - 2 * MARGIN;
                                params.setMargins(MARGIN, MARGIN, MARGIN, MARGIN);
                                ticTacToeViews[yPos * numOfCol + xPos].setLayoutParams(params);
                            }
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    private void createViews() {
        int numberOfColumns = gameBoard.getColumnCount();
        int numberOfRows = gameBoard.getRowCount();
        ticTacToeViews = new TicTacToeView[numberOfColumns * numberOfRows];
        for (int yPosition = 0; yPosition < numberOfRows; yPosition++) {
            for (int xPosition = 0; xPosition < numberOfColumns; xPosition++) {
                TicTacToeView ticTacToeView = new TicTacToeView(this);
                ticTacToeViews[yPosition * numberOfColumns + xPosition] = ticTacToeView;
                gameBoard.addView(ticTacToeView);
            }
        }
    }
}
