package com.server.salpinoffServer.monster.service;

import com.server.salpinoffServer.monster.domain.Monster;

public interface MonsterRepository {

    Monster saveMonster(Monster monster);
}
