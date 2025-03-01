package demo.slm.url.controller.response;

public record ResponseDto<T>(
        T data
) {
    public static <T> ResponseDto<T> of(T t) {
        return new ResponseDto<>(t);
    }
}
