package com.zhujunji.common.request;

import com.alibaba.fastjson.JSONObject;
import com.zhujunji.common.enums.LanguageEnum;
import lombok.Data;

import java.util.Date;

@Data
public class BaseJSONObjectCreateRequest extends BaseCreateRequest{

    private JSONObject data;

    public BaseJSONObjectCreateRequest() {
        this.data = new JSONObject();
    }

    @Override
    public void initAfter() {
        super.initAfter();
        // 创建人
        data.put("create_by", this.getRequester());
        // 创建时间
        data.put("create_time", this.getCreateTime());
        // 更新人
        data.put("update_by", this.getRequester());
        // 更新时间
        data.put("update_time", this.getCreateTime());
    }
}
