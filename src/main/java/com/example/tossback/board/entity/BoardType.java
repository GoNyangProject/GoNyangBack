package com.example.tossback.board.entity;

import com.example.tossback.board.enums.BoardCode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BoardType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long boardTypeId;

    @Enumerated(EnumType.STRING)
    private BoardCode boardCode;

}
