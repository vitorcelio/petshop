package com.metaway.petshop.backing;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

@ViewScoped
@ManagedBean
public class MenuBacking implements Serializable {

    private String uri = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRequestURI();

    public boolean activeMenu(String ...menu) {
        for (String menuItem : menu) {
            if (uri.equals(menuItem)) {
                return true;
            }
        }

        return false;
    }

}
