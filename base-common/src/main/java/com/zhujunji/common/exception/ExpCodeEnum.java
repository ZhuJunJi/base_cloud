package com.zhujunji.common.exception;

import java.io.Serializable;

import static com.zhujunji.common.exception.ExpPrefix.*;

/**
 * 全局的异常状态码 和 异常描述
 * <p>
 * PS:异常码一共由5位组成，前两位为固定前缀，请参考{@link ExpPrefix}
 *
 * @Author J.zhu
 */
@SuppressWarnings("all")
public enum ExpCodeEnum implements Serializable {

    /**
     * 通用异常
     */
    UNKNOW_ERROR(COM_EXP_PREFIX + "000", "未知异常"),
    ERROR_404(COM_EXP_PREFIX + "001", "没有该接口"),
    PARAM_NULL(COM_EXP_PREFIX + "002", "参数为空"),
    PARAM_ERROR(COM_EXP_PREFIX + "003", "参数错误"),
    NO_REPEAT(COM_EXP_PREFIX + "004", "请勿重复提交"),
    SESSION_NULL(COM_EXP_PREFIX + "005", "请求头中SessionId不存在"),
    HTTP_REQ_METHOD_ERROR(COM_EXP_PREFIX + "006", "HTTP请求方法不正确"),
    JSONERROR(COM_EXP_PREFIX + "007", "JSON解析异常"),
    UPLOAD_FAILED(COM_EXP_PREFIX + "008", "上传文件失败"),
    EMAIL_FORMAT_INCORRECT(COM_EXP_PREFIX + "009", "邮箱格式有误！"),
    CREATE_BY_NULL(COM_EXP_PREFIX + "010", "创建者为空！"),
    UPDATE_BY_NULL(COM_EXP_PREFIX + "011", "修改者为空！"),
    DATA_ACCESS_EXCEPTION_CODE(COM_EXP_PREFIX + "012", "持久层数据访问异常！"),
    NUMBER_FORMAT_EXCEPTION(COM_EXP_PREFIX + "014", "数字转换异常！"),
    FIELD_CREATE_EXCEPTION(COM_EXP_PREFIX + "015", "创建字段异常！"),
    FIELD_NOT_EXSIT_EXCEPTION(COM_EXP_PREFIX + "016", "字段不存在异常！"),
    CONVERT_NOT_EXSIT_EXCEPTION(COM_EXP_PREFIX + "017", "转换器不存在异常！"),
    DEFAULT_FIELD_HANDLER_NOT_EXSIT_EXCEPTION(COM_EXP_PREFIX + "018", "默认的通用字段处理器不存在请检查 Bean:DefaultFieldHandler 是否存在！"),
    PICKLIST_FIELD_OPTION_NOT_EXSIT_EXCEPTION(COM_EXP_PREFIX + "019", "枚举类型字段的选项值不存在！"),
    STRING_TO_OBJECT_ID_EXCEPTION(COM_EXP_PREFIX + "020",""),

    /**
     * User模块异常
     */
    USERNAME_NULL(USER_EXP_PREFIX + "000", "用户名为空"),
    PASSWD_NULL(USER_EXP_PREFIX + "001", "密码为空"),
    AUTH_NULL(USER_EXP_PREFIX + "002", "手机、电子邮件、用户名 至少填一个"),
    LOGIN_FAIL(USER_EXP_PREFIX + "003", "登录失败"),
    UNLOGIN(USER_EXP_PREFIX + "004", "尚未登录"),
    NO_PERMISSION(USER_EXP_PREFIX + "005", "没有权限"),
    PHONE_NULL(USER_EXP_PREFIX + "006", "手机号为空"),
    MAIL_NULL(USER_EXP_PREFIX + "007", "电子邮件为空"),
    USERTYPE_NULL(USER_EXP_PREFIX + "008", "用户类别为空"),
    ROLE_NULL(USER_EXP_PREFIX + "009", "角色为空"),
    ROLEID_NULL(USER_EXP_PREFIX + "010", "roleId为空"),
    MENUIDLIST_NULL(USER_EXP_PREFIX + "011", "menuIdList为空"),
    PERMISSIONIDLIST_NULL(USER_EXP_PREFIX + "012", "permissionIdList为空"),
    NAME_NULL(USER_EXP_PREFIX + "013", "name为空"),

    /**
     * Product模块异常
     */
    PRODUCT_NAME_NULL(PROD_EXP_PREFIX + "000", "产品名称为空"),

    /**
     * Order模块异常
     */
    PROCESSREQ_ORDERID_NULL(ORDER_EXP_PREFIX + "000", "受理请求中的orderId为空"),
    ORDER_NOT_EXIST(ORDER_EXP_PREFIX + "001", "OrderId未查询到订单信息"),

