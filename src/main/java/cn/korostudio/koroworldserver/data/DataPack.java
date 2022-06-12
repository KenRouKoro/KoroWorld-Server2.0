package cn.korostudio.koroworldserver.data;

import lombok.Data;
/**
 * This class is not for use.Just a Template;
 **/
@Data
public class DataPack {
    String uuid;
    String type;//command, or in Mod , These are the only ones that are handled on the server.
    String from;
    String target;// servername or ALL
    String data;//Can be null
    boolean http;
}
