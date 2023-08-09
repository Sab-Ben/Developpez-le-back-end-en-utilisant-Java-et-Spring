package com.chatop.chatopbackend.dto.request;

import lombok.Data;

@Data
public class UpdateRentalDto  {
    private String name;
    private double surface;
    private double price;
    private String description;
}
