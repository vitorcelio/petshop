package com.metaway.petshop.backing;

import com.google.gson.Gson;
import com.metaway.petshop.dto.request.AuthenticationRequestDTO;
import com.metaway.petshop.dto.response.AuthenticationResponseDTO;
import com.metaway.petshop.util.PetShopUtil;
import com.metaway.petshop.util.RoutesUtil;
import com.metaway.petshop.util.ServiceUtil;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.servlet.http.Cookie;
import java.io.Serializable;

@ViewScoped
@ManagedBean
public class LoginBacking implements Serializable {

    private static final Gson gson = new Gson();
    private AuthenticationRequestDTO request;

    public void onLoad() {
        Cookie cookie = PetShopUtil.getCookie(PetShopUtil.UPDATE_CPF_COOKIE);
        if (cookie != null && cookie.getValue() != null && cookie.getValue().equals("1")) {
            PetShopUtil.addMessage(FacesMessage.SEVERITY_INFO, "Atenção", "Faça login com CPF atualizado!");
        }
    }

    public void actionLogin() {

        getRequest().setCpf(PetShopUtil.removeCharacter(getRequest().getCpf()));

        try {
            String result = ServiceUtil.post(RoutesUtil.AUTH_LOGIN, gson.toJson(getRequest()), false, null);
            if (!PetShopUtil.isEmpty(result)) {
                if (result.contains("token")) {
                    AuthenticationResponseDTO response = gson.fromJson(result, AuthenticationResponseDTO.class);

                    PetShopUtil.addObjectSession(PetShopUtil.TOKEN_SESSION, response.getToken());
                    PetShopUtil.addObjectSession(PetShopUtil.USER_ID_SESSION, response.getUserId());
                    PetShopUtil.addObjectSession(PetShopUtil.EXPIRATION_SESSION, response.getExpiration());
                    PetShopUtil.addObjectSession(PetShopUtil.CPF_SESSION, response.getCpf());
                    PetShopUtil.addObjectSession(PetShopUtil.ROLE_ID_SESSION, response.getRoleId());

                    PetShopUtil.redirectPage("/petshop/pages/home.xhtml");
                } else {
                    PetShopUtil.showMessagesErrors(gson, result);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            PetShopUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro inesperado aconteceu!");
        }
    }

    public AuthenticationRequestDTO getRequest() {
        if (request == null) request = new AuthenticationRequestDTO();
        return request;
    }

    public void setRequest(AuthenticationRequestDTO request) {
        this.request = request;
    }
}
