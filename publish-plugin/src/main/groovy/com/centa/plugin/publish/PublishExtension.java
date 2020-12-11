package com.centa.plugin.publish;

import org.gradle.api.Project;

/**
 * 描述:
 * <p>
 * author zys
 * create by 2020/12/10
 */
public class PublishExtension {

    public String prefix;

    public PublishExtension() {
    }

    public PublishExtension(Project project) {
        prefix = "publish--";
    }

    public static PublishExtension getConfig(Project project) {
        PublishExtension config = project.getExtensions().findByType(PublishExtension.class);
        if (config == null) {
            config = new PublishExtension();
        }
        return config;
    }
}
