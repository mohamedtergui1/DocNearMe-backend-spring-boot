package ma.tr.docnearme.dto;

public record ApiResponse<T>(
        String message
        ,
        T data
) {
}
