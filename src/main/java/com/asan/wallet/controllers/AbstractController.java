package com.asan.wallet.controllers;

import com.asan.wallet.exceptionHandler.exceptions.ServiceException;
import com.asan.wallet.models.dto.Request;
import com.asan.wallet.models.dto.TransactionDto;
import com.asan.wallet.models.dto.converters.BaseConverter;
import com.asan.wallet.services.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/wallet")
public class AbstractController<E, D, S extends AbstractService<E, ? extends JpaRepository<E, Long>>> {

    @Autowired
    protected S service;

    @Autowired
    protected BaseConverter<E, D> converter;


    @PostMapping("/transactions")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void getTransaction(@RequestBody Request request) throws ServiceException {

        service.get(request);
    }

}
