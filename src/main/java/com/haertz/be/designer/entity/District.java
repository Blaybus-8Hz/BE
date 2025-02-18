package com.haertz.be.designer.entity;

public enum District {
    SEOUL_ALL("서울 전체"),
    GANGNAM_CHUNGDAM_APGUJUNG("강남/청담/압구정"),
    HONGDAE_YEONNAM_HAPJEONG("홍대/연남/합정");

    private final String displayName;

    District(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
