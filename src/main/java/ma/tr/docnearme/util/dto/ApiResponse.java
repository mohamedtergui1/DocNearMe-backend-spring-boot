package ma.tr.docnearme.util.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private  String message;
    private  T data;
    private  PaginationMeta meta;

    public ApiResponse( String message ,T data) {
        this.data = data;
        this.message = message;
    }
}
