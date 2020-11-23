package publish.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project


/**
 * 描述:
 *
 * author zys
 * create by 2020/11/17
 */
class PublishPlugin implements Plugin<Project> {


    @Override
    void apply(Project project) {
        if (!project.plugins.hasPlugin("com.android.application")) {
            throw ProjectConfigurationException("Plugin requires the 'com.android.application' plugin to be configured.", null)
        }

        def list =

                project.rootProject.subprojects.iterator()


        // Register a task
        project.tasks.register("greeting") { task ->
            task.group = PluginConst.GROUP
            task.doLast {
                println("Hello from plugin 'com.zys.publish.greeting'")
            }
        }
    }
}