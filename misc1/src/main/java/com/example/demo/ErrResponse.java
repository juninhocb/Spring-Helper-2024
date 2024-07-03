package com.example.demo;

import com.fasterxml.jackson.annotation.JsonProperty;
record ErrResponse(
        @JsonProperty("err_message") String errMessage,
        String timestamp,
        @JsonProperty("status_code") int statusCode,
        String path) { }

