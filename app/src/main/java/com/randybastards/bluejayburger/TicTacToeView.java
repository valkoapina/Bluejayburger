package com.randybastards.bluejayburger;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class TicTacToeView extends View {

    enum State {Blank, X, O}

    private OnPlayerChooseListener onPlayerChooseListener;

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
        invalidate();
    }

    private State state = State.Blank;

    public interface OnPlayerChooseListener {
        void OnPlayerChoose(TicTacToeView ticTacToeView);
    }

    public TicTacToeView(Context context) {
        super(context);
        initialize();
    }

    public TicTacToeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public TicTacToeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    private void initialize() {
        setBackgroundColor(Color.rgb(0xFF, 0xFF, 0xFF));
        setVisibility(View.VISIBLE);
        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPlayerChooseListener.OnPlayerChoose(TicTacToeView.this);
            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        try {
            onPlayerChooseListener = (OnPlayerChooseListener) this.getContext();
        } catch (ClassCastException e) {
            throw new ClassCastException(this.getContext().toString() + " must implement OnPlayerChooseListener");
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
                MeasureSpec.getSize(heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
        if (state == State.O) {
            canvas.drawCircle(getPivotX(), getPivotY(), getWidth() * 0.35f, paint);
        } else if (state == State.X) {
            canvas.drawLine(getWidth() * 0.3f, getHeight() * 0.7f, getWidth() * 0.7f, getHeight() * 0.3f, paint);
            canvas.drawLine(getWidth() * 0.3f, getHeight() * 0.3f, getWidth() * 0.7f, getHeight() * 0.7f, paint);
        } else {
            canvas.drawColor(Color.rgb(0xFF, 0xFF, 0xFF));
        }
    }
}
