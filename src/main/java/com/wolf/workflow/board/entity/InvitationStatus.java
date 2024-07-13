package com.wolf.workflow.board.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InvitationStatus {
  ACCEPTED("ACCEPTED"),
  DECLINED("DECLINED"),
  ;

  private final String InvitationName;
}
