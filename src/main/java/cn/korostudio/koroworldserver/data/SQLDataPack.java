package cn.korostudio.koroworldserver.data;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
@Data
public class SQLDataPack {
    @Id
    String UUID = StrUtil.uuid();
    String tags;
    @Lob
    String data;
}
