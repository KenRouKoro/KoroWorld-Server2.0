package cn.korostudio.koroworldserver;

import cn.korostudio.koroworldserver.mod.ModLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@Slf4j
public class ServerApplication {

    public final static ArrayList<String> Packages = new ArrayList<>(List.of(new String[]{"cn.korostudio.koroworldserver","cn.korostudio.koroworld"}));

    public static void main(String[] args) {

        //String modsFile = System.getProperty("user.dir")+"/mods";
        //ReflectUtil.invoke(ClassLoaders.appClassLoader(), "appendClassPath", modsFile);

        ModLoader.load();
        ModLoader.runInit();

        SpringBootApplication springBootApplication = ServerApplication.class.getAnnotation(SpringBootApplication.class);

        InvocationHandler h = Proxy.getInvocationHandler(springBootApplication);
        try {
            Field hField = h.getClass().getDeclaredField("memberValues");
            hField.setAccessible(true);
            Map memberValues = (Map) hField.get(h);
            String [] pack = new String[0];
            memberValues.put("scanBasePackages", Packages.toArray(pack));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            log.error("启动出现致命错误！");
            System.exit(0);
        }
        //Thread.currentThread().setContextClassLoader(ModLoader.getLoader());
        SpringApplication.run(ServerApplication.class, args);
    }

}
