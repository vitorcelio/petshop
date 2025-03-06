package com.metaway.petshop.backing;

import com.google.gson.reflect.TypeToken;
import com.metaway.petshop.dto.request.PetCareRequestDTO;
import com.metaway.petshop.dto.response.PetCareResponseDTO;
import com.metaway.petshop.dto.response.PetResponseDTO;
import com.metaway.petshop.dto.search.PetsCareSearch;
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
public class PetsCareBacking extends AbstractBacking implements Serializable {

    private PetsCareSearch search;

    private List<PetCareResponseDTO> listPetsCare;
    private List<PetResponseDTO> pets;
    private PetCareRequestDTO petCare;

    @PostConstruct
    public void init() {
       searchPetsCare();

        loadPets();
    }

    private void loadPets() {
        List<PetResponseDTO> petsList = loadPets(isCustomer() ? getUserId() : null, "");
        this.setPets(petsList);
    }

    public void searchPetsCare() {
        if (isAdmin()) {
            loadPetsCares();
        } else {
            loadPetsCaresCustomer();
        }
    }

    private void loadPetsCaresCustomer() {
        try {

            String params = getParams();

            String url = RoutesUtil.PET_CARE_GET_ALL + params
                    .replace("'", "%27")
                    .replace("(", "%28")
                    .replace(")", "%29")
                    .replace(" ", "%20");

            String result = ServiceUtil.get(url, true, getToken());

            if (result != null) {

                if (!result.contains("error")) {
                    TypeToken<List<PetCareResponseDTO>> typeToken = new TypeToken<>() {
                    };

                    List<PetCareResponseDTO> petsCare = gson.fromJson(result, typeToken.getType());
                    this.setListPetsCare(petsCare);

                } else {
                    PetShopUtil.showMessagesErrors(gson, result);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            PetShopUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao buscar atendimentos!");
        }
    }

    private void loadPetsCares() {
        try {

            String params = getParams();

            String url = RoutesUtil.PET_CARE_GET_ALL_ENTRIES + params
                    .replace("'", "%27")
                    .replace("(", "%28")
                    .replace(")", "%29")
                    .replace(" ", "%20");

            String result = ServiceUtil.get(url, true, getToken());

            if (result != null) {

                if (!result.contains("error")) {
                    TypeToken<List<PetCareResponseDTO>> typeToken = new TypeToken<>() {
                    };

                    List<PetCareResponseDTO> petsCare = gson.fromJson(result, typeToken.getType());
                    this.setListPetsCare(petsCare);

                } else {
                    PetShopUtil.showMessagesErrors(gson, result);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            PetShopUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao buscar atendimentos!");
        }
    }

    private String getParams() {
        StringBuilder params = new StringBuilder();

        if (!PetShopUtil.isEmpty(getSearch().getStatus())) {
            params.append(params.length() == 0 ? "?" : "&").append("status=").append(getSearch().getStatus());
        }

        if (getSearch().getCustomerId() != null) {
            params.append(params.length() == 0 ? "?" : "&").append("customerId=").append(getSearch().getCustomerId());
        }

        if (getSearch().getPetId() != null) {
            params.append(params.length() == 0 ? "?" : "&").append("petId=").append(getSearch().getPetId());
        }

        if (!PetShopUtil.isEmpty(getSearch().getCreatedAtStart())) {
            params.append(params.length() == 0 ? "?" : "&").append("createdAtStart=").append(getSearch().getCreatedAtStart());
        }

        if (!PetShopUtil.isEmpty(getSearch().getCreatedAtEnd())) {
            params.append(params.length() == 0 ? "?" : "&").append("createdAtEnd=").append(getSearch().getCreatedAtEnd());
        }

        return params.toString();
    }

    public String actionSavePetCare() {
        String uuid = (String) PetShopUtil.getObjectSession("petCareEdit");
        if (uuid != null) {
            return actionEditPetCare();
        } else {
            return actionCreatePetCare();
        }
    }

    private String actionCreatePetCare() {
        try {
            String result = ServiceUtil.post(RoutesUtil.PET_CARE_CREATE, gson.toJson(getPetCare()), true, getToken());

            if (result != null) {

                if (!result.contains("error")) {
                    PetShopUtil.addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Atendimento cadastrado com sucesso!");
                    this.setPetCare(new PetCareRequestDTO());

                } else {
                    PetShopUtil.showMessagesErrors(gson, result);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            PetShopUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao salvar atendimento!");
        }

        init();
        updateField("frm:listPetsCare");
        return "";
    }

    private String actionEditPetCare() {
        try {
            String uuid = (String) PetShopUtil.getObjectSession("petCareEdit");
            String result = ServiceUtil.put(String.format(RoutesUtil.PET_CARE_UPDATE, uuid), gson.toJson(getPetCare()), true, getToken());

            if (result != null) {

                if (!result.contains("error")) {
                    PetShopUtil.addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Atendimento editado com sucesso!");

                } else {
                    PetShopUtil.showMessagesErrors(gson, result);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            PetShopUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao atualizar atendimento!");
        }

        PetShopUtil.removeObjectSession("petCareEdit");
        this.setPetCare(new PetCareRequestDTO());
        init();
        updateField("frm:listPetsCare");
        return "";
    }

    public String actionDeletePetCare() {
        try {

            String uuid = (String) PetShopUtil.getObjectSession("petCareDelete");
            String result = ServiceUtil.delete(String.format(RoutesUtil.PET_CARE_DELETE, uuid), true, getToken());

            if (result != null) {

                if (!result.contains("error")) {
                    PetShopUtil.addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Atendimento deletado com sucesso!");

                } else {
                    PetShopUtil.showMessagesErrors(gson, result);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            PetShopUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao deletar atendimento!");
        }

        PetShopUtil.removeObjectSession("petCareDelete");
        this.setPetCare(new PetCareRequestDTO());
        init();
        updateField("frm:listPetsCare");
        return "";
    }
    
    public void setPetCareEdit(PetCareResponseDTO petCare) {
        if (petCare != null) {
            PetShopUtil.addObjectSession("petCareEdit", petCare.getUuid());

            getPetCare().setUuid(petCare.getUuid());
            getPetCare().setPetId(petCare.getPet().getId());
            getPetCare().setDate(petCare.getDate());
            getPetCare().setStatus(petCare.getStatus());
            getPetCare().setPrice(petCare.getPrice());
            getPetCare().setDescription(petCare.getDescription());

            updateField("frm:createPetsCares");
            openDialog("createPetsCares");
        }
    }

    public void setPetCareDelete(PetCareResponseDTO petCare) {
        if (petCare != null) {
            PetShopUtil.addObjectSession("petCareDelete", petCare.getUuid());

            openDialog("deletePetsCares");
        }
    }

    public void resetPetsCare() {
        this.setPetCare(new PetCareRequestDTO());
        PetShopUtil.removeObjectSession("petCareEdit");

        updateField("frm:createPetsCares");
        openDialog("createPetsCares");
    }

    public String getClassStatus(String status) {
        switch (status) {
            case "PENDING":
                return "bg-gray-50 text-gray-600 ring-gray-500/10";
            case "CANCELLED":
                return "bg-red-50 text-red-600 ring-red-500/10";
            case "COMPLETED":
                return "bg-green-50 text-green-600 ring-green-500/10";
        }

        return "bg-gray-50 text-gray-600 ring-gray-500/10";
    }

    public String getNameStatus(String status) {
        switch (status) {
            case "PENDING":
                return "Pendente";
            case "CANCELLED":
                return "Cancelado";
            case "COMPLETED":
                return "Completo";
        }

        return "Pendente";
    }

    public List<PetCareResponseDTO> getListPetsCare() {
        if (listPetsCare == null) {
            listPetsCare = new ArrayList<>();
        }
        return listPetsCare;
    }

    public void setListPetsCare(List<PetCareResponseDTO> listPetsCare) {
        this.listPetsCare = listPetsCare;
    }

    public PetCareRequestDTO getPetCare() {
        if (petCare == null) petCare = new PetCareRequestDTO();
        return petCare;
    }

    public void setPetCare(PetCareRequestDTO petCare) {
        this.petCare = petCare;
    }

    public List<PetResponseDTO> getPets() {
        if (pets == null) {
            pets = new ArrayList<>();
        }
        return pets;
    }

    public void setPets(List<PetResponseDTO> pets) {
        this.pets = pets;
    }

    public PetsCareSearch getSearch() {
        if (search == null) search = new PetsCareSearch();
        return search;
    }

    public void setSearch(PetsCareSearch search) {
        this.search = search;
    }
}
