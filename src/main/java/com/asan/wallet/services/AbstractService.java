package com.asan.wallet.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


public abstract class AbstractService <E,  R extends JpaRepository<E , String>>{

    @Autowired
    protected R repository;


    public E insert(E e)  {

        return repository.save(e);

    }


}
