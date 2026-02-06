package com.techservice.techservice.modules.account.controller;

import com.techservice.techservice.modules.account.dto.AccountResponseDTO;
import com.techservice.techservice.modules.account.usecase.GetAllAccountsUseCase;
import com.techservice.techservice.shared.Routes.Routes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(Routes.ACCOUNT)
public class GetAllAccountsController {
    private final GetAllAccountsUseCase useCase;

    public GetAllAccountsController(GetAllAccountsUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping
    public ResponseEntity<List<AccountResponseDTO>> execute(){
        List<AccountResponseDTO> accounts = useCase.execute();
        if(accounts.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(accounts);
        }
        return ResponseEntity.status(HttpStatus.OK).body(accounts);
    }
}
