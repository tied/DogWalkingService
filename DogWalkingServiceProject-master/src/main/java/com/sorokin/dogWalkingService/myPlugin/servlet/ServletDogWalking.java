package com.sorokin.dogWalkingService.myPlugin.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import com.atlassian.jira.component.pico.ComponentManager;
import com.atlassian.modzdetector.IOUtils;
import com.atlassian.plugin.PluginAccessor;
import com.atlassian.templaterenderer.TemplateRenderer;

public class ServletDogWalking extends HttpServlet {

    private final TemplateRenderer templateRenderer;

    public ServletDogWalking(TemplateRenderer templateRenderer) {
        this.templateRenderer = templateRenderer;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("text/html;charset=utf-8");
        String requestUri = req.getRequestURI(); // /plugins/servlet/servlet-inf/
        String path = requestUri.replace("/plugins/servlet/servlet-inf/", "");

        if (path.startsWith("site/")) {

            if (path.endsWith("infclient.html")) {

                templateRenderer.render("site/infClient.html", new HashMap<>(), resp.getWriter());

            } else if (path.endsWith("infdog.html")) {

                templateRenderer.render("site/infDog.html", new HashMap<>(), resp.getWriter());

            } else if (path.endsWith("infdogwalker.html")) {

                templateRenderer.render("site/infDogWalker.html", new HashMap<>(), resp.getWriter());

            } else if (path.endsWith("infRequestHistory.html")) {

                templateRenderer.render("site/infRequestHistory.html", new HashMap<>(), resp.getWriter());

            } else if (path.endsWith("mainpage.html")) {

                templateRenderer.render("site/mainPage.html", new HashMap<>(), resp.getWriter());

            } else if (path.endsWith("infrequestwalk.html")) {

                templateRenderer.render("site/infRequestWalk.html", new HashMap<>(), resp.getWriter());

            } else {
                PluginAccessor accessor = ComponentManager.getInstance().getComponent(PluginAccessor.class);
                InputStream inputStream = accessor.getDynamicResourceAsStream(path);
                byte[] bytes = IOUtils.toByteArray(inputStream);

                String fileName = path;
                int pos = path.lastIndexOf("/");
                if (pos >= 0)
                    fileName = path.substring(pos + 1);

                resp.reset();
                resp.setHeader("Content-Disposition", "filename=\"" + fileName + "\"");

                if (path.endsWith(".js")) resp.setContentType("text/javascript");
                if (path.endsWith(".css")) resp.setContentType("text/css");
                if (path.endsWith(".png")) resp.setContentType("image/png");
                if (path.endsWith(".jpg")) resp.setContentType("image/jpg");
                if (path.endsWith(".ico")) resp.setContentType("image/x-icon");

                resp.setContentLength(bytes.length);
                resp.getOutputStream().write(bytes);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }
}