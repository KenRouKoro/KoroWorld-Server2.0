package cn.korostudio.koroworldserver.mod;

public abstract class ModTemplate {
    public abstract String getName();
    public abstract void Init();
    public abstract void Unload();
}
