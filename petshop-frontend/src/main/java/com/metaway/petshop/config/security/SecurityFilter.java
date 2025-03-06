package com.metaway.petshop.config.security;

import com.metaway.petshop.util.PetShopUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class SecurityFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        String url = req.getServletPath();

        String cpfSession = (String) session.getAttribute(PetShopUtil.CPF_SESSION);

        if (!PetShopUtil.isEmpty(cpfSession)) {

            String token = (String) session.getAttribute(PetShopUtil.TOKEN_SESSION);

            if (!PetShopUtil.isEmpty(token)) {

                String cpf = new TokenConfiguration().validateToken(token);

                if (PetShopUtil.isEmpty(cpf) || !cpf.equals(cpfSession)) {
                    session.invalidate();
                    req.getRequestDispatcher("/login.xhtml?faces-redirect=true").forward(request, response);
                }

                chain.doFilter(request, response);
            }

        } else {
            if (url != null && (url.contains("login") || url.contains("resource") || url.contains("register"))) {
                chain.doFilter(request, response);
            } else {
                req.getRequestDispatcher("/login.xhtml?faces-redirect=true").forward(request, response);
            }

        }

    }

}
