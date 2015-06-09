package com.geoinfo.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebFilter(servletNames={"Faces Servlet"})
public class GeoInfoAccessControlFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest hsreq = (HttpServletRequest) request;
        HttpSession hs = hsreq.getSession();
        
        if(hs.getAttribute("pessoaLogada") != null || 
                hsreq.getRequestURI().endsWith("index.xhtml")||
                hsreq.getRequestURI().endsWith("/GeoInfo/")||
                !hsreq.getRequestURI().endsWith(".xhtml")){
            chain.doFilter(request, response);
        }else{
            HttpServletResponse hsresp = (HttpServletResponse) response;
            if(hsreq.getRequestURI().contains("/faces/")){
                hsresp.sendRedirect("index.xhtml");
            }else{
                hsresp.sendRedirect("faces/index.xhtml");
            }
        }
    }

    @Override
    public void destroy() {
        
    }
    
}
