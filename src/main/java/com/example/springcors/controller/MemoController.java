package com.example.springcors.controller;

import com.example.springcors.domain.Memo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/memos")
public class MemoController {
    // private final CopyOnWriteArrayList<Memo> memos = new CopyOnWriteArrayList<>(); // 이것도 동시성이다~
    private final ConcurrentHashMap<Long, Memo> memos = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong();

    @GetMapping
    public List<Memo> getMemos() {
        return memos.values().stream().toList();
    }

    @PostMapping
    public Memo createMemo(@RequestBody Memo.MemoDTO dto) {
        long newId = counter.incrementAndGet();
        Memo memo = new Memo(newId, dto.content());
        memos.put(newId, memo);
        return memo;
    }

    @DeleteMapping("/{id}") // 삭제하려면 id 필요함
    public ResponseEntity<Void> deleteMemo(@PathVariable Long id) {
        memos.remove(id);
        return ResponseEntity.noContent().build();
    }
}
