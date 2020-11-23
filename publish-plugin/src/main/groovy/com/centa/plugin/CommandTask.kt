package com.centa.plugin

import org.gradle.api.tasks.AbstractExecTask
import org.gradle.internal.os.OperatingSystem

/**
 * 描述:
 *
 * author zys
 * create by 2020/11/19
 */
open class CommandTask : AbstractExecTask<CommandTask>(CommandTask::class.java) {
    override fun commandLine(vararg arguments: Any?): CommandTask {
        val windows = OperatingSystem.current().isWindows
        if (windows) {
            commandLine("cmd", "/c", "gradlew.bat", arguments)
        } else {
            commandLine("gradlew", arguments)
        }
        return this
    }
}