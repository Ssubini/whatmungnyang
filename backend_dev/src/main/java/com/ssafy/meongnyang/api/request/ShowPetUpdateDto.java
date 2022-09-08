package com.ssafy.meongnyang.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowPetUpdateDto {
    private Long id;
    private String title;
    private String name;
    private Long user_id;
    private String content;
    private List<String> imgs;

}
