package az.vtb.iticket.mapper;//package az.vtb.iticket.mapper;
//
//import az.vtb.iticket.model.criteria.PageCriteria;
//import az.vtb.iticket.model.response.PageableResponse;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//
//import java.util.function.Function;
//
//public enum PageableMapper {
//    PAGEABLE_MAPPER;
//
//    public <R, T> PageableResponse<R> buildPageableResponse(Page<T> elements, Function<T, R> mapper) {
//        return PageableResponse.<R>builder()
//                               .content(elements.stream().map(mapper).toList())
//                               .totalElements(elements.getTotalElements())
//                               .totalPages(elements.getTotalPages())
//                               .hasNextPage(elements.hasNext())
//                               .build();
//    }
//
//    public PageRequest toPageRequest(PageCriteria pageCriteria) {
//        return PageRequest.of(pageCriteria.getPage(), pageCriteria.getSize());
//    }
//}