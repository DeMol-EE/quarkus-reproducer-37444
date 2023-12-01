package org.acme;

import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Provider
@PreMatching
public class Filter implements ContainerRequestFilter {

    @Inject
    @ConfigProperty(name = "stuff")
    Optional<List<String>> stuff;

    @Inject
    Logger logger;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        logger.infof("injected: %s", stuff);
        // Sanity check: is the env var really set?
        List<String> locatedStuff = Arrays.stream(ConfigProvider
                        .getConfig()
                        .getOptionalValue("stuff", String[].class)
                        .orElse(new String[]{}))
                .toList();
        logger.infof("located: %s", locatedStuff);
    }
}
