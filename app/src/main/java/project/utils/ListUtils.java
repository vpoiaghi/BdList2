package project.utils;

import java.util.Collections;
import java.util.List;

/**
 *
 * Created by VINCENT on 17/04/2016.
 */
public class ListUtils {

    public static void SortAndRemoveDoubleItems(final List list) {

        if (list != null && list.size() > 0) {

            try {
                Collections.sort(list);
            } catch (NullPointerException e) {
                int t = 0;
            }

            int index = 1;
            Object oldId = null;
            Object curId = null;

            while (index < list.size()) {

                curId = list.get(index);

                if (curId.equals(oldId)) {
                    list.remove(index);
                } else {
                    oldId = curId;
                    index++;
                }
            }
        }

    }

}
