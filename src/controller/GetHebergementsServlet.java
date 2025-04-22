package controller;

import dao.HebergementDAO;
import model.Hebergement;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import com.google.gson.Gson;

@WebServlet("/api/hebergements")
public class GetHebergementsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("✅ Servlet GET appelée");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HebergementDAO dao = new HebergementDAO();
        List<Hebergement> hebergements = dao.findAll();

        Gson gson = new Gson();
        String json = gson.toJson(hebergements);
        response.getWriter().write(json);
    }
}
