package cn.korostudio.koroworldserver.mod;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.lang.ClassScanner;
import cn.hutool.core.lang.JarClassLoader;
import cn.hutool.core.util.ClassLoaderUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import jdk.internal.loader.ClassLoaders;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
public class ModLoader {
    @Getter
    protected static ConcurrentHashMap<String,ModTemplate>mods = new ConcurrentHashMap<>();

    protected static Set<Class<?>>modsClass =null;

    protected static CopyOnWriteArrayList<String>classPath = new CopyOnWriteArrayList<>();

    static public void load(){
        log.info("Mod loading class being scanned.");
        JarClassLoader loader = ClassLoaderUtil.getJarClassLoader(new File(System.getProperty("user.dir")+"/mods"));
        ClassScanner classScanner = new ClassScanner(StrUtil.EMPTY,clazz -> ModTemplate.class.isAssignableFrom(clazz) && !ModTemplate.class.equals(clazz));
        classScanner.setClassLoader(loader);
        modsClass = classScanner.scan(true);
        log.info("Scan Mod load class complete.");
    }
    static public void runInit(){
        log.info("Mod being initialized.");
        for(Class<?>cla:modsClass){
            ModTemplate template = (ModTemplate) ReflectUtil.newInstanceIfPossible(cla);
            mods.put(template.getName(),template);
            log.info(template.getName());
        }
        for (ModTemplate template: mods.values()){
            template.Init();
        }
        log.info("Initialization mod complete");
    }
}
