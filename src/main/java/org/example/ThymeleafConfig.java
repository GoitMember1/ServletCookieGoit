package org.example;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

public class ThymeleafConfig {
    private static TemplateEngine templateEngine;

    public static TemplateEngine getTemplateEngine() {
        if (templateEngine == null) {
            ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
            resolver.setPrefix("/templates/");
            resolver.setSuffix(".html");
            resolver.setTemplateMode("HTML5");
            resolver.setCacheable(false);

            templateEngine = new TemplateEngine();
            templateEngine.setTemplateResolver(resolver);
        }
        return templateEngine;
    }
}
