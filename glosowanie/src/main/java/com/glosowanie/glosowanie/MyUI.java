package com.glosowanie.glosowanie;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;
import com.vaadin.data.util.sqlcontainer.query.generator.filter.QueryBuilder;
import com.vaadin.data.util.sqlcontainer.query.generator.filter.StringDecorator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

	//POLACZENIE Z BAZA DANYCH
	public void DBConnect()
	{
		Connection con = null;

    	try {
    	Class.forName("com.mysql.jdbc.Driver").newInstance();
    	con = DriverManager.getConnection("jdbc:mysql:///Wybory",
    	"root", "root");

    	if(!con.isClosed())
    	System.out.println("Successfully connected to " +
    	"MySQL server using TCP/IP...");

    	String g="DOESNT WORK!!211";
    	//System.out.println(con.nativeSQL("USE Wybory; SELECT * FROM UZYTKOWNICY;"));
    	//TableQuery tq = new TableQuery("UZYTKOWNICY", con);
    	
    	} catch(Exception ee) {
    	System.err.println("Exception: " + ee.getMessage());
    	} finally {
    	try {
    	if(con != null)
    	con.close();
    	} catch(SQLException ee) {}}
	}

	public void DBConnect3()
	{
		JDBCConnectionPool con;
		try {
			con = new SimpleJDBCConnectionPool(
			        "org.hsqldb.jdbc.JDBCDriver",
			        "localhost", "root", "root", 2, 5);
			TableQuery tq = new TableQuery("UZYTKOWNICY", con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//############################
	public JDBCConnectionPool connectionPool = null;
	public SQLContainer credentialContainer=null;
	
	private void initContainers(){
		try{
			
		
		QueryBuilder.setStringDecorator(new StringDecorator("'","'"));
		TableQuery q1= new TableQuery("credentials",connectionPool);
		q1.setVersionColumn("version");
		credentialContainer = new SQLContainer(q1);
		}catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	public void DBConnect2()
	{
		//private JDBCConnectionPool connectionPool=null;
		//private SQLContainer credentialContainer=null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			connectionPool=new SimpleJDBCConnectionPool("com.mysql.jdbc.Driver","jdbc:mysql://localhost:3306/vaadin",
					"root","root",2,10);
			initContainers();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	//####################################
	
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        
        getPage().setTitle("Wybory elektroniczne");
        
        Label tytul = new Label("Zaloguj się by móc zagłosować");
        
        final TextField name = new TextField();
        name.setCaption("Podaj swój pesel:");
        
        final TextField password = new TextField();
        password.setCaption("Podaj otrzymane hasło");
        
        final PasswordField tf = new PasswordField();
        tf.setCaption("Podaj otrzymane hasło:");

        Button button = new Button("Zaloguj");
        button.addClickListener( e -> {
        	
        	//DBConnect("root","root");
        	DBConnect();
        	//Runtime r = Runtime.getRuntime();
            //String[] cmds = { "java -cp /home/serq/workspace/glosowanie/func Greet" };
        	//String[] rmiregistry = { "rmiregistry"};
        	//String[] helloworld = { "java /home/serq/workspace/glosowanie/func HelloWorld.java" };
            //String[] cmds = { "java /home/serq/workspace/glosowanie/func Greet.java" };
            //String[] pwd = { "pwd" };
            try {
            	//r.exec(pwd);
                //r.exec(rmiregistry);
                //r.exec(helloworld);
            	//r.exec(cmds);
                
                
            } catch (Exception ee) {
                System.out.println(ee.toString());
            }
            //layout.addComponent(new Label("Nie działa :("));
        });
        
        layout.addComponents(tytul, name, tf, button);
        layout.setMargin(true);
        layout.setSpacing(true);
        
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
