package net.ent.etrs.bibliofront.filters;


import net.ent.etrs.bibliofront.model.entities.references.Role;
import net.ent.etrs.bibliofront.view.SessionBean;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebFilter("/*")
public class SecurityFilter implements Filter {
    
    @Inject
    private SessionBean sessionBean;
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String uri = req.getRequestURI().substring(req.getContextPath().length());
        if (!this.isAlwaysAuthorized(uri)) {
            if (sessionBean.getUser() == null) {
                resp.sendRedirect(req.getContextPath() + "/security/login.xhtml");
            } else if (sessionBean.getUser() != null && !this.isAuthorized(sessionBean.getUser().getRole(), uri)) {
                resp.sendRedirect(req.getContextPath() + "/index.xhtml");
            } else {
                filterChain.doFilter(request, response);
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
    
    private boolean isAuthorized(Role role, String uri) {
        if (role.hasRole(Role.ADMIN) && this.getAdminPages().contains(uri)) {
            return true;
        } else return role.hasRole(Role.USER) && this.getUserPages().contains(uri);
    }
    
    private List<String> getAdminPages() {
        List<String> pages = new ArrayList<>();
        pages.add("/livre/fiche.xhtml");
        pages.add("/auteur/fiche.xhtml");
        return pages;
    }
    
    private List<String> getUserPages() {
        List<String> pages = new ArrayList<>();
        pages.add("/livre/liste.xhtml");
        pages.add("/auteur/liste.xhtml");
        return pages;
    }
    
    private boolean isAlwaysAuthorized(String uri) {
        return uri.startsWith("/index.xhtml") || uri.startsWith("/security/login.xhtml") || uri.startsWith("/javax.faces.resource");
    }
    
}
