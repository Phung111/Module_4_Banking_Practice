package com.example.case4.service.deposit;

import com.example.case4.model.Deposit;
import com.example.case4.repository.DepositRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class DepositServiceImp implements IDepositService {

    @Autowired
    private DepositRepository depositRepository;

    @Override
    public List<Deposit> findAll() {

        return depositRepository.findAll();
    }

    @Override
    public Optional<Deposit> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Boolean existById(Long id) {
        return null;
    }

    @Override
    public Deposit save(Deposit deposit) {
        return depositRepository.save(deposit);
    }

    @Override
    public void delete(Deposit deposit) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
