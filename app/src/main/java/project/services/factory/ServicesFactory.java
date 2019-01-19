package project.services.factory;

import java.util.HashMap;

import project.services.abs.IService;

/**
 * Created by VINCENT on 01/01/2019.
 *
 */
public final class ServicesFactory {

    private static final HashMap<Object, Object> h = new HashMap<>();

    private ServicesFactory(){}

    // Retourne une instance unique d'un Service
    // Usage : ServiceEditions s = ServicesFactory.g(ServiceEditions.class);
    public static <T extends IService> T get(final Class<T> type) {

        T svc = (T) h.get(type);

        if (svc == null) {

            try {
                svc = type.newInstance();
                h.put(type, svc);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

        return svc;
    }

}
