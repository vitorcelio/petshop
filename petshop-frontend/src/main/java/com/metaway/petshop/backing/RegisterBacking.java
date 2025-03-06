package com.metaway.petshop.backing;

import com.metaway.petshop.dto.request.UserRequestDTO;
import com.metaway.petshop.util.PetShopUtil;
import com.metaway.petshop.util.RoutesUtil;
import com.metaway.petshop.util.ServiceUtil;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import java.io.Serializable;

@ViewScoped
@ManagedBean
public class RegisterBacking extends AbstractBacking implements Serializable {

    private UserRequestDTO user;

    public void actionRegister() {

        getUser().setCpf(PetShopUtil.removeCharacter(getUser().getCpf()));

        if (!getUser().getPassword().equals(getUser().getRepeatPassword())) {
            PetShopUtil.addMessage(FacesMessage.SEVERITY_WARN, "Atenção", "As senhas não coincidem!");
            return;
        }

        try {
            String result = ServiceUtil.post(RoutesUtil.USER_CREATE, gson.toJson(getUser()), false, null);

            if (result != null) {

                if (!result.contains("error")) {
                    PetShopUtil.addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Cliente cadastrado com sucesso!");
                    PetShopUtil.redirectPage("/petshop");
                } else {
                    PetShopUtil.showMessagesErrors(gson, result);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            PetShopUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao registrar cliente!");
        }

    }

    public UserRequestDTO getUser() {
        if (user == null) user = new UserRequestDTO();
        return user;
    }

    public void setUser(UserRequestDTO user) {
        this.user = user;
    }
}
