package com.chatop.chatopbackend.controller;

import com.chatop.chatopbackend.dto.request.CreateRentalDto;
import com.chatop.chatopbackend.dto.request.UpdateRentalDto;
import com.chatop.chatopbackend.dto.response.RentalResponse;
import com.chatop.chatopbackend.dto.response.RentalsResponse;
import com.chatop.chatopbackend.dto.response.MessageResponse;
import com.chatop.chatopbackend.service.rental.RentalsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("rentals")
public class RentalsController implements SecurityController {

    private final RentalsService rentalsService;

    public RentalsController(RentalsService rentalsService) {
        this.rentalsService = rentalsService;
    }

    @Operation(summary = "Get all rentals")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "401", content = @Content)
    })
    @GetMapping("")
    public RentalsResponse getAll(){
        return this.rentalsService.getRentals();
    }

    @Operation(summary = "Get one rental")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "401", content = @Content)
    })
    @GetMapping("/{id}")
    public RentalResponse getOne(@PathVariable("id") final long id){
        return this.rentalsService.getRental(id);
    }

    @Operation(summary = "Create one rental")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "401", content = @Content)
    })
    @PostMapping(value = "", consumes = "multipart/form-data")
    public MessageResponse create(@Valid @ModelAttribute CreateRentalDto createRentalDto, Authentication authentication){
        return this.rentalsService.create(createRentalDto, authentication);
    }

    @Operation(summary = "Update one rental")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "401", content = @Content)
    })
    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public MessageResponse update(@PathVariable("id") final long id, @Valid @ModelAttribute UpdateRentalDto updateRentalDto, Authentication authentication){
        return this.rentalsService.update(id, updateRentalDto, authentication);
    }
}
