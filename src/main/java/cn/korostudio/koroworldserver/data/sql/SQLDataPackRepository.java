package cn.korostudio.koroworldserver.data.sql;

import cn.korostudio.koroworldserver.data.SQLDataPack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SQLDataPackRepository extends JpaRepository<SQLDataPack, String> {
    SQLDataPack findByTags(String tags);
    SQLDataPack findByUUID(String uuid);
}
