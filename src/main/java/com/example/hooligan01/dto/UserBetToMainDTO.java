package com.example.hooligan01.dto;

import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder @Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBetToMainDTO {

  private UUID betId;
  private LocalDate date;
  private String home;
  private String away;
  private Double allocation;
  private String win;
}
