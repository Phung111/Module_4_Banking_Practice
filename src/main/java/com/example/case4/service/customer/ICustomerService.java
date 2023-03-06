package com.example.case4.service.customer;

import com.example.case4.model.Customer;
import com.example.case4.model.Deposit;
import com.example.case4.model.Transfer;
import com.example.case4.model.Withdraw;
import com.example.case4.service.IGeneralService;

import java.util.List;

//public interface ICustomerService {
//
//    List<Customer> findAll();
//
//    void save(Customer customer);
//
//    Customer findById(long id);
//
//    void update(long id, Customer customer);
//
//    void remove(long id);
//}

public interface ICustomerService extends IGeneralService<Customer> {
//    List<Customer> findAllByFullNameLikeOrEmailLikeOrPhoneLikeOrAddressLike(String fullName, String email, String phone, String address);
    List<Customer> findAllByFullNameLikeOrEmailLikeOrPhoneLikeOrAddressLikeAndDeletedIsFalse(String fullName, String email, String phone, String address);

    List<Customer> findAllByIdNot(Long id);

    Deposit deposit(Deposit deposit);

    Transfer transfer(Transfer transfer);

    Withdraw withdraw(Withdraw withdraw);
}
