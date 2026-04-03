package com.capgemini.socialmediafrontendupdated.dto;

import lombok.Data;
import java.util.List;
import com.capgemini.socialmediafrontendupdated.dto.TrendingDTO;
@Data

public class TrendingWrapperDTO {
    private List<TrendingDTO> content;
    private boolean last;
    private int totalPages;
    private int number;
    private long totalElements;
    private int size;

}