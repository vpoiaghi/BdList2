package framework.fragments;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * Created by VINCENT on 16/11/2015.
 */
public class FragmentParameters implements Serializable {

    private static final long serialVersionUID = 465489764;

    transient public static final int PRM_FROM_FRAGMENT = 0;

    public final Map<Integer, Serializable> parameters = new HashMap();


    public void add(int key, Serializable value) {
        parameters.put(new Integer(key), value);
    }

    public Serializable get(int key) {
        return parameters.get(new Integer(key));
    }
}
