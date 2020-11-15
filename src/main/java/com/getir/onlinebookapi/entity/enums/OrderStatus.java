package com.getir.onlinebookapi.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {

    AWAITING_APPROVAL(1, "Onay Bekleniyor"),
    APPROVED(2, "Onaylandı"),
    CARGO(3, "Kargoya Verildi"),
    DELIVERED(4, "Teslim Edildi"),
    CANCELLED(5, "İptal Edildi");

    private final Integer id;
    private final String description;
}
