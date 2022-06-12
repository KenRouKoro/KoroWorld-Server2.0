package cn.korostudio.koroworldserver.httpservice;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.korostudio.koroworldserver.data.DataPackProcess;
import cn.korostudio.koroworldserver.data.SQLDataPack;
import cn.korostudio.koroworldserver.data.sql.SQLDataPackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController()
@RequestMapping(value = "/data")
public class HttpService {
    protected static SQLDataPackRepository sqlDataPackRepository;

    @Autowired
    public SQLDataPackRepository getSqlDataPackRepository() {
        return sqlDataPackRepository;
    }

    @PostMapping("/process")
    public String process(@RequestParam Map<String, Object> params){
        String data = (String) params.get("data");
        JSONObject dataJSON = JSONUtil.parseObj(data);
        dataJSON.putOnce("http",true);
        DataPackProcess.run(dataJSON);
        return dataJSON.toString();
    }


}
