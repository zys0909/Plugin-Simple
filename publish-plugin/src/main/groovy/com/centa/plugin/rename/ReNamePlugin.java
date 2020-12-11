package com.centa.plugin.rename;

import com.android.build.gradle.AppExtension;
import com.android.build.gradle.api.ApkVariantOutput;
import com.android.build.gradle.api.ApplicationVariant;
import com.centa.plugin.PluginConst;
import com.centa.plugin.PluginUtil;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Nonnull;

import groovy.text.SimpleTemplateEngine;

/**
 * 描述:
 * <p>
 * author zys
 * create by 2020/11/25
 */
public class ReNamePlugin implements Plugin<Project> {
    @Override
    public void apply(@Nonnull Project p) {
        PluginUtil.checkHasPlug(p, PluginConst.APPLICATION);

        PluginUtil.applyExtension(p, "rename", RenameExtension.class);
        p.afterEvaluate(project -> {
            RenameExtension config = RenameExtension.getConfig(p);
            AppExtension appExtension = project.getExtensions().getByType(AppExtension.class);
            appExtension.getApplicationVariants().forEach(appVariant -> {
                if ("debug".equalsIgnoreCase(appVariant.getBuildType().getName())) {
//                    File apkDir = new File(project.getBuildDir(), "apk");
                    if (config.apkOutputFolder != null) {
                        File apkDir = config.apkOutputFolder;
                        boolean mkdirs = true;
                        if (!apkDir.exists()) {
                            mkdirs = apkDir.mkdirs();
                        }
                        if (mkdirs) {
                            appVariant.getPackageApplicationProvider().get()
                                    .getOutputDirectory().set(apkDir);
                        }
                    }
                    appVariant.getOutputs().forEach(output -> {
                        if (output.getOutputFile() != null && output.getOutputFile().getName().endsWith(".apk")) {
                            String apkName = generateApkName(project, appVariant, config.apkFileNameFormat);
                            ((ApkVariantOutput) output).setOutputFileName(apkName);
                        }
                    });
                }
            });
        });
    }

    private String generateApkName(@Nonnull Project project, ApplicationVariant variant, String apkFileNameFormat) {
        Map<String, String> map = new HashMap<>();
        String buildTime = new SimpleDateFormat("yyyyMMdd-HHmm", Locale.CHINA).format(new Date());
        map.put("appName", project.getName());
        map.put("projectName", project.getRootDir().getName());
        map.put("buildType", variant.getBuildType().getName());
        map.put("flavorName", variant.getFlavorName());
        map.put("versionName", variant.getVersionName());
        map.put("versionCode", String.valueOf(variant.getVersionCode()));
        map.put("buildTime", buildTime);
        try {
            return new SimpleTemplateEngine().createTemplate(apkFileNameFormat).make(map).toString();
        } catch (Exception e) {
            e.printStackTrace();
            String versionName = variant.getVersionName();
            String buildType = variant.getBuildType().getName();
            String flavorName = variant.getFlavorName();
            StringBuilder apkSB = new StringBuilder(32);
            apkSB.append("app");
            if (PluginUtil.isNotEmpty(buildType)) {
                apkSB.append("-").append(buildType);
            }
            if (PluginUtil.isNotEmpty(flavorName)) {
                apkSB.append("-").append(flavorName);
            }
            if (PluginUtil.isNotEmpty(versionName)) {
                apkSB.append("-").append(versionName);
            }
            apkSB.append(".apk");
            return apkSB.toString();
        }
    }

}
