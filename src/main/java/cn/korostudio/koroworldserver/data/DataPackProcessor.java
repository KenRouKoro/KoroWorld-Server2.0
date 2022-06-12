package cn.korostudio.koroworldserver.data;

import cn.hutool.json.JSONObject;

public interface DataPackProcessor {
    String run(JSONObject dataPack);
}
