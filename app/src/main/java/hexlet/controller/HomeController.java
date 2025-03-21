package hexlet.controller;

import io.javalin.http.Context;

public class HomeController {
    
    public static void index(Context ctx) {
        ctx.render("index.jte");
    }
    
}