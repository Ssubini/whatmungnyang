package com.ssafy.meongnyang.api.service;

import com.ssafy.meongnyang.api.request.ShowPetRegisterDto;
import com.ssafy.meongnyang.api.request.ShowPetUpdateDto;
import com.ssafy.meongnyang.api.response.*;
import com.ssafy.meongnyang.common.exception.handler.ShowPetNotFoundException;
import com.ssafy.meongnyang.db.entity.ShowPet;
import com.ssafy.meongnyang.db.entity.ShowPetImg;
import com.ssafy.meongnyang.db.entity.User;
import com.ssafy.meongnyang.db.repository.ShowPetImgRepository;
import com.ssafy.meongnyang.db.repository.ShowPetRepository;
import com.ssafy.meongnyang.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Transactional
@Service
@RequiredArgsConstructor
public class ShowPetServiceImpl implements ShowPetService {

    private final ShowPetRepository showPetRepository;
    private final ShowPetImgRepository showPetImgRepository;
    private final UserRepository userRepository;
    @Override
    public ShowPetResponseDto writeShowPet(ShowPetRegisterDto showPetRegisterDto) {
        User user = userRepository.findById(showPetRegisterDto.getUser_id()).orElseThrow(ShowPetNotFoundException::new);

        ShowPet showPet = ShowPet.builder()
                .user(user)
                .title(showPetRegisterDto.getTitle())
                .content(showPetRegisterDto.getContent())
                .name(showPetRegisterDto.getName())
                .showPetImgList(new ArrayList<>())
                .build();
        ShowPet showPetResponse = showPetRepository.save(showPet);
        List<ShowPetImg> imgList = showPetImgRepository.saveAll(showPetRegisterDto.getImgs()
                .stream()
                .map(s -> ShowPetImg.builder()
                        .img_url(s)
                        .showpet(showPet)
                        .build()).collect(Collectors.toList()));

        ShowPetResponseDto showPetResponseDto = ShowPetResponseDto.builder()
                .id(showPetResponse.getId())
                .user_nickname(showPetResponse.getUser().getNickname())
                .title(showPetResponse.getTitle())
                .content(showPetResponse.getContent())
                .name(showPetResponse.getName())
                .date(showPetResponse.getDate())
                .imgs(imgList
                        .stream()
                        .map(showPetImg -> ShowPetImgResponseDto.builder()
                                .id(showPetImg.getId())
                                .show_pet_id(showPetImg.getShowpet().getId())
                                .img_url(showPetImg.getImg_url())
                                .build())
                        .collect(Collectors.toList()))
                .build();
        return showPetResponseDto;
    }

    @Override
    public ShowPetResponseDto updateShowPet(ShowPetUpdateDto showPetUpdateDto) {
        ShowPet showPet = showPetRepository.findById(showPetUpdateDto.getId()).orElseThrow(ShowPetNotFoundException::new);
        showPetImgRepository.deleteAllByShowPetId(showPet.getId());

        showPet.updateShowPet(showPetUpdateDto);
        List<ShowPetImg> imgList = showPetImgRepository.saveAll(showPetUpdateDto.getImgs()
                .stream()
                .map(s -> ShowPetImg.builder()
                        .img_url(s)
                        .showpet(showPet)
                        .build()).collect(Collectors.toList()));

        ShowPetResponseDto showPetResponseDto = ShowPetResponseDto.builder()
                .id(showPet.getId())
                .user_nickname(showPet.getUser().getNickname())
                .title(showPet.getTitle())
                .content(showPet.getContent())
                .name(showPet.getName())
                .date(showPet.getDate())
                .imgs(imgList
                        .stream()
                        .map(showPetImg -> ShowPetImgResponseDto.builder()
                                .id(showPetImg.getId())
                                .show_pet_id(showPetImg.getShowpet().getId())
                                .img_url(showPetImg.getImg_url())
                                .build())
                        .collect(Collectors.toList()))
                .build();
        return showPetResponseDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShowPetListResponseDto> getShowPetList() {
        return showPetRepository.findAll()
                .stream()
                .map(showPet -> ShowPetListResponseDto.builder()
                        .id(showPet.getId())
                        .title(showPet.getTitle())
                        .user_nickname(showPet.getUser().getNickname())
                        .date(showPet.getDate())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ShowPetDetailResponseDto getShowPet(Long id) {
        ShowPet showPet = showPetRepository.findById(id).orElseThrow(ShowPetNotFoundException::new);
        ShowPetDetailResponseDto showPetDetailResponseDto = ShowPetDetailResponseDto.builder()
                .id(showPet.getId())
                .title(showPet.getTitle())
                .user_nickname(showPet.getUser().getNickname())
                .name(showPet.getName())
                .content(showPet.getContent())
                .date(showPet.getDate())
                .imgs(showPet.getShowPetImgList()
                        .stream()
                        .map(showPetImg -> ShowPetImgResponseDto.builder()
                                .show_pet_id(showPetImg.getShowpet().getId())
                                .id(showPetImg.getId())
                                .img_url(showPetImg.getImg_url())
                                .build())
                        .collect(Collectors.toList()))
                .build();
        return showPetDetailResponseDto;
    }

    @Override
    public boolean deleteShowPet(Long id) {
        showPetRepository.findById(id).orElseThrow(ShowPetNotFoundException::new);
        showPetRepository.deleteById(id);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShowPetListResponseDto> getUserShowPetList(Long id) {
        return showPetRepository.findAllByUserId(id)
                .stream()
                .map(showPet -> ShowPetListResponseDto.builder()
                        .id(showPet.getId())
                        .title(showPet.getTitle())
                        .user_nickname(showPet.getUser().getNickname())
                        .date(showPet.getDate())
                        .build())
                .collect(Collectors.toList());
    }
}