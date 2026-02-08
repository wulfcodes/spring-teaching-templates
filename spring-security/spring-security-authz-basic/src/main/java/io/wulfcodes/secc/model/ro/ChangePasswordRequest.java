package io.wulfcodes.secc.model.ro;

public record ChangePasswordRequest(String oldPassword, String newPassword) {}