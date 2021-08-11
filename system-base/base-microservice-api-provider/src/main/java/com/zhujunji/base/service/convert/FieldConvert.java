package com.zhujunji.base.service.convert;

import com.zhujunji.base.convert.Convert;
import com.zhujunji.base.domain.SysFieldDO;
import com.zhujunji.base.factory.ConvertFactory;
import com.zhujunji.base.factory.FieldFactory;
import com.zhujunji.base.service.SysDictService;
import com.zhujunji.base.service.vo.SysFieldVO;
import com.zhujunji.common.entity.Dict;
import com.zhujunji.common.entity.Field;
import com.zhujunji.common.entity.PicklistField;
import com.zhujunji.common.enums.FieldTypeEnum;
import com.zhujunji.common.enums.LanguageEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class FieldConvert {

    @Resource
    private SysDictService sysDictService;

    @SuppressWarnings("all")
    public Field<?> sysFieldDOToField(SysFieldDO sysFieldDO, LanguageEnum language) {
        if (sysFieldDO == null) {
            return null;
        }
        FieldTypeEnum fieldType = sysFieldDO.getFieldType();

        Field field = FieldFactory.createField(fieldType);
        BeanUtils.copyProperties(sysFieldDO, field, "minValue", "maxValue", "defaultValue");
        // 对应语言名称
        String name = sysFieldDO.getNameByLanguage(language);
        field.setName(name);

        Class<?> valueClass = fieldType.getValueClass();
        // 通过值类型获取转换器将 value 转换为指定类型
        Convert<?> convert = ConvertFactory.createConvert(valueClass);

        Object minValue = convert.stringToValue(sysFieldDO.getMinValue());
        Object maxValue = convert.stringToValue(sysFieldDO.getMaxValue());
        Object defaultValue = convert.stringToValue(sysFieldDO.getMinValue());

        // 最小值
        field.setMinValue(minValue);
        // 最大值
        field.setMaxValue(maxValue);
        // 默认值
        field.setDefaultValue(defaultValue);

        // 选项类型的字段补全选项值
        if (field instanceof PicklistField) {
            String dictType = sysFieldDO.getDictType();
            PicklistField picklistField = (PicklistField) field;
            List<Dict<?>> dictList = sysDictService.findByType(dictType, convert, language);
            picklistField.setOptions(dictList);
        }
        return field;
    }

    /**
     * SysFieldDO TO SysFieldVO
     * @param sysFieldDO    字段持久类
     * @return SysFieldVO
     */
    public SysFieldVO sysFieldDOToFieldVO(SysFieldDO sysFieldDO) {
        if(sysFieldDO == null){
            return null;
        }
        SysFieldVO sysFieldVO = new SysFieldVO();
        BeanUtils.copyProperties(sysFieldDO,sysFieldVO);
        return sysFieldVO;
    }
}
