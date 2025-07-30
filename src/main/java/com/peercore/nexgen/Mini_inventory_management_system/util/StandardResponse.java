package com.peercore.nexgen.Mini_inventory_management_system.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class StandardResponse {
    private int status;
    private Object data;
    private String message;

    public StandardResponse(int status, Object data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

}
