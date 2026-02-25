package listeners;

import manager.AccManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MainListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        AccManager accManager = new AccManager();
        servletContextEvent.getServletContext().setAttribute("accManager", accManager);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
