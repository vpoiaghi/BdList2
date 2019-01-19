package project.services;

import java.util.HashMap;

import project.services.abs.IService;

/**
 * Created by VINCENT on 01/01/2019.
 *
 */
public final class FactoryServices {

    private static final HashMap<Object, Object> h = new HashMap<>();

    private FactoryServices(){}

    // Retourne une instance unique d'un Service
    // Usage : ServiceEditions s = FactoryServices.g(ServiceEditions.class);
    public static <T extends IService> T get(final Class<T> type) {

        T svc = null;

        try {
            @SuppressWarnings("unchecked")
            T tmpSvc = (T) h.get(type);
            svc = tmpSvc;
        } catch(ClassCastException e) {
            // rien à faire
        }

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
