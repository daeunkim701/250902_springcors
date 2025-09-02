
package com.example.springcors.controller;

import com.example.springcors.domain.Memo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/memos")
@CrossOrigin // vs code에서 html이랑 js 실행했을 제대로 안 먹히는 걸 해결하기 위해 쓰는 것
// 원래 여기에다가 허용할 출처 url을 적는 건데 그냥 CrossOrigin 이라고만 적으면 싹다 허용함
// 정식적으로 회사에서 하는 origin이 아니기 때문에 허용할 출처만 적는 게 아니라 그냥 풀어둔다.
// 테스트용으로 연습할 때는 CrossOrigin이라고 적고 정식으로 할 때는 풀어둬야 하는 것만 적기
public class MemoController {
    //    private final CopyOnWriteArrayList<Memo> memos = new CopyOnWriteArrayList<>();
    // 이것도 동시성이다~
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

    @DeleteMapping("/{id}") // 삭제하려면 id가 필요함
    public ResponseEntity<Void> deleteMemo(@PathVariable Long id) {
        memos.remove(id);
        return ResponseEntity.noContent().build();
    }
}
