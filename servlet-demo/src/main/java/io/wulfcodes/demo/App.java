package io.wulfcodes.demo;

import java.io.File;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.Context;

public class App {
    public static void main(String[] args) throws Exception {

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.getConnector();

        File webAppDir = new File("src/main/webapp");
        Context context = tomcat.addWebapp("", webAppDir.getAbsolutePath());

        Tomcat.addServlet(context, "homeServlet", new io.wulfcodes.demo.controller.HomeController());
        context.addServletMappingDecoded("/home", "homeServlet");

        tomcat.start();
        tomcat.getServer().await();
    }
}
