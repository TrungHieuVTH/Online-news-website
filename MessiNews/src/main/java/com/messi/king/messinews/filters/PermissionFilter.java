package com.messi.king.messinews.filters;

import com.messi.king.messinews.model.bean.Users;
import com.messi.king.messinews.utils.ServletUtils;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "PermissionFilter")
public class PermissionFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("authUser");
        String path = "/"+request.getServletPath().split("/")[1];

        switch (path) {
            case "/Admin":
                if (user.getRole()!=4) {
                    ServletUtils.redirect("/Account/Login", request, (HttpServletResponse) res);
                    return;
                }
                break;
            case "/Editor":
                if (user.getRole()!=3 && user.getRole()!=4) {
                    ServletUtils.redirect("/Account/Login", request, (HttpServletResponse) res);
                    return;
                }
                break;
            case "/Writer":
                if (user.getRole()==4 && request.getPathInfo().equals("/List")) {
                    ServletUtils.redirect("/Admin/Articles/ListComplete", request, (HttpServletResponse) res);
                    return;
                }
                if (user.getRole()!=2 && user.getRole()!=4) {
                    ServletUtils.redirect("/Account/Login", request, (HttpServletResponse) res);
                    return;
                }
                break;
        }

        chain.doFilter(req, res);
    }
}
