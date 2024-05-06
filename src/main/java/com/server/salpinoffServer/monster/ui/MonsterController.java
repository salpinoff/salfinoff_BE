package com.server.salpinoffServer.monster.ui;

import com.server.salpinoffServer.infra.auth.dto.MemberInfo;
import com.server.salpinoffServer.infra.ui.dto.PageRequest;
import com.server.salpinoffServer.infra.ui.dto.PageResponse;
import com.server.salpinoffServer.monster.service.MonsterService;
import com.server.salpinoffServer.monster.service.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/monsters")
@RequiredArgsConstructor
public class MonsterController {

    private final MonsterService monsterService;

    /**
     * 모든 유저들에게 열어야함
     */
    @GetMapping("/{monsterId}")
    public ResponseEntity<MonsterDetailsResponse> getMonster(@PathVariable(value = "monsterId") Long monsterId) {
        MonsterDetailsResponse response = monsterService.getMonster(monsterId);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/my")
    public ResponseEntity<PageResponse<MonsterDetailsResponse>> getMonstersByMember(
            @AuthenticationPrincipal MemberInfo memberInfo,
            @Valid PageRequest pageRequest) {

        Page<MonsterDetailsResponse> response = monsterService.getMonstersByMember(memberInfo.memberId(), pageRequest.getPageable());

        return ResponseEntity.ok().body(new PageResponse<>(response));
    }

    @PostMapping
    public ResponseEntity<Void> createMonster(@AuthenticationPrincipal MemberInfo memberInfo,
                                              @Valid @RequestBody MonsterCreationRequest request) {
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{monsterId}")
    public ResponseEntity<Void> updateMonster(@AuthenticationPrincipal MemberInfo memberInfo,
                                              @PathVariable(value = "monsterId") Long monsterId,
                                              @Valid @RequestBody MonsterModificationRequest request) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{monsterId}/interactions")
    public ResponseEntity<MonsterInteractionResponse> interactMonster(@PathVariable(value = "monsterId") Long monsterId,
                                                                      @AuthenticationPrincipal MemberInfo memberInfo,
                                                                      @RequestBody MonsterInteractionRequest request) {
        MonsterInteractionResponse response = monsterService.interactMonster(monsterId, memberInfo.memberId(), request);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{monsterId}/messages")
    public ResponseEntity<PageResponse<MonsterMessagesResponse>> getMonsterMessages(
            @PathVariable(value = "monsterId") Long monsterId,
            @AuthenticationPrincipal MemberInfo memberInfo,
            @Valid PageRequest pageRequest) {

        Page<MonsterMessagesResponse> response = monsterService.getMonsterMessages(monsterId, memberInfo.memberId(),
                pageRequest.getPageable());

        return ResponseEntity.ok().body(new PageResponse<>(response));
    }

    @PostMapping("/{monsterId}/messages/{messageId}")
    public ResponseEntity<Void> checkMonsterMessage(@PathVariable(value = "monsterId") Long monsterId,
                                                    @PathVariable(value = "messageId") Long messageId,
                                                    @AuthenticationPrincipal MemberInfo memberInfo) {
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Void> getMonstersByUser(@AuthenticationPrincipal MemberInfo memberInfo,
                                                  @Valid PageRequest pageRequest) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{monsterId}/encouragement")
    public ResponseEntity<Void> encourageUser(@PathVariable(value = "monsterId") Long monsterId,
                                              @Valid @RequestBody EncouragementMessageRequest request) {
        return ResponseEntity.ok().build();
    }
}
