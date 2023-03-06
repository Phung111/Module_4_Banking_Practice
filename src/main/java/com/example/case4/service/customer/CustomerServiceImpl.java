package com.example.case4.service.customer;

import com.example.case4.model.Customer;
import com.example.case4.model.Deposit;
import com.example.case4.model.Transfer;
import com.example.case4.model.Withdraw;
import com.example.case4.repository.CustomerRepository;
import com.example.case4.repository.DepositRepository;
import com.example.case4.repository.TransferRepository;
import com.example.case4.repository.WithdrawRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class CustomerServiceImpl implements ICustomerService{

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private DepositRepository depositRepository;

    @Autowired
    private WithdrawRepository withdrawRepository;

    @Autowired
    private TransferRepository transferRepository;

    @Override
    public List<Customer> findAll() {

//        return customerRepository.findAll();
        return customerRepository.findAllByDeletedFalse();
    }

    @Override
    public Optional<Customer> findById(Long id) {

//        return customerRepository.findById(id);
        return customerRepository.findByIdAndDeletedFalse(id);
    }

    @Override
    public Boolean existById(Long id) {
        return null;
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void delete(Customer customer) {

//        customerRepository.delete(customer);

        customer.setDeleted(true);
        customerRepository.save(customer);
    }

    @Override
    public void deleteById(Long id) {

    }

//    @Override
//    public List<Customer> findAllByFullNameLikeOrEmailLikeOrPhoneLikeOrAddressLike(String fullName, String email, String phone, String address) {
////        return customerRepository.findAllByFullNameLikeOrEmailLikeOrPhoneLikeOrAddressLike(fullName, email, phone, address);
//        return customerRepository.findAllByFullNameLikeOrEmailLikeOrPhoneLikeOrAddressLikeAndDeletedFalse(fullName, email, phone, address);
//    }

    @Override
    public List<Customer> findAllByFullNameLikeOrEmailLikeOrPhoneLikeOrAddressLikeAndDeletedIsFalse(String fullName, String email, String phone, String address) {
        return customerRepository.findAllByFullNameLikeOrEmailLikeOrPhoneLikeOrAddressLikeAndDeletedIsFalse(fullName, email, phone, address);

    }

    @Override
    public List<Customer> findAllByIdNot(Long id) {

//        return customerRepository.findAllByIdNot(id);
        return customerRepository.findAllByIdNotAndDeletedFalse(id);
    }

    @Override
    public Deposit deposit(Deposit deposit) {
        depositRepository.save(deposit);
        customerRepository.save(deposit.getCustomer());

        return deposit;
    }

    @Override
    public Withdraw withdraw(Withdraw withdraw) {
        withdrawRepository.save(withdraw);
        customerRepository.save(withdraw.getCustomer());

        return withdraw;
    }

    @Override
    public Transfer transfer(Transfer transfer) {
        customerRepository.save(transfer.getSender());
        customerRepository.save(transfer.getRecipient());
        transferRepository.save(transfer);

        return transfer;
    }


}
