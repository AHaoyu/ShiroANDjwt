package ShiroANDjwt.pojo;

public enum ResultEnum {
    SUCCESS(1, "success"),
    FAIL(2, "fail"),
    DATA_NOT_FOUND(3, "data not found"),
    UNAUTHORIZED(4, "unauthorized"),
    FORBIDDEN(5, "forbidden"),
    PARAM_NOT_VALID(6, "param not found"),
    TYPE_CONVERTION_ERROR(7, "type convertion error"),
    NUMBER_FORMAT_ERROR(8, "number format error"),
    INTERNAL_SERVER_ERROR(9, "internal server error"),
    NOT_FOUND(10, "not found");

    private Integer code; private String message;

    private ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
