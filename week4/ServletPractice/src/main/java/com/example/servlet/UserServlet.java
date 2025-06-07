package com.example.servlet;

import com.example.entity.User;
import com.example.util.HibernateUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.util.List;

/**
 * UserServlet now integrates JSP front-end for CRUD operations.
 */
@WebServlet(name = "UserServlet", urlPatterns = {"/users/*"})
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo(); // "/", "/list-users", "/new-user", "/view-user/{id}", "/update-user/{id}"

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // LIST ALL
            if (path == null || "/".equals(path) || "/list-users".equals(path)) {
                List<User> users = session.createQuery("FROM User", User.class).getResultList();
                req.setAttribute("users", users);
                req.getRequestDispatcher("/WEB-INF/listUsers.jsp").forward(req, resp);
                return;
            }

            // SHOW CREATE FORM
            if ("/new-user".equals(path)) {
                req.getRequestDispatcher("/WEB-INF/newUser.jsp").forward(req, resp);
                return;
            }

            // VIEW SINGLE
            if (path.startsWith("/view-user/")) {
                Integer id = Integer.valueOf(path.substring("/view-user/".length()));
                User u = session.find(User.class, id);
                if (u == null) {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                req.setAttribute("user", u);
                req.getRequestDispatcher("/WEB-INF/viewUser.jsp").forward(req, resp);
                return;
            }

            // SHOW EDIT FORM
            if (path.startsWith("/update-user/")) {
                Integer id = Integer.valueOf(path.substring("/update-user/".length()));
                User u = session.find(User.class, id);
                if (u == null) {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                req.setAttribute("user", u);
                req.getRequestDispatcher("/WEB-INF/editUser.jsp").forward(req, resp);
                return;
            }

            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo(); // "/new-user", "/update-user/{id}", "/delete-user/{id}"

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            // CREATE
            if ("/new-user".equals(path)) {
                String name = req.getParameter("name");
                String email = req.getParameter("email");
                User u = new User(name, email);
                session.persist(u);
                tx.commit();
                resp.sendRedirect(req.getContextPath() + "/users");
                return;
            }

            // UPDATE
            if (path.startsWith("/update-user/")) {
                Integer id = Integer.valueOf(path.substring("/update-user/".length()));
                User u = session.find(User.class, id);
                if (u == null) {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                u.setName(req.getParameter("name"));
                u.setEmail(req.getParameter("email"));
                tx.commit();
                resp.sendRedirect(req.getContextPath() + "/users/view-user/" + id);
                return;
            }

            // DELETE
            if (path.startsWith("/delete-user/")) {
                Integer id = Integer.valueOf(path.substring("/delete-user/".length()));
                User u = session.find(User.class, id);
                if (u == null) {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                session.remove(u);
                tx.commit();
                resp.sendRedirect(req.getContextPath() + "/users");
                return;
            }

            tx.rollback();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
