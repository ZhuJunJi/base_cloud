package com.zhujunji.base.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zhujunji.base.service.BaseWorkItemDataService;
import com.zhujunji.base.service.SysWorkItemFieldService;
import com.zhujunji.base.service.SysWorkItemService;
import com.zhujunji.base.service.vo.SysWorkItemVO;
import com.zhujunji.common.constant.GlobalConstants;
import com.zhujunji.common.entity.Field;
import com.zhujunji.common.enums.LanguageEnum;
import com.zhujunji.common.exception.CommonBizException;
import com.zhujunji.common.exception.ExpCodeEnum;
import com.zhujunji.common.handler.FieldHandler;
import com.zhujunji.common.request.BaseJSONObjectCreateRequest;
import com.zhujunji.common.request.BaseJSONObjectUpdateRequest;
import com.zhujunji.common.utils.SpringBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.lang.NonNull;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@DubboService(protocol = "dubbo", version = "1.0.0")
public class BaseWorkItemDataServiceImpl implements BaseWorkItemDataService {

    @Resource
    private SysWorkItemFieldService sysWorkItemFieldService;

    @Resource
    private SysWorkItemService sysWorkItemService;

    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    private ApplicationContext applicationContext;

    @Override
    public boolean create(@NonNull Long workItemId, @NonNull BaseJSONObjectCreateRequest jsonObjectCreateRequest) {
        LanguageEnum language = jsonObjectCreateRequest.getLanguage();
        SysWorkItemVO sysWorkItemVO = sysWorkItemService.getById(workItemId, language);
        if (sysWorkItemVO == null) {
            throw new CommonBizException(ExpCodeEnum.WORKITEM_NOT_EXSIT_EXCEPTION);
        }
        String collection = sysWorkItemVO.getCollection();

        List<Field<?>> fieldList = sysWorkItemVO.getFieldList();
        if (CollectionUtils.isEmpty(fieldList)) {
            throw new CommonBizException(ExpCodeEnum.WORKITEM_FILED_EMPTY_EXCEPTION);
        }


        JSONObject jsonObject = jsonObjectCreateRequest.getData();

        if (jsonObject == null || jsonObject.isEmpty()) {
            throw new CommonBizException(ExpCodeEnum.WORKITEM_DATA_NULL_EXCEPTION);
        }
        Document document = new Document();
        // 遍历模板定义的所有字段，获取字段指定的处理器处理
        fieldList.forEach(field -> {
            String fieldKey = field.getFieldKey();
            // 获取字段的处理器
            FieldHandler fieldHandler = getFieldHandler(collection, fieldKey);
            Object value = fieldHandler.handlerCreate(field, jsonObject.get(fieldKey));
            document.put(fieldKey, value);
        });

        Document documentDO = mongoTemplate.insert(document, collection);

        ObjectId id = documentDO.get("_id", ObjectId.class);

        log.info("insert success id:{}", id.toString());

        return true;
    }

    @Override
    public boolean update(Long workItemId, BaseJSONObjectUpdateRequest jsonObjectUpdateRequest) {
        return false;
    }

    /**
     * 获取工作项字段处理器
     *
     * @param collection 集合
     * @param fieldKey   字段信息
     * @return String
     */
    private String getFieldHandlerName(String collection, String fieldKey) {
        if (StringUtils.isBlank(collection) || StringUtils.isBlank(fieldKey)) {
            log.error("参数为空，获取字段处理器名称失败！");
            throw new CommonBizException(ExpCodeEnum.PARAM_NULL);
        }
        return collection + "_" + fieldKey;
    }

    /**
     * 获取工作项字段处理器
     *
     * @param collection 集合
     * @param fieldKey   字段信息
     * @return FieldHandler
     */
    private FieldHandler getFieldHandler(String collection, String fieldKey) {
        // 获取工作项相应的字段处理器
        String fieldHandlerName = getFieldHandlerName(collection, fieldKey);

        FieldHandler fieldHandler = SpringBeanUtil.getBeanIfAvailable(applicationContext, fieldHandlerName,
                FieldHandler.class);

        if (fieldHandler != null) {
            return fieldHandler;
        }
        // 没有实现指定的字段处理器时获取默认的字段处理器
        FieldHandler defaultFieldHandler = SpringBeanUtil.getBeanIfAvailable(applicationContext,
                GlobalConstants.DEFAULT_FIELD_HANDLER, FieldHandler.class);

        if (null == defaultFieldHandler) {
            throw new CommonBizException(ExpCodeEnum.DEFAULT_FIELD_HANDLER_NOT_EXSIT_EXCEPTION);
        }
        return defaultFieldHandler;
    }
}
