package com.centa.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task


/**
 * 描述:
 *
 * author zys
 * create by 2020/11/17
 */
class PublishPlugin implements Plugin<Project> {

    private final static String APP = "com.android.application"


    @Override
    void apply(Project project) {
        if (!project.plugins.hasPlugin(APP)) {
            throw ProjectConfigurationException("Plugin requires the 'com.android.application' plugin to be configured.", null)
        }

        def names = new ArrayList<String>()
        def iterator = project.rootProject.subprojects.iterator()
        println("sub-projects : " + project.rootProject.subprojects.size())
        while (iterator.hasNext()) {
            def p = iterator.next()
            if (!p.plugins.hasPlugin(APP)) {
                names.add(p.name)
            }
        }
        def tasks = new ArrayList<Task>()
        names.forEach { name ->
            def uploadArchives = project.rootProject.tasks.findByName(name + ":uploadArchives")
            if (uploadArchives != null) {
                tasks.add(uploadArchives)
            }
        }

        // Register a task
        project.tasks.create("greeting") { task ->
            task.group = "centa"
            println("list size : " + tasks.size())
            tasks.forEach { name ->
                println("project : " + name)
            }
        }
    }
}