package com.metaway.petshop.backing;

import com.metaway.petshop.dto.request.RecoverPasswordRequestDTO;
import com.metaway.petshop.dto.request.UserUpdateRequestDTO;
import com.metaway.petshop.util.PetShopUtil;
import com.metaway.petshop.util.RoutesUtil;
import com.metaway.petshop.util.ServiceUtil;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.Part;
import java.io.Serializable;

@ViewScoped
@ManagedBean
public class ProfileBacking extends AbstractBacking implements Serializable {

    private UserUpdateRequestDTO user;
    private RecoverPasswordRequestDTO recoverPassword;

    private Part file;

    @PostConstruct
    public void init() {
        this.setUser(new UserUpdateRequestDTO(getUserLogged()));
    }

    public void uploadArchiveUser() {
        if (file != null) {

            String base64 = "", base64Mini = "";

            try {

                base64 = PetShopUtil.getImageBase64(file);
                base64Mini = PetShopUtil.getImageBase64Mini(file);

            } catch (Exception e) {
                e.printStackTrace();
                PetShopUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao realizar upload da Imagem! Tente escolher uma imagem menor e sem caracteres especiais.");
            }

            uploadArchive(null, base64, base64Mini);

        } else {
            PetShopUtil.addMessage(FacesMessage.SEVERITY_WARN, "Atenção", "Nenhum arquivo selecionado!");
        }


        updateField("@form");
    }

    public String actionEditUser() {

        try {

            getUser().setCpf(PetShopUtil.removeCharacter(getUser().getCpf()));
            String result = ServiceUtil.put(RoutesUtil.USER_UPDATE, gson.toJson(getUser()), true, getToken());

            if (result != null) {

                if (!result.contains("error")) {
                    PetShopUtil.addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Usuário atualizado com sucesso!");

                    String cpfSession = (String) PetShopUtil.getObjectSession(PetShopUtil.CPF_SESSION);

                    if (!getUser().getCpf().equals(cpfSession)) {
                        PetShopUtil.setCookie(PetShopUtil.UPDATE_CPF_COOKIE, "1", 60);
                        logout();
                    }

                } else {
                    PetShopUtil.showMessagesErrors(gson, result);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            PetShopUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao editar usuário!");
        }

        updateField("@form");
        return "";
    }

    public String actionRecoverPassword() {

        try {

            String result = ServiceUtil.patch(RoutesUtil.USER_CHANGE_PASSWORD, gson.toJson(getRecoverPassword()), true, getToken());

            if (result != null) {

                if (!result.contains("error")) {
                    PetShopUtil.addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Senha atualizada com sucesso!");
                } else {
                    PetShopUtil.showMessagesErrors(gson, result);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            PetShopUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao tentar trocar de senha!");
        }

        updateField("@form");
        return "";
    }

    public UserUpdateRequestDTO getUser() {
        if (user == null) user = new UserUpdateRequestDTO();
        return user;
    }

    public void setUser(UserUpdateRequestDTO user) {
        this.user = user;
    }

    public RecoverPasswordRequestDTO getRecoverPassword() {
        if (recoverPassword == null) recoverPassword = new RecoverPasswordRequestDTO();
        return recoverPassword;
    }

    public void setRecoverPassword(RecoverPasswordRequestDTO recoverPassword) {
        this.recoverPassword = recoverPassword;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }
}
