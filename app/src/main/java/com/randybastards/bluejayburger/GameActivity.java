package com.randybastards.bluejayburger;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.GridLayout;
import android.widget.Toast;

public class GameActivity extends Activity implements TicTacToeView.OnPlayerChooseListener {

    private TicTacToeView[] ticTacToeViews;
    private GridLayout gameBoard;

    enum Player {X, O}

    private Player currentPlayer;

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
                        int numberOfColumns = gameBoard.getColumnCount();
                        int numberOfRows = gameBoard.getRowCount();
                        int w = pWidth / numberOfColumns;
                        int h = pHeight / numberOfRows;

                        for (int yPosition = 0; yPosition < numberOfRows; yPosition++) {
                            for (int xPosition = 0; xPosition < numberOfColumns; xPosition++) {
                                GridLayout.LayoutParams params =
                                        (GridLayout.LayoutParams) ticTacToeViews[yPosition * numberOfColumns + xPosition].getLayoutParams();
                                params.width = w - 2 * MARGIN;
                                params.height = h - 2 * MARGIN;
                                params.setMargins(MARGIN, MARGIN, MARGIN, MARGIN);
                                ticTacToeViews[yPosition * numberOfColumns + xPosition].setLayoutParams(params);
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

    @Override
    public void OnPlayerChoose(TicTacToeView ticTacToeView) {
        int[] position = (int[]) ticTacToeView.getTag();
        if (currentPlayer == Player.X) {
            ticTacToeView.setState(TicTacToeView.State.O);
            currentPlayer = Player.O;
        } else {
            ticTacToeView.setState(TicTacToeView.State.X);
            currentPlayer = Player.X;
        }
        if (checkWinConditions(position, ticTacToeView.getState())) {
            Toast.makeText(GameActivity.this,
                    currentPlayer.toString() + " wins, ha ha ha!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void createViews() {
        int numberOfColumns = gameBoard.getColumnCount();
        int numberOfRows = gameBoard.getRowCount();
        ticTacToeViews = new TicTacToeView[numberOfColumns * numberOfRows];
        for (int yPosition = 0; yPosition < numberOfRows; yPosition++) {
            for (int xPosition = 0; xPosition < numberOfColumns; xPosition++) {
                TicTacToeView ticTacToeView = new TicTacToeView(this);
                ticTacToeView.setTag(new int[]{xPosition, yPosition});
                ticTacToeViews[yPosition * numberOfColumns + xPosition] = ticTacToeView;
                gameBoard.addView(ticTacToeView);
            }
        }
    }

    private boolean checkWinConditions(int[] position, TicTacToeView.State state) {
        boolean retval = false;
        if (checkColumn(position[0], state) || checkRow(position[1], state)) {
            retval = true;
        }
        return retval;
    }

    private boolean checkColumn(int xPosition, TicTacToeView.State state) {
        boolean retval = false;
        for (int i = 0; i < gameBoard.getColumnCount(); i++) {
            if (ticTacToeViews[xPosition + i * gameBoard.getColumnCount()].getState() != state)
                break;
            if (i == gameBoard.getColumnCount() - 1) {
                retval = true;
            }
        }
        return retval;
    }

    private boolean checkRow(int yPosition, TicTacToeView.State state) {
        boolean retval = false;
        for (int i = 0; i < gameBoard.getRowCount(); i++) {
            if (ticTacToeViews[yPosition * gameBoard.getRowCount() + i].getState() != state)
                break;
            if (i == gameBoard.getRowCount() - 1) {
                retval = true;
            }
        }
        return retval;
    }
}
