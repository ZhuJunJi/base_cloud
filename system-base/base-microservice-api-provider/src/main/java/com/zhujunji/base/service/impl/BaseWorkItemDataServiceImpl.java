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
        // ????????????????????????????????????????????????????????????????????????
        fieldList.forEach(field -> {
            String fieldKey = field.getFieldKey();
            // ????????????????????????
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
     * ??????????????????????????????
     *
     * @param collection ??????
     * @param fieldKey   ????????????
     * @return String
     */
    private String getFieldHandlerName(String collection, String fieldKey) {
        if (StringUtils.isBlank(collection) || StringUtils.isBlank(fieldKey)) {
            log.error("???????????????????????????????????????????????????");
            throw new CommonBizException(ExpCodeEnum.PARAM_NULL);
        }
        return collection + "_" + fieldKey;
    }

    /**
     * ??????????????????????????????
     *
     * @param collection ??????
     * @param fieldKey   ????????????
     * @return FieldHandler
     */
    private FieldHandler getFieldHandler(String collection, String fieldKey) {
        // ???????????????????????????????????????
        String fieldHandlerName = getFieldHandlerName(collection, fieldKey);

        FieldHandler fieldHandler = SpringBeanUtil.getBeanIfAvailable(applicationContext, fieldHandlerName,
                FieldHandler.class);

        if (fieldHandler != null) {
            return fieldHandler;
        }
        // ?????????????????????????????????????????????????????????????????????
        FieldHandler defaultFieldHandler = SpringBeanUtil.getBeanIfAvailable(applicationContext,
                GlobalConstants.DEFAULT_FIELD_HANDLER, FieldHandler.class);

        if (null == defaultFieldHandler) {
            throw new CommonBizException(ExpCodeEnum.DEFAULT_FIELD_HANDLER_NOT_EXSIT_EXCEPTION);
        }
        return defaultFieldHandler;
    }
}
