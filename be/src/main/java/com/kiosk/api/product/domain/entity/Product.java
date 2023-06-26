package com.kiosk.api.product.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Product {

    private Long id;            // 상품 아이디
    private String name;        // 이름
    private Long price;         // 가격
    private String imageUrl;       // 이미지 저장 경로
    private boolean isBest;     // 인기 상품
    private boolean hasHot;     // 핫 가능 여부
    private boolean hasIce;     // 아이스 가능 여부
    private boolean hasLarge;   // 라지 사이즈 가능 여부
    private boolean hasSmall;   // 스몰 사이즈 가능 여부
    private Category category;  // 종류

}
