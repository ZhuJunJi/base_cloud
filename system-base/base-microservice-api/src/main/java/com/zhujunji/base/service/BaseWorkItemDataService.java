package com.zhujunji.base.service;

import com.zhujunji.common.exception.CommonBizException;
import com.zhujunji.common.request.BaseJSONObjectCreateRequest;
import com.zhujunji.common.request.BaseJSONObjectUpdateRequest;
import org.springframework.lang.NonNull;

public interface BaseWorkItemDataService {

    /**
     * 通用新增工作项数据
     * @param workItemId                工作项 ID
     * @param jsonObjectCreateRequest   工作项新增数据
     * @return boolean
     */
    boolean create(@NonNull Long workItemId, @NonNull BaseJSONObjectCreateRequest jsonObjectCreateRequest) throws CommonBizException;

    /**
     * 通用更新工作项数据
     * @param workItemId                工作项 ID
     * @param jsonObjectUpdateRequest   工作项更新数据
     * @return boolean
     */
    boolean update(Long workItemId, BaseJSONObjectUpdateRequest jsonObjectUpdateRequest) throws CommonBizException;
}
