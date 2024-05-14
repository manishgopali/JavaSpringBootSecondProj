package com.secondProject.banking.service.impl;

import com.secondProject.banking.entity.Account;
import com.secondProject.banking.dto.AccountDto;
import com.secondProject.banking.mapper.AccountMapper;
import com.secondProject.banking.repository.AccountRepository;
import com.secondProject.banking.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount =accountRepository.save(account);

        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(long id) {
        Account account=accountRepository
                .findById(id)
                .orElseThrow(()->new RuntimeException("Account does not exist"));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto deposit(long id, double amount) {
        Account account=accountRepository
                .findById(id)
                .orElseThrow(()->new RuntimeException("Account does not exist"));
        double updatedBalance = account.getBalance()+amount;
        account.setBalance(updatedBalance);
       Account savedAccount =  accountRepository.save(account);

       return AccountMapper.mapToAccountDto(savedAccount);



    }

    @Override
    public AccountDto withdraw(long id, double amount) {
        Account account=accountRepository
                .findById(id)
                .orElseThrow(()->new RuntimeException("Account does not exist"));
        if(account.getBalance()<amount){
            throw new RuntimeException("Insufficient amount");
        }
        double updatedBalance = account.getBalance()-amount;
        account.setBalance(updatedBalance);
        Account savedAccount =  accountRepository.save(account);

        return AccountMapper.mapToAccountDto(savedAccount);


    }

    @Override
    public List<AccountDto> getAllAccounts() {
       List<Account> accounts= accountRepository.findAll();
       return accounts.stream().map((account) -> AccountMapper.mapToAccountDto(account))
               .collect(Collectors.toList());

    }

    @Override
    public void deleteAccount(long id) {
        Account account=accountRepository
                .findById(id)
                .orElseThrow(()->new RuntimeException("Account does not exist"));

        accountRepository.deleteById(id);

    }


}
