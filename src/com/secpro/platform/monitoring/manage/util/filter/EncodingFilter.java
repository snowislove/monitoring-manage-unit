package com.secpro.platform.monitoring.manage.util.filter;

import java.io.IOException;

import javax.persistence.Entity;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author baiyanwei
 * 处理整个WEBAPP的编码问题
 */
@Entity
public class EncodingFilter implements Filter {
    protected String encoding = null;
    protected FilterConfig config;

    public void destroy() {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //if request doesn't have encoding setting. 
        if (request.getCharacterEncoding() == null) {
            if (encoding != null) {
                //setting up the encoding. 
               request.setCharacterEncoding(encoding);
            }
        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        this.config = filterConfig;
        //ready setting from web.xml 
        this.encoding = filterConfig.getInitParameter("Encoding");
    }
}
