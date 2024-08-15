package com.server.salpinoffServer.monster.service;

import com.server.salpinoffServer.infra.encrypt.AESUtil;
import com.server.salpinoffServer.monster.domain.Monster;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MonsterEncryptService {

    private static final String VALUE_PREFIX = "monster_";
    private final AESUtil aesUtil;

    public String encryptMonsterId(Monster monster) {
        return aesUtil.encrypt(VALUE_PREFIX + monster.getId());
    }

    public Long decryptMonsterId(String encryptedMonsterId) {
        String monsterId = aesUtil.decrypt(encryptedMonsterId).replace(VALUE_PREFIX, "");

        return Long.parseLong(monsterId);
    }
}
