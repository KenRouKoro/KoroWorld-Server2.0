package cn.korostudio.koroworldserver.mod;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

public abstract class ModTemplate {
    public abstract String getName();
    public abstract void Init();
    public abstract void Unload();
}
