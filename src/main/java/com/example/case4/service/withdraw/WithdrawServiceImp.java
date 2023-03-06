package com.example.case4.service.withdraw;

import com.example.case4.model.Withdraw;
import com.example.case4.repository.DepositRepository;
import com.example.case4.repository.WithdrawRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class WithdrawServiceImp implements IWithdrawService{

    @Autowired
    private WithdrawRepository withdrawRepository;
    @Override
    public List<Withdraw> findAll() {

        return withdrawRepository.findAll();
    }

    @Override
    public Optional<Withdraw> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Boolean existById(Long id) {
        return null;
    }

    @Override
    public Withdraw save(Withdraw withdraw) {
        return withdrawRepository.save(withdraw);
    }

    @Override
    public void delete(Withdraw withdraw) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
