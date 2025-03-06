package com.metaway.petshop.backing;

import com.metaway.petshop.dto.request.BreedRequestDTO;
import com.metaway.petshop.dto.response.BreedResponseDTO;
import com.metaway.petshop.util.PetShopUtil;
import com.metaway.petshop.util.RoutesUtil;
import com.metaway.petshop.util.ServiceUtil;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ViewScoped
@ManagedBean
public class BreedBacking extends AbstractBacking implements Serializable {

    private List<BreedResponseDTO> listBreeds;
    private BreedRequestDTO breed;

    @PostConstruct
    public void init() {
        validateProfileAdmin();

        searchBreeds();
    }

    private void searchBreeds() {
        List<BreedResponseDTO> breeds = loadBreeds();
        this.setListBreeds(breeds);
    }

    public String actionSaveBreed() {
        Integer breedId = (Integer) PetShopUtil.getObjectSession("breedEdit");
        if (breedId != null) {
            return actionEditBreed();
        } else {
            return actionCreateBreed();
        }
    }

    private String actionCreateBreed() {
        try {
            String result = ServiceUtil.post(RoutesUtil.BREED_CREATE, gson.toJson(getBreed()), true, getToken());

            if (result != null) {

                if (!result.contains("error")) {
                    PetShopUtil.addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Raça cadastrada com sucesso!");
                } else {
                    PetShopUtil.showMessagesErrors(gson, result);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            PetShopUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao salvar raça!");
        }

        this.setBreed(new BreedRequestDTO());
        searchBreeds();
        updateField("frm:listBreeds");
        return "";
    }

    private String actionEditBreed() {
        try {
            Integer breedId = (Integer) PetShopUtil.getObjectSession("breedEdit");

            String result = ServiceUtil.put(String.format(RoutesUtil.BREED_UPDATE, breedId), gson.toJson(getBreed()), true, getToken());

            if (result != null) {

                if (!result.contains("error")) {
                    PetShopUtil.addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Raça editada com sucesso!");
                } else {
                    PetShopUtil.showMessagesErrors(gson, result);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            PetShopUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao atualizar raça!");
        }

        this.setBreed(new BreedRequestDTO());
        searchBreeds();
        PetShopUtil.removeObjectSession("breedEdit");
        updateField("frm:listBreeds");
        return "";
    }

    public String actionDeleteBreed() {
        try {
            Integer breedId = (Integer) PetShopUtil.getObjectSession("breedDelete");

            String result = ServiceUtil.delete(String.format(RoutesUtil.BREED_DELETE, breedId), true, getToken());

            if (result != null) {

                if (!result.contains("error")) {
                    PetShopUtil.addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Raça deletada com sucesso!");
                } else {
                    PetShopUtil.showMessagesErrors(gson, result);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            PetShopUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao deletar raça!");
        }

        searchBreeds();
        PetShopUtil.removeObjectSession("breedDelete");
        updateField("frm:listBreeds");
        return "";
    }

    public void setBreedEdit(BreedResponseDTO breed) {
        if (breed != null) {
            PetShopUtil.addObjectSession("breedEdit", breed.getId());

            getBreed().setId(breed.getId());
            getBreed().setName(breed.getName());
            getBreed().setDescription(breed.getDescription());

            updateField("frm:createBreeds");
        }
    }

    public void setBreedDelete(BreedResponseDTO breed) {
        if (breed != null) {
            PetShopUtil.addObjectSession("breedDelete", breed.getId());

            openDialog("deleteBreeds");
        }
    }

    public List<BreedResponseDTO> getListBreeds() {
        if (listBreeds == null) listBreeds = new ArrayList<>();
        return listBreeds;
    }

    public void setListBreeds(List<BreedResponseDTO> listBreeds) {
        this.listBreeds = listBreeds;
    }

    public BreedRequestDTO getBreed() {
        if (breed == null) breed = new BreedRequestDTO();
        return breed;
    }

    public void setBreed(BreedRequestDTO breed) {
        this.breed = breed;
    }

}
