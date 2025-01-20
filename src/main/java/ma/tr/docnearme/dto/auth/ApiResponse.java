package ma.tr.docnearme.dto.auth;

public record ApiResponse<T>(
         String message
        ,
         T data
) {
}
