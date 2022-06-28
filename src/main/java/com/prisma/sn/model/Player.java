package com.prisma.sn.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor
@Getter
public class Player {
    private String name;
    @Setter
    private int position;
    @Setter
    private boolean won;

}
