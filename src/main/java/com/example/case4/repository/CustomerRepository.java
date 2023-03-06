package com.example.case4.repository;

import com.example.case4.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

//    List<Customer> findAllByFullNameLikeOrEmailLikeOrPhoneLikeOrAddressLike(String fullName, String email, String phone, String address);
//    List<Customer> findAllByIdNot(long id);

    List<Customer> findAllByFullNameLikeOrEmailLikeOrPhoneLikeOrAddressLikeAndDeletedIsFalse(String fullName, String email, String phone, String address);

    List<Customer> findAllByIdNotAndDeletedFalse(Long id);

    List<Customer> findAllByDeletedFalse();

    Optional<Customer> findByIdAndDeletedFalse(Long id);


}
