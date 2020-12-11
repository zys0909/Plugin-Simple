package com.centa.plugin.rename;


import org.gradle.api.Project;

import java.io.File;
import java.util.List;

/**
 * 描述:
 * <p>
 * author zys
 * create by 2020/12/10
 */
public class RenameExtension {

    static final String DEFAULT_APK_FILE_NAME_TEMPLATE = "${appName}-${buildType}-${channel}.apk";

    /**
     * apk output dir
     * default value: null, the channels' apk will output in '${project}/build/output/apk' folder
     */
    public File apkOutputFolder;

    /**
     * file name template string
     * <p>
     * Available vars:
     * 1. projectName
     * 2. appName
     * 3. packageName
     * 4. buildType
     * 5. channel
     * 6. versionName
     * 7. versionCode
     * 8. buildTime
     * 9. fileSHA1
     * 10. flavorName
     * <p>
     * default value: '${appName}-${buildType}-${channel}.apk'
     */
    public String apkFileNameFormat;

    public RenameExtension() {
        apkOutputFolder = null;
        apkFileNameFormat = DEFAULT_APK_FILE_NAME_TEMPLATE;
    }

    public RenameExtension(Project project) {
        apkOutputFolder = null;
        apkFileNameFormat = DEFAULT_APK_FILE_NAME_TEMPLATE;
    }

    static RenameExtension getConfig(Project project) {
        RenameExtension config = project.getExtensions().findByType(RenameExtension.class);
        if (config == null) {
            config = new RenameExtension();
        }
        return config;
    }
}
