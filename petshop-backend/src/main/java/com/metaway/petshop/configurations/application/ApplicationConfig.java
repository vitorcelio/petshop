package com.metaway.petshop.configurations.application;

import com.metaway.petshop.configurations.security.SecuredFilter;
import com.metaway.petshop.controllers.v2.ArchiveControllerV2;
import com.metaway.petshop.controllers.v2.BreedControllerV2;
import com.metaway.petshop.controllers.v2.PetControllerV2;
import com.metaway.petshop.controllers.v2.UserControllerV2;
import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@ApplicationPath("/api/v2")
@Configuration
public class ApplicationConfig extends ResourceConfig {

    public ApplicationConfig() {
        packages("com.metaway.petshop.configurations.exceptions");
        register(UserControllerV2.class);
        register(PetControllerV2.class);
        register(BreedControllerV2.class);
        register(ArchiveControllerV2.class);
        register(SecuredFilter.class);
    }

}
