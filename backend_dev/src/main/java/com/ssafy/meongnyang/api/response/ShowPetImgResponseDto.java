package com.ssafy.meongnyang.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowPetImgResponseDto {
    private Long id;
    private Long show_pet_id;
    private String img_url;
}
