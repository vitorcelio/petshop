package com.metaway.petshop.configurations.security;

import com.metaway.petshop.configurations.exceptions.ForbiddenException;
import com.metaway.petshop.configurations.exceptions.NotFoundException;
import com.metaway.petshop.configurations.exceptions.UnauthorizedException;
import com.metaway.petshop.configurations.exceptions.ValidationException;
import com.metaway.petshop.enums.RolesEnum;
import com.metaway.petshop.mappers.UserDao;
import com.metaway.petshop.models.myBatis.UserV2;
import com.metaway.petshop.services.auth.TokenService;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
@Provider
@Priority(Priorities.AUTHENTICATION)
@Secured
public class SecuredFilter implements ContainerRequestFilter {

    private final TokenService tokenService;
    private final UserDao userDao;

    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        Method method = resourceInfo.getResourceMethod();
        Class<?> clazz = resourceInfo.getResourceClass();

        String authorization = requestContext.getHeaderString("Authorization");

        if (ObjectUtils.isEmpty(authorization)) {
            throw new UnauthorizedException("Usuário não está autorizado!");
        }

        String token = authorization.substring(7);

        if (!ObjectUtils.isEmpty(token)) {
            String cpf = tokenService.validateToken(token);
            UserV2 user = userDao.findByCpf(cpf);

            if (user == null) {
                throw new NotFoundException("Usuário não encontrado!");
            }

            if (!validateRole(method, clazz, user.getRole().getName())) {
                throw new ForbiddenException("Usuário sem permissão!");
            }

            try {

                final SecurityContext originalContext = requestContext.getSecurityContext();

                requestContext.setSecurityContext(new SecurityContext() {
                    @Override
                    public Principal getUserPrincipal() {
                        return user;
                    }

                    @Override
                    public boolean isUserInRole(String role) {
                        return user.getRole().getName().equals(role);
                    }

                    @Override
                    public boolean isSecure() {
                        return originalContext.isSecure();
                    }

                    @Override
                    public String getAuthenticationScheme() {
                        return "Bearer";
                    }
                });


            } catch (Exception e) {
                log.error("Erro ao validar token: {}", e.getMessage());
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
                throw new ValidationException("Usuário não encontrado!");
            }
        }
    }

    private boolean validateRole(Method method, Class<?> clazz, String role) {
        if (method != null && method.isAnnotationPresent(Secured.class)) {
            RolesEnum[] roles = method.getAnnotation(Secured.class).roles();
            if (!ObjectUtils.isEmpty(roles)) {
                List<String> rolesString = Arrays.stream(roles).map(RolesEnum::name).toList();

                return rolesString.contains(role);
            }
        }

        if (clazz != null && clazz.isAnnotationPresent(Secured.class)) {
            RolesEnum[] roles = clazz.getAnnotation(Secured.class).roles();
            if (!ObjectUtils.isEmpty(roles)) {
                List<String> rolesString = Arrays.stream(roles).map(RolesEnum::name).toList();

                return rolesString.contains(role);
            }
        }

        return true;
    }
}
