package com.capgemini.socialmediafrontendupdated.dto;

import lombok.Data;
import java.util.List;
import com.capgemini.socialmediafrontendupdated.dto.TrendingDTO;
@Data

public class TrendingWrapperDTO {
    private List<TrendingDTO> content;
}