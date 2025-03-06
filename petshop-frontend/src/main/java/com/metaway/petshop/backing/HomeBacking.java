package com.metaway.petshop.backing;

import com.metaway.petshop.dto.response.InfosResponseDTO;
import com.metaway.petshop.util.PetShopUtil;
import com.metaway.petshop.util.RoutesUtil;
import com.metaway.petshop.util.ServiceUtil;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import java.io.Serializable;

@ViewScoped
@ManagedBean
public class HomeBacking extends AbstractBacking implements Serializable {

    private InfosResponseDTO infos;

    @PostConstruct
    public void init() {
        loadInfo();
    }

    private void loadInfo() {
        try {

            String result = ServiceUtil.get(isAdmin() ? RoutesUtil.INFOS_ADMIN : RoutesUtil.INFOS_CUSTOMER, true, getToken());

            if (result != null) {

                if (!result.contains("error")) {
                    InfosResponseDTO response = gson.fromJson(result, InfosResponseDTO.class);
                    this.setInfos(response);
                } else {
                    PetShopUtil.showMessagesErrors(gson, result);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            PetShopUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao buscar informações!");
        }
    }

    public InfosResponseDTO getInfos() {
        if (infos == null) infos = new InfosResponseDTO();
        return infos;
    }

    public void setInfos(InfosResponseDTO infos) {
        this.infos = infos;
    }
}
