package com.prisma.sn.service;
 import java.util.List;

import com.prisma.sn.model.PlayerName;
import com.prisma.sn.response.GameResponse;
public interface SNService {
    public GameResponse playGame(List<PlayerName> players);
}
