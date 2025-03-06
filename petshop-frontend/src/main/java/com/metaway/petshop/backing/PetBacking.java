package com.metaway.petshop.backing;

import com.metaway.petshop.dto.request.ArchiveRequestDTO;
import com.metaway.petshop.dto.request.PetRequestDTO;
import com.metaway.petshop.dto.response.BreedResponseDTO;
import com.metaway.petshop.dto.response.PetResponseDTO;
import com.metaway.petshop.dto.search.PetSearch;
import com.metaway.petshop.util.PetShopUtil;
import com.metaway.petshop.util.RoutesUtil;
import com.metaway.petshop.util.ServiceUtil;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.Part;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ViewScoped
@ManagedBean
public class PetBacking extends AbstractBacking implements Serializable {

    private PetSearch search;

    private List<PetResponseDTO> petsList;
    private List<BreedResponseDTO> listBreeds;

    private PetRequestDTO pet;
    private Part file;

    @PostConstruct
    public void init() {
        validateProfileCustomer();

        search();
        searchBreeds();
    }

    private void searchBreeds() {
        List<BreedResponseDTO> breeds = loadBreeds();
        this.setListBreeds(breeds);
    }

    public String search() {
        String params = getParams();
        List<PetResponseDTO> pets = loadPets(getUserId(), params);

        this.setPetsList(pets);
        updateField("frm:petsList");
        return "";
    }

    private String getParams() {
        StringBuilder params = new StringBuilder();

        if (!PetShopUtil.isEmpty(getSearch().getName())) {
            params.append(params.length() == 0 ? "?" : "&").append("name=").append(getSearch().getName());
        }

        if (getSearch().getGender() != null) {
            params.append(params.length() == 0 ? "?" : "&").append("gender=").append(getSearch().getGender());
        }

        if (getSearch().getBreedId() != null) {
            params.append(params.length() == 0 ? "?" : "&").append("breedId=").append(getSearch().getBreedId());
        }

        return params.toString();
    }

    public String actionSavePet() {
        Integer petId = (Integer) PetShopUtil.getObjectSession("petEdit");

        if (petId != null) {
            return actionEditPet();
        } else {
            return actionCreatePet();
        }
    }

    private String actionCreatePet() {
        try {

            getPet().setCustomerId(getUserId());

            String result = ServiceUtil.post(RoutesUtil.PET_CREATE, gson.toJson(getPet()), true, getToken());

            if (result != null) {

                if (!result.contains("error")) {
                    PetShopUtil.addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Animal cadastrado com sucesso!");
                } else {
                    PetShopUtil.showMessagesErrors(gson, result);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            PetShopUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao salvar Animal!");
        }

        search();
        updateField("frm:petsList");
        return "";
    }

    private String actionEditPet() {
        try {

            Integer petId = (Integer) PetShopUtil.getObjectSession("petEdit");

            String url = String.format(RoutesUtil.PET_UPDATE, petId);

            String result = ServiceUtil.put(url, gson.toJson(getPet()), true, getToken());

            if (result != null) {

                if (!result.contains("error")) {
                    PetShopUtil.addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Animal editado com sucesso!");
                } else {
                    PetShopUtil.showMessagesErrors(gson, result);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            PetShopUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao editar Animal!");
        }

        PetShopUtil.removeObjectSession("petEdit");
        search();
        updateField("frm:petsList");
        return "";
    }

    public String actionDeletePet() {
        try {
            Integer petId = (Integer) PetShopUtil.getObjectSession("petDelete");

            String url = String.format(RoutesUtil.PET_DELETE, petId);

            String result = ServiceUtil.delete(url, true, getToken());

            if (result != null) {

                if (!result.contains("error")) {
                    PetShopUtil.addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Animal deletado com sucesso!");
                } else {
                    PetShopUtil.showMessagesErrors(gson, result);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            PetShopUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao deletar Animal!");
        }

        PetShopUtil.removeObjectSession("petDelete");
        search();
        updateField("frm:petsList");
        return "";
    }

    public void setResetPets() {
        this.setPet(new PetRequestDTO());
        PetShopUtil.removeObjectSession("petEdit");

        updateField("frm:petsCreate");
        openDialog("petsCreate");
    }

    public void setPetEdit(PetResponseDTO pet) {
        if (pet != null) {
            PetShopUtil.addObjectSession("petEdit", pet.getId());

            getPet().setId(pet.getId());
            getPet().setName(pet.getName());
            getPet().setCustomerId(pet.getCustomer().getId());
            getPet().setGender(pet.getGender());
            getPet().setBreedId(pet.getBreed().getId());
            getPet().setBirthDate(pet.getBirthDate());

            updateField("frm:petsCreate");
            openDialog("petsCreate");
        }
    }

    public void setPetDelete(PetResponseDTO pet) {
        if (pet != null) {
            PetShopUtil.addObjectSession("petDelete", pet.getId());

            openDialog("petsDelete");
        }
    }

    public void uploadArchivePet(PetRequestDTO pet) {
        if (file != null) {

            String base64 = "", base64Mini = "";

            try {

                base64 = PetShopUtil.getImageBase64(getFile());
                base64Mini = PetShopUtil.getImageBase64Mini(getFile());

            } catch (Exception e) {
                e.printStackTrace();
                PetShopUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao realizar upload da Imagem!");
            }

            uploadArchive(pet.getId(), base64, base64Mini);
        } else {
            PetShopUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao realizar upload da Imagem! Tente escolher uma imagem menor e sem caracteres especiais.");
        }

        search();
        updateField("frm:petsList");
    }

    public PetSearch getSearch() {
        if (search == null) search = new PetSearch();
        return search;
    }

    public void setSearch(PetSearch search) {
        this.search = search;
    }

    public List<PetResponseDTO> getPetsList() {
        if (petsList == null) petsList = new ArrayList<>();
        return petsList;
    }

    public void setPetsList(List<PetResponseDTO> petsList) {
        this.petsList = petsList;
    }

    public List<BreedResponseDTO> getListBreeds() {
        if (listBreeds == null) listBreeds = new ArrayList<>();
        return listBreeds;
    }

    public void setListBreeds(List<BreedResponseDTO> listBreeds) {
        this.listBreeds = listBreeds;
    }

    public PetRequestDTO getPet() {
        if (pet == null) pet = new PetRequestDTO();
        return pet;
    }

    public void setPet(PetRequestDTO pet) {
        this.pet = pet;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }
}
