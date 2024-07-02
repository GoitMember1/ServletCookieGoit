package org.example;

import org.thymeleaf.context.WebContext;
import org.thymeleaf.TemplateEngine;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;

@WebServlet("/time")
public class TimeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String timezone = request.getParameter("timezone");
        if (timezone == null || timezone.isEmpty()) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("lastTimezone".equals(cookie.getName())) {
                        timezone = cookie.getValue();
                        break;
                    }
                }
            }
            if (timezone == null || timezone.isEmpty()) {
                timezone = "UTC";
            }
        } else {
            Cookie cookie = new Cookie("lastTimezone", timezone);
            response.addCookie(cookie);
        }

        ZoneId zoneId;
        try {
            zoneId = ZoneId.of(timezone);
        } catch (Exception e) {
            zoneId = ZoneId.of("UTC");
        }

        ZonedDateTime now = ZonedDateTime.now(zoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");

        WebContext ctx = new WebContext(request, response, getServletContext(), request.getLocale());
        ctx.setVariable("currentTime", now.format(formatter));

        TemplateEngine engine = ThymeleafConfig.getTemplateEngine();
        response.setContentType("text/html");
        engine.process("time", ctx, response.getWriter());
    }
}
