package com.kiosk.api.product.domain.entity;

public enum CategoryType {
    COFFEE, LATTE, TEA, JUICE, SPARKLING;

    public static CategoryType resolve(String value) {
        for (CategoryType categoryType : values()) {
            if (categoryType.name().equalsIgnoreCase(value)) {
                return categoryType;
            }
        }
        throw new RuntimeException("종류를 찾지 못하였습니다. : " + value);
    }
}
