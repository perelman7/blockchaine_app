package com.ether.web3test.controller;

import com.ether.web3test.model.block.TrxResponse;
import com.ether.web3test.model.transaction.TypeRequest;
import com.ether.web3test.service.block.Web3jBlockProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/block")
public class BlockController {

    @Autowired
    private Web3jBlockProvider blockProvider;

    @GetMapping("/all")
    public ResponseEntity<TrxResponse> getAllTransactions(@RequestParam String privateKey,
                                                          @RequestParam(required = false) TypeRequest typeRequest){
        if(privateKey != null && !privateKey.isEmpty()){
            TrxResponse allTransactions;
            if(typeRequest != null){
                allTransactions = blockProvider.getAllTransactions(privateKey, typeRequest);
            }else{
                allTransactions = blockProvider.getAllTransactions(privateKey, TypeRequest.RECEIVE);
            }
            return new ResponseEntity<>(allTransactions, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
