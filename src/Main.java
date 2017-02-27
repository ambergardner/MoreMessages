import com.github.mustachejava.Mustache;
import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    static User user;

    static HashMap<String, User> users = new HashMap<>();

    public static void main(String[] args) {
        Spark.init();

        // ArrayList<Messsaga> commentList = new ArrayList<Messsaga>();

        Spark.get("/",
                (request, response) -> {
                    Session session = request.session();
                    String name = session.attribute("userName");
                    User user = users.get(name);

                    HashMap m = new HashMap();
                    if (user == null) {
                        return new ModelAndView(m, "create-user.html");
                    } else {
                        m.put("name", user.name);
                        m.put("messageboard", user.messages);
                        return new ModelAndView(user, "create-messages.html");
                    }
                },
                new MustacheTemplateEngine()
        );
        Spark.post("/create-user", (request, response) -> {
                    String name = request.queryParams("loginName");
                    String password = request.queryParams("password");
                    User user = users.get(name);
                    if (user == null) {
                        user = new User(name, password);

                        Session session = request.session();
                        session.attribute("userName", name);
                        users.put(name, user);
                    } else if (user.password.equals(password)) {
                        Session session = request.session();
                        session.attribute("userName", name);
                    }


                    response.redirect("/");
                    return "/";
                });

        Spark.post("/create-message", (request, response) -> {
            Message message1 = new Message(request.queryParams("messageboard"));
            message1.equals (message1);
            response.redirect("/");
            return "";

              });
        Spark.post("/logout", (request, response) -> {
            Session session = request.session();
            session.invalidate();
            response.redirect("/");
            return "";
        });

        Spark.delete("/delete", (request, response) -> {
            int number = Integer.parseInt(request.queryParams("delete"));

            Session session = request.session();
            String name = session.attribute("userName");
            User user = users.get(name);
            
            HashMap m = new HashMap();
            m.put("name", user.name);
            m.put("messageboard", user.messages);

            //users.equals(user).messageboard.remove(number - 1);

            response.redirect("/");
            return "";
        });


    };
}
