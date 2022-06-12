package cn.korostudio.koroworldserver.data.sql;

import cn.korostudio.koroworldserver.data.SQLDataPack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SQLDataPackRepository extends JpaRepository<SQLDataPack, String> {
    SQLDataPack findByTags(String tags);
}