    /**
     * Analysis模块异常
     */
    XXXX_NULL(ANALYSIS_EXP_PREFIX + "000", "XXXX异常"),

    /** Common模块异常*/

    /** Dict 模块异常 */
    DICT_NULL_EXCEPTION(DICT_EXP_PREFIX + "001", "字典数据为空！"),
    DICT_TYPE_NULL_EXCEPTION(DICT_EXP_PREFIX + "002", "字典类型为空！"),
    DICT_LABEL_ZH_NULL_EXCEPTION(DICT_EXP_PREFIX + "003", "字典中文标签为空！"),
    DICT_LABEL_EN_NULL_EXCEPTION(DICT_EXP_PREFIX + "004", "字典英文标签为空！"),
    DICT_VALUE_NULL_EXCEPTION(DICT_EXP_PREFIX + "005", "字典值为空！"),
    DICT_PARENT_NOT_EXSIT_EXCEPTION(DICT_EXP_PREFIX + "006", "字典父节点不存在！"),
    DICT_EXSIT_EXCEPTION(DICT_EXP_PREFIX + "007", "字典已经存在！请检查类型、中英文标签 是否重复"),
    DICT_NOT_EXSIT_EXCEPTION(DICT_EXP_PREFIX + "008", "字典不存在！"),
    DICT_ID_EXCEPTION(DICT_EXP_PREFIX + "009", "字典 ID 异常！"),
    DICT_MOVE_TO_OTHER_TYPE_EXCEPTION(DICT_EXP_PREFIX + "010", "字典节点变更异常！字典不能移动到其他类型的节点"),

    /** Field 模块异常 */
    FIELD_EXSIT_EXCEPTION(FIELD_EXP_PREFIX + "001", "字段已经存在！已经存在 field_key 相同的字段信息！"),
    FIELD_KEY_NULL_EXCEPTION(FIELD_EXP_PREFIX + "002", "字段 field_key 不能为空！"),
    FIELD_NAME_ZH_NULL_EXCEPTION(FIELD_EXP_PREFIX + "003", "字段中文名称不能为空！"),
    FIELD_NAME_EN_NULL_EXCEPTION(FIELD_EXP_PREFIX + "004", "字段英文名称不能为空！"),
    FIELD_DESC_NULL_EXCEPTION(FIELD_EXP_PREFIX + "005", "字段描述不能为空！"),
    FIELD_TYPE_NULL_EXCEPTION(FIELD_EXP_PREFIX + "006", "字段类型不能为空！"),
    FIELD_DICT_TYP_NULL_EXCEPTION(FIELD_EXP_PREFIX + "007", "选项类型字段的选项类型不能为空！"),
    WORK_ITEM_FIELD_ITEM_ID_NULL_EXCEPTION(FIELD_EXP_PREFIX + "008", "工作项字段的工作项 ID 不能为空！"),
    WORK_ITEM_FIELD_FIELD_ID_NULL_EXCEPTION(FIELD_EXP_PREFIX + "008", "工作项字段的字段 ID 不能为空！"),

    /** Work Item 模块异常 */
    WORKITEM_NAME_NULL_EXCEPTION(WORKITEM_EXP_PREFIX + "001", "工作项名称为空异常！"),
    WORKITEM_EXSIT_EXCEPTION(WORKITEM_EXP_PREFIX + "002", "工作项已经存在！"),
    WORKITEM_NOT_EXSIT_EXCEPTION(WORKITEM_EXP_PREFIX + "003", "工作项不存在！"),
    WORKITEM_FILED_EMPTY_EXCEPTION(WORKITEM_EXP_PREFIX + "004", "工作项还未配置任何字段信息！"),
    WORKITEM_DATA_NULL_EXCEPTION(WORKITEM_EXP_PREFIX + "005", "工作项数据为空！"),
    WORKITEM_COLLECTION_NULL_EXCEPTION(WORKITEM_EXP_PREFIX + "006", "工作项集合为空！"),

    /**
     * Seckill模块异常
     */
    SECKILL_MUCH(SEC_KILL_EXP_PREFIX + "000", "哎呦喂，人也太多了，请稍后！"),
    SECKILL_SUCCESS(SEC_KILL_EXP_PREFIX + "001", "秒杀成功"),
    SECKILL_END(SEC_KILL_EXP_PREFIX + "002", "秒杀结束"),
    SECKILL_REPEAT_KILL(SEC_KILL_EXP_PREFIX + "003", "重复秒杀"),
    SECKILL_INNER_ERROR(SEC_KILL_EXP_PREFIX + "004", "系统异常"),
    SECKILL_DATE_REWRITE(SEC_KILL_EXP_PREFIX + "005", "数据篡改"),

    END(ORDER_EXP_PREFIX + "XXX", "XXX");

    private String code;
    private String message;

    ExpCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
