package com.zhujunji.common.request;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class BaseJSONObjectCreateRequest extends BaseCreateRequest{

    private JSONObject data;
}
