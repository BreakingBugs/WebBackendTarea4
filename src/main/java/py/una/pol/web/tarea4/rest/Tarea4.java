package py.una.pol.web.tarea4.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class Tarea4 extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> set = new HashSet<Class<?>>(2);
        set.add(JacksonConfig.class);
        set.add(CustomerService.class);
        set.add(ItemService.class);
        set.add(ProviderService.class);
        return set;
    }
}
