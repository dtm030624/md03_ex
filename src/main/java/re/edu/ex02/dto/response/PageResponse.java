package re.edu.ex02.dto.response;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Builder
@Getter
public class PageResponse<T> {
    private List<T> content;
    private long totalElements;
    private int totalPages;
    private int pageNumber;
    private int pageSize;
    private String sortDirection;
    private String sortField;
    private boolean isLast;
    private boolean isFirst;
}
