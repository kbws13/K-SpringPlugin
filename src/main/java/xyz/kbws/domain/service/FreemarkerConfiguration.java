package xyz.kbws.domain.service;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.IOException;

/**
 * @author kbws
 * @date 2024/10/5
 * @description:
 */
public class FreemarkerConfiguration extends Configuration {
    public FreemarkerConfiguration() {
        this("/template");
    }

    public FreemarkerConfiguration(String basePackagePath) {
        super(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        setDefaultEncoding("UTF-8");
        setClassForTemplateLoading(getClass(), basePackagePath);
    }

    public Template getTemplate(String ftl) throws IOException {
        return this.getTemplate(ftl, "UTF-8");
    }
}
