package com.randybastards.bluejayburger;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class TicTacToeView extends View {

    private boolean touched = false;

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
        setVisibility(View.VISIBLE);
        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                touched = true;
                /* Todo find out why invalidate needs to be called to get Android to call onDraw */
                invalidate();
            }
        });
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
        if (touched) {
            canvas.drawCircle(getPivotX(), getPivotY(), getWidth() * 0.35f, paint);
        } else {
            canvas.drawLine(getWidth() * 0.3f, getHeight() * 0.7f, getWidth() * 0.7f, getHeight() * 0.3f, paint);
            canvas.drawLine(getWidth() * 0.3f, getHeight() * 0.3f, getWidth() * 0.7f, getHeight() * 0.7f, paint);
        }
    }
}
