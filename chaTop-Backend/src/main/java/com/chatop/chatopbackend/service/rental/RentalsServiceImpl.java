package com.chatop.chatopbackend.service.rental;

import com.chatop.chatopbackend.dto.request.CreateRentalDto;
import com.chatop.chatopbackend.dto.request.UpdateRentalDto;
import com.chatop.chatopbackend.dto.response.RentalResponse;
import com.chatop.chatopbackend.dto.response.RentalsResponse;
import com.chatop.chatopbackend.dto.response.MessageResponse;
import com.chatop.chatopbackend.entity.Rental;
import com.chatop.chatopbackend.repository.RentalRepository;
import com.chatop.chatopbackend.entity.User;
import com.chatop.chatopbackend.service.filestorage.FileStorageServiceImpl;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.webjars.NotFoundException;

@Service
public class RentalsServiceImpl implements RentalsService {

    private final FileStorageServiceImpl fileStorageServiceImpl;

    private final RentalRepository rentalRepository;

    private final ModelMapper modelMapper;

    public RentalsServiceImpl(FileStorageServiceImpl fileStorageServiceImpl, RentalRepository rentalRepository, ModelMapper modelMapper) {
        this.fileStorageServiceImpl = fileStorageServiceImpl;
        this.rentalRepository = rentalRepository;
        this.modelMapper = modelMapper;
    }

    public RentalsResponse getRentals(){
        Iterable<Rental> rentals = this.rentalRepository.findAll();
        rentals.forEach(rental -> rental.setPicture(this.formatImagePath(rental.getPicture())));
        Iterable<RentalResponse> rentalResponses = this.modelMapper.map(rentals, new TypeToken<Iterable<RentalResponse>>() {}.getType());

        return new RentalsResponse(rentalResponses);
    }

    public RentalResponse getRental(final Long id){
        Rental rental = this.rentalRepository.findById(id).orElse(null);
        RentalResponse rentalResponse = this.modelMapper.map(rental, RentalResponse.class);
        String imagePath = this.formatImagePath(rentalResponse.getPicture());
        rentalResponse.setPicture(imagePath);

        return rentalResponse;
    }

    public MessageResponse create(CreateRentalDto rentalDto, Authentication authentication){
        long userId = this.getAuthorizedUserId(authentication);
        String imagePath = this.fileStorageServiceImpl.saveToS3(rentalDto.getPicture());

        Rental rental = this.modelMapper.map(rentalDto, Rental.class);
        rental.setOwner_id(userId);
        rental.setPicture(imagePath);
        this.rentalRepository.save(rental);

        return new MessageResponse().setMessage("Rental created");
    }

    public MessageResponse update(final long id, UpdateRentalDto rentalDto, Authentication authentication){
        Rental dbRental = this.rentalRepository.findById(id).orElse(null);
        if (dbRental == null) {
            throw new NotFoundException("");
        }
        this.verifyUserOwnership(authentication, dbRental.getOwner_id());

        Rental updatedRental = this.mapUpdateData(dbRental, rentalDto);
        this.rentalRepository.save(updatedRental);

        return new MessageResponse().setMessage("Rental updated");
    }

    private String getPathImage(){
        return String.format("https://chatop-bucket.s3.eu-west-3.amazonaws.com/");
    }

    private String formatImagePath(String imagePath){
        return getPathImage() + imagePath;
    }

    private long getAuthorizedUserId(Authentication authentication){
        User authorizedUser = (User) authentication.getPrincipal();
        return authorizedUser.getId();
    }

   private void verifyUserOwnership(Authentication authentication, long ownerId) throws HttpClientErrorException {
       long userId = this.getAuthorizedUserId(authentication);
       if (userId != ownerId){
           throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
       }
   }

   private Rental mapUpdateData(Rental dbRental, UpdateRentalDto rentalDto){
       dbRental.setName(rentalDto.getName());
       dbRental.setPrice(rentalDto.getPrice());
       dbRental.setSurface(rentalDto.getSurface());
       dbRental.setDescription(rentalDto.getDescription());
       return dbRental;
   }
}
