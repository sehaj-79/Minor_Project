package com.aliferous.minorproject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CustomView extends View {
    private Paint paint;
    private List<PointF> path;

    private List<PointF> nodes;


    public CustomView(Context context) {
        super(context);
        init();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);

        path = new ArrayList<>();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (nodes != null) {
            paint.setColor(Color.GREEN);
            for (PointF node : nodes) {
                canvas.drawCircle(node.x, node.y, 15, paint);
            }
        }

        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        for (int i = 0; i < path.size() - 1; i++) {
            PointF startPoint = path.get(i);
            PointF endPoint = path.get(i + 1);
            canvas.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y, paint);
        }
    }



    public void setNodes(List<PointF> nodes) {
        this.nodes = nodes;
        invalidate();
    }

    public void setPath(List<PointF> newPath) {
        this.path = newPath;
        invalidate();
    }
}
