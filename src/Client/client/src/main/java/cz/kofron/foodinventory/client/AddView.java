package cz.kofron.foodinventory.client;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

/**
 * Created by kofee on 3/2/14.
 */
public class AddView extends AddRemoveAbstractView {

    public AddView(Context context, AttributeSet attributeSet)
    {
        super(context, AddRemoveAbstractView.ADD, attributeSet);
    }

    public AddView(Context context)
    {
        super(context, AddRemoveAbstractView.ADD);
    }
}
