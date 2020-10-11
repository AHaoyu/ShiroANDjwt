package ShiroANDjwt.Utils;


import ShiroANDjwt.pojo.Result;
import ShiroANDjwt.pojo.ResultEnum;

public class ResultUtils {
    public static Result success(Object object) {
        return new Result()
                .setCode(ResultEnum.SUCCESS.getCode())
                .setMessage(ResultEnum.SUCCESS.getMessage())
                .setData(object);
    }

    public static Result success() {
        return success(null);
    }

    public static Result error(String errMsg) {
        return error(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getMessage(), errMsg);
    }

    public static Result dataNotFoundError(String errMsg) {
        return error(ResultEnum.DATA_NOT_FOUND.getCode(), ResultEnum.DATA_NOT_FOUND.getMessage(), errMsg);
    }

    public static Result unauthorizedError(String errMsg) {
        return error(ResultEnum.UNAUTHORIZED.getCode(), ResultEnum.UNAUTHORIZED.getMessage(), errMsg);
    }

    public static Result forbiddenError(String errMsg) {
        return error(ResultEnum.FORBIDDEN.getCode(), ResultEnum.FORBIDDEN.getMessage(), errMsg);
    }

    public static Result paramNotVaildError(String errMsg) {
        return error(ResultEnum.PARAM_NOT_VALID.getCode(), ResultEnum.PARAM_NOT_VALID.getMessage(), errMsg);
    }

    public static Result paramTypeConversionError(String errorMsg) {
        return error(ResultEnum.TYPE_CONVERTION_ERROR.getCode(), ResultEnum.TYPE_CONVERTION_ERROR.getMessage(), errorMsg);
    }

    public static Result numberFormatError(String errorMsg) {
        return error(ResultEnum.NUMBER_FORMAT_ERROR.getCode(), ResultEnum.NUMBER_FORMAT_ERROR.getMessage(), errorMsg);
    }

    public static Result internalServerError(String errorMsg) {
        return error(ResultEnum.INTERNAL_SERVER_ERROR.getCode(), ResultEnum.INTERNAL_SERVER_ERROR.getMessage(), errorMsg);
    }

    public static Result interfaceNotFoundError(String errorMsg) {
        return error(ResultEnum.NOT_FOUND.getCode(), ResultEnum.NOT_FOUND.getMessage(), errorMsg);
    }

    private static Result error(Integer code, String message, String errMsg) {
        return new Result()
                .setCode(code)
                .setMessage(message)
                .setData(errMsg);
    }
}
