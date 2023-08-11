package com.chatop.chatopbackend.service.rental;

import com.chatop.chatopbackend.dto.request.CreateRentalDto;
import com.chatop.chatopbackend.dto.request.UpdateRentalDto;
import com.chatop.chatopbackend.dto.response.MessageResponse;
import com.chatop.chatopbackend.dto.response.RentalResponse;
import com.chatop.chatopbackend.dto.response.RentalsResponse;
import org.springframework.security.core.Authentication;

public interface RentalsService {

    RentalsResponse getRentals();

    RentalResponse getRental(final Long id);

    MessageResponse create(CreateRentalDto rentalDto, Authentication authentication);

    public MessageResponse update(final long id, UpdateRentalDto rentalDto, Authentication authentication);
}
