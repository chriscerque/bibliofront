package net.ent.etrs.bibliofront.view;


import lombok.Getter;
import lombok.Setter;
import net.ent.etrs.bibliofront.model.dtos.DtoUserLogin;
import net.ent.etrs.bibliofront.model.entities.references.Role;
import net.ent.etrs.bibliofront.model.entities.security.UserSecurity;
import net.ent.etrs.bibliofront.model.facades.FacadeClientUser;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.registry.infomodel.User;
import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

@Named
@SessionScoped
public class SessionBean implements Serializable {

    @Inject
    private FacadeClientUser facadeUser;

    @Getter
    @Setter
    private UserSecurity user;

    public boolean hasRoleAdmin() {
        return user != null && user.getRole().equals(Role.ADMIN);
    }

    public boolean hasRoleUser() {
        return user != null && user.getRole().equals(Role.USER);
    }

    public void login(String login, String password) {
        DtoUserLogin dtoUserLogin = new DtoUserLogin(login, password);
        String token = this.facadeUser.login(dtoUserLogin);
        if (Objects.nonNull(token)) {
            this.user = new UserSecurity(token);
        }
    }

    public void logout() throws IOException {
        this.user = null;
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(ec.getRequestContextPath() + "/security/login.xhtml");
    }
}
