package com.example.lawtest.dto;

public class LawyerWithLoadDto {
    private Long id;
    private String name;
    private int activeOrdersCount;

    public LawyerWithLoadDto(Long id, String name, int activeOrdersCount) {
        this.id = id;
        this.name = name;
        this.activeOrdersCount = activeOrdersCount;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public int getActiveOrdersCount() { return activeOrdersCount; }
}