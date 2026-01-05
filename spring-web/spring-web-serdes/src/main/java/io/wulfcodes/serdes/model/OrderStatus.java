package io.wulfcodes.serdes.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OrderStatus {
        PENDING("pending"),
        CONFIRMED("confirmed"),
        SHIPPED("shipped");

        private String jsonValue;

        OrderStatus(String jsonValue) {
            this.jsonValue = jsonValue;
        }

        @JsonValue
        public String toValue() {
            return jsonValue;
        }
    }