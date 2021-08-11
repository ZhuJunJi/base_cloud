package com.zhujunji.common.request;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class BaseJSONObjectUpdateRequest extends BaseUpdateRequest{

    private JSONObject data;
}
