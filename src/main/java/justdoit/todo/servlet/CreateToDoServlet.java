package justdoit.todo.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import justdoit.common.jpa.FormatUtils;
import justdoit.common.ejb.ValidationBean;
import justdoit.common.exception.EntityAlreadyExistsException;
import justdoit.common.jpa.Form;
import justdoit.todo.ejb.CategoryBean;
import justdoit.todo.ejb.ToDoBean;
import justdoit.todo.jpa.Category;
import justdoit.todo.jpa.CategoryId;
import justdoit.todo.jpa.ToDo;
import justdoit.todo.jpa.ToDoPriority;
import justdoit.todo.jpa.ToDoStatus;
import justdoit.common.jpa.User;
import justdoit.common.ejb.UserBean;

@WebServlet(name = "CreateToDoServlet", urlPatterns = {"/view/todo/create/"})
public class CreateToDoServlet extends HttpServlet {

    @EJB
    CategoryBean categoryBean;

    @EJB
    ToDoBean toDoBean;

    @EJB
    UserBean userBean;

    @EJB
    ValidationBean validationBean;

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        List<User> users = this.userBean.findAll();
        session.setAttribute("users", users);

        List<Category> categories = this.categoryBean.findByUser(this.userBean.getCurrentUser());
        session.setAttribute("categories", categories);
        //TODO: In deutscher Sprache anzeigen
        ToDoPriority[] priorities = ToDoPriority.values();
        session.setAttribute("priorities", priorities);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/createToDo.jsp");
        dispatcher.forward(request, response);

        session.removeAttribute("todo_form");
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<User> user = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        HttpSession session = request.getSession();

        User todoUser = this.userBean.findByUsername(request.getParameter("todo_user"));
        user.add(todoUser);

        CategoryId id = new CategoryId(request.getParameter("todo_user"), request.getParameter("todo_category"));
        Category todoCategory = this.categoryBean.findById(id);
        if (todoUser != this.userBean.getCurrentUser() && todoCategory == null) {
            try {
                this.categoryBean.saveNew(todoCategory, id);
            } catch (EJBException ex) {
                if (ex.getCausedByException() instanceof EntityAlreadyExistsException) {
                    errors.add("Das ToDo kann dem Benutzer $user nicht unter der Kategorie $category zugewiesen werden"
                            .replace("$user", todoUser.getUsername())
                            .replace("$category", todoCategory.getCategoryName()));
                }
            }
        };

        String dueDate = FormatUtils.formatDate(request.getParameter("todo_due_date"));
        String dueTime = FormatUtils.formatTime(request.getParameter("todo_due_time"));

        ToDoPriority priority = ToDoPriority.valueOf(request.getParameter("todo_priority"));
        ToDo todo = new ToDo(request.getParameter("todo_title"),
                todoCategory, //request.getParameter("todo_category"),
                request.getParameter("todo_description"),
                ToDoStatus.OPEN,
                priority,
                dueDate,
                dueTime,
                user);
        errors = this.validationBean.validate(todo, errors);

        if (!errors.isEmpty()) {
            Form form = new Form();
            form.setValues(request.getParameterMap());
            form.setErrors(errors);
            session.setAttribute("todo_form", form);

            response.sendRedirect(request.getRequestURI());
        } else {
            this.toDoBean.saveNew(todo, todo.getId());
            response.sendRedirect(request.getContextPath() + "/view/dashboard/");
        }
    }
}