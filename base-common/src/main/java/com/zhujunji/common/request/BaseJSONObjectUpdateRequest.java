package com.zhujunji.common.request;

import com.alibaba.fastjson.JSONObject;
import com.zhujunji.common.enums.LanguageEnum;
import lombok.Data;
import org.bson.Document;

import java.util.Date;

@Data
public class BaseJSONObjectUpdateRequest extends BaseUpdateRequest{

    private JSONObject data;

    public BaseJSONObjectUpdateRequest(){
        this.data = new JSONObject();
    }

    @Override
    public void init(String requestId, Long requester, LanguageEnum language, Date requestTime) {
        super.init(requestId, requester, language, requestTime);
        // 更新人
        data.put("update_by", this.getUpdateBy());
        // 更新时间
        data.put("update_time", this.getUpdateTime());
    }
}
