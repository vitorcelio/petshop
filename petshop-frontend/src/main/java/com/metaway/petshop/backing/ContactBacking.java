package com.metaway.petshop.backing;

import com.google.gson.reflect.TypeToken;
import com.metaway.petshop.dto.request.ContactRequestDTO;
import com.metaway.petshop.dto.response.BreedResponseDTO;
import com.metaway.petshop.dto.response.ContactResponseDTO;
import com.metaway.petshop.dto.response.UserResponseDTO;
import com.metaway.petshop.dto.search.ContactSearch;
import com.metaway.petshop.enums.RolesEnum;
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
public class ContactBacking extends AbstractBacking implements Serializable {

    private ContactSearch search;

    private List<ContactResponseDTO> listContacts;
    private ContactRequestDTO contact;

    @PostConstruct
    public void init() {
        searchContact();
    }

    public void searchContact() {
        if (isAdmin()) {
            loadContactsAdmin();
        } else {
            loadContactsCustomer();
        }
    }

    private void loadContactsAdmin() {
        try {

            String params = getParams();
            String url = RoutesUtil.CONTACT_GET_ALL_ENTRIES + params
                    .replace("'", "%27")
                    .replace("(", "%28")
                    .replace(")", "%29")
                    .replace(" ", "%20");

            String result = ServiceUtil.get(url, true, getToken());

            if (result != null) {

                if (!result.contains("error")) {

                    TypeToken<List<ContactResponseDTO>> typeToken = new TypeToken<>() {
                    };

                    List<ContactResponseDTO> list = gson.fromJson(result, typeToken.getType());
                    this.setListContacts(list);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            PetShopUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao carregar os contatos!");
        }
    }

    private void loadContactsCustomer() {

        try {
            String params = getParams();
            String url = RoutesUtil.CONTACT_GET_ALL + params
                    .replace("'", "%27")
                    .replace("(", "%28")
                    .replace(")", "%29")
                    .replace(" ", "%20");

            String result = ServiceUtil.get(url, true, getToken());

            if (result != null) {

                if (!result.contains("error")) {
                    TypeToken<List<ContactResponseDTO>> typeToken = new TypeToken<>() {
                    };

                    List<ContactResponseDTO> list = gson.fromJson(result, typeToken.getType());
                    this.setListContacts(list);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            PetShopUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao carregar os contatos!");
        }
    }

    private String getParams() {
        StringBuilder params = new StringBuilder("");

        if (getSearch().getCustomerId() != null) {
            params.append(params.length() == 0 ? "?" : "&").append("customerId=").append(getSearch().getCustomerId());
        }

        if (!PetShopUtil.isEmpty(getSearch().getTag())) {
            params.append(params.length() == 0 ? "?" : "&").append("tag=").append(getSearch().getTag());
        }

        if (!PetShopUtil.isEmpty(getSearch().getType())) {
            params.append(params.length() == 0 ? "?" : "&").append("type=").append(getSearch().getType());
        }

        if (!PetShopUtil.isEmpty(getSearch().getCreatedAtStart())) {
            params.append(params.length() == 0 ? "?" : "&").append("createdAtStart=").append(getSearch().getCreatedAtStart());
        }

        if (!PetShopUtil.isEmpty(getSearch().getCreatedAtEnd())) {
            params.append(params.length() == 0 ? "?" : "&").append("createdAtEnd=").append(getSearch().getCreatedAtEnd());
        }

        return params.toString();
    }

    public String actionSaveContact() {
        String contactId = (String) PetShopUtil.getObjectSession("contactEdit");
        if (contactId != null) {
            return actionEditContact();
        } else {
            return actionCreateContact();
        }
    }

    private String actionCreateContact() {
        try {
            if (getContact().getType().equals("PHONE")) {

                if (!PetShopUtil.isValidNumber(getContact().getValue())) {
                    PetShopUtil.addMessage(FacesMessage.SEVERITY_WARN, "Atenção", "Digite um número de telefone válido!");
                    return null;
                }

            } else {

                if (!PetShopUtil.isValidEmail(getContact().getValue())) {
                    PetShopUtil.addMessage(FacesMessage.SEVERITY_WARN, "Atenção", "Digite um e-mail válido!");
                    return null;
                }

            }

            getContact().setCustomerId(getUserId());

            String result = ServiceUtil.post(RoutesUtil.CONTACT_CREATE, gson.toJson(getContact()), true, getToken());

            if (result != null) {

                if (!result.contains("error")) {
                    PetShopUtil.addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Contato cadastrado com sucesso!");

                } else {
                    PetShopUtil.showMessagesErrors(gson, result);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            PetShopUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao salvar contato!");
        }

        this.setContact(new ContactRequestDTO());
        init();
        updateField("frm:listContacts");
        return "";
    }

    private String actionEditContact() {
        try {
            if (getContact().getType().equals("PHONE")) {

                if (!PetShopUtil.isValidNumber(getContact().getValue())) {
                    PetShopUtil.addMessage(FacesMessage.SEVERITY_WARN, "Atenção", "Digite um número de telefone válido!");
                    return null;
                }

            } else {

                if (!PetShopUtil.isValidEmail(getContact().getValue())) {
                    PetShopUtil.addMessage(FacesMessage.SEVERITY_WARN, "Atenção", "Digite um e-mail válido!");
                    return null;
                }

            }

            getContact().setCustomerId(getUserId());

            String uuid = (String) PetShopUtil.getObjectSession("contactEdit");

            String result = ServiceUtil.put(String.format(RoutesUtil.CONTACT_UPDATE, uuid),
                    gson.toJson(getContact()), true, getToken());

            if (result != null) {

                if (!result.contains("error")) {
                    PetShopUtil.addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Contato editado com sucesso!");

                } else {
                    PetShopUtil.showMessagesErrors(gson, result);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            PetShopUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao atualizar contato!");
        }

        this.setContact(new ContactRequestDTO());
        init();
        updateField("frm:listContacts");
        return "";
    }

    public String actionDeleteContact() {
        try {

            String uuid = (String) PetShopUtil.getObjectSession("contactDelete");

            String result = ServiceUtil.delete(String.format(RoutesUtil.CONTACT_DELETE, uuid), true, getToken());

            if (result != null) {

                if (!result.contains("error")) {
                    PetShopUtil.addMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Contato deletado com sucesso!");

                } else {
                    PetShopUtil.showMessagesErrors(gson, result);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            PetShopUtil.addMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao deletar contato!");
        }

        this.setContact(new ContactRequestDTO());
        init();
        updateField("frm:listContacts");
        return "";
    }

    public void setContactEdit(ContactResponseDTO contact) {
        if (contact != null) {
            PetShopUtil.addObjectSession("contactEdit", contact.getUuid());
            this.setContact(new ContactRequestDTO());

            getContact().setUuid(contact.getUuid());
            getContact().setCustomerId(contact.getCustomer().getId());
            getContact().setTag(contact.getTag());
            getContact().setType(contact.getType());
            getContact().setValue(contact.getValue());
            getContact().setMessage(contact.getMessage());

            updateField("frm:createContacts");
            openDialog("createContacts");
        }
    }

    public void setContactDelete(ContactResponseDTO contact) {
        if (contact != null) {
            PetShopUtil.addObjectSession("contactDelete", contact.getUuid());

            openDialog("deleteContacts");
        }
    }

    public String getTypeContact(String type) {
        if (type.equals("EMAIL")) {
            return "E-mail";
        } else {
            return "Telefone";
        }
    }

    public void resetContact() {
        this.setContact(new ContactRequestDTO());
        PetShopUtil.removeObjectSession("contactEdit");

        updateField("frm:createContacts");
        openDialog("createContacts");
    }

    public List<ContactResponseDTO> getListContacts() {
        if (listContacts == null) listContacts = new ArrayList<>();
        return listContacts;
    }

    public void setListContacts(List<ContactResponseDTO> listContacts) {
        this.listContacts = listContacts;
    }

    public ContactRequestDTO getContact() {
        if (contact == null) contact = new ContactRequestDTO();
        return contact;
    }

    public void setContact(ContactRequestDTO contact) {
        this.contact = contact;
    }

    public ContactSearch getSearch() {
        if (search == null) search = new ContactSearch();
        return search;
    }

    public void setSearch(ContactSearch search) {
        this.search = search;
    }
}
