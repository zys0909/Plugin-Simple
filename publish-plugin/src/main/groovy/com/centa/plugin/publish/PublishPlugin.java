package com.centa.plugin.publish;

import com.centa.plugin.PluginConst;
import com.centa.plugin.PluginUtil;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.Exec;
import org.gradle.internal.os.OperatingSystem;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;


/**
 * 描述:
 * <p>
 * author zys
 * create by 2020/11/17
 */
public class PublishPlugin implements Plugin<Project> {

    @Override
    public void apply(@Nonnull Project appProject) {
        PluginUtil.checkHasPlug(appProject, PluginConst.APPLICATION);

        PluginUtil.checkHasPlug(appProject, "maven");

        PluginUtil.applyExtension(appProject, "publishMaven", PublishExtension.class);
        appProject.afterEvaluate(project -> {
            PublishExtension config = PublishExtension.getConfig(project);
            project.getRootProject().getSubprojects().forEach(project1 -> {
                if (PluginUtil.notAppProject(project1)) {
                    createTask(project, project1.getName(), config);
                }
            });

        });
    }

    private void createTask(@Nonnull Project project, String projectName, PublishExtension config) {
        String prefix = config.prefix;
        project.getTasks().create(prefix + projectName, Exec.class, exec -> {
            exec.setGroup(PluginConst.GROUP);
            exec.workingDir(exec.getProject().getRootDir());

            List<String> commandList = new ArrayList<>();
            if (OperatingSystem.current().isWindows()) {
                commandList.add("cmd");
                commandList.add("/c");
                commandList.add("gradlew.bat");
            } else {
                commandList.add("gradlew");
            }
            commandList.add(projectName + ":uploadArchives");
            exec.commandLine(commandList.toArray());
        });
    }
}
