package justdoit.mail;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.mail.MessagingException;
import justdoit.common.jpa.User;
import justdoit.todo.ejb.ToDoBean;
import justdoit.todo.jpa.ToDo;

@Singleton
public class NotificationMailScheduler {

    @EJB
    MailBean mailBean;

    @EJB
    ToDoBean todoBean;

    @Schedule(dayOfWeek = "*", hour = "12", minute = "0", second = "0", persistent = false)
    public void sendNotificationMails() {
        List<ToDo> todoList = this.todoBean.getDueTasks();
        List<NotificationMailContent> notificationMailContentList = new ArrayList<NotificationMailContent>();
        //alle Mails erstellen
        for (ToDo todo : todoList) {
            for (User user : todo.getUser()) {
                notificationMailContentList.add(new NotificationMailContent(user, todo.getName(), todo.getDueDate(), todo.getDueTime()));
            }
        }
        //alle Mails versenden
        for (NotificationMailContent notificationMailContent : notificationMailContentList) {
            try {
                this.mailBean.sendMail(notificationMailContent);
            } catch (MessagingException ex) {
                Logger.getLogger(NotificationMailScheduler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
