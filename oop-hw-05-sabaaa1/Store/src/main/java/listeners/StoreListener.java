package listeners;

import util.DatabaseUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class StoreListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        if (DatabaseUtil.testConnection()) {
            servletContextEvent.getServletContext().setAttribute("dbStatus", "connected");
        } else {
            servletContextEvent.getServletContext().setAttribute("dbStatus", "fail");
        }
    }
    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}