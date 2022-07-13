package net.ent.etrs.bibliofront.view.security;

import lombok.Getter;
import lombok.Setter;
import net.ent.etrs.bibliofront.utils.JsfUtils;
import net.ent.etrs.bibliofront.view.SessionBean;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

@Named
@ViewScoped
public class LoginBean implements Serializable {
    
    @Inject
    private SessionBean sessionBean;
    
    @Getter
    @Setter
    private String login;
    
    @Getter
    @Setter
    private String password;
    
    public void login() throws IOException {
        this.sessionBean.login(this.login, this.password);
        
        if (this.sessionBean.getUser() != null) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("../index.xhtml");
        }
        JsfUtils.sendGrowlMessage(FacesMessage.SEVERITY_WARN, "Login ou password incorrect !");
    }
    
}
