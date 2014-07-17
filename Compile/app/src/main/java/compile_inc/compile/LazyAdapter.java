package compile_inc.compile;

import android.content.Context;
import android.widget.ArrayAdapter;

/**
 * Created by DUCA on 7/16/2014.
 */

//this is class will add stuff to the cards in our card layout --- TODO make it work... ---
public class LazyAdapter extends ArrayAdapter<RowItem> {
    Context context;

    public LazyAdapter(Context context, int resourceId, Contact contact) {
        super(context, resourceId);
        this.context = context;
    }

}
