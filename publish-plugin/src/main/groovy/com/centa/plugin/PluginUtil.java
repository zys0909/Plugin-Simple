package com.centa.plugin;


import org.gradle.api.Project;
import org.gradle.api.ProjectConfigurationException;

/**
 * 描述:
 * <p>
 * author zys
 * create by 2020/12/9
 */
public class PluginUtil {
    private PluginUtil() {
    }


    public static boolean isNotEmpty(CharSequence s) {
        return s != null && s.length() != 0;
    }

    public static void checkHasPlug(Project project, String pluginName) {
        if (!project.getPlugins().hasPlugin(pluginName)) {
            throw new ProjectConfigurationException("Plugin requires the " + pluginName + " plugin to be configured.", (Throwable) null);
        }
    }

    public static boolean notAppProject(Project project) {
        return !project.getPlugins().hasPlugin(PluginConst.APPLICATION);
    }

    public static <T> void applyExtension(Project project, String extName, Class<?> clazz) {
        project.getExtensions().create(extName, clazz,project);
    }
}
