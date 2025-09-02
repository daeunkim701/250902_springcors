package com.example.springcors.domain;

public record Memo(Long id, String content) {
    public record MemoDTO(String content) {}
}