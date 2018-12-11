import java.io.IOException;
import java.sql.* ;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import java.io.PrintWriter;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

/**
 * Servlet implementation class for Servlet: ConfigurationTest
 *
 */
public class Editor extends HttpServlet {
    /**
     * The Servlet constructor
     *
     * @see javax.servlet.http.HttpServlet#HttpServlet()
     */
    public Editor() {}

    Connection c = null;
    PreparedStatement  ps = null;
    ResultSet rs = null;

    public void destroy()
    {
        /*  write any servlet cleanup code here or remove this function */
        try { rs.close(); } catch (Exception e) { /* ignored */ }
        try { ps.close(); } catch (Exception e) { /* ignored */ }
        try { c.close(); } catch (Exception e) { /* ignored */ }
    }

    /**
     * Handles HTTP GET requests
     *
     * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request,
     *      HttpServletResponse response)
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        // implement your GET method handling code here
        if(request.getParameter("action") == null) {
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head><title>Something went wrong</title></head>");
            out.println("<body> Invalid parameter for action </body>");
            out.println("</html>");
            out.close();
            return;
        }
        String action = request.getParameter("action");
        if(action.equals("open")) {
            Open(request, response);
        } else if (action.equals("preview")) {
            Preview(request, response);
        } else if (action.equals("list")) {
            List(request, response);
        } else {
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head><title>Action do not exist!</title></head>");
            out.println("<body> Action do not exist!</body>");
            out.println("</html>");
            out.close();
            return;
        }
    }

    /**
     * Handles HTTP POST requests
     *
     * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request,
     *      HttpServletResponse response)
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        // implement your POST method handling code here
        String action = request.getParameter("action");
        if(action.equals("open")) {
            Open(request, response);
        } else if (action.equals("save")) {
            Save(request, response);
        } else if (action.equals("delete")) {
            Delete(request, response);
        } else if (action.equals("preview")) {
            Preview(request, response);
        } else if (action.equals("list")) {
            List(request, response);
        } 
        else {
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head><title>Action do not exist!</title></head>");
            out.println("<body> Action do not exist!</body>");
            out.println("</html>");
            out.close();
            return;
        }
    }

    // return the "edit page" for the post with the given postid by the user
    private void Open(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String username = request.getParameter("username");
        String pid = request.getParameter("postid");
        if(username == null || pid == null) {
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head><title>Something went wrong</title></head>");
            out.println("<body> Invalid parameter in open action </body>");
            out.println("</html>");
            out.close();
            return;
       }
        try {
            int postid = Integer.parseInt(pid);
            String title = request.getParameter("title");
            String body = request.getParameter("body");

            if(title == null) {
                title = "";
            }
            if(body == null) {
                body = "";
            }
            
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/CS144", "cs144", "");
            ps = c.prepareStatement("SELECT * FROM Posts WHERE username = ? AND postid = ?");
            ps.setString(1, username);
            ps.setInt(2, postid);
            rs = ps.executeQuery();
            if (rs.next()) {
                title = rs.getString("title");
                body = rs.getString("body");
            } 
            else if (postid != 0) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
           
            request.setAttribute("title", title);
            request.setAttribute("body", body);
        }
        catch (NumberFormatException en) {
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head><title>Something went wrong</title></head>");
            out.println("<body> NumberFormatException caught: invalid postid </body>");
            out.println("</html>");
            out.close();
            return;
        }
        catch (SQLException es) {
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head><title>Something went wrong</title></head>");
            out.println("<body> SQLException caught </body>");
            out.println("</html>");
            out.close();
            return;
        }
        finally {
            destroy();
        }
        request.getRequestDispatcher("/edit.jsp").forward(request, response);
    }

    // save the post into the database and go to the "list page" for the user
    private void Save(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String username = request.getParameter("username");
        String pid = request.getParameter("postid");
        String title = request.getParameter("title");
        String body = request.getParameter("body");
        int postid = Integer.parseInt(pid);
        try {
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/CS144", "cs144", "");
            if(postid <= 0) {
                ps = c.prepareStatement("SELECT MAX(postid) FROM Posts WHERE username = ?");
                ps.setString(1, username);
                rs = ps.executeQuery();
                String max_pid = "";
                int new_pid = 1;
                if (rs.next()) {
                    max_pid = rs.getString("MAX(postid)");
                    if(max_pid != null) {
                        new_pid = Integer.parseInt(max_pid) + 1;
                    }
                }
                ps.close();

                ps = c.prepareStatement("INSERT INTO Posts VALUES (?, ?, ?, ?, ?, ?)");
                ps.setString(1, username);
                ps.setInt(2, new_pid);
                ps.setString(3, title);
                ps.setString(4, body);
                Timestamp t = new Timestamp(new java.util.Date().getTime());
                ps.setTimestamp(5, t);
                ps.setTimestamp(6, t);
                ps.executeUpdate();
            } else {
                ps = c.prepareStatement("SELECT * FROM Posts WHERE username = ? AND postid = ?");
                ps.setString(1, username);
                ps.setInt(2, postid);
                rs = ps.executeQuery();
                if (rs.next()) {
                    ps.close();

                    ps = c.prepareStatement("UPDATE Posts SET title = ?, body = ?, modified = ? WHERE username = ? AND postid = ?");
                    ps.setString(1, title);
                    ps.setString(2, body);
                    Timestamp t = new Timestamp(new java.util.Date().getTime());
                    ps.setTimestamp(3, t);
                    ps.setString(4, username);
                    ps.setInt(5, postid);
                    ps.executeUpdate();
                }
            }
        }
        catch (SQLException es) {
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head><title>Something went wrong</title></head>");
            out.println("<body> SQLException caught </body>");
            out.println("</html>");
            out.close();
            return;
        }
        finally {
            destroy();
        }
        List(request, response);
    }

    // delete the corresponding post and go to the "list page"
    private void Delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String username = request.getParameter("username");
        String pid = request.getParameter("postid");
        try {
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/CS144", "cs144", "");
            ps = c.prepareStatement("DELETE FROM Posts WHERE username = ? AND postid = ?");
            ps.setString(1, username);
            ps.setString(2, pid);
            ps.executeUpdate();
        }
        catch (SQLException es) {
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head><title>Something went wrong</title></head>");
            out.println("<body> SQLException caught </body>");
            out.println("</html>");
            out.close();
            return;
        }
        finally {
            destroy();
        }
        List(request, response);
    }

    private void Preview(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        // request.getRequestDispatcher("/edit.jsp").forward(request, response);
        String username = request.getParameter("username");
        String pid = request.getParameter("postid");
        String title = request.getParameter("title");
        String body = request.getParameter("body");
        if (username == null || pid == null || title == null || body == null) {
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head><title>Something went wrong</title></head>");
            out.println("<body> Invalid parameter in preview action </body>");
            out.println("</html>");
            out.close();
            return;
        }
        try {
            Integer.parseInt(pid);
        }
        catch (NumberFormatException es) {
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head><title>Something went wrong</title></head>");
            out.println("<body>" + es.getMessage() + "</body>");
            out.println("</html>");
            out.close();
            return;
        }
        // return the "preview page" with the html rendering of the given title and body
        Parser parser = Parser.builder().build();
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        String title_render = "<h1>" + renderer.render(parser.parse(title)) + "</h1>";
        String body_render = renderer.render(parser.parse(body));
        String markdown = title_render + body_render;
        request.setAttribute("markdown", markdown);
        request.getRequestDispatcher("/preview.jsp").forward(request, response);
    }

    private void List(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String username = request.getParameter("username");
        if (username == null) {
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head><title>Something went wrong</title></head>");
            out.println("<body>Invalid parameter in list action, username is null</body>");
            out.println("</html>");
            out.close();
        }
        try {
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/CS144", "cs144", ""); 
            ps = c.prepareStatement("SELECT * FROM Posts WHERE username = ?");
            ps.setString(1, username);
            rs = ps.executeQuery();
            List<List<String>> table = new ArrayList<List<String>>();
            while (rs.next()) {
                String id = rs.getString("postid");
                String title = rs.getString("title");
                String created = rs.getString("created");
                created = created.substring(0, 19);
                String modified = rs.getString("modified");
                modified = modified.substring(0, 19);
                List<String> row = new ArrayList<String>();
                row.add(id);
                row.add(title);
                row.add(created);
                row.add(modified);
                table.add(row);
            }
            request.setAttribute("table", table);
        }
        catch (SQLException es) {
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head><title>Something went wrong</title></head>");
            out.println("<body>SQLException caught</body>");
            out.println("</html>");
            out.close();
            return;
        }
        finally {
            destroy();
        }
        request.getRequestDispatcher("/list.jsp").forward(request, response);
    }
}