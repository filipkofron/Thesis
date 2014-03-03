package cz.kofron.foodinventory.client;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by kofee on 3/2/14.
 */
public class RemoveView extends AddRemoveAbstractView {
    public RemoveView(Context context, AttributeSet attributeSet)
    {
        super(context, AddRemoveAbstractView.REMOVE, attributeSet);
    }

    public RemoveView(Context context)
    {
        super(context, AddRemoveAbstractView.REMOVE);
    }
}
