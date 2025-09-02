package com.example.springcors.domain;

public record Memo(Long id, String content) {
    public record MemoDTO(String content) {} // content만 받고 싶어서
}
