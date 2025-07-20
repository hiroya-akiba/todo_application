package jp.kouto.fuyuki.akiba.todo_application.dto;

import java.util.List;

public record SelectResultDto<T>(List<T> items, int totalCount) {}