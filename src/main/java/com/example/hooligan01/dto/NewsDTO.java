package com.example.hooligan01.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewsDTO {

    private String platform;
    private String url;
    private List<Posts> posts = new ArrayList<>();

    @Data @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Posts {

        private String title;
        private String href;
    }
}
