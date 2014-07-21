package compile_inc.compile;

import android.view.animation.Animation;
import android.view.animation.Transformation;

import javax.xml.transform.Transformer;

/**
 * Created by DUCA on 7/21/2014.
 */
public class ExpandAnimation extends Animation {

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t){
        super.applyTransformation(interpolatedTime, t);

        if(interpolatedTime < 1.0f) {

            //TODO - Make this have smooth animations for listView expansions
        }
    }
}