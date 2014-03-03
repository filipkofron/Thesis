package cz.kofron.foodinventory.client;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by kofee on 3/2/14.
 */
public abstract class AddRemoveAbstractView extends View {
    public final static int ADD = 0, REMOVE = 1;

    private int type;
    private Paint paint;

    public AddRemoveAbstractView(Context context, int type, AttributeSet attributeSet)
    {
        super(context, attributeSet);

        this.type = type;

        paint = new Paint();
        paint.setColor(Color.BLACK);
    }

    public AddRemoveAbstractView(Context context, int type)
    {
        super(context);
        this.type = type;

        paint = new Paint();
        paint.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float x = getPaddingLeft();
        float y = getPaddingTop();

        float x2 = getPaddingRight();
        float y2 = getPaddingBottom();

        float w = canvas.getWidth() - (x + x2);
        float h = canvas.getHeight() - (y + y2);

        float sw = (w + h) / 15.0f;

        if(sw < 1.0f)
        {
            sw = 1.0f;
        }
        paint.setStrokeWidth(sw);

        switch (type)
        {
            case ADD:
                paint.setColor(Color.GREEN);
                canvas.drawLine(x, y + h / 2, x + w, y + h / 2, paint);
                canvas.drawLine(x + w / 2, y, x + w / 2, y + h, paint);
                break;
            case REMOVE:
                paint.setColor(Color.RED);

                canvas.drawLine(x, y + h / 2, x + w, y + h / 2, paint);
                break;
        }
    }
}
